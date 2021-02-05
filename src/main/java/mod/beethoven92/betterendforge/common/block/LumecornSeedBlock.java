package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

public class LumecornSeedBlock extends PlantBlockWithAge {
	
	public LumecornSeedBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void growAdult(ISeedReader world, Random random, BlockPos pos) {
		ModFeatures.LUMECORN.generate(world, null, random, pos, null);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.END_MOSS.get());
	}
	
	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
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
