package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
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
			ModFeatures.END_LOTUS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	public static final ConfiguredFeature<?, ?> END_LOTUS_LEAF = 
			ModFeatures.END_LOTUS_LEAF.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(25));
	
	public static final ConfiguredFeature<?, ?> BUBBLE_CORAL = 
			ModFeatures.BUBBLE_CORAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> BUBBLE_CORAL_RARE = 
			ModFeatures.BUBBLE_CORAL_RARE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(4));
	
	public static final ConfiguredFeature<?, ?> END_LILY = 
			ModFeatures.END_LILY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> END_LILY_RARE = 
			ModFeatures.END_LILY_RARE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(4));
	
	public static final ConfiguredFeature<?, ?> MENGER_SPONGE = 
			ModFeatures.MENGER_SPONGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(1));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_RED = 
			ModFeatures.CHARNIA_RED.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_PURPLE = 
			ModFeatures.CHARNIA_PURPLE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_ORANGE = 
			ModFeatures.CHARNIA_ORANGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_LIGHT_BLUE = 
			ModFeatures.CHARNIA_LIGHT_BLUE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_CYAN = 
			ModFeatures.CHARNIA_CYAN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_GREEN = 
			ModFeatures.CHARNIA_GREEN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_RED_RARE = 
			ModFeatures.CHARNIA_RED_RARE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(2));
	
	public static final ConfiguredFeature<?, ?> HYDRALUX = 
			ModFeatures.HYDRALUX.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	// BUSHES
	public static final ConfiguredFeature<?, ?> PYTHADENDRON_BUSH = 
			ModFeatures.PYTHADENDRON_BUSH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> DRAGON_TREE_BUSH = 
			ModFeatures.DRAGON_TREE_BUSH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> TENANEA_BUSH = 
			ModFeatures.TENANEA_BUSH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	// PLANTS
	public static final ConfiguredFeature<?, ?> UMBRELLA_MOSS = 
			ModFeatures.UMBRELLA_MOSS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	public static final ConfiguredFeature<?, ?> CREEPING_MOSS = 
			ModFeatures.CREEPING_MOSS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	public static final ConfiguredFeature<?, ?> CHORUS_GRASS = 
			ModFeatures.CHORUS_GRASS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	public static final ConfiguredFeature<?, ?> CAVE_GRASS = 
			ModFeatures.CAVE_GRASS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(7));
	
	public static final ConfiguredFeature<?, ?> CRYSTAL_GRASS = 
			ModFeatures.CRYSTAL_GRASS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	public static final ConfiguredFeature<?, ?> SHADOW_PLANT = 
			ModFeatures.SHADOW_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(9));
	
	public static final ConfiguredFeature<?, ?> BLUE_VINE = 
			ModFeatures.BLUE_VINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(1));
	
	public static final ConfiguredFeature<?, ?> MURKWEED = 
			ModFeatures.MURKWEED.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(2));
	
	public static final ConfiguredFeature<?, ?> NEEDLEGRASS = 
			ModFeatures.NEEDLEGRASS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(2));
	
	public static final ConfiguredFeature<?, ?> SHADOW_BERRY = 
			ModFeatures.SHADOW_BERRY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(1));
	
	public static final ConfiguredFeature<?, ?> BUSHY_GRASS = 
			ModFeatures.BUSHY_GRASS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(20));
	
	public static final ConfiguredFeature<?, ?> BUSHY_GRASS_WG = 
			ModFeatures.BUSHY_GRASS_WG.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(10));
	
	public static final ConfiguredFeature<?, ?> LANCELEAF = 
			ModFeatures.LANCELEAF.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(3));
	
	// WALL PLANTS
	public static final ConfiguredFeature<?, ?> PURPLE_POLYPORE = 
			ModFeatures.PURPLE_POLYPORE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	public static final ConfiguredFeature<?, ?> PURPLE_POLYPORE_DENSE = 
			ModFeatures.PURPLE_POLYPORE_DENSE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(15));
	
	public static final ConfiguredFeature<?, ?> TAIL_MOSS = 
			ModFeatures.TAIL_MOSS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(15));
	
	public static final ConfiguredFeature<?, ?> TAIL_MOSS_WOOD = 
			ModFeatures.TAIL_MOSS_WOOD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(25));
	
	public static final ConfiguredFeature<?, ?> CYAN_MOSS = 
			ModFeatures.CYAN_MOSS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(15));
	
	public static final ConfiguredFeature<?, ?> CYAN_MOSS_WOOD = 
			ModFeatures.CYAN_MOSS_WOOD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(25));
	
	public static final ConfiguredFeature<?, ?> TWISTED_MOSS = 
			ModFeatures.TWISTED_MOSS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(15));
	
	public static final ConfiguredFeature<?, ?> TWISTED_MOSS_WOOD = 
			ModFeatures.TWISTED_MOSS_WOOD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(25));
	
	// VINES
	public static final ConfiguredFeature<?, ?> DENSE_VINE = 
			ModFeatures.DENSE_VINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(3));
	
	public static final ConfiguredFeature<?, ?> TWISTED_VINE = 
			ModFeatures.TWISTED_VINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(3));
	
	public static final ConfiguredFeature<?, ?> BULB_VINE = 
			ModFeatures.BULB_VINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).func_242731_b(5));
	
	// TERRAIN
	public static final ConfiguredFeature<?, ?> ROUND_CAVE = 
			ModFeatures.ROUND_CAVE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
	public static final ConfiguredFeature<?, ?> ROUND_CAVE_RARE = 
			ModFeatures.ROUND_CAVE_RARE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.CHANCE.configure(new ChanceConfig(25)));
	
	public static final ConfiguredFeature<?, ?> END_LAKE = 
			ModFeatures.END_LAKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4)));
	public static final ConfiguredFeature<?, ?> END_LAKE_RARE = 
			ModFeatures.END_LAKE_RARE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(40)));
	
	public static final ConfiguredFeature<?, ?> SPIRE = 
			ModFeatures.SPIRE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
	public static final ConfiguredFeature<?, ?> FLOATING_SPIRE = 
			ModFeatures.FLOATING_SPIRE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.CHANCE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> GEYSER = 
			ModFeatures.GEYSER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.CHANCE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SULPHURIC_LAKE = 
			ModFeatures.SULPHURIC_LAKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SULPHURIC_CAVE = 
			ModFeatures.SULPHURIC_CAVE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.COUNT.configure(new FeatureSpreadConfig(2)));
	
	public static final ConfiguredFeature<?, ?> SURFACE_VENT = 
			ModFeatures.SURFACE_VENT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.CHANCE.configure(new ChanceConfig(4)));
	
	// TREES
	public static final ConfiguredFeature<?, ?> MOSSY_GLOWSHROOM = 
			ModFeatures.MOSSY_GLOWSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));//func_242731_b(3));
	
	public static final ConfiguredFeature<?, ?> LACUGROVE = 
			ModFeatures.LACUGROVE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> PYTHADENDRON = 
			ModFeatures.PYTHADENDRON.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> DRAGON_TREE = 
			ModFeatures.DRAGON_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> TENANEA = 
			ModFeatures.TENANEA.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> HELIX_TREE = 
			ModFeatures.HELIX_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			withPlacement(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	// ORES
	public static final ConfiguredFeature<?, ?> ENDER_ORE =
			ModFeatures.ENDER_ORE.withConfiguration(
					new OreFeatureConfig(
							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.ENDER_ORE.get().getDefaultState(), 3)).
			range(96).square().func_242731_b(20);
	
	public static final ConfiguredFeature<?, ?> FLAVOLITE_LAYER = 
			ModFeatures.FLAVOLITE_LAYER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			//withPlacement(Placement.field_242898_b.configure(new ChanceConfig(6)));
			withPlacement(Placement.COUNT.configure(new FeatureSpreadConfig(6)));
	
	public static final ConfiguredFeature<?, ?> VIOLECITE_LAYER = 
			ModFeatures.VIOLECITE_LAYER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).
			//withPlacement(Placement.field_242898_b.configure(new ChanceConfig(8)));
			withPlacement(Placement.COUNT.configure(new FeatureSpreadConfig(8)));
    
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
        // BUSHES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "pythadendron_bush"), PYTHADENDRON_BUSH);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "dragon_tree_bush"), DRAGON_TREE_BUSH);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tenanea_bush"), TENANEA_BUSH);
        // PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "umbrella_moss"), UMBRELLA_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "creeping_moss"), CREEPING_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "chorus_grass"), CHORUS_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cave_grass"), CAVE_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "crystal_grass"), CRYSTAL_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "shadow_plant"), SHADOW_PLANT);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "blue_vine"), BLUE_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "murkweed"), MURKWEED);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "needlegrass"), NEEDLEGRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "shadow_berry"), SHADOW_BERRY);   
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bushy_grass"), BUSHY_GRASS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bushy_grass_wg"), BUSHY_GRASS_WG); 
        // WALL PLANTS
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "purple_polypore"), PURPLE_POLYPORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "purple_polypore_dense"), PURPLE_POLYPORE_DENSE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tail_moss"), TAIL_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tail_moss_wood"), TAIL_MOSS_WOOD);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cyan_moss"), CYAN_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "cyan_moss_wood"), CYAN_MOSS_WOOD);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_moss"), TWISTED_MOSS);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_moss_wood"), TWISTED_MOSS_WOOD);      
        // VINES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "dense_vine"), DENSE_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "twisted_vine"), TWISTED_VINE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "bulb_vine"), BULB_VINE);
        // TERRAIN
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "round_cave"), ROUND_CAVE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "round_cave_rare"), ROUND_CAVE_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lake"), END_LAKE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "end_lake_rare"), END_LAKE_RARE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "spire"), SPIRE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "floating_spire"), FLOATING_SPIRE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "geyser"), GEYSER);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "sulphuric_lake"), SULPHURIC_LAKE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "sulphuric_cave"), SULPHURIC_CAVE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "surface_vent"), SURFACE_VENT);
        // TREES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "mossy_glowshroom"), MOSSY_GLOWSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "lacugrove"), LACUGROVE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "pythadendron"), PYTHADENDRON);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "dragon_tree"), DRAGON_TREE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "tenanea"), TENANEA);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "helix_tree"), HELIX_TREE);
        // ORES
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "ender_ore"), ENDER_ORE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "flavolite_layer"), FLAVOLITE_LAYER);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "violecite_layer"), VIOLECITE_LAYER);
    }   
}
