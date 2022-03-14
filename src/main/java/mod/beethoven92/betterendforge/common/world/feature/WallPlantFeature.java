package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.block.template.WallPlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class WallPlantFeature extends WallScatterFeature
{	
	private final Block block;
	
	public WallPlantFeature(Block block, int radius)
	{
		super(radius);
		this.block = block;
	}

	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos pos, Direction dir) 
	{
		if (block instanceof WallPlantBlock) 
		{
			BlockState state = block.defaultBlockState().setValue(WallPlantBlock.FACING, dir);
			return block.canSurvive(state, world, pos);
		}
		else if (block instanceof AttachedBlock) 
		{
			BlockState state = block.defaultBlockState().setValue(BlockStateProperties.FACING, dir);
			return block.canSurvive(state, world, pos);
		}
		return block.canSurvive(block.defaultBlockState(), world, pos);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos pos, Direction dir) 
	{
		BlockState state = block.defaultBlockState();
		if (block instanceof WallPlantBlock)
		{
			state = state.setValue(WallPlantBlock.FACING, dir);
		}
		else if (block instanceof AttachedBlock)
		{
			state = state.setValue(BlockStateProperties.FACING, dir);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
	}
}
