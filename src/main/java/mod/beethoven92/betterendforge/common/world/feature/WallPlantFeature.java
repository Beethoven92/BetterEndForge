package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.WallPlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class WallPlantFeature extends WallScatterFeature
{	
	private final Block block;
	
	public WallPlantFeature(Block block, int radius)
	{
		super(radius);
		this.block = block;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos pos, Direction dir) 
	{
		BlockState state = block.getDefaultState().with(WallPlantBlock.FACING, dir);
		return block.isValidPosition(state, world, pos);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos pos, Direction dir) 
	{
		BlockState state = block.getDefaultState().with(WallPlantBlock.FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos, state);
	}
}
