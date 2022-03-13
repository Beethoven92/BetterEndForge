package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;

import net.minecraft.block.AbstractBlock.Properties;

public class MossyGlowshroomCapBlock extends Block
{
	public static final BooleanProperty TRANSITION = BooleanProperty.create("transition");
	
	public MossyGlowshroomCapBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(TRANSITION, false));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		return this.defaultBlockState().setValue(TRANSITION, 
				ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(context.getLevel().getBlockState(context.getClickedPos().below())));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(TRANSITION);
	}
}
