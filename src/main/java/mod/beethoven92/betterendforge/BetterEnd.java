package mod.beethoven92.betterendforge;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.beethoven92.betterendforge.client.PhysicalClientSide;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModContainerTypes;
import mod.beethoven92.betterendforge.common.init.ModEffects;
import mod.beethoven92.betterendforge.common.init.ModEnchantments;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModPotions;
import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.init.ModStructures;
import mod.beethoven92.betterendforge.common.init.ModSurfaceBuilders;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.world.TerraforgedIntegrationWorldType;
import mod.beethoven92.betterendforge.common.world.feature.BiomeNBTStructures;
import mod.beethoven92.betterendforge.common.world.generator.BetterEndBiomeProvider;
import mod.beethoven92.betterendforge.config.ClientConfig;
import mod.beethoven92.betterendforge.config.CommonConfig;
import mod.beethoven92.betterendforge.server.PhysicalServerSide;


@Mod(BetterEnd.MOD_ID)
public class BetterEnd
{
	public static final String MOD_ID = "betterendforge";

    public static final Logger LOGGER = LogManager.getLogger();
    
	public static final IPhysicalSide SIDE = 
			DistExecutor.safeRunForDist(() -> PhysicalClientSide::new, () -> PhysicalServerSide::new);

	public static final Path CONFIG_PATH = new File(String.valueOf(FMLPaths.CONFIGDIR.get().resolve(MOD_ID))).toPath();
    
	public BetterEnd() 
    {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
    	
    	SIDE.setup(modEventBus, forgeEventBus);

    	modEventBus.addListener(this::setupCommon);

    	ModBlocks.BLOCKS.register(modEventBus);
    	ModItems.ITEMS.register(modEventBus);
    	ModSurfaceBuilders.SURFACE_BUILDERS.register(modEventBus);
    	ModEnchantments.ENCHANTMENTS.register(modEventBus);
    	ModEffects.EFFECTS.register(modEventBus);
    	ModPotions.POTIONS.register(modEventBus);
    	ModParticleTypes.PARTICLE_TYPES.register(modEventBus);
    	ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
    	ModEntityTypes.ENTITY_TYPES.register(modEventBus);
    	ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
    	ModSoundEvents.SOUND_EVENTS.register(modEventBus);
    	ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        
    	File configDirectory = new File(CONFIG_PATH.toString());
        if (!configDirectory.exists()) configDirectory.mkdir();
    	
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.getConfig(), CONFIG_PATH.resolve("client-config.toml").toString());
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.getConfig(), CONFIG_PATH.resolve("world-generator-config.toml").toString());
    }

    private void setupCommon(final FMLCommonSetupEvent event)
    {   	
    	event.enqueueWork(() -> {
        	BetterEndBiomeProvider.register();
    		ModEntityTypes.registerGlobalEntityAttributes();
    		ModEntityTypes.registerEntitySpawns();
    	    BiomeNBTStructures.loadStructures();
    	});    
    }    
    
    @Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = Bus.MOD)
    public static class WorldGenRegistryEvents
    {
    	@SubscribeEvent
    	public static void registerBiomes(RegistryEvent.Register<Biome> event) 
    	{
    		ModBiomes.register();
    		ModBiomes.getModBiomes().forEach((end_biome) -> event.getRegistry().register(end_biome.getBiome()));
    	}
    	
    	@SubscribeEvent
    	public static void registerStructures(RegistryEvent.Register<Structure<?>> event) 
    	{
    		ModStructures.registerStructures(event);
    		ModConfiguredStructures.registerConfiguredStructures();
    	}
    	
    	@SubscribeEvent
    	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) 
    	{
    		ModFeatures.registerFeatures(event);
    		ModConfiguredFeatures.registerConfiguredFeatures();
    	}
        
    	//Terraforge integration
        @SubscribeEvent
        public static void registerWorldtype(RegistryEvent.Register<ForgeWorldType> event)
        {
            event.getRegistry().register(new TerraforgedIntegrationWorldType().setRegistryName(new ResourceLocation(MOD_ID, "world_type")));
        }
    }
    
    @Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
    public static class ForgeEvents
    {      	
    	@SubscribeEvent(priority = EventPriority.HIGH)
        public static void biomeModification(final BiomeLoadingEvent event) 
    	{
    		if (event.getCategory() == Category.THEEND)
    		{
    			if (event.getName() == null) return;
    			
    			if (!event.getName().getPath().contains("mountain") && 
    					!event.getName().getPath().contains("lake"))
    			{
    			    event.getGeneration().getStructures().add(() -> ModConfiguredStructures.ETERNAL_PORTAL);
    			}
    			
    			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.ENDER_ORE);
    			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.FLAVOLITE_LAYER);
    			event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).add(() -> ModConfiguredFeatures.CRASHED_SHIP);
    			
    			if (ModBiomes.getBiome(event.getName()).hasCaves()) 
    			{
    	  			event.getGeneration().getFeatures(Decoration.RAW_GENERATION).add(() -> ModConfiguredFeatures.ROUND_CAVE_RARE);
        			event.getGeneration().getFeatures(Decoration.RAW_GENERATION).add(() -> ModConfiguredFeatures.CAVE_GRASS);
    			}
    			
    			if (!ModBiomes.getBiome(event.getName()).getNBTStructures().isEmpty())
    			{
    				event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).add(() -> ModConfiguredFeatures.NBT_STRUCTURES);
    			}
    		}
        }
    	
    	// Thanks to TelepathicGrunt (https://github.com/TelepathicGrunt) for this code
    	@SubscribeEvent
    	public static void addDimensionalSpacing(final WorldEvent.Load event) 
    	{
    		if(event.getWorld() instanceof ServerWorld)
    		{  
    			ServerWorld serverWorld = (ServerWorld)event.getWorld();
    			
    			// Initialize biome registry if it was not initialized during world generation
    			// (example, when using mods/datapacks, that overrides the End generation)
    			ModBiomes.initRegistry(serverWorld.getServer());
    			
    			LOGGER.debug("BETTER_END_FORGE: registering structure separation settings for dimension " +
    					serverWorld.getDimensionKey().getLocation());
    			
    			// Prevent spawning of structures in Vanilla's superflat world
    			if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
    					serverWorld.getDimensionKey().equals(World.OVERWORLD))
    			{
    				return;
    			}

    			// Need temp map as some mods use custom chunk generators with immutable maps in themselves.
    			Map<Structure<?>, StructureSeparationSettings> tempMap = 
    					new HashMap<>(serverWorld.getChunkProvider().getChunkGenerator().func_235957_b_().func_236195_a_());
    			tempMap.put(ModStructures.MOUNTAIN, 
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.MOUNTAIN));
    			tempMap.put(ModStructures.MEGALAKE,
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.MEGALAKE));
    			tempMap.put(ModStructures.MEGALAKE_SMALL,
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.MEGALAKE_SMALL));
    			tempMap.put(ModStructures.GIANT_MOSSY_GLOWSHROOM,
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.GIANT_MOSSY_GLOWSHROOM));
    			tempMap.put(ModStructures.PAINTED_MOUNTAIN, 
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.PAINTED_MOUNTAIN));
    			tempMap.put(ModStructures.ETERNAL_PORTAL, 
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.ETERNAL_PORTAL));
    			tempMap.put(ModStructures.GIANT_ICE_STAR, 
    					DimensionStructuresSettings.field_236191_b_.get(ModStructures.GIANT_ICE_STAR));
    			serverWorld.getChunkProvider().getChunkGenerator().func_235957_b_().field_236193_d_ = tempMap;
    		}
    	}
    }

    // Registration helper
    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) 
    {
        entry.setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, registryKey));
        registry.register(entry);
        return entry;
    }
}
