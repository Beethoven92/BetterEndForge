package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;

public class AeterniumAnvil extends EndAnvilBlock
{
	private static final IntegerProperty DESTRUCTION = BlockProperties.DESTRUCTION_LONG;
	
	public AeterniumAnvil(Properties properties) 
	{
		super(properties, ModItemTier.AETERNIUM.getHarvestLevel());
	}
	
	@Override
	public IntegerProperty getDestructionProperty()
	{
		return DESTRUCTION;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(DESTRUCTION);
		builder.add(FACING);
	}
}
