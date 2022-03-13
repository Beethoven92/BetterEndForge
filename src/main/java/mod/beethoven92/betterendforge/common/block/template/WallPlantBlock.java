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

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class WallPlantBlock extends PlantBlock
{
	private static final EnumMap<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.box(1, 1, 8, 15, 15, 16),
			Direction.SOUTH, Block.box(1, 1, 0, 15, 15, 8),
			Direction.WEST, Block.box(8, 1, 1, 16, 15, 15),
			Direction.EAST, Block.box(0, 1, 1, 8, 15, 15)));
	
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	
	public WallPlantBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPES.get(state.getValue(FACING));
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		Direction direction = (Direction)state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		BlockState blockState = worldIn.getBlockState(blockPos);
		return isValidSupport(worldIn, blockPos, blockState, direction);
	}
	
	public boolean isValidSupport(IWorldReader world, BlockPos pos, BlockState blockState, Direction direction) 
	{
		return blockState.getMaterial().isSolid() && blockState.isFaceSturdy(world, pos, direction);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockState blockState = this.defaultBlockState();
		IWorldReader worldView = context.getLevel();
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
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
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
