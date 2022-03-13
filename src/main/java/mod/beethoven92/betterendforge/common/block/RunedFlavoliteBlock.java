package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class RunedFlavoliteBlock extends Block
{
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	
	public RunedFlavoliteBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(ACTIVATED, false));
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) 
	{
		return state.getValue(ACTIVATED) ? 8 : 0;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(ACTIVATED);
	}
}
