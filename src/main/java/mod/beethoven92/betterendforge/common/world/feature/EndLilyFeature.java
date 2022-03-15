package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.EndLilySeedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class EndLilyFeature extends UnderwaterPlantScatter
{
	public EndLilyFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) 
	{	
		EndLilySeedBlock seed = (EndLilySeedBlock) ModBlocks.END_LILY_SEED.get();
		seed.doGrow(world, random, blockPos);
	}
	@Override
	protected int getChance()
	{
		return 15;
	}
}
