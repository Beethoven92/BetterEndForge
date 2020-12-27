package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class HydraluxBlock extends UnderwaterPlantBlock
{
	public static final EnumProperty<HydraluxShape> SHAPE = BlockProperties.HYDRALUX_SHAPE;
	
	public HydraluxBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.down());
		HydraluxShape shape = state.get(SHAPE);
		if (shape == HydraluxShape.FLOWER_BIG_TOP || shape == HydraluxShape.FLOWER_SMALL_TOP) 
		{
			return down.isIn(this);
		}
		else if (shape == HydraluxShape.ROOTS) 
		{
			return down.isIn(ModBlocks.SULPHURIC_ROCK.stone.get()) && worldIn.getBlockState(pos.up()).isIn(this);
		}
		else 
		{
			return down.isIn(this) && worldIn.getBlockState(pos.up()).isIn(this);
		}
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
