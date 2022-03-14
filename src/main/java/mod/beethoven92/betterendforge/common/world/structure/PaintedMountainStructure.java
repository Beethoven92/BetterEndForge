package mod.beethoven92.betterendforge.common.world.structure;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.PaintedMountainPiece;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
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

public class PaintedMountainStructure extends StructureFeature<NoneFeatureConfiguration>
{
	private static final BlockState[] VARIANTS;
	
	static 
	{
		VARIANTS = new BlockState[] {
				Blocks.END_STONE.defaultBlockState(),
				ModBlocks.FLAVOLITE.stone.get().defaultBlockState(),
				ModBlocks.VIOLECITE.stone.get().defaultBlockState(),
		};
	}
	
    public PaintedMountainStructure(Codec<NoneFeatureConfiguration> p_i231997_1_)
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
		return BetterEnd.MOD_ID + ":painted_mountain_structure";
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
			if (y > 50) 
			{
				float radius = ModMathHelper.randRange(50, 100, random);
				float height = radius * ModMathHelper.randRange(0.4F, 0.6F, random);
				int count = ModMathHelper.floor(height * ModMathHelper.randRange(0.1F, 0.35F, random) + 1);
				BlockState[] slises = new BlockState[count];
				for (int i = 0; i < count; i++) 
				{
					slises[i] = VARIANTS[random.nextInt(VARIANTS.length)];
				}
				this.pieces.add(new PaintedMountainPiece(new BlockPos(x, y, z), radius, height, random, biome, slises));
			}
			this.calculateBoundingBox();
		}		
	}
}
