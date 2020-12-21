package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

public class ShadowBerryBlock extends PlantBlockWithAge
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 8, 15);
	
	public ShadowBerryBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public void growAdult(ISeedReader world, Random random, BlockPos pos) 
	{		
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return state.get(AGE) < 3;
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return state.get(AGE) < 3;
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.isIn(ModBlocks.SHADOW_GRASS.get());
	}
}
