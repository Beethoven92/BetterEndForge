package mod.beethoven92.betterendforge.common.world.feature;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class BiomeNBTStructures extends NBTFeature
{
	private StructureInfo selected;
	
	private static final HashMap<BetterEndBiome, List<StructureInfo>> nbtStructures = Maps.newHashMap();
	
	// Called in setupCommon
	public static void loadStructures()
	{ 
		for (BetterEndBiome endBiome : ModBiomes.getModBiomes())
		{
			if (!endBiome.getNBTStructures().isEmpty())
			{
				nbtStructures.put(endBiome, endBiome.getNBTStructures());
			}
		}
	}
	
	@Override
	protected StructureTemplate getStructure(WorldGenLevel world, BlockPos pos, Random random) 
	{
		Biome biome = world.getBiome(pos);
		BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
		List<StructureInfo> biomeStructures = nbtStructures.get(endBiome);
		
		selected = biomeStructures.get(random.nextInt(biomeStructures.size()));
		return selected.getStructure();
	}

	@Override
	protected boolean canSpawn(WorldGenLevel world, BlockPos pos, Random random) 
	{
		if (!nbtStructures.containsKey(ModBiomes.getFromBiome(world.getBiome(pos))))
		{
			return false;
		}
		
		int cx = pos.getX() >> 4;
		int cz = pos.getZ() >> 4;
		return ((cx + cz) & 1) == 0 && pos.getY() > 58 && world.getBlockState(pos.below()).is(ModTags.GEN_TERRAIN);
	}

	@Override
	protected Rotation getRotation(WorldGenLevel world, BlockPos pos, Random random) 
	{
		return Rotation.getRandom(random);
	}

	@Override
	protected Mirror getMirror(WorldGenLevel world, BlockPos pos, Random random) 
	{
		return Mirror.values()[random.nextInt(3)];
	}

	@Override
	protected int getYOffset(StructureTemplate structure, WorldGenLevel world, BlockPos pos, Random random) 
	{
		return selected.offsetY;
	}

	@Override
	protected TerrainMerge getTerrainMerge(WorldGenLevel world, BlockPos pos, Random random) 
	{
		return selected.terrainMerge;
	}

	@Override
	protected void addStructureData(StructurePlaceSettings data) 
	{
	}
	
	public static final class StructureInfo 
	{
		public final TerrainMerge terrainMerge;
		public final String structurePath;
		public final String replacePath;
		public final int offsetY;
		
		private StructureTemplate structure;
		
		public StructureInfo(String structurePath, String replacePath, int offsetY, TerrainMerge terrainMerge) 
		{
			this.terrainMerge = terrainMerge;
			this.structurePath = structurePath;
			this.replacePath = replacePath;
			this.offsetY = offsetY;
		}
		
		public StructureTemplate getStructure() 
		{
			if (structure == null) 
			{	
				structure = StructureHelper.readStructure(structurePath);
				
				// Use this to replace the mod id of the blocks in the structure and save the updated file
				//structure = NBTFeature.readStructure(structurePath, replacePath);
			}
			return structure;
		}
	}
}
