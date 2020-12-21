package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.EndLotusLeafBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.Heightmap.Type;

public class EndLotusLeafFeature extends ScatterFeature
{
	public EndLotusLeafFeature(int radius) 
	{
		super(radius);
	}

	private boolean canGenerate(ISeedReader world, BlockPos blockPos) 
	{
		Mutable p = new Mutable();
		p.setY(blockPos.getY());
		int count = 0;
		for (int x = -1; x < 2; x ++) 
		{
			p.setX(blockPos.getX() + x);
			for (int z = -1; z < 2; z ++) 
			{
				p.setZ(blockPos.getZ() + z);
				if (world.isAirBlock(p) && world.getBlockState(p.down()).isIn(Blocks.WATER))
					count ++;
			}
		}
		return count == 9;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.isAirBlock(blockPos) && world.getBlockState(blockPos.down()).isIn(Blocks.WATER);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos)
	{
		if (canGenerate(world, blockPos)) 
		{
			generateLeaf(world, blockPos);
		}
	}
	
	private void generateLeaf(ISeedReader world, BlockPos pos) 
	{
		Mutable p = new Mutable();
		BlockState leaf = ModBlocks.END_LOTUS_LEAF.get().getDefaultState();
		BlockHelper.setWithoutUpdate(world, pos, leaf.with(EndLotusLeafBlock.SHAPE, TripleShape.BOTTOM));

		for (Direction move: BlockHelper.HORIZONTAL_DIRECTIONS) 
		{
			BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(move), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, move).with(EndLotusLeafBlock.SHAPE, TripleShape.MIDDLE));
		}

		for (int i = 0; i < 4; i ++) 
		{
			Direction d1 = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			Direction d2 = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(d1).move(d2), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, d1).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
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
	protected BlockPos getCenterGround(ISeedReader world, BlockPos pos) 
	{
		return world.getHeight(Type.WORLD_SURFACE, pos);
	}
}
