package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.SulphurCrystalBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SulphuricLakeFeature extends Feature<NoneFeatureConfiguration>
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(15152);
	private static final MutableBlockPos POS = new MutableBlockPos();
	
	public SulphuricLakeFeature() 
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos blockPos,
			NoneFeatureConfiguration config) 
	{
		blockPos = FeatureHelper.getPosOnSurfaceWG(world, blockPos);
		
		if (blockPos.getY() < 57) 
		{
			return false;
		}
		
		double radius = ModMathHelper.randRange(10.0, 20.0, rand);
		int dist2 = ModMathHelper.floor(radius * 1.5);
		
		int minX = blockPos.getX() - dist2;
		int maxX = blockPos.getX() + dist2;
		int minZ = blockPos.getZ() - dist2;
		int maxZ = blockPos.getZ() + dist2;
		
		Set<BlockPos> brimstone = Sets.newHashSet();
		for (int x = minX; x <= maxX; x++) 
		{
			POS.setX(x);
			int x2 = x - blockPos.getX();
			x2 *= x2;
			for (int z = minZ; z <= maxZ; z++) 
			{
				POS.setZ(z);
				int z2 = z - blockPos.getZ();
				z2 *= z2;
				double r = radius * (NOISE.eval(x * 0.2, z * 0.2) * 0.25 + 0.75);
				double r2 = r * 1.5;
				r *= r;
				r2 *= r2;
				int dist = x2 + z2;
				if (dist <= r) 
				{
					POS.setY(FeatureHelper.getYOnSurface(world, x, z) - 1);
					if (world.getBlockState(POS).is(ModTags.GEN_TERRAIN)) 
					{
						if (isBorder(world, POS)) 
						{
							if (rand.nextInt(8) > 0) 
							{
								brimstone.add(POS.immutable());
								if (rand.nextBoolean()) 
								{
									brimstone.add(POS.below());
									if (rand.nextBoolean())
									{
										brimstone.add(POS.below(2));
									}
								}
							}
							else 
							{
								if (!isAbsoluteBorder(world, POS)) 
								{
									BlockHelper.setWithoutUpdate(world, POS, Blocks.WATER);
									world.getLiquidTicks().scheduleTick(POS, Fluids.WATER, 0);
									brimstone.add(POS.below());
									if (rand.nextBoolean()) 
									{
										brimstone.add(POS.below(2));
										if (rand.nextBoolean()) 
										{
											brimstone.add(POS.below(3));
										}
									}
								}
								else
								{
									brimstone.add(POS.immutable());
									if (rand.nextBoolean()) 
									{
										brimstone.add(POS.below());
									}
								}
							}
						}
						else 
						{
							BlockHelper.setWithoutUpdate(world, POS, Blocks.WATER);
							brimstone.remove(POS);
							for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
							{
								BlockPos offseted = POS.relative(dir);
								if (world.getBlockState(offseted).is(ModTags.GEN_TERRAIN)) 
								{
									brimstone.add(offseted);
								}
							}
							if (isDeepWater(world, POS)) 
							{
								BlockHelper.setWithoutUpdate(world, POS.move(Direction.DOWN), Blocks.WATER);
								brimstone.remove(POS);
								for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
								{
									BlockPos offseted = POS.relative(dir);
									if (world.getBlockState(offseted).is(ModTags.GEN_TERRAIN)) 
									{
										brimstone.add(offseted);
									}
								}
							}
							brimstone.add(POS.below());
							if (rand.nextBoolean()) 
							{
								brimstone.add(POS.below(2));
								if (rand.nextBoolean()) 
								{
									brimstone.add(POS.below(3));
								}
							}
						}
					}
				}
				else if (dist < r2) 
				{
					POS.setY(FeatureHelper.getYOnSurface(world, x, z) - 1);
					if (world.getBlockState(POS).is(ModTags.GEN_TERRAIN))
					{
						brimstone.add(POS.immutable());
						if (rand.nextBoolean()) 
						{
							brimstone.add(POS.below());
							if (rand.nextBoolean()) 
							{
								brimstone.add(POS.below(2));
							}
						}
					}
				}
			}
		}
		
		brimstone.forEach((bpos) -> {
			placeBrimstone(world, bpos, rand);
		});
		
		return true;
	}

	private boolean isBorder(WorldGenLevel world, BlockPos pos) 
	{
		int y = pos.getY() + 1;
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (FeatureHelper.getYOnSurface(world, pos.getX() + dir.getStepX(), pos.getZ() + dir.getStepZ()) < y) 
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isAbsoluteBorder(WorldGenLevel world, BlockPos pos) 
	{
		int y = pos.getY() - 2;
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (FeatureHelper.getYOnSurface(world, pos.getX() + dir.getStepX() * 3, pos.getZ() + dir.getStepZ() * 3) < y) 
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isDeepWater(WorldGenLevel world, BlockPos pos) 
	{
		int y = pos.getY() + 1;
		for (Direction dir: BlockHelper.DIRECTIONS) {
			if (FeatureHelper.getYOnSurface(world, pos.getX() + dir.getStepX(), pos.getZ() + dir.getStepZ()) < y
					|| FeatureHelper.getYOnSurface(world, pos.getX() + dir.getStepX() * 2, pos.getZ() + dir.getStepZ() * 2) < y
					|| FeatureHelper.getYOnSurface(world, pos.getX() + dir.getStepX() * 3, pos.getZ() + dir.getStepZ() * 3) < y) {
				return false;
			}
		}
		return true;
	}
	
	private void placeBrimstone(WorldGenLevel world, BlockPos pos, Random random) 
	{
		BlockState state = getBrimstone(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, state);
		if (state.getValue(BlockProperties.ACTIVATED)) 
		{
			makeShards(world, pos, random);
		}
	}
	
	private BlockState getBrimstone(WorldGenLevel world, BlockPos pos) 
	{
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (world.getBlockState(pos.relative(dir)).is(Blocks.WATER)) {
				return ModBlocks.BRIMSTONE.get().defaultBlockState().setValue(BlockProperties.ACTIVATED, true);
			}
		}
		return ModBlocks.BRIMSTONE.get().defaultBlockState();
	}
	
	private void makeShards(WorldGenLevel world, BlockPos pos, Random random) 
	{
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			BlockPos side;
			if (random.nextInt(16) == 0 && world.getBlockState((side = pos.relative(dir))).is(Blocks.WATER))
			{
				BlockState state = ModBlocks.SULPHUR_CRYSTAL.get().defaultBlockState()
						.setValue(SulphurCrystalBlock.WATERLOGGED, true)
						.setValue(SulphurCrystalBlock.FACING, dir)
						.setValue(SulphurCrystalBlock.AGE, random.nextInt(3));
				BlockHelper.setWithoutUpdate(world, side, state);
			}
		}
	}
}
