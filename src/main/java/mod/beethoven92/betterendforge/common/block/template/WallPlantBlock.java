package mod.beethoven92.betterendforge.common.block.template;

import java.util.EnumMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WallPlantBlock extends PlantBlock
{
	private static final EnumMap<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.box(1, 1, 8, 15, 15, 16),
			Direction.SOUTH, Block.box(1, 1, 0, 15, 15, 8),
			Direction.WEST, Block.box(8, 1, 1, 16, 15, 15),
			Direction.EAST, Block.box(0, 1, 1, 8, 15, 15)));
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	public WallPlantBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return SHAPES.get(state.getValue(FACING));
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		Direction direction = (Direction)state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		BlockState blockState = worldIn.getBlockState(blockPos);
		return isValidSupport(worldIn, blockPos, blockState, direction);
	}
	
	public boolean isValidSupport(LevelReader world, BlockPos pos, BlockState blockState, Direction direction) 
	{
		return blockState.getMaterial().isSolid() && blockState.isFaceSturdy(world, pos, direction);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		BlockState blockState = this.defaultBlockState();
		LevelReader worldView = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		Direction[] directions = context.getNearestLookingDirections();
		for (int i = 0; i < directions.length; ++i) 
		{
			Direction direction = directions[i];
			if (direction.getAxis().isHorizontal()) 
			{
				Direction direction2 = direction.getOpposite();
				blockState = blockState.setValue(FACING, direction2);
				if (blockState.canSurvive(worldView, blockPos)) 
				{
					return blockState;
				}
			}
		}
		return null;
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos))
		{
			return Blocks.AIR.defaultBlockState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}
