package mod.beethoven92.betterendforge.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedPetalItem extends Item
{
	public EnchantedPetalItem(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
