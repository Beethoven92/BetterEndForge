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
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class ThinArchFeature extends Feature<NoFeatureConfig> {
    private Block block;

    public ThinArchFeature(Block block)
    {
        super(NoFeatureConfig.field_236558_a_);
        this.block = block;
    }

    public static AxisAlignedBB ofSize(Vector3d vec3, double d, double e, double f) {
        return new AxisAlignedBB (vec3.x - d / 2.0D, vec3.y - e / 2.0D, vec3.z - f / 2.0D, vec3.x + d / 2.0D, vec3.y + e / 2.0D, vec3.z + f / 2.0D);
    }

    public static Vector3d atCenterOf(Vector3i vec3i) {
        return new Vector3d((double)vec3i.getX() + 0.5D, (double)vec3i.getY() + 0.5D, (double)vec3i.getZ() + 0.5D);
    }

    @Override
    public boolean generate(ISeedReader level, ChunkGenerator generator, Random random, BlockPos origin,
                            NoFeatureConfig config)
    {
        final ISeedReader world = level;

        BlockPos pos = FeatureHelper.getPosOnSurface(world, new BlockPos((origin.getX() & 0xFFFFFFF0) | 7, 0, (origin.getZ() & 0xFFFFFFF0) | 7));
        if (!world.getBlockState(pos.down(5)).isIn(ModTags.GEN_TERRAIN)) {
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
            float dx = (float) noise.eval(vec.getY() * 0.02, vec.getZ() * 0.02);
            float dy = (float) noise.eval(vec.getX() * 0.02, vec.getZ() * 0.02);
            float dz = (float) noise.eval(vec.getX() * 0.02, vec.getY() * 0.02);
            vec.set(vec.getX() + dx * 10, vec.getY() + dy * 10, vec.getZ() + dz * 10);
        }).setSource(sdf);
        sdf = new SDFDisplacement().setFunction(vec -> {
            float offset = vec.getY() / bigRadius - 0.5F;
            return MathHelper.clamp(offset * 3, -10F, 0F);
        }).setSource(sdf);

        float side = (bigRadius + 2.5F) * 2;
        if (side > 47) {
            side = 47;
        }

        sdf.fillArea(world, pos, ofSize(atCenterOf(pos), side, side, side));
        return true;

    }
}
