package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class LanceleafFeature extends ScatterFeature {
	public LanceleafFeature() {
		super(7);
	}

	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		return ModBlocks.LANCELEAF_SEED.get().canSurvive(Blocks.AIR.defaultBlockState(), world, blockPos);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) {
		PlantBlockWithAge seed = ((PlantBlockWithAge) ModBlocks.LANCELEAF_SEED.get());
		seed.growAdult(world, random, blockPos);
	}

	@Override
	protected int getChance() {
		return 5;
	}
}
