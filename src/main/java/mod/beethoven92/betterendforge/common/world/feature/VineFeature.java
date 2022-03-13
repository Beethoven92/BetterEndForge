package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class VineFeature extends InvertedScatterFeature
{
	private final Block vineBlock;
	private final int maxLength;
	
	public VineFeature(Block vineBlock, int maxLength) 
	{
		super(6);
		
		this.vineBlock = vineBlock;
		this.maxLength = maxLength;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.isEmptyBlock(blockPos) && vineBlock.canSurvive(Blocks.AIR.defaultBlockState(), world, blockPos)
				&& !world.getBlockState(blockPos.above()).is(vineBlock); // Attempt to fix vines generating below other vines
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
	{
		int h = BlockHelper.downRay(world, blockPos, random.nextInt(maxLength)) - 1;
		if (h > 2) 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, vineBlock.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
			for (int i = 1; i < h; i++) 
			{
				BlockHelper.setWithoutUpdate(world, blockPos.below(i), vineBlock.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
			}
			BlockHelper.setWithoutUpdate(world, blockPos.below(h), vineBlock.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		}
	}
}
