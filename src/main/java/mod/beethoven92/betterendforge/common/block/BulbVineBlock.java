package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BulbVineBlock extends EndVineBlock
{
	public BulbVineBlock(Properties properties) 
	{
		super(properties, 15, true);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		boolean canPlace = super.isValidPosition(state, worldIn, pos);
		return (state.isIn(this) && state.get(SHAPE) == TripleShape.BOTTOM) ? canPlace : canPlace && worldIn.getBlockState(pos.down()).isIn(this);
	}
}
