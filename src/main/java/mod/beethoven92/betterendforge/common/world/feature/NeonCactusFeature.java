package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.NeonCactusPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class NeonCactusFeature extends Feature<NoFeatureConfig> {

	public NeonCactusFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{		BlockState ground = world.getBlockState(pos.down());
		if (!ground.isIn(ModBlocks.ENDSTONE_DUST.get()) && !ground.isIn(ModBlocks.END_MOSS.get())) {
			return false;
		}

		NeonCactusPlantBlock cactus = ((NeonCactusPlantBlock) ModBlocks.NEON_CACTUS.get());
		cactus.growPlant(world, pos, rand);

		return true;
	}
}
