package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class UnderwaterPlantFeature extends UnderwaterPlantScatter
{
	private final Block plant;
	
	public UnderwaterPlantFeature(Block plant, int radius) 
	{
		super(radius);
		this.plant = plant;
	}

	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return super.canSpawn(world, blockPos) && plant.canSurvive(plant.defaultBlockState(), world, blockPos);
	}
	
	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) 
	{
		if (plant instanceof DoublePlantBlock) 
		{
			int rot = random.nextInt(4);
			BlockState state = plant.defaultBlockState().setValue(DoublePlantBlock.ROTATION, rot);
			BlockHelper.setWithoutUpdate(world, blockPos, state);
			BlockHelper.setWithoutUpdate(world, blockPos.above(), state.setValue(DoublePlantBlock.TOP, true));
		}
		else 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, plant.defaultBlockState());
		}
	}

}
