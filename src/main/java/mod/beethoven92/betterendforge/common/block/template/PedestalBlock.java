package mod.beethoven92.betterendforge.common.block.template;

import java.util.function.ToIntFunction;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.PedestalState;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends Block implements EntityBlock
{
	public static final EnumProperty<PedestalState> STATE = BlockProperties.PEDESTAL_STATE;
	public static final BooleanProperty HAS_ITEM = BlockProperties.HAS_ITEM;
	public static final BooleanProperty HAS_LIGHT = BlockProperties.HAS_LIGHT;
	
	private static final VoxelShape SHAPE_DEFAULT;
	private static final VoxelShape SHAPE_COLUMN;
	private static final VoxelShape SHAPE_PILLAR;
	private static final VoxelShape SHAPE_PEDESTAL_TOP;
	private static final VoxelShape SHAPE_COLUMN_TOP;
	private static final VoxelShape SHAPE_BOTTOM;
	
	static 
	{
		VoxelShape basinUp = Block.box(2, 3, 2, 14, 4, 14);
		VoxelShape basinDown = Block.box(0, 0, 0, 16, 3, 16);
		VoxelShape columnTopUp = Block.box(1, 14, 1, 15, 16, 15);
		VoxelShape columnTopDown = Block.box(2, 13, 2, 14, 14, 14);
		VoxelShape pedestalTop = Block.box(1, 8, 1, 15, 10, 15);
		VoxelShape pedestalDefault = Block.box(1, 12, 1, 15, 14, 15);
		VoxelShape pillar = Block.box(3, 0, 3, 13, 8, 13);
		VoxelShape pillarDefault = Block.box(3, 0, 3, 13, 12, 13);
		VoxelShape columnTop = Shapes.or(columnTopDown, columnTopUp);
		VoxelShape basin = Shapes.or(basinDown, basinUp);
		SHAPE_PILLAR = Block.box(3, 0, 3, 13, 16, 13);
		SHAPE_DEFAULT = Shapes.or(basin, pillarDefault, pedestalDefault);
		SHAPE_PEDESTAL_TOP = Shapes.or(pillar, pedestalTop);
		SHAPE_COLUMN_TOP = Shapes.or(SHAPE_PILLAR, columnTop);
		SHAPE_COLUMN = Shapes.or(basin, SHAPE_PILLAR, columnTop);
		SHAPE_BOTTOM = Shapes.or(basin, SHAPE_PILLAR);
	}
	
	protected float height = 1.0F;
	
	public PedestalBlock(Properties properties) 
	{
		super(properties);
		
		this.registerDefaultState(this.stateDefinition.any().setValue(STATE, PedestalState.DEFAULT).setValue(HAS_ITEM, false).setValue(HAS_LIGHT, false));
	}
	
	public static ToIntFunction<BlockState> light() {
		return (state) -> {return state.getValue(HAS_LIGHT) ? 12 : 0;};
	}
	
	public float getHeight(BlockState state) 
	{
		if (state.getBlock() instanceof PedestalBlock && state.getValue(STATE) == PedestalState.PEDESTAL_TOP) 
		{
			return this.height - 0.2F;
		}
		return this.height;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		if (state.is(this)) 
		{
			switch(state.getValue(STATE)) 
			{
				case BOTTOM: 
				{
					return SHAPE_BOTTOM;
				}
				case PEDESTAL_TOP: 
				{
					return SHAPE_PEDESTAL_TOP;
				}
				case COLUMN_TOP: 
				{
					return SHAPE_COLUMN_TOP;
				}
				case PILLAR: 
				{
					return SHAPE_PILLAR;
				}
				case COLUMN: 
				{
					return SHAPE_COLUMN;
				}
				default: 
				{
					return SHAPE_DEFAULT;
				}
			}
		}
		return Shapes.block();
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return ModTileEntityTypes.PEDESTAL.get().create(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
		return PedestalTileEntity::commonTick;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) 
	{
		if (worldIn.isClientSide|| !state.is(this)) return InteractionResult.CONSUME;
		if (!this.isPlaceable(state)) 
		{
			return InteractionResult.PASS;
		}
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof PedestalTileEntity) 
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) tileEntity;
			if (pedestal.isEmpty()) 
			{
				ItemStack itemStack = player.getItemInHand(handIn);
				if (itemStack.isEmpty()) return InteractionResult.CONSUME;
				pedestal.setStack(player.getAbilities().instabuild ? itemStack.copy().split(1) : itemStack.split(1));
				//this.checkRitual(worldIn, pos);
				return InteractionResult.SUCCESS;
			} 
			else 
			{
				ItemStack itemStack = pedestal.getStack();
				if (player.addItem(itemStack)) 
				{
					pedestal.removeStack(worldIn, state);
					return InteractionResult.SUCCESS;
				}
				return InteractionResult.FAIL;
			}
		}
		return InteractionResult.PASS;
	}
	
	/*public void checkRitual(World world, BlockPos pos) 
	{
		Mutable mut = new Mutable();
		Point[] points = InfusionRitual.getMap();
		for (Point p: points) 
		{
			mut.setPos(pos).move(p.x, 0, p.y);
			BlockState state = world.getBlockState(mut);
			if (state.getBlock() instanceof InfusionPedestal) 
			{
				((InfusionPedestal) state.getBlock()).checkRitual(world, mut);
				break;
			}
		}
	}*/
	
	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (!state.is(newState.getBlock())) 
		{
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
	        if (tileentity instanceof PedestalTileEntity) 
	        {
	        	PedestalTileEntity pedestal = (PedestalTileEntity) tileentity;
	        	Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), pedestal.getStack());
	        	worldIn.updateNeighbourForOutputSignal(pos, this);
	        }
	        super.onRemove(state, worldIn, pos, newState, isMoving);
	    }
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState upState = world.getBlockState(pos.above());
		BlockState downState = world.getBlockState(pos.below());
		boolean upSideSolid = upState.isFaceSturdy(world, pos.above(), Direction.DOWN) || upState.is(BlockTags.WALLS);
		boolean hasPedestalOver = upState.getBlock() instanceof PedestalBlock;
		boolean hasPedestalUnder = downState.getBlock() instanceof PedestalBlock;
		if (!hasPedestalOver && hasPedestalUnder && upSideSolid) 
		{
			return this.defaultBlockState().setValue(STATE, PedestalState.COLUMN_TOP);
		}
		else if (!hasPedestalOver && !hasPedestalUnder && upSideSolid) 
		{
			return this.defaultBlockState().setValue(STATE, PedestalState.COLUMN);
		}
		else if (hasPedestalUnder && hasPedestalOver) 
		{
			return this.defaultBlockState().setValue(STATE, PedestalState.PILLAR);
		} 
		else if (hasPedestalUnder)
		{
			return this.defaultBlockState().setValue(STATE, PedestalState.PEDESTAL_TOP);
		} 
		else if (hasPedestalOver) 
		{
			return this.defaultBlockState().setValue(STATE, PedestalState.BOTTOM);
		}
		return this.defaultBlockState();
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos)
	{
		BlockState updated = this.getUpdatedState(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if (!updated.is(this)) return updated;
		if (!this.isPlaceable(updated))
		{
			this.moveStoredStack(worldIn, updated, currentPos);
		}
		return updated;
	}

	private BlockState getUpdatedState(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom)
	{
		if (!state.is(this)) return state.updateShape(direction, newState, world, pos, posFrom);
		if (direction != Direction.UP && direction != Direction.DOWN) return state;
		BlockState upState = world.getBlockState(pos.above());
		BlockState downState = world.getBlockState(pos.below());
		boolean upSideSolid = upState.isFaceSturdy(world, pos.above(), Direction.DOWN) || upState.is(BlockTags.WALLS);
		boolean hasPedestalOver = upState.getBlock() instanceof PedestalBlock;
		boolean hasPedestalUnder = downState.getBlock() instanceof PedestalBlock;
		if (direction == Direction.UP) 
		{
			upSideSolid = newState.isFaceSturdy(world, posFrom, Direction.DOWN) || newState.is(BlockTags.WALLS);
			hasPedestalOver = newState.getBlock() instanceof PedestalBlock;
		} 
		else if (direction == Direction.DOWN) 
		{
			hasPedestalUnder = newState.getBlock() instanceof PedestalBlock;
		}
		if (!hasPedestalOver && hasPedestalUnder && upSideSolid)
		{
			return state.setValue(STATE, PedestalState.COLUMN_TOP);
		} 
		else if (!hasPedestalOver && !hasPedestalUnder && upSideSolid) 
		{
			return state.setValue(STATE, PedestalState.COLUMN);
		} 
		else if (hasPedestalUnder && hasPedestalOver) 
		{
			return state.setValue(STATE, PedestalState.PILLAR);
		} 
		else if (hasPedestalUnder) 
		{
			return state.setValue(STATE, PedestalState.PEDESTAL_TOP);
		} 
		else if (hasPedestalOver) 
		{
			return state.setValue(STATE, PedestalState.BOTTOM);
		}
		return state.setValue(STATE, PedestalState.DEFAULT);
	}
	
	private void moveStoredStack(LevelAccessor world, BlockState state, BlockPos pos) 
	{
		ItemStack stack = ItemStack.EMPTY;
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof PedestalTileEntity && state.is(this)) 
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) blockEntity;
			stack = pedestal.getStack();
			pedestal.clear();
			BlockHelper.setWithoutUpdate(world, pos, state.setValue(HAS_ITEM, false));
		}
		if (!stack.isEmpty()) {
			BlockPos upPos = pos.above();
			this.moveStoredStack(world, stack, world.getBlockState(upPos), upPos);
		}
	}
	
	private void moveStoredStack(LevelAccessor world, ItemStack stack, BlockState state, BlockPos pos)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (!state.is(this)) 
		{
			this.dropStoredStack(blockEntity, stack, pos);
		} 
		else if (state.getValue(STATE).equals(PedestalState.PILLAR))
		{
			BlockPos upPos = pos.above();
			this.moveStoredStack(world, stack, world.getBlockState(upPos), upPos);
		} 
		else if (!this.isPlaceable(state)) 
		{
			this.dropStoredStack(blockEntity, stack, pos);
		} 
		else if (blockEntity instanceof PedestalTileEntity) 
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) blockEntity;
			if (pedestal.isEmpty()) 
			{
				pedestal.setStack(stack);
			} 
			else 
			{
				this.dropStoredStack(blockEntity, stack, pos);
			}
		} 
		else 
		{
			this.dropStoredStack(blockEntity, stack, pos);
		}
	}
	
	private void dropStoredStack(BlockEntity tileEntity, ItemStack stack, BlockPos pos) 
	{
		if (tileEntity != null && tileEntity.getLevel() != null) 
		{
			Level world = tileEntity.getLevel();
			Block.popResource(world, this.getDropPos(world, pos), stack);
		}
	}
	
	private BlockPos getDropPos(LevelAccessor world, BlockPos pos) 
	{
		BlockPos dropPos;
		for(int i = 2; i < Direction.values().length; i++) 
		{
			dropPos = pos.relative(Direction.from3DDataValue(i));
			if (world.getBlockState(dropPos).isAir()) 
			{
				return dropPos.immutable();
			}
		}
		if (world.getBlockState(pos.above()).isAir()) 
		{
			return pos.above();
		}
		return this.getDropPos(world, pos.above());
	}
	
	protected boolean isPlaceable(BlockState state)
	{
		if (!state.is(this)) return false;
		PedestalState currentState = state.getValue(STATE);
		return currentState != PedestalState.BOTTOM &&
			   currentState != PedestalState.COLUMN &&
			   currentState != PedestalState.PILLAR &&
			   currentState != PedestalState.COLUMN_TOP;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(STATE, HAS_ITEM, HAS_LIGHT);
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) 
	{
		return state.getBlock() instanceof PedestalBlock;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) 
	{
		return blockState.getValue(HAS_ITEM) ? 15 : 0;
	}
}
