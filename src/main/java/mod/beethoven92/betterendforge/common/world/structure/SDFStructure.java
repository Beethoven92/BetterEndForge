package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.world.structure.piece.VoxelPiece;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public abstract class SDFStructure extends StructureFeature<NoneFeatureConfiguration>
{
	public SDFStructure(Codec<NoneFeatureConfiguration> p_i231997_1_) 
	{
		super(p_i231997_1_);
	}
	
	/*public SDFStructure() 
	{
		super(NoFeatureConfig.CODEC);
	}*/

	protected abstract SDF getSDF(BlockPos pos, Random random);
	
	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() 
	{
		return SDFStructureStart::new;
	}
	
	public static class SDFStructureStart extends StructureStart<NoneFeatureConfiguration> 
	{

		public SDFStructureStart(StructureFeature<NoneFeatureConfiguration> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				BoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) {
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void generatePieces(RegistryAccess p_230364_1_, ChunkGenerator chunkGenerator,
				StructureManager p_230364_3_, int chunkX, int chunkZ, Biome p_230364_6_,
				NoneFeatureConfiguration p_230364_7_) 
		{
			int x = (chunkX << 4) | Mth.nextInt(random, 4, 12);
			int z = (chunkZ << 4) | Mth.nextInt(random, 4, 12);
			int y = chunkGenerator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG);
			if (y > 5) 
			{
				BlockPos start = new BlockPos(x, y, z);
				VoxelPiece piece = new VoxelPiece((world) -> { ((SDFStructure) this.getFeature()).getSDF(start, this.random).fillRecursive(world, start); }, random.nextInt());
				this.pieces.add(piece);
			}
			this.calculateBoundingBox();
		}

	}
}
