package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class PathBlock extends Block
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 15, 16);
	
	public PathBlock(Block block)
	{
		super(AbstractBlock.Properties.from(block).setAllowsSpawn((state, world, pos, type) -> { return false; }));
		if (block instanceof TerrainBlock)
		{
			TerrainBlock terrain = (TerrainBlock)block;
			terrain.setPathBlock(this);
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) 
	{
		return SHAPE;
	}
}
