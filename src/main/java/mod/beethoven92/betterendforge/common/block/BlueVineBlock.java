package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UpDownPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;

public class BlueVineBlock extends UpDownPlantBlock
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	
	public BlueVineBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.getBlock() == ModBlocks.END_MOSS.get() || state.getBlock() == ModBlocks.END_MYCELIUM.get();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
