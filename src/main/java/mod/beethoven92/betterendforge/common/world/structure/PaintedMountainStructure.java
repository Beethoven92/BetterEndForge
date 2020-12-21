package mod.beethoven92.betterendforge.common.world.structure;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.PaintedMountainPiece;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

public class PaintedMountainStructure extends Structure<NoFeatureConfig>
{
	private static final BlockState[] VARIANTS;
	
	static 
	{
		VARIANTS = new BlockState[] {
				Blocks.END_STONE.getDefaultState(),
				ModBlocks.FLAVOLITE.stone.get().getDefaultState(),
				ModBlocks.VIOLECITE.stone.get().getDefaultState(),
		};
	}
	
    public PaintedMountainStructure(Codec<NoFeatureConfig> p_i231997_1_)
	{
		super(p_i231997_1_);
	}

	@Override
	public Decoration getDecorationStage() 
	{
		return Decoration.RAW_GENERATION;
	}
	
	@Override
	public String getStructureName() 
	{
		return BetterEnd.MOD_ID + ":painted_mountain_structure";
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
			if (y > 50) 
			{
				float radius = ModMathHelper.randRange(50, 100, rand);
				float height = radius * ModMathHelper.randRange(0.4F, 0.6F, rand);
				int count = ModMathHelper.floor(height * ModMathHelper.randRange(0.1F, 0.35F, rand) + 1);
				BlockState[] slises = new BlockState[count];
				for (int i = 0; i < count; i++) 
				{
					slises[i] = VARIANTS[rand.nextInt(VARIANTS.length)];
				}
				this.components.add(new PaintedMountainPiece(new BlockPos(x, y, z), radius, height, rand, biome, slises));
			}
			this.recalculateStructureSize();
		}		
	}
}
