package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class UpDownPlantBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 16, 12);
	
	public UpDownPlantBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.below());
		BlockState up = worldIn.getBlockState(pos.above());
		return (isTerrain(down) || down.getBlock() == this) && (isSupport(up, worldIn, pos) || up.getBlock() == this);
	}
	
	protected boolean isTerrain(BlockState state) 
	{
		return state.is(ModTags.END_GROUND);
	}
	
	protected boolean isSupport(BlockState state, LevelReader world, BlockPos pos) 
	{
		return canSupportCenter(world, pos.above(), Direction.UP);
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
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
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) 
	{
		super.playerWillDestroy(worldIn, pos, state, player);
		worldIn.neighborChanged(pos, Blocks.AIR, pos.below());
	}
}
