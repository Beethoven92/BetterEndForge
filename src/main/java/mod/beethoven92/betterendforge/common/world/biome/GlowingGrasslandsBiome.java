package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class GlowingGrasslandsBiome extends BetterEndBiome 
{
	public GlowingGrasslandsBiome() {
		super(new BiomeTemplate("glowing_grasslands")
				.setWaterColor(92, 250, 230)
				.setWaterFogColor(92, 250, 230)
				.setFoliageColor(73, 210, 209)
				.setGrassColor(73, 210, 209)
				.setFogColor(99, 228, 247)				
				.setFogDensity(1.3F)
				.setMusic(ModSoundEvents.MUSIC_OPENSPACE.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_GLOWING_GRASSLANDS.get())
				.setParticles(ModParticleTypes.FIREFLY.get(), 0.001F)
				.setSurface(ModBlocks.END_MOSS.get())
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUMECORN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLOOMING_COOKSONIA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SALTEAGO)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.VAIOLUSH_FERN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FRACTURN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_UMBRELLA_MOSS_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_LIGHT_BLUE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE)
				.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
