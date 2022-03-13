package mod.beethoven92.betterendforge.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.BulbVineBlock;
import mod.beethoven92.betterendforge.common.block.EndLilyBlock;
import mod.beethoven92.betterendforge.common.block.HydraluxBlock;
import mod.beethoven92.betterendforge.common.block.LanceleafBlock;
import mod.beethoven92.betterendforge.common.block.LumecornBlock;
import mod.beethoven92.betterendforge.common.block.RespawnObeliskBlock;
import mod.beethoven92.betterendforge.common.block.ShadowBerryBlock;
import mod.beethoven92.betterendforge.common.block.SilkMothNestBlock;
import mod.beethoven92.betterendforge.common.block.SulphurCrystalBlock;
import mod.beethoven92.betterendforge.common.block.UmbrellaTreeMembraneBlock;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
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
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables {
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null
						&& entry.getRegistryName().getNamespace().equals(BetterEnd.MOD_ID))
				.collect(Collectors.toSet());
	}

	@Override
	protected void addTables() {
		// BLOCKS
		anvilLootTable((EndAnvilBlock) ModBlocks.AETERNIUM_ANVIL.get());

		add(ModBlocks.RESPAWN_OBELISK.get(), (block) -> {
			return createSinglePropConditionTable(block, RespawnObeliskBlock.SHAPE, TripleShape.BOTTOM);
		});

		dropSelf(ModBlocks.HYDRALUX_PETAL_BLOCK.get());

		dropSelf(ModBlocks.DENSE_SNOW.get());
		dropWhenSilkTouch(ModBlocks.EMERALD_ICE.get());
		dropSelf(ModBlocks.DENSE_EMERALD_ICE.get());
		dropSelf(ModBlocks.ANCIENT_EMERALD_ICE.get());

		dropSelf(ModBlocks.IRON_CHANDELIER.get());
		dropSelf(ModBlocks.GOLD_CHANDELIER.get());

		dropSelf(ModBlocks.MISSING_TILE.get());

		// TERRAINS
		add(ModBlocks.CRYSTAL_MOSS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.END_MYCELIUM.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.END_MOSS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.CHORUS_NYLIUM.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.CAVE_MOSS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.SHADOW_GRASS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.PINK_MOSS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.AMBER_MOSS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.JUNGLE_MOSS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.SANGNUM.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		add(ModBlocks.RUTISCUS.get(), (terrain) -> {
			return createSingleItemTableWithSilkTouch(terrain, Blocks.END_STONE);
		});
		dropSelf(ModBlocks.ENDSTONE_DUST.get());

		// PATHS
		dropOther(ModBlocks.CRYSTAL_MOSS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.END_MYCELIUM_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.END_MOSS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.CHORUS_NYLIUM_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.CAVE_MOSS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.SHADOW_GRASS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.PINK_MOSS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.AMBER_MOSS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.JUNGLE_MOSS_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.SANGNUM_PATH.get(), Blocks.END_STONE);
		dropOther(ModBlocks.RUTISCUS_PATH.get(), Blocks.END_STONE);

		add(ModBlocks.MOSSY_OBSIDIAN.get(), (block) -> {
			return createSingleItemTableWithSilkTouch(block, Blocks.OBSIDIAN);
		});

		// MATERIALS
		dropSelf(ModBlocks.AETERNIUM_BLOCK.get());
		dropSelf(ModBlocks.ENDER_BLOCK.get());
		dropSelf(ModBlocks.AMBER_BLOCK.get());
		add(ModBlocks.AURORA_CRYSTAL.get(), (block) -> {
			return createOreDrop(block, ModItems.CRYSTAL_SHARDS.get());
		});
		dropSelf(ModBlocks.SMARAGDANT_CRYSTAL.get());
		dropSelf(ModBlocks.SMARAGDANT_CRYSTAL_SHARD.get());

		// ORES
		add(ModBlocks.ENDER_ORE.get(), (ore) -> {
			return createOreDrop(ore, ModItems.ENDER_SHARD.get());
		});
		add(ModBlocks.AMBER_ORE.get(), (ore) -> {
			return createOreDrop(ore, ModItems.RAW_AMBER.get());
		});

		// STONES
		dropSelf(ModBlocks.FLAVOLITE_RUNED.get());

		dropSelf(ModBlocks.QUARTZ_PEDESTAL.get());
		dropSelf(ModBlocks.PURPUR_PEDESTAL.get());
		dropSelf(ModBlocks.ANDESITE_PEDESTAL.get());
		dropSelf(ModBlocks.DIORITE_PEDESTAL.get());
		dropSelf(ModBlocks.GRANITE_PEDESTAL.get());
		dropSelf(ModBlocks.ETERNAL_PEDESTAL.get());
		dropSelf(ModBlocks.INFUSION_PEDESTAL.get());

		dropSelf(ModBlocks.ANDESITE_LANTERN.get());
		dropSelf(ModBlocks.DIORITE_LANTERN.get());
		dropSelf(ModBlocks.GRANITE_LANTERN.get());
		dropSelf(ModBlocks.QUARTZ_LANTERN.get());
		dropSelf(ModBlocks.PURPUR_LANTERN.get());
		dropSelf(ModBlocks.END_STONE_LANTERN.get());
		dropSelf(ModBlocks.BLACKSTONE_LANTERN.get());

		dropSelf(ModBlocks.BRIMSTONE.get());

		dropSelf(ModBlocks.HYDROTHERMAL_VENT.get());

		add(ModBlocks.SULPHUR_CRYSTAL.get(), (block) -> {
			return sulphurCrystalDrop(block, ModItems.CRYSTALLINE_SULPHUR.get());
		});

		add(ModBlocks.END_STONE_SMELTER.get(), BlockLootTables::createNameableBlockEntityTable);
		add(ModBlocks.END_STONE_FURNACE.get(), BlockLootTables::createNameableBlockEntityTable);

		dropSelf(ModBlocks.END_STONE_STALACTITE.get());
		dropSelf(ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get());

		// PLANTS
		add(ModBlocks.UMBRELLA_MOSS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.UMBRELLA_MOSS_TALL.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CREEPING_MOSS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CHORUS_GRASS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CAVE_GRASS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CRYSTAL_GRASS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.AMBER_GRASS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.SHADOW_PLANT.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.BUSHY_GRASS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.TWISTED_UMBRELLA_MOSS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.JUNGLE_GRASS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.BLOOMING_COOKSONIA.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.SALTEAGO.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.VAIOLUSH_FERN.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.FRACTURN.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.LARGE_AMARANITA_MUSHROOM.get(), noDrop());
		add(ModBlocks.SMALL_AMARANITA_MUSHROOM.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.GLOBULAGUS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CLAWFERN.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.AERIDIUM.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.LAMELLARIUM.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.POND_ANEMONE.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.RUSCUS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.ORANGO.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.LUTEBUS.get(), BlockLootTables::createShearsOnlyDrop);
		dropSelf(ModBlocks.FLAMAEA.get());
		dropSelf(ModBlocks.BOLUX_MUSHROOM.get());
		dropSelf(ModBlocks.AURANT_POLYPORE.get());
		dropSelf(ModBlocks.NEON_CACTUS.get());
		dropSelf(ModBlocks.NEON_CACTUS_BLOCK.get());
		dropSelf(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get());
		dropSelf(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get());

		add(ModBlocks.BLUE_VINE_SEED.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.BLUE_VINE.get(), BlockLootTables::createShearsOnlyDrop);
		dropSelf(ModBlocks.BLUE_VINE_LANTERN.get());
		add(ModBlocks.BLUE_VINE_FUR.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.BLUE_VINE_SEED.get())).when(
							TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});

		add(ModBlocks.CAVE_BUSH.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.END_LILY_SEED.get(), BlockLootTables::createShearsOnlyDrop);

		this.add(ModBlocks.END_LILY.get(), (block) -> {
			return endLilyDrop();
		});

		add(ModBlocks.END_LOTUS_SEED.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.END_LOTUS_LEAF.get(), BlockLootTables::createShearsOnlyDrop);
		dropSelf(ModBlocks.END_LOTUS_STEM.get());
		add(ModBlocks.END_LOTUS_FLOWER.get(),
				createSingleItemTable(ModBlocks.END_LOTUS_SEED.get(), RandomValueRange.between(1.0F, 2.0F)));

		add(ModBlocks.BUBBLE_CORAL.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.MURKWEED.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.NEEDLEGRASS.get(), (block) -> {
			return createShearsDispatchTable(block, applyExplosionDecay(block, ItemLootEntry.lootTableItem(Items.STICK)
					.apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))));
		});

		dropSelf(ModBlocks.MENGER_SPONGE.get());
		dropSelf(ModBlocks.MENGER_SPONGE_WET.get());

		add(ModBlocks.CHARNIA_RED.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CHARNIA_PURPLE.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CHARNIA_ORANGE.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CHARNIA_LIGHT_BLUE.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CHARNIA_CYAN.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.CHARNIA_GREEN.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.HYDRALUX_SAPLING.get(), BlockLootTables::createShearsOnlyDrop);
		this.add(ModBlocks.HYDRALUX.get(), (block) -> {
			return hydraluxDrop();
		});

		add(ModBlocks.LANCELEAF_SEED.get(), BlockLootTables::createShearsOnlyDrop);
		lanceleaf();

		lumecorn();

		add(ModBlocks.GLOWING_PILLAR_SEED.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.GLOWING_PILLAR_ROOTS.get(), (a) -> BlockLootTables.noDrop());
		dropSelf(ModBlocks.GLOWING_PILLAR_LUMINOPHOR.get());
		add(ModBlocks.GLOWING_PILLAR_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.GLOWING_PILLAR_SEED.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		add(ModBlocks.SMALL_JELLYSHROOM.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.SILK_MOTH_NEST.get(), (block) -> {
			return LootTable.lootTable()
					.withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(block).when(
									BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder
											.properties().hasProperty(SilkMothNestBlock.ACTIVE, true))))));
		});

		// SKY PLANTS
		dropSelf(ModBlocks.FILALUX_LANTERN.get());
		dropSelf(ModBlocks.FILALUX_WINGS.get());

		// CROPS
		ILootCondition.IBuilder ilootcondition$ibuilder = BlockStateProperty.hasBlockStateProperties(ModBlocks.SHADOW_BERRY.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ShadowBerryBlock.AGE, 3));
		add(ModBlocks.SHADOW_BERRY.get(), createCropDrops(ModBlocks.SHADOW_BERRY.get(),
				ModItems.SHADOW_BERRY_RAW.get(), ModBlocks.SHADOW_BERRY.get().asItem(), ilootcondition$ibuilder));

		ilootcondition$ibuilder = BlockStateProperty.hasBlockStateProperties(ModBlocks.BLOSSOM_BERRY.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EndCropBlock.AGE, 3));
		add(ModBlocks.BLOSSOM_BERRY.get(), createCropDrops(ModBlocks.BLOSSOM_BERRY.get(),
				ModItems.BLOSSOM_BERRY.get(), ModBlocks.BLOSSOM_BERRY.get().asItem(), ilootcondition$ibuilder));

		ilootcondition$ibuilder = BlockStateProperty.hasBlockStateProperties(ModBlocks.AMBER_ROOT.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EndCropBlock.AGE, 3));
		add(ModBlocks.AMBER_ROOT.get(), createCropDrops(ModBlocks.AMBER_ROOT.get(),
				ModItems.AMBER_ROOT_RAW.get(), ModBlocks.AMBER_ROOT.get().asItem(), ilootcondition$ibuilder));

		ilootcondition$ibuilder = BlockStateProperty.hasBlockStateProperties(ModBlocks.CHORUS_MUSHROOM.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EndCropBlock.AGE, 3));
		add(ModBlocks.CHORUS_MUSHROOM.get(), createCropDrops(ModBlocks.CHORUS_MUSHROOM.get(),
				ModItems.CHORUS_MUSHROOM_RAW.get(), ModBlocks.CHORUS_MUSHROOM.get().asItem(), ilootcondition$ibuilder));

//	    ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.PEARLBERRY.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
//	    registerLootTable(ModBlocks.PEARLBERRY.get(), droppingAndBonusWhen(ModBlocks.PEARLBERRY.get(), ModItems.BLOSSOM_BERRY.get(), ModBlocks.PEARLBERRY.get().asItem(), ilootcondition$ibuilder));

		// WALL_PLANTS
		dropSelf(ModBlocks.PURPLE_POLYPORE.get());

		add(ModBlocks.TAIL_MOSS.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.CYAN_MOSS.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.TWISTED_MOSS.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.BULB_MOSS.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.TUBE_WORM.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.JUNGLE_FERN.get(), BlockLootTables::createShearsOnlyDrop);

		// VINES
		add(ModBlocks.DENSE_VINE.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.TWISTED_VINE.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.BULB_VINE.get(), (block) -> {
			return bulbVineDrop();
		});
		add(ModBlocks.BULB_VINE_SEED.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.JUNGLE_VINE.get(), BlockLootTables::createShearsOnlyDrop);

		add(ModBlocks.RUBINEA.get(), BlockLootTables::createShearsOnlyDrop);

		// TREES
		dropSelf(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get());
		dropSelf(ModBlocks.MOSSY_GLOWSHROOM_CAP.get());
		dropSelf(ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE.get());
		add(ModBlocks.MOSSY_GLOWSHROOM_FUR.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		dropSelf(ModBlocks.LACUGROVE_SAPLING.get());
		add(ModBlocks.LACUGROVE_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.LACUGROVE_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		dropSelf(ModBlocks.PYTHADENDRON_SAPLING.get());
		add(ModBlocks.PYTHADENDRON_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.PYTHADENDRON_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		dropSelf(ModBlocks.DRAGON_TREE_SAPLING.get());
		add(ModBlocks.DRAGON_TREE_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.DRAGON_TREE_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		dropSelf(ModBlocks.TENANEA_SAPLING.get());
		add(ModBlocks.TENANEA_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.TENANEA_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		add(ModBlocks.TENANEA_FLOWERS.get(), BlockLootTables::createShearsOnlyDrop);
		add(ModBlocks.TENANEA_OUTER_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.TENANEA_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		dropSelf(ModBlocks.HELIX_TREE_SAPLING.get());
		add(ModBlocks.HELIX_TREE_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.HELIX_TREE_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		dropSelf(ModBlocks.UMBRELLA_TREE_SAPLING.get());
		dropSelf(ModBlocks.UMBRELLA_TREE_CLUSTER.get());
		dropSelf(ModBlocks.UMBRELLA_TREE_CLUSTER_EMPTY.get());
		add(ModBlocks.UMBRELLA_TREE_MEMBRANE.get(), (block) -> {
			return umbrellaTreeMembraneDrop();
		});
		dropSelf(ModBlocks.PALLIDIUM_FULL.get());
		dropSelf(ModBlocks.PALLIDIUM_HEAVY.get());
		dropSelf(ModBlocks.PALLIDIUM_THIN.get());
		dropSelf(ModBlocks.PALLIDIUM_TINY.get());

		dropSelf(ModBlocks.FLAMMALIX.get());
		dropSelf(ModBlocks.INFLEXIA.get());

		dropSelf(ModBlocks.CHARCOAL_BLOCK.get());

		dropSelf(ModBlocks.JELLYSHROOM_CAP_PURPLE.get());

		dropSelf(ModBlocks.AMARANITA_STEM.get());
		dropSelf(ModBlocks.AMARANITA_HYPHAE.get());
		dropSelf(ModBlocks.AMARANITA_HYMENOPHORE.get());
		dropSelf(ModBlocks.AMARANITA_LANTERN.get());
		dropSelf(ModBlocks.AMARANITA_CAP.get());
		add(ModBlocks.AMARANITA_FUR.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		add(ModBlocks.MOSSY_DRAGON_BONE.get(), (block) -> {
			return createSingleItemTableWithSilkTouch(block, ModBlocks.DRAGON_BONE_BLOCK.get());
		});
		dropSelf(ModBlocks.DRAGON_BONE_BLOCK.get());
		dropSelf(ModBlocks.DRAGON_BONE_SLAB.get());
		dropSelf(ModBlocks.DRAGON_BONE_STAIRS.get());

		dropSelf(ModBlocks.LUCERNIA_SAPLING.get());
		add(ModBlocks.LUCERNIA_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block,
					applyExplosionCondition(block, ItemLootEntry.lootTableItem(ModBlocks.LUCERNIA_SAPLING.get()))
							.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});
		add(ModBlocks.LUCERNIA_OUTER_LEAVES.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block, ItemLootEntry.lootTableItem(Items.AIR));
		});
		add(ModBlocks.FILALUX.get(), (block) -> {
			return createSilkTouchOrShearsDispatchTable(block, ItemLootEntry.lootTableItem(Items.AIR));
		});

		// FLOWER POT BLOCKS
		registerFlowerPotLootTables();

		// WOODEN_MATERIALS
		registerWoodenMaterialLootTables(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialLootTables(ModBlocks.LACUGROVE);
		registerWoodenMaterialLootTables(ModBlocks.END_LOTUS);
		registerWoodenMaterialLootTables(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialLootTables(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialLootTables(ModBlocks.TENANEA);
		registerWoodenMaterialLootTables(ModBlocks.HELIX_TREE);
		registerWoodenMaterialLootTables(ModBlocks.UMBRELLA_TREE);
		registerWoodenMaterialLootTables(ModBlocks.JELLYSHROOM);
		registerWoodenMaterialLootTables(ModBlocks.LUCERNIA);

		// STONE MATERIALS
		registerStoneMaterialLootTables(ModBlocks.FLAVOLITE);
		registerStoneMaterialLootTables(ModBlocks.VIOLECITE);
		registerStoneMaterialLootTables(ModBlocks.SULPHURIC_ROCK);
		registerStoneMaterialLootTables(ModBlocks.VIRID_JADESTONE);
		registerStoneMaterialLootTables(ModBlocks.AZURE_JADESTONE);
		registerStoneMaterialLootTables(ModBlocks.SANDY_JADESTONE);
		registerStoneMaterialLootTables(ModBlocks.UMBRALITH);


		// METAL MATERIALS
		registerMetalMaterialLootTables(ModBlocks.THALLASIUM);
		registerMetalMaterialLootTables(ModBlocks.TERMINITE);

		// COLORED MATERIALS
		registerColoredMaterialLootTables(ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
		registerColoredMaterialLootTables(ModBlocks.IRON_BULB_LANTERN_COLORED);
		dropSelf(ModBlocks.IRON_BULB_LANTERN.get());
	}

	private void lanceleaf() {
		ILootCondition.IBuilder lanceleafSeedCond1 = BlockStateProperty.hasBlockStateProperties((ModBlocks.LANCELEAF.get()))
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.BOTTOM));
		ILootCondition.IBuilder lanceleafSeedCond2 = BlockStateProperty.hasBlockStateProperties((ModBlocks.LANCELEAF.get()))
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.PRE_BOTTOM));
		ILootCondition.IBuilder lanceleafSeedCond3 = BlockStateProperty.hasBlockStateProperties((ModBlocks.LANCELEAF.get()))
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.MIDDLE));
		ILootCondition.IBuilder lanceleafSeedCond4 = BlockStateProperty.hasBlockStateProperties((ModBlocks.LANCELEAF.get()))
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.PRE_TOP));
		ILootCondition.IBuilder lanceleafSeedCond5 = BlockStateProperty.hasBlockStateProperties((ModBlocks.LANCELEAF.get()))
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.TOP));
		LootEntry.Builder<?> lanceleafSeedDrop = ItemLootEntry.lootTableItem(ModBlocks.LANCELEAF_SEED.get())
				.when(RandomChance.randomChance(0.5F));
		add(ModBlocks.LANCELEAF.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool().add(lanceleafSeedDrop).when(lanceleafSeedCond1))
				.withPool(LootPool.lootPool().add(lanceleafSeedDrop).when(lanceleafSeedCond2))
				.withPool(LootPool.lootPool().add(lanceleafSeedDrop).when(lanceleafSeedCond3))
				.withPool(LootPool.lootPool().add(lanceleafSeedDrop).when(lanceleafSeedCond4))
				.withPool(LootPool.lootPool().add(lanceleafSeedDrop).when(lanceleafSeedCond5)));

	}

	private void lumecorn() {
		add(ModBlocks.LUMECORN_SEED.get(), BlockLootTables::createShearsOnlyDrop);
		ILootCondition.IBuilder lumecornSeedCond1 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.BOTTOM_BIG));
		ILootCondition.IBuilder lumecornSeedCond2 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.BOTTOM_SMALL));
		ILootCondition.IBuilder lumecornSeedCond3 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.MIDDLE));
		ILootCondition.IBuilder lumecornRodCond1 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_TOP));
		ILootCondition.IBuilder lumecornRodCond2 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_TOP_MIDDLE));
		ILootCondition.IBuilder lumecornRodCond3 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_MIDDLE));
		ILootCondition.IBuilder lumecornRodCond4 = BlockStateProperty.hasBlockStateProperties(ModBlocks.LUMECORN.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_BOTTOM));
		LootEntry.Builder<?> lumecornSeedDrop = ItemLootEntry.lootTableItem(ModBlocks.LUMECORN_SEED.get())
				.when(RandomChance.randomChance(0.5F));
		LootEntry.Builder<?> lumecornRodDrop = ItemLootEntry.lootTableItem(ModItems.LUMECORN_ROD.get())
				.when(RandomChance.randomChance(0.5F));
		add(ModBlocks.LUMECORN.get(),
				LootTable.lootTable()
						.withPool(LootPool.lootPool().add(lumecornSeedDrop).when(lumecornSeedCond1))
						.withPool(LootPool.lootPool().add(lumecornSeedDrop).when(lumecornSeedCond2))
						.withPool(LootPool.lootPool().add(lumecornSeedDrop).when(lumecornSeedCond3))
						.withPool(LootPool.lootPool().add(lumecornRodDrop).when(lumecornRodCond1))
						.withPool(LootPool.lootPool().add(lumecornRodDrop).when(lumecornRodCond2))
						.withPool(LootPool.lootPool().add(lumecornRodDrop).when(lumecornRodCond3))
						.withPool(LootPool.lootPool().add(lumecornRodDrop).when(lumecornRodCond4)));
	}

	private void registerWoodenMaterialLootTables(WoodenMaterial material) {
		dropSelf(material.log.get());
		dropSelf(material.bark.get());
		dropSelf(material.log_stripped.get());
		dropSelf(material.bark_stripped.get());
		dropSelf(material.planks.get());
		dropSelf(material.stairs.get());
		add(material.slab.get(), BlockLootTables::createSlabItemTable);
		dropSelf(material.fence.get());
		dropSelf(material.gate.get());
		dropSelf(material.button.get());
		dropSelf(material.pressurePlate.get());
		dropSelf(material.trapdoor.get());
		add(material.door.get(), BlockLootTables::createDoorTable);
		dropSelf(material.composter.get());
		dropSelf(material.craftingTable.get());
		dropSelf(material.ladder.get());
		dropSelf(material.chest.get());
		dropSelf(material.sign.get());
		dropSelf(material.barrel.get());
		add(material.shelf.get(),
				createSingleItemTableWithSilkTouch(material.shelf.get(), Items.BOOK, ConstantRange.exactly(3)));
	}

	private void registerStoneMaterialLootTables(StoneMaterial material) {
		dropSelf(material.stone.get());
		dropSelf(material.polished.get());
		dropSelf(material.tiles.get());
		dropSelf(material.pillar.get());
		dropSelf(material.stairs.get());
		add(material.slab.get(), BlockLootTables::createSlabItemTable);
		dropSelf(material.wall.get());
		dropSelf(material.button.get());
		dropSelf(material.pressure_plate.get());
		dropSelf(material.bricks.get());
		dropSelf(material.brick_stairs.get());
		add(material.brick_slab.get(), BlockLootTables::createSlabItemTable);
		dropSelf(material.brick_wall.get());
		dropSelf(material.lantern.get());
		dropSelf(material.pedestal.get());
		add(material.furnace.get(), BlockLootTables::createNameableBlockEntityTable);
	}

	private void registerMetalMaterialLootTables(MetalMaterial material) {
		if (material.hasOre) {
			dropSelf(material.ore.get());
		}
		dropSelf(material.block.get());
		dropSelf(material.tile.get());
		dropSelf(material.stairs.get());
		add(material.slab.get(), BlockLootTables::createSlabItemTable);
		add(material.door.get(), BlockLootTables::createDoorTable);
		dropSelf(material.trapdoor.get());
		anvilLootTable((EndAnvilBlock) material.anvil.get());
		dropSelf(material.chain.get());
		dropSelf(material.pressure_plate.get());
		dropSelf(material.bars.get());
		dropSelf(material.chandelier.get());
		registerColoredMaterialLootTables(material.bulb_lantern_colored);
		dropSelf(material.bulb_lantern.get());
	}

	private void anvilLootTable(EndAnvilBlock block) {
		ILootCondition.IBuilder builder0 = BlockStateProperty.hasBlockStateProperties(block).setProperties(
				StatePropertiesPredicate.Builder.properties().hasProperty(EndAnvilBlock.DESTRUCTION, 0));
		ILootCondition.IBuilder builder1 = BlockStateProperty.hasBlockStateProperties(block).setProperties(
				StatePropertiesPredicate.Builder.properties().hasProperty(EndAnvilBlock.DESTRUCTION, 1));
		ILootCondition.IBuilder builder2 = BlockStateProperty.hasBlockStateProperties(block).setProperties(
				StatePropertiesPredicate.Builder.properties().hasProperty(EndAnvilBlock.DESTRUCTION, 2));
		CompoundNBT level0 = new CompoundNBT();
		level0.putInt("level", 0);
		CompoundNBT level1 = new CompoundNBT();
		level1.putInt("level", 1);
		CompoundNBT level2 = new CompoundNBT();
		level2.putInt("level", 2);
		
		add(block, LootTable.lootTable()
				.withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(block).apply(SetNBT.setTag(level0)).when(builder0)))
				.withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(block).apply(SetNBT.setTag(level1)).when(builder1)))
				.withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(block).apply(SetNBT.setTag(level2)).when(builder2))));

	}

	private void registerColoredMaterialLootTables(ColoredMaterial material) {
		for (Block block : material.getBlocks()) {
			dropSelf(block);
		}
	}

	private void registerFlowerPotLootTables() {
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof FlowerPotBlock) {
				dropPottedContents(block);
			}
		});
	}

	private static LootTable.Builder endLilyDrop() {
		LootEntry.Builder<?> leaf_drop = ItemLootEntry.lootTableItem(ModItems.END_LILY_LEAF.get())
				.apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)));
		LootEntry.Builder<?> seed_drop = ItemLootEntry.lootTableItem(ModBlocks.END_LILY_SEED.get())
				.apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)));
		LootPool.Builder top_loot_leaf = LootPool.lootPool().add(leaf_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.END_LILY.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(EndLilyBlock.SHAPE, TripleShape.TOP)));
		LootPool.Builder top_loot_seed = LootPool.lootPool().add(seed_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.END_LILY.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(EndLilyBlock.SHAPE, TripleShape.TOP)));

		return LootTable.lootTable().withPool(top_loot_leaf).withPool(top_loot_seed);
	}

	private static LootTable.Builder bulbVineDrop() {
		LootEntry.Builder<?> bulb_drop = ItemLootEntry.lootTableItem(ModItems.GLOWING_BULB.get());
		LootEntry.Builder<?> seed_drop = ItemLootEntry.lootTableItem(ModBlocks.BULB_VINE_SEED.get())
				.when(RandomChance.randomChance(0.125F));

		LootPool.Builder bottom_loot = LootPool.lootPool().add(bulb_drop).when(
				BlockStateProperty.hasBlockStateProperties(ModBlocks.BULB_VINE.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(BulbVineBlock.SHAPE, TripleShape.BOTTOM)));
		LootPool.Builder middle_loot = LootPool.lootPool().add(seed_drop).when(
				BlockStateProperty.hasBlockStateProperties(ModBlocks.BULB_VINE.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(BulbVineBlock.SHAPE, TripleShape.MIDDLE)));
		LootPool.Builder top_loot = LootPool.lootPool().add(bulb_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.BULB_VINE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(BulbVineBlock.SHAPE, TripleShape.TOP)));

		return LootTable.lootTable().withPool(bottom_loot).withPool(middle_loot).withPool(top_loot);
	}

	private static LootTable.Builder hydraluxDrop() {
		LootEntry.Builder<?> petal_drop = ItemLootEntry.lootTableItem(ModItems.HYDRALUX_PETAL.get())
				.apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)));
		LootEntry.Builder<?> roots_drop = ItemLootEntry.lootTableItem(ModBlocks.HYDRALUX_SAPLING.get())
				.apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)));

		LootPool.Builder small_flower_loot = LootPool.lootPool().add(petal_drop).when(
				BlockStateProperty.hasBlockStateProperties(ModBlocks.HYDRALUX.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(HydraluxBlock.SHAPE, HydraluxShape.FLOWER_SMALL_BOTTOM)));
		LootPool.Builder big_flower_loot = LootPool.lootPool().add(petal_drop).when(
				BlockStateProperty.hasBlockStateProperties(ModBlocks.HYDRALUX.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(HydraluxBlock.SHAPE, HydraluxShape.FLOWER_BIG_BOTTOM)));
		LootPool.Builder roots_loot = LootPool.lootPool().add(roots_drop).when(
				BlockStateProperty.hasBlockStateProperties(ModBlocks.HYDRALUX.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(HydraluxBlock.SHAPE, HydraluxShape.ROOTS)));

		return LootTable.lootTable().withPool(small_flower_loot).withPool(big_flower_loot).withPool(roots_loot);
	}

	private static LootTable.Builder umbrellaTreeMembraneDrop() {
		LootEntry.Builder<?> block_drop = ItemLootEntry.lootTableItem(ModBlocks.UMBRELLA_TREE_MEMBRANE.get());
		LootEntry.Builder<?> sapling_drop = ItemLootEntry.lootTableItem(ModBlocks.UMBRELLA_TREE_SAPLING.get())
				.when(RandomChance.randomChance(0.25F));

		LootPool.Builder color_0_loot = LootPool.lootPool().add(sapling_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 0)));
		LootPool.Builder color_1_loot = LootPool.lootPool().add(block_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 1)));
		LootPool.Builder color_2_loot = LootPool.lootPool().add(block_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 2)));
		LootPool.Builder color_3_loot = LootPool.lootPool().add(block_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 3)));
		LootPool.Builder color_4_loot = LootPool.lootPool().add(block_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 4)));
		LootPool.Builder color_5_loot = LootPool.lootPool().add(block_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 5)));
		LootPool.Builder color_6_loot = LootPool.lootPool().add(block_drop)
				.when(BlockStateProperty.hasBlockStateProperties(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).setProperties(
						StatePropertiesPredicate.Builder.properties().hasProperty(UmbrellaTreeMembraneBlock.COLOR, 6)));

		return LootTable.lootTable().withPool(color_0_loot).withPool(color_1_loot).withPool(color_2_loot)
				.withPool(color_3_loot).withPool(color_4_loot).withPool(color_5_loot)
				.withPool(color_6_loot);
	}

	// Need to improve
	private static LootTable.Builder sulphurCrystalDrop(Block block, Item drop) { // .rolls(ConstantRange.of(1)).
		return LootTable.lootTable()
				.withPool(applyExplosionDecay(block, LootPool.lootPool().add(ItemLootEntry.lootTableItem(drop)
						.apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F)).when(
								BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder
										.properties().hasProperty(SulphurCrystalBlock.AGE, 3)))))));
	}
}
