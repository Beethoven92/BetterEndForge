package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class ShadowForestBiome extends BetterEndBiome
{
	public ShadowForestBiome() 
	{
		super(new BiomeTemplate("shadow_forest").
				setFogColor(0, 0, 0).
				setFogDensity(2.5F).
				setGrassColor(45, 45, 45).
				setFoliageColor(45, 45, 45).
				setWaterColor(42, 45, 80).
				setWaterFogColor(42, 45, 80).
				setSurface(ModBlocks.SHADOW_GRASS.get()).
				setParticles(ParticleTypes.MYCELIUM, 0.01F).
				setAmbientSound(ModSoundEvents.AMBIENT_CHORUS_FOREST.get()).
				setMusic(ModSoundEvents.MUSIC_DARK.get()).
				addFeature(Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.VIOLECITE_LAYER).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DRAGON_TREE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DRAGON_TREE_BUSH).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SHADOW_PLANT).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MURKWEED).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.NEEDLEGRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SHADOW_BERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PURPLE_POLYPORE_DENSE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_PURPLE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				addStructure(StructureFeatures.END_CITY).
				addMobSpawn(EntityClassification.MONSTER, ModEntityTypes.SHADOW_WALKER.get(), 80, 2, 4).
				addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 40, 1, 4));
	}
}
