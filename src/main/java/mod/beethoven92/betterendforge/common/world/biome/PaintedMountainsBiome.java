package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;

public class PaintedMountainsBiome extends BetterEndBiome
{
	public PaintedMountainsBiome()
	{
		super(new BiomeTemplate("painted_mountains").
				setFogColor(226, 239, 168).
				setFogDensity(2).
				setWaterColor(192, 180, 131).
				setWaterFogColor(192, 180, 131).
				setCaves(false).
				setMusic(ModSoundEvents.MUSIC_OPENSPACE.get()).
				setAmbientSound(ModSoundEvents.AMBIENT_DUST_WASTELANDS.get()).
				setSurface(ModBlocks.ENDSTONE_DUST.get()).
				addStructure(ModConfiguredStructures.PAINTED_MOUNTAIN).
				setParticles(ParticleTypes.WHITE_ASH, 0.01F).
				addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 2));
	}
}
