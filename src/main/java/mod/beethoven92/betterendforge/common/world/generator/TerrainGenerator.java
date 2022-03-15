package mod.beethoven92.betterendforge.common.world.generator;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Lists;
import java.util.List;
import com.google.common.collect.Maps;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;

public class TerrainGenerator {
	private static final Map<Point, TerrainBoolCache> TERRAIN_BOOL_CACHE_MAP = Maps.newHashMap();
	private static final ReentrantLock LOCKER = new ReentrantLock();
	private static final Point POS = new Point();
	private static final double SCALE_XZ = 8.0;
	private static final double SCALE_Y = 4.0;
	private static final float[] COEF;
	private static final Point[] OFFS;

	private static IslandLayer largeIslands;
	private static IslandLayer mediumIslands;
	private static IslandLayer smallIslands;
	private static OpenSimplexNoise noise1;
	private static OpenSimplexNoise noise2;

	public static void initNoise(long seed) {
		Random random = new Random(seed);
		largeIslands = new IslandLayer(random.nextInt(), GeneratorOptions.bigOptions);
		mediumIslands = new IslandLayer(random.nextInt(), GeneratorOptions.mediumOptions);
		smallIslands = new IslandLayer(random.nextInt(), GeneratorOptions.smallOptions);
		noise1 = new OpenSimplexNoise(random.nextInt());
		noise2 = new OpenSimplexNoise(random.nextInt());
		TERRAIN_BOOL_CACHE_MAP.clear();
	}
	
	public static void fillTerrainDensity(double[] buffer, int x, int z, BiomeSource biomeSource) {
		LOCKER.lock();

		largeIslands.clearCache();
		mediumIslands.clearCache();
		smallIslands.clearCache();

		double distortion1 = noise1.eval(x * 0.1, z * 0.1) * 20 + noise2.eval(
				x * 0.2,
				z * 0.2
		) * 10 + noise1.eval(x * 0.4, z * 0.4) * 5;
		double distortion2 = noise2.eval(x * 0.1, z * 0.1) * 20 + noise1.eval(
				x * 0.2,
				z * 0.2
		) * 10 + noise2.eval(x * 0.4, z * 0.4) * 5;
		double px = (double) x * SCALE_XZ + distortion1;
		double pz = (double) z * SCALE_XZ + distortion2;

		largeIslands.updatePositions(px, pz);
		mediumIslands.updatePositions(px, pz);
		smallIslands.updatePositions(px, pz);

		float height = getAverageDepth(biomeSource, x << 1, z << 1) * 0.5F;

		for (int y = 0; y < buffer.length; y++) {
			double py = (double) y * SCALE_Y;
			float dist = largeIslands.getDensity(px, py, pz, height);
			dist = dist > 1 ? dist : ModMathHelper.max(dist, mediumIslands.getDensity(px, py, pz, height));
			dist = dist > 1 ? dist : ModMathHelper.max(dist, smallIslands.getDensity(px, py, pz, height));
			if (dist > -0.5F) {
				dist += noise1.eval(px * 0.01, py * 0.01, pz * 0.01) * 0.02 + 0.02;
				dist += noise2.eval(px * 0.05, py * 0.05, pz * 0.05) * 0.01 + 0.01;
				dist += noise1.eval(px * 0.1, py * 0.1, pz * 0.1) * 0.005 + 0.005;
			}
			buffer[y] = dist;
		}

		LOCKER.unlock();
	}

	private static float getAverageDepth(BiomeSource biomeSource, int x, int z) {
		if (getBiome(biomeSource, x, z).getDepth() < 0.1F) {
			return 0F;
		}
		float depth = 0F;
		for (int i = 0; i < OFFS.length; i++) {
			int px = x + OFFS[i].x;
			int pz = z + OFFS[i].y;
			depth += getBiome(biomeSource, px, pz).getDepth() * COEF[i];
		}
		return depth;
	}

	private static Biome getBiome(BiomeSource biomeSource, int x, int z) {
		return biomeSource.getNoiseBiome(x, 0, z);
	}


	/**
	 * Check if this is land
	 * @param x - biome pos x
	 * @param z - biome pos z
	 */
	public static boolean isLand(int x, int z) 
	{
		int sectionX = TerrainBoolCache.scaleCoordinate(x);
		int sectionZ = TerrainBoolCache.scaleCoordinate(z);

		LOCKER.lock();
		POS.setLocation(sectionX, sectionZ);

		TerrainBoolCache section = TERRAIN_BOOL_CACHE_MAP.get(POS);
		if (section == null) {
			if (TERRAIN_BOOL_CACHE_MAP.size() > 64) {
				TERRAIN_BOOL_CACHE_MAP.clear();
			}
			section = new TerrainBoolCache();
			TERRAIN_BOOL_CACHE_MAP.put(new Point(POS.x, POS.y), section);
		}
		byte value = section.getData(x, z);
		if (value > 0) {
			LOCKER.unlock();
			return value > 1;
		}
		
		double px = (x >> 1) + 0.5;
		double pz = (z >> 1) + 0.5;

		double distortion1 = noise1.eval(px * 0.1, pz * 0.1) * 20 + noise2.eval(px * 0.2, pz * 0.2) * 10 + noise1.eval(
				px * 0.4,
				pz * 0.4
		) * 5;
		double distortion2 = noise2.eval(px * 0.1, pz * 0.1) * 20 + noise1.eval(px * 0.2, pz * 0.2) * 10 + noise2.eval(
				px * 0.4,
				pz * 0.4
		) * 5;
		px = px * SCALE_XZ + distortion1;
		pz = pz * SCALE_XZ + distortion2;
		
		largeIslands.updatePositions(px, pz);
		mediumIslands.updatePositions(px, pz);
		smallIslands.updatePositions(px, pz);

		boolean result = false;
		for (int y = 0; y < 32; y++) 
		{
			double py = (double) y * SCALE_Y;
			float dist = largeIslands.getDensity(px, py, pz);
			dist = dist > 1 ? dist : ModMathHelper.max(dist, mediumIslands.getDensity(px, py, pz));
			dist = dist > 1 ? dist : ModMathHelper.max(dist, smallIslands.getDensity(px, py, pz));
			if (dist > -0.5F) {
				dist += noise1.eval(px * 0.01, py * 0.01, pz * 0.01) * 0.02 + 0.02;
				dist += noise2.eval(px * 0.05, py * 0.05, pz * 0.05) * 0.01 + 0.01;
				dist += noise1.eval(px * 0.1, py * 0.1, pz * 0.1) * 0.005 + 0.005;
			}
			if (dist > -0.01) {
				result = true;
				break;
			}
		}
		section.setData(x, z, (byte) (result ? 2 : 1));
		LOCKER.unlock();

		return result;
	}
	
	/**
	 * Get something like height
	 * @param x - block pos x
	 * @param z - block pos z
	 */
	public static int getHeight(int x, int z) 
	{
		LOCKER.lock();
		
		double px = (double) x / 8.0;
		double pz = (double) z / 8.0;

		double distortion1 = noise1.eval(px * 0.1, pz * 0.1) * 20 + noise2.eval(px * 0.2, pz * 0.2) * 10 + noise1.eval(
				px * 0.4,
				pz * 0.4
		) * 5;
		double distortion2 = noise2.eval(px * 0.1, pz * 0.1) * 20 + noise1.eval(px * 0.2, pz * 0.2) * 10 + noise2.eval(
				px * 0.4,
				pz * 0.4
		) * 5;
		px = (double) x * SCALE_XZ + distortion1;
		pz = (double) z * SCALE_XZ + distortion2;
		
		largeIslands.updatePositions(px, pz);
		mediumIslands.updatePositions(px, pz);
		smallIslands.updatePositions(px, pz);
		
		for (int y = 32; y >= 0; y--) {
			double py = (double) y * SCALE_Y;
			float dist = largeIslands.getDensity(px, py, pz);
			dist = dist > 1 ? dist : ModMathHelper.max(dist, mediumIslands.getDensity(px, py, pz));
			dist = dist > 1 ? dist : ModMathHelper.max(dist, smallIslands.getDensity(px, py, pz));
			if (dist > -0.5F) {
				dist += noise1.eval(px * 0.01, py * 0.01, pz * 0.01) * 0.02 + 0.02;
				dist += noise2.eval(px * 0.05, py * 0.05, pz * 0.05) * 0.01 + 0.01;
				dist += noise1.eval(px * 0.1, py * 0.1, pz * 0.1) * 0.005 + 0.005;
			}
			if (dist > 0) {
				LOCKER.unlock();
				return Mth.floor(Mth.clamp(y + dist, y, y + 1) * SCALE_Y);
			}
		}
		
		LOCKER.unlock();
		return 0;
	}

	static {
		float sum = 0;
		List<Float> coef = Lists.newArrayList();
		List<Point> pos = Lists.newArrayList();
		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				float dist = ModMathHelper.length(x, z) / 3F;
				if (dist <= 1) {
					sum += dist;
					coef.add(dist);
					pos.add(new Point(x, z));
				}
			}
		}
		OFFS = pos.toArray(new Point[] {});
		COEF = new float[coef.size()];
		for (int i = 0; i < COEF.length; i++) {
			COEF[i] = coef.get(i) / sum;
		}
	}

}


