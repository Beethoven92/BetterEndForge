package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class BlossomingSpiresBiome extends BetterEndBiome
{
	public BlossomingSpiresBiome() 
	{
		super(new BiomeTemplate("blossoming_spires").
				setFogColor(241, 146, 229).
				setFogDensity(1.7F).
				setGrassColor(122, 45, 122).
				setFoliageColor(122, 45, 122).
				setCaves(false).
				setSurface(ModBlocks.PINK_MOSS.get()).
				setAmbientSound(ModSoundEvents.AMBIENT_BLOSSOMING_SPIRES.get()).
				setMusic(ModSoundEvents.MUSIC_FOREST.get()).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.SPIRE).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.FLOATING_SPIRE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TENANEA).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TENANEA_BUSH).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BULB_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUSHY_GRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUSHY_GRASS_WG).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLOSSOM_BERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLOSSOM_BERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SILK_MOTH_NEST).
				addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 4).
		        addMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.SILK_MOTH.get(), 5, 1, 2));
	}
}
