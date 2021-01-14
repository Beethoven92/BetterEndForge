package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.Feature;

public class UmbrellaTreeSaplingBlock extends EndSaplingBlock
{
	public UmbrellaTreeSaplingBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	protected Feature<?> getFeature() 
	{
		return ModFeatures.UMBRELLA_TREE;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return worldIn.getBlockState(pos.down()).isIn(ModBlocks.JUNGLE_MOSS.get());
	}
}
