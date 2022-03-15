package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HydraluxSaplingBlock extends UnderwaterPlantBlockWithAge
{
	public HydraluxSaplingBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void doGrow(WorldGenLevel world, Random random, BlockPos pos) 
	{
		int h = ModMathHelper.randRange(4, 8, random);
		MutableBlockPos mut = new MutableBlockPos().set(pos);
		
		for (int i = 1; i < h; i++) 
		{
			mut.setY(pos.getY() + i);
			if (!world.getBlockState(mut).is(Blocks.WATER)) 
			{
				return;
			}
		}
		
		mut.setY(pos.getY());
		BlockState state = ModBlocks.HYDRALUX.get().defaultBlockState();
		BlockHelper.setWithoutUpdate(world, pos, state.setValue(BlockProperties.HYDRALUX_SHAPE, HydraluxShape.ROOTS));
		for (int i = 1; i < h - 2; i++) 
		{
			mut.setY(pos.getY() + i);
			BlockHelper.setWithoutUpdate(world, mut, state.setValue(BlockProperties.HYDRALUX_SHAPE, HydraluxShape.VINE));
		}
		
		mut.setY(mut.getY() + 1);
		boolean big = random.nextBoolean();
		BlockHelper.setWithoutUpdate(world, mut, state.setValue(BlockProperties.HYDRALUX_SHAPE, big ? HydraluxShape.FLOWER_BIG_BOTTOM : HydraluxShape.FLOWER_SMALL_BOTTOM));
		
		mut.setY(mut.getY() + 1);
		BlockHelper.setWithoutUpdate(world, mut, state.setValue(BlockProperties.HYDRALUX_SHAPE, big ? HydraluxShape.FLOWER_BIG_TOP : HydraluxShape.FLOWER_SMALL_TOP));
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.is(ModBlocks.SULPHURIC_ROCK.stone.get());
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
