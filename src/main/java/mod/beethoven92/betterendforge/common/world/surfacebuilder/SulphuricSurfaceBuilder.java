package mod.beethoven92.betterendforge.common.world.surfacebuilder;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModSurfaceBuilders;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class SulphuricSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration>
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(5123);
	
	public SulphuricSurfaceBuilder() 
	{
		super(SurfaceBuilderBaseConfiguration.CODEC);
	}
	
	@Override
	public void apply(Random random, ChunkAccess chunk, Biome biome, int x, int z, int height, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderBaseConfiguration config) 
	{	
		double value = NOISE.eval(x * 0.03, z * 0.03) + NOISE.eval(x * 0.1, z * 0.1) * 0.3 + ModMathHelper.randRange(-0.1, 0.1, ModMathHelper.RANDOM);
		if (value < -0.6) 
		{
			SurfaceBuilder.DEFAULT.apply(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.DEFAULT_END_CONFIG);
		}
		else if (value < -0.3) 
		{
			SurfaceBuilder.DEFAULT.apply(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.FLAVOLITE_CONFIG);
		}
		else if (value < 0.5)
		{
			SurfaceBuilder.DEFAULT.apply(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.SULFURIC_ROCK_CONFIG);
		}
		else
		{
			SurfaceBuilder.DEFAULT.apply(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.BRIMSTONE_CONFIG);
		}
	}
}
