package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.feature.BlueVineFeature;
import mod.beethoven92.betterendforge.common.world.feature.BushFeature;
import mod.beethoven92.betterendforge.common.world.feature.CavePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.CharniaFeature;
import mod.beethoven92.betterendforge.common.world.feature.DoublePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.DragonTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLilyFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLotusFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLotusLeafFeature;
import mod.beethoven92.betterendforge.common.world.feature.FloatingSpireFeature;
import mod.beethoven92.betterendforge.common.world.feature.GeyserFeature;
import mod.beethoven92.betterendforge.common.world.feature.HelixTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.HydraluxFeature;
import mod.beethoven92.betterendforge.common.world.feature.LacugroveFeature;
import mod.beethoven92.betterendforge.common.world.feature.LanceleafFeature;
import mod.beethoven92.betterendforge.common.world.feature.MengerSpongeFeature;
import mod.beethoven92.betterendforge.common.world.feature.MossyGlowshroomFeature;
import mod.beethoven92.betterendforge.common.world.feature.OreLayerFeature;
import mod.beethoven92.betterendforge.common.world.feature.PythadendronFeature;
import mod.beethoven92.betterendforge.common.world.feature.RoundCaveFeature;
import mod.beethoven92.betterendforge.common.world.feature.SinglePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.SpireFeature;
import mod.beethoven92.betterendforge.common.world.feature.SulphuricCaveFeature;
import mod.beethoven92.betterendforge.common.world.feature.SulphuricLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.SurfaceVentFeature;
import mod.beethoven92.betterendforge.common.world.feature.TenaneaBushFeature;
import mod.beethoven92.betterendforge.common.world.feature.TenaneaFeature;
import mod.beethoven92.betterendforge.common.world.feature.UnderwaterPlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.VineFeature;
import mod.beethoven92.betterendforge.common.world.feature.WallPlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.WallPlantOnLogFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.RegistryEvent.Register;

public class ModFeatures
{
	// WATER PLANTS
	public static final Feature<NoFeatureConfig> END_LOTUS = new EndLotusFeature(7);
	public static final Feature<NoFeatureConfig> END_LOTUS_LEAF = new EndLotusLeafFeature(20);	
	public static final Feature<NoFeatureConfig> BUBBLE_CORAL = new UnderwaterPlantFeature(ModBlocks.BUBBLE_CORAL.get(), 6);
	public static final Feature<NoFeatureConfig> BUBBLE_CORAL_RARE = new UnderwaterPlantFeature(ModBlocks.BUBBLE_CORAL.get(), 3);
	public static final Feature<NoFeatureConfig> END_LILY = new EndLilyFeature(6);
	public static final Feature<NoFeatureConfig> END_LILY_RARE = new EndLilyFeature(3);
	public static final Feature<NoFeatureConfig> MENGER_SPONGE = new MengerSpongeFeature(5);
	public static final Feature<NoFeatureConfig> CHARNIA_RED = new CharniaFeature(ModBlocks.CHARNIA_RED.get());
	public static final Feature<NoFeatureConfig> CHARNIA_PURPLE = new CharniaFeature(ModBlocks.CHARNIA_PURPLE.get());
	public static final Feature<NoFeatureConfig> CHARNIA_ORANGE = new CharniaFeature(ModBlocks.CHARNIA_ORANGE.get());
	public static final Feature<NoFeatureConfig> CHARNIA_LIGHT_BLUE = new CharniaFeature(ModBlocks.CHARNIA_LIGHT_BLUE.get());
	public static final Feature<NoFeatureConfig> CHARNIA_CYAN = new CharniaFeature(ModBlocks.CHARNIA_CYAN.get());
	public static final Feature<NoFeatureConfig> CHARNIA_GREEN = new CharniaFeature(ModBlocks.CHARNIA_GREEN.get());
	public static final Feature<NoFeatureConfig> CHARNIA_RED_RARE = new CharniaFeature(ModBlocks.CHARNIA_RED.get());
	public static final Feature<NoFeatureConfig> HYDRALUX = new HydraluxFeature(5);
	
	// BUSHES
	public static final Feature<NoFeatureConfig> PYTHADENDRON_BUSH = new BushFeature(ModBlocks.PYTHADENDRON_LEAVES.get(), ModBlocks.PYTHADENDRON.bark.get());
	public static final Feature<NoFeatureConfig> DRAGON_TREE_BUSH = new BushFeature(ModBlocks.DRAGON_TREE_LEAVES.get(), ModBlocks.DRAGON_TREE.bark.get());
	public static final Feature<NoFeatureConfig> TENANEA_BUSH = new TenaneaBushFeature();
	
	// PLANTS
	public static final Feature<NoFeatureConfig> UMBRELLA_MOSS = new DoublePlantFeature(ModBlocks.UMBRELLA_MOSS.get(), ModBlocks.UMBRELLA_MOSS_TALL.get(), 5);
	public static final Feature<NoFeatureConfig> CREEPING_MOSS = new SinglePlantFeature(ModBlocks.CREEPING_MOSS.get(), 5);
	public static final Feature<NoFeatureConfig> CHORUS_GRASS = new SinglePlantFeature(ModBlocks.CHORUS_GRASS.get(), 4);
	public static final Feature<NoFeatureConfig> CAVE_GRASS = new CavePlantFeature(ModBlocks.CAVE_GRASS.get(), 7);
	public static final Feature<NoFeatureConfig> CRYSTAL_GRASS = new SinglePlantFeature(ModBlocks.CRYSTAL_GRASS.get(), 8, false);
	public static final Feature<NoFeatureConfig> SHADOW_PLANT = new SinglePlantFeature(ModBlocks.SHADOW_PLANT.get(), 6);
	public static final Feature<NoFeatureConfig> BLUE_VINE = new BlueVineFeature(5);
	public static final Feature<NoFeatureConfig> MURKWEED = new SinglePlantFeature(ModBlocks.MURKWEED.get(), 3);
	public static final Feature<NoFeatureConfig> NEEDLEGRASS = new SinglePlantFeature(ModBlocks.NEEDLEGRASS.get(), 3);
	public static final Feature<NoFeatureConfig> SHADOW_BERRY = new SinglePlantFeature(ModBlocks.SHADOW_BERRY.get(), 2);
	public static final Feature<NoFeatureConfig> BUSHY_GRASS = new SinglePlantFeature(ModBlocks.BUSHY_GRASS.get(), 8, false);
	public static final Feature<NoFeatureConfig> BUSHY_GRASS_WG = new SinglePlantFeature(ModBlocks.BUSHY_GRASS.get(), 5);
	public static final Feature<NoFeatureConfig> LANCELEAF = new LanceleafFeature();
	
	// WALL PLANTS
	public static final Feature<NoFeatureConfig> PURPLE_POLYPORE = new WallPlantOnLogFeature(ModBlocks.PURPLE_POLYPORE.get(), 3);
	public static final Feature<NoFeatureConfig> PURPLE_POLYPORE_DENSE = new WallPlantOnLogFeature(ModBlocks.PURPLE_POLYPORE.get(), 5);
	public static final Feature<NoFeatureConfig> TAIL_MOSS = new WallPlantFeature(ModBlocks.TAIL_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> TAIL_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.TAIL_MOSS.get(), 4);
	public static final Feature<NoFeatureConfig> CYAN_MOSS = new WallPlantFeature(ModBlocks.CYAN_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> CYAN_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.CYAN_MOSS.get(), 4);
	public static final Feature<NoFeatureConfig> TWISTED_MOSS = new WallPlantFeature(ModBlocks.TWISTED_MOSS.get(), 6);
	public static final Feature<NoFeatureConfig> TWISTED_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.TWISTED_MOSS.get(), 6);
	
	// VINES
	public static final Feature<NoFeatureConfig> DENSE_VINE = new VineFeature(ModBlocks.DENSE_VINE.get(), 24);
	public static final Feature<NoFeatureConfig> TWISTED_VINE = new VineFeature(ModBlocks.TWISTED_VINE.get(), 24);
	public static final Feature<NoFeatureConfig> BULB_VINE = new VineFeature(ModBlocks.BULB_VINE.get(), 24);
	
	// TERRAIN
	public static final Feature<NoFeatureConfig> ROUND_CAVE = new RoundCaveFeature();
	public static final Feature<NoFeatureConfig> ROUND_CAVE_RARE = new RoundCaveFeature();
	public static final Feature<NoFeatureConfig> END_LAKE = new EndLakeFeature();
	public static final Feature<NoFeatureConfig> END_LAKE_RARE = new EndLakeFeature();
	public static final Feature<NoFeatureConfig> SPIRE = new SpireFeature();
	public static final Feature<NoFeatureConfig> FLOATING_SPIRE = new FloatingSpireFeature();
	public static final Feature<NoFeatureConfig> GEYSER = new GeyserFeature();
	public static final Feature<NoFeatureConfig> SULPHURIC_LAKE = new SulphuricLakeFeature();
	public static final Feature<NoFeatureConfig> SULPHURIC_CAVE = new SulphuricCaveFeature();
	public static final Feature<NoFeatureConfig> SURFACE_VENT = new SurfaceVentFeature();
	
	// TREES
	public static final Feature<NoFeatureConfig> MOSSY_GLOWSHROOM = new MossyGlowshroomFeature();
	public static final Feature<NoFeatureConfig> LACUGROVE = new LacugroveFeature();
	public static final Feature<NoFeatureConfig> PYTHADENDRON = new PythadendronFeature();
	public static final Feature<NoFeatureConfig> DRAGON_TREE = new DragonTreeFeature();
	public static final Feature<NoFeatureConfig> TENANEA = new TenaneaFeature();
	public static final Feature<NoFeatureConfig> HELIX_TREE = new HelixTreeFeature();
	
	// ORES
	public static final Feature<OreFeatureConfig> ENDER_ORE = new OreFeature(OreFeatureConfig.CODEC);
	public static final Feature<NoFeatureConfig> FLAVOLITE_LAYER = new OreLayerFeature(ModBlocks.FLAVOLITE.stone.get().getDefaultState(), 12, 4, 96);
	public static final Feature<NoFeatureConfig> VIOLECITE_LAYER = new OreLayerFeature(ModBlocks.VIOLECITE.stone.get().getDefaultState(), 15, 4, 96);
	
	public static void registerFeatures(Register<Feature<?>> event)
    {
		// WATER PLANTS
    	BetterEnd.register(event.getRegistry(), END_LOTUS, "end_lotus");
    	BetterEnd.register(event.getRegistry(), END_LOTUS_LEAF, "end_lotus_leaf");    	
    	BetterEnd.register(event.getRegistry(), BUBBLE_CORAL, "bubble_coral");
    	BetterEnd.register(event.getRegistry(), BUBBLE_CORAL_RARE, "bubble_coral_rare");  
    	BetterEnd.register(event.getRegistry(), END_LILY, "end_lily");
    	BetterEnd.register(event.getRegistry(), END_LILY_RARE, "end_lily_rare");  
    	BetterEnd.register(event.getRegistry(), MENGER_SPONGE, "menger_sponge");
    	BetterEnd.register(event.getRegistry(), CHARNIA_RED, "charnia_red");
    	BetterEnd.register(event.getRegistry(), CHARNIA_PURPLE, "charnia_purple");  
    	BetterEnd.register(event.getRegistry(), CHARNIA_ORANGE, "charnia_orange");
    	BetterEnd.register(event.getRegistry(), CHARNIA_LIGHT_BLUE, "charnia_light_blue");
    	BetterEnd.register(event.getRegistry(), CHARNIA_CYAN, "charnia_cyan");  
    	BetterEnd.register(event.getRegistry(), CHARNIA_GREEN, "charnia_green");
    	BetterEnd.register(event.getRegistry(), CHARNIA_RED_RARE, "charnia_red_rare");
    	BetterEnd.register(event.getRegistry(), HYDRALUX, "hydralux");
    	//BUSHES
    	BetterEnd.register(event.getRegistry(), PYTHADENDRON_BUSH, "pythadendron_bush"); 
    	BetterEnd.register(event.getRegistry(), DRAGON_TREE_BUSH, "dragon_tree_bush"); 
    	BetterEnd.register(event.getRegistry(), TENANEA_BUSH, "tenanea_bush"); 
    	// PLANTS
    	BetterEnd.register(event.getRegistry(), UMBRELLA_MOSS, "umbrella_moss");
    	BetterEnd.register(event.getRegistry(), CREEPING_MOSS, "creeping_moss");   	
    	BetterEnd.register(event.getRegistry(), CHORUS_GRASS, "chorus_grass");
    	BetterEnd.register(event.getRegistry(), CAVE_GRASS, "cave_grass");
    	BetterEnd.register(event.getRegistry(), CRYSTAL_GRASS, "crystal_grass");
    	BetterEnd.register(event.getRegistry(), SHADOW_PLANT, "shadow_plant");  	
    	BetterEnd.register(event.getRegistry(), BLUE_VINE, "blue_vine"); 
    	BetterEnd.register(event.getRegistry(), MURKWEED, "murkweed");  	
    	BetterEnd.register(event.getRegistry(), NEEDLEGRASS, "needlegrass"); 
    	BetterEnd.register(event.getRegistry(), SHADOW_BERRY, "shadow_berry"); 
    	BetterEnd.register(event.getRegistry(), BUSHY_GRASS, "bushy_grass"); 
    	BetterEnd.register(event.getRegistry(), BUSHY_GRASS_WG, "bushy_grass_wg"); 
    	// WALL_PLANTS
    	BetterEnd.register(event.getRegistry(), PURPLE_POLYPORE, "purple_polypore");
    	BetterEnd.register(event.getRegistry(), PURPLE_POLYPORE_DENSE, "purple_polypore_dense");
    	BetterEnd.register(event.getRegistry(), TAIL_MOSS, "tail_moss");
    	BetterEnd.register(event.getRegistry(), TAIL_MOSS_WOOD, "tail_moss_wood");  	
    	BetterEnd.register(event.getRegistry(), CYAN_MOSS, "cyan_moss"); 
    	BetterEnd.register(event.getRegistry(), CYAN_MOSS_WOOD, "cyan_moss_wood"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_MOSS, "twisted_moss"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_MOSS_WOOD, "twisted_moss_wood"); 
    	// VINES
    	BetterEnd.register(event.getRegistry(), DENSE_VINE, "dense_vine"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_VINE, "twisted_vine"); 
    	BetterEnd.register(event.getRegistry(), BULB_VINE, "bulb_vine"); 
    	// TERRAIN
    	BetterEnd.register(event.getRegistry(), ROUND_CAVE, "round_cave");
    	BetterEnd.register(event.getRegistry(), ROUND_CAVE_RARE, "round_cave_rare");
    	BetterEnd.register(event.getRegistry(), END_LAKE, "end_lake");   
    	BetterEnd.register(event.getRegistry(), END_LAKE_RARE, "end_lake_rare"); 
    	BetterEnd.register(event.getRegistry(), SPIRE, "spire");   
    	BetterEnd.register(event.getRegistry(), FLOATING_SPIRE, "floating_spire");
    	BetterEnd.register(event.getRegistry(), GEYSER, "geyser");
    	BetterEnd.register(event.getRegistry(), SULPHURIC_LAKE, "sulphuric_lake");
    	BetterEnd.register(event.getRegistry(), SULPHURIC_CAVE, "sulphuric_cave");
    	BetterEnd.register(event.getRegistry(), SURFACE_VENT, "surface_vent");
    	// TREES
    	BetterEnd.register(event.getRegistry(), MOSSY_GLOWSHROOM, "mossy_glowshroom");
    	BetterEnd.register(event.getRegistry(), LACUGROVE, "lacugrove");   
    	BetterEnd.register(event.getRegistry(), PYTHADENDRON, "pythadendron"); 
    	BetterEnd.register(event.getRegistry(), DRAGON_TREE, "dragon_tree");
    	BetterEnd.register(event.getRegistry(), TENANEA, "tenanea");
    	BetterEnd.register(event.getRegistry(), HELIX_TREE, "helix_tree");
    	// ORES
    	BetterEnd.register(event.getRegistry(), ENDER_ORE, "ender_ore");
    	BetterEnd.register(event.getRegistry(), FLAVOLITE_LAYER, "flavolite_layer");
    	BetterEnd.register(event.getRegistry(), VIOLECITE_LAYER, "violecite_layer");
    }
}
