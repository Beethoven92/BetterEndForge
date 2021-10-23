package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredSurfaceBuilders;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class NeonOasisBiome extends BetterEndBiome {
	public NeonOasisBiome() {
		super(new BiomeTemplate("neon_oasis").setGenChance(0.5F).setFogColor(226, 239, 168).setFogDensity(2)
				.setWaterFogColor(106, 238, 215).setWaterColor(106, 238, 215)
				.setSurface(ModConfiguredSurfaceBuilders.get(ModConfiguredSurfaceBuilders.NEON_OASUS_SURFACE))
				.setParticles(ParticleTypes.WHITE_ASH, 0.01F)
				.setAmbientSound(ModSoundEvents.AMBIENT_DUST_WASTELANDS.get())
				.setMusic(ModSoundEvents.MUSIC_OPENSPACE.get())
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.DESERT_LAKE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.NEON_CACTUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED)
				.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
