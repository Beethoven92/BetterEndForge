package mod.beethoven92.betterendforge.common.world.biome.cave;

import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.BiomeTemplate;

public class EmptyAuroraCaveBiome extends BetterEndCaveBiome
{
	public EmptyAuroraCaveBiome() 
	{
		super(new BiomeTemplate("empty_aurora_cave")
				.setFogColor(150, 30, 68)
				.setFogDensity(2.0F)
				.setGrassColor(108, 25, 46)
				.setFoliageColor(108, 25, 46)
				.setWaterColor(186, 77, 237)
				.setWaterFogColor(186, 77, 237)
				.setMusic(ModSoundEvents.MUSIC_CAVES.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_CAVES.get())
				.setParticles(ModParticleTypes.GLOWING_SPHERE.get(), 0.001F));
		
		this.addFloorFeature(ModFeatures.BIG_AURORA_CRYSTAL, 1);		
		this.addCeilFeature(ModFeatures.END_STONE_STALACTITE, 1);
	}
	
	@Override
	public float getFloorDensity() 
	{
		return 0.01F;
	}
	
	@Override
	public float getCeilDensity() 
	{
		return 0.1F;
	}
}
