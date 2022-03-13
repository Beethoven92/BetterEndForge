package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class EndLilySeedBlock extends UnderwaterPlantBlockWithAge
{
	public EndLilySeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void doGrow(ISeedReader world, Random random, BlockPos pos) 
	{	
		if (searchForAirAbove(world, pos)) 
		{
			BlockHelper.setWithoutUpdate(world, pos, ModBlocks.END_LILY.get().defaultBlockState().setValue(EndLilyBlock.SHAPE, TripleShape.BOTTOM));
			BlockPos up = pos.above();
			while (world.getFluidState(up).isSource()) 
			{
				BlockHelper.setWithoutUpdate(world, up, ModBlocks.END_LILY.get().defaultBlockState().setValue(EndLilyBlock.SHAPE, TripleShape.MIDDLE));
				up = up.above();
			}
			BlockHelper.setWithoutUpdate(world, up, ModBlocks.END_LILY.get().defaultBlockState().setValue(EndLilyBlock.SHAPE, TripleShape.TOP));
		}
	}
	
	private boolean searchForAirAbove(ISeedReader world, BlockPos pos)
	{
		BlockPos up = pos.above();
		while (world.getBlockState(up).getFluidState().getType().equals(Fluids.WATER.getSource())) 
		{
			up = up.above();
		}
		return world.isEmptyBlock(up);
	}
	
	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
