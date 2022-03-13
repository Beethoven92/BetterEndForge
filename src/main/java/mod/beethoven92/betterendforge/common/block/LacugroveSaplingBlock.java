package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.Feature;

import net.minecraft.block.AbstractBlock.Properties;

public class LacugroveSaplingBlock extends EndSaplingBlock
{	
	public LacugroveSaplingBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return worldIn.getBlockState(pos.below()).is(ModBlocks.END_MOSS.get()) 
				|| worldIn.getBlockState(pos.below()).is(ModBlocks.ENDSTONE_DUST.get());
	}

	@Override
	protected Feature<?> getFeature()
	{
		return ModFeatures.LACUGROVE;
	}
}
