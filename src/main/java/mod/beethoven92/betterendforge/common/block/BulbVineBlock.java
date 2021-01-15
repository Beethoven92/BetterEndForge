package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;

public class BulbVineBlock extends EndVineBlock
{
	public BulbVineBlock(Properties properties) 
	{
		super(properties);
	}
	
	// NEED FIX: bulb vines appear to not be able to generate when the feature is using the method below
	/*@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		boolean canPlace = super.isValidPosition(state, worldIn, pos);
		return (state.isIn(this) && state.get(SHAPE) == TripleShape.BOTTOM) ? canPlace : canPlace && worldIn.getBlockState(pos.down()).isIn(this);
	}*/
}
