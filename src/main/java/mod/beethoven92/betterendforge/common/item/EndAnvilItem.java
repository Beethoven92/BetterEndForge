package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class EndAnvilItem extends BlockItem {

	public EndAnvilItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState blockState = super.getPlacementState(context);
		if (blockState == null)
			return null;
		ItemStack stack = context.getItemInHand();
		int level = stack.getOrCreateTag().getInt("level");
		blockState = blockState.setValue(((EndAnvilBlock) blockState.getBlock()).getDestructionProperty(), level);
		return blockState;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		int l = itemStack.getOrCreateTag().getInt("level");
		if (l > 0) {
			list.add(new TranslatableComponent("message." + BetterEnd.MOD_ID + ".anvil_damage").append(": " + l));
		}
	}
}
