package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.util.Direction;

public class MetalPaneBlock extends PaneBlock
{
	public MetalPaneBlock(Properties builder)
	{
		super(builder);
	}
	
	@Override
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
		if (side.getAxis().isVertical() && adjacentBlockState.getBlock().equals(this) && !adjacentBlockState.equals(state)) 
		{
			return false;
		}
		return super.isSideInvisible(state, adjacentBlockState, side);
	}
}
