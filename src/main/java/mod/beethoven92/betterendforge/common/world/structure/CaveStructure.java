package mod.beethoven92.betterendforge.common.world.structure;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.structure.piece.CavePiece;
import mod.beethoven92.betterendforge.common.world.structure.piece.MountainPiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class CaveStructure extends Structure<NoFeatureConfig>
{
    public CaveStructure(Codec<NoFeatureConfig> p_i231997_1_)
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
        public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator,
                                   TemplateManager manager, int chunkX, int chunkZ, Biome biome,
                                   NoFeatureConfig config)
        {
            int x = (chunkX << 4) | MathHelper.nextInt(this.random, 4, 12);
            int z = (chunkZ << 4) | MathHelper.nextInt(this.random, 4, 12);
            int y = chunkGenerator.getBaseHeight(x, z, Type.WORLD_SURFACE_WG);
            if (y > 5)
            {
                float radius = MathHelper.nextInt(this.random, 50, 100);
                float height = radius * MathHelper.nextFloat(this.random, 0.8F, 1.2F);
                CavePiece piece = new CavePiece(new BlockPos(x, y, z), radius, random);
                this.pieces.add(piece);
            }
            this.calculateBoundingBox();
        }
    }
}
