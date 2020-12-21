package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class CavePlantFeature extends FullHeightScatterFeature
{
	private final Block plant;
	
	public CavePlantFeature(Block plant, int radius)
	{
		super(radius);
		this.plant = plant;
	}
	
	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return plant.isValidPosition(world.getBlockState(blockPos), world, blockPos);
	}
	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
	{	
		BlockHelper.setWithoutUpdate(world, blockPos, plant);
	}
}
