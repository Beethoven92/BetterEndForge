package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class VentBubbleColumnBlock extends Block implements BucketPickup
{
	public VentBubbleColumnBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return Shapes.empty();
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		BlockState blockState = worldIn.getBlockState(pos.below());
		return blockState.is(this) || blockState.is(ModBlocks.HYDROTHERMAL_VENT.get());
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
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
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn)
	{
		BlockState blockState = worldIn.getBlockState(pos.above());
		if (blockState.isAir())
		{
			entityIn.onAboveBubbleCol(false);
			if (!worldIn.isClientSide()) 
			{
				ServerLevel serverWorld = (ServerLevel) worldIn;

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
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) 
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
					SoundSource.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
		}
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) 
	{
		return RenderShape.INVISIBLE;
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getSource(false);
	}

	@Override
	public Fluid takeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state) 
	{
		worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
		return Fluids.WATER;
	}
}
