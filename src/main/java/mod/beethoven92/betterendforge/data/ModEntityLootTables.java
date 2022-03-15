package mod.beethoven92.betterendforge.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.ConstantIntValue;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.RandomValueBounds;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityLootTables extends EntityLoot
{
	@Override
	protected void addTables()
	{
		this.add(ModEntityTypes.DRAGONFLY.get(), LootTable.lootTable());
		this.add(ModEntityTypes.END_FISH.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantIntValue.exactly(1)).add(LootItem.lootTableItem(ModItems.END_FISH_RAW.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))));
		this.add(ModEntityTypes.SHADOW_WALKER.get(), LootTable.lootTable());
		this.add(ModEntityTypes.END_SLIME.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantIntValue.exactly(1)).add(LootItem.lootTableItem(Items.SLIME_BALL).apply(SetItemCountFunction.setCount(RandomValueBounds.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(RandomValueBounds.between(0.0F, 1.0F))))));
		this.add(ModEntityTypes.CUBOZOA.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantIntValue.exactly(1)).add(LootItem.lootTableItem(ModItems.GELATINE.get()).apply(SetItemCountFunction.setCount(RandomValueBounds.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(RandomValueBounds.between(0.0F, 1.0F))))));
		this.add(ModEntityTypes.SILK_MOTH.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantIntValue.exactly(1)).add(LootItem.lootTableItem(ModItems.SILK_FIBER.get()).apply(SetItemCountFunction.setCount(RandomValueBounds.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(RandomValueBounds.between(0.0F, 1.0F))))));
	}
	
	@Override
	protected Iterable<EntityType<?>> getKnownEntities() 
	{
		return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(BetterEnd.MOD_ID))
				.collect(Collectors.toSet());
	}
}
