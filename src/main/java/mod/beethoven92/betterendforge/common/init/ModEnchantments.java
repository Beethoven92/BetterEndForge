package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.enchantment.EndVeilEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments 
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = 
			DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BetterEnd.MOD_ID);
	
	public static final RegistryObject<Enchantment> END_VEIL = 
			ENCHANTMENTS.register("end_veil", 
					() -> new EndVeilEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, 
							new EquipmentSlotType[] { EquipmentSlotType.HEAD }));
}
