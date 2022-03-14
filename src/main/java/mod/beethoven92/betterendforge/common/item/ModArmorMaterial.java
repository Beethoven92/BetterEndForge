package mod.beethoven92.betterendforge.common.item;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum ModArmorMaterial implements ArmorMaterial
{
	THALLASIUM(BetterEnd.MOD_ID + ":thallasium", 17, new int[] { 1, 4, 5, 2 }, 12, 
			SoundEvents.ARMOR_EQUIP_IRON, 0.0F, () -> {
				return Ingredient.of(ModBlocks.THALLASIUM.ingot.get());}, 0.0F),
	
	TERMINITE(BetterEnd.MOD_ID + ":terminite", 26, new int[] {3, 6, 7, 3}, 14, 
			SoundEvents.ARMOR_EQUIP_IRON, 1.0F, () -> {
				return Ingredient.of(ModBlocks.TERMINITE.ingot.get());}, 0.05F),
	
	AETERNIUM(BetterEnd.MOD_ID + ":aeternium", 40, new int[] { 4, 7, 9, 4 }, 18, 
			SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, () -> {
				return Ingredient.of(ModItems.AETERNIUM_INGOT.get());}, 0.2F),
	
	CRYSTALITE(BetterEnd.MOD_ID + ":crystalite", 30, new int[] { 3, 6, 8, 3 }, 24, 
			SoundEvents.ARMOR_EQUIP_DIAMOND, 1.2F, () -> {
				return Ingredient.of(ModBlocks.TERMINITE.ingot.get());}, 0.1F);


	private static final int[] MAX_DAMAGE_ARRAY = { 11, 16, 15, 13 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float thoughness;
	private final Supplier<Ingredient> repairMaterial;
	private final float knockbackResistance;
	
	ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, 
			SoundEvent soundEvent, float thoughness, Supplier<Ingredient> repairMaterial, float knockbackResistance)
	{
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.thoughness = thoughness;
		this.repairMaterial = repairMaterial;
		this.knockbackResistance = knockbackResistance;
	}
	
	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) 
	{
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) 
	{
		return damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantmentValue() 
	{
		return enchantability;
	}

	@Override
	public SoundEvent getEquipSound() 
	{
		return soundEvent;
	}

	@Override
	public Ingredient getRepairIngredient() 
	{
		return repairMaterial.get();
	}

	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public float getToughness() 
	{
		return thoughness;
	}

	@Override
	public float getKnockbackResistance()
	{
		return knockbackResistance;
	}

}
