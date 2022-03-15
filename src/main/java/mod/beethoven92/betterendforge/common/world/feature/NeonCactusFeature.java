package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.NeonCactusPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NeonCactusFeature extends Feature<NoneFeatureConfiguration> {

	public NeonCactusFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config) 
	{		BlockState ground = world.getBlockState(pos.below());
		if (!ground.is(ModBlocks.ENDSTONE_DUST.get()) && !ground.is(ModBlocks.END_MOSS.get())) {
			return false;
		}

		NeonCactusPlantBlock cactus = ((NeonCactusPlantBlock) ModBlocks.NEON_CACTUS.get());
		cactus.growPlant(world, pos, rand);

		return true;
	}
}
