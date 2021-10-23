package mod.beethoven92.betterendforge.common.world.generator;

import java.util.HashMap;

import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

public class BiomeMap 
{
	private static final SharedSeedRandom RANDOM = new SharedSeedRandom();
	
	private final HashMap<ChunkPos, BiomeChunk> maps = new HashMap<ChunkPos, BiomeChunk>();
	private final int size;
	private final int sizeXZ;
	private final int depth;
	private final OpenSimplexNoise noiseX;
	private final OpenSimplexNoise noiseZ;
	private final BiomePicker picker;
	private final long seed;
	
	public BiomeMap(long seed, int size, BiomePicker picker)
	{
		maps.clear();
		RANDOM.setSeed(seed);
		noiseX = new OpenSimplexNoise(RANDOM.nextLong());
		noiseZ = new OpenSimplexNoise(RANDOM.nextLong());
		this.sizeXZ = size;
		depth = (int) Math.ceil(Math.log(size) / Math.log(2)) - 2;
		this.size = 1 << depth;
		this.picker = picker;
		this.seed = seed;
	}

	public long getSeed() {
		return seed;
	}
	
	public void clearCache()
	{
		if (maps.size() > 32) 
		{
			maps.clear();
		}
	}
	
	private BetterEndBiome getRawBiome(int bx, int bz)
	{
		double x = (double) bx * size / sizeXZ;
		double z = (double) bz * size / sizeXZ;
		double nx = x;
		double nz = z;
		
		double px = bx * 0.2;
		double pz = bz * 0.2;
		
		for (int i = 0; i < depth; i++)
		{
			nx = (x + noiseX.eval(px, pz)) / 2F;
			nz = (z + noiseZ.eval(px, pz)) / 2F;
			
			x = nx;
			z = nz;
			
			px = px / 2 + i;
			pz = pz / 2 + i;
		}
		
		bx = MathHelper.floor(x);
		bz = MathHelper.floor(z);
		
		if ((bx & BiomeChunk.MASK_WIDTH) == BiomeChunk.MASK_WIDTH) 
		{
			x += (bz / 2) & 1;
		}
		
		if ((bz & BiomeChunk.MASK_WIDTH) == BiomeChunk.MASK_WIDTH) 
		{
			z += (bx / 2) & 1;
		}
		
		ChunkPos cpos = new ChunkPos(MathHelper.floor(x / BiomeChunk.WIDTH), MathHelper.floor(z / BiomeChunk.WIDTH));
		BiomeChunk chunk = maps.get(cpos);
		if (chunk == null)
		{
			RANDOM.setBaseChunkSeed(cpos.x, cpos.z);
			chunk = new BiomeChunk(this, RANDOM, picker);
			maps.put(cpos, chunk);
		}
		
		return chunk.getBiome(MathHelper.floor(x), MathHelper.floor(z));
	}
	
	public BetterEndBiome getBiome(int x, int z)
	{
		BetterEndBiome biome = getRawBiome(x, z);
		
		if (biome.hasEdge() || (biome.hasParentBiome() && biome.getParentBiome().hasEdge()))
		{
			BetterEndBiome search = biome;
			if (biome.hasParentBiome())
				search = biome.getParentBiome();
			int d = (int) Math.ceil(search.getEdgeSize() / 4F) << 2;
			
			boolean edge = !search.isSame(getRawBiome(x + d, z));
			edge = edge || !search.isSame(getRawBiome(x - d, z));
			edge = edge || !search.isSame(getRawBiome(x, z + d));
			edge = edge || !search.isSame(getRawBiome(x, z - d));
			edge = edge || !search.isSame(getRawBiome(x - 1, z - 1));
			edge = edge || !search.isSame(getRawBiome(x - 1, z + 1));
			edge = edge || !search.isSame(getRawBiome(x + 1, z - 1));
			edge = edge || !search.isSame(getRawBiome(x + 1, z + 1));
			
			if (edge)
			{
				biome = search.getEdge();
			}
		}
		
		return biome;
	}
}
