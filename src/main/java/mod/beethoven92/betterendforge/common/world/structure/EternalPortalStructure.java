package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.NBTPiece;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class EternalPortalStructure extends Structure<NoFeatureConfig>
{
	private static final ResourceLocation STRUCTURE_ID = new ResourceLocation(BetterEnd.MOD_ID, "portal/eternal_portal");
	private static final Template STRUCTURE = StructureHelper.readStructure(STRUCTURE_ID);
	
	public EternalPortalStructure(Codec<NoFeatureConfig> p_i231997_1_) 
	{
		super(p_i231997_1_);
	}
	
	@Override
	public Decoration getDecorationStage() 
	{
		return Decoration.SURFACE_STRUCTURES;
	}
	
	@Override
	public String getStructureName() 
	{
		return BetterEnd.MOD_ID + ":eternal_portal_structure";
	}
	
	@Override
	protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider provider, long seed,
			SharedSeedRandom sharedSeedRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			NoFeatureConfig config) 
	{
		long x = chunkPos.x * chunkPos.x;
		long z = chunkPos.z * chunkPos.z;
		long d = x * x + z * z;
		if (d < 1024) 
		{
			return false;
		}
		if (chunkGenerator.getHeight((chunkX << 4) | 8, (chunkZ << 4) | 8, Type.WORLD_SURFACE_WG) < 58) 
		{
			return false;
		}
		return getGenerationHeight(chunkX, chunkZ, chunkGenerator) >= 20;
	}

	private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) 
	{
		Random random = new Random((long) (chunkX + chunkZ * 10387313));
		Rotation blockRotation = Rotation.randomRotation(random);
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
		int m = chunkGenerator.getHeight(k, l, Heightmap.Type.WORLD_SURFACE_WG);
		int n = chunkGenerator.getHeight(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		int o = chunkGenerator.getHeight(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
		int p = chunkGenerator.getHeight(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		return Math.min(Math.min(m, n), Math.min(o, p));
	}
	
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() 
	{
		return Start::new;
	}
	
	public static class Start extends StructureStart<NoFeatureConfig>
	{
		public Start(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) 
		{
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void func_230364_a_(DynamicRegistries registry, ChunkGenerator chunkGenerator,
				TemplateManager manager, int chunkX, int chunkZ, Biome biome,
				NoFeatureConfig config)
		{
			int x = (chunkX << 4) | ModMathHelper.randRange(4, 12, rand);
			int z = (chunkZ << 4) | ModMathHelper.randRange(4, 12, rand);
			int y = chunkGenerator.getHeight(x, z, Type.WORLD_SURFACE_WG);
			if (y > 10) 
			{
				this.components.add(new NBTPiece(STRUCTURE_ID, STRUCTURE, new BlockPos(x, y - 4, z), rand.nextInt(5), true, rand));
			}
			this.recalculateStructureSize();	
		}		
	}
}
