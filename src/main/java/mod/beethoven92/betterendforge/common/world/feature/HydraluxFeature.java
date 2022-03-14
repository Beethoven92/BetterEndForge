package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.HydraluxSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class HydraluxFeature extends UnderwaterPlantScatter
{
	public HydraluxFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) 
	{
		HydraluxSaplingBlock seed = (HydraluxSaplingBlock) ModBlocks.HYDRALUX_SAPLING.get();
		seed.doGrow(world, random, blockPos);
	}

	@Override
	protected int getChance() 
	{
		return 15;
	}
}
