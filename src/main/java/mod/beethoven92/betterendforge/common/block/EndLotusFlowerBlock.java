package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class EndLotusFlowerBlock extends Block
{
	private static final VoxelShape SHAPE_OUTLINE = Block.makeCuboidShape(2, 0, 2, 14, 14, 14);
	private static final VoxelShape SHAPE_COLLISION = Block.makeCuboidShape(0, 0, 0, 16, 2, 16);
	
	public EndLotusFlowerBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE_OUTLINE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) 
	{
		return SHAPE_COLLISION;
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.down());
		return down.isIn(ModBlocks.END_LOTUS_STEM.get());
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else 
		{
			return stateIn;
		}
	}
}
