package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class DryShrublandBiome extends BetterEndBiome {
	public DryShrublandBiome() {
		super(new BiomeTemplate("dry_shrubland")
				.setFogColor(132, 35, 13)
				.setFogDensity(1.2F)
				.setWaterColor(113, 88, 53)
				.setWaterFogColor(113, 88, 53)
				.setFoliageColor(237, 122, 66)
				.setSurface(ModBlocks.RUTISCUS.get())
				.setMusic(ModSoundEvents.MUSIC_OPENSPACE.get())
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.ORANGO)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AERIDIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUTEBUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LAMELLARIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUCERNIA_BUSH_RARE)
				.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
