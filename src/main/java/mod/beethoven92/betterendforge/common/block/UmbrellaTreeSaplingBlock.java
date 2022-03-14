package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		return worldIn.getBlockState(pos.below()).is(ModBlocks.JUNGLE_MOSS.get());
	}
}
