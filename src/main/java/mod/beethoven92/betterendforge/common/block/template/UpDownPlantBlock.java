package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class UpDownPlantBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 16, 12);
	
	public UpDownPlantBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.below());
		BlockState up = worldIn.getBlockState(pos.above());
		return (isTerrain(down) || down.getBlock() == this) && (isSupport(up, worldIn, pos) || up.getBlock() == this);
	}
	
	protected boolean isTerrain(BlockState state) 
	{
		return state.is(ModTags.END_GROUND);
	}
	
	protected boolean isSupport(BlockState state, IWorldReader world, BlockPos pos) 
	{
		return canSupportCenter(world, pos.above(), Direction.UP);
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.defaultBlockState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) 
	{
		super.playerWillDestroy(worldIn, pos, state, player);
		worldIn.neighborChanged(pos, Blocks.AIR, pos.below());
	}
}
