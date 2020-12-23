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

public class EndLotusStemBlock extends Block implements IWaterLoggable
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LEAF = BooleanProperty.create("leaf");
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final Map<Axis, VoxelShape> SHAPES = Maps.newEnumMap(Axis.class);
	
	static 
	{
		SHAPES.put(Axis.X, Block.makeCuboidShape(0, 6, 6, 16, 10, 10));
		SHAPES.put(Axis.Y, Block.makeCuboidShape(6, 0, 6, 10, 16, 10));
		SHAPES.put(Axis.Z, Block.makeCuboidShape(6, 6, 0, 10, 10, 16));
	}
	
	public EndLotusStemBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(getDefaultState().with(WATERLOGGED, false).with(SHAPE, TripleShape.MIDDLE).
				with(LEAF, false).with(FACING, Direction.UP));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING, LEAF, WATERLOGGED, SHAPE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return state.get(LEAF) ? SHAPES.get(Axis.Y) : SHAPES.get(state.get(FACING).getAxis());
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		return this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER).
				with(FACING, context.getFace());
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (stateIn.get(WATERLOGGED)) 
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
	    }
		
		return stateIn;
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
