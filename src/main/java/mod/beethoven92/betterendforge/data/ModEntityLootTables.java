package mod.beethoven92.betterendforge.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityLootTables extends EntityLootTables
{
	@Override
	protected void addTables()
	{
		this.registerLootTable(ModEntityTypes.DRAGONFLY.get(), LootTable.builder());
		this.registerLootTable(ModEntityTypes.END_FISH.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ModItems.END_FISH_RAW.get()).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))))));
		this.registerLootTable(ModEntityTypes.SHADOW_WALKER.get(), LootTable.builder());
		this.registerLootTable(ModEntityTypes.END_SLIME.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SLIME_BALL).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
		this.registerLootTable(ModEntityTypes.CUBOZOA.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ModItems.GELATINE.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
		this.registerLootTable(ModEntityTypes.SILK_MOTH.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ModItems.SILK_FIBER.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
	}
	
	@Override
	protected Iterable<EntityType<?>> getKnownEntities() 
	{
		return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(BetterEnd.MOD_ID))
				.collect(Collectors.toSet());
	}
}
