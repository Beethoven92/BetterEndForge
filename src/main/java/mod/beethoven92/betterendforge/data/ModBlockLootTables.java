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

		registerLootTable(ModBlocks.RESPAWN_OBELISK.get(), (block) -> {
			return droppingWhen(block, RespawnObeliskBlock.SHAPE, TripleShape.BOTTOM);
		});

		registerDropSelfLootTable(ModBlocks.HYDRALUX_PETAL_BLOCK.get());

		registerDropSelfLootTable(ModBlocks.DENSE_SNOW.get());
		registerSilkTouch(ModBlocks.EMERALD_ICE.get());
		registerDropSelfLootTable(ModBlocks.DENSE_EMERALD_ICE.get());
		registerDropSelfLootTable(ModBlocks.ANCIENT_EMERALD_ICE.get());

		registerDropSelfLootTable(ModBlocks.IRON_CHANDELIER.get());
		registerDropSelfLootTable(ModBlocks.GOLD_CHANDELIER.get());

		registerDropSelfLootTable(ModBlocks.MISSING_TILE.get());

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
		registerLootTable(ModBlocks.AMBER_MOSS.get(), (terrain) -> {
			return droppingWithSilkTouch(terrain, Blocks.END_STONE);
		});
		registerLootTable(ModBlocks.JUNGLE_MOSS.get(), (terrain) -> {
			return droppingWithSilkTouch(terrain, Blocks.END_STONE);
		});
		registerLootTable(ModBlocks.SANGNUM.get(), (terrain) -> {
			return droppingWithSilkTouch(terrain, Blocks.END_STONE);
		});
		registerLootTable(ModBlocks.RUTISCUS.get(), (terrain) -> {
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
		registerDropping(ModBlocks.AMBER_MOSS_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.JUNGLE_MOSS_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.SANGNUM_PATH.get(), Blocks.END_STONE);
		registerDropping(ModBlocks.RUTISCUS_PATH.get(), Blocks.END_STONE);

		registerLootTable(ModBlocks.MOSSY_OBSIDIAN.get(), (block) -> {
			return droppingWithSilkTouch(block, Blocks.OBSIDIAN);
		});

		// MATERIALS
		registerDropSelfLootTable(ModBlocks.AETERNIUM_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.ENDER_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.AMBER_BLOCK.get());
		registerLootTable(ModBlocks.AURORA_CRYSTAL.get(), (block) -> {
			return droppingItemWithFortune(block, ModItems.CRYSTAL_SHARDS.get());
		});
		registerDropSelfLootTable(ModBlocks.SMARAGDANT_CRYSTAL.get());
		registerDropSelfLootTable(ModBlocks.SMARAGDANT_CRYSTAL_SHARD.get());

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
		registerDropSelfLootTable(ModBlocks.ANDESITE_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.DIORITE_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.GRANITE_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.ETERNAL_PEDESTAL.get());
		registerDropSelfLootTable(ModBlocks.INFUSION_PEDESTAL.get());

		registerDropSelfLootTable(ModBlocks.ANDESITE_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.DIORITE_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.GRANITE_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.QUARTZ_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.PURPUR_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.END_STONE_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.BLACKSTONE_LANTERN.get());

		registerDropSelfLootTable(ModBlocks.BRIMSTONE.get());

		registerDropSelfLootTable(ModBlocks.HYDROTHERMAL_VENT.get());

		registerLootTable(ModBlocks.SULPHUR_CRYSTAL.get(), (block) -> {
			return sulphurCrystalDrop(block, ModItems.CRYSTALLINE_SULPHUR.get());
		});

		registerLootTable(ModBlocks.END_STONE_SMELTER.get(), BlockLootTables::droppingWithName);
		registerLootTable(ModBlocks.END_STONE_FURNACE.get(), BlockLootTables::droppingWithName);

		registerDropSelfLootTable(ModBlocks.END_STONE_STALACTITE.get());
		registerDropSelfLootTable(ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get());

		// PLANTS
		registerLootTable(ModBlocks.UMBRELLA_MOSS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.UMBRELLA_MOSS_TALL.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CREEPING_MOSS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CHORUS_GRASS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CAVE_GRASS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CRYSTAL_GRASS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.AMBER_GRASS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.SHADOW_PLANT.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.BUSHY_GRASS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.TWISTED_UMBRELLA_MOSS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.JUNGLE_GRASS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.BLOOMING_COOKSONIA.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.SALTEAGO.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.VAIOLUSH_FERN.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.FRACTURN.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.LARGE_AMARANITA_MUSHROOM.get(), blockNoDrop());
		registerLootTable(ModBlocks.SMALL_AMARANITA_MUSHROOM.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.GLOBULAGUS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CLAWFERN.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.AERIDIUM.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.LAMELLARIUM.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.POND_ANEMONE.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.RUSCUS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.ORANGO.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.LUTEBUS.get(), BlockLootTables::onlyWithShears);
		registerDropSelfLootTable(ModBlocks.FLAMAEA.get());
		registerDropSelfLootTable(ModBlocks.BOLUX_MUSHROOM.get());
		registerDropSelfLootTable(ModBlocks.AURANT_POLYPORE.get());
		registerDropSelfLootTable(ModBlocks.NEON_CACTUS.get());
		registerDropSelfLootTable(ModBlocks.NEON_CACTUS_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get());
		registerDropSelfLootTable(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get());

		registerLootTable(ModBlocks.BLUE_VINE_SEED.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.BLUE_VINE.get(), BlockLootTables::onlyWithShears);
		registerDropSelfLootTable(ModBlocks.BLUE_VINE_LANTERN.get());
		registerLootTable(ModBlocks.BLUE_VINE_FUR.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.BLUE_VINE_SEED.get())).acceptCondition(
							TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
		});

		registerLootTable(ModBlocks.CAVE_BUSH.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.END_LILY_SEED.get(), BlockLootTables::onlyWithShears);

		this.registerLootTable(ModBlocks.END_LILY.get(), (block) -> {
			return endLilyDrop();
		});

		registerLootTable(ModBlocks.END_LOTUS_SEED.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.END_LOTUS_LEAF.get(), BlockLootTables::onlyWithShears);
		registerDropSelfLootTable(ModBlocks.END_LOTUS_STEM.get());
		registerLootTable(ModBlocks.END_LOTUS_FLOWER.get(),
				droppingRandomly(ModBlocks.END_LOTUS_SEED.get(), RandomValueRange.of(1.0F, 2.0F)));

		registerLootTable(ModBlocks.BUBBLE_CORAL.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.MURKWEED.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.NEEDLEGRASS.get(), (block) -> {
			return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.builder(Items.STICK)
					.acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))));
		});

		registerDropSelfLootTable(ModBlocks.MENGER_SPONGE.get());
		registerDropSelfLootTable(ModBlocks.MENGER_SPONGE_WET.get());

		registerLootTable(ModBlocks.CHARNIA_RED.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CHARNIA_PURPLE.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CHARNIA_ORANGE.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CHARNIA_LIGHT_BLUE.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CHARNIA_CYAN.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.CHARNIA_GREEN.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.HYDRALUX_SAPLING.get(), BlockLootTables::onlyWithShears);
		this.registerLootTable(ModBlocks.HYDRALUX.get(), (block) -> {
			return hydraluxDrop();
		});

		registerLootTable(ModBlocks.LANCELEAF_SEED.get(), BlockLootTables::onlyWithShears);
		lanceleaf();

		lumecorn();

		registerLootTable(ModBlocks.GLOWING_PILLAR_SEED.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.GLOWING_PILLAR_ROOTS.get(), (a) -> BlockLootTables.blockNoDrop());
		registerDropSelfLootTable(ModBlocks.GLOWING_PILLAR_LUMINOPHOR.get());
		registerLootTable(ModBlocks.GLOWING_PILLAR_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.GLOWING_PILLAR_SEED.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerLootTable(ModBlocks.SMALL_JELLYSHROOM.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.SILK_MOTH_NEST.get(), (block) -> {
			return LootTable.builder()
					.addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1))
							.addEntry(ItemLootEntry.builder(block).acceptCondition(
									BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder
											.newBuilder().withBoolProp(SilkMothNestBlock.ACTIVE, true))))));
		});

		// SKY PLANTS
		registerDropSelfLootTable(ModBlocks.FILALUX_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.FILALUX_WINGS.get());

		// CROPS
		ILootCondition.IBuilder ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.SHADOW_BERRY.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(ShadowBerryBlock.AGE, 3));
		registerLootTable(ModBlocks.SHADOW_BERRY.get(), droppingAndBonusWhen(ModBlocks.SHADOW_BERRY.get(),
				ModItems.SHADOW_BERRY_RAW.get(), ModBlocks.SHADOW_BERRY.get().asItem(), ilootcondition$ibuilder));

		ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.BLOSSOM_BERRY.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
		registerLootTable(ModBlocks.BLOSSOM_BERRY.get(), droppingAndBonusWhen(ModBlocks.BLOSSOM_BERRY.get(),
				ModItems.BLOSSOM_BERRY.get(), ModBlocks.BLOSSOM_BERRY.get().asItem(), ilootcondition$ibuilder));

		ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.AMBER_ROOT.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
		registerLootTable(ModBlocks.AMBER_ROOT.get(), droppingAndBonusWhen(ModBlocks.AMBER_ROOT.get(),
				ModItems.AMBER_ROOT_RAW.get(), ModBlocks.AMBER_ROOT.get().asItem(), ilootcondition$ibuilder));

		ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.CHORUS_MUSHROOM.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
		registerLootTable(ModBlocks.CHORUS_MUSHROOM.get(), droppingAndBonusWhen(ModBlocks.CHORUS_MUSHROOM.get(),
				ModItems.CHORUS_MUSHROOM_RAW.get(), ModBlocks.CHORUS_MUSHROOM.get().asItem(), ilootcondition$ibuilder));

//	    ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.PEARLBERRY.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
//	    registerLootTable(ModBlocks.PEARLBERRY.get(), droppingAndBonusWhen(ModBlocks.PEARLBERRY.get(), ModItems.BLOSSOM_BERRY.get(), ModBlocks.PEARLBERRY.get().asItem(), ilootcondition$ibuilder));

		// WALL_PLANTS
		registerDropSelfLootTable(ModBlocks.PURPLE_POLYPORE.get());

		registerLootTable(ModBlocks.TAIL_MOSS.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.CYAN_MOSS.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.TWISTED_MOSS.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.BULB_MOSS.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.TUBE_WORM.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.JUNGLE_FERN.get(), BlockLootTables::onlyWithShears);

		// VINES
		registerLootTable(ModBlocks.DENSE_VINE.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.TWISTED_VINE.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.BULB_VINE.get(), (block) -> {
			return bulbVineDrop();
		});
		registerLootTable(ModBlocks.BULB_VINE_SEED.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.JUNGLE_VINE.get(), BlockLootTables::onlyWithShears);

		registerLootTable(ModBlocks.RUBINEA.get(), BlockLootTables::onlyWithShears);

		// TREES
		registerDropSelfLootTable(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get());
		registerDropSelfLootTable(ModBlocks.MOSSY_GLOWSHROOM_CAP.get());
		registerDropSelfLootTable(ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE.get());
		registerLootTable(ModBlocks.MOSSY_GLOWSHROOM_FUR.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerDropSelfLootTable(ModBlocks.LACUGROVE_SAPLING.get());
		registerLootTable(ModBlocks.LACUGROVE_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.LACUGROVE_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerDropSelfLootTable(ModBlocks.PYTHADENDRON_SAPLING.get());
		registerLootTable(ModBlocks.PYTHADENDRON_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.PYTHADENDRON_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerDropSelfLootTable(ModBlocks.DRAGON_TREE_SAPLING.get());
		registerLootTable(ModBlocks.DRAGON_TREE_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.DRAGON_TREE_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerDropSelfLootTable(ModBlocks.TENANEA_SAPLING.get());
		registerLootTable(ModBlocks.TENANEA_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.TENANEA_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerLootTable(ModBlocks.TENANEA_FLOWERS.get(), BlockLootTables::onlyWithShears);
		registerLootTable(ModBlocks.TENANEA_OUTER_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.TENANEA_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerDropSelfLootTable(ModBlocks.HELIX_TREE_SAPLING.get());
		registerLootTable(ModBlocks.HELIX_TREE_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.HELIX_TREE_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerDropSelfLootTable(ModBlocks.UMBRELLA_TREE_SAPLING.get());
		registerDropSelfLootTable(ModBlocks.UMBRELLA_TREE_CLUSTER.get());
		registerDropSelfLootTable(ModBlocks.UMBRELLA_TREE_CLUSTER_EMPTY.get());
		registerLootTable(ModBlocks.UMBRELLA_TREE_MEMBRANE.get(), (block) -> {
			return umbrellaTreeMembraneDrop();
		});
		registerDropSelfLootTable(ModBlocks.PALLIDIUM_FULL.get());
		registerDropSelfLootTable(ModBlocks.PALLIDIUM_HEAVY.get());
		registerDropSelfLootTable(ModBlocks.PALLIDIUM_THIN.get());
		registerDropSelfLootTable(ModBlocks.PALLIDIUM_TINY.get());

		registerDropSelfLootTable(ModBlocks.FLAMMALIX.get());
		registerDropSelfLootTable(ModBlocks.INFLEXIA.get());

		registerDropSelfLootTable(ModBlocks.CHARCOAL_BLOCK.get());

		registerDropSelfLootTable(ModBlocks.JELLYSHROOM_CAP_PURPLE.get());

		registerDropSelfLootTable(ModBlocks.AMARANITA_STEM.get());
		registerDropSelfLootTable(ModBlocks.AMARANITA_HYPHAE.get());
		registerDropSelfLootTable(ModBlocks.AMARANITA_HYMENOPHORE.get());
		registerDropSelfLootTable(ModBlocks.AMARANITA_LANTERN.get());
		registerDropSelfLootTable(ModBlocks.AMARANITA_CAP.get());
		registerLootTable(ModBlocks.AMARANITA_FUR.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});

		registerLootTable(ModBlocks.MOSSY_DRAGON_BONE.get(), (block) -> {
			return droppingWithSilkTouch(block, ModBlocks.DRAGON_BONE_BLOCK.get());
		});
		registerDropSelfLootTable(ModBlocks.DRAGON_BONE_BLOCK.get());
		registerDropSelfLootTable(ModBlocks.DRAGON_BONE_SLAB.get());
		registerDropSelfLootTable(ModBlocks.DRAGON_BONE_STAIRS.get());

		registerDropSelfLootTable(ModBlocks.LUCERNIA_SAPLING.get());
		registerLootTable(ModBlocks.LUCERNIA_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block,
					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.LUCERNIA_SAPLING.get()))
							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
									0.083333336F, 0.1F)));
		});
		registerLootTable(ModBlocks.LUCERNIA_OUTER_LEAVES.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(Items.AIR));
		});
		registerLootTable(ModBlocks.FILALUX.get(), (block) -> {
			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(Items.AIR));
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
		registerDropSelfLootTable(ModBlocks.IRON_BULB_LANTERN.get());
	}

	private void lanceleaf() {
		ILootCondition.IBuilder lanceleafSeedCond1 = BlockStateProperty.builder((ModBlocks.LANCELEAF.get()))
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.BOTTOM));
		ILootCondition.IBuilder lanceleafSeedCond2 = BlockStateProperty.builder((ModBlocks.LANCELEAF.get()))
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.PRE_BOTTOM));
		ILootCondition.IBuilder lanceleafSeedCond3 = BlockStateProperty.builder((ModBlocks.LANCELEAF.get()))
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.MIDDLE));
		ILootCondition.IBuilder lanceleafSeedCond4 = BlockStateProperty.builder((ModBlocks.LANCELEAF.get()))
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.PRE_TOP));
		ILootCondition.IBuilder lanceleafSeedCond5 = BlockStateProperty.builder((ModBlocks.LANCELEAF.get()))
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
						BlockProperties.PentaShape.TOP));
		LootEntry.Builder<?> lanceleafSeedDrop = ItemLootEntry.builder(ModBlocks.LANCELEAF_SEED.get())
				.acceptCondition(RandomChance.builder(0.5F));
		registerLootTable(ModBlocks.LANCELEAF.get(), LootTable.builder()
				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond1))
				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond2))
				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond3))
				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond4))
				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond5)));

	}

	private void lumecorn() {
		registerLootTable(ModBlocks.LUMECORN_SEED.get(), BlockLootTables::onlyWithShears);
		ILootCondition.IBuilder lumecornSeedCond1 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.BOTTOM_BIG));
		ILootCondition.IBuilder lumecornSeedCond2 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.BOTTOM_SMALL));
		ILootCondition.IBuilder lumecornSeedCond3 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.MIDDLE));
		ILootCondition.IBuilder lumecornRodCond1 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_TOP));
		ILootCondition.IBuilder lumecornRodCond2 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_TOP_MIDDLE));
		ILootCondition.IBuilder lumecornRodCond3 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_MIDDLE));
		ILootCondition.IBuilder lumecornRodCond4 = BlockStateProperty.builder(ModBlocks.LUMECORN.get())
				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
						BlockProperties.LumecornShape.LIGHT_BOTTOM));
		LootEntry.Builder<?> lumecornSeedDrop = ItemLootEntry.builder(ModBlocks.LUMECORN_SEED.get())
				.acceptCondition(RandomChance.builder(0.5F));
		LootEntry.Builder<?> lumecornRodDrop = ItemLootEntry.builder(ModItems.LUMECORN_ROD.get())
				.acceptCondition(RandomChance.builder(0.5F));
		registerLootTable(ModBlocks.LUMECORN.get(),
				LootTable.builder()
						.addLootPool(LootPool.builder().addEntry(lumecornSeedDrop).acceptCondition(lumecornSeedCond1))
						.addLootPool(LootPool.builder().addEntry(lumecornSeedDrop).acceptCondition(lumecornSeedCond2))
						.addLootPool(LootPool.builder().addEntry(lumecornSeedDrop).acceptCondition(lumecornSeedCond3))
						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond1))
						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond2))
						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond3))
						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond4)));
	}

	private void registerWoodenMaterialLootTables(WoodenMaterial material) {
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
		registerDropSelfLootTable(material.composter.get());
		registerDropSelfLootTable(material.craftingTable.get());
		registerDropSelfLootTable(material.ladder.get());
		registerDropSelfLootTable(material.chest.get());
		registerDropSelfLootTable(material.sign.get());
		registerDropSelfLootTable(material.barrel.get());
		registerLootTable(material.shelf.get(),
				droppingWithSilkTouchOrRandomly(material.shelf.get(), Items.BOOK, ConstantRange.of(3)));
	}

	private void registerStoneMaterialLootTables(StoneMaterial material) {
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
		registerDropSelfLootTable(material.lantern.get());
		registerDropSelfLootTable(material.pedestal.get());
		registerLootTable(material.furnace.get(), BlockLootTables::droppingWithName);
	}

	private void registerMetalMaterialLootTables(MetalMaterial material) {
		if (material.hasOre) {
			registerDropSelfLootTable(material.ore.get());
		}
		registerDropSelfLootTable(material.block.get());
		registerDropSelfLootTable(material.tile.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerLootTable(material.door.get(), BlockLootTables::registerDoor);
		registerDropSelfLootTable(material.trapdoor.get());
		anvilLootTable((EndAnvilBlock) material.anvil.get());
		registerDropSelfLootTable(material.chain.get());
		registerDropSelfLootTable(material.pressure_plate.get());
		registerDropSelfLootTable(material.bars.get());
		registerDropSelfLootTable(material.chandelier.get());
		registerColoredMaterialLootTables(material.bulb_lantern_colored);
		registerDropSelfLootTable(material.bulb_lantern.get());
	}

	private void anvilLootTable(EndAnvilBlock block) {
		ILootCondition.IBuilder builder0 = BlockStateProperty.builder(block).fromProperties(
				StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndAnvilBlock.DESTRUCTION, 0));
		ILootCondition.IBuilder builder1 = BlockStateProperty.builder(block).fromProperties(
				StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndAnvilBlock.DESTRUCTION, 1));
		ILootCondition.IBuilder builder2 = BlockStateProperty.builder(block).fromProperties(
				StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndAnvilBlock.DESTRUCTION, 2));
		CompoundNBT level0 = new CompoundNBT();
		level0.putInt("level", 0);
		CompoundNBT level1 = new CompoundNBT();
		level1.putInt("level", 1);
		CompoundNBT level2 = new CompoundNBT();
		level2.putInt("level", 2);
		
		registerLootTable(block, LootTable.builder()
				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(block).acceptFunction(SetNBT.builder(level0)).acceptCondition(builder0)))
				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(block).acceptFunction(SetNBT.builder(level1)).acceptCondition(builder1)))
				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(block).acceptFunction(SetNBT.builder(level2)).acceptCondition(builder2))));

	}

	private void registerColoredMaterialLootTables(ColoredMaterial material) {
		for (Block block : material.getBlocks()) {
			registerDropSelfLootTable(block);
		}
	}

	private void registerFlowerPotLootTables() {
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof FlowerPotBlock) {
				registerFlowerPot(block);
			}
		});
	}

	private static LootTable.Builder endLilyDrop() {
		LootEntry.Builder<?> leaf_drop = ItemLootEntry.builder(ModItems.END_LILY_LEAF.get())
				.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)));
		LootEntry.Builder<?> seed_drop = ItemLootEntry.builder(ModBlocks.END_LILY_SEED.get())
				.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)));
		LootPool.Builder top_loot_leaf = LootPool.builder().addEntry(leaf_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.END_LILY.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withProp(EndLilyBlock.SHAPE, TripleShape.TOP)));
		LootPool.Builder top_loot_seed = LootPool.builder().addEntry(seed_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.END_LILY.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withProp(EndLilyBlock.SHAPE, TripleShape.TOP)));

		return LootTable.builder().addLootPool(top_loot_leaf).addLootPool(top_loot_seed);
	}

	private static LootTable.Builder bulbVineDrop() {
		LootEntry.Builder<?> bulb_drop = ItemLootEntry.builder(ModItems.GLOWING_BULB.get());
		LootEntry.Builder<?> seed_drop = ItemLootEntry.builder(ModBlocks.BULB_VINE_SEED.get())
				.acceptCondition(RandomChance.builder(0.125F));

		LootPool.Builder bottom_loot = LootPool.builder().addEntry(bulb_drop).acceptCondition(
				BlockStateProperty.builder(ModBlocks.BULB_VINE.get()).fromProperties(StatePropertiesPredicate.Builder
						.newBuilder().withProp(BulbVineBlock.SHAPE, TripleShape.BOTTOM)));
		LootPool.Builder middle_loot = LootPool.builder().addEntry(seed_drop).acceptCondition(
				BlockStateProperty.builder(ModBlocks.BULB_VINE.get()).fromProperties(StatePropertiesPredicate.Builder
						.newBuilder().withProp(BulbVineBlock.SHAPE, TripleShape.MIDDLE)));
		LootPool.Builder top_loot = LootPool.builder().addEntry(bulb_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.BULB_VINE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withProp(BulbVineBlock.SHAPE, TripleShape.TOP)));

		return LootTable.builder().addLootPool(bottom_loot).addLootPool(middle_loot).addLootPool(top_loot);
	}

	private static LootTable.Builder hydraluxDrop() {
		LootEntry.Builder<?> petal_drop = ItemLootEntry.builder(ModItems.HYDRALUX_PETAL.get())
				.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F)));
		LootEntry.Builder<?> roots_drop = ItemLootEntry.builder(ModBlocks.HYDRALUX_SAPLING.get())
				.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)));

		LootPool.Builder small_flower_loot = LootPool.builder().addEntry(petal_drop).acceptCondition(
				BlockStateProperty.builder(ModBlocks.HYDRALUX.get()).fromProperties(StatePropertiesPredicate.Builder
						.newBuilder().withProp(HydraluxBlock.SHAPE, HydraluxShape.FLOWER_SMALL_BOTTOM)));
		LootPool.Builder big_flower_loot = LootPool.builder().addEntry(petal_drop).acceptCondition(
				BlockStateProperty.builder(ModBlocks.HYDRALUX.get()).fromProperties(StatePropertiesPredicate.Builder
						.newBuilder().withProp(HydraluxBlock.SHAPE, HydraluxShape.FLOWER_BIG_BOTTOM)));
		LootPool.Builder roots_loot = LootPool.builder().addEntry(roots_drop).acceptCondition(
				BlockStateProperty.builder(ModBlocks.HYDRALUX.get()).fromProperties(StatePropertiesPredicate.Builder
						.newBuilder().withProp(HydraluxBlock.SHAPE, HydraluxShape.ROOTS)));

		return LootTable.builder().addLootPool(small_flower_loot).addLootPool(big_flower_loot).addLootPool(roots_loot);
	}

	private static LootTable.Builder umbrellaTreeMembraneDrop() {
		LootEntry.Builder<?> block_drop = ItemLootEntry.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get());
		LootEntry.Builder<?> sapling_drop = ItemLootEntry.builder(ModBlocks.UMBRELLA_TREE_SAPLING.get())
				.acceptCondition(RandomChance.builder(0.25F));

		LootPool.Builder color_0_loot = LootPool.builder().addEntry(sapling_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 0)));
		LootPool.Builder color_1_loot = LootPool.builder().addEntry(block_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 1)));
		LootPool.Builder color_2_loot = LootPool.builder().addEntry(block_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 2)));
		LootPool.Builder color_3_loot = LootPool.builder().addEntry(block_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 3)));
		LootPool.Builder color_4_loot = LootPool.builder().addEntry(block_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 4)));
		LootPool.Builder color_5_loot = LootPool.builder().addEntry(block_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 5)));
		LootPool.Builder color_6_loot = LootPool.builder().addEntry(block_drop)
				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE.get()).fromProperties(
						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 6)));

		return LootTable.builder().addLootPool(color_0_loot).addLootPool(color_1_loot).addLootPool(color_2_loot)
				.addLootPool(color_3_loot).addLootPool(color_4_loot).addLootPool(color_5_loot)
				.addLootPool(color_6_loot);
	}

	// Need to improve
	private static LootTable.Builder sulphurCrystalDrop(Block block, Item drop) { // .rolls(ConstantRange.of(1)).
		return LootTable.builder()
				.addLootPool(withExplosionDecay(block, LootPool.builder().addEntry(ItemLootEntry.builder(drop)
						.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)).acceptCondition(
								BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder
										.newBuilder().withIntProp(SulphurCrystalBlock.AGE, 3)))))));
	}
}
