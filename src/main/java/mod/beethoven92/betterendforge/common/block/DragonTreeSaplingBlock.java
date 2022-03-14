package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DragonTreeSaplingBlock extends EndSaplingBlock
{
	public DragonTreeSaplingBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		return worldIn.getBlockState(pos.below()).is(ModBlocks.SHADOW_GRASS.get());
	}
	
	@Override
	protected Feature<?> getFeature() 
	{
		return ModFeatures.DRAGON_TREE;
	}
}
