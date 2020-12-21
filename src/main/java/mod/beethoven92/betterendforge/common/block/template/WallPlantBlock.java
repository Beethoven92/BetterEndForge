package mod.beethoven92.betterendforge.common.block.template;

import java.util.EnumMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class WallPlantBlock extends PlantBlock
{
	private static final EnumMap<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.makeCuboidShape(1, 1, 8, 15, 15, 16),
			Direction.SOUTH, Block.makeCuboidShape(1, 1, 0, 15, 15, 8),
			Direction.WEST, Block.makeCuboidShape(8, 1, 1, 16, 15, 15),
			Direction.EAST, Block.makeCuboidShape(0, 1, 1, 8, 15, 15)));
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	public WallPlantBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPES.get(state.get(FACING));
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		Direction direction = (Direction)state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		BlockState blockState = worldIn.getBlockState(blockPos);
		return isValidSupport(worldIn, blockPos, blockState, direction);
	}
	
	public boolean isValidSupport(IWorldReader world, BlockPos pos, BlockState blockState, Direction direction) 
	{
		return blockState.getMaterial().isSolid() && blockState.isSolidSide(world, pos, direction);
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
			if (direction.getAxis().isHorizontal()) 
			{
				Direction direction2 = direction.getOpposite();
				blockState = blockState.with(FACING, direction2);
				if (blockState.isValidPosition(worldView, blockPos)) 
				{
					return blockState;
				}
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
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
    }

	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
}
