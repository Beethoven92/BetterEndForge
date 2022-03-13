package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class ModConfiguredFeatures 
{
	// WATER PLANTS
	public static final ConfiguredFeature<?, ?> END_LOTUS = 
			ModFeatures.END_LOTUS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> END_LOTUS_LEAF = 
			ModFeatures.END_LOTUS_LEAF.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(25));
	
	public static final ConfiguredFeature<?, ?> BUBBLE_CORAL = 
			ModFeatures.BUBBLE_CORAL.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> BUBBLE_CORAL_RARE = 
			ModFeatures.BUBBLE_CORAL_RARE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(4));
	
	public static final ConfiguredFeature<?, ?> END_LILY = 
			ModFeatures.END_LILY.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> END_LILY_RARE = 
			ModFeatures.END_LILY_RARE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(4));
	
	public static final ConfiguredFeature<?, ?> MENGER_SPONGE = 
			ModFeatures.MENGER_SPONGE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_RED = 
			ModFeatures.CHARNIA_RED.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_PURPLE = 
			ModFeatures.CHARNIA_PURPLE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_ORANGE = 
			ModFeatures.CHARNIA_ORANGE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_LIGHT_BLUE = 
			ModFeatures.CHARNIA_LIGHT_BLUE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_CYAN = 
			ModFeatures.CHARNIA_CYAN.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_GREEN = 
			ModFeatures.CHARNIA_GREEN.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_RED_RARE = 
			ModFeatures.CHARNIA_RED_RARE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(2));
	
	public static final ConfiguredFeature<?, ?> HYDRALUX = 
			ModFeatures.HYDRALUX.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> FLAMAEA = 
			ModFeatures.FLAMAEA.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(20);
	
	public static final ConfiguredFeature<?, ?> POND_ANEMONE = 
			ModFeatures.POND_ANEMONE.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(10);

	
	// BUSHES
	public static final ConfiguredFeature<?, ?> PYTHADENDRON_BUSH = 
			ModFeatures.PYTHADENDRON_BUSH.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> DRAGON_TREE_BUSH = 
			ModFeatures.DRAGON_TREE_BUSH.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> LARGE_AMARANITA = 
			ModFeatures.LARGE_AMARANITA.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> TENANEA_BUSH = 
			ModFeatures.TENANEA_BUSH.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> LUCERNIA_BUSH = 
			ModFeatures.LUCERNIA_BUSH.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(10);

	public static final ConfiguredFeature<?, ?> LUCERNIA_BUSH_RARE =
			ModFeatures.LUCERNIA_BUSH_RARE.configured(IFeatureConfig.NONE).
					decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(1);
	
	public static final ConfiguredFeature<?, ?> NEON_CACTUS = 
			ModFeatures.NEON_CACTUS.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(2);

	
	// PLANTS
	public static final ConfiguredFeature<?, ?> UMBRELLA_MOSS = 
			ModFeatures.UMBRELLA_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> CREEPING_MOSS = 
			ModFeatures.CREEPING_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> CHORUS_GRASS = 
			ModFeatures.CHORUS_GRASS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));

	public static final ConfiguredFeature<?, ?> CHORUS_MUSHROOM =
			ModFeatures.CHORUS_MUSHROOM.configured(IFeatureConfig.NONE).
					decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));

	public static final ConfiguredFeature<?, ?> CRYSTAL_GRASS = 
			ModFeatures.CRYSTAL_GRASS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> AMBER_GRASS = 
			ModFeatures.AMBER_GRASS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(9));

	public static final ConfiguredFeature<?, ?> AMBER_ROOT =
			ModFeatures.AMBER_ROOT.configured(IFeatureConfig.NONE).
					decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));

	public static final ConfiguredFeature<?, ?> SHADOW_PLANT = 
			ModFeatures.SHADOW_PLANT.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(9));
	
	public static final ConfiguredFeature<?, ?> BLUE_VINE = 
			ModFeatures.BLUE_VINE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));
	
	public static final ConfiguredFeature<?, ?> MURKWEED = 
			ModFeatures.MURKWEED.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(2));
	
	public static final ConfiguredFeature<?, ?> NEEDLEGRASS = 
			ModFeatures.NEEDLEGRASS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(2));
	
	public static final ConfiguredFeature<?, ?> SHADOW_BERRY = 
			ModFeatures.SHADOW_BERRY.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));
	
	public static final ConfiguredFeature<?, ?> BUSHY_GRASS = 
			ModFeatures.BUSHY_GRASS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(20));
	
	public static final ConfiguredFeature<?, ?> BUSHY_GRASS_WG = 
			ModFeatures.BUSHY_GRASS_WG.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> LANCELEAF = 
			ModFeatures.LANCELEAF.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(3));
	
	public static final ConfiguredFeature<?, ?> GLOW_PILLAR = 
			ModFeatures.GLOW_PILLAR.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));
	
	public static final ConfiguredFeature<?, ?> TWISTED_UMBRELLA_MOSS = 
			ModFeatures.TWISTED_UMBRELLA_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> JUNGLE_GRASS = 
			ModFeatures.JUNGLE_GRASS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(8));
	
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_FLOOR = 
			ModFeatures.SMALL_JELLYSHROOM_FLOOR.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(4));
	
	public static final ConfiguredFeature<?, ?> BLOSSOM_BERRY = 
			ModFeatures.BLOSSOM_BERRY.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(2));
	
	public static final ConfiguredFeature<?, ?> LUMECORN = 
			ModFeatures.LUMECORN.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> BLOOMING_COOKSONIA = 
			ModFeatures.BLOOMING_COOKSONIA.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> SALTEAGO = 
			ModFeatures.SALTEAGO.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> VAIOLUSH_FERN = 
			ModFeatures.VAIOLUSH_FERN.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> FRACTURN = 
			ModFeatures.FRACTURN.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> UMBRELLA_MOSS_RARE = 
			ModFeatures.UMBRELLA_MOSS_RARE.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(2);
	
	public static final ConfiguredFeature<?, ?> CREEPING_MOSS_RARE = 
			ModFeatures.CREEPING_MOSS_RARE.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(2);
	
	public static final ConfiguredFeature<?, ?> TWISTED_UMBRELLA_MOSS_RARE = 
			ModFeatures.TWISTED_UMBRELLA_MOSS_RARE.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(2);
	
	public static final ConfiguredFeature<?, ?> SMALL_AMARANITA = 
			ModFeatures.SMALL_AMARANITA.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(4);
	
	public static final ConfiguredFeature<?, ?> GLOBULAGUS = 
			ModFeatures.GLOBULAGUS.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(6);
	
	public static final ConfiguredFeature<?, ?> CLAWFERN = 
			ModFeatures.CLAWFERN.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> AERIDIUM = 
			ModFeatures.AERIDIUM.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> LAMELLARIUM = 
			ModFeatures.LAMELLARIUM.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(6); 
	
	public static final ConfiguredFeature<?, ?> BOLUX_MUSHROOM = 
			ModFeatures.BOLUX_MUSHROOM.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(2); 
	
	public static final ConfiguredFeature<?, ?> ORANGO = 
			ModFeatures.ORANGO.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(6); 
	
	public static final ConfiguredFeature<?, ?> LUTEBUS = 
			ModFeatures.LUTEBUS.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);

	public static final ConfiguredFeature<?, ?> FLAMMALIX =
			ModFeatures.FLAMMALIX.configured(IFeatureConfig.NONE).
					decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(5);

	public static final ConfiguredFeature<?, ?> INFLEXIA =
			ModFeatures.INFLEXIA.configured(IFeatureConfig.NONE).
					decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(16);


	// SKY PLANTS
	public static final ConfiguredFeature<?, ?> FILALUX = 
			ModFeatures.FILALUX.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(1);

	
	
	// WALL PLANTS
	public static final ConfiguredFeature<?, ?> PURPLE_POLYPORE = 
			ModFeatures.PURPLE_POLYPORE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> AURANT_POLYPORE = 
			ModFeatures.AURANT_POLYPORE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> PURPLE_POLYPORE_DENSE = 
			ModFeatures.PURPLE_POLYPORE_DENSE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(15));
	
	public static final ConfiguredFeature<?, ?> TAIL_MOSS = 
			ModFeatures.TAIL_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(15));
	
	public static final ConfiguredFeature<?, ?> TAIL_MOSS_WOOD = 
			ModFeatures.TAIL_MOSS_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(25));
	
	public static final ConfiguredFeature<?, ?> CYAN_MOSS = 
			ModFeatures.CYAN_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(15));
	
	public static final ConfiguredFeature<?, ?> CYAN_MOSS_WOOD = 
			ModFeatures.CYAN_MOSS_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(25));
	
	public static final ConfiguredFeature<?, ?> TWISTED_MOSS = 
			ModFeatures.TWISTED_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(15));
	
	public static final ConfiguredFeature<?, ?> TWISTED_MOSS_WOOD = 
			ModFeatures.TWISTED_MOSS_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(25));
	
	public static final ConfiguredFeature<?, ?> BULB_MOSS = 
			ModFeatures.BULB_MOSS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(1));
	
	public static final ConfiguredFeature<?, ?> BULB_MOSS_WOOD = 
			ModFeatures.BULB_MOSS_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(15));
	
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_WALL = 
			ModFeatures.SMALL_JELLYSHROOM_WALL.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(4));
	
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_WOOD = 
			ModFeatures.SMALL_JELLYSHROOM_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(8));
	
	public static final ConfiguredFeature<?, ?> JUNGLE_FERN_WOOD = 
			ModFeatures.JUNGLE_FERN_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(12));
	
	public static final ConfiguredFeature<?, ?> RUSCUS = 
			ModFeatures.RUSCUS.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));
	
	public static final ConfiguredFeature<?, ?> RUSCUS_WOOD = 
			ModFeatures.RUSCUS_WOOD.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(10));

	
	// VINES
	public static final ConfiguredFeature<?, ?> DENSE_VINE = 
			ModFeatures.DENSE_VINE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(3));
	
	public static final ConfiguredFeature<?, ?> TWISTED_VINE = 
			ModFeatures.TWISTED_VINE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(3));
	
	public static final ConfiguredFeature<?, ?> BULB_VINE = 
			ModFeatures.BULB_VINE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	public static final ConfiguredFeature<?, ?> JUNGLE_VINE = 
			ModFeatures.JUNGLE_VINE.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(5));
	
	// CEIL PLANTS
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_CEIL = 
			ModFeatures.SMALL_JELLYSHROOM_CEIL.configured(IFeatureConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE).count(8));
	
	// TERRAIN
	public static final ConfiguredFeature<?, ?> ROUND_CAVE = 
			ModFeatures.ROUND_CAVE.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(2)));
	
	public static final ConfiguredFeature<?, ?> END_LAKE = 
			ModFeatures.END_LAKE.configured(IFeatureConfig.NONE).
			decorated(Placement.WATER_LAKE.configured(new ChanceConfig(4)));
	public static final ConfiguredFeature<?, ?> END_LAKE_RARE = 
			ModFeatures.END_LAKE_RARE.configured(IFeatureConfig.NONE).
			decorated(Placement.WATER_LAKE.configured(new ChanceConfig(40)));
	public static final ConfiguredFeature<?, ?> END_LAKE_NORMAL = 
			ModFeatures.END_LAKE.configured(IFeatureConfig.NONE).
			decorated(Placement.WATER_LAKE.configured(new ChanceConfig(20)));
	
	public static final ConfiguredFeature<?, ?> DESERT_LAKE = 
			ModFeatures.DESERT_LAKE.configured(IFeatureConfig.NONE).
			decorated(Placement.WATER_LAKE.configured(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SPIRE = 
			ModFeatures.SPIRE.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(2)));
	public static final ConfiguredFeature<?, ?> FLOATING_SPIRE = 
			ModFeatures.FLOATING_SPIRE.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> GEYSER = 
			ModFeatures.GEYSER.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SULPHURIC_LAKE = 
			ModFeatures.SULPHURIC_LAKE.configured(IFeatureConfig.NONE).
			decorated(Placement.WATER_LAKE.configured(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SULPHURIC_CAVE = 
			ModFeatures.SULPHURIC_CAVE.configured(IFeatureConfig.NONE).
			decorated(Placement.COUNT.configured(new FeatureSpreadConfig(2)));
	
	public static final ConfiguredFeature<?, ?> SURFACE_VENT = 
			ModFeatures.SURFACE_VENT.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(4)));
	
	public static final ConfiguredFeature<?, ?> ICE_STAR = 
			ModFeatures.ICE_STAR.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(15)));
	
	public static final ConfiguredFeature<?, ?> ICE_STAR_SMALL = 
			ModFeatures.ICE_STAR_SMALL.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> OVERWORLD_ISLAND = 
			ModFeatures.OVERWORLD_ISLAND.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> OBSIDIAN_PILLAR_BASEMENT = 
			ModFeatures.OBSIDIAN_PILLAR_BASEMENT.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> FALLEN_PILLAR = 
			ModFeatures.FALLEN_PILLAR.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(20)));
	
	public static final ConfiguredFeature<?, ?> OBSIDIAN_BOULDER = 
			ModFeatures.OBSIDIAN_BOULDER.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(10)));

	public static final ConfiguredFeature<?, ?> TUNEL_CAVE =
			ModFeatures.TUNEL_CAVE.configured(IFeatureConfig.NONE)
			.decorated(Placement.COUNT.configured(new FeatureSpreadConfig(1)));

	public static final ConfiguredFeature<?, ?> THIN_ARCH =
			ModFeatures.THIN_ARCH.configured(IFeatureConfig.NONE)
					.decorated(Placement.CHANCE.configured(new ChanceConfig(15)));

	public static final ConfiguredFeature<?, ?> UMBRALITH_ARCH =
			ModFeatures.UMBRALITH_ARCH.configured(IFeatureConfig.NONE)
					.decorated(Placement.CHANCE.configured(new ChanceConfig(10)));


	// TREES
	public static final ConfiguredFeature<?, ?> MOSSY_GLOWSHROOM = 
			ModFeatures.MOSSY_GLOWSHROOM.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));//count(3));
	
	public static final ConfiguredFeature<?, ?> LACUGROVE = 
			ModFeatures.LACUGROVE.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> PYTHADENDRON = 
			ModFeatures.PYTHADENDRON.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> DRAGON_TREE = 
			ModFeatures.DRAGON_TREE.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> TENANEA = 
			ModFeatures.TENANEA.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> HELIX_TREE = 
			ModFeatures.HELIX_TREE.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> UMBRELLA_TREE = 
			ModFeatures.UMBRELLA_TREE.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> JELLYSHROOM = 
			ModFeatures.JELLYSHROOM.configured(IFeatureConfig.NONE).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configured(NoPlacementConfig.NONE).
			decorated(Placement.SQUARE.configured(NoPlacementConfig.NONE)));
	
	public static final ConfiguredFeature<?, ?> GIGANTIC_AMARANITA = 
			ModFeatures.GIGANTIC_AMARANITA.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(1);
	
	public static final ConfiguredFeature<?, ?> LUCERNIA = 
			ModFeatures.LUCERNIA.configured(IFeatureConfig.NONE).
			decorated(Features.Placements.HEIGHTMAP_SQUARE).countRandom(3);

	// ORES
	public static final ConfiguredFeature<?, ?> THALLASIUM_ORE =
			ModFeatures.THALLASIUM_ORE.configured(
					new OreFeatureConfig(
							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.THALLASIUM.ore.get().defaultBlockState(), 6)).
			range(96).squared().count(20);
	
	public static final ConfiguredFeature<?, ?> ENDER_ORE =
			ModFeatures.ENDER_ORE.configured(
					new OreFeatureConfig(
							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.ENDER_ORE.get().defaultBlockState(), 3)).
			range(96).squared().count(20);
	
	public static final ConfiguredFeature<?, ?> AMBER_ORE =
			ModFeatures.AMBER_ORE.configured(
					new OreFeatureConfig(
							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.AMBER_ORE.get().defaultBlockState(), 6)).
			range(96).squared().count(20);
	
	public static final ConfiguredFeature<?, ?> FLAVOLITE_LAYER = 
			ModFeatures.FLAVOLITE_LAYER.configured(IFeatureConfig.NONE).
			//withPlacement(Placement.CHANCE.configure(new ChanceConfig(6)));
			decorated(Placement.COUNT.configured(new FeatureSpreadConfig(6)));
	
	public static final ConfiguredFeature<?, ?> VIOLECITE_LAYER = 
			ModFeatures.VIOLECITE_LAYER.configured(IFeatureConfig.NONE).
			//withPlacement(Placement.CHANCE.configure(new ChanceConfig(8)));
			decorated(Placement.COUNT.configured(new FeatureSpreadConfig(8)));
	
	// BUILDINGS
	public static final ConfiguredFeature<?, ?> CRASHED_SHIP = 
			ModFeatures.CRASHED_SHIP.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(500)));
	
	public static final ConfiguredFeature<?, ?> NBT_STRUCTURES = 
			ModFeatures.NBT_STRUCTURES.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(10)));
	
	// MOBS
	public static final ConfiguredFeature<?, ?> SILK_MOTH_NEST = 
			ModFeatures.SILK_MOTH_NEST.configured(IFeatureConfig.NONE).
			decorated(Placement.CHANCE.configured(new ChanceConfig(2)));

	// CAVES
	public static final ConfiguredFeature<?, ?> SMARAGDANT_CRYSTAL = 
			ModFeatures.SMARAGDANT_CRYSTAL.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> SMARAGDANT_CRYSTAL_SHARD = 
			ModFeatures.SMARAGDANT_CRYSTAL_SHARD.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> BIG_AURORA_CRYSTAL = 
			ModFeatures.BIG_AURORA_CRYSTAL.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALACTITE = 
			ModFeatures.END_STONE_STALACTITE.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALAGMITE = 
			ModFeatures.END_STONE_STALAGMITE.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALACTITE_CAVEMOSS = 
			ModFeatures.END_STONE_STALACTITE_CAVEMOSS.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALAGMITE_CAVEMOSS = 
			ModFeatures.END_STONE_STALAGMITE_CAVEMOSS.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> CAVE_BUSH = 
			ModFeatures.CAVE_BUSH.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> CAVE_GRASS = 
			ModFeatures.CAVE_GRASS.configured(IFeatureConfig.NONE);
	
	public static final ConfiguredFeature<?, ?> RUBINEA = 
			ModFeatures.RUBINEA.configured(IFeatureConfig.NONE);
	
	public static void registerConfiguredFeatures() 	
	{
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        // WATER PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lotus"), END_LOTUS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lotus_leaf"), END_LOTUS_LEAF);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bubble_coral"), BUBBLE_CORAL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bubble_coral_rare"), BUBBLE_CORAL_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lily"), END_LILY);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lily_rare"), END_LILY_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "menger_sponge"), MENGER_SPONGE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_red"), CHARNIA_RED);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_purple"), CHARNIA_PURPLE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_orange"), CHARNIA_ORANGE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_light_blue"), CHARNIA_LIGHT_BLUE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_cyan"), CHARNIA_CYAN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_green"), CHARNIA_GREEN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "charnia_red_rare"), CHARNIA_RED_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "hydralux"), HYDRALUX);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "flamaea"), FLAMAEA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "pond_anemone"), POND_ANEMONE);
        // BUSHES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "pythadendron_bush"), PYTHADENDRON_BUSH);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "dragon_tree_bush"), DRAGON_TREE_BUSH);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tenanea_bush"), TENANEA_BUSH);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "large_amaranita"), LARGE_AMARANITA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lucernia_bush"), LUCERNIA_BUSH);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lucernia_bush_rare"), LUCERNIA_BUSH_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "neon_cactus"), NEON_CACTUS);
        // PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "umbrella_moss"), UMBRELLA_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "creeping_moss"), CREEPING_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "chorus_grass"), CHORUS_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "crystal_grass"), CRYSTAL_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "amber_grass"), AMBER_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "shadow_plant"), SHADOW_PLANT);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "blue_vine"), BLUE_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "murkweed"), MURKWEED);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "needlegrass"), NEEDLEGRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "shadow_berry"), SHADOW_BERRY);   
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bushy_grass"), BUSHY_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bushy_grass_wg"), BUSHY_GRASS_WG); 
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lanceleaf"), LANCELEAF); 
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "glow_pillar"), GLOW_PILLAR); 
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_umbrella_moss"), TWISTED_UMBRELLA_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "jungle_grass"), JUNGLE_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "small_jellyshroom_floor"), SMALL_JELLYSHROOM_FLOOR);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "blossom_berry"), BLOSSOM_BERRY);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lumecorn"), LUMECORN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "blooming_cooksonia"), BLOOMING_COOKSONIA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "salteago"), SALTEAGO);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "vaiolush_fern"), VAIOLUSH_FERN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "fracturn"), FRACTURN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "umbrella_moss_rare"), UMBRELLA_MOSS_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "creeping_moss_rare"), CREEPING_MOSS_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_umbrella_moss_rare"), TWISTED_UMBRELLA_MOSS_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "small_amaranita"), SMALL_AMARANITA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "globulagus"), GLOBULAGUS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "clawfern"), CLAWFERN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "aeridium"), AERIDIUM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lamellarium"), LAMELLARIUM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bolux_mushroom"), BOLUX_MUSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lutebus"), LUTEBUS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "orango"), ORANGO);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "flammalix"), FLAMMALIX);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "inflexia"), INFLEXIA);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "amber_root"), AMBER_ROOT);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "chorus_mushroom"), CHORUS_MUSHROOM);
		// SKY PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "filalux"), FILALUX);
        // WALL PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "purple_polypore"), PURPLE_POLYPORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "aurant_polypore"), AURANT_POLYPORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "purple_polypore_dense"), PURPLE_POLYPORE_DENSE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tail_moss"), TAIL_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tail_moss_wood"), TAIL_MOSS_WOOD);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cyan_moss"), CYAN_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cyan_moss_wood"), CYAN_MOSS_WOOD);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_moss"), TWISTED_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_moss_wood"), TWISTED_MOSS_WOOD);      
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bulb_moss"), BULB_MOSS);      
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bulb_moss_wood"), BULB_MOSS_WOOD);  
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "small_jellyshroom_wall"), SMALL_JELLYSHROOM_WALL);      
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "small_jellyshroom_wood"), SMALL_JELLYSHROOM_WOOD); 
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "jungle_fern_wood"), JUNGLE_FERN_WOOD); 
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "ruscus"), RUSCUS); 
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "ruscus_wood"), RUSCUS_WOOD); 
        // VINES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "dense_vine"), DENSE_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_vine"), TWISTED_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bulb_vine"), BULB_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "jungle_vine"), JUNGLE_VINE);
        // CEIL PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "small_jellyshroom_ceil"), SMALL_JELLYSHROOM_CEIL);
        // TERRAIN
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "round_cave"), ROUND_CAVE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lake"), END_LAKE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lake_rare"), END_LAKE_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "desert_lake"), DESERT_LAKE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lake_normal"), END_LAKE_NORMAL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "spire"), SPIRE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "floating_spire"), FLOATING_SPIRE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "geyser"), GEYSER);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "sulphuric_lake"), SULPHURIC_LAKE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "sulphuric_cave"), SULPHURIC_CAVE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "surface_vent"), SURFACE_VENT);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "ice_star"), ICE_STAR);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "ice_star_small"), ICE_STAR_SMALL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "overworld_island"), OVERWORLD_ISLAND);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "obsidian_pillar_basement"), OBSIDIAN_PILLAR_BASEMENT);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "fallen_pillar"), FALLEN_PILLAR);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "obsidian_boulder"), OBSIDIAN_BOULDER);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "thin_arch"), THIN_ARCH);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "umbralith_arch"), UMBRALITH_ARCH);
        // TREES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "mossy_glowshroom"), MOSSY_GLOWSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lacugrove"), LACUGROVE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "pythadendron"), PYTHADENDRON);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "dragon_tree"), DRAGON_TREE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tenanea"), TENANEA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "helix_tree"), HELIX_TREE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "umbrella_tree"), UMBRELLA_TREE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "jellyshroom"), JELLYSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "gigantic_amaranita"), GIGANTIC_AMARANITA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lucernia"), LUCERNIA);
        // ORES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "thallasium_ore"), THALLASIUM_ORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "ender_ore"), ENDER_ORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "amber_ore"), AMBER_ORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "flavolite_layer"), FLAVOLITE_LAYER);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "violecite_layer"), VIOLECITE_LAYER);
        // BUILDINGS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "crashed_ship"), CRASHED_SHIP);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "nbt_structures"), NBT_STRUCTURES);
        // MOBS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "silk_moth_nest"), SILK_MOTH_NEST);
        // CAVES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "smaragdant_crystal"), SMARAGDANT_CRYSTAL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "smaragdant_crystal_shard"), SMARAGDANT_CRYSTAL_SHARD);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "big_aurora_crystal"), BIG_AURORA_CRYSTAL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_stone_stalactite"), END_STONE_STALACTITE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_stone_stalagmite"), END_STONE_STALAGMITE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_stone_stalactite_cavemoss"), END_STONE_STALACTITE_CAVEMOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_stone_stalagmite_cavemoss"), END_STONE_STALAGMITE_CAVEMOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cave_bush"), CAVE_BUSH);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cave_grass"), CAVE_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "rubinea"), RUBINEA);
		Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tunel_cave"), TUNEL_CAVE);

		//Compat
	}   
}
