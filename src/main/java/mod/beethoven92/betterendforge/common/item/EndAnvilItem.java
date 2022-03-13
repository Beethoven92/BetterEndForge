package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class EndAnvilItem extends BlockItem {

	public EndAnvilItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	protected BlockState getPlacementState(BlockItemUseContext context) {
		BlockState blockState = super.getPlacementState(context);
		if (blockState == null)
			return null;
		ItemStack stack = context.getItemInHand();
		int level = stack.getOrCreateTag().getInt("level");
		blockState = blockState.setValue(((EndAnvilBlock) blockState.getBlock()).getDestructionProperty(), level);
		return blockState;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, World level, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		int l = itemStack.getOrCreateTag().getInt("level");
		if (l > 0) {
			list.add(new TranslationTextComponent("message." + BetterEnd.MOD_ID + ".anvil_damage").append(": " + l));
		}
	}
}
