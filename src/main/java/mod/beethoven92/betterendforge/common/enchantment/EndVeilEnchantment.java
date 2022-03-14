package mod.beethoven92.betterendforge.common.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class EndVeilEnchantment extends Enchantment
{
	public EndVeilEnchantment(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot[] slots)
	{
		super(rarityIn, typeIn, slots);
	}
	
	public int getMaxLevel() 
	{
		return 1;
	}
}
