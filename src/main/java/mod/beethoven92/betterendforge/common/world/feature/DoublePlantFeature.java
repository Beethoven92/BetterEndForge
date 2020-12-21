package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class DoublePlantFeature extends ScatterFeature
{
	private final Block smallPlant;
	private final Block largePlant;
	private Block plant;
	
	public DoublePlantFeature(Block smallPlant, Block largePlant, int radius) 
	{
		super(radius);
		this.smallPlant = smallPlant;
		this.largePlant = largePlant;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		float d = ModMathHelper.length(center.getX() - blockPos.getX(), center.getZ() - blockPos.getZ()) / radius * 0.6F + random.nextFloat() * 0.4F;
		plant = d < 0.5F ? largePlant : smallPlant;
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
		else 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, plant);
		}
	}
}
