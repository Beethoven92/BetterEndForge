package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.feature.BigAuroraCrystalFeature;
import mod.beethoven92.betterendforge.common.world.feature.BiomeNBTStructures;
import mod.beethoven92.betterendforge.common.world.feature.BlueVineFeature;
import mod.beethoven92.betterendforge.common.world.feature.BushFeature;
import mod.beethoven92.betterendforge.common.world.feature.BushWithOuterFeature;
import mod.beethoven92.betterendforge.common.world.feature.CharniaFeature;
import mod.beethoven92.betterendforge.common.world.feature.CrashedShipFeature;
import mod.beethoven92.betterendforge.common.world.feature.DesertLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.DoublePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.DragonTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLilyFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLotusFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLotusLeafFeature;
import mod.beethoven92.betterendforge.common.world.feature.FallenPillarFeature;
import mod.beethoven92.betterendforge.common.world.feature.FilaluxFeature;
import mod.beethoven92.betterendforge.common.world.feature.FloatingSpireFeature;
import mod.beethoven92.betterendforge.common.world.feature.GeyserFeature;
import mod.beethoven92.betterendforge.common.world.feature.GiganticAmaranitaFeature;
import mod.beethoven92.betterendforge.common.world.feature.GlowPillarFeature;
import mod.beethoven92.betterendforge.common.world.feature.HelixTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.HydraluxFeature;
import mod.beethoven92.betterendforge.common.world.feature.IceStarFeature;
import mod.beethoven92.betterendforge.common.world.feature.JellyshroomFeature;
import mod.beethoven92.betterendforge.common.world.feature.LacugroveFeature;
import mod.beethoven92.betterendforge.common.world.feature.LanceleafFeature;
import mod.beethoven92.betterendforge.common.world.feature.LargeAmaranitaFeature;
import mod.beethoven92.betterendforge.common.world.feature.LucerniaFeature;
import mod.beethoven92.betterendforge.common.world.feature.Lumecorn;
import mod.beethoven92.betterendforge.common.world.feature.MengerSpongeFeature;
import mod.beethoven92.betterendforge.common.world.feature.MossyGlowshroomFeature;
import mod.beethoven92.betterendforge.common.world.feature.NeonCactusFeature;
import mod.beethoven92.betterendforge.common.world.feature.ObsidianBoulderFeature;
import mod.beethoven92.betterendforge.common.world.feature.ObsidianPillarBasementFeature;
import mod.beethoven92.betterendforge.common.world.feature.OreLayerFeature;
import mod.beethoven92.betterendforge.common.world.feature.OverworldIslandFeature;
import mod.beethoven92.betterendforge.common.world.feature.PythadendronFeature;
import mod.beethoven92.betterendforge.common.world.feature.SilkMothNestFeature;
import mod.beethoven92.betterendforge.common.world.feature.SingleBlockFeature;
import mod.beethoven92.betterendforge.common.world.feature.SingleInvertedScatterFeature;
import mod.beethoven92.betterendforge.common.world.feature.SinglePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.SmaragdantCrystalFeature;
import mod.beethoven92.betterendforge.common.world.feature.SpireFeature;
import mod.beethoven92.betterendforge.common.world.feature.StalactiteFeature;
import mod.beethoven92.betterendforge.common.world.feature.SulphuricCaveFeature;
import mod.beethoven92.betterendforge.common.world.feature.SulphuricLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.SurfaceVentFeature;
import mod.beethoven92.betterendforge.common.world.feature.TenaneaBushFeature;
import mod.beethoven92.betterendforge.common.world.feature.TenaneaFeature;
import mod.beethoven92.betterendforge.common.world.feature.UmbrellaTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.UnderwaterPlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.VineFeature;
import mod.beethoven92.betterendforge.common.world.feature.WallPlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.WallPlantOnLogFeature;
import mod.beethoven92.betterendforge.common.world.feature.*;
import mod.beethoven92.betterendforge.common.world.feature.caves.RoundCaveFeature;
import mod.beethoven92.betterendforge.common.world.feature.caves.TunelCaveFeature;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.UmbraSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.List;
import java.util.function.Supplier;

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
	public static final Feature<NoFeatureConfig> FLAMAEA = new SinglePlantFeature(ModBlocks.FLAMAEA.get(), 12, false, 5);
	public static final Feature<NoFeatureConfig> POND_ANEMONE = new UnderwaterPlantFeature(ModBlocks.POND_ANEMONE.get(), 6);


	// BUSHES
	public static final Feature<NoFeatureConfig> PYTHADENDRON_BUSH = new BushFeature(ModBlocks.PYTHADENDRON_LEAVES.get(), ModBlocks.PYTHADENDRON.bark.get());
	public static final Feature<NoFeatureConfig> DRAGON_TREE_BUSH = new BushFeature(ModBlocks.DRAGON_TREE_LEAVES.get(), ModBlocks.DRAGON_TREE.bark.get());
	public static final Feature<NoFeatureConfig> TENANEA_BUSH = new TenaneaBushFeature();
	public static final Feature<NoFeatureConfig> LARGE_AMARANITA = new LargeAmaranitaFeature();
	public static final Feature<NoFeatureConfig> LUCERNIA_BUSH = new BushWithOuterFeature(ModBlocks.LUCERNIA_LEAVES.get(), ModBlocks.LUCERNIA_OUTER_LEAVES.get(), ModBlocks.LUCERNIA.bark.get());
	public static final Feature<NoFeatureConfig> LUCERNIA_BUSH_RARE = new BushWithOuterFeature(ModBlocks.LUCERNIA_LEAVES.get(), ModBlocks.LUCERNIA_OUTER_LEAVES.get(), ModBlocks.LUCERNIA.bark.get());
	public static final Feature<NoFeatureConfig> NEON_CACTUS = new NeonCactusFeature();
	
	// PLANTS
	public static final Feature<NoFeatureConfig> UMBRELLA_MOSS = new DoublePlantFeature(ModBlocks.UMBRELLA_MOSS.get(), ModBlocks.UMBRELLA_MOSS_TALL.get(), 5);
	public static final Feature<NoFeatureConfig> CREEPING_MOSS = new SinglePlantFeature(ModBlocks.CREEPING_MOSS.get(), 5);
	public static final Feature<NoFeatureConfig> CHORUS_GRASS = new SinglePlantFeature(ModBlocks.CHORUS_GRASS.get(), 4);
	public static final Feature<NoFeatureConfig> CHORUS_MUSHROOM = new SinglePlantFeature(ModBlocks.CHORUS_MUSHROOM.get(), 5, 5);
	public static final Feature<NoFeatureConfig> CRYSTAL_GRASS = new SinglePlantFeature(ModBlocks.CRYSTAL_GRASS.get(), 8, false);
	public static final Feature<NoFeatureConfig> AMBER_GRASS = new SinglePlantFeature(ModBlocks.AMBER_GRASS.get(), 6);
	public static final Feature<NoFeatureConfig> AMBER_ROOT = new SinglePlantFeature(ModBlocks.AMBER_ROOT.get(), 5, 5);
	public static final Feature<NoFeatureConfig> SHADOW_PLANT = new SinglePlantFeature(ModBlocks.SHADOW_PLANT.get(), 6);
	public static final Feature<NoFeatureConfig> BLUE_VINE = new BlueVineFeature(5);
	public static final Feature<NoFeatureConfig> MURKWEED = new SinglePlantFeature(ModBlocks.MURKWEED.get(), 3);
	public static final Feature<NoFeatureConfig> NEEDLEGRASS = new SinglePlantFeature(ModBlocks.NEEDLEGRASS.get(), 3);
	public static final Feature<NoFeatureConfig> SHADOW_BERRY = new SinglePlantFeature(ModBlocks.SHADOW_BERRY.get(), 2);
	public static final Feature<NoFeatureConfig> BUSHY_GRASS = new SinglePlantFeature(ModBlocks.BUSHY_GRASS.get(), 8, false);
	public static final Feature<NoFeatureConfig> BUSHY_GRASS_WG = new SinglePlantFeature(ModBlocks.BUSHY_GRASS.get(), 5);
	public static final Feature<NoFeatureConfig> LANCELEAF = new LanceleafFeature();
	public static final Feature<NoFeatureConfig> GLOW_PILLAR = new GlowPillarFeature();
	public static final Feature<NoFeatureConfig> TWISTED_UMBRELLA_MOSS = new DoublePlantFeature(ModBlocks.TWISTED_UMBRELLA_MOSS.get(), ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get(), 6);
	public static final Feature<NoFeatureConfig> JUNGLE_GRASS = new SinglePlantFeature(ModBlocks.JUNGLE_GRASS.get(), 7, 3);
	public static final Feature<NoFeatureConfig> SMALL_JELLYSHROOM_FLOOR = new SinglePlantFeature(ModBlocks.SMALL_JELLYSHROOM.get(), 5, 5);
	public static final Feature<NoFeatureConfig> BLOSSOM_BERRY = new SinglePlantFeature(ModBlocks.BLOSSOM_BERRY.get(), 3, 3);
	public static final Feature<NoFeatureConfig> BLOOMING_COOKSONIA = new SinglePlantFeature(ModBlocks.BLOOMING_COOKSONIA.get(), 5);
	public static final Feature<NoFeatureConfig> SALTEAGO = new SinglePlantFeature(ModBlocks.SALTEAGO.get(), 5);
	public static final Feature<NoFeatureConfig> VAIOLUSH_FERN = new SinglePlantFeature(ModBlocks.VAIOLUSH_FERN.get(), 5);
	public static final Feature<NoFeatureConfig> FRACTURN = new SinglePlantFeature(ModBlocks.FRACTURN.get(), 5);
	public static final Feature<NoFeatureConfig> UMBRELLA_MOSS_RARE = new SinglePlantFeature(ModBlocks.UMBRELLA_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> CREEPING_MOSS_RARE = new SinglePlantFeature(ModBlocks.CREEPING_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> TWISTED_UMBRELLA_MOSS_RARE = new SinglePlantFeature(ModBlocks.TWISTED_UMBRELLA_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> LUMECORN = new Lumecorn();
	public static final Feature<NoFeatureConfig> SMALL_AMARANITA = new SinglePlantFeature(ModBlocks.SMALL_AMARANITA_MUSHROOM.get(), 5, 5);
	public static final Feature<NoFeatureConfig> GLOBULAGUS = new SinglePlantFeature(ModBlocks.GLOBULAGUS.get(), 5, 3);
	public static final Feature<NoFeatureConfig> CLAWFERN = new SinglePlantFeature(ModBlocks.CLAWFERN.get(), 5, 4);
	public static final Feature<NoFeatureConfig> AERIDIUM = new SinglePlantFeature(ModBlocks.AERIDIUM.get(), 5, 4);
	public static final Feature<NoFeatureConfig> LAMELLARIUM = new SinglePlantFeature(ModBlocks.LAMELLARIUM.get(), 5);
	public static final Feature<NoFeatureConfig> BOLUX_MUSHROOM = new SinglePlantFeature(ModBlocks.BOLUX_MUSHROOM.get(), 5, 5);
	public static final Feature<NoFeatureConfig> ORANGO = new SinglePlantFeature(ModBlocks.ORANGO.get(), 5);
	public static final Feature<NoFeatureConfig> LUTEBUS = new SinglePlantFeature(ModBlocks.LUTEBUS.get(), 5, 2);
	public static final Feature<NoFeatureConfig> INFLEXIA = new SinglePlantFeature(ModBlocks.INFLEXIA.get(), 7, false, 3);
	public static final Feature<NoFeatureConfig> FLAMMALIX = new SinglePlantFeature(ModBlocks.FLAMMALIX.get(), 3, false, 7);


	// SKY PLANTS
	public static final Feature<NoFeatureConfig> FILALUX = new FilaluxFeature();
	
	// WALL PLANTS
	public static final Feature<NoFeatureConfig> PURPLE_POLYPORE = new WallPlantOnLogFeature(ModBlocks.PURPLE_POLYPORE.get(), 3);
	public static final Feature<NoFeatureConfig> AURANT_POLYPORE = new WallPlantOnLogFeature(ModBlocks.AURANT_POLYPORE.get(), 3);
	public static final Feature<NoFeatureConfig> PURPLE_POLYPORE_DENSE = new WallPlantOnLogFeature(ModBlocks.PURPLE_POLYPORE.get(), 5);
	public static final Feature<NoFeatureConfig> TAIL_MOSS = new WallPlantFeature(ModBlocks.TAIL_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> TAIL_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.TAIL_MOSS.get(), 4);
	public static final Feature<NoFeatureConfig> CYAN_MOSS = new WallPlantFeature(ModBlocks.CYAN_MOSS.get(), 3);
	public static final Feature<NoFeatureConfig> CYAN_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.CYAN_MOSS.get(), 4);
	public static final Feature<NoFeatureConfig> TWISTED_MOSS = new WallPlantFeature(ModBlocks.TWISTED_MOSS.get(), 6);
	public static final Feature<NoFeatureConfig> TWISTED_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.TWISTED_MOSS.get(), 6);
	public static final Feature<NoFeatureConfig> BULB_MOSS = new WallPlantFeature(ModBlocks.BULB_MOSS.get(), 6);
	public static final Feature<NoFeatureConfig> BULB_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.BULB_MOSS.get(), 6);
	public static final Feature<NoFeatureConfig> SMALL_JELLYSHROOM_WALL = new WallPlantFeature(ModBlocks.SMALL_JELLYSHROOM.get(), 4);
	public static final Feature<NoFeatureConfig> SMALL_JELLYSHROOM_WOOD = new WallPlantOnLogFeature(ModBlocks.SMALL_JELLYSHROOM.get(), 4);
	public static final Feature<NoFeatureConfig> JUNGLE_FERN_WOOD = new WallPlantOnLogFeature(ModBlocks.JUNGLE_FERN.get(), 3);
	public static final Feature<NoFeatureConfig> RUSCUS = new WallPlantFeature(ModBlocks.RUSCUS.get(), 6);
	public static final Feature<NoFeatureConfig> RUSCUS_WOOD = new WallPlantOnLogFeature(ModBlocks.RUSCUS.get(), 6);
	
	// VINES
	public static final Feature<NoFeatureConfig> DENSE_VINE = new VineFeature(ModBlocks.DENSE_VINE.get(), 24);
	public static final Feature<NoFeatureConfig> TWISTED_VINE = new VineFeature(ModBlocks.TWISTED_VINE.get(), 24);
	public static final Feature<NoFeatureConfig> BULB_VINE = new VineFeature(ModBlocks.BULB_VINE.get(), 24);
	public static final Feature<NoFeatureConfig> JUNGLE_VINE = new VineFeature(ModBlocks.JUNGLE_VINE.get(), 24);
	
	// CEIL PLANTS
	public static final Feature<NoFeatureConfig> SMALL_JELLYSHROOM_CEIL = new SingleInvertedScatterFeature(ModBlocks.SMALL_JELLYSHROOM.get(), 8);
		
	// TERRAIN
	public static final Feature<NoFeatureConfig> ROUND_CAVE = new RoundCaveFeature();
	public static final Feature<NoFeatureConfig> END_LAKE = new EndLakeFeature();
	public static final Feature<NoFeatureConfig> END_LAKE_RARE = new EndLakeFeature();
	public static final Feature<NoFeatureConfig> DESERT_LAKE = new DesertLakeFeature();
	public static final Feature<NoFeatureConfig> SPIRE = new SpireFeature();
	public static final Feature<NoFeatureConfig> FLOATING_SPIRE = new FloatingSpireFeature();
	public static final Feature<NoFeatureConfig> GEYSER = new GeyserFeature();
	public static final Feature<NoFeatureConfig> SULPHURIC_LAKE = new SulphuricLakeFeature();
	public static final Feature<NoFeatureConfig> SULPHURIC_CAVE = new SulphuricCaveFeature();
	public static final Feature<NoFeatureConfig> SURFACE_VENT = new SurfaceVentFeature();
	public static final Feature<NoFeatureConfig> ICE_STAR = new IceStarFeature(5, 15, 10, 25);
	public static final Feature<NoFeatureConfig> ICE_STAR_SMALL = new IceStarFeature(3, 5, 7, 12);
	public static final Feature<NoFeatureConfig> OVERWORLD_ISLAND = new OverworldIslandFeature();
	public static final Feature<NoFeatureConfig> OBSIDIAN_PILLAR_BASEMENT = new ObsidianPillarBasementFeature();
	public static final Feature<NoFeatureConfig> FALLEN_PILLAR = new FallenPillarFeature();
	public static final Feature<NoFeatureConfig> OBSIDIAN_BOULDER = new ObsidianBoulderFeature();
	public static final Feature<NoFeatureConfig> TUNEL_CAVE = new TunelCaveFeature();
	public static final Feature<NoFeatureConfig> THIN_ARCH = new ThinArchFeature(ModBlocks.UMBRALITH.stone.get());
	public static final Feature<NoFeatureConfig> UMBRALITH_ARCH = new ArchFeature(ModBlocks.UMBRALITH.stone.get(), UmbraSurfaceBuilder::getSurfaceState);


	// TREES
	public static final Feature<NoFeatureConfig> MOSSY_GLOWSHROOM = new MossyGlowshroomFeature();
	public static final Feature<NoFeatureConfig> LACUGROVE = new LacugroveFeature();
	public static final Feature<NoFeatureConfig> PYTHADENDRON = new PythadendronFeature();
	public static final Feature<NoFeatureConfig> DRAGON_TREE = new DragonTreeFeature();
	public static final Feature<NoFeatureConfig> TENANEA = new TenaneaFeature();
	public static final Feature<NoFeatureConfig> HELIX_TREE = new HelixTreeFeature();
	public static final Feature<NoFeatureConfig> UMBRELLA_TREE = new UmbrellaTreeFeature();
	public static final Feature<NoFeatureConfig> JELLYSHROOM = new JellyshroomFeature();
	public static final Feature<NoFeatureConfig> GIGANTIC_AMARANITA = new GiganticAmaranitaFeature();
	public static final Feature<NoFeatureConfig> LUCERNIA = new LucerniaFeature();
	
	// ORES
	public static final Feature<OreFeatureConfig> ENDER_ORE = new OreFeature(OreFeatureConfig.CODEC);
	public static final Feature<OreFeatureConfig> AMBER_ORE = new OreFeature(OreFeatureConfig.CODEC);
	public static final Feature<OreFeatureConfig> THALLASIUM_ORE = new OreFeature(OreFeatureConfig.CODEC);
	public static final Feature<NoFeatureConfig> FLAVOLITE_LAYER = new OreLayerFeature(ModBlocks.FLAVOLITE.stone.get().getDefaultState(), 12, 4, 96);
	public static final Feature<NoFeatureConfig> VIOLECITE_LAYER = new OreLayerFeature(ModBlocks.VIOLECITE.stone.get().getDefaultState(), 15, 4, 96);
	
	// BUILDINGS
	public static final Feature<NoFeatureConfig> CRASHED_SHIP = new CrashedShipFeature();
	public static final Feature<NoFeatureConfig> NBT_STRUCTURES = new BiomeNBTStructures();
	
	// Mobs
	public static final Feature<NoFeatureConfig> SILK_MOTH_NEST = new SilkMothNestFeature();
	
	// Caves
    public static final Feature<NoFeatureConfig> SMARAGDANT_CRYSTAL = new SmaragdantCrystalFeature();
    public static final Feature<NoFeatureConfig> SMARAGDANT_CRYSTAL_SHARD = new SingleBlockFeature(ModBlocks.SMARAGDANT_CRYSTAL_SHARD.get());
    public static final Feature<NoFeatureConfig> BIG_AURORA_CRYSTAL = new BigAuroraCrystalFeature();
	public static final Feature<NoFeatureConfig> END_STONE_STALACTITE = new StalactiteFeature(true, ModBlocks.END_STONE_STALACTITE.get(), Blocks.END_STONE);
	public static final Feature<NoFeatureConfig> END_STONE_STALAGMITE = new StalactiteFeature(false, ModBlocks.END_STONE_STALACTITE.get(), Blocks.END_STONE);
	public static final Feature<NoFeatureConfig> END_STONE_STALACTITE_CAVEMOSS = new StalactiteFeature(true, ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get(), Blocks.END_STONE, ModBlocks.CAVE_MOSS.get());
	public static final Feature<NoFeatureConfig> END_STONE_STALAGMITE_CAVEMOSS = new StalactiteFeature(false, ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get(), ModBlocks.CAVE_MOSS.get());
	public static final Feature<NoFeatureConfig> CAVE_BUSH = new BushFeature(ModBlocks.CAVE_BUSH.get(), ModBlocks.CAVE_BUSH.get());
	public static final Feature<NoFeatureConfig> CAVE_GRASS = new SingleBlockFeature(ModBlocks.CAVE_GRASS.get());
	public static final Feature<NoFeatureConfig> RUBINEA = new VineFeature(ModBlocks.RUBINEA.get(), 8);

	//Integration

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
    	BetterEnd.register(event.getRegistry(), FLAMAEA, "flamaea");
    	BetterEnd.register(event.getRegistry(), POND_ANEMONE, "pond_anemone");
    	//BUSHES
    	BetterEnd.register(event.getRegistry(), PYTHADENDRON_BUSH, "pythadendron_bush"); 
    	BetterEnd.register(event.getRegistry(), DRAGON_TREE_BUSH, "dragon_tree_bush"); 
    	BetterEnd.register(event.getRegistry(), TENANEA_BUSH, "tenanea_bush"); 
    	BetterEnd.register(event.getRegistry(), LARGE_AMARANITA, "large_amaranita"); 
    	BetterEnd.register(event.getRegistry(), LUCERNIA_BUSH, "lucernia_bush");
		BetterEnd.register(event.getRegistry(), LUCERNIA_BUSH_RARE, "lucernia_bush_rare");
		BetterEnd.register(event.getRegistry(), NEON_CACTUS, "neon_cactus");
    	// PLANTS
    	BetterEnd.register(event.getRegistry(), UMBRELLA_MOSS, "umbrella_moss");
    	BetterEnd.register(event.getRegistry(), CREEPING_MOSS, "creeping_moss");   	
    	BetterEnd.register(event.getRegistry(), CHORUS_GRASS, "chorus_grass");
    	BetterEnd.register(event.getRegistry(), CRYSTAL_GRASS, "crystal_grass");
    	BetterEnd.register(event.getRegistry(), AMBER_GRASS, "amber_grass");
    	BetterEnd.register(event.getRegistry(), SHADOW_PLANT, "shadow_plant");  	
    	BetterEnd.register(event.getRegistry(), BLUE_VINE, "blue_vine"); 
    	BetterEnd.register(event.getRegistry(), MURKWEED, "murkweed");  	
    	BetterEnd.register(event.getRegistry(), NEEDLEGRASS, "needlegrass"); 
    	BetterEnd.register(event.getRegistry(), SHADOW_BERRY, "shadow_berry"); 
    	BetterEnd.register(event.getRegistry(), BUSHY_GRASS, "bushy_grass"); 
    	BetterEnd.register(event.getRegistry(), BUSHY_GRASS_WG, "bushy_grass_wg"); 
    	BetterEnd.register(event.getRegistry(), LANCELEAF, "lanceleaf"); 
    	BetterEnd.register(event.getRegistry(), GLOW_PILLAR, "glow_pillar"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_UMBRELLA_MOSS, "twisted_umbrella_moss");
    	BetterEnd.register(event.getRegistry(), JUNGLE_GRASS, "jungle_grass");
    	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_FLOOR, "small_jellyshroom_floor");
    	BetterEnd.register(event.getRegistry(), BLOSSOM_BERRY, "blossom_berry");
    	BetterEnd.register(event.getRegistry(), LUMECORN, "lumecorn");
    	BetterEnd.register(event.getRegistry(), BLOOMING_COOKSONIA, "blooming_cooksonia");
    	BetterEnd.register(event.getRegistry(), SALTEAGO, "salteago");
    	BetterEnd.register(event.getRegistry(), VAIOLUSH_FERN, "vaiolush_fern");
    	BetterEnd.register(event.getRegistry(), FRACTURN, "fracturn");
    	BetterEnd.register(event.getRegistry(), UMBRELLA_MOSS_RARE, "umbrella_moss_rare");
    	BetterEnd.register(event.getRegistry(), CREEPING_MOSS_RARE, "creeping_moss_rare");
    	BetterEnd.register(event.getRegistry(), TWISTED_UMBRELLA_MOSS_RARE, "twisted_umbrella_moss_rare");
    	BetterEnd.register(event.getRegistry(), SMALL_AMARANITA, "small_amaranita");
    	BetterEnd.register(event.getRegistry(), GLOBULAGUS, "globulagus");
    	BetterEnd.register(event.getRegistry(), CLAWFERN, "clawfern");
    	BetterEnd.register(event.getRegistry(), AERIDIUM, "aeridium");
    	BetterEnd.register(event.getRegistry(), LAMELLARIUM, "lamellarium");
    	BetterEnd.register(event.getRegistry(), BOLUX_MUSHROOM, "bolux_mushroom");
    	BetterEnd.register(event.getRegistry(), ORANGO, "orango");
    	BetterEnd.register(event.getRegistry(), LUTEBUS, "lutebus");
		BetterEnd.register(event.getRegistry(), FLAMMALIX, "flammalix");
		BetterEnd.register(event.getRegistry(), INFLEXIA, "inflexia");
		BetterEnd.register(event.getRegistry(), AMBER_ROOT, "amber_root");
    	// SKY PLANTS
    	BetterEnd.register(event.getRegistry(), FILALUX, "filalux");    	
    	// WALL_PLANTS
    	BetterEnd.register(event.getRegistry(), PURPLE_POLYPORE, "purple_polypore");
    	BetterEnd.register(event.getRegistry(), AURANT_POLYPORE, "aurant_polypore");
    	BetterEnd.register(event.getRegistry(), PURPLE_POLYPORE_DENSE, "purple_polypore_dense");
    	BetterEnd.register(event.getRegistry(), TAIL_MOSS, "tail_moss");
    	BetterEnd.register(event.getRegistry(), TAIL_MOSS_WOOD, "tail_moss_wood");  	
    	BetterEnd.register(event.getRegistry(), CYAN_MOSS, "cyan_moss"); 
    	BetterEnd.register(event.getRegistry(), CYAN_MOSS_WOOD, "cyan_moss_wood"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_MOSS, "twisted_moss"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_MOSS_WOOD, "twisted_moss_wood"); 
    	BetterEnd.register(event.getRegistry(), BULB_MOSS, "bulb_moss"); 
    	BetterEnd.register(event.getRegistry(), BULB_MOSS_WOOD, "bulb_moss_wood");
       	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_WALL, "small_jellyshroom_wall"); 
    	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_WOOD, "small_jellyshroom_wood");
    	BetterEnd.register(event.getRegistry(), JUNGLE_FERN_WOOD, "jungle_fern_wood");
    	BetterEnd.register(event.getRegistry(), RUSCUS, "ruscus");
    	BetterEnd.register(event.getRegistry(), RUSCUS_WOOD, "ruscus_wood");
    	// VINES
    	BetterEnd.register(event.getRegistry(), DENSE_VINE, "dense_vine"); 
    	BetterEnd.register(event.getRegistry(), TWISTED_VINE, "twisted_vine"); 
    	BetterEnd.register(event.getRegistry(), BULB_VINE, "bulb_vine"); 
    	BetterEnd.register(event.getRegistry(), JUNGLE_VINE, "jungle_vine"); 
    	// CEIL PLANTS
    	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_CEIL, "small_jellyshroom_ceil"); 
    	// TERRAIN
    	BetterEnd.register(event.getRegistry(), ROUND_CAVE, "round_cave");
    	BetterEnd.register(event.getRegistry(), END_LAKE, "end_lake");   
    	BetterEnd.register(event.getRegistry(), END_LAKE_RARE, "end_lake_rare"); 
    	BetterEnd.register(event.getRegistry(), DESERT_LAKE, "desert_lake"); 
    	BetterEnd.register(event.getRegistry(), SPIRE, "spire");   
    	BetterEnd.register(event.getRegistry(), FLOATING_SPIRE, "floating_spire");
    	BetterEnd.register(event.getRegistry(), GEYSER, "geyser");
    	BetterEnd.register(event.getRegistry(), SULPHURIC_LAKE, "sulphuric_lake");
    	BetterEnd.register(event.getRegistry(), SULPHURIC_CAVE, "sulphuric_cave");
    	BetterEnd.register(event.getRegistry(), SURFACE_VENT, "surface_vent");
    	BetterEnd.register(event.getRegistry(), ICE_STAR, "ice_star");
    	BetterEnd.register(event.getRegistry(), ICE_STAR_SMALL, "ice_star_small");
    	BetterEnd.register(event.getRegistry(), OVERWORLD_ISLAND, "overworld_island");
    	BetterEnd.register(event.getRegistry(), OBSIDIAN_PILLAR_BASEMENT, "obsidian_pillar_basement");
    	BetterEnd.register(event.getRegistry(), FALLEN_PILLAR, "fallen_pillar");
    	BetterEnd.register(event.getRegistry(), OBSIDIAN_BOULDER, "obsidian_boulder");
		BetterEnd.register(event.getRegistry(), THIN_ARCH, "thin_arch");
		BetterEnd.register(event.getRegistry(), UMBRALITH_ARCH, "umbralith_arch");

		// TREES
    	BetterEnd.register(event.getRegistry(), MOSSY_GLOWSHROOM, "mossy_glowshroom");
    	BetterEnd.register(event.getRegistry(), LACUGROVE, "lacugrove");   
    	BetterEnd.register(event.getRegistry(), PYTHADENDRON, "pythadendron"); 
    	BetterEnd.register(event.getRegistry(), DRAGON_TREE, "dragon_tree");
    	BetterEnd.register(event.getRegistry(), TENANEA, "tenanea");
    	BetterEnd.register(event.getRegistry(), HELIX_TREE, "helix_tree");
    	BetterEnd.register(event.getRegistry(), UMBRELLA_TREE, "umbrella_tree");
    	BetterEnd.register(event.getRegistry(), JELLYSHROOM, "jellyshroom");
    	BetterEnd.register(event.getRegistry(), GIGANTIC_AMARANITA, "gigantic_amaranita");
    	BetterEnd.register(event.getRegistry(), LUCERNIA, "lucernia");
    	// ORES
    	BetterEnd.register(event.getRegistry(), THALLASIUM_ORE, "thallasium_ore");
    	BetterEnd.register(event.getRegistry(), ENDER_ORE, "ender_ore");
    	BetterEnd.register(event.getRegistry(), AMBER_ORE, "amber_ore");
    	BetterEnd.register(event.getRegistry(), FLAVOLITE_LAYER, "flavolite_layer");
    	BetterEnd.register(event.getRegistry(), VIOLECITE_LAYER, "violecite_layer");
    	// BUILDINGS
    	BetterEnd.register(event.getRegistry(), CRASHED_SHIP, "crashed_ship");
    	BetterEnd.register(event.getRegistry(), NBT_STRUCTURES, "nbt_structures");    	
    	// MOBS
    	BetterEnd.register(event.getRegistry(), SILK_MOTH_NEST, "silk_moth_nest");
    	// CAVES
    	BetterEnd.register(event.getRegistry(), SMARAGDANT_CRYSTAL, "smaragdant_crystal");
    	BetterEnd.register(event.getRegistry(), SMARAGDANT_CRYSTAL_SHARD, "smaragdant_crystal_shard");
    	BetterEnd.register(event.getRegistry(), BIG_AURORA_CRYSTAL, "big_aurora_crystal");
    	BetterEnd.register(event.getRegistry(), END_STONE_STALACTITE, "end_stone_stalactite");
    	BetterEnd.register(event.getRegistry(), END_STONE_STALAGMITE, "end_stone_stalagmite");
    	BetterEnd.register(event.getRegistry(), END_STONE_STALACTITE_CAVEMOSS, "end_stone_stalactite_cavemoss");
    	BetterEnd.register(event.getRegistry(), END_STONE_STALAGMITE_CAVEMOSS, "end_stone_stalagmite_cavemoss");
    	BetterEnd.register(event.getRegistry(), CAVE_BUSH, "cave_bush");
      	BetterEnd.register(event.getRegistry(), CAVE_GRASS, "cave_grass");
      	BetterEnd.register(event.getRegistry(), RUBINEA, "rubinea");
		BetterEnd.register(event.getRegistry(), TUNEL_CAVE, "tunel_cave");

		//Integration
	}





}
