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
	}
	
	public void clearMutables() 
	{
		maxChance = maxChanceUnmutable;
		for (int i = biomes.size() - 1; i >= biomeCount; i--)
		{
			biomes.remove(i);
		}
	}
	
	public BetterEndBiome getBiome(Random random) 
	{
		return biomes.isEmpty() ? null : tree.getBiome(random.nextFloat() * maxChance);
	}
	
	public List<BetterEndBiome> getBiomes() 
	{
		return biomes;
	}
	
	public boolean containsImmutable(ResourceLocation id) 
	{
		return immutableIDs.contains(id);
	}
	
	public void removeMutableBiome(ResourceLocation id) 
	{
		for (int i = biomeCount; i < biomes.size(); i++) 
		{
			BetterEndBiome biome = biomes.get(i);
			if (biome.getID().equals(id)) 
			{
				biomes.remove(i);
				break;
			}
		}
	}
	
	public void rebuild() 
	{
		if (biomes.isEmpty()) 
		{
			return;
		}
		maxChance = maxChanceUnmutable;
		for (int i = biomeCount; i < biomes.size(); i++) 
		{
			maxChance = biomes.get(i).mutateGenChance(maxChance);
		}
		tree = new WeighTree(biomes);
	}
	
	public boolean isRebuilt() {
		return tree != null;
	}
}
