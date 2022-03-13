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

import net.minecraft.block.AbstractBlock.Properties;

public class HydraluxBlock extends UnderwaterPlantBlock
{
	public static final EnumProperty<HydraluxShape> SHAPE = BlockProperties.HYDRALUX_SHAPE;
	
	public HydraluxBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.below());
		HydraluxShape shape = state.getValue(SHAPE);
		if (shape == HydraluxShape.FLOWER_BIG_TOP || shape == HydraluxShape.FLOWER_SMALL_TOP) 
		{
			return down.is(this);
		}
		else if (shape == HydraluxShape.ROOTS) 
		{
			return down.is(ModBlocks.SULPHURIC_ROCK.stone.get()) && worldIn.getBlockState(pos.above()).is(this);
		}
		else 
		{
			return down.is(this) && worldIn.getBlockState(pos.above()).is(this);
		}
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
