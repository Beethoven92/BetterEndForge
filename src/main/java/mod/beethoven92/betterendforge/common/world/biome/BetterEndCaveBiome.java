package mod.beethoven92.betterendforge.common.world.biome;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;

public class BetterEndCaveBiome extends BetterEndBiome
{
	private List<Feature<?>> floorFeatures = Lists.newArrayList();
	private List<Feature<?>> ceilFeatures = Lists.newArrayList();

	public BetterEndCaveBiome(BiomeTemplate definition) 
	{
		super(definition.setCaveBiome());
	}

	public void addFloorFeature(Feature<?> feature) 
	{
		floorFeatures.add(feature);
	}

	public void addCeilFeature(Feature<?> feature) 
	{
		ceilFeatures.add(feature);
	}

	public Feature<?> getFloorFeature(Random random) 
	{
		return floorFeatures.isEmpty() ? null : floorFeatures.get(random.nextInt(floorFeatures.size()));
	}

	public Feature<?> getCeilFeature(Random random) 
	{
		return ceilFeatures.isEmpty() ? null : ceilFeatures.get(random.nextInt(ceilFeatures.size()));
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
}
