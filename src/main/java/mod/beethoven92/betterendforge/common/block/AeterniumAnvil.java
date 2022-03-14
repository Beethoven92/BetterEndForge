package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
