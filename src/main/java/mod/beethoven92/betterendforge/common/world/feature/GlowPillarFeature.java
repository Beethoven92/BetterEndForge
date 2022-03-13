package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class GlowPillarFeature extends ScatterFeature {
	public GlowPillarFeature() {
		super(9);
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		return ModBlocks.GLOWING_PILLAR_SEED.get().canSurvive(Blocks.AIR.defaultBlockState(), world, blockPos);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) {
		PlantBlockWithAge seed = ((PlantBlockWithAge) ModBlocks.GLOWING_PILLAR_SEED.get());
		seed.growAdult(world, random, blockPos);
	}
	
	@Override
	protected int getChance() {
		return 10;
	}
}
