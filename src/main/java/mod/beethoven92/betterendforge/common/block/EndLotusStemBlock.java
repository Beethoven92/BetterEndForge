package mod.beethoven92.betterendforge.common.block;

import java.util.Map;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class EndLotusStemBlock extends Block implements IWaterLoggable
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LEAF = BooleanProperty.create("leaf");
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final Map<Axis, VoxelShape> SHAPES = Maps.newEnumMap(Axis.class);
	
	static 
	{
		SHAPES.put(Axis.X, Block.box(0, 6, 6, 16, 10, 10));
		SHAPES.put(Axis.Y, Block.box(6, 0, 6, 10, 16, 10));
		SHAPES.put(Axis.Z, Block.box(6, 6, 0, 10, 10, 16));
	}
	
	public EndLotusStemBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(SHAPE, TripleShape.MIDDLE).
				setValue(LEAF, false).setValue(FACING, Direction.UP));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING, LEAF, WATERLOGGED, SHAPE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return state.getValue(LEAF) ? SHAPES.get(Axis.Y) : SHAPES.get(state.getValue(FACING).getAxis());
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER).
				setValue(FACING, context.getClickedFace());
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (stateIn.getValue(WATERLOGGED)) 
		{
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
	    }
		
		return stateIn;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}
