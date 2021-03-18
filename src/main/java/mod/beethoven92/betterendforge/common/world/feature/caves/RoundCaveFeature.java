package mod.beethoven92.betterendforge.common.world.feature.caves;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraftforge.common.Tags;

public class RoundCaveFeature extends EndCaveFeature
{
	@Override
	protected Set<BlockPos> generateCaveBlocks(ISeedReader world, BlockPos center, int radius, Random random) 
	{
		OpenSimplexNoise noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, center.getX(), center.getZ()));

		int x1 = center.getX() - radius - 5;
		int z1 = center.getZ() - radius - 5;
		int x2 = center.getX() + radius + 5;
		int z2 = center.getZ() + radius + 5;
		int y1 = ModMathHelper.floor(center.getY() - (radius + 5) / 1.6);
		int y2 = ModMathHelper.floor(center.getY() + (radius + 5) / 1.6);

		double hr = radius * 0.75;
		double nr = radius * 0.25;

		BlockState state;
		Mutable bpos = new Mutable();
		Set<BlockPos> blocks = Sets.newHashSet();
		for (int x = x1; x <= x2; x++) 
		{
			int xsq = x - center.getX();
			xsq *= xsq;
			bpos.setX(x);
			for (int z = z1; z <= z2; z++) 
			{
				int zsq = z - center.getZ();
				zsq *= zsq;
				bpos.setZ(z);
				for (int y = y1; y <= y2; y++)
				{
					int ysq = y - center.getY();
					ysq *= 1.6;
					ysq *= ysq;
					bpos.setY(y);
					double r = noise.eval(x * 0.1, y * 0.1, z * 0.1) * nr + hr;
					//double r2 = r + 5;
					double dist = xsq + ysq + zsq;
					if (dist < r * r)
					{
						state = world.getBlockState(bpos);
						if (isReplaceable(state)) 
						{
							BlockHelper.setWithoutUpdate(world, bpos, CAVE_AIR);
							blocks.add(bpos.toImmutable());

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
					}
				}
			}
		}

		return blocks;
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
