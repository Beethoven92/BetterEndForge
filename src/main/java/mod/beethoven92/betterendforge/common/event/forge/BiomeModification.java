package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.DecoratedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class BiomeModification 
{
	@SubscribeEvent(priority = EventPriority.HIGH)
    public static void addFeaturesToEndBiomes(final BiomeLoadingEvent event) 
	{
		if (event.getCategory() == BiomeCategory.THEEND)
		{
			if (event.getName() == null) return;
			
			// Add surface structures to biomes
			if (!event.getName().getPath().contains("mountain") && 
					!event.getName().getPath().contains("lake"))
			{
			    event.getGeneration().getStructures().add(() -> ModConfiguredStructures.ETERNAL_PORTAL);
			}
			event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).add(() -> ModConfiguredFeatures.CRASHED_SHIP);
			event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(() -> ModConfiguredFeatures.TUNEL_CAVE);
			// Add ores to all biomes
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.THALLASIUM_ORE);
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.ENDER_ORE);
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.FLAVOLITE_LAYER);
			
			
			// Add end caves to biomes that have caves enabled
			if (ModBiomes.getBiome(event.getName()).hasCaves()) 
			{
	  			event.getGeneration().getFeatures(Decoration.RAW_GENERATION).add(() -> ModConfiguredFeatures.ROUND_CAVE);
			}
			
			// Add scattered nbt structures to biomes
			if (!ModBiomes.getBiome(event.getName()).getNBTStructures().isEmpty())
			{
				event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).add(() -> ModConfiguredFeatures.NBT_STRUCTURES);
			}
			
			// If the Deadly End Phantoms mod is installed, their specter will spawn in shadow forest
			// instead of vanilla phantoms
			if (event.getName().equals(ModBiomes.SHADOW_FOREST.getID()))
			{
				MobSpawnSettings.SpawnerData phantom = new MobSpawnSettings.SpawnerData(EntityType.byString("deadlyendphantoms:specter").orElse(EntityType.PHANTOM), 10, 1, 2);
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(phantom);
			}
		}
    }
    
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public static void removeChorusFromVanillaBiomes(final BiomeLoadingEvent event) 
    {	   	    
		if (GeneratorOptions.removeChorusFromVanillaBiomes())
		{
			if (event.getCategory() == BiomeCategory.THEEND) 
			{
				if (event.getName() == null || !event.getName().getNamespace().equals("minecraft")) return;
				
				String path = event.getName().getPath();
				if (path.equals("end_highlands") || path.equals("end_midlands") || path.equals("small_end_islands"))
				{   
					event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION).removeIf((supplier) -> 
					{
						ConfiguredFeature<?, ?> feature = supplier.get();
						
						// Retrieve the original feature
						while(feature.feature() instanceof DecoratedFeature)
						{
							feature = ((DecoratedFeatureConfiguration)feature.config()).feature.get();
						}
						
			            if (feature.feature instanceof ChorusPlantFeature) 
			            {
			            	return true;
			            }
			            return false;
			        });
				}
			}
		}
    }
}
