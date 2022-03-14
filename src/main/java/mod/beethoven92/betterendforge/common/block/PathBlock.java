package mod.beethoven92.betterendforge.common.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class PathBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 15, 16);
	
	public PathBlock(Block block)
	{
		super(BlockBehaviour.Properties.copy(block).isValidSpawn((state, world, pos, type) -> { return false; }));
		if (block instanceof TerrainBlock)
		{
			TerrainBlock terrain = (TerrainBlock)block;
			terrain.setPathBlock(this);
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
			CollisionContext context) 
	{
		return SHAPE;
	}
}
