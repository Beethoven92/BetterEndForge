package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class MegalakeGroveBiome extends BetterEndBiome 
{
	public MegalakeGroveBiome() 
	{
		super(new BiomeTemplate("megalake_grove").
				setFoliageColor(73, 210, 209).
				setGrassColor(73, 210, 209).
				setFogColor(178, 209, 248).
				setWaterColor(96, 163, 255).
				setWaterFogColor(96, 163, 255).
				setFogDensity(2.0F).
				setParticles(ModParticleTypes.GLOWING_SPHERE.get(), 0.001F).
				setMusic(ModSoundEvents.MUSIC_MEGALAKE.get()).
				setAmbientSound(ModSoundEvents.AMBIENT_MEGALAKE_GROVE.get()).
				setSurface(ModBlocks.END_MOSS.get()).
				addStructure(ModConfiguredStructures.MEGALAKE_STRUCTURE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LACUGROVE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LOTUS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LOTUS_LEAF).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUBBLE_CORAL_RARE).
			    addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LILY_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS).
				setMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.DRAGONFLY.get(), 20, 1, 3).
				setMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.END_FISH.get(), 20, 3, 8).
				setMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 2));
	}
}
