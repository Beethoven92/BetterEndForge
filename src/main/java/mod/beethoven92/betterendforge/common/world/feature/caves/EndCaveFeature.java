package mod.beethoven92.betterendforge.common.world.feature.caves;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.interfaces.IBiomeArray;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class EndCaveFeature extends Feature<NoFeatureConfig>
{
	protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	protected static final BlockState END_STONE = Blocks.END_STONE.getDefaultState();
	protected static final BlockState WATER = Blocks.WATER.getDefaultState();
	private static final Vector3i[] SPHERE;
	
	public EndCaveFeature() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand,
			BlockPos pos, NoFeatureConfig config)
	{
		/*if (!(CommonConfig.isNewGeneratorEnabled() && GeneratorOptions.noRingVoid()) || pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 22500)
		{
			return false;
		}*/
		if (!(GeneratorOptions.useNewGenerator() && GeneratorOptions.noRingVoid()))
		{
			if (pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 22500)
			{
				return false;
			}
		}

		if (biomeMissingCaves(world, pos))
		{
			return false;
		}

		int radius = ModMathHelper.randRange(10, 30, rand);
		BlockPos center = findPos(world, pos, radius, rand);

		if (center == null)
		{
			return false;
		}

		BetterEndCaveBiome biome = ModBiomes.getCaveBiome(rand);
		Set<BlockPos> caveBlocks = generateCaveBlocks(world, center, radius, rand);
		if (!caveBlocks.isEmpty())
		{
			if (biome != null)
			{
				setBiomes(world, biome, caveBlocks);
				Set<BlockPos> floorPositions = Sets.newHashSet();
				Set<BlockPos> ceilPositions = Sets.newHashSet();
				Mutable mut = new Mutable();
				caveBlocks.forEach((bpos) -> {
					mut.setPos(bpos);
					if (world.getBlockState(mut).getMaterial().isReplaceable())
					{
						mut.setY(bpos.getY() - 1);
						if (world.getBlockState(mut).isIn(ModTags.GEN_TERRAIN))
						{
							floorPositions.add(mut.toImmutable());
						}
						mut.setY(bpos.getY() + 1);
						if (world.getBlockState(mut).isIn(ModTags.GEN_TERRAIN))
						{
							ceilPositions.add(mut.toImmutable());
						}
					}
				});
				BlockState surfaceBlock = biome.getBiome().getGenerationSettings().getSurfaceBuilderConfig().getTop();
				placeFloor(world, biome, floorPositions, rand, surfaceBlock);
				placeCeil(world, biome, ceilPositions, rand);
				placeWalls(world, biome, caveBlocks, rand);
			}
			fixBlocks(world, caveBlocks);
		}

		return true;
	}

	protected abstract Set<BlockPos> generateCaveBlocks(ISeedReader world, BlockPos center, int radius, Random random);

	protected void placeFloor(ISeedReader world, BetterEndCaveBiome biome, Set<BlockPos> floorPositions, 
			Random random, BlockState surfaceBlock) 
	{
		float density = biome.getFloorDensity();
		floorPositions.forEach((pos) -> {
			BlockHelper.setWithoutUpdate(world, pos, surfaceBlock);
			if (density > 0 && random.nextFloat() <= density) 
			{
				Feature<?> feature = biome.getFloorFeature(random);
				if (feature != null) 
				{
					feature.generate(world, null, random, pos.up(), null);
				}
			}
		});
	}
	
	protected void placeCeil(ISeedReader world, BetterEndCaveBiome biome, Set<BlockPos> ceilPositions, Random random) 
	{
		float density = biome.getCeilDensity();
		ceilPositions.forEach((pos) -> {
			BlockState ceilBlock = biome.getCeil(pos);
			if (ceilBlock != null) {
				BlockHelper.setWithoutUpdate(world, pos, ceilBlock);
			}
			if (density > 0 && random.nextFloat() <= density) 
			{
				Feature<?> feature = biome.getCeilFeature(random);
				if (feature != null) 
				{
					feature.generate(world, null, random, pos.down(), null);
				}
			}
		});
	}


	protected void placeWalls(ISeedReader world, BetterEndCaveBiome biome, Set<BlockPos> positions, Random random) {
		Set<BlockPos> placed = Sets.newHashSet();
		positions.forEach(pos -> {
			if (random.nextInt(4) == 0 && hasOpenSide(pos, positions)) {
				BlockState wallBlock = biome.getWall(pos);
				if (wallBlock != null) {
					for (Vector3i offset : SPHERE) {
						BlockPos wallPos = pos.add(offset);
						if (!positions.contains(wallPos) && !placed.contains(wallPos) && world.getBlockState(wallPos)
								.isIn(ModTags.GEN_TERRAIN)) {
							wallBlock = biome.getWall(wallPos);
							BlockHelper.setWithoutUpdate(world, wallPos, wallBlock);
							placed.add(wallPos);
						}
					}
				}
			}
		});
	}



	private boolean hasOpenSide(BlockPos pos, Set<BlockPos> positions) {
		for (Direction dir : BlockHelper.DIRECTIONS) {
			if (!positions.contains(pos.offset(dir))) {
				return true;
			}
		}
		return false;
	}

	
	protected void setBiomes(ISeedReader world, BetterEndCaveBiome biome, Set<BlockPos> blocks) 
	{
		blocks.forEach((pos) -> setBiome(world, pos, biome));
	}
	
	public void setBiome(ISeedReader world, BlockPos pos, BetterEndCaveBiome biome)
	{
		IBiomeArray array = (IBiomeArray) world.getChunk(pos).getBiomes();
		if (array != null) 
		{
			array.setBiome(biome.getActualBiome(), pos);
		}
	}
	
	private BlockPos findPos(ISeedReader world, BlockPos pos, int radius, Random random) 
	{
		int top = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
		Mutable bpos = new Mutable();
		bpos.setX(pos.getX());
		bpos.setZ(pos.getZ());
		bpos.setY(top - 1);
		
		BlockState state = world.getBlockState(bpos);
		while (!state.isIn(ModTags.GEN_TERRAIN) && bpos.getY() > 5) 
		{
			bpos.setY(bpos.getY() - 1);
			state = world.getBlockState(bpos);
		}
		if (bpos.getY() < 10) 
		{
			return null;
		}
		top = (int) (bpos.getY() - (radius * 1.3F + 5));
		
		while (state.isIn(ModTags.GEN_TERRAIN) || !state.getFluidState().isEmpty() && bpos.getY() > 5) 
		{
			bpos.setY(bpos.getY() - 1);
			state = world.getBlockState(bpos);
		}
		int bottom = (int) (bpos.getY() + radius * 1.3F + 5);
		
		if (top <= bottom) 
		{
			return null;
		}
		
		return new BlockPos(pos.getX(), ModMathHelper.randRange(bottom, top, random), pos.getZ());
	}
	
	protected void fixBlocks(ISeedReader world, Set<BlockPos> caveBlocks)
	{
		BlockPos pos = caveBlocks.iterator().next();
		Mutable start = new Mutable().setPos(pos);
		Mutable end = new Mutable().setPos(pos);
		caveBlocks.forEach((bpos) -> {
			if (bpos.getX() < start.getX())
			{
				start.setX(bpos.getX());
			}
			if (bpos.getX() > end.getX())
			{
				end.setX(bpos.getX());
			}

			if (bpos.getY() < start.getY())
			{
				start.setY(bpos.getY());
			}
			if (bpos.getY() > end.getY())
			{
				end.setY(bpos.getY());
			}

			if (bpos.getZ() < start.getZ())
			{
				start.setZ(bpos.getZ());
			}
			if (bpos.getZ() > end.getZ())
			{
				end.setZ(bpos.getZ());
			}
		});
		BlockHelper.fixBlocks(world, start.add(-5, -5, -5), end.add(5, 5, 5));
	}
	
	protected boolean isWaterNear(ISeedReader world, BlockPos pos) 
	{
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (!world.getFluidState(pos.offset(dir, 5)).isEmpty()) 
			{
				return true;
			}
		}
		return false;
	}



	protected boolean biomeMissingCaves(ISeedReader world, BlockPos pos) 
	{
		for (int x = -2; x < 3; x++) 
		{
			for (int z = -2; z < 3; z++)
			{
				Biome biome = world.getBiome(pos.add(x << 4, 0, z << 4));
				BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
				if (!endBiome.hasCaves()) 
				{
					return true;
				}
			}
		}
		return false;
	}

	static {
		List<Vector3i> prePos = Lists.newArrayList();
		int radius = 5;
		int r2 = radius * radius;
		for (int x = -radius; x <= radius; x++) {
			int x2 = x * x;
			for (int y = -radius; y <= radius; y++) {
				int y2 = y * y;
				for (int z = -radius; z <= radius; z++) {
					int z2 = z * z;
					if (x2 + y2 + z2 < r2) {
						prePos.add(new Vector3i(x, y, z));
					}
				}
			}
		}
		SPHERE = prePos.toArray(new Vector3i[] {});
	}
}
