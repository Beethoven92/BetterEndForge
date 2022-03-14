package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class BlueVineFeature extends ScatterFeature
{
	private boolean small;
	
	public BlueVineFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		float d = ModMathHelper.length(center.getX() - blockPos.getX(), center.getZ() - blockPos.getZ()) / radius * 0.6F + random.nextFloat() * 0.4F;
		small = d > 0.5F;
		return ModBlocks.BLUE_VINE_SEED.get().canSurvive(Blocks.AIR.defaultBlockState(), world, blockPos);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) 
	{
		if (small) 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.BLUE_VINE_SEED.get().defaultBlockState().setValue(PlantBlockWithAge.AGE, random.nextInt(4)));
		}
		else 
		{
			PlantBlockWithAge seed = ((PlantBlockWithAge) ModBlocks.BLUE_VINE_SEED.get());
			seed.growAdult(world, random, blockPos);
		}
	}

}
