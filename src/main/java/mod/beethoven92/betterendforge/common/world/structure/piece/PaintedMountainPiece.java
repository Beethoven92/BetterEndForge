package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class PaintedMountainPiece extends StructurePiece
{
	private Map<Integer, Integer> heightmap = Maps.newHashMap();
	private OpenSimplexNoise noise1;
	private OpenSimplexNoise noise2;
	private BlockPos center;
	private float radius;
	private float height;
	private float r2;
	private ResourceLocation biomeID;
	private BlockState[] slises;
	private int seed1;
	private int seed2;
	
	public PaintedMountainPiece(BlockPos center, float radius, float height, Random random, Biome biome, BlockState[] slises)
	{
		super(ModStructurePieces.PAINTED_MOUNTAIN_PIECE, random.nextInt());
		this.center = center;
		this.radius = radius;
		this.height = height;
		this.r2 = radius * radius;
		this.seed1 = random.nextInt();
		this.seed2 = random.nextInt();
		this.noise1 = new OpenSimplexNoise(this.seed1);
		this.noise2 = new OpenSimplexNoise(this.seed2);
		this.biomeID = ModBiomes.getBiomeID(biome);
		this.slises = slises;
		makeBoundingBox();
	}
	
	public PaintedMountainPiece(StructureManager p_i50677_1_, CompoundTag nbt) 
	{
		super(ModStructurePieces.PAINTED_MOUNTAIN_PIECE, nbt);
		
		center = new BlockPos(nbt.getInt("centerX"), nbt.getInt("centerY"), nbt.getInt("centerZ"));
		radius = nbt.getFloat("radius");
		height = nbt.getFloat("height");
		biomeID = new ResourceLocation(nbt.getString("biome"));
		r2 = radius * radius;
		seed1 = nbt.getInt("seed1");
		seed2 = nbt.getInt("seed2");
		noise1 = new OpenSimplexNoise(seed1);
		noise2 = new OpenSimplexNoise(seed2);
		ListTag slise = nbt.getList("slises", 10);
		slises = new BlockState[slise.size()];
		for (int i = 0; i < slises.length; i++) 
		{
			slises[i] = NbtUtils.readBlockState(slise.getCompound(i));
		}
		makeBoundingBox();
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tagCompound)
	{	
		tagCompound.putInt("centerX", this.center.getX());
	    tagCompound.putInt("centerY", this.center.getY());
	    tagCompound.putInt("centerZ", this.center.getZ());
		tagCompound.putFloat("radius", radius);
		tagCompound.putFloat("height", height);
		tagCompound.putString("biome", biomeID.toString());
		tagCompound.putInt("seed1", seed1);
		tagCompound.putInt("seed2", seed2);
		
		ListTag slise = new ListTag();
		for (BlockState state: slises) 
		{
			slise.add(NbtUtils.writeBlockState(state));
		}
		tagCompound.put("slises", slise);
	}
	
	private void makeBoundingBox() 
	{
		int minX = ModMathHelper.floor(center.getX() - radius);
		int minZ = ModMathHelper.floor(center.getZ() - radius);
		int maxX = ModMathHelper.floor(center.getX() + radius + 1);
		int maxZ = ModMathHelper.floor(center.getZ() + radius + 1);
		this.boundingBox = new BoundingBox(minX, minZ, maxX, maxZ);
	}
	
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGenerator,
			Random random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) 
	{
		int sx = chunkPos.getMinBlockX();
		int sz = chunkPos.getMinBlockZ();
		MutableBlockPos pos = new MutableBlockPos();
		ChunkAccess chunk = world.getChunk(chunkPos.x, chunkPos.z);
		Heightmap map = chunk.getOrCreateHeightmapUnprimed(Types.WORLD_SURFACE);
		Heightmap map2 = chunk.getOrCreateHeightmapUnprimed(Types.WORLD_SURFACE_WG);
		for (int x = 0; x < 16; x++) 
		{
			int px = x + sx;
			int px2 = px - center.getX();
			px2 *= px2;
			pos.setX(x);
			for (int z = 0; z < 16; z++) 
			{
				int pz = z + sz;
				int pz2 = pz - center.getZ();
				pz2 *= pz2;
				float dist = px2 + pz2;
				if (dist < r2) {
					pos.setZ(z);
					dist = 1 - dist / r2;
					int minY = map.getFirstAvailable(x, z);
					pos.setY(minY - 1);
					while (chunk.getBlockState(pos).isAir() && pos.getY() > 50) 
					{
						pos.setY(minY --);
					}
					minY = pos.getY();
					minY = Math.max(minY, map2.getFirstAvailable(x, z));
					if (minY > 56)
					{
						float maxY = dist * height * getHeightClamp(world, 8, px, pz);
						if (maxY > 0) {
							maxY *= (float) noise1.eval(px * 0.05, pz * 0.05) * 0.3F + 0.7F;
							maxY *= (float) noise1.eval(px * 0.1, pz * 0.1) * 0.1F + 0.9F;
							maxY += 56;
							float offset = (float) (noise1.eval(px * 0.07, pz * 0.07) * 5 + noise1.eval(px * 0.2, pz * 0.2) * 2 + 7);
							for (int y = minY - 1; y < maxY; y++) 
							{
								pos.setY(y);
								int index = ModMathHelper.floor((y + offset) * 0.65F) % slises.length;
								chunk.setBlockState(pos, slises[index], false);
							}
						}
					}
				}
			}
		}		
		return true;
	}
	
	private int getHeight(WorldGenLevel world, BlockPos pos) 
	{
		int p = ((pos.getX() & 2047) << 11) | (pos.getZ() & 2047);
		int h = heightmap.getOrDefault(p, Integer.MIN_VALUE);
		if (h > Integer.MIN_VALUE)
		{
			return h;
		}
		
		if (!ModBiomes.getBiomeID(world.getBiome(pos)).equals(biomeID)) 
		{
			heightmap.put(p, -4);
			return -4;
		}
		
		h = world.getHeight(Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
		if (h < 57) 
		{
			heightmap.put(p, -4);
			return -4;
		}
		h = ModMathHelper.floor(noise2.eval(pos.getX() * 0.005, pos.getZ() * 0.005) * noise2.eval(pos.getX() * 0.001, pos.getZ() * 0.001) * 8 + 8);
		
		if (h < 0) 
		{
			heightmap.put(p, 0);
			return 0;
		}
		
		heightmap.put(p, h);
		
		return h;
	}
	
	private float getHeightClamp(WorldGenLevel world, int radius, int posX, int posZ) 
	{
		MutableBlockPos mut = new MutableBlockPos();
		float height = 0;
		float max = 0;
		for (int x = -radius; x <= radius; x++)
		{
			mut.setX(posX + x);
			int x2 = x * x;
			for (int z = -radius; z <= radius; z++) 
			{
				mut.setZ(posZ + z);
				int z2 = z * z;
				float mult = 1 - (float) Math.sqrt(x2 + z2) / radius;
				if (mult > 0) {
					max += mult;
					height += getHeight(world, mut) * mult;
				}
			}
		}
		height /= max;
		return Mth.clamp(height / radius, 0, 1);
	}
}
