package mod.beethoven92.betterendforge.common.block;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.particles.InfusionParticleData;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RespawnObeliskBlock extends Block {
	private static final VoxelShape VOXEL_SHAPE_BOTTOM = Block.box(1, 0, 1, 15, 16, 15);
	private static final VoxelShape VOXEL_SHAPE_MIDDLE_TOP = Block.box(2, 0, 2, 14, 16, 14);

	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

	public RespawnObeliskBlock(Properties properties) {
		super(properties);
	}
	
	public static int getBlockColor(BlockPos pos) {
		return AuroraCrystalBlock.getBlockColor(pos);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return (state.getValue(SHAPE) == TripleShape.BOTTOM) ? VOXEL_SHAPE_BOTTOM : VOXEL_SHAPE_MIDDLE_TOP;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		for (int i = 0; i < 3; i++) {
			if (!world.getBlockState(pos.above(i)).getMaterial().isReplaceable()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
			ItemStack stack) {
		state = this.defaultBlockState();
		BlockHelper.setWithUpdate(world, pos, state.setValue(SHAPE, TripleShape.BOTTOM));
		BlockHelper.setWithUpdate(world, pos.above(), state.setValue(SHAPE, TripleShape.MIDDLE));
		BlockHelper.setWithUpdate(world, pos.above(2), state.setValue(SHAPE, TripleShape.TOP));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world,
			BlockPos pos, BlockPos facingPos) {
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			if (world.getBlockState(pos.above()).is(this)) {
				return state;
			} else {
				return Blocks.AIR.defaultBlockState();
			}
		} else if (shape == TripleShape.MIDDLE) {
			if (world.getBlockState(pos.above()).is(this) && world.getBlockState(pos.below()).is(this)) {
				return state;
			} else {
				return Blocks.AIR.defaultBlockState();
			}
		} else {
			if (world.getBlockState(pos.below()).is(this)) {
				return state;
			} else {
				return Blocks.AIR.defaultBlockState();
			}
		}
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		if (player.isCreative()) {
			TripleShape shape = state.getValue(SHAPE);
			if (shape == TripleShape.MIDDLE) {
				BlockHelper.setWithUpdate(world, pos.below(), Blocks.AIR);
			} else if (shape == TripleShape.TOP) {
				BlockHelper.setWithUpdate(world, pos.below(2), Blocks.AIR);
			}
		}
		super.playerWillDestroy(world, pos, state, player);
	}

	/*@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (state.get(SHAPE) == TripleShape.BOTTOM) {
			return Lists.newArrayList(new ItemStack(this));
		} else {
			return Lists.newArrayList();
		}
	}*/

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		boolean canActivate = itemStack.getItem() == ModItems.AMBER_GEM.get() && itemStack.getCount() > 5;
		if (hand != InteractionHand.MAIN_HAND || !canActivate) {
			if (!world.isClientSide && !(itemStack.getItem() instanceof BlockItem) && !player.isCreative()) {
				ServerPlayer serverPlayerEntity = (ServerPlayer) player;
				serverPlayerEntity.displayClientMessage(new TranslatableComponent("message.betterendforge.fail_spawn"), true);
			}
			return InteractionResult.FAIL;
		} else if (!world.isClientSide) {
			ServerPlayer serverPlayerEntity = (ServerPlayer) player;
			serverPlayerEntity.setRespawnPosition(world.dimension(), pos, 0.0F, false, false);
			serverPlayerEntity.displayClientMessage(new TranslatableComponent("message.betterendforge.set_spawn"), true);
			double px = pos.getX() + 0.5;
			double py = pos.getY() + 0.5;
			double pz = pos.getZ() + 0.5;
			InfusionParticleData particle = new InfusionParticleData(new ItemStack(ModItems.AMBER_GEM.get()));
			if (world instanceof ServerLevel) {
				double py1 = py;
				double py2 = py - 0.2;
				if (state.getValue(SHAPE) == TripleShape.BOTTOM) {
					py1 += 1;
					py2 += 2;
				} else if (state.getValue(SHAPE) == TripleShape.MIDDLE) {
					py1 += 0;
					py2 += 1;
				} else {
					py1 -= 2;
				}
				((ServerLevel) world).sendParticles(particle, px, py1, pz, 20, 0.14, 0.5, 0.14, 0.1);
				((ServerLevel) world).sendParticles(particle, px, py2, pz, 20, 0.14, 0.3, 0.14, 0.1);
			}
			world.playSound(null, px, py, py, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1F, 1F);
			if (!player.isCreative()) {
				itemStack.shrink(6);
			}
		}
		return player.isCreative() ? InteractionResult.PASS : InteractionResult.sidedSuccess(world.isClientSide);
	}
}
