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
		this.add(ModEntityTypes.DRAGONFLY.get(), LootTable.lootTable());
		this.add(ModEntityTypes.END_FISH.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ModItems.END_FISH_RAW.get()).apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))));
		this.add(ModEntityTypes.SHADOW_WALKER.get(), LootTable.lootTable());
		this.add(ModEntityTypes.END_SLIME.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.SLIME_BALL).apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));
		this.add(ModEntityTypes.CUBOZOA.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ModItems.GELATINE.get()).apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));
		this.add(ModEntityTypes.SILK_MOTH.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ModItems.SILK_FIBER.get()).apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));
	}
	
	@Override
	protected Iterable<EntityType<?>> getKnownEntities() 
	{
		return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(BetterEnd.MOD_ID))
				.collect(Collectors.toSet());
	}
}
