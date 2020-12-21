package mod.beethoven92.betterendforge.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class EndVeilEnchantment extends Enchantment
{
	public EndVeilEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots)
	{
		super(rarityIn, typeIn, slots);
	}
	
	public int getMaxLevel() 
	{
		return 1;
	}
}
