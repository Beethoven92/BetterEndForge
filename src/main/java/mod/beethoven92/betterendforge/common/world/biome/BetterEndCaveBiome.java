package mod.beethoven92.betterendforge.common.world.biome;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;

public class BetterEndCaveBiome extends BetterEndBiome
{
	private WeightedList<Feature<?>> floorFeatures = new WeightedList<Feature<?>>();
	private WeightedList<Feature<?>> ceilFeatures = new WeightedList<Feature<?>>();

	public BetterEndCaveBiome(BiomeTemplate definition)
	{
		super(definition.setCaveBiome());
	}

	public void addFloorFeature(Feature<?> feature, int weight) 
	{
		floorFeatures.add(feature, weight);
	}

	public void addCeilFeature(Feature<?> feature, int weight) 
	{
		ceilFeatures.add(feature, weight);
	}

	public Feature<?> getFloorFeature(Random random) 
	{
		return floorFeatures.isEmpty() ? null : floorFeatures.getOne(random);
	}

	public Feature<?> getCeilFeature(Random random) 
	{
		return ceilFeatures.isEmpty() ? null : ceilFeatures.getOne(random);
	}
	
	public float getFloorDensity() 
	{
		return 0;
	}
	
	public float getCeilDensity() 
	{
		return 0;
	}
	
	public BlockState getCeil(BlockPos pos) 
	{
		return null;
	}

	public BlockState getWall(BlockPos pos) {
		return null;
	}
}
