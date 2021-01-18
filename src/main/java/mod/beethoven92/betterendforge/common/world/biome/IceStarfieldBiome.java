package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;

public class IceStarfieldBiome extends BetterEndBiome
{
	public IceStarfieldBiome() 
	{
		super(new BiomeTemplate("ice_starfield").
				setFogColor(224, 245, 254).
				setFogDensity(2.2F).
				setFoliageColor(193, 244, 244).
				setGenChance(0.25f).
				setParticles(ModParticleTypes.SNOWFLAKE_PARTICLE.get(), 0.002F).
				addStructure(ModConfiguredStructures.GIANT_ICE_STAR).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.ICE_STAR).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.ICE_STAR_SMALL).
				addMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 20, 1, 4));
	}
}
