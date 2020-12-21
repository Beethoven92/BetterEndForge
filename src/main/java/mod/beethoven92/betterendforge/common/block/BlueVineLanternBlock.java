package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlueVineLanternBlock extends Block
{
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
	
	public BlueVineLanternBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(NATURAL, false));
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return state.get(NATURAL) ? worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.BLUE_VINE.get() : true;
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else {
			return stateIn;
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(NATURAL);
	}
}
