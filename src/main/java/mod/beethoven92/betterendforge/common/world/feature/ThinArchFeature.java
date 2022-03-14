package mod.beethoven92.betterendforge.common.world.feature;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFCoordModify;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFTorus;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import mod.beethoven92.betterendforge.data.AABBAcc;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class ThinArchFeature extends Feature<NoneFeatureConfiguration> {
    private Block block;

    public ThinArchFeature(Block block)
    {
        super(NoneFeatureConfiguration.CODEC);
        this.block = block;
    }

    public static AABB ofSize(Vec3 vec3, double d, double e, double f) {
        return new AABB (vec3.x - d / 2.0D, vec3.y - e / 2.0D, vec3.z - f / 2.0D, vec3.x + d / 2.0D, vec3.y + e / 2.0D, vec3.z + f / 2.0D);
    }

    public static Vec3 atCenterOf(Vec3i vec3i) {
        return new Vec3((double)vec3i.getX() + 0.5D, (double)vec3i.getY() + 0.5D, (double)vec3i.getZ() + 0.5D);
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator generator, Random random, BlockPos origin,
                            NoneFeatureConfiguration config)
    {
        final WorldGenLevel world = level;

        BlockPos pos = FeatureHelper.getPosOnSurface(world, new BlockPos((origin.getX() & 0xFFFFFFF0) | 7, 0, (origin.getZ() & 0xFFFFFFF0) | 7));
        if (!world.getBlockState(pos.below(5)).is(ModTags.GEN_TERRAIN)) {
            return false;
        }

        SDF sdf = null;
        float bigRadius = ModMathHelper.randRange(15F, 20F, random);
        float variation = bigRadius * 0.3F;
        int count = ModMathHelper.randRange(2, 4, random);
        
        for (int i = 0; i < count; i++) {
            float smallRadius = ModMathHelper.randRange(0.6F, 1.3F, random);
            SDF arch = new SDFTorus().setBigRadius(bigRadius - random.nextFloat() * variation).setSmallRadius(smallRadius).setBlock(block);
            float angle = (i - count * 0.5F) * 0.3F + random.nextFloat() * 0.05F + (float) Math.PI * 0.5F;
            arch = new SDFRotation().setRotation(Vector3f.XP, angle).setSource(arch);
            sdf = sdf == null ? arch : new SDFUnion().setSourceA(sdf).setSourceB(arch);
        }

        sdf = new SDFRotation().setRotation(ModMathHelper.randomHorizontal(random), random.nextFloat() * ModMathHelper.PI2).setSource(sdf);

        OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
        sdf = new SDFCoordModify().setFunction(vec -> {
            float dx = (float) noise.eval(vec.y() * 0.02, vec.z() * 0.02);
            float dy = (float) noise.eval(vec.x() * 0.02, vec.z() * 0.02);
            float dz = (float) noise.eval(vec.x() * 0.02, vec.y() * 0.02);
            vec.set(vec.x() + dx * 10, vec.y() + dy * 10, vec.z() + dz * 10);
        }).setSource(sdf);
        sdf = new SDFDisplacement().setFunction(vec -> {
            float offset = vec.y() / bigRadius - 0.5F;
            return Mth.clamp(offset * 3, -10F, 0F);
        }).setSource(sdf);

        float side = (bigRadius + 2.5F) * 2;
        if (side > 47) {
            side = 47;
        }

        sdf.fillArea(world, pos, ofSize(atCenterOf(pos), side, side, side));
        return true;

    }
}
