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
		super(AbstractBlock.Properties.from(Blocks.CACTUS).setLightLevel(s -> 15).tickRandomly());
		setDefaultState(
				getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP).with(SHAPE, TripleShape.TOP));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE, CACTUS_BOTTOM, WATERLOGGED, FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		Direction dir = ctx.getFace();
		BlockState down = world.getBlockState(pos.offset(dir.getOpposite()));
		BlockState state = this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
				.with(FACING, ctx.getFace());
		if (down.isIn(Blocks.END_STONE) || down.isIn(ModBlocks.ENDSTONE_DUST.get())) {
			state = state.with(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (down.isIn(ModBlocks.END_MOSS.get())) {
			state = state.with(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.with(CACTUS_BOTTOM, CactusBottom.EMPTY);
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
		return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		world.getPendingBlockTicks().scheduleTick(pos, this, 2);
		if ((Boolean) state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		Direction dir = state.get(FACING);
		BlockState downState = world.getBlockState(pos.offset(dir.getOpposite()));
		if (downState.isIn(Blocks.END_STONE) || downState.isIn(ModBlocks.ENDSTONE_DUST.get())) {
			state = state.with(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (downState.isIn(ModBlocks.END_MOSS.get())) {
			state = state.with(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.with(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		return state;
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!state.isValidPosition(worldIn, pos)) {
			worldIn.destroyBlock(pos, true, null, 1);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext context) {
		TripleShape shape = state.get(SHAPE);
		Direction dir = state.get(FACING);
		BlockState next = view.getBlockState(pos.offset(dir));
		if (next.isIn(this)) {
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
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction dir = state.get(FACING);
		BlockPos supportPos = pos.offset(dir.getOpposite());
		BlockState support = worldIn.getBlockState(supportPos);
		return support.isIn(this) || support.isSolidSide(worldIn, supportPos, dir);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!this.isValidPosition(state, world, pos) || random.nextInt(8) > 0) {
			return;
		}
		Direction dir = state.get(FACING);
		if (!world.isAirBlock(pos.offset(dir))) {
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
				if (!world.getBlockState(pos.up()).isAir()) {
					return;
				}
			}
		} else if (length > 1 && world.getBlockState(pos.offset(dir.getOpposite())).isIn(this)) {
			Direction side = getSideDirection(world, pos, state, dir, random);
			BlockPos sidePos = pos.offset(side);
			if (world.isAirBlock(sidePos)) {
				BlockState placement = state.with(SHAPE, TripleShape.TOP).with(CACTUS_BOTTOM, CactusBottom.EMPTY)
						.with(WATERLOGGED, false).with(FACING, side);
				BlockHelper.setWithoutUpdate(world, sidePos, placement);
			}
		}
		BlockState placement = state.with(SHAPE, TripleShape.TOP).with(CACTUS_BOTTOM, CactusBottom.EMPTY)
				.with(WATERLOGGED, false).with(FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos.offset(dir), placement);
		mutateStem(placement, world, pos, MAX_LENGTH);
	}

	public void growPlant(ISeedReader world, BlockPos pos, Random random) {
		growPlant(world, pos, random, ModMathHelper.randRange(MAX_LENGTH >> 1, MAX_LENGTH, random));
	}

	public void growPlant(ISeedReader world, BlockPos pos, Random random, int iterations) {
		BlockState state = getDefaultState();
		BlockState downState = world.getBlockState(pos.down());
		if (downState.isIn(Blocks.END_STONE) || downState.isIn(ModBlocks.ENDSTONE_DUST.get())) {
			state = state.with(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (downState.isIn(ModBlocks.END_MOSS.get())) {
			state = state.with(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.with(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
		List<Mutable> ends = Lists.newArrayList(pos.toMutable());
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
		if (!state.isIn(this)) {
			return false;
		}
		Direction dir = state.get(FACING);
		if (!world.isAirBlock(pos.offset(dir))) {
			return false;
		}
		if (dir.getAxis().isHorizontal()) {
			int horizontal = getHorizontal(state, world, pos, 2);
			if (horizontal > random.nextInt(2)) {
				dir = Direction.UP;
				if (!world.getBlockState(pos.up()).isAir()) {
					return false;
				}
			}
		} else if (length > 1 && world.getBlockState(pos.down()).isIn(this)) {
			Direction side = getSideDirection(world, pos, state, dir, random);
			BlockPos sidePos = pos.offset(side);
			if (world.isAirBlock(sidePos)) {
				BlockState placement = state.with(SHAPE, TripleShape.TOP).with(CACTUS_BOTTOM, CactusBottom.EMPTY)
						.with(WATERLOGGED, false).with(FACING, side);
				BlockHelper.setWithoutUpdate(world, sidePos, placement);
				ends.add(sidePos.toMutable());
			}
		}
		BlockState placement = state.with(SHAPE, TripleShape.TOP).with(CACTUS_BOTTOM, CactusBottom.EMPTY)
				.with(WATERLOGGED, false).with(FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos.offset(dir), placement);
		mutateStem(placement, world, pos, MAX_LENGTH);
		pos.move(dir);
		return true;
	}

	private Direction getSideDirection(ISeedReader world, BlockPos pos, BlockState iterState, Direction dir,
			Random random) {
		Mutable iterPos = pos.toMutable();
		Direction startDir = dir;
		Direction lastDir = null;
		while (iterState.isIn(this) && startDir.getAxis().isVertical()) {
			startDir = iterState.get(FACING);
			if (lastDir == null) {
				for (Direction side : BlockHelper.HORIZONTAL_DIRECTIONS) {
					BlockState sideState = world.getBlockState(iterPos.offset(side));
					if (sideState.isIn(this)) {
						Direction sideDir = sideState.get(FACING);
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

		Direction side = lastDir == null ? BlockHelper.randomHorizontal(random) : lastDir.rotateY();
		if (side.getOpposite() == startDir) {
			side = side.getOpposite();
		}
		return side;
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}

	private int getLength(BlockState state, ServerWorld world, BlockPos pos, int max) {
		int length = 0;
		Direction dir = state.get(FACING).getOpposite();
		Mutable mut = new Mutable().setPos(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (!state.isIn(this)) {
				if (!state.isIn(ModTags.END_GROUND)) {
					length = -1;
				}
				break;
			}
			dir = state.get(FACING).getOpposite();
			length++;
		}
		return length;
	}

	private int getHorizontal(BlockState state, ISeedReader world, BlockPos pos, int max) {
		int count = 0;
		Direction dir = state.get(FACING).getOpposite();
		Mutable mut = new Mutable().setPos(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (!state.isIn(this)) {
				break;
			}
			dir = state.get(FACING).getOpposite();
			if (dir.getYOffset() != 0) {
				break;
			}
			count++;
		}
		return count;
	}

	private void mutateStem(BlockState state, ISeedReader world, BlockPos pos, int max) {
		Direction dir = state.get(FACING).getOpposite();
		Mutable mut = new Mutable().setPos(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (!state.isIn(this)) {
				return;
			}
			int size = (i + 2) * 3 / max;
			int src = state.get(SHAPE).getIndex();
			dir = state.get(FACING).getOpposite();
			if (src < size) {
				TripleShape shape = TripleShape.fromIndex(size);
				BlockHelper.setWithoutUpdate(world, mut, state.with(SHAPE, shape));
			}
		}
	}

	static {
		BIG_SHAPES.put(Axis.X, Block.makeCuboidShape(0, 2, 2, 16, 14, 14));
		BIG_SHAPES.put(Axis.Y, Block.makeCuboidShape(2, 0, 2, 14, 16, 14));
		BIG_SHAPES.put(Axis.Z, Block.makeCuboidShape(2, 2, 0, 14, 14, 16));

		MEDIUM_SHAPES.put(Axis.X, Block.makeCuboidShape(0, 3, 3, 16, 13, 13));
		MEDIUM_SHAPES.put(Axis.Y, Block.makeCuboidShape(3, 0, 3, 13, 16, 13));
		MEDIUM_SHAPES.put(Axis.Z, Block.makeCuboidShape(3, 3, 0, 13, 13, 16));

		SMALL_SHAPES.put(Axis.X, Block.makeCuboidShape(0, 4, 4, 16, 12, 12));
		SMALL_SHAPES.put(Axis.Y, Block.makeCuboidShape(4, 0, 4, 12, 16, 12));
		SMALL_SHAPES.put(Axis.Z, Block.makeCuboidShape(4, 4, 0, 12, 12, 16));

		BIG_SHAPES_OPEN.put(Direction.UP, Block.makeCuboidShape(2, 0, 2, 14, 14, 14));
		BIG_SHAPES_OPEN.put(Direction.DOWN, Block.makeCuboidShape(2, 2, 2, 14, 16, 14));
		BIG_SHAPES_OPEN.put(Direction.NORTH, Block.makeCuboidShape(2, 2, 2, 14, 14, 16));
		BIG_SHAPES_OPEN.put(Direction.SOUTH, Block.makeCuboidShape(2, 2, 0, 14, 14, 14));
		BIG_SHAPES_OPEN.put(Direction.WEST, Block.makeCuboidShape(2, 2, 2, 16, 14, 14));
		BIG_SHAPES_OPEN.put(Direction.EAST, Block.makeCuboidShape(0, 2, 2, 14, 14, 14));

		MEDIUM_SHAPES_OPEN.put(Direction.UP, Block.makeCuboidShape(3, 0, 3, 13, 13, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.DOWN, Block.makeCuboidShape(3, 3, 3, 13, 16, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.NORTH, Block.makeCuboidShape(3, 3, 3, 13, 13, 16));
		MEDIUM_SHAPES_OPEN.put(Direction.SOUTH, Block.makeCuboidShape(3, 3, 0, 13, 13, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.WEST, Block.makeCuboidShape(3, 3, 3, 16, 13, 13));
		MEDIUM_SHAPES_OPEN.put(Direction.EAST, Block.makeCuboidShape(0, 3, 3, 13, 13, 13));

		SMALL_SHAPES_OPEN.put(Direction.UP, Block.makeCuboidShape(4, 0, 4, 12, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.DOWN, Block.makeCuboidShape(4, 4, 4, 12, 16, 12));
		SMALL_SHAPES_OPEN.put(Direction.NORTH, Block.makeCuboidShape(4, 4, 4, 12, 12, 16));
		SMALL_SHAPES_OPEN.put(Direction.SOUTH, Block.makeCuboidShape(4, 4, 0, 12, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.WEST, Block.makeCuboidShape(4, 4, 4, 16, 12, 12));
		SMALL_SHAPES_OPEN.put(Direction.EAST, Block.makeCuboidShape(0, 4, 4, 12, 12, 12));
	}
}
