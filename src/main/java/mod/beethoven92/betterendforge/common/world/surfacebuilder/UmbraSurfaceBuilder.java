package mod.beethoven92.betterendforge.common.world.surfacebuilder;

import mod.beethoven92.betterendforge.common.init.ModSurfaceBuilders;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.Random;

public class UmbraSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(1512);

    public UmbraSurfaceBuilder()
    {
        super(SurfaceBuilderBaseConfiguration.CODEC);
    }


	
	public static BlockState getSurfaceState(BlockPos pos) {
		return getConfig(pos.getX(), pos.getZ(), ModMathHelper.RANDOM).getTopMaterial();
	}
	
	private static SurfaceBuilderBaseConfiguration getConfig(int x, int z, Random random) {
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
	public void apply(Random random, ChunkAccess chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderBaseConfiguration config) {
		int depth = (int) (NOISE.eval(x * 0.1, z * 0.1) * 20 + NOISE.eval(x * 0.5, z * 0.5) * 10 + 60);
		config = getConfig(x, z, random);
		SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise + depth, defaultBlock, defaultFluid, seaLevel, seed, config);
	}
}