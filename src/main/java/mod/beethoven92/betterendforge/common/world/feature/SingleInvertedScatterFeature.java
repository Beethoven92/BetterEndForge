package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class SingleInvertedScatterFeature extends InvertedScatterFeature
{
	private final Block block;
	
	public SingleInvertedScatterFeature(Block block, int radius) 
	{
		super(radius);
		this.block = block;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		if (!world.isAirBlock(blockPos)) 
		{
			return false;
		}
		BlockState state = block.getDefaultState();
		if (block instanceof AttachedBlock) 
		{
			state = state.with(BlockStateProperties.FACING, Direction.DOWN);
		}
		return state.isValidPosition(world, blockPos);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
	{
		BlockState state = block.getDefaultState();
		if (block instanceof AttachedBlock) 
		{
			state = state.with(BlockStateProperties.FACING, Direction.DOWN);
		}
		BlockHelper.setWithoutUpdate(world, blockPos, state);
	}
}
