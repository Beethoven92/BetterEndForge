package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;

public class MossyGlowshroomCapBlock extends Block
{
	public static final BooleanProperty TRANSITION = BooleanProperty.create("transition");
	
	public MossyGlowshroomCapBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(TRANSITION, false));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		return this.getDefaultState().with(TRANSITION, 
				ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(context.getWorld().getBlockState(context.getPos().down())));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(TRANSITION);
	}
}
