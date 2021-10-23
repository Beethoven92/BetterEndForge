package mod.beethoven92.betterendforge.common.world.surfacebuilder;

import java.util.Random;

import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DoubleBlockSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(4141);
	private SurfaceBuilderConfig config1;
	private SurfaceBuilderConfig config2;
	
	public DoubleBlockSurfaceBuilder(Block block1, Block block2) 
	{
		super(SurfaceBuilderConfig.field_237203_a_);
		setBlock1(block1);
		setBlock2(block2);
	}


	
	public DoubleBlockSurfaceBuilder setBlock1(Block block) 
	{
		BlockState stone = Blocks.END_STONE.getDefaultState();
		config1 = new SurfaceBuilderConfig(block.getDefaultState(), stone, stone);
		return this;
	}
	
	public DoubleBlockSurfaceBuilder setBlock2(Block block) 
	{
		BlockState stone = Blocks.END_STONE.getDefaultState();
		config2 = new SurfaceBuilderConfig(block.getDefaultState(), stone, stone);
		return this;
	}
	
	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) 
	{
		noise = NOISE.eval(x * 0.1, z * 0.1) + MathHelper.nextDouble(random, -0.4, 0.4);
		SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, noise > 0 ? config1 : config2);
	}
}
