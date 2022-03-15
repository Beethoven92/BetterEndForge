package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.core.Direction;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MetalPaneBlock extends IronBarsBlock
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
