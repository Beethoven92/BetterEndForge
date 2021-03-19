package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FallenPillarFeature extends Feature<NoFeatureConfig> {

	public FallenPillarFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos,
			NoFeatureConfig config) {
		pos = world.getHeight(Heightmap.Type.WORLD_SURFACE,
				new BlockPos(pos.getX() + random.nextInt(16), pos.getY(), pos.getZ() + random.nextInt(16)));
		if (!world.getBlockState(pos.down(5)).isIn(ModTags.GEN_TERRAIN)) {
			return false;
		}

		float height = ModMathHelper.randRange(20F, 40F, random);
		float radius = ModMathHelper.randRange(2F, 4F, random);
		SDF pillar = new SDFCappedCone().setRadius1(radius).setRadius2(radius).setHeight(height * 0.5F)
				.setBlock(Blocks.OBSIDIAN);
		pillar = new SDFTranslate().setTranslate(0, radius * 0.5F - 2, 0).setSource(pillar);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		pillar = new SDFDisplacement().setFunction((vec) -> {
			return (float) (noise.eval(vec.getX() * 0.3, vec.getY() * 0.3, vec.getZ() * 0.3) * 0.5F);
		}).setSource(pillar);
		Vector3f vec = ModMathHelper.randomHorizontal(random);
		float angle = (float) random.nextGaussian() * 0.05F + (float) Math.PI;
		pillar = new SDFRotation().setRotation(vec, angle).setSource(pillar);

		BlockState mossy = ModBlocks.MOSSY_OBSIDIAN.get().getDefaultState();
		pillar.addPostProcess((info) -> {
			if (info.getStateUp().isAir() && random.nextFloat() > 0.1F) {
				return mossy;
			}
			return info.getState();
		}).setReplaceFunction((state) -> {
			return state.getMaterial().isReplaceable() || state.isIn(ModTags.GEN_TERRAIN)
					|| state.getMaterial().equals(Material.PLANTS);
		}).fillRecursive(world, pos);

		return true;
	}
}
