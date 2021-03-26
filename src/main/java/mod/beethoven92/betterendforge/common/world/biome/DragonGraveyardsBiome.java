package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class DragonGraveyardsBiome extends BetterEndBiome {
	public DragonGraveyardsBiome() {
		super(new BiomeTemplate("dragon_graveyards")
				.setGenChance(0.1F)
				.setFogColor(244, 46, 79)
				.setFogDensity(1.3F)
				.setParticles(ModParticleTypes.FIREFLY.get(), 0.0007F)
				.setMusic(ModSoundEvents.MUSIC_OPENSPACE.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_GLOWING_GRASSLANDS.get())
				.setSurface(ModBlocks.SANGNUM.get())
				.setWaterFogColor(203, 59, 167).setFoliageColor(244, 46, 79)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.OBSIDIAN_PILLAR_BASEMENT)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FALLEN_PILLAR)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.OBSIDIAN_BOULDER)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GIGANTIC_AMARANITA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LARGE_AMARANITA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SMALL_AMARANITA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GLOBULAGUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CLAWFERN)
				.addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
