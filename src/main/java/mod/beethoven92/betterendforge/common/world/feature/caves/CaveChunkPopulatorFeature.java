package mod.beethoven92.betterendforge.common.world.feature.caves;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class CaveChunkPopulatorFeature extends Feature<NoFeatureConfig>
{
	private Supplier<BetterEndCaveBiome> supplier;



	public CaveChunkPopulatorFeature()
	{
		super(NoFeatureConfig.field_236558_a_);
	}



	public boolean generate(ISeedReader world, ChunkGenerator generator, Random random,
							BlockPos pos, NoFeatureConfig config)
	{

		Set<BlockPos> floorPositions = Sets.newHashSet();
		Set<BlockPos> ceilPositions = Sets.newHashSet();
		int sx = (pos.getX() >> 4) << 4;
		int sz = (pos.getZ() >> 4) << 4;
		BlockPos.Mutable min = new BlockPos.Mutable().setPos(pos);
		BlockPos.Mutable max = new BlockPos.Mutable().setPos(pos);
		fillSets(sx, sz, world.getChunk(pos), floorPositions, ceilPositions, min, max);
		BetterEndCaveBiome biome = supplier.get();
		BlockState surfaceBlock = biome.getBiome().getGenerationSettings().getSurfaceBuilderConfig().getTop();
		placeFloor(world, biome, floorPositions, random, surfaceBlock);
		placeCeil(world, biome, ceilPositions, random);
		BlockHelper.fixBlocks(world, min, max);
		return true;
	}
	
	protected void fillSets(int sx, int sz, IChunk chunk, Set<BlockPos> floorPositions, Set<BlockPos> ceilPositions, BlockPos.Mutable min, BlockPos.Mutable max) {
		BlockPos.Mutable mut = new BlockPos.Mutable();
		BlockPos.Mutable mut2 = new BlockPos.Mutable();
		BlockPos.Mutable mut3 = new BlockPos.Mutable();
		for (int x = 0; x < 16; x++) {
			mut.setX(x);
			mut2.setX(x);
			for (int z = 0; z < 16; z++) {
				mut.setZ(z);
				mut2.setZ(z);
				mut2.setY(0);
				for (int y = 1; y < chunk.getHeight(); y++) {
					mut.setY(y);
					BlockState top = chunk.getBlockState(mut);
					BlockState bottom = chunk.getBlockState(mut2);
					if (top.isAir() && (bottom.isIn(ModTags.GEN_TERRAIN) || bottom.isIn(Blocks.STONE))) {
						mut3.setPos(mut2).move(sx, 0, sz);
						floorPositions.add(mut3.toImmutable());
						updateMin(mut3, min);
						updateMax(mut3, max);
					}
					else if (bottom.isAir() && (top.isIn(ModTags.GEN_TERRAIN) || top.isIn(Blocks.STONE))) {
						mut3.setPos(mut).move(sx, 0, sz);
						ceilPositions.add(mut3.toImmutable());
						updateMin(mut3, min);
						updateMax(mut3, max);
					}
					mut2.setY(y);
				}
			}
		}
	}
	
	private void updateMin(BlockPos pos, BlockPos.Mutable min) {
		if (pos.getX() < min.getX()) {
			min.setX(pos.getX());
		}
		if (pos.getY() < min.getY()) {
			min.setY(pos.getY());
		}
		if (pos.getZ() < min.getZ()) {
			min.setZ(pos.getZ());
		}
	}
	
	private void updateMax(BlockPos pos, BlockPos.Mutable max) {
		if (pos.getX() > max.getX()) {
			max.setX(pos.getX());
		}
		if (pos.getY() > max.getY()) {
			max.setY(pos.getY());
		}
		if (pos.getZ() > max.getZ()) {
			max.setZ(pos.getZ());
		}
	}
	
	protected void placeFloor(ISeedReader world, BetterEndCaveBiome biome, Set<BlockPos> floorPositions, Random random, BlockState surfaceBlock) {
		float density = biome.getFloorDensity();
		floorPositions.forEach((pos) -> {
			BlockHelper.setWithoutUpdate(world, pos, surfaceBlock);
			if (density > 0 && random.nextFloat() <= density) {
				Feature<?> feature = biome.getFloorFeature(random);
				if (feature != null) {
					feature.generate(world, null, random, pos.up(), null);
				}
			}
		});
	}
	
	protected void placeCeil(ISeedReader world, BetterEndCaveBiome biome, Set<BlockPos> ceilPositions, Random random) {
		float density = biome.getCeilDensity();
		ceilPositions.forEach((pos) -> {
			BlockState ceilBlock = biome.getCeil(pos);
			if (ceilBlock != null) {
				BlockHelper.setWithoutUpdate(world, pos, ceilBlock);
			}
			if (density > 0 && random.nextFloat() <= density) {
				Feature<?> feature = biome.getCeilFeature(random);
				if (feature != null) {
					feature.generate(world, null, random, pos.down(), null);
				}
			}
		});
	}
}
