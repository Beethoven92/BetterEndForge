package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.block.template.WallPlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class WallPlantFeature extends WallScatterFeature
{	
	private final Block block;
	
	public WallPlantFeature(Block block, int radius)
	{
		super(radius);
		this.block = block;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos pos, Direction dir) 
	{
		if (block instanceof WallPlantBlock) 
		{
			BlockState state = block.getDefaultState().with(WallPlantBlock.FACING, dir);
			return block.isValidPosition(state, world, pos);
		}
		else if (block instanceof AttachedBlock) 
		{
			BlockState state = block.getDefaultState().with(BlockStateProperties.FACING, dir);
			return block.isValidPosition(state, world, pos);
		}
		return block.isValidPosition(block.getDefaultState(), world, pos);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos pos, Direction dir) 
	{
		BlockState state = block.getDefaultState();
		if (block instanceof WallPlantBlock)
		{
			state = state.with(WallPlantBlock.FACING, dir);
		}
		else if (block instanceof AttachedBlock)
		{
			state = state.with(BlockStateProperties.FACING, dir);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
	}
}
