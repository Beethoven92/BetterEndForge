package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.EndLotusLeafBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class EndLotusLeafFeature extends ScatterFeature
{
	public EndLotusLeafFeature(int radius) 
	{
		super(radius);
	}

	private boolean canGenerate(WorldGenLevel world, BlockPos blockPos) 
	{
		MutableBlockPos p = new MutableBlockPos();
		p.setY(blockPos.getY());
		int count = 0;
		for (int x = -1; x < 2; x ++) 
		{
			p.setX(blockPos.getX() + x);
			for (int z = -1; z < 2; z ++) 
			{
				p.setZ(blockPos.getZ() + z);
				if (world.isEmptyBlock(p) && world.getBlockState(p.below()).is(Blocks.WATER))
					count ++;
			}
		}
		return count == 9;
	}

	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.isEmptyBlock(blockPos) && world.getBlockState(blockPos.below()).is(Blocks.WATER);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos)
	{
		if (canGenerate(world, blockPos)) 
		{
			generateLeaf(world, blockPos);
		}
	}
	
	private void generateLeaf(WorldGenLevel world, BlockPos pos) 
	{
		MutableBlockPos p = new MutableBlockPos();
		BlockState leaf = ModBlocks.END_LOTUS_LEAF.get().defaultBlockState();
		BlockHelper.setWithoutUpdate(world, pos, leaf.setValue(EndLotusLeafBlock.SHAPE, TripleShape.BOTTOM));

		for (Direction move: BlockHelper.HORIZONTAL_DIRECTIONS) 
		{
			BlockHelper.setWithoutUpdate(world, p.set(pos).move(move), leaf.setValue(EndLotusLeafBlock.HORIZONTAL_FACING, move).setValue(EndLotusLeafBlock.SHAPE, TripleShape.MIDDLE));
		}

		for (int i = 0; i < 4; i ++) 
		{
			Direction d1 = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			Direction d2 = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			BlockHelper.setWithoutUpdate(world, p.set(pos).move(d1).move(d2), leaf.setValue(EndLotusLeafBlock.HORIZONTAL_FACING, d1).setValue(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		}
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.NORTH).move(Direction.EAST), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.NORTH).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.EAST).move(Direction.SOUTH), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.EAST).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.SOUTH).move(Direction.WEST), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.SOUTH).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.WEST).move(Direction.NORTH), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.WEST).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
	}
	
	@Override
	protected int getChance() 
	{
		return 15;
	}
	
	@Override
	protected BlockPos getCenterGround(WorldGenLevel world, BlockPos pos) 
	{
		return world.getHeightmapPos(Types.WORLD_SURFACE, pos);
	}
}
