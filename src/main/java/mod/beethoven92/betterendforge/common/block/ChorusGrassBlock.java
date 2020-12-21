package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;

public class ChorusGrassBlock extends PlantBlock
{
	public ChorusGrassBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.getBlock() == ModBlocks.CHORUS_NYLIUM.get();
	}
}
