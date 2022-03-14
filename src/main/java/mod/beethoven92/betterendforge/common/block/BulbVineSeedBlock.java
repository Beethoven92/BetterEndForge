package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BulbVineSeedBlock extends PlantBlockWithAge
{
	public BulbVineSeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos)
	{
		BlockState up = worldIn.getBlockState(pos.above());
		return up.is(ModTags.GEN_TERRAIN) || up.is(BlockTags.LOGS) || up.is(BlockTags.LEAVES);
	}
	
	@Override
	public void growAdult(WorldGenLevel world, Random random, BlockPos pos) 
	{
		int h = BlockHelper.downRay(world, pos, random.nextInt(24)) - 1;
		if (h > 2) 
		{
			BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BULB_VINE.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
			for (int i = 1; i < h; i++)
			{
				BlockHelper.setWithoutUpdate(world, pos.below(i), ModBlocks.BULB_VINE.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
			}
			BlockHelper.setWithoutUpdate(world, pos.below(h), ModBlocks.BULB_VINE.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		}
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
