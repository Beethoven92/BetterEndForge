package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

public class EndLakeFeature extends Feature<NoFeatureConfig>
{
	private static final BlockState END_STONE = Blocks.END_STONE.defaultBlockState();
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(15152);
	private static final Mutable POS = new Mutable();
	
	public EndLakeFeature() 
	{
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator chunkGenerator_, Random random,
			BlockPos blockPos, NoFeatureConfig config) 
	{	
		double radius = ModMathHelper.randRange(10.0, 20.0, random);
		double depth = radius * 0.5 * ModMathHelper.randRange(0.8, 1.2, random);
		int dist = ModMathHelper.floor(radius);
		int dist2 = ModMathHelper.floor(radius * 1.5);
		int bott = ModMathHelper.floor(depth);
		
		blockPos = FeatureHelper.getPosOnSurfaceWG(world, blockPos);
		if (blockPos.getY() < 10) return false;
		
		int waterLevel = blockPos.getY();
		
		BlockPos pos = FeatureHelper.getPosOnSurfaceRaycast(world, blockPos.north(dist).above(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5) return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);
		
		pos = FeatureHelper.getPosOnSurfaceRaycast(world, blockPos.south(dist).above(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5) return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);
		
		pos = FeatureHelper.getPosOnSurfaceRaycast(world, blockPos.east(dist).above(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5) return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);
		
		pos = FeatureHelper.getPosOnSurfaceRaycast(world, blockPos.west(dist).above(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5) return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);
		BlockState state;
		
		int minX = blockPos.getX() - dist2;
		int maxX = blockPos.getX() + dist2;
		int minZ = blockPos.getZ() - dist2;
		int maxZ = blockPos.getZ() + dist2;
		int maskMinX = minX - 1;
		int maskMinZ = minZ - 1;
		
		boolean[][] mask = new boolean[maxX - minX + 3][maxZ - minZ + 3];
		for (int x = minX; x <= maxX; x++) 
		{
			POS.setX(x);
			int mx = x - maskMinX;
			for (int z = minZ; z <= maxZ; z++) 
			{
				POS.setZ(z);
				int mz = z - maskMinZ;
				if (!mask[mx][mz]) 
				{
					for (int y = waterLevel + 1; y <= waterLevel + 20; y++) 
					{
						POS.setY(y);
						FluidState fluid = world.getFluidState(POS);
						if (!fluid.isEmpty()) 
						{
							for (int i = -1; i < 2; i++) 
							{
								int px = mx + i;
								for (int j = -1; j < 2; j++) 
								{
									int pz = mz + j;
									mask[px][pz] = true;
								}
							}
							break;
						}
					}
				}
			}
		}
		
		for (int x = minX; x <= maxX; x++) 
		{
			POS.setX(x);
			int x2 = x - blockPos.getX();
			x2 *= x2;
			int mx = x - maskMinX;
			for (int z = minZ; z <= maxZ; z++) 
			{
				POS.setZ(z);
				int z2 = z - blockPos.getZ();
				z2 *= z2;
				int mz = z - maskMinZ;
				if (!mask[mx][mz]) 
				{
					double size = 1;
					for (int y = blockPos.getY(); y <= blockPos.getY() + 20; y++) 
					{
						POS.setY(y);
						double add = y - blockPos.getY();
						if (add > 5) 
						{
							size *= 0.8;
							add = 5;
						}
						double r = (add * 1.8 + radius * (NOISE.eval(x * 0.2, y * 0.2, z * 0.2) * 0.25 + 0.75)) - 1.0 / size;
						if (r > 0)
						{
							r *= r;
							if (x2 + z2 <= r) 
							{
								state = world.getBlockState(POS);
								if (state.is(ModTags.GEN_TERRAIN)) 
								{
									BlockHelper.setWithoutUpdate(world, POS, Blocks.AIR.defaultBlockState());
								}
								pos = POS.below();
								if (world.getBlockState(pos).is(ModTags.GEN_TERRAIN)) 
								{
									state = world.getBiome(pos).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
									if (y > waterLevel + 1)
										BlockHelper.setWithoutUpdate(world, pos, state);
									else if (y > waterLevel)
										BlockHelper.setWithoutUpdate(world, pos, random.nextBoolean() ? state : ModBlocks.ENDSTONE_DUST.get().defaultBlockState());
									else
										BlockHelper.setWithoutUpdate(world, pos, ModBlocks.ENDSTONE_DUST.get().defaultBlockState());
								}
							}
						}
						else 
						{
							break;
						}
					}
				}
			}
		}
		
		double aspect = ((double) radius / (double) depth);

		for (int x = blockPos.getX() - dist; x <= blockPos.getX() + dist; x++) 
		{
			POS.setX(x);
			int x2 = x - blockPos.getX();
			x2 *= x2;
			int mx = x - maskMinX;
			for (int z = blockPos.getZ() - dist; z <= blockPos.getZ() + dist; z++) 
			{
				POS.setZ(z);
				int z2 = z - blockPos.getZ();
				z2 *= z2;
				int mz = z - maskMinZ;
				if (!mask[mx][mz]) 
				{
					for (int y = blockPos.getY() - bott; y < blockPos.getY(); y++) 
					{
						POS.setY(y);
						double y2 = (double) (y - blockPos.getY()) * aspect;
						y2 *= y2;
						double r = radius * (NOISE.eval(x * 0.2, y * 0.2, z * 0.2) * 0.25 + 0.75);
						double rb = r * 1.2;
						r *= r;
						rb *= rb;
						if (y2 + x2 + z2 <= r) 
						{
							state = world.getBlockState(POS);
							if (canReplace(state)) 
							{
								state = world.getBlockState(POS.above());
								state = canReplace(state) ? (y < waterLevel ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState()) : state;
								BlockHelper.setWithoutUpdate(world, POS, state);
							}
							pos = POS.below();
							if (world.getBlockState(pos).getBlock().is(ModTags.GEN_TERRAIN)) 
							{
								BlockHelper.setWithoutUpdate(world, pos, ModBlocks.ENDSTONE_DUST.get().defaultBlockState());
							}
							pos = POS.above();
							while (canReplace(state = world.getBlockState(pos)) && !state.isAir() && state.getFluidState().isEmpty()) 
							{
								BlockHelper.setWithoutUpdate(world, pos, pos.getY() < waterLevel ? Blocks.WATER : Blocks.AIR);
								pos = pos.above();
							}
						}
						// Make border
						else if (y < waterLevel && y2 + x2 + z2 <= rb) 
						{
							if (world.isEmptyBlock(POS.above())) 
							{
								state = world.getBiome(POS).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
								BlockHelper.setWithoutUpdate(world, POS, random.nextBoolean() ? state : ModBlocks.ENDSTONE_DUST.get().defaultBlockState());
								BlockHelper.setWithoutUpdate(world, POS.below(), END_STONE);
							}
							else 
							{
								BlockHelper.setWithoutUpdate(world, POS, ModBlocks.ENDSTONE_DUST.get().defaultBlockState());
								BlockHelper.setWithoutUpdate(world, POS.below(), END_STONE);
							}
						}
					}
				}
			}
		}
		
		BlockHelper.fixBlocks(world, new BlockPos(minX - 2, waterLevel - 2, minZ - 2), new BlockPos(maxX + 2, blockPos.getY() + 20, maxZ + 2));
		
		return true;
	}
	
	private boolean canReplace(BlockState state) 
	{
		return state.getMaterial().isReplaceable()
				|| state.is(ModTags.GEN_TERRAIN)
				|| state.is(Tags.Blocks.ORES) // Handles floating ores
				|| state.is(Tags.Blocks.END_STONES) // Handles other blocks that could be left floating
				|| state.is(ModBlocks.ENDSTONE_DUST.get())
				|| state.getMaterial().equals(Material.PLANT)
				|| state.getMaterial().equals(Material.WATER_PLANT)
		        || state.getMaterial().equals(Material.REPLACEABLE_WATER_PLANT);
	}
}
