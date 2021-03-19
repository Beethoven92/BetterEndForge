package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TripleTerrainBlock extends TerrainBlock
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	
	public TripleTerrainBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(SHAPE, TripleShape.BOTTOM));
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		TripleShape shape = state.get(SHAPE);
		if (shape == TripleShape.BOTTOM) 
		{
			return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		return ActionResultType.FAIL;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		Direction dir = context.getFace();
		TripleShape shape = dir == Direction.UP ? TripleShape.BOTTOM : dir == Direction.DOWN ? TripleShape.TOP : TripleShape.MIDDLE;
		return this.getDefaultState().with(SHAPE, shape);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) 
	{
		TripleShape shape = state.get(SHAPE);
		if (shape == TripleShape.BOTTOM) 
		{
			super.randomTick(state, world, pos, random);
			return;
		}
		else if (random.nextInt(16) == 0) 
		{
			boolean bottom = canSurviveBottom(world, pos);
			if (shape == TripleShape.TOP) 
			{
				if (!bottom) 
				{
					world.setBlockState(pos, Blocks.END_STONE.getDefaultState());
				}
			}
			else 
			{
				boolean top = canSurvive(state, world, pos) || isMiddle(world.getBlockState(pos.up()));
				if (!top && !bottom)
				{
					world.setBlockState(pos, Blocks.END_STONE.getDefaultState());
				}
				else if (top && !bottom)
				{
					world.setBlockState(pos, state.with(SHAPE, TripleShape.BOTTOM));
				}
				else if (!top && bottom) 
				{
					world.setBlockState(pos, state.with(SHAPE, TripleShape.TOP));
				}
			}
		}
	}
	
	protected boolean canSurviveBottom(IWorld world, BlockPos pos) 
	{
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		if (isMiddle(blockState))
		{
			return true;
		}
		else if (blockState.getFluidState().getLevel() == 8)
		{
			return false;
		}
		else 
		{
			return !blockState.isSolidSide(world, blockPos, Direction.UP);
		}
	}
	
	protected boolean isMiddle(BlockState state) 
	{
		return state.isIn(this) && state.get(SHAPE) == TripleShape.MIDDLE;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
