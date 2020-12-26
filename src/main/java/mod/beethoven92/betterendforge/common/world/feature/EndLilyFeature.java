package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.EndLilySeedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class EndLilyFeature extends UnderwaterPlantScatter
{
	public EndLilyFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
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
