package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.tileentity.HydrothermalVentTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import javax.annotation.Nullable;

public class HydrothermalVentBlock extends Block implements SimpleWaterloggedBlock, EntityBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	private static final VoxelShape SHAPE = Block.box(1, 1, 1, 15, 16, 15);
	
	public HydrothermalVentBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, true).setValue(ACTIVATED, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return SHAPE;
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new HydrothermalVentTileEntity(p_153215_, p_153216_);
	}
	
	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if (worldIn instanceof ServerLevel && state.getValue(WATERLOGGED) && worldIn.getBlockState(pos.above()).is(Blocks.WATER))
		{
			randomTick(state,(ServerLevel) worldIn, pos, worldIn.random);
		}
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		state = worldIn.getBlockState(pos.below());
		return state.is(ModBlocks.SULPHURIC_ROCK.stone.get());
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		Level world = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(blockPos).getType() == Fluids.WATER);
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			return Blocks.WATER.defaultBlockState();
		}
		else if (stateIn.getValue(WATERLOGGED) && facing == Direction.UP && facingState.is(Blocks.WATER)) 
		{
			worldIn.scheduleTick(currentPos, this, 20);
		}
		return stateIn;
	}
	
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) 
	{
		if (!stateIn.getValue(ACTIVATED) && rand.nextBoolean()) 
		{
			double x = pos.getX() + rand.nextDouble();
			double y = pos.getY() + 0.9 + rand.nextDouble() * 0.3;
			double z = pos.getZ() + rand.nextDouble();
			if (stateIn.getValue(WATERLOGGED)) 
			{
				worldIn.addParticle(ModParticleTypes.GEYSER_PARTICLE.get(), x, y, z, 0, 0, 0);
			}
			else 
			{
				worldIn.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
			}
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) 
	{
		BlockPos up = pos.above();
		if (worldIn.getBlockState(up).is(Blocks.WATER)) 
		{
			BlockHelper.setWithoutUpdate(worldIn, up, ModBlocks.VENT_BUBBLE_COLUMN.get());
			worldIn.scheduleTick(up, ModBlocks.VENT_BUBBLE_COLUMN.get(), 5);
		}
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return (Boolean) state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(WATERLOGGED, ACTIVATED);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return HydrothermalVentTileEntity::commonTick;
	}
}
