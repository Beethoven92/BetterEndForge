package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class WallPlantOnLogFeature extends WallPlantFeature
{
	public WallPlantOnLogFeature(Block block, int radius) 
	{
		super(block, radius);
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos pos, Direction dir)
	{
		BlockPos blockPos = pos.offset(dir.getOpposite());
		BlockState blockState = world.getBlockState(blockPos);
		return blockState.isIn(BlockTags.LOGS);
	}
}
