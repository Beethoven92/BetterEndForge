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

public class BulbVineSeedBlock extends PlantBlockWithAge
{
	public BulbVineSeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState up = worldIn.getBlockState(pos.up());
		return up.isIn(ModTags.GEN_TERRAIN) || up.isIn(BlockTags.LOGS) || up.isIn(BlockTags.LEAVES);
	}
	
	@Override
	public void growAdult(ISeedReader world, Random random, BlockPos pos) 
	{
		int h = BlockHelper.downRay(world, pos, random.nextInt(24)) - 1;
		if (h > 2) 
		{
			BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BULB_VINE.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
			for (int i = 1; i < h; i++)
			{
				BlockHelper.setWithoutUpdate(world, pos.down(i), ModBlocks.BULB_VINE.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
			}
			BlockHelper.setWithoutUpdate(world, pos.down(h), ModBlocks.BULB_VINE.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		}
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
