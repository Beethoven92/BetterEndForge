package mod.beethoven92.betterendforge.common.world.feature;
/*
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModStructures;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFHexPrism;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

@Deprecated
public class RoundCaveFeature extends Feature<NoFeatureConfig>
{
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	
	public RoundCaveFeature() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}
	
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos pos, NoFeatureConfig config) 
	{
		if (!(GeneratorOptions.useNewGenerator() && GeneratorOptions.noRingVoid()))
		{
			if (pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 22500)
			{
				return false;
			}
		}
		
		int radius = ModMathHelper.randRange(10, 30, random);
		
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
		
		pos = new BlockPos(pos.getX(), ModMathHelper.randRange(bottom, top, random), pos.getZ());
		
		OpenSimplexNoise noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, pos.getX(), pos.getZ()));
		
		int x1 = pos.getX() - radius - 5;
		int z1 = pos.getZ() - radius - 5;
		int x2 = pos.getX() + radius + 5;
		int z2 = pos.getZ() + radius + 5;
		int y1 = ModMathHelper.floor(pos.getY() - (radius + 5) / 1.6);
		int y2 = ModMathHelper.floor(pos.getY() + (radius + 5) / 1.6);
		
		double hr = radius * 0.75;
		double nr = radius * 0.25;
		
		Set<BlockPos> bushes = Sets.newHashSet();
		BlockState terrain = ModBlocks.CAVE_MOSS.get().getDefaultState();
		for (int x = x1; x <= x2; x++) 
		{
			int xsq = x - pos.getX();
			xsq *= xsq;
			bpos.setX(x);
			for (int z = z1; z <= z2; z++) 
			{
				int zsq = z - pos.getZ();
				zsq *= zsq;
				bpos.setZ(z);
				for (int y = y1; y <= y2; y++)
				{
					int ysq = y - pos.getY();
					ysq *= 1.6;
					ysq *= ysq;
					bpos.setY(y);
					double r = noise.eval(x * 0.1, y * 0.1, z * 0.1) * nr + hr;
					double r2 = r + 5;
					double dist = xsq + ysq + zsq;
					if (dist < r * r) 
					{
						state = world.getBlockState(bpos);
						if (isReplaceable(state)) 
						{
							BlockHelper.setWithoutUpdate(world, bpos, CAVE_AIR);
							
							while (state.getMaterial().equals(Material.LEAVES)) 
							{
								BlockHelper.setWithoutUpdate(world, bpos, CAVE_AIR);
								bpos.setY(bpos.getY() + 1);
								state = world.getBlockState(bpos);
							}
							
							bpos.setY(y - 1);
							while (state.getMaterial().equals(Material.LEAVES)) 
							{
								BlockHelper.setWithoutUpdate(world, bpos, CAVE_AIR);
								bpos.setY(bpos.getY() - 1);
								state = world.getBlockState(bpos);
							}
						}
						bpos.setY(y - 1);
						if (world.getBlockState(bpos).isIn(ModTags.GEN_TERRAIN)) 
						{
							BlockHelper.setWithoutUpdate(world, bpos, terrain);
						}
					}
					else if (dist < r2 * r2) 
					{
						state = world.getBlockState(bpos);
						if (!state.getFluidState().isEmpty()) 
						{
							BlockHelper.setWithoutUpdate(world, bpos, Blocks.END_STONE.getDefaultState());
						}
						else if (world.getBlockState(bpos).isIn(ModTags.GEN_TERRAIN)) 
						{
							if (world.isAirBlock(bpos.down())) 
							{
								int h = BlockHelper.downRay(world, bpos.down(), 64);
								if (h > 6 && h < 32 && world.getBlockState(bpos.down(h + 3)).isIn(ModTags.GEN_TERRAIN)) 
								{
									bushes.add(bpos.down());
								}
							}
							else if (world.isAirBlock(bpos.up())) 
							{
								int h = BlockHelper.upRay(world, bpos.up(), 64);
								if (h > 6 && h < 32 && world.getBlockState(bpos.up(h + 3)).isIn(ModTags.GEN_TERRAIN))
								{
									bushes.add(bpos.up());
								}
						    }
						}
					}
				}
			}
		}
		
		bushes.forEach((cpos) -> {
			if (random.nextInt(32) == 0) 
			{
				generateBush(world, random, cpos);
			}
		});
		
		if (random.nextBoolean() && world.getBiome(pos).getGenerationSettings().hasStructure(ModStructures.MOUNTAIN)) 
		{
			pos = pos.add(random.nextGaussian() * 5, random.nextGaussian() * 5, random.nextGaussian() * 5);
			BlockPos down = pos.down(BlockHelper.downRay(world, pos, 64) + 2);
			if (isReplaceable(world.getBlockState(down))) 
			{
				SDF prism = new SDFHexPrism().setHeight(radius * ModMathHelper.randRange(0.6F, 0.75F, random)).setRadius(ModMathHelper.randRange(1.7F, 3F, random)).setBlock(ModBlocks.AURORA_CRYSTAL.get());
				Vector3f vec = ModMathHelper.randomHorizontal(random);
				prism = new SDFRotation().setRotation(vec, random.nextFloat()).setSource(prism);
				
				prism.setReplaceFunction((bState) -> {
					return bState.getMaterial().isReplaceable()
							|| bState.isIn(ModTags.GEN_TERRAIN)
							|| bState.getMaterial().equals(Material.PLANTS)
							|| bState.getMaterial().equals(Material.LEAVES);
				});
				prism.fillRecursive(world, pos);
				BlockHelper.setWithoutUpdate(world, pos, ModBlocks.AURORA_CRYSTAL.get());
			}
		}
		
		BlockHelper.fixBlocks(world, new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
		
		return true;
	}
	
	private boolean isReplaceable(BlockState state) 
	{
		return state.isIn(ModTags.GEN_TERRAIN)
				|| state.isIn(Tags.Blocks.ORES) // Handles floating ores
				|| state.isIn(Tags.Blocks.END_STONES) // Handles other blocks that could be left floating
				|| state.getMaterial().isReplaceable()
				|| state.getMaterial().equals(Material.PLANTS)
				|| state.getMaterial().equals(Material.LEAVES);
	}
	
	private void generateBush(ISeedReader world, Random random, BlockPos blockPos) 
	{
		float radius = MathHelper.nextFloat(random, 1.0F, 3.2F);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextInt());
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(ModBlocks.CAVE_BUSH.get());
		sphere = new SDFScale3D().setScale(ModMathHelper.randRange(0.8F, 1.2F, random), ModMathHelper.randRange(0.8F, 1.2F, random), ModMathHelper.randRange(0.8F, 1.2F, random)).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return random.nextFloat() * 3F - 1.5F; }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		sphere.fillRecursive(world, blockPos);
		world.setBlockState(blockPos, ModBlocks.CAVE_BUSH.get().getDefaultState(), 1 | 2 | 16);
	}
}

 */
