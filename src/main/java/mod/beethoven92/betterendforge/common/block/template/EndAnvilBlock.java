package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;

public class EndAnvilBlock extends AnvilBlock
{
	public static final IntegerProperty DESTRUCTION = BlockProperties.DESTRUCTION;
	
	public EndAnvilBlock(Properties properties) 
	{
		super(properties);
	}

	public int getCraftingLevel() 
	{
		return 1;
	}
	
	public IntegerProperty getDestructionProperty() 
	{
		return DESTRUCTION;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		super.fillStateContainer(builder);
		builder.add(DESTRUCTION);
	}
}
