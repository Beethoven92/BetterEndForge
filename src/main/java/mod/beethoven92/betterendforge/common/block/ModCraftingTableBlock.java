package mod.beethoven92.betterendforge.common.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModCraftingTableBlock extends CraftingTableBlock {
	private static final Component CONTAINER_NAME = new TranslatableComponent("container.crafting");

	public ModCraftingTableBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			SimpleMenuProvider provider = new SimpleMenuProvider((id, inventory,
					p) -> new ModCraftingContainer(id, inventory, ContainerLevelAccess.create(worldIn, pos)),
					CONTAINER_NAME);
			NetworkHooks.openGui((ServerPlayer) player, provider);
			player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player) -> {
			return new CraftingMenu(id, inventory, ContainerLevelAccess.create(worldIn, pos));
		}, CONTAINER_NAME);
	}

	private static class ModCraftingContainer extends CraftingMenu {

		private ContainerLevelAccess worldPosCallable;

		public ModCraftingContainer(int syncid, Inventory playerInv, ContainerLevelAccess posCallable) {
			super(syncid, playerInv, posCallable);
			this.worldPosCallable = posCallable;
		}

		@Override
		public boolean stillValid(Player playerIn) {
			return worldPosCallable.evaluate((world, pos) -> {
				return !(world.getBlockState(pos).getBlock() instanceof ModCraftingTableBlock) ? false
						: playerIn.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64d;
			}, true);
		}

	}

}
