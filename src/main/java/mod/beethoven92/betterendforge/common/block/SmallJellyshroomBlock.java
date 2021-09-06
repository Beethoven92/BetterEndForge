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

public class SmallJellyshroomBlock extends AttachedBlock implements IGrowable
{
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	
	static 
	{
		BOUNDING_SHAPES.put(Direction.UP, Block.makeCuboidShape(3, 0, 3, 13, 16, 13));
		BOUNDING_SHAPES.put(Direction.DOWN, Block.makeCuboidShape(3, 0, 3, 13, 16, 13));
		BOUNDING_SHAPES.put(Direction.NORTH, VoxelShapes.create(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, VoxelShapes.create(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, VoxelShapes.create(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(Direction.EAST, VoxelShapes.create(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
	}
	
	public SmallJellyshroomBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return BOUNDING_SHAPES.get(state.get(FACING));
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		BlockState support = worldIn.getBlockState(blockPos);
		return hasEnoughSolidSide(worldIn, blockPos, direction) && support.isSolid() && support.getLightValue() == 0;
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return state.get(FACING) == Direction.UP && worldIn.getBlockState(pos.down()).isIn(ModTags.END_GROUND);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return state.get(FACING) == Direction.UP && worldIn.getBlockState(pos.down()).isIn(ModTags.END_GROUND);
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		if(rand.nextInt(16) == 0)
		{
			BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR);
			ModFeatures.JELLYSHROOM.generate(worldIn, null, rand, pos, null);
		}
	}

}
