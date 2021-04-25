package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class NeonCactusBlock extends Block implements IWaterLoggable {
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	private static final EnumMap<Direction, VoxelShape> MEDIUM_SHAPES_OPEN = Maps.newEnumMap(Direction.class);
	private static final EnumMap<Direction, VoxelShape> SMALL_SHAPES_OPEN = Maps.newEnumMap(Direction.class);
	private static final EnumMap<Axis, VoxelShape> MEDIUM_SHAPES = Maps.newEnumMap(Axis.class);
	private static final EnumMap<Axis, VoxelShape> SMALL_SHAPES = Maps.newEnumMap(Axis.class);

	public NeonCactusBlock() {
		super(AbstractBlock.Properties.from(Blocks.CACTUS).setLightLevel(s -> 15));
		this.setDefaultState(
				getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP).with(SHAPE, TripleShape.TOP));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(SHAPE, WATERLOGGED, FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		World worldAccess = ctx.getWorld();
		BlockPos blockPos = ctx.getPos();
		return this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER)
				.with(FACING, ctx.getFace());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return BlockHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return BlockHelper.mirrorHorizontal(state, mirror, FACING);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		if ((Boolean) state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return state;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext context) {
		TripleShape shape = state.get(SHAPE);

		if (shape == TripleShape.BOTTOM) {
			return VoxelShapes.fullCube();
		}
		Direction dir = state.get(FACING);
		BlockState next = view.getBlockState(pos.offset(dir));
		if (next.isIn(this)) {
			Axis axis = dir.getAxis();
			return shape == TripleShape.MIDDLE ? MEDIUM_SHAPES.get(axis) : SMALL_SHAPES.get(axis);
		} else {
			return shape == TripleShape.MIDDLE ? MEDIUM_SHAPES_OPEN.get(dir) : SMALL_SHAPES_OPEN.get(dir);
		}
	}

	static {
		MEDIUM_SHAPES.put(Axis.X, Block.makeCuboidShape(0, 2, 2, 16, 14, 14));
		MEDIUM_SHAPES.put(Axis.Y, Block.makeCuboidShape(2, 0, 2, 14, 16, 14));
		MEDIUM_SHAPES.put(Axis.Z, Block.makeCuboidShape(2, 2, 0, 14, 14, 16));

		SMALL_SHAPES.put(Axis.X, Block.makeCuboidShape(0, 4, 4, 16, 12, 12));
		SMALL_SHAPES.put(Axis.Y, Block.makeCuboidShape(4, 0, 4, 12, 16, 12));
		SMALL_SHAPES.put(Axis.Z, Block.makeCuboidShape(4, 4, 0, 12, 12, 16));

		MEDIUM_SHAPES_OPEN.put(Direction.UP, Block.makeCuboidShape(2, 0, 2, 14, 14, 14));
		MEDIUM_SHAPES_OPEN.put(Direction.DOWN, Block.makeCuboidShape(2, 2, 2, 14, 16, 14));
		MEDIUM_SHAPES_OPEN.put(Direction.NORTH, Block.makeCuboidShape(2, 2, 2, 14, 14, 16));
		MEDIUM_SHAPES_OPEN.put(Direction.SOUTH, Block.makeCuboidShape(2, 2, 0, 14, 14, 14));
		MEDIUM_SHAPES_OPEN.put(Direction.WEST, Block.makeCuboidShape(2, 2, 2, 16, 14, 14));
		MEDIUM_SHAPES_OPEN.put(Direction.EAST, Block.makeCuboidShape(0, 2, 2, 14, 14, 14));

		SMALL_SHAPES_OPEN.put(Direction.UP, Block.makeCuboidShape(4, 0, 4, 12, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.DOWN, Block.makeCuboidShape(4, 4, 4, 12, 16, 12));
		SMALL_SHAPES_OPEN.put(Direction.NORTH, Block.makeCuboidShape(4, 4, 4, 12, 12, 16));
		SMALL_SHAPES_OPEN.put(Direction.SOUTH, Block.makeCuboidShape(4, 4, 0, 12, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.WEST, Block.makeCuboidShape(4, 4, 4, 16, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.EAST, Block.makeCuboidShape(0, 4, 4, 12, 12, 12));
	}
}
