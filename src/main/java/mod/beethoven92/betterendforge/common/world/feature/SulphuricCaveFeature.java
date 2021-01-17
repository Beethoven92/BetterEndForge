package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.SulphurCrystalBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SulphuricCaveFeature extends Feature<NoFeatureConfig>
{
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	private static final Direction[] HORIZONTAL = BlockHelper.makeHorizontal();
	
	public SulphuricCaveFeature() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		int radius = ModMathHelper.randRange(10, 30, rand);
		
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
			return false;
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
			return false;
		}
		
		Mutable mut = new Mutable();
		pos = new BlockPos(pos.getX(), ModMathHelper.randRange(bottom, top, rand), pos.getZ());
		
		OpenSimplexNoise noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, pos.getX(), pos.getZ()));
		
		int x1 = pos.getX() - radius - 5;
		int z1 = pos.getZ() - radius - 5;
		int x2 = pos.getX() + radius + 5;
		int z2 = pos.getZ() + radius + 5;
		int y1 = ModMathHelper.floor(pos.getY() - (radius + 5) / 1.6);
		int y2 = ModMathHelper.floor(pos.getY() + (radius + 5) / 1.6);
		
		double hr = radius * 0.75;
		double nr = radius * 0.25;
		
		Set<BlockPos> brimstone = Sets.newHashSet();
		BlockState rock = ModBlocks.SULPHURIC_ROCK.stone.get().getDefaultState();
		int waterLevel = pos.getY() + ModMathHelper.randRange(ModMathHelper.floor(radius * 0.8), radius, rand);
		for (int x = x1; x <= x2; x++)
		{
			int xsq = x - pos.getX();
			xsq *= xsq;
			mut.setX(x);
			for (int z = z1; z <= z2; z++) 
			{
				int zsq = z - pos.getZ();
				zsq *= zsq;
				mut.setZ(z);
				for (int y = y1; y <= y2; y++)
				{
					int ysq = y - pos.getY();
					ysq *= 1.6;
					ysq *= ysq;
					mut.setY(y);
					double r = noise.eval(x * 0.1, y * 0.1, z * 0.1) * nr + hr;
					double r2 = r + 5;
					double dist = xsq + ysq + zsq;
					if (dist < r * r) 
					{
						state = world.getBlockState(mut);
						if (isReplaceable(state)) 
						{
							BlockHelper.setWithoutUpdate(world, mut, y < waterLevel ? WATER : CAVE_AIR);
						}
					}
					else if (dist < r2 * r2) 
					{
						state = world.getBlockState(mut);
						if (state.isIn(ModTags.GEN_TERRAIN) || state.isIn(Blocks.AIR)) 
						{
							double v = noise.eval(x * 0.1, y * 0.1, z * 0.1) + noise.eval(x * 0.03, y * 0.03, z * 0.03) * 0.5;
							if (v > 0.4) 
							{
								brimstone.add(mut.toImmutable());
							}
							else 
							{
								BlockHelper.setWithoutUpdate(world, mut, rock);
							}
						}
					}
				}
			}
		}
		brimstone.forEach((blockPos) -> {
			placeBrimstone(world, blockPos, rand);
		});
		
		if (rand.nextInt(4) == 0) 
		{
			int count = ModMathHelper.randRange(5, 20, rand);
			for (int i = 0; i < count; i++) 
			{
				mut.setPos(pos).move(ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5), 0, ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5));
				int dist = ModMathHelper.floor(3 - ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ())) + rand.nextInt(2);
				if (dist > 0)
				{
					state = world.getBlockState(mut);
					while (!state.getFluidState().isEmpty() || state.getMaterial().equals(Material.OCEAN_PLANT)) 
					{
						mut.setY(mut.getY() - 1);
						state = world.getBlockState(mut);
					}
					if (state.isIn(ModTags.GEN_TERRAIN) && !world.getBlockState(mut.up()).isIn(ModBlocks.HYDROTHERMAL_VENT.get())) 
					{
						for (int j = 0; j <= dist; j++) 
						{
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.SULPHURIC_ROCK.stone.get());
							ModMathHelper.shuffle(HORIZONTAL, rand);
							for (Direction dir: HORIZONTAL) 
							{
								BlockPos p = mut.offset(dir);
								if (rand.nextBoolean() && world.getBlockState(p).isIn(Blocks.WATER)) 
								{
									BlockHelper.setWithoutUpdate(world, p, ModBlocks.TUBE_WORM.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, dir));
								}
							}
							mut.setY(mut.getY() + 1);
						}
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.HYDROTHERMAL_VENT.get());
						mut.setY(mut.getY() + 1);
						state = world.getBlockState(mut);
						while (state.isIn(Blocks.WATER)) 
						{
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.VENT_BUBBLE_COLUMN.get().getDefaultState());
							world.getPendingBlockTicks().scheduleTick(mut, ModBlocks.VENT_BUBBLE_COLUMN.get(), ModMathHelper.randRange(8, 32, rand));
							mut.setY(mut.getY() + 1);
							state = world.getBlockState(mut);
						}
					}
				}
			}
		}
		
		BlockHelper.fixBlocks(world, new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
		
		return true;
	}

	private boolean isReplaceable(BlockState state) 
	{
		return state.isIn(ModTags.GEN_TERRAIN)
				|| state.isIn(ModBlocks.HYDROTHERMAL_VENT.get())
				|| state.isIn(ModBlocks.VENT_BUBBLE_COLUMN.get())
				|| state.isIn(ModBlocks.SULPHUR_CRYSTAL.get())
				|| state.getMaterial().isReplaceable()
				|| state.getMaterial().equals(Material.PLANTS)
				|| state.getMaterial().equals(Material.OCEAN_PLANT)
				|| state.getMaterial().equals(Material.LEAVES);
	}
	
	private void placeBrimstone(ISeedReader world, BlockPos pos, Random random) 
	{
		BlockState state = getBrimstone(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, state);
		if (state.get(BlockProperties.ACTIVATED))
		{
			makeShards(world, pos, random);
		}
	}
	
	private BlockState getBrimstone(ISeedReader world, BlockPos pos) 
	{
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (world.getBlockState(pos.offset(dir)).isIn(Blocks.WATER)) 
			{
				return ModBlocks.BRIMSTONE.get().getDefaultState().with(BlockProperties.ACTIVATED, true);
			}
		}
		return ModBlocks.BRIMSTONE.get().getDefaultState();
	}
	
	private void makeShards(ISeedReader world, BlockPos pos, Random random) 
	{
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			BlockPos side;
			if (random.nextInt(16) == 0 && world.getBlockState((side = pos.offset(dir))).isIn(Blocks.WATER)) 
			{
				BlockState state = ModBlocks.SULPHUR_CRYSTAL.get().getDefaultState()
						.with(SulphurCrystalBlock.WATERLOGGED, true)
						.with(SulphurCrystalBlock.FACING, dir)
						.with(SulphurCrystalBlock.AGE, random.nextInt(3));
				BlockHelper.setWithoutUpdate(world, side, state);
			}
		}
	}
}
