package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.world.structure.piece.VoxelPiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class SDFStructure extends Structure<NoFeatureConfig>
{
	public SDFStructure(Codec<NoFeatureConfig> p_i231997_1_) 
	{
		super(p_i231997_1_);
	}
	
	/*public SDFStructure() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}*/

	protected abstract SDF getSDF(BlockPos pos, Random random);
	
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() 
	{
		return SDFStructureStart::new;
	}
	
	public static class SDFStructureStart extends StructureStart<NoFeatureConfig> 
	{

		public SDFStructureStart(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) {
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator chunkGenerator,
				TemplateManager p_230364_3_, int chunkX, int chunkZ, Biome p_230364_6_,
				NoFeatureConfig p_230364_7_) 
		{
			int x = (chunkX << 4) | MathHelper.nextInt(rand, 4, 12);
			int z = (chunkZ << 4) | MathHelper.nextInt(rand, 4, 12);
			int y = chunkGenerator.getHeight(x, z, Type.WORLD_SURFACE_WG);
			if (y > 5) 
			{
				BlockPos start = new BlockPos(x, y, z);
				VoxelPiece piece = new VoxelPiece((world) -> { ((SDFStructure) this.getStructure()).getSDF(start, this.rand).fillRecursive(world, start); }, rand.nextInt());
				this.components.add(piece);
			}
			this.recalculateStructureSize();
		}

	}
}
