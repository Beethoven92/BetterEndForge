package mod.beethoven92.betterendforge.common.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class UmbrellaClusterJuiceItem extends Item
{
	public UmbrellaClusterJuiceItem(Properties properties) 
	{
		super(properties);
	}

	public int getUseDuration(ItemStack stack) 
	{
		return 32;
	}

	public UseAnim getUseAnimation(ItemStack stack) 
	{
		return UseAnim.DRINK;
	}

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) 
	{
		return ItemUtils.useDrink(worldIn, playerIn, handIn);
	}
}
