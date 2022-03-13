package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class PlantBlockWithAge extends PlantBlock
{
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	
	public PlantBlockWithAge(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
	
	public abstract void growAdult(ISeedReader world, Random random, BlockPos pos);
	
	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		if (rand.nextInt(4) == 0) 
		{
			int age = state.getValue(AGE);
			if (age < 3) 
			{
				worldIn.setBlockAndUpdate(pos, state.setValue(AGE, age + 1));
			}
			else 
			{
				growAdult(worldIn, rand, pos);
			}
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		performBonemeal(worldIn, random, pos, state);
	}
}
