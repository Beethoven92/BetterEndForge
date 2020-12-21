package mod.beethoven92.betterendforge.common.world.biome;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BetterEndBiome 
{
	protected List<BetterEndBiome> subbiomes = Lists.newArrayList();
    
	private final Biome biome;
	
	private final ResourceLocation id;
	
	protected BetterEndBiome edge;
	protected int edgeSize;
	protected BetterEndBiome biomeParent;
	
	protected float maxSubBiomeChance = 1;
	protected final float genChanceUnmutable;
	private float genChance = 1;
	
	private final float fogDensity;
	private final boolean hasCaves;
	
	private Biome actualBiome;
	
	public BetterEndBiome(BiomeTemplate template)
	{
		biome = template.build();
		
		id = template.getID();
		fogDensity = template.getFogDensity();
		genChanceUnmutable = template.getGenChance();
		hasCaves = template.hasCaves();
	}
	
	public BetterEndBiome(ResourceLocation id, Biome biome, float fogDensity, float genChance, boolean hasCaves) 
	{
		this.biome = biome;
		this.id = id;
		this.fogDensity = fogDensity;
		this.genChanceUnmutable = genChance;
		this.hasCaves = hasCaves;
	}
	
	public BetterEndBiome getEdge() 
	{
		return edge == null ? this : edge;
	}

	public void setEdge(BetterEndBiome edge) 
	{
		this.edge = edge;
		edge.biomeParent = this;
	}

	public int getEdgeSize() 
	{
		return edgeSize;
	}

	public void setEdgeSize(int size) 
	{
		edgeSize = size;
	}
	
	public void addSubBiome(BetterEndBiome biome) 
	{
		maxSubBiomeChance += biome.mutateGenChance(maxSubBiomeChance);
		biome.biomeParent = this;
		subbiomes.add(biome);
	}

	public BetterEndBiome getSubBiome(Random random) 
	{
		float chance = random.nextFloat() * maxSubBiomeChance;
		for (BetterEndBiome biome : subbiomes)
			if (biome.canGenerate(chance))
				return biome;
		return this;
	}

	public BetterEndBiome getParentBiome() 
	{
		return this.biomeParent;
	}

	public boolean hasEdge() 
	{
		return edge != null;
	}

	public boolean hasParentBiome() 
	{
		return biomeParent != null;
	}

	public boolean isSame(BetterEndBiome biome) 
	{
		return biome == this || (biome.hasParentBiome() && biome.getParentBiome() == this);
	}
	
	public boolean canGenerate(float chance) 
	{
		return chance <= this.genChance;
	}

	public float mutateGenChance(float chance) 
	{
		genChance = genChanceUnmutable;
		genChance += chance;
		return genChance;
	}
	
	public Biome getBiome() 
    {
        return this.biome;
    }
    
    public ResourceLocation getID()
    {
    	return id;
    }
    
    public float getFogDensity()
    {
    	return fogDensity;
    }
    
    public float getGenChance()
    {
    	return genChance;
    }
	
    public void setActualBiome(Biome biome) 
    {
		this.actualBiome = biome;
	}
	
	public Biome getActualBiome() 
	{
		return this.actualBiome;
	}
	
	public boolean hasCaves() 
	{
		return hasCaves;
	}
}

