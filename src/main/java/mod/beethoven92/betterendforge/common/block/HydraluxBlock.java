package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HydraluxBlock extends UnderwaterPlantBlock
{
	public static final EnumProperty<HydraluxShape> SHAPE = BlockProperties.HYDRALUX_SHAPE;
	
	public HydraluxBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
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
