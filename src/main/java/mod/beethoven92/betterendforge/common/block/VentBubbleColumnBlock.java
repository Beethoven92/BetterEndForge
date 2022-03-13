package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock.Properties;

public class VentBubbleColumnBlock extends Block implements IBucketPickupHandler
{
	public VentBubbleColumnBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return VoxelShapes.empty();
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState blockState = worldIn.getBlockState(pos.below());
		return blockState.is(this) || blockState.is(ModBlocks.HYDROTHERMAL_VENT.get());
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!stateIn.canSurvive(worldIn, currentPos)) 
		{
			return Blocks.WATER.defaultBlockState();
		}
		else 
		{
			BlockPos up = currentPos.above();
			if (worldIn.getBlockState(up).is(Blocks.WATER)) 
			{
				BlockHelper.setWithoutUpdate(worldIn, up, this);
				worldIn.getBlockTicks().scheduleTick(up, this, 5);
			}
		}
		return stateIn;
	}
	
	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		BlockState blockState = worldIn.getBlockState(pos.above());
		if (blockState.isAir())
		{
			entityIn.onAboveBubbleCol(false);
			if (!worldIn.isClientSide()) 
			{
				ServerWorld serverWorld = (ServerWorld) worldIn;

				for (int i = 0; i < 2; ++i) 
				{
					serverWorld.sendParticles(ParticleTypes.SPLASH, (double) pos.getX() + worldIn.random.nextDouble(), (double) (pos.getY() + 1), (double) pos.getZ() + worldIn.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
					serverWorld.sendParticles(ParticleTypes.BUBBLE, (double) pos.getX() + worldIn.random.nextDouble(), (double) (pos.getY() + 1), (double) pos.getZ() + worldIn.random.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
				}
			}
		}
		else 
		{
			entityIn.onInsideBubbleColumn(false);
		}
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		if (rand.nextInt(4) == 0) 
		{
			double px = pos.getX() + rand.nextDouble();
			double py = pos.getY() + rand.nextDouble();
			double pz = pos.getZ() + rand.nextDouble();
			worldIn.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, px, py, pz, 0, 0.04, 0);
		}
		if (rand.nextInt(200) == 0) 
		{
			worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, 
					SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
		}
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) 
	{
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getSource(false);
	}

	@Override
	public Fluid takeLiquid(IWorld worldIn, BlockPos pos, BlockState state) 
	{
		worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
		return Fluids.WATER;
	}
}
