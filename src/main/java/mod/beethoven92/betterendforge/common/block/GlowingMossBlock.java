package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class GlowingMossBlock extends PlantBlock
{
	public GlowingMossBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	protected boolean isTerrain(BlockState state)
	{
		return state.getBlock() == ModBlocks.END_MOSS.get() || state.getBlock() == ModBlocks.END_MYCELIUM.get();
	}

	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) 
	{
		return 1F;
	}
}
