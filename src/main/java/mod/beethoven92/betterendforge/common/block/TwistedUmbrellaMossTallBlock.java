package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;

public class TwistedUmbrellaMossTallBlock extends DoublePlantBlock
{
	public TwistedUmbrellaMossTallBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.isIn(ModBlocks.END_MOSS.get()) || state.isIn(ModBlocks.END_MYCELIUM.get()) || 
				state.isIn(ModBlocks.JUNGLE_MOSS.get());
	}
}
