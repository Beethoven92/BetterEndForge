package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;

public class BubbleCoralBlock extends Block implements ILiquidContainer, IForgeShearable
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 14, 16);
	
	public BubbleCoralBlock(Properties properties) 
	{
		super(properties);
	}
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble() * 0.5F + 0.5F;
		double z = pos.getZ() + rand.nextDouble();
		worldIn.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0D, 0.0D, 0.0D);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.down());
		state = worldIn.getBlockState(pos);
		return isTerrain(down) && state.getFluidState().getFluid().equals(Fluids.WATER.getStillFluid());
	}
	
	protected boolean isTerrain(BlockState state) 
	{
		//return state.isIn(EndTags.END_GROUND) || state.getBlock() == EndBlocks.ENDSTONE_DUST;
		return state.isIn(ModBlocks.ENDSTONE_DUST.get());
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.WATER.getDefaultState();
		}
		else 
		{
			return stateIn;
		}
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
