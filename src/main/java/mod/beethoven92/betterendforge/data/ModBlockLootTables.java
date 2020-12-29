package mod.beethoven92.betterendforge.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.EndLilyBlock;
import mod.beethoven92.betterendforge.common.block.RespawnObeliskBlock;
import mod.beethoven92.betterendforge.common.block.ShadowBerryBlock;
import mod.beethoven92.betterendforge.common.block.SulphurCrystalBlock;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables
{
	@Override
	protected void addTables() 
	{
		// BLOCKS		
	    registerLootTable(ModBlocks.RESPAWN_OBELISK.get(), (block) -> {
	    	return droppingWhen(block, RespawnObeliskBlock.SHAPE, TripleShape.BOTTOM);
	    });
	    
	    registerDropSelfLootTable(ModBlocks.HYDRALUX_PETAL_BLOCK.get());
	    registerDropSelfLootTable(ModBlocks.HYDRALUX.get());
	    
	    // TERRAINS
	    registerLootTable(ModBlocks.CRYSTAL_MOSS.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
	    registerLootTable(ModBlocks.END_MYCELIUM.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
	    registerLootTable(ModBlocks.END_MOSS.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
	    registerLootTable(ModBlocks.CHORUS_NYLIUM.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
	    registerLootTable(ModBlocks.CAVE_MOSS.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
	    registerLootTable(ModBlocks.SHADOW_GRASS.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
	    registerLootTable(ModBlocks.PINK_MOSS.get(), (terrain) -> {
	    	return droppingWithSilkTouch(terrain, Blocks.END_STONE);
	    });
		registerDropSelfLootTable(ModBlocks.ENDSTONE_DUST.get());
		
		// PATHS
		registerDropping(ModBlocks.CRYSTAL_MOSS_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.END_MYCELIUM_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.END_MOSS_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.CHORUS_NYLIUM_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.CAVE_MOSS_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.SHADOW_GRASS_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.PINK_MOSS_PATH.get(), Blocks.END_STONE);
		
		// MATERIALS
		registerDropSelfLootTable(ModBlocks.AETERNIUM_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.TERMINITE_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.ENDER_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.AMBER_BLOCK.get());
	    registerLootTable(ModBlocks.AURORA_CRYSTAL.get(), (block) -> {
	    	return droppingItemWithFortune(block, ModItems.CRYSTAL_SHARDS.get());
	    });
		
		// ORES
	    registerLootTable(ModBlocks.ENDER_ORE.get(), (ore) -> {
	    	return droppingItemWithFortune(ore, ModItems.ENDER_SHARD.get());
	    });
	    registerLootTable(ModBlocks.AMBER_ORE.get(), (ore) -> {
	    	return droppingItemWithFortune(ore, ModItems.RAW_AMBER.get());
	    });
	    
		// STONES
		registerDropSelfLootTable(ModBlocks.FLAVOLITE_RUNED.get());
		
		registerDropSelfLootTable(ModBlocks.QUARTZ_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.PURPUR_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.ETERNAL_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.INFUSION_PEDESTAL.get());
		
	    registerDropSelfLootTable(ModBlocks.BRIMSTONE.get());
	    
	    registerDropSelfLootTable(ModBlocks.HYDROTHERMAL_VENT.get());
	    
	    registerLootTable(ModBlocks.SULPHUR_CRYSTAL.get(), (block) -> {
	    	return sulphurCrystalDrop(block, ModItems.CRYSTALLINE_SULPHUR.get());
	    });
		
		registerLootTable(ModBlocks.END_STONE_SMELTER.get(), BlockLootTables::droppingWithName);
		
		// PLANTS
		registerLootTable(ModBlocks.UMBRELLA_MOSS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.UMBRELLA_MOSS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.UMBRELLA_MOSS_TALL.get(), BlockLootTables::onlyWithShears);
		//registerDropSelfLootTable(ModBlocks.UMBRELLA_MOSS_TALL.get());
		registerLootTable(ModBlocks.CREEPING_MOSS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.CREEPING_MOSS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.CHORUS_GRASS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.CHORUS_GRASS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.CAVE_GRASS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.CAVE_GRASS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.CRYSTAL_GRASS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.CRYSTAL_GRASS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.SHADOW_PLANT.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.SHADOW_PLANT.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.BUSHY_GRASS.get(), BlockLootTables::onlyWithShears);
		
		registerLootTable(ModBlocks.BLUE_VINE_SEED.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.BLUE_VINE_SEED.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.BLUE_VINE.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.BLUE_VINE.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerDropSelfLootTable(ModBlocks.BLUE_VINE_LANTERN.get());
		
		registerLootTable(ModBlocks.BLUE_VINE_FUR.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.BLUE_VINE_SEED.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});
		
		registerLootTable(ModBlocks.CAVE_BUSH.get(), BlockLootTables::onlyWithShears);
		//registerDropSelfLootTable(ModBlocks.CAVE_BUSH.get());
		
		registerLootTable(ModBlocks.END_LILY_SEED.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.END_LILY_SEED.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
	    
		this.registerLootTable(ModBlocks.END_LILY.get(), (block) -> {
			return endLilyDrop();
	    });
		
		registerLootTable(ModBlocks.END_LOTUS_SEED.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.END_LOTUS_SEED.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		
		registerLootTable(ModBlocks.END_LOTUS_LEAF.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.END_LOTUS_LEAF.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		
		registerDropSelfLootTable(ModBlocks.END_LOTUS_STEM.get());
		
		registerLootTable(ModBlocks.END_LOTUS_FLOWER.get(), droppingRandomly(ModBlocks.END_LOTUS_SEED.get(), RandomValueRange.of(1.0F, 2.0F)));
		
		registerLootTable(ModBlocks.BUBBLE_CORAL.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.BUBBLE_CORAL.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/	
		
		registerLootTable(ModBlocks.MURKWEED.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.MURKWEED.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
	    
		registerLootTable(ModBlocks.NEEDLEGRASS.get(), (block) -> {
	    	return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))));
	    });
		
	    ILootCondition.IBuilder ilootcondition$ibuilder1 = BlockStateProperty.builder(ModBlocks.SHADOW_BERRY.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(ShadowBerryBlock.AGE, 3));
	    registerLootTable(ModBlocks.SHADOW_BERRY.get(), droppingAndBonusWhen(ModBlocks.SHADOW_BERRY.get(), ModItems.SHADOW_BERRY_RAW.get(), ModBlocks.SHADOW_BERRY.get().asItem(), ilootcondition$ibuilder1)); 
	    
	    registerDropSelfLootTable(ModBlocks.MENGER_SPONGE.get());
	    registerDropSelfLootTable(ModBlocks.MENGER_SPONGE_WET.get());
	    
	    registerLootTable(ModBlocks.CHARNIA_RED.get(), BlockLootTables::onlyWithShears);
	    registerLootTable(ModBlocks.CHARNIA_PURPLE.get(), BlockLootTables::onlyWithShears);
	    registerLootTable(ModBlocks.CHARNIA_ORANGE.get(), BlockLootTables::onlyWithShears);
	    registerLootTable(ModBlocks.CHARNIA_LIGHT_BLUE.get(), BlockLootTables::onlyWithShears);
	    registerLootTable(ModBlocks.CHARNIA_CYAN.get(), BlockLootTables::onlyWithShears);
	    registerLootTable(ModBlocks.CHARNIA_GREEN.get(), BlockLootTables::onlyWithShears);
	    
	    // WALL_PLANTS
		registerDropSelfLootTable(ModBlocks.PURPLE_POLYPORE.get());
		
		registerLootTable(ModBlocks.TAIL_MOSS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.TAIL_MOSS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		
		registerLootTable(ModBlocks.CYAN_MOSS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.CYAN_MOSS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		
		registerLootTable(ModBlocks.TWISTED_MOSS.get(), BlockLootTables::onlyWithShears);
		
		registerLootTable(ModBlocks.TUBE_WORM.get(), BlockLootTables::onlyWithShears);
		
		// VINES
		registerLootTable(ModBlocks.DENSE_VINE.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.DENSE_VINE.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		
		registerLootTable(ModBlocks.TWISTED_VINE.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.TWISTED_VINE.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		
		registerLootTable(ModBlocks.BULB_VINE.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.BULB_VINE_SEED.get(), BlockLootTables::onlyWithShears);
		
		// TREES
		registerDropSelfLootTable(ModBlocks.HYDRALUX_SAPLING.get());

		
		registerDropSelfLootTable(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get());
		registerDropSelfLootTable(ModBlocks.MOSSY_GLOWSHROOM_CAP.get());
		registerDropSelfLootTable(ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE.get());
		registerLootTable(ModBlocks.MOSSY_GLOWSHROOM_FUR.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});
		
		registerDropSelfLootTable(ModBlocks.LACUGROVE_SAPLING.get());
		registerLootTable(ModBlocks.LACUGROVE_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.LACUGROVE_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});
		
		registerDropSelfLootTable(ModBlocks.PYTHADENDRON_SAPLING.get());
		registerLootTable(ModBlocks.PYTHADENDRON_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.PYTHADENDRON_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});
		
		registerDropSelfLootTable(ModBlocks.DRAGON_TREE_SAPLING.get());
		registerLootTable(ModBlocks.DRAGON_TREE_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.DRAGON_TREE_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});
		
		registerDropSelfLootTable(ModBlocks.TENANEA_SAPLING.get());
		registerLootTable(ModBlocks.TENANEA_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.TENANEA_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});		
		
		registerLootTable(ModBlocks.TENANEA_FLOWERS.get(), BlockLootTables::onlyWithShears);
		/*registerLootTable(ModBlocks.TENANEA_FLOWERS.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(block).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
		});*/
		registerLootTable(ModBlocks.TENANEA_OUTER_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.TENANEA_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});
		
		registerDropSelfLootTable(ModBlocks.HELIX_TREE_SAPLING.get());
		registerLootTable(ModBlocks.HELIX_TREE_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.HELIX_TREE_SAPLING.get())).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});		
		
		// WOODEN_MATERIALS
		registerWoodenMaterialLootTables(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialLootTables(ModBlocks.LACUGROVE);
		registerWoodenMaterialLootTables(ModBlocks.END_LOTUS);
		registerWoodenMaterialLootTables(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialLootTables(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialLootTables(ModBlocks.TENANEA);
		registerWoodenMaterialLootTables(ModBlocks.HELIX_TREE);
		
		// STONE MATERIALS
		registerStoneMaterialLootTables(ModBlocks.FLAVOLITE);
		registerStoneMaterialLootTables(ModBlocks.VIOLECITE);
		registerStoneMaterialLootTables(ModBlocks.SULPHURIC_ROCK);
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks()
	{
		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(BetterEnd.MOD_ID))
				.collect(Collectors.toSet());
	}
	
	private void registerWoodenMaterialLootTables(WoodenMaterial material)
	{
		registerDropSelfLootTable(material.log.get());
		registerDropSelfLootTable(material.bark.get());
		registerDropSelfLootTable(material.log_stripped.get());
		registerDropSelfLootTable(material.bark_stripped.get());
		registerDropSelfLootTable(material.planks.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.fence.get());
		registerDropSelfLootTable(material.gate.get());
		registerDropSelfLootTable(material.button.get());
		registerDropSelfLootTable(material.pressurePlate.get());
		registerDropSelfLootTable(material.trapdoor.get());
		registerLootTable(material.door.get(), BlockLootTables::registerDoor);
	}
	
	private void registerStoneMaterialLootTables(StoneMaterial material)
	{
		registerDropSelfLootTable(material.stone.get());
		registerDropSelfLootTable(material.polished.get());
		registerDropSelfLootTable(material.tiles.get());
		registerDropSelfLootTable(material.pillar.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.wall.get());
		registerDropSelfLootTable(material.button.get());
		registerDropSelfLootTable(material.pressure_plate.get());
		registerDropSelfLootTable(material.bricks.get());
		registerDropSelfLootTable(material.brick_stairs.get());
		registerLootTable(material.brick_slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.brick_wall.get());
	}
	
	// Need to improve
	private static LootTable.Builder endLilyDrop() 
	{
		LootEntry.Builder<?> leaf_drop = ItemLootEntry.builder(ModItems.END_LILY_LEAF.get()).acceptCondition(RandomChance.builder(0.525F));
		LootEntry.Builder<?> seed_drop = ItemLootEntry.builder(ModBlocks.END_LILY_SEED.get()).acceptCondition(RandomChance.builder(0.525F));
		return LootTable.builder().addLootPool(LootPool.builder().addEntry(leaf_drop).acceptCondition(BlockStateProperty.builder(ModBlocks.END_LILY.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(EndLilyBlock.SHAPE, TripleShape.TOP)))).addLootPool(LootPool.builder().addEntry(seed_drop).acceptCondition(BlockStateProperty.builder(ModBlocks.END_LILY.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(EndLilyBlock.SHAPE, TripleShape.BOTTOM))));
	}
	
	private static LootTable.Builder sulphurCrystalDrop(Block block, Item drop)
	{
		                                                                             //.rolls(ConstantRange.of(1)).
		return LootTable.builder().addLootPool(withExplosionDecay(block, LootPool.builder().addEntry(ItemLootEntry.builder(drop).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SulphurCrystalBlock.AGE, 3)))))));
	}
}
