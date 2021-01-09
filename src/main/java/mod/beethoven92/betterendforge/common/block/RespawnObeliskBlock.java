package mod.beethoven92.betterendforge.common.block;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.particles.InfusionParticleData;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RespawnObeliskBlock extends Block {
	private static final VoxelShape VOXEL_SHAPE_BOTTOM = Block.makeCuboidShape(1, 0, 1, 15, 16, 15);
	private static final VoxelShape VOXEL_SHAPE_MIDDLE_TOP = Block.makeCuboidShape(2, 0, 2, 14, 16, 14);

	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

	public RespawnObeliskBlock(Properties properties) {
		super(properties);
	}
	
	public static int getBlockColor(BlockPos pos) {
		return AuroraCrystalBlock.getBlockColor(pos);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return (state.get(SHAPE) == TripleShape.BOTTOM) ? VOXEL_SHAPE_BOTTOM : VOXEL_SHAPE_MIDDLE_TOP;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		for (int i = 0; i < 3; i++) {
			if (!world.getBlockState(pos.up(i)).getMaterial().isReplaceable()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
			ItemStack stack) {
		state = this.getDefaultState();
		BlockHelper.setWithUpdate(world, pos, state.with(SHAPE, TripleShape.BOTTOM));
		BlockHelper.setWithUpdate(world, pos.up(), state.with(SHAPE, TripleShape.MIDDLE));
		BlockHelper.setWithUpdate(world, pos.up(2), state.with(SHAPE, TripleShape.TOP));
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		TripleShape shape = state.get(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			if (world.getBlockState(pos.up()).isIn(this)) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		} else if (shape == TripleShape.MIDDLE) {
			if (world.getBlockState(pos.up()).isIn(this) && world.getBlockState(pos.down()).isIn(this)) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		} else {
			if (world.getBlockState(pos.down()).isIn(this)) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (player.isCreative()) {
			TripleShape shape = state.get(SHAPE);
			if (shape == TripleShape.MIDDLE) {
				BlockHelper.setWithUpdate(world, pos.down(), Blocks.AIR);
			} else if (shape == TripleShape.TOP) {
				BlockHelper.setWithUpdate(world, pos.down(2), Blocks.AIR);
			}
		}
		super.onBlockHarvested(world, pos, state, player);
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
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand hand, BlockRayTraceResult hit) {
		ItemStack itemStack = player.getHeldItem(hand);
		boolean canActivate = itemStack.getItem() == ModItems.AMBER_GEM.get() && itemStack.getCount() > 5;
		if (hand != Hand.MAIN_HAND || !canActivate) {
			if (!world.isRemote && !(itemStack.getItem() instanceof BlockItem) && !player.isCreative()) {
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
				serverPlayerEntity.sendStatusMessage(new TranslationTextComponent("message.betterendforge.fail_spawn"), true);
			}
			return ActionResultType.FAIL;
		} else if (!world.isRemote) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
			serverPlayerEntity.func_242111_a(world.getDimensionKey(), pos, 0.0F, false, false);
			serverPlayerEntity.sendStatusMessage(new TranslationTextComponent("message.betterendforge.set_spawn"), true);
			double px = pos.getX() + 0.5;
			double py = pos.getY() + 0.5;
			double pz = pos.getZ() + 0.5;
			InfusionParticleData particle = new InfusionParticleData(new ItemStack(ModItems.AMBER_GEM.get()));
			if (world instanceof ServerWorld) {
				double py1 = py;
				double py2 = py - 0.2;
				if (state.get(SHAPE) == TripleShape.BOTTOM) {
					py1 += 1;
					py2 += 2;
				} else if (state.get(SHAPE) == TripleShape.MIDDLE) {
					py1 += 0;
					py2 += 1;
				} else {
					py1 -= 2;
				}
				((ServerWorld) world).spawnParticle(particle, px, py1, pz, 20, 0.14, 0.5, 0.14, 0.1);
				((ServerWorld) world).spawnParticle(particle, px, py2, pz, 20, 0.14, 0.3, 0.14, 0.1);
			}
			world.playSound(null, px, py, py, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1F, 1F);
			if (!player.isCreative()) {
				itemStack.shrink(6);
			}
		}
		return player.isCreative() ? ActionResultType.PASS : ActionResultType.func_233537_a_(world.isRemote);
	}
}
