package mod.beethoven92.betterendforge.common.world.feature.caves;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.gen.feature.*;


import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TunelCaveFeature extends EndCaveFeature {

	private Set<BlockPos> generate(WorldGenLevel world, BlockPos center, Random random) {
		int cx = center.getX() >> 4;
		int cz = center.getZ() >> 4;
		if ((long) cx * (long) cx + (long) cz + (long) cz < 256) {
			return Sets.newHashSet();
		}

		int x1 = cx << 4;
		int z1 = cz << 4;
		int x2 = x1 + 16;
		int z2 = z1 + 16;

		Random rand = new Random(world.getSeed());
		OpenSimplexNoise noiseH = new OpenSimplexNoise(rand.nextInt());
		OpenSimplexNoise noiseV = new OpenSimplexNoise(rand.nextInt());
		OpenSimplexNoise noiseD = new OpenSimplexNoise(rand.nextInt());

		Set<BlockPos> positions = Sets.newConcurrentHashSet();

		float a = hasCaves(world, new BlockPos(x1, 0, z1)) ? 1F : 0F;
		float b = hasCaves(world, new BlockPos(x2, 0, z1)) ? 1F : 0F;
		float c = hasCaves(world, new BlockPos(x1, 0, z2)) ? 1F : 0F;
		float d = hasCaves(world, new BlockPos(x2, 0, z2)) ? 1F : 0F;

		ChunkAccess chunk = world.getChunk(cx, cz);
		IntStream.range(0, 256).parallel().forEach(index -> {
			BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
			int x = index & 15;
			int z = index >> 4;
			int wheight = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
			float dx = x / 16F;
			float dz = z / 16F;
			pos.setX(x + x1);
			pos.setZ(z + z1);
			float da = Mth.lerp(dx, a, b);
			float db = Mth.lerp(dx, c, d);
			float density = 1 - Mth.lerp(dz, da, db);
			if (density < 0.5) {
				for (int y = 0; y < wheight; y++) {
					pos.setY(y);
					float gradient = 1 - Mth.clamp((wheight - y) * 0.1F, 0F, 1F);
					if (gradient > 0.5) {
						break;
					}
					float val = Mth.abs((float) noiseH.eval(pos.getX() * 0.02, y * 0.01, pos.getZ() * 0.02));
					float vert = Mth.sin((y + (float) noiseV.eval(
						pos.getX() * 0.01,
						pos.getZ() * 0.01
					) * 20) * 0.1F) * 0.9F;
					float dist = (float) noiseD.eval(pos.getX() * 0.1, y * 0.1, pos.getZ() * 0.1) * 0.12F;
					val = (val + vert * vert + dist) + density + gradient;
					if (val < 0.15 && world.getBlockState(pos).is(ModTags.GEN_TERRAIN) && noWaterNear(world, pos)) {
						positions.add(pos.immutable());
					}
				}
			}
		});
		positions.forEach(bpos -> BlockHelper.setWithoutUpdate(world, bpos, CAVE_AIR));

		return positions;
	}

	private boolean noWaterNear(WorldGenLevel world, BlockPos pos) {
		BlockPos above1 = pos.above();
		BlockPos above2 = pos.above(2);
		if (!world.getFluidState(above1).isEmpty() || !world.getFluidState(above2).isEmpty()) {
			return false;
		}
		for (Direction dir : BlockHelper.HORIZONTAL_DIRECTIONS) {
			if (!world.getFluidState(above1.relative(dir)).isEmpty()) {
				return false;
			}
			if (!world.getFluidState(above2.relative(dir)).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random random,
						 BlockPos pos, NoneFeatureConfiguration config) {
		if (pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 2500) {
			return false;
		}

		if (biomeMissingCaves(world, pos)) {
			return false;
		}

		Set<BlockPos> caveBlocks = generate(world, pos, random);
		if (caveBlocks.isEmpty()) {
			return false;
		}

		Map<BetterEndCaveBiome, Set<BlockPos>> floorSets = Maps.newHashMap();
		Map<BetterEndCaveBiome, Set<BlockPos>> ceilSets = Maps.newHashMap();
		BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
		Set<BlockPos> remove = Sets.newHashSet();
		caveBlocks.forEach((bpos) -> {
			mut.set(bpos);
			BetterEndCaveBiome bio = ModBiomes.getCaveBiome(random);
			int height = world.getHeight(Heightmap.Types.WORLD_SURFACE, bpos.getX(), bpos.getZ());
			if (mut.getY() >= height) {
				remove.add(bpos);
			}
			else if (world.getBlockState(mut).getMaterial().isReplaceable()) {
				mut.setY(bpos.getY() - 1);
				if (world.getBlockState(mut).is(ModTags.GEN_TERRAIN)) {
					Set<BlockPos> floorPositions = floorSets.get(bio);
					if (floorPositions == null) {
						floorPositions = Sets.newHashSet();
						floorSets.put(bio, floorPositions);
					}
					floorPositions.add(mut.immutable());
				}
				mut.setY(bpos.getY() + 1);
				if (world.getBlockState(mut).is(ModTags.GEN_TERRAIN)) {
					Set<BlockPos> ceilPositions = ceilSets.get(bio);
					if (ceilPositions == null) {
						ceilPositions = Sets.newHashSet();
						ceilSets.put(bio, ceilPositions);
					}
					ceilPositions.add(mut.immutable());
				}
				setBiome(world, bpos, bio);
			}
		});
		caveBlocks.removeAll(remove);

		if (caveBlocks.isEmpty()) {
			return true;
		}

		floorSets.forEach((biome, floorPositions) -> {
			BlockState surfaceBlock = biome.getBiome()
					.getGenerationSettings()
					.getSurfaceBuilderConfig()
					.getTopMaterial();
			placeFloor(world, biome, floorPositions, random, surfaceBlock);
		});
		ceilSets.forEach((biome, ceilPositions) -> {
			placeCeil(world, biome, ceilPositions, random);
		});
		BetterEndCaveBiome biome = ModBiomes.getCaveBiome(random);
		placeWalls(world, biome, caveBlocks, random);
		fixBlocks(world, caveBlocks);

		return true;
	}
	@Override
	protected Set<BlockPos> generateCaveBlocks(WorldGenLevel world, BlockPos center, int radius, Random random) {
		return null;
	}


	@Override
	protected void placeFloor(WorldGenLevel world, BetterEndCaveBiome biome, Set<BlockPos> floorPositions, Random random, BlockState surfaceBlock) {
		float density = biome.getFloorDensity() * 0.2F;
		floorPositions.forEach((pos) -> {
			if (!surfaceBlock.is(Blocks.END_STONE)) {
				BlockHelper.setWithoutUpdate(world, pos, surfaceBlock);
			}
			if (density > 0 && random.nextFloat() <= density) {
				Feature<?> feature = biome.getFloorFeature(random);
				if (feature != null) {
					feature.place(world, null, random, pos.above(), null);
				}
			}
		});
	}

	@Override
	protected void placeCeil(WorldGenLevel world, BetterEndCaveBiome biome, Set<BlockPos> ceilPositions, Random random) {
		float density = biome.getCeilDensity() * 0.2F;
		ceilPositions.forEach((pos) -> {
			BlockState ceilBlock = biome.getCeil(pos);
			if (ceilBlock != null) {
				BlockHelper.setWithoutUpdate(world, pos, ceilBlock);
			}
			if (density > 0 && random.nextFloat() <= density) {
				Feature<?> feature = biome.getCeilFeature(random);
				if (feature != null) {
					feature.place(world, null, random, pos.below(), null);
				}
			}
		});
	}

	protected boolean hasCaves(WorldGenLevel world, BlockPos pos) {
		return hasCavesInBiome(world, pos.offset(-8, 0, -8)) &&
				hasCavesInBiome(world, pos.offset(8, 0, -8)) &&
				hasCavesInBiome(world, pos.offset(-8, 0, 8)) &&
				hasCavesInBiome(world, pos.offset(8, 0, 8));
	}

	protected boolean hasCavesInBiome(WorldGenLevel world, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
		return endBiome.hasCaves();
	}
}


