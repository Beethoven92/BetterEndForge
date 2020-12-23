package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class UnderwaterPlantFeature extends UnderwaterPlantScatter
{
	private final Block plant;
	
	public UnderwaterPlantFeature(Block plant, int radius) 
	{
		super(radius);
		this.plant = plant;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return super.canSpawn(world, blockPos) && plant.isValidPosition(plant.getDefaultState(), world, blockPos);
	}
	
	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
	{
		if (plant instanceof DoublePlantBlock) 
		{
			int rot = random.nextInt(4);
			BlockState state = plant.getDefaultState().with(DoublePlantBlock.ROTATION, rot);
			BlockHelper.setWithoutUpdate(world, blockPos, state);
			BlockHelper.setWithoutUpdate(world, blockPos.up(), state.with(DoublePlantBlock.TOP, true));
		}
		else 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, plant.getDefaultState());
		}
	}

}
