package mod.beethoven92.betterendforge.common.item;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ModItemTier implements IItemTier
{
	THALLASIUM(2, 320, 7.0F, 1.5F, 12, () -> {
		return Ingredient.fromItems(ModBlocks.THALLASIUM.ingot.get());
	}),
	TERMINITE(3, 1230, 8.5F, 3.0F, 14, () -> {
		return Ingredient.fromItems(ModBlocks.TERMINITE.ingot.get());
	}),
	AETERNIUM(5, 2196, 10.0F, 4.5F, 18, () -> {
		return Ingredient.fromItems(ModItems.AETERNIUM_INGOT.get());
	});
	
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int harvestLevel;
	private final int enchantability;
	private final Supplier<Ingredient> repairMaterial;
	
	ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
			Supplier<Ingredient> repairMaterial) 
	{
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.harvestLevel = harvestLevel;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getMaxUses() 
	{
		return maxUses;
	}

	@Override
	public float getEfficiency()
	{
		return efficiency;
	}

	@Override
	public float getAttackDamage() 
	{
		return attackDamage;
	}

	@Override
	public int getHarvestLevel() 
	{
		return harvestLevel;
	}

	@Override
	public int getEnchantability() 
	{
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() 
	{
		return repairMaterial.get();
	}

}
