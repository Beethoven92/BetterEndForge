package mod.beethoven92.betterendforge.common.world.biome.cave;

import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.BiomeTemplate;


public class EndCaveBiome extends BetterEndCaveBiome {
	public EndCaveBiome()
	{
		super(new BiomeTemplate("end_cave")
				.setFogDensity(2.0F)
				.setMusic(ModSoundEvents.MUSIC_CAVES.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_CAVES.get()));

	}

	@Override
	public float getFloorDensity()
	{
		return 0.0F;
	}

	@Override
	public float getCeilDensity()
	{
		return 0.0F;
	}

}
