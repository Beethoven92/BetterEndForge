package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class UnderwaterPlantBlock extends Block implements IGrowable, ILiquidContainer, IForgeShearable
{
	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 14, 12);
	
	public UnderwaterPlantBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.below());
		state = worldIn.getBlockState(pos);
		return isTerrain(down) && state.getFluidState().getType().equals(Fluids.WATER.getSource());
	}
	
	protected boolean isTerrain(BlockState state) 
	{		
		return state.is(ModTags.END_GROUND) || state.getBlock() == ModBlocks.ENDSTONE_DUST.get();
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			worldIn.destroyBlock(currentPos, true);
			return Blocks.WATER.defaultBlockState();
		}
		else 
		{
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
			return stateIn;
		}
	}
	
	@Override
	public boolean canPlaceLiquid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) 
	{
		return false;
	}

	@Override
	public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) 
	{
		return false;
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getSource(false);
	}
	
	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return false;
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return false;
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{	
	}

}
