package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class SinglePlantFeature extends ScatterFeature
{
	private final Block plant;
	private final boolean rawHeightmap;
	private final int chance;
	
	public SinglePlantFeature(Block plant, int radius) 
	{
		this(plant, radius, true, 1);
	}
	
	public SinglePlantFeature(Block plant, int radius, int chance) 
	{
		this(plant, radius, true, chance);
	}
	
	public SinglePlantFeature(Block plant, int radius, boolean rawHeightmap) 
	{
		this(plant, radius, rawHeightmap, 1);
	}
	
	public SinglePlantFeature(Block plant, int radius, boolean rawHeightmap, int chance) 
	{
		super(radius);
		this.plant = plant;
		this.rawHeightmap = rawHeightmap;
		this.chance = chance;
	}
	
	public int getChance() 
	{
		return chance;
	}
	
	@Override
	protected BlockPos getCenterGround(ISeedReader world, BlockPos pos) 
	{
		return rawHeightmap ? FeatureHelper.getPosOnSurfaceWG(world, pos) : FeatureHelper.getPosOnSurface(world, pos);
	}
	
	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return plant.isValidPosition(plant.getDefaultState(), world, blockPos);
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
		else if (plant instanceof EndCropBlock) 
		{
			BlockState state = plant.getDefaultState().with(EndCropBlock.AGE, 3);
			BlockHelper.setWithoutUpdate(world, blockPos, state);
		}
		else if (plant instanceof PlantBlockWithAge) 
		{
			int age = random.nextInt(4);
			BlockState state = plant.getDefaultState().with(PlantBlockWithAge.AGE, age);
			BlockHelper.setWithoutUpdate(world, blockPos, state);
		}
		else 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, plant);
		}
	}

}
