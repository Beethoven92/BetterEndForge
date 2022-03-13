package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class CharniaBlock extends UnderwaterPlantBlock
{
	public CharniaBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return canSupportCenter(worldIn, pos.below(), Direction.UP) 
				&& worldIn.getFluidState(pos).getType() == Fluids.WATER;
	}
}
