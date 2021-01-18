package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredSurfaceBuilders;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class MegalakeBiome extends BetterEndBiome
{
	public MegalakeBiome()
	{
		super(new BiomeTemplate("megalake").
				setGrassColor(73, 210, 209).
				setFoliageColor(73, 210, 209).
				setFogColor(178, 209, 248).
				setWaterColor(96, 163, 255).
				setWaterFogColor(96, 163, 255).
				setFogDensity(1.75F).
				setMusic(ModSoundEvents.MUSIC_WATER.get()).
				setAmbientSound(ModSoundEvents.AMBIENT_MEGALAKE.get()).
				setSurface(ModConfiguredSurfaceBuilders.get(ModConfiguredSurfaceBuilders.MEGALAKE_SURFACE)).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUBBLE_CORAL_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LOTUS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LOTUS_LEAF).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LILY_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MENGER_SPONGE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_LIGHT_BLUE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				addStructure(ModConfiguredStructures.MEGALAKE_STRUCTURE).
				addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 2).
				addMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.DRAGONFLY.get(), 50, 1, 3).
				addMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.CUBOZOA.get(), 50, 3, 8).
				addMobSpawn(EntityClassification.WATER_AMBIENT, ModEntityTypes.END_FISH.get(), 50, 3, 8));
	}
}
