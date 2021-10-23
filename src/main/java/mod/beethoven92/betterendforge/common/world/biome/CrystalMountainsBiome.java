package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class CrystalMountainsBiome extends BetterEndBiome
{
	public CrystalMountainsBiome() 
	{
		super(new BiomeTemplate("crystal_mountains").setGrassColor(255, 133, 211).
				                  setFoliageColor(255, 133, 211).
				                  setMusic(ModSoundEvents.MUSIC_OPENSPACE.get()).
				                  setSurface(ModBlocks.CRYSTAL_MOSS.get()).
				                  addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.ROUND_CAVE).
				                  addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CRYSTAL_GRASS).
				                  addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CAVE_GRASS).
				                  addStructure(ModConfiguredStructures.MOUNTAIN_STRUCTURE).
				                  addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
