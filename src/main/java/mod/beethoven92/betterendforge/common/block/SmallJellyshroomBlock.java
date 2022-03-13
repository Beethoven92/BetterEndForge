package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock.Properties;

public class SmallJellyshroomBlock extends AttachedBlock implements IGrowable
{
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	
	static 
	{
		BOUNDING_SHAPES.put(Direction.UP, Block.box(3, 0, 3, 13, 16, 13));
		BOUNDING_SHAPES.put(Direction.DOWN, Block.box(3, 0, 3, 13, 16, 13));
		BOUNDING_SHAPES.put(Direction.NORTH, VoxelShapes.box(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, VoxelShapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, VoxelShapes.box(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.EAST, VoxelShapes.box(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
	}
	
	public SmallJellyshroomBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		Direction direction = state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		BlockState support = worldIn.getBlockState(blockPos);
		return canSupportCenter(worldIn, blockPos, direction) && support.canOcclude() && support.getLightEmission() == 0;
	}
	
	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return state.getValue(FACING) == Direction.UP && worldIn.getBlockState(pos.below()).is(ModTags.END_GROUND);
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return state.getValue(FACING) == Direction.UP && worldIn.getBlockState(pos.below()).is(ModTags.END_GROUND);
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		if(rand.nextInt(16) == 0)
		{
			BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR);
			ModFeatures.JELLYSHROOM.place(worldIn, null, rand, pos, null);
		}
	}

}
