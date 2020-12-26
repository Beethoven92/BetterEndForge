package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

public class BlueVineSeedBlock extends PlantBlockWithAge
{
	public BlueVineSeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void growAdult(ISeedReader world, Random random, BlockPos pos) 
	{
		int height = ModMathHelper.randRange(2, 5, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height + 1) 
		{
			return;
		}
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BLUE_VINE.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		for (int i = 1; i < height; i++) 
		{
			BlockHelper.setWithoutUpdate(world, pos.up(i), ModBlocks.BLUE_VINE.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
		}
		BlockHelper.setWithoutUpdate(world, pos.up(height), ModBlocks.BLUE_VINE.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
		placeLantern(world, pos.up(height + 1));
	}
	
	private void placeLantern(ISeedReader world, BlockPos pos) 
	{
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BLUE_VINE_LANTERN.get().getDefaultState().with(BlueVineLanternBlock.NATURAL, true));
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
		{
			BlockPos p = pos.offset(dir);
			if (world.isAirBlock(p)) 
			{
				BlockHelper.setWithoutUpdate(world, p, ModBlocks.BLUE_VINE_FUR.get().getDefaultState().with(FurBlock.FACING, dir));
			}
		}
		if (world.isAirBlock(pos.up())) 
		{
			BlockHelper.setWithoutUpdate(world, pos.up(), ModBlocks.BLUE_VINE_FUR.get().getDefaultState().with(FurBlock.FACING, Direction.UP));
		}
	}
	
	@Override
	protected boolean isTerrain(BlockState state)
	{
		return state.getBlock() == ModBlocks.END_MOSS.get() || state.getBlock() == ModBlocks.END_MYCELIUM.get();
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
