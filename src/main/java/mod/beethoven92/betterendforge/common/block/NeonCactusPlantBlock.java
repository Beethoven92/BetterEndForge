package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.BlockProperties.CactusBottom;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NeonCactusPlantBlock extends Block implements IWaterLoggable {
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	public static final EnumProperty<CactusBottom> CACTUS_BOTTOM = BlockProperties.CACTUS_BOTTOM;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	private static final EnumMap<Direction, VoxelShape> BIG_SHAPES_OPEN = Maps.newEnumMap(Direction.class);
	private static final EnumMap<Direction, VoxelShape> MEDIUM_SHAPES_OPEN = Maps.newEnumMap(Direction.class);
	private static final EnumMap<Direction, VoxelShape> SMALL_SHAPES_OPEN = Maps.newEnumMap(Direction.class);
	private static final EnumMap<Axis, VoxelShape> BIG_SHAPES = Maps.newEnumMap(Axis.class);
	private static final EnumMap<Axis, VoxelShape> MEDIUM_SHAPES = Maps.newEnumMap(Axis.class);
	private static final EnumMap<Axis, VoxelShape> SMALL_SHAPES = Maps.newEnumMap(Axis.class);
	private static final int MAX_LENGTH = 12;

	public NeonCactusPlantBlock() {
		super(AbstractBlock.Properties.copy(Blocks.CACTUS).lightLevel(s -> 15).randomTicks());
		registerDefaultState(
				defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP).setValue(SHAPE, TripleShape.TOP));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE, CACTUS_BOTTOM, WATERLOGGED, FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		World world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Direction dir = ctx.getClickedFace();
		BlockState down = world.getBlockState(pos.relative(dir.getOpposite()));
		BlockState state = this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
				.setValue(FACING, ctx.getClickedFace());
		if (down.is(Blocks.END_STONE) || down.is(ModBlocks.ENDSTONE_DUST.get())) {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (down.is(ModBlocks.END_MOSS.get())) {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		return state;
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
		return (Boolean) state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		world.getBlockTicks().scheduleTick(pos, this, 2);
		if ((Boolean) state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		Direction dir = state.getValue(FACING);
		BlockState downState = world.getBlockState(pos.relative(dir.getOpposite()));
		if (downState.is(Blocks.END_STONE) || downState.is(ModBlocks.ENDSTONE_DUST.get())) {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (downState.is(ModBlocks.END_MOSS.get())) {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		return state;
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!state.canSurvive(worldIn, pos)) {
			worldIn.destroyBlock(pos, true, null, 1);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext context) {
		TripleShape shape = state.getValue(SHAPE);
		Direction dir = state.getValue(FACING);
		BlockState next = view.getBlockState(pos.relative(dir));
		if (next.is(this)) {
			Axis axis = dir.getAxis();
			if (shape == TripleShape.BOTTOM) {
				return BIG_SHAPES.get(axis);
			}
			return shape == TripleShape.MIDDLE ? MEDIUM_SHAPES.get(axis) : SMALL_SHAPES.get(axis);
		} else {
			if (shape == TripleShape.BOTTOM) {
				return BIG_SHAPES_OPEN.get(dir);
			}
			return shape == TripleShape.MIDDLE ? MEDIUM_SHAPES_OPEN.get(dir) : SMALL_SHAPES_OPEN.get(dir);
		}
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction dir = state.getValue(FACING);
		BlockPos supportPos = pos.relative(dir.getOpposite());
		BlockState support = worldIn.getBlockState(supportPos);
		return support.is(this) || support.isFaceSturdy(worldIn, supportPos, dir);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!this.canSurvive(state, world, pos) || random.nextInt(8) > 0) {
			return;
		}
		Direction dir = state.getValue(FACING);
		if (!world.isEmptyBlock(pos.relative(dir))) {
			return;
		}
		int length = getLength(state, world, pos, MAX_LENGTH);
		if (length < 0 || length > MAX_LENGTH - 1) {
			return;
		}
		if (dir.getAxis().isHorizontal()) {
			int horizontal = getHorizontal(state, world, pos, 2);
			if (horizontal > random.nextInt(2)) {
				dir = Direction.UP;
				if (!world.getBlockState(pos.above()).isAir()) {
					return;
				}
			}
		} else if (length > 1 && world.getBlockState(pos.relative(dir.getOpposite())).is(this)) {
			Direction side = getSideDirection(world, pos, state, dir, random);
			BlockPos sidePos = pos.relative(side);
			if (world.isEmptyBlock(sidePos)) {
				BlockState placement = state.setValue(SHAPE, TripleShape.TOP).setValue(CACTUS_BOTTOM, CactusBottom.EMPTY)
						.setValue(WATERLOGGED, false).setValue(FACING, side);
				BlockHelper.setWithoutUpdate(world, sidePos, placement);
			}
		}
		BlockState placement = state.setValue(SHAPE, TripleShape.TOP).setValue(CACTUS_BOTTOM, CactusBottom.EMPTY)
				.setValue(WATERLOGGED, false).setValue(FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos.relative(dir), placement);
		mutateStem(placement, world, pos, MAX_LENGTH);
	}

	public void growPlant(ISeedReader world, BlockPos pos, Random random) {
		growPlant(world, pos, random, ModMathHelper.randRange(MAX_LENGTH >> 1, MAX_LENGTH, random));
	}

	public void growPlant(ISeedReader world, BlockPos pos, Random random, int iterations) {
		BlockState state = defaultBlockState();
		BlockState downState = world.getBlockState(pos.below());
		if (downState.is(Blocks.END_STONE) || downState.is(ModBlocks.ENDSTONE_DUST.get())) {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (downState.is(ModBlocks.END_MOSS.get())) {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.setValue(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
		List<Mutable> ends = Lists.newArrayList(pos.mutable());
		for (int i = 0; i < iterations; i++) {
			int count = ends.size();
			for (int n = 0; n < count; n++) {
				if (!growIteration(world, ends.get(n), random, ends, i)) {
					ends.remove(n);
					count--;
					n--;
				}
			}
		}
	}

	private boolean growIteration(ISeedReader world, Mutable pos, Random random, List<Mutable> ends, int length) {
		BlockState state = world.getBlockState(pos);
		if (!state.is(this)) {
			return false;
		}
		Direction dir = state.getValue(FACING);
		if (!world.isEmptyBlock(pos.relative(dir))) {
			return false;
		}
		if (dir.getAxis().isHorizontal()) {
			int horizontal = getHorizontal(state, world, pos, 2);
			if (horizontal > random.nextInt(2)) {
				dir = Direction.UP;
				if (!world.getBlockState(pos.above()).isAir()) {
					return false;
				}
			}
		} else if (length > 1 && world.getBlockState(pos.below()).is(this)) {
			Direction side = getSideDirection(world, pos, state, dir, random);
			BlockPos sidePos = pos.relative(side);
			if (world.isEmptyBlock(sidePos)) {
				BlockState placement = state.setValue(SHAPE, TripleShape.TOP).setValue(CACTUS_BOTTOM, CactusBottom.EMPTY)
						.setValue(WATERLOGGED, false).setValue(FACING, side);
				BlockHelper.setWithoutUpdate(world, sidePos, placement);
				ends.add(sidePos.mutable());
			}
		}
		BlockState placement = state.setValue(SHAPE, TripleShape.TOP).setValue(CACTUS_BOTTOM, CactusBottom.EMPTY)
				.setValue(WATERLOGGED, false).setValue(FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos.relative(dir), placement);
		mutateStem(placement, world, pos, MAX_LENGTH);
		pos.move(dir);
		return true;
	}

	private Direction getSideDirection(ISeedReader world, BlockPos pos, BlockState iterState, Direction dir,
			Random random) {
		Mutable iterPos = pos.mutable();
		Direction startDir = dir;
		Direction lastDir = null;
		while (iterState.is(this) && startDir.getAxis().isVertical()) {
			startDir = iterState.getValue(FACING);
			if (lastDir == null) {
				for (Direction side : BlockHelper.HORIZONTAL_DIRECTIONS) {
					BlockState sideState = world.getBlockState(iterPos.relative(side));
					if (sideState.is(this)) {
						Direction sideDir = sideState.getValue(FACING);
						if (sideDir != side) {
							continue;
						}
						lastDir = sideDir;
					}
				}
			}
			iterPos.move(dir);
			iterState = world.getBlockState(iterPos);
		}

		Direction side = lastDir == null ? BlockHelper.randomHorizontal(random) : lastDir.getClockWise();
		if (side.getOpposite() == startDir) {
			side = side.getOpposite();
		}
		return side;
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.hurt(DamageSource.CACTUS, 1.0F);
	}

	private int getLength(BlockState state, ServerWorld world, BlockPos pos, int max) {
		int length = 0;
		Direction dir = state.getValue(FACING).getOpposite();
		Mutable mut = new Mutable().set(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (!state.is(this)) {
				if (!state.is(ModTags.END_GROUND)) {
					length = -1;
				}
				break;
			}
			dir = state.getValue(FACING).getOpposite();
			length++;
		}
		return length;
	}

	private int getHorizontal(BlockState state, ISeedReader world, BlockPos pos, int max) {
		int count = 0;
		Direction dir = state.getValue(FACING).getOpposite();
		Mutable mut = new Mutable().set(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (!state.is(this)) {
				break;
			}
			dir = state.getValue(FACING).getOpposite();
			if (dir.getStepY() != 0) {
				break;
			}
			count++;
		}
		return count;
	}

	private void mutateStem(BlockState state, ISeedReader world, BlockPos pos, int max) {
		Direction dir = state.getValue(FACING).getOpposite();
		Mutable mut = new Mutable().set(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (!state.is(this)) {
				return;
			}
			int size = (i + 2) * 3 / max;
			int src = state.getValue(SHAPE).getIndex();
			dir = state.getValue(FACING).getOpposite();
			if (src < size) {
				TripleShape shape = TripleShape.fromIndex(size);
				BlockHelper.setWithoutUpdate(world, mut, state.setValue(SHAPE, shape));
			}
		}
	}

	static {
		BIG_SHAPES.put(Axis.X, Block.box(0, 2, 2, 16, 14, 14));
		BIG_SHAPES.put(Axis.Y, Block.box(2, 0, 2, 14, 16, 14));
		BIG_SHAPES.put(Axis.Z, Block.box(2, 2, 0, 14, 14, 16));

		MEDIUM_SHAPES.put(Axis.X, Block.box(0, 3, 3, 16, 13, 13));
		MEDIUM_SHAPES.put(Axis.Y, Block.box(3, 0, 3, 13, 16, 13));
		MEDIUM_SHAPES.put(Axis.Z, Block.box(3, 3, 0, 13, 13, 16));

		SMALL_SHAPES.put(Axis.X, Block.box(0, 4, 4, 16, 12, 12));
		SMALL_SHAPES.put(Axis.Y, Block.box(4, 0, 4, 12, 16, 12));
		SMALL_SHAPES.put(Axis.Z, Block.box(4, 4, 0, 12, 12, 16));

		BIG_SHAPES_OPEN.put(Direction.UP, Block.box(2, 0, 2, 14, 14, 14));
		BIG_SHAPES_OPEN.put(Direction.DOWN, Block.box(2, 2, 2, 14, 16, 14));
		BIG_SHAPES_OPEN.put(Direction.NORTH, Block.box(2, 2, 2, 14, 14, 16));
		BIG_SHAPES_OPEN.put(Direction.SOUTH, Block.box(2, 2, 0, 14, 14, 14));
		BIG_SHAPES_OPEN.put(Direction.WEST, Block.box(2, 2, 2, 16, 14, 14));
		BIG_SHAPES_OPEN.put(Direction.EAST, Block.box(0, 2, 2, 14, 14, 14));

		MEDIUM_SHAPES_OPEN.put(Direction.UP, Block.box(3, 0, 3, 13, 13, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.DOWN, Block.box(3, 3, 3, 13, 16, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.NORTH, Block.box(3, 3, 3, 13, 13, 16));
		MEDIUM_SHAPES_OPEN.put(Direction.SOUTH, Block.box(3, 3, 0, 13, 13, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.WEST, Block.box(3, 3, 3, 16, 13, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.EAST, Block.box(0, 3, 3, 13, 13, 13));

		SMALL_SHAPES_OPEN.put(Direction.UP, Block.box(4, 0, 4, 12, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.DOWN, Block.box(4, 4, 4, 12, 16, 12));
		SMALL_SHAPES_OPEN.put(Direction.NORTH, Block.box(4, 4, 4, 12, 12, 16));
		SMALL_SHAPES_OPEN.put(Direction.SOUTH, Block.box(4, 4, 0, 12, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.WEST, Block.box(4, 4, 4, 16, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.EAST, Block.box(0, 4, 4, 12, 12, 12));
	}
}
