package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class AmberLandBiome extends BetterEndBiome {
	public AmberLandBiome() {
		super(new BiomeTemplate("amber_land")
				.setFogColor(255, 184, 71)
				.setFogDensity(2.0F)
				.setFoliageColor(219, 115, 38)
				.setGrassColor(219, 115, 38)
				.setWaterFogColor(145, 108, 72)
				.setMusic(ModSoundEvents.MUSIC_FOREST.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_AMBER_LAND.get())
				.setParticles(ModParticleTypes.AMBER_SPHERE.get(), 0.001F)
				.setSurface(ModBlocks.AMBER_MOSS.get())
				.addFeature(Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.AMBER_ORE)
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.HELIX_TREE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LANCELEAF)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GLOW_PILLAR)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AMBER_GRASS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AMBER_ROOT)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BULB_MOSS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BULB_MOSS_WOOD)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_ORANGE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED)
				.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 4));
	}
}
