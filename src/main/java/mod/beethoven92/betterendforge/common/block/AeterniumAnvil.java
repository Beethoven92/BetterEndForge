package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;

import net.minecraft.block.AbstractBlock.Properties;

public class AeterniumAnvil extends EndAnvilBlock
{
	private static final IntegerProperty DESTRUCTION = BlockProperties.DESTRUCTION_LONG;
	
	public AeterniumAnvil(Properties properties) 
	{
		super(properties, ModItemTier.AETERNIUM.getLevel());
	}
	
	@Override
	public IntegerProperty getDestructionProperty()
	{
		return DESTRUCTION;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(DESTRUCTION);
		builder.add(FACING);
	}
}
