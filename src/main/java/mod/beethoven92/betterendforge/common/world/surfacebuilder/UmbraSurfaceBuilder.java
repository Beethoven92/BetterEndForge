package mod.beethoven92.betterendforge.common.world.surfacebuilder;

import mod.beethoven92.betterendforge.common.init.ModSurfaceBuilders;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class UmbraSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(1512);

    public UmbraSurfaceBuilder()
    {
        super(SurfaceBuilderConfig.field_237203_a_);
    }


	
	public static BlockState getSurfaceState(BlockPos pos) {
		return getConfig(pos.getX(), pos.getZ(), ModMathHelper.RANDOM).getTop();
	}
	
	private static SurfaceBuilderConfig getConfig(int x, int z, Random random) {
		float grass = ((float) NOISE.eval(x * 0.03, z * 0.03) + (float) NOISE.eval(x * 0.1, z * 0.1) * 0.6F + random.nextFloat() * 0.2F) - 0.05F;
		if (grass > 0.4F) {
			return ModSurfaceBuilders.Configs.PALLIDIUM_FULL_SURFACE_CONFIG;
		}
		else if (grass > 0.15F) {
			return ModSurfaceBuilders.Configs.PALLIDIUM_HEAVY_SURFACE_CONFIG;
		}
		else if (grass > -0.15) {
			return ModSurfaceBuilders.Configs.PALLIDIUM_THIN_SURFACE_CONFIG;
		}
		else if (grass > -0.4F) {
			return ModSurfaceBuilders.Configs.PALLIDIUM_TINY_SURFACE_CONFIG;
		}
		else {
			return ModSurfaceBuilders.Configs.UMBRA_SURFACE_CONFIG;
		}
	}


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
		int depth = (int) (NOISE.eval(x * 0.1, z * 0.1) * 20 + NOISE.eval(x * 0.5, z * 0.5) * 10 + 60);
		config = getConfig(x, z, random);
		SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise + depth, defaultBlock, defaultFluid, seaLevel, seed, config);
	}
}