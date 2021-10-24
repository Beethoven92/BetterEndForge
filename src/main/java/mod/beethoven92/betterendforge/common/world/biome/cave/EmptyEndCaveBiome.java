package mod.beethoven92.betterendforge.common.world.biome.cave;

import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.BiomeTemplate;

public class EmptyEndCaveBiome extends BetterEndCaveBiome
{
	public EmptyEndCaveBiome() 
	{
		super(new BiomeTemplate("empty_end_cave")
				.setFogDensity(2.0F)
				.setMusic(ModSoundEvents.MUSIC_CAVES.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_CAVES.get()));
		
		this.addFloorFeature(ModFeatures.END_STONE_STALAGMITE, 1);
		this.addCeilFeature(ModFeatures.END_STONE_STALACTITE, 1);
	}
	
	@Override
	public float getFloorDensity() 
	{
		return 0.1F;
	}
	
	@Override
	public float getCeilDensity() 
	{
		return 0.1F;
	}
}
