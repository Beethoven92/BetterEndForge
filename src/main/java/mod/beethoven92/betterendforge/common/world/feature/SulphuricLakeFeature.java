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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SulphuricLakeFeature extends Feature<NoFeatureConfig>
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(15152);
	private static final Mutable POS = new Mutable();
	
	public SulphuricLakeFeature() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos blockPos,
			NoFeatureConfig config) 
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
					if (world.getBlockState(POS).isIn(ModTags.GEN_TERRAIN)) 
					{
						if (isBorder(world, POS)) 
						{
							if (rand.nextInt(8) > 0) 
							{
								brimstone.add(POS.toImmutable());
								if (rand.nextBoolean()) 
								{
									brimstone.add(POS.down());
									if (rand.nextBoolean())
									{
										brimstone.add(POS.down(2));
									}
								}
							}
							else 
							{
								if (!isAbsoluteBorder(world, POS)) 
								{
									BlockHelper.setWithoutUpdate(world, POS, Blocks.WATER);
									world.getPendingFluidTicks().scheduleTick(POS, Fluids.WATER, 0);
									brimstone.add(POS.down());
									if (rand.nextBoolean()) 
									{
										brimstone.add(POS.down(2));
										if (rand.nextBoolean()) 
										{
											brimstone.add(POS.down(3));
										}
									}
								}
								else
								{
									brimstone.add(POS.toImmutable());
									if (rand.nextBoolean()) 
									{
										brimstone.add(POS.down());
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
								BlockPos offseted = POS.offset(dir);
								if (world.getBlockState(offseted).isIn(ModTags.GEN_TERRAIN)) 
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
									BlockPos offseted = POS.offset(dir);
									if (world.getBlockState(offseted).isIn(ModTags.GEN_TERRAIN)) 
									{
										brimstone.add(offseted);
									}
								}
							}
							brimstone.add(POS.down());
							if (rand.nextBoolean()) 
							{
								brimstone.add(POS.down(2));
								if (rand.nextBoolean()) 
								{
									brimstone.add(POS.down(3));
								}
							}
						}
					}
				}
				else if (dist < r2) 
				{
					POS.setY(FeatureHelper.getYOnSurface(world, x, z) - 1);
					if (world.getBlockState(POS).isIn(ModTags.GEN_TERRAIN))
					{
						brimstone.add(POS.toImmutable());
						if (rand.nextBoolean()) 
						{
							brimstone.add(POS.down());
							if (rand.nextBoolean()) 
							{
								brimstone.add(POS.down(2));
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

	private boolean isBorder(ISeedReader world, BlockPos pos) 
	{
		int y = pos.getY() + 1;
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (FeatureHelper.getYOnSurface(world, pos.getX() + dir.getXOffset(), pos.getZ() + dir.getZOffset()) < y) 
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isAbsoluteBorder(ISeedReader world, BlockPos pos) 
	{
		int y = pos.getY() - 2;
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (FeatureHelper.getYOnSurface(world, pos.getX() + dir.getXOffset() * 3, pos.getZ() + dir.getZOffset() * 3) < y) 
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isDeepWater(ISeedReader world, BlockPos pos) 
	{
		int y = pos.getY() + 1;
		for (Direction dir: BlockHelper.DIRECTIONS) {
			if (FeatureHelper.getYOnSurface(world, pos.getX() + dir.getXOffset(), pos.getZ() + dir.getZOffset()) < y
					|| FeatureHelper.getYOnSurface(world, pos.getX() + dir.getXOffset() * 2, pos.getZ() + dir.getZOffset() * 2) < y
					|| FeatureHelper.getYOnSurface(world, pos.getX() + dir.getXOffset() * 3, pos.getZ() + dir.getZOffset() * 3) < y) {
				return false;
			}
		}
		return true;
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
			if (world.getBlockState(pos.offset(dir)).isIn(Blocks.WATER)) {
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
