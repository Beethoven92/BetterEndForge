package mod.beethoven92.betterendforge.common.world.biome;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.util.JsonFactory;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.feature.BiomeNBTStructures.StructureInfo;
import mod.beethoven92.betterendforge.common.world.feature.NBTFeature.TerrainMerge;
import mod.beethoven92.betterendforge.config.Configs;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
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
	
	private List<StructureInfo> nbtStructures = Lists.newArrayList();
	
	public BetterEndBiome(BiomeTemplate template)
	{
		biome = template.build();
		
		id = template.getID();
		fogDensity = Configs.BIOME_CONFIG.getFloat(id, "fog_density", template.getFogDensity());
		genChanceUnmutable = Configs.BIOME_CONFIG.getFloat(id, "generation_chance", template.getGenChance());
		hasCaves = Configs.BIOME_CONFIG.getBoolean(id, "has_caves", template.hasCaves());
		this.readNBTStructureList();
	}
	
	public BetterEndBiome(ResourceLocation id, Biome biome, float fogDensity, float genChance, boolean hasCaves) 
	{
		this.biome = biome;
		this.id = id;
		this.fogDensity = Configs.BIOME_CONFIG.getFloat(id, "fog_density", fogDensity);
		this.genChanceUnmutable = Configs.BIOME_CONFIG.getFloat(id, "generation_chance", genChance);
		this.hasCaves = Configs.BIOME_CONFIG.getBoolean(id, "has_caves", hasCaves);
		this.readNBTStructureList();
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

	public boolean containsSubBiome(ModBiomes biome) {
		return subbiomes.contains(biome);
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
	
	public void updateActualBiomes(Registry<Biome> biomeRegistry) 
	{
		subbiomes.forEach((sub) -> {
			if (sub != this) {
				sub.updateActualBiomes(biomeRegistry);
			}
		});
		if (edge != null && edge != this) {
			edge.updateActualBiomes(biomeRegistry);
		}
		this.actualBiome = biomeRegistry.getOrDefault(id);
	}
	
	public boolean hasCaves() 
	{
		return hasCaves;
	}
	
	protected void readNBTStructureList() 
	{
		nbtStructures.clear();
		
		String ns = id.getNamespace();
		String nm = id.getPath();

		String path = "/data/" + ns + "/structures/biome/" + nm + "/";
		InputStream inputstream = StructureHelper.class.getResourceAsStream(path + "structures.json");
		if (inputstream != null) 
		{
			JsonObject obj = JsonFactory.getJsonObject(inputstream);
			JsonArray entries = obj.getAsJsonArray("structures");
			if (entries != null) 
			{
				entries.forEach((entry) -> {
					JsonObject e = entry.getAsJsonObject();
					String structurePath = path + e.get("nbt").getAsString() + ".nbt";
					String replacePath = nm + "\\" + e.get("nbt").getAsString();
					TerrainMerge terrainMerge = TerrainMerge.getFromString(e.get("terrainMerge").getAsString());
					int offsetY = e.get("offsetY").getAsInt();
					nbtStructures.add(new StructureInfo(structurePath, replacePath, offsetY, terrainMerge));
				});
			}
		}
	}
	
	public List<StructureInfo> getNBTStructures() 
	{
		return nbtStructures;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		BetterEndBiome biome = (BetterEndBiome) obj;
		return biome == null ? false : biome.id.equals(id);
	}
	
	@Override
	public int hashCode() 
	{
		return id.hashCode();
	}
}

