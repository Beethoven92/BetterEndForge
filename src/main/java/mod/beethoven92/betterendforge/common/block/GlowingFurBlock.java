package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IForgeShearable;

public class GlowingFurBlock extends Block implements IForgeShearable
{
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	static {
		BOUNDING_SHAPES.put(Direction.UP, VoxelShapes.create(0.0, 0.0, 0.0, 1.0, 0.5, 1.0));
		BOUNDING_SHAPES.put(Direction.DOWN, VoxelShapes.create(0.0, 0.5, 0.0, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.NORTH, VoxelShapes.create(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, VoxelShapes.create(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, VoxelShapes.create(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.EAST, VoxelShapes.create(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
	}
	
	public GlowingFurBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return BOUNDING_SHAPES.get(state.get(FACING));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockState blockState = this.getDefaultState();
		IWorldReader worldView = context.getWorld();
		BlockPos blockPos = context.getPos();
		Direction[] directions = context.getNearestLookingDirections();
		for (int i = 0; i < directions.length; ++i) 
		{
			Direction direction = directions[i];
			Direction direction2 = direction.getOpposite();
			blockState = (BlockState) blockState.with(FACING, direction2);
			if (blockState.isValidPosition(worldView, blockPos)) 
			{
				return blockState;
			}
		}
		return null;
	}	
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		Direction direction = (Direction) state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return hasEnoughSolidSide(worldIn, blockPos, direction);
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
    }
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
	
}
