package mod.beethoven92.betterendforge.common.world.generator;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.util.ResourceLocation;

public class BiomePicker 
{
	private final Set<ResourceLocation> immutableIDs = Sets.newHashSet();
	private final List<BetterEndBiome> biomes = Lists.newArrayList();
	private float maxChanceUnmutable = 0;
	private float maxChance = 0;
	private int biomeCount = 0;
	private WeighTree tree;
	
	public void addBiome(BetterEndBiome biome) 
	{
		maxChance = biome.mutateGenChance(maxChance);
		immutableIDs.add(biome.getID());
		maxChanceUnmutable = maxChance;
		biomes.add(biome);
		biomeCount++;
	}
	
	public void addBiomeMutable(BetterEndBiome biome) 
	{
		biomes.add(biome);
		maxChance = biome.mutateGenChance(maxChance);
	}
	
	public void clearMutables() 
	{
		maxChance = maxChanceUnmutable;
		for (int i = biomes.size() - 1; i >= biomeCount; i--)
			biomes.remove(i);
	}
	
	public BetterEndBiome getBiome(Random random) 
	{
		return tree.getBiome(random.nextFloat() * maxChance);
	}
	
	public List<BetterEndBiome> getBiomes() 
	{
		return biomes;
	}
	
	public boolean containsImmutable(ResourceLocation id) 
	{
		return immutableIDs.contains(id);
	}
	
	public void rebuild() 
	{
		tree = new WeighTree(biomes);
	}
}
