package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.NBTPiece;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public class EternalPortalStructure extends StructureFeature<NoneFeatureConfiguration>
{
	private static final ResourceLocation STRUCTURE_ID = new ResourceLocation(BetterEnd.MOD_ID, "portal/eternal_portal");
	private static final StructureTemplate STRUCTURE = StructureHelper.readStructure(STRUCTURE_ID);
	
	public EternalPortalStructure(Codec<NoneFeatureConfiguration> p_i231997_1_) 
	{
		super(p_i231997_1_);
	}
	
	@Override
	public Decoration step() 
	{
		return Decoration.SURFACE_STRUCTURES;
	}
	
	@Override
	public String getFeatureName() 
	{
		return BetterEnd.MOD_ID + ":eternal_portal_structure";
	}
	
	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource provider, long seed,
			WorldgenRandom sharedSeedRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			NoneFeatureConfiguration config) 
	{
		long x = chunkPos.x * chunkPos.x;
		long z = chunkPos.z * chunkPos.z;
		long d = x * x + z * z;
		if (d < 1024) 
		{
			return false;
		}
		if (chunkGenerator.getBaseHeight((chunkX << 4) | 8, (chunkZ << 4) | 8, Types.WORLD_SURFACE_WG) < 58) 
		{
			return false;
		}
		return getGenerationHeight(chunkX, chunkZ, chunkGenerator) >= 20;
	}

	private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) 
	{
		Random random = new Random((long) (chunkX + chunkZ * 10387313));
		Rotation blockRotation = Rotation.getRandom(random);
		int i = 5;
		int j = 5;
		if (blockRotation == Rotation.CLOCKWISE_90) 
		{
			i = -5;
		} 
		else if (blockRotation == Rotation.CLOCKWISE_180) 
		{
			i = -5;
			j = -5;
		} 
		else if (blockRotation == Rotation.COUNTERCLOCKWISE_90) 
		{
			j = -5;
		}

		int k = (chunkX << 4) + 7;
		int l = (chunkZ << 4) + 7;
		int m = chunkGenerator.getBaseHeight(k, l, Heightmap.Types.WORLD_SURFACE_WG);
		int n = chunkGenerator.getBaseHeight(k, l + j, Heightmap.Types.WORLD_SURFACE_WG);
		int o = chunkGenerator.getBaseHeight(k + i, l, Heightmap.Types.WORLD_SURFACE_WG);
		int p = chunkGenerator.getBaseHeight(k + i, l + j, Heightmap.Types.WORLD_SURFACE_WG);
		return Math.min(Math.min(m, n), Math.min(o, p));
	}
	
	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() 
	{
		return Start::new;
	}
	
	public static class Start extends StructureStart<NoneFeatureConfiguration>
	{
		public Start(StructureFeature<NoneFeatureConfiguration> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				BoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) 
		{
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void generatePieces(RegistryAccess registry, ChunkGenerator chunkGenerator,
				StructureManager manager, int chunkX, int chunkZ, Biome biome,
				NoneFeatureConfiguration config)
		{
			int x = (chunkX << 4) | ModMathHelper.randRange(4, 12, random);
			int z = (chunkZ << 4) | ModMathHelper.randRange(4, 12, random);
			int y = chunkGenerator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG);
			if (y > 10) 
			{
				this.pieces.add(new NBTPiece(STRUCTURE_ID, STRUCTURE, new BlockPos(x, y - 4, z), random.nextInt(5), true, random));
			}
			this.calculateBoundingBox();	
		}		
	}
}
