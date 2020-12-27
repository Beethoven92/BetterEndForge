package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class SulphurSpringsBiome extends BetterEndBiome
{
	public SulphurSpringsBiome() 
	{
		super(new BiomeTemplate("sulphur_springs").
				setFogColor(207, 194, 62).
				setFogDensity(1.5F).
				setWaterColor(25, 90, 157).
				setWaterFogColor(30, 65, 61).
				setCaves(false).
				//setSurface(ModBlocks.PINK_MOSS.get()).
				setAmbientSound(ModSoundEvents.AMBIENT_SULPHUR_SPRINGS.get()).
				setMusic(ModSoundEvents.MUSIC_SULPHUR_SPRINGS.get()).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.GEYSER).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.SULPHURIC_LAKE).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.SULPHURIC_CAVE).
				addFeature(Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.SURFACE_VENT).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.HYDRALUX).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_ORANGE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				setMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.END_FISH.get(), 50, 3, 8).
				setMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.CUBOZOA.get(), 50, 3, 8).
				setMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 4));
	}
}
