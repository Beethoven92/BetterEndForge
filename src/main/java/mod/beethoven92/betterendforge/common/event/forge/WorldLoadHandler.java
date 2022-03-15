package mod.beethoven92.betterendforge.common.event.forge;

import java.util.HashMap;
import java.util.Map;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModStructures;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class WorldLoadHandler 
{
   	// Thanks to TelepathicGrunt (https://github.com/TelepathicGrunt) for this code
	@SubscribeEvent
	public static void addDimensionalSpacing(final WorldEvent.Load event) 
	{
		if(event.getWorld() instanceof ServerLevel)
		{  
			ServerLevel serverWorld = (ServerLevel)event.getWorld();
			
			// Initialize biome registry if it was not initialized during world generation
			// (example, when using mods/datapacks, that overrides the End generation)
			ModBiomes.initRegistry(serverWorld.getServer());
			
			BetterEnd.LOGGER.debug("BETTER_END_FORGE: registering structure separation settings for dimension " +
					serverWorld.dimension().location());
			
			// Prevent spawning of structures in Vanilla's superflat world
			if(serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource &&
					serverWorld.dimension().equals(Level.OVERWORLD))
			{
				return;
			}

			// Need temp map as some mods use custom chunk generators with immutable maps in themselves.
			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = 
					new HashMap<>(serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
			tempMap.put(ModStructures.MOUNTAIN, 
					StructureSettings.DEFAULTS.get(ModStructures.MOUNTAIN));
			tempMap.put(ModStructures.MEGALAKE,
					StructureSettings.DEFAULTS.get(ModStructures.MEGALAKE));
			tempMap.put(ModStructures.MEGALAKE_SMALL,
					StructureSettings.DEFAULTS.get(ModStructures.MEGALAKE_SMALL));
			tempMap.put(ModStructures.GIANT_MOSSY_GLOWSHROOM,
					StructureSettings.DEFAULTS.get(ModStructures.GIANT_MOSSY_GLOWSHROOM));
			tempMap.put(ModStructures.PAINTED_MOUNTAIN, 
					StructureSettings.DEFAULTS.get(ModStructures.PAINTED_MOUNTAIN));
			tempMap.put(ModStructures.ETERNAL_PORTAL,
					StructureSettings.DEFAULTS.get(ModStructures.ETERNAL_PORTAL));
			tempMap.put(ModStructures.GIANT_ICE_STAR,
					StructureSettings.DEFAULTS.get(ModStructures.GIANT_ICE_STAR));
			serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
		}
	}
}
