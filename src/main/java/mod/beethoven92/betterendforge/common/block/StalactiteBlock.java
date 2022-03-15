package mod.beethoven92.betterendforge.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class StalactiteBlock extends Block implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty IS_FLOOR = BlockProperties.IS_FLOOR;
	public static final IntegerProperty SIZE = BlockProperties.SIZE;
	private static final VoxelShape[] SHAPES;

	static 
	{
		float end = 2F / 8F;
		float start = 5F / 8F;
		SHAPES = new VoxelShape[8];
		for (int i = 0; i < 8; i++) 
		{
			int side = Mth.floor(Mth.lerp(i / 7F, start, end) * 8F + 0.5F);
			SHAPES[i] = Block.box(side, 0, side, 16 - side, 16, 16 - side);
		}
	}
	
	public StalactiteBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(SIZE, 0).setValue(IS_FLOOR, true).setValue(WATERLOGGED, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return SHAPES[state.getValue(SIZE)];
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		LevelReader world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction dir = context.getClickedFace();
		boolean water = world.getFluidState(pos).getType() == Fluids.WATER;
		
		if (dir == Direction.DOWN) 
		{
			System.out.println("Check up!");
			if (isThis(world, pos.above()) || this.canSupportCenter(world, pos.above(), Direction.DOWN)) 
			{
				System.out.println("Up true!");
				return defaultBlockState().setValue(IS_FLOOR, false).setValue(WATERLOGGED, water);
			}
			else if (isThis(world, pos.below()) || canSupportCenter(world, pos.below(), Direction.UP)) 
			{
				System.out.println("Up false!");
				return defaultBlockState().setValue(IS_FLOOR, true).setValue(WATERLOGGED, water);
			}
			else 
			{
				return null;
			}
		}
		else 
		{
			System.out.println("Check down!");
			if (isThis(world, pos.below()) || canSupportCenter(world, pos.below(), Direction.UP)) 
			{
				System.out.println("Down true!");
				return defaultBlockState().setValue(IS_FLOOR, true).setValue(WATERLOGGED, water);
			}
			else if (isThis(world, pos.above()) || canSupportCenter(world, pos.above(), Direction.DOWN)) 
			{
				System.out.println("Down false!");
				return defaultBlockState().setValue(IS_FLOOR, false).setValue(WATERLOGGED, water);
			}
			else 
			{
				return null;
			}
		}
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) 
	{
		boolean hasUp = isThis(world, pos.above());
		boolean hasDown = isThis(world, pos.below());
		MutableBlockPos mut = new MutableBlockPos();
		if (hasUp && hasDown) 
		{
			boolean floor = state.getValue(IS_FLOOR);
			BlockPos second = floor ? pos.above() : pos.below();
			BlockState bState = world.getBlockState(second);
			world.setBlockAndUpdate(pos, state.setValue(SIZE, 1).setValue(IS_FLOOR, floor));
			world.setBlockAndUpdate(second, bState.setValue(SIZE, 1).setValue(IS_FLOOR, !floor));
			
			bState = state;
			int startSize = floor ? 1 : 2;
			mut.set(pos.getX(), pos.getY() + 1, pos.getZ());
			for (int i = 0; i < 8 && isThis(bState); i++) 
			{
				world.setBlockAndUpdate(mut, bState.setValue(SIZE, startSize++).setValue(IS_FLOOR, false));
				mut.setY(mut.getY() + 1);
				bState = world.getBlockState(mut);
			}
			
			bState = state;
			startSize = floor ? 2 : 1;
			mut.set(pos.getX(), pos.getY() - 1, pos.getZ());
			for (int i = 0; i < 8 && isThis(bState); i++) 
			{
				world.setBlockAndUpdate(mut, bState.setValue(SIZE, startSize++).setValue(IS_FLOOR, true));
				mut.setY(mut.getY() - 1);
				bState = world.getBlockState(mut);
			}
		}
		else if (hasDown)
		{
			mut.setX(pos.getX());
			mut.setZ(pos.getZ());
			for (int i = 1; i < 8; i++) 
			{
				mut.setY(pos.getY() - i);
				if (isThis(world, mut)) 
				{
					BlockState state2 = world.getBlockState(mut);
					int size = state2.getValue(SIZE);
					if (size < i) 
					{
						world.setBlockAndUpdate(mut, state2.setValue(SIZE, i).setValue(IS_FLOOR, true));
					}
					else 
					{
						break;
					}
				}
				else 
				{
					break;
				}
			}
		}
		else if (hasUp)
		{
			mut.setX(pos.getX());
			mut.setZ(pos.getZ());
			for (int i = 1; i < 8; i++)
			{
				mut.setY(pos.getY() + i);
				if (isThis(world, mut)) 
				{
					BlockState state2 = world.getBlockState(mut);
					int size = state2.getValue(SIZE);
					if (size < i) 
					{
						world.setBlockAndUpdate(mut, state2.setValue(SIZE, i).setValue(IS_FLOOR, false));
					}
					else 
					{
						break;
					}
				}
				else 
				{
					break;
				}
			}
		}
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.defaultBlockState();
		}
		return stateIn;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		int size = state.getValue(SIZE);
		return checkUp(worldIn, pos, size) || checkDown(worldIn, pos, size);
	}
	
	private boolean isThis(LevelReader world, BlockPos pos)
	{
		return isThis(world.getBlockState(pos));
	}
	
	private boolean isThis(BlockState state) 
	{
		return state.getBlock() instanceof StalactiteBlock;
	}
	
	private boolean checkUp(BlockGetter world, BlockPos pos, int size) 
	{
		BlockPos p = pos.above();
		BlockState state = world.getBlockState(p);
		return (isThis(state) && state.getValue(SIZE) >= size) || state.isRedstoneConductor(world, p);
	}
	
	private boolean checkDown(BlockGetter world, BlockPos pos, int size) 
	{
		BlockPos p = pos.below();
		BlockState state = world.getBlockState(p);
		return (isThis(state) && state.getValue(SIZE) >= size) || state.isRedstoneConductor(world, p);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(WATERLOGGED, IS_FLOOR, SIZE);
	}

}
