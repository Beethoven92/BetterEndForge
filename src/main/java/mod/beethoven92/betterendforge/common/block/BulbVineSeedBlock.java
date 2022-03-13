package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class BulbVineSeedBlock extends PlantBlockWithAge
{
	public BulbVineSeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState up = worldIn.getBlockState(pos.above());
		return up.is(ModTags.GEN_TERRAIN) || up.is(BlockTags.LOGS) || up.is(BlockTags.LEAVES);
	}
	
	@Override
	public void growAdult(ISeedReader world, Random random, BlockPos pos) 
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
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
