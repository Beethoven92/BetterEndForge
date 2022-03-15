package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.data.worldgen.StructureFeatures;

public class DustWastelandsBiome extends BetterEndBiome
{

	public DustWastelandsBiome()
	{
		super(new BiomeTemplate("dust_wastelands").
				setFogColor(226, 239, 168).
				setFogDensity(2).
                setWaterColor(192, 180, 131).
                setWaterFogColor(192, 180, 131).
                setCaves(false).
				setMusic(ModSoundEvents.MUSIC_OPENSPACE.get()).
				setAmbientSound(ModSoundEvents.AMBIENT_DUST_WASTELANDS.get()).
				setSurface(ModBlocks.ENDSTONE_DUST.get()).
				setParticles(ParticleTypes.WHITE_ASH, 0.01F).
                addStructure(StructureFeatures.END_CITY).
                addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
