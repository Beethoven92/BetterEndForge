package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyGlowshroomCapBlock extends Block
{
	public static final BooleanProperty TRANSITION = BooleanProperty.create("transition");
	
	public MossyGlowshroomCapBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(TRANSITION, false));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
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
