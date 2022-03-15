package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class UmbrellaMossTallBlock extends DoublePlantBlock
{
	public UmbrellaMossTallBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.getBlock() == ModBlocks.END_MOSS.get() || state.getBlock() == ModBlocks.END_MYCELIUM.get();
	}
}
