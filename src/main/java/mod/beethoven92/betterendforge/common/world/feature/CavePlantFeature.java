package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class CavePlantFeature extends FullHeightScatterFeature
{
	private final Block plant;
	
	public CavePlantFeature(Block plant, int radius)
	{
		super(radius);
		this.plant = plant;
	}
	
	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return plant.canSurvive(world.getBlockState(blockPos), world, blockPos);
	}
	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) 
	{	
		BlockHelper.setWithoutUpdate(world, blockPos, plant);
	}
}
