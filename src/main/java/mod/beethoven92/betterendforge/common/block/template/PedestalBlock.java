package mod.beethoven92.betterendforge.common.block.template;

import java.util.function.ToIntFunction;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.PedestalState;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PedestalBlock extends Block
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
		VoxelShape basinUp = Block.makeCuboidShape(2, 3, 2, 14, 4, 14);
		VoxelShape basinDown = Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
		VoxelShape columnTopUp = Block.makeCuboidShape(1, 14, 1, 15, 16, 15);
		VoxelShape columnTopDown = Block.makeCuboidShape(2, 13, 2, 14, 14, 14);
		VoxelShape pedestalTop = Block.makeCuboidShape(1, 8, 1, 15, 10, 15);
		VoxelShape pedestalDefault = Block.makeCuboidShape(1, 12, 1, 15, 14, 15);
		VoxelShape pillar = Block.makeCuboidShape(3, 0, 3, 13, 8, 13);
		VoxelShape pillarDefault = Block.makeCuboidShape(3, 0, 3, 13, 12, 13);
		VoxelShape columnTop = VoxelShapes.or(columnTopDown, columnTopUp);
		VoxelShape basin = VoxelShapes.or(basinDown, basinUp);
		SHAPE_PILLAR = Block.makeCuboidShape(3, 0, 3, 13, 16, 13);
		SHAPE_DEFAULT = VoxelShapes.or(basin, pillarDefault, pedestalDefault);
		SHAPE_PEDESTAL_TOP = VoxelShapes.or(pillar, pedestalTop);
		SHAPE_COLUMN_TOP = VoxelShapes.or(SHAPE_PILLAR, columnTop);
		SHAPE_COLUMN = VoxelShapes.or(basin, SHAPE_PILLAR, columnTop);
		SHAPE_BOTTOM = VoxelShapes.or(basin, SHAPE_PILLAR);
	}
	
	protected float height = 1.0F;
	
	public PedestalBlock(Properties properties) 
	{
		super(properties);
		
		this.setDefaultState(this.stateContainer.getBaseState().with(STATE, PedestalState.DEFAULT).with(HAS_ITEM, false).with(HAS_LIGHT, false));
	}
	
	public static ToIntFunction<BlockState> light() {
		return (state) -> {return state.get(HAS_LIGHT) ? 12 : 0;};
	}
	
	public float getHeight(BlockState state) 
	{
		if (state.getBlock() instanceof PedestalBlock && state.get(STATE) == PedestalState.PEDESTAL_TOP) 
		{
			return this.height - 0.2F;
		}
		return this.height;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		if (state.isIn(this)) 
		{
			switch(state.get(STATE)) 
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
		return VoxelShapes.fullCube();
	}

	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return ModTileEntityTypes.PEDESTAL.get().create();
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		if (worldIn.isRemote|| !state.isIn(this)) return ActionResultType.CONSUME;
		if (!this.isPlaceable(state)) 
		{
			return ActionResultType.PASS;
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof PedestalTileEntity) 
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) tileEntity;
			if (pedestal.isEmpty()) 
			{
				ItemStack itemStack = player.getHeldItem(handIn);
				if (itemStack.isEmpty()) return ActionResultType.CONSUME;
				pedestal.setStack(player.abilities.isCreativeMode ? itemStack.copy().split(1) : itemStack.split(1));
				//this.checkRitual(worldIn, pos);
				return ActionResultType.SUCCESS;
			} 
			else 
			{
				ItemStack itemStack = pedestal.getStack();
				if (player.addItemStackToInventory(itemStack)) 
				{
					pedestal.removeStack(worldIn, state);
					return ActionResultType.SUCCESS;
				}
				return ActionResultType.FAIL;
			}
		}
		return ActionResultType.PASS;
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
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (!state.isIn(newState.getBlock())) 
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
	        if (tileentity instanceof PedestalTileEntity) 
	        {
	        	PedestalTileEntity pedestal = (PedestalTileEntity) tileentity;
	        	InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), pedestal.getStack());
	        	worldIn.updateComparatorOutputLevel(pos, this);
	        }
	        super.onReplaced(state, worldIn, pos, newState, isMoving);
	    }
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState upState = world.getBlockState(pos.up());
		BlockState downState = world.getBlockState(pos.down());
		boolean upSideSolid = upState.isSolidSide(world, pos.up(), Direction.DOWN) || upState.isIn(BlockTags.WALLS);
		boolean hasPedestalOver = upState.getBlock() instanceof PedestalBlock;
		boolean hasPedestalUnder = downState.getBlock() instanceof PedestalBlock;
		if (!hasPedestalOver && hasPedestalUnder && upSideSolid) 
		{
			return this.getDefaultState().with(STATE, PedestalState.COLUMN_TOP);
		}
		else if (!hasPedestalOver && !hasPedestalUnder && upSideSolid) 
		{
			return this.getDefaultState().with(STATE, PedestalState.COLUMN);
		}
		else if (hasPedestalUnder && hasPedestalOver) 
		{
			return this.getDefaultState().with(STATE, PedestalState.PILLAR);
		} 
		else if (hasPedestalUnder)
		{
			return this.getDefaultState().with(STATE, PedestalState.PEDESTAL_TOP);
		} 
		else if (hasPedestalOver) 
		{
			return this.getDefaultState().with(STATE, PedestalState.BOTTOM);
		}
		return this.getDefaultState();
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos)
	{
		BlockState updated = this.getUpdatedState(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if (!updated.isIn(this)) return updated;
		if (!this.isPlaceable(updated))
		{
			this.moveStoredStack(worldIn, updated, currentPos);
		}
		return updated;
	}

	private BlockState getUpdatedState(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos posFrom)
	{
		if (!state.isIn(this)) return state.updatePostPlacement(direction, newState, world, pos, posFrom);
		if (direction != Direction.UP && direction != Direction.DOWN) return state;
		BlockState upState = world.getBlockState(pos.up());
		BlockState downState = world.getBlockState(pos.down());
		boolean upSideSolid = upState.isSolidSide(world, pos.up(), Direction.DOWN) || upState.isIn(BlockTags.WALLS);
		boolean hasPedestalOver = upState.getBlock() instanceof PedestalBlock;
		boolean hasPedestalUnder = downState.getBlock() instanceof PedestalBlock;
		if (direction == Direction.UP) 
		{
			upSideSolid = newState.isSolidSide(world, posFrom, Direction.DOWN) || newState.isIn(BlockTags.WALLS);
			hasPedestalOver = newState.getBlock() instanceof PedestalBlock;
		} 
		else if (direction == Direction.DOWN) 
		{
			hasPedestalUnder = newState.getBlock() instanceof PedestalBlock;
		}
		if (!hasPedestalOver && hasPedestalUnder && upSideSolid)
		{
			return state.with(STATE, PedestalState.COLUMN_TOP);
		} 
		else if (!hasPedestalOver && !hasPedestalUnder && upSideSolid) 
		{
			return state.with(STATE, PedestalState.COLUMN);
		} 
		else if (hasPedestalUnder && hasPedestalOver) 
		{
			return state.with(STATE, PedestalState.PILLAR);
		} 
		else if (hasPedestalUnder) 
		{
			return state.with(STATE, PedestalState.PEDESTAL_TOP);
		} 
		else if (hasPedestalOver) 
		{
			return state.with(STATE, PedestalState.BOTTOM);
		}
		return state.with(STATE, PedestalState.DEFAULT);
	}
	
	private void moveStoredStack(IWorld world, BlockState state, BlockPos pos) 
	{
		ItemStack stack = ItemStack.EMPTY;
		TileEntity blockEntity = world.getTileEntity(pos);
		if (blockEntity instanceof PedestalTileEntity && state.isIn(this)) 
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) blockEntity;
			stack = pedestal.getStack();
			pedestal.clear();
			BlockHelper.setWithoutUpdate(world, pos, state.with(HAS_ITEM, false));
		}
		if (!stack.isEmpty()) {
			BlockPos upPos = pos.up();
			this.moveStoredStack(world, stack, world.getBlockState(upPos), upPos);
		}
	}
	
	private void moveStoredStack(IWorld world, ItemStack stack, BlockState state, BlockPos pos)
	{
		TileEntity blockEntity = world.getTileEntity(pos);
		if (!state.isIn(this)) 
		{
			this.dropStoredStack(blockEntity, stack, pos);
		} 
		else if (state.get(STATE).equals(PedestalState.PILLAR))
		{
			BlockPos upPos = pos.up();
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
	
	private void dropStoredStack(TileEntity tileEntity, ItemStack stack, BlockPos pos) 
	{
		if (tileEntity != null && tileEntity.getWorld() != null) 
		{
			World world = tileEntity.getWorld();
			Block.spawnAsEntity(world, this.getDropPos(world, pos), stack);
		}
	}
	
	private BlockPos getDropPos(IWorld world, BlockPos pos) 
	{
		BlockPos dropPos;
		for(int i = 2; i < Direction.values().length; i++) 
		{
			dropPos = pos.offset(Direction.byIndex(i));
			if (world.getBlockState(dropPos).isAir()) 
			{
				return dropPos.toImmutable();
			}
		}
		if (world.getBlockState(pos.up()).isAir()) 
		{
			return pos.up();
		}
		return this.getDropPos(world, pos.up());
	}
	
	protected boolean isPlaceable(BlockState state)
	{
		if (!state.isIn(this)) return false;
		PedestalState currentState = state.get(STATE);
		return currentState != PedestalState.BOTTOM &&
			   currentState != PedestalState.COLUMN &&
			   currentState != PedestalState.PILLAR &&
			   currentState != PedestalState.COLUMN_TOP;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(STATE, HAS_ITEM, HAS_LIGHT);
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) 
	{
		return state.getBlock() instanceof PedestalBlock;
	}
	
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) 
	{
		return blockState.get(HAS_ITEM) ? 15 : 0;
	}
}
