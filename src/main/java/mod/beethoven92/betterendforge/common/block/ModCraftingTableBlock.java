package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.AbstractBlock.Properties;

public class ModCraftingTableBlock extends CraftingTableBlock {
	private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

	public ModCraftingTableBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider((id, inventory,
					p) -> new ModCraftingContainer(id, inventory, IWorldPosCallable.create(worldIn, pos)),
					CONTAINER_NAME);
			NetworkHooks.openGui((ServerPlayerEntity) player, provider);
			player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, inventory, player) -> {
			return new WorkbenchContainer(id, inventory, IWorldPosCallable.create(worldIn, pos));
		}, CONTAINER_NAME);
	}

	private static class ModCraftingContainer extends WorkbenchContainer {

		private IWorldPosCallable worldPosCallable;

		public ModCraftingContainer(int syncid, PlayerInventory playerInv, IWorldPosCallable posCallable) {
			super(syncid, playerInv, posCallable);
			this.worldPosCallable = posCallable;
		}

		@Override
		public boolean stillValid(PlayerEntity playerIn) {
			return worldPosCallable.evaluate((world, pos) -> {
				return !(world.getBlockState(pos).getBlock() instanceof ModCraftingTableBlock) ? false
						: playerIn.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64d;
			}, true);
		}

	}

}
