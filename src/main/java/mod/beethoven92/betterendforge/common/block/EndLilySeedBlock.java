package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndLilySeedBlock extends UnderwaterPlantBlockWithAge
{
	public EndLilySeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void doGrow(WorldGenLevel world, Random random, BlockPos pos) 
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
	
	private boolean searchForAirAbove(WorldGenLevel world, BlockPos pos)
	{
		BlockPos up = pos.above();
		while (world.getBlockState(up).getFluidState().getType().equals(Fluids.WATER.getSource())) 
		{
			up = up.above();
		}
		return world.isEmptyBlock(up);
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
