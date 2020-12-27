package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockState;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class UnderwaterWallPlantBlock extends WallPlantBlock implements ILiquidContainer
{
	public UnderwaterWallPlantBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return worldIn.getFluidState(pos).getFluid() == Fluids.WATER && super.isValidPosition(state, worldIn, pos);
	}
	
	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) 
	{
		return false;
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) 
	{
		return false;
	}

	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getStillFluidState(false);
	}
}
