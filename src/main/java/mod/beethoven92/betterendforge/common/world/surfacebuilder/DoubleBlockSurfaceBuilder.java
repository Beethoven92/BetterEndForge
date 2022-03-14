package mod.beethoven92.betterendforge.common.world.surfacebuilder;

import java.util.Random;

import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class DoubleBlockSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration>
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(4141);
	private SurfaceBuilderBaseConfiguration config1;
	private SurfaceBuilderBaseConfiguration config2;
	
	public DoubleBlockSurfaceBuilder(Block block1, Block block2) 
	{
		super(SurfaceBuilderBaseConfiguration.CODEC);
		setBlock1(block1);
		setBlock2(block2);
	}


	
	public DoubleBlockSurfaceBuilder setBlock1(Block block) 
	{
		BlockState stone = Blocks.END_STONE.defaultBlockState();
		config1 = new SurfaceBuilderBaseConfiguration(block.defaultBlockState(), stone, stone);
		return this;
	}
	
	public DoubleBlockSurfaceBuilder setBlock2(Block block) 
	{
		BlockState stone = Blocks.END_STONE.defaultBlockState();
		config2 = new SurfaceBuilderBaseConfiguration(block.defaultBlockState(), stone, stone);
		return this;
	}
	
	@Override
	public void apply(Random random, ChunkAccess chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderBaseConfiguration config) 
	{
		noise = NOISE.eval(x * 0.1, z * 0.1) + Mth.nextDouble(random, -0.4, 0.4);
		SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, noise > 0 ? config1 : config2);
	}
}
