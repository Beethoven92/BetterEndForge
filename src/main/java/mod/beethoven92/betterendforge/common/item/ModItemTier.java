package mod.beethoven92.betterendforge.common.item;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModItemTier implements Tier
{
	THALLASIUM(2, 320, 7.0F, 1.5F, 12, () -> {
		return Ingredient.of(ModBlocks.THALLASIUM.ingot.get());
	}),
	TERMINITE(3, 1230, 8.5F, 3.0F, 14, () -> {
		return Ingredient.of(ModBlocks.TERMINITE.ingot.get());
	}),
	AETERNIUM(5, 2196, 10.0F, 4.5F, 18, () -> {
		return Ingredient.of(ModItems.AETERNIUM_INGOT.get());
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
	public int getUses() 
	{
		return maxUses;
	}

	@Override
	public float getSpeed()
	{
		return efficiency;
	}

	@Override
	public float getAttackDamageBonus() 
	{
		return attackDamage;
	}

	@Override
	public int getLevel() 
	{
		return harvestLevel;
	}

	@Override
	public int getEnchantmentValue() 
	{
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() 
	{
		return repairMaterial.get();
	}

}
