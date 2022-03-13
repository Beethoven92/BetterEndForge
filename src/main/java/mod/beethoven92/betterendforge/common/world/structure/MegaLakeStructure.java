package mod.beethoven92.betterendforge.common.world.structure;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.LakePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import net.minecraft.world.gen.feature.structure.Structure.IStartFactory;

public class MegaLakeStructure extends Structure<NoFeatureConfig>
{

	public MegaLakeStructure(Codec<NoFeatureConfig> p_i231997_1_) 
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
		return BetterEnd.MOD_ID + ":megalake_structure";
	}
	
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() 
	{
		return SDFStructureStart::new;
	}
	
	public static class SDFStructureStart extends StructureStart<NoFeatureConfig> 
	{
		public SDFStructureStart(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) 
		{
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator,
				TemplateManager manager, int chunkX, int chunkZ, Biome biome,
				NoFeatureConfig config) 
		{
			int x = (chunkX << 4) | ModMathHelper.randRange(4, 12, random);
			int z = (chunkZ << 4) | ModMathHelper.randRange(4, 12, random);
			int y = chunkGenerator.getBaseHeight(x, z, Type.WORLD_SURFACE_WG);
			if (y > 5) 
			{
				//float radius = ModMathHelper.randRange(50, 100, rand);
				//float depth = ModMathHelper.randRange(10, 16, rand);
				float radius = ModMathHelper.randRange(50, 150, random);
				float depth = ModMathHelper.randRange(6, 10, random);
				LakePiece piece = new LakePiece(new BlockPos(x, y, z), radius, depth, random, biome);
				this.pieces.add(piece);
			}
			this.calculateBoundingBox();
		}

	}
}
