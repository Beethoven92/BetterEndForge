package mod.beethoven92.betterendforge.common.world.structure;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.LakePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public class MegaLakeSmallStructure extends StructureFeature<NoneFeatureConfiguration>
{

	public MegaLakeSmallStructure(Codec<NoneFeatureConfiguration> p_i231997_1_) 
	{
		super(p_i231997_1_);
	}
	
	@Override
	public Decoration step() 
	{
		return Decoration.RAW_GENERATION;
	}
	
	@Override
	public String getFeatureName() 
	{
		return BetterEnd.MOD_ID + ":megalake_small_structure";
	}
	
	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() 
	{
		return SDFStructureStart::new;
	}
	
	public static class SDFStructureStart extends StructureStart<NoneFeatureConfiguration> 
	{
		public SDFStructureStart(StructureFeature<NoneFeatureConfiguration> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
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
			if (y > 5) 
			{
				float radius = ModMathHelper.randRange(20, 40, random);
				float depth = ModMathHelper.randRange(5, 10, random);
				LakePiece piece = new LakePiece(new BlockPos(x, y, z), radius, depth, random, biome);
				this.pieces.add(piece);
			}
			this.calculateBoundingBox();
		}
	}
}
