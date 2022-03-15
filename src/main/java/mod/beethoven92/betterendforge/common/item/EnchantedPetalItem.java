package mod.beethoven92.betterendforge.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class EnchantedPetalItem extends Item
{
	public EnchantedPetalItem(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean isFoil(ItemStack stack)
	{
		return true;
	}
}
