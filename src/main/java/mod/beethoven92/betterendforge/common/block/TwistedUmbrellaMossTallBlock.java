package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;

import net.minecraft.block.AbstractBlock.Properties;

public class TwistedUmbrellaMossTallBlock extends DoublePlantBlock
{
	public TwistedUmbrellaMossTallBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.is(ModBlocks.END_MOSS.get()) || state.is(ModBlocks.END_MYCELIUM.get()) || 
				state.is(ModBlocks.JUNGLE_MOSS.get());
	}
}
