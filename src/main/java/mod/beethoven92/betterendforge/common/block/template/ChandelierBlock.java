package mod.beethoven92.betterendforge.common.block.template;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class ChandelierBlock extends AttachedBlock
{
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	
	static
	{
		BOUNDING_SHAPES.put(Direction.UP, Block.box(5, 0, 5, 11, 13, 11));
		BOUNDING_SHAPES.put(Direction.DOWN, Block.box(5, 3, 5, 11, 16, 11));
		BOUNDING_SHAPES.put(Direction.NORTH, VoxelShapes.box(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, VoxelShapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, VoxelShapes.box(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.EAST, VoxelShapes.box(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
	}
	
	public ChandelierBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) 
	{
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}
}
