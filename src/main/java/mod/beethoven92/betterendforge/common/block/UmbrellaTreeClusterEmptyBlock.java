package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class UmbrellaTreeClusterEmptyBlock extends Block
{
	public static final BooleanProperty NATURAL = BlockProperties.NATURAL;
	
	public UmbrellaTreeClusterEmptyBlock(Properties properties) 
	{
		super(properties);
		setDefaultState(stateContainer.getBaseState().with(NATURAL, false));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(NATURAL);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
	{
		if (state.get(NATURAL) && random.nextInt(16) == 0) 
		{
			BlockHelper.setWithUpdate(worldIn, pos, ModBlocks.UMBRELLA_TREE_CLUSTER.get().getDefaultState().with(UmbrellaTreeClusterBlock.NATURAL, true));
		}
	}
}
