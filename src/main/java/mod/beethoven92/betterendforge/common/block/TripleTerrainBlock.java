package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TripleTerrainBlock extends TerrainBlock
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	
	public TripleTerrainBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(SHAPE, TripleShape.BOTTOM));
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) 
	{
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) 
		{
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
		return InteractionResult.FAIL;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		Direction dir = context.getClickedFace();
		TripleShape shape = dir == Direction.UP ? TripleShape.BOTTOM : dir == Direction.DOWN ? TripleShape.TOP : TripleShape.MIDDLE;
		return this.defaultBlockState().setValue(SHAPE, shape);
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) 
	{
		TripleShape shape = state.getValue(SHAPE);
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
					world.setBlockAndUpdate(pos, Blocks.END_STONE.defaultBlockState());
				}
			}
			else 
			{
				boolean top = canSurvive(state, world, pos) || isMiddle(world.getBlockState(pos.above()));
				if (!top && !bottom)
				{
					world.setBlockAndUpdate(pos, Blocks.END_STONE.defaultBlockState());
				}
				else if (top && !bottom)
				{
					world.setBlockAndUpdate(pos, state.setValue(SHAPE, TripleShape.BOTTOM));
				}
				else if (!top && bottom) 
				{
					world.setBlockAndUpdate(pos, state.setValue(SHAPE, TripleShape.TOP));
				}
			}
		}
	}
	
	protected boolean canSurviveBottom(LevelAccessor world, BlockPos pos) 
	{
		BlockPos blockPos = pos.below();
		BlockState blockState = world.getBlockState(blockPos);
		if (isMiddle(blockState))
		{
			return true;
		}
		else if (blockState.getFluidState().getAmount() == 8)
		{
			return false;
		}
		else 
		{
			return !blockState.isFaceSturdy(world, blockPos, Direction.UP);
		}
	}
	
	protected boolean isMiddle(BlockState state) 
	{
		return state.is(this) && state.getValue(SHAPE) == TripleShape.MIDDLE;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
