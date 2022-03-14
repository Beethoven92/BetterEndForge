package mod.beethoven92.betterendforge.common.block;

import java.util.Map;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndLotusStemBlock extends Block implements SimpleWaterloggedBlock
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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return state.getValue(LEAF) ? SHAPES.get(Axis.Y) : SHAPES.get(state.getValue(FACING).getAxis());
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		Level world = context.getLevel();
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
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
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
