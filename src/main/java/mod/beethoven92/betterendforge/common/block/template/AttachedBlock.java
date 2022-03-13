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

import net.minecraft.block.AbstractBlock.Properties;

public class AttachedBlock extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public AttachedBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockState blockState = this.defaultBlockState();
		IWorldReader worldView = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		Direction[] directions = context.getNearestLookingDirections();
		for (int i = 0; i < directions.length; ++i) 
		{
			Direction direction = directions[i];
			Direction direction2 = direction.getOpposite();
			blockState = (BlockState) blockState.setValue(FACING, direction2);
			if (blockState.canSurvive(worldView, blockPos)) 
			{
				return blockState;
			}
		}
		return null;
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.defaultBlockState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		Direction direction = (Direction) state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		return canSupportCenter(worldIn, blockPos, direction) || worldIn.getBlockState(blockPos).is(BlockTags.LEAVES);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}
