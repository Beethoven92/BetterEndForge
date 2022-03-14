package mod.beethoven92.betterendforge.common.block;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

public class EndSignBlock extends SignBlock {
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	public static final BooleanProperty FLOOR = BooleanProperty.create("floor");
	private static final VoxelShape[] WALL_SHAPES = new VoxelShape[] {
			Block.box(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D),
			Block.box(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D),
			Block.box(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D),
			Block.box(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D) };

	public EndSignBlock(BlockBehaviour.Properties builder) {
		super(builder, WoodType.OAK);
		this.registerDefaultState(
				this.stateDefinition.any().setValue(ROTATION, 0).setValue(FLOOR, true).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FLOOR, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return state.getValue(FLOOR) ? SHAPE : WALL_SHAPES[state.getValue(ROTATION) >> 2];
	}

	@Override
	public BlockEntity newBlockEntity(BlockGetter worldIn) {
		return new ESignTileEntity();
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		boolean bl = itemStack.getItem() instanceof DyeItem && player.abilities.mayBuild;
		if (world.isClientSide) {
			return bl ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ESignTileEntity) {
				ESignTileEntity signBlockEntity = (ESignTileEntity) blockEntity;
				if (bl) {
					boolean bl2 = signBlockEntity.setTextColor(((DyeItem) itemStack.getItem()).getDyeColor());
					if (bl2 && !player.isCreative()) {
						itemStack.shrink(1);
					}
				}
				return signBlockEntity.onActivate(player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
			} else {
				return InteractionResult.PASS;
			}
		}
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (placer != null && placer instanceof Player) {
			ESignTileEntity sign = (ESignTileEntity) world.getBlockEntity(pos);
			if (!world.isClientSide) {
				sign.setEditor((Player) placer);
				((ServerPlayer) placer).connection.send(new ClientboundOpenSignEditorPacket(pos));
			} else
				sign.setEditable(true);
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState,
			LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if ((Boolean) state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		return super.updateShape(state, facing, neighborState, world, pos, neighborPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		if (ctx.getClickedFace() == Direction.UP) {
			FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
			return this.defaultBlockState().setValue(FLOOR, true)
					.setValue(ROTATION, Mth.floor((180.0 + ctx.getRotation() * 16.0 / 360.0) + 0.5 - 12) & 15)
					.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
		} else if (ctx.getClickedFace() != Direction.DOWN) {
			BlockState blockState = this.defaultBlockState();
			FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
			LevelAccessor worldView = ctx.getLevel();
			BlockPos blockPos = ctx.getClickedPos();
			Direction[] directions = ctx.getNearestLookingDirections();
			Direction[] var7 = directions;
			int var8 = directions.length;

			for (int var9 = 0; var9 < var8; ++var9) {
				Direction direction = var7[var9];
				if (direction.getAxis().isHorizontal()) {
					Direction direction2 = direction.getOpposite();
					int rot = Mth.floor((180.0 + direction2.toYRot() * 16.0 / 360.0) + 0.5 + 4) & 15;
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