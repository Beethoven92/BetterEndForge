package mod.beethoven92.betterendforge.common.world.feature.caves;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.Tags;


import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

public class TunelCaveFeature extends EndCaveFeature {

	@Override
	protected Set<BlockPos> generateCaveBlocks(ISeedReader world, BlockPos center, int radius, Random random)
	{
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

		IChunk chunk = world.getChunk(cx, cz);
		IntStream.range(0, 256).parallel().forEach(index -> {
			BlockPos.Mutable pos = new BlockPos.Mutable();
			int x = index & 15;
			int z = index >> 4;
			int wheight = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
			float dx = x / 16F;
			float dz = z / 16F;
			pos.setX(x + x1);
			pos.setZ(z + z1);
			float da = MathHelper.lerp(dx, a, b);
			float db = MathHelper.lerp(dx, c, d);
			float density = 1 - MathHelper.lerp(dz, da, db);
			if (density < 0.5) {
				for (int y = 0; y < wheight; y++) {
					pos.setY(y);
					float gradient = 1 - MathHelper.clamp((wheight - y) * 0.1F, 0F, 1F);
					if (gradient > 0.5) {
						break;
					}
					float val = MathHelper.abs((float) noiseH.eval(pos.getX() * 0.02, y * 0.01, pos.getZ() * 0.02));
					float vert = MathHelper.sin((y + (float) noiseV.eval(
							pos.getX() * 0.01,
							pos.getZ() * 0.01
					) * 20) * 0.1F) * 0.9F;
					float dist = (float) noiseD.eval(pos.getX() * 0.1, y * 0.1, pos.getZ() * 0.1) * 0.12F;
					val = (val + vert * vert + dist) + density + gradient;
					if (val < 0.15 && world.getBlockState(pos).isIn(ModTags.GEN_TERRAIN)) {
						positions.add(pos.toImmutable());
					}
				}
			}
		});
		positions.forEach(bpos -> BlockHelper.setWithoutUpdate(world, bpos, CAVE_AIR));

		return positions;
	}

	protected boolean hasCaves(ISeedReader world, BlockPos pos) {
		return hasCavesInBiome(world, pos.add(-8, 0, -8)) &&
				hasCavesInBiome(world, pos.add(8, 0, -8)) &&
				hasCavesInBiome(world, pos.add(-8, 0, 8)) &&
				hasCavesInBiome(world, pos.add(8, 0, 8));
	}

	protected boolean hasCavesInBiome(ISeedReader world, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
		return endBiome.hasCaves();
	}

	private boolean isReplaceable(BlockState state)
	{
		return state.isIn(ModTags.GEN_TERRAIN)
				|| state.getMaterial().isReplaceable()
				|| state.getMaterial().equals(Material.PLANTS)
				|| state.getMaterial().equals(Material.LEAVES)
				|| state.isIn(Tags.Blocks.ORES) // Handles floating ores
				|| state.isIn(Tags.Blocks.END_STONES); // Handles other blocks that could be left floating
	}
}


