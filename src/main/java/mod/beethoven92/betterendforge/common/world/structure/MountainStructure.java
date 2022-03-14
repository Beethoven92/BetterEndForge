package mod.beethoven92.betterendforge.common.world.structure;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.structure.piece.MountainPiece;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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

public class MountainStructure extends StructureFeature<NoneFeatureConfiguration>
{
	public MountainStructure(Codec<NoneFeatureConfiguration> p_i231997_1_) 
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
		return BetterEnd.MOD_ID + ":mountain_structure";
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
			int x = (chunkX << 4) | Mth.nextInt(this.random, 4, 12);
			int z = (chunkZ << 4) | Mth.nextInt(this.random, 4, 12);
			int y = chunkGenerator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG);
			if (y > 5) 
			{
				float radius = Mth.nextInt(this.random, 50, 100);
				float height = radius * Mth.nextFloat(this.random, 0.8F, 1.2F);
				MountainPiece piece = new MountainPiece(new BlockPos(x, y, z), radius, height, random, biome);
				this.pieces.add(piece);
			}
			this.calculateBoundingBox();
		}
	}
}
