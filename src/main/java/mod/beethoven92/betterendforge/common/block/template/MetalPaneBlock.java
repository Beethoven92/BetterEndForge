package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.util.Direction;

import net.minecraft.block.AbstractBlock.Properties;

public class MetalPaneBlock extends PaneBlock
{
	public MetalPaneBlock(Properties builder)
	{
		super(builder);
	}
	
	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
		if (side.getAxis().isVertical() && adjacentBlockState.getBlock().equals(this) && !adjacentBlockState.equals(state)) 
		{
			return false;
		}
		return super.skipRendering(state, adjacentBlockState, side);
	}
}
