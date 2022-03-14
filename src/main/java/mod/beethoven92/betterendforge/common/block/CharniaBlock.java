package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CharniaBlock extends UnderwaterPlantBlock
{
	public CharniaBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		return canSupportCenter(worldIn, pos.below(), Direction.UP) 
				&& worldIn.getFluidState(pos).getType() == Fluids.WATER;
	}
}
