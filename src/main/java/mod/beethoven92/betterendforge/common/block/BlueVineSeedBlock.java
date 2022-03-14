package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlueVineSeedBlock extends PlantBlockWithAge
{
	public BlueVineSeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void growAdult(WorldGenLevel world, Random random, BlockPos pos) 
	{
		int height = ModMathHelper.randRange(2, 5, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height + 1) 
		{
			return;
		}
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BLUE_VINE.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		for (int i = 1; i < height; i++) 
		{
			BlockHelper.setWithoutUpdate(world, pos.above(i), ModBlocks.BLUE_VINE.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
		}
		BlockHelper.setWithoutUpdate(world, pos.above(height), ModBlocks.BLUE_VINE.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
		placeLantern(world, pos.above(height + 1));
	}
	
	private void placeLantern(WorldGenLevel world, BlockPos pos) 
	{
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BLUE_VINE_LANTERN.get().defaultBlockState().setValue(BlueVineLanternBlock.NATURAL, true));
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
		{
			BlockPos p = pos.relative(dir);
			if (world.isEmptyBlock(p)) 
			{
				BlockHelper.setWithoutUpdate(world, p, ModBlocks.BLUE_VINE_FUR.get().defaultBlockState().setValue(FurBlock.FACING, dir));
			}
		}
		if (world.isEmptyBlock(pos.above())) 
		{
			BlockHelper.setWithoutUpdate(world, pos.above(), ModBlocks.BLUE_VINE_FUR.get().defaultBlockState().setValue(FurBlock.FACING, Direction.UP));
		}
	}
	
	@Override
	protected boolean isTerrain(BlockState state)
	{
		return state.getBlock() == ModBlocks.END_MOSS.get() || state.getBlock() == ModBlocks.END_MYCELIUM.get();
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
