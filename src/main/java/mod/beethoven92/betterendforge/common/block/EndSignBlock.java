package mod.beethoven92.betterendforge.common.block;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EndSignBlock extends AbstractSignBlock {
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	public static final BooleanProperty FLOOR = BooleanProperty.create("floor");
	private static final VoxelShape[] WALL_SHAPES = new VoxelShape[] {
			Block.box(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D),
			Block.box(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D),
			Block.box(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D),
			Block.box(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D) };

	public EndSignBlock(AbstractBlock.Properties builder) {
		super(builder, WoodType.OAK);
		this.registerDefaultState(
				this.stateDefinition.any().setValue(ROTATION, 0).setValue(FLOOR, true).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FLOOR, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return state.getValue(FLOOR) ? SHAPE : WALL_SHAPES[state.getValue(ROTATION) >> 2];
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new ESignTileEntity();
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand hand, BlockRayTraceResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		boolean bl = itemStack.getItem() instanceof DyeItem && player.abilities.mayBuild;
		if (world.isClientSide) {
			return bl ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
		} else {
			TileEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ESignTileEntity) {
				ESignTileEntity signBlockEntity = (ESignTileEntity) blockEntity;
				if (bl) {
					boolean bl2 = signBlockEntity.setTextColor(((DyeItem) itemStack.getItem()).getDyeColor());
					if (bl2 && !player.isCreative()) {
						itemStack.shrink(1);
					}
				}
				return signBlockEntity.onActivate(player) ? ActionResultType.SUCCESS : ActionResultType.PASS;
			} else {
				return ActionResultType.PASS;
			}
		}
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (placer != null && placer instanceof PlayerEntity) {
			ESignTileEntity sign = (ESignTileEntity) world.getBlockEntity(pos);
			if (!world.isClientSide) {
				sign.setEditor((PlayerEntity) placer);
				((ServerPlayerEntity) placer).connection.send(new SOpenSignMenuPacket(pos));
			} else
				sign.setEditable(true);
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState,
			IWorld world, BlockPos pos, BlockPos neighborPos) {
		if ((Boolean) state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		return super.updateShape(state, facing, neighborState, world, pos, neighborPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		if (ctx.getClickedFace() == Direction.UP) {
			FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
			return this.defaultBlockState().setValue(FLOOR, true)
					.setValue(ROTATION, MathHelper.floor((180.0 + ctx.getRotation() * 16.0 / 360.0) + 0.5 - 12) & 15)
					.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
		} else if (ctx.getClickedFace() != Direction.DOWN) {
			BlockState blockState = this.defaultBlockState();
			FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
			IWorld worldView = ctx.getLevel();
			BlockPos blockPos = ctx.getClickedPos();
			Direction[] directions = ctx.getNearestLookingDirections();
			Direction[] var7 = directions;
			int var8 = directions.length;

			for (int var9 = 0; var9 < var8; ++var9) {
				Direction direction = var7[var9];
				if (direction.getAxis().isHorizontal()) {
					Direction direction2 = direction.getOpposite();
					int rot = MathHelper.floor((180.0 + direction2.toYRot() * 16.0 / 360.0) + 0.5 + 4) & 15;
					blockState = blockState.setValue(ROTATION, rot);
					if (blockState.canSurvive(worldView, blockPos)) {
						return blockState.setValue(FLOOR, false).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
					}
				}
			}
		}

		return null;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return (BlockState) state.setValue(ROTATION, rotation.rotate((Integer) state.getValue(ROTATION), 16));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return (BlockState) state.setValue(ROTATION, mirror.mirror((Integer) state.getValue(ROTATION), 16));
	}
}