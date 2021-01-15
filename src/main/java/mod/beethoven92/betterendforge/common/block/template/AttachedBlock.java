package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class AttachedBlock extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public AttachedBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockState blockState = this.getDefaultState();
		IWorldReader worldView = context.getWorld();
		BlockPos blockPos = context.getPos();
		Direction[] directions = context.getNearestLookingDirections();
		for (int i = 0; i < directions.length; ++i) 
		{
			Direction direction = directions[i];
			Direction direction2 = direction.getOpposite();
			blockState = (BlockState) blockState.with(FACING, direction2);
			if (blockState.isValidPosition(worldView, blockPos)) 
			{
				return blockState;
			}
		}
		return null;
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
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		Direction direction = (Direction) state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return hasEnoughSolidSide(worldIn, blockPos, direction) || worldIn.getBlockState(blockPos).isIn(BlockTags.LEAVES);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
    }

	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
}
