package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class LanternWoodsBiome extends BetterEndBiome {
	public LanternWoodsBiome() {
		super(new BiomeTemplate("lantern_woods")
				.setFogColor(189, 82, 70)
				.setFogDensity(1.1F)
				.setWaterFogColor(171, 234, 226)
				.setWaterColor(171, 234, 226)
				.setFoliageColor(254, 85, 57)
				.setSurface(ModBlocks.RUTISCUS.get())
				.setMusic(ModSoundEvents.MUSIC_FOREST.get())
				.setParticles(ModParticleTypes.GLOWING_SPHERE.get(), 0.001F)
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_NORMAL)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FLAMAEA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUCERNIA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUCERNIA_BUSH)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FILALUX)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AERIDIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LAMELLARIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BOLUX_MUSHROOM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AURANT_POLYPORE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.POND_ANEMONE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_ORANGE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.RUSCUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.RUSCUS_WOOD)
				.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
