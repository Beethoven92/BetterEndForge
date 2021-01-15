package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

public class EndVineBlock extends Block implements IGrowable, IForgeShearable
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final VoxelShape VOXEL_SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 16, 14);
	
	public EndVineBlock(Properties properties) 
	{
		super(properties);
		
		this.setDefaultState(this.getDefaultState().with(SHAPE, TripleShape.BOTTOM));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return VOXEL_SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.XZ;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return isValidSupport(state, worldIn, pos);
	}
	
	protected boolean isValidSupport(BlockState state, IWorldReader world, BlockPos pos) 
	{
		BlockState up = world.getBlockState(pos.up()); 
		return up.isIn(this) || up.isIn(BlockTags.LEAVES) || hasEnoughSolidSide(world, pos.up(), Direction.DOWN);
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else
		{
			if (worldIn.getBlockState(currentPos.down()).getBlock() != this)
				return stateIn.with(SHAPE, TripleShape.BOTTOM);
			else if (worldIn.getBlockState(currentPos.up()).getBlock() != this)
				return stateIn.with(SHAPE, TripleShape.TOP);
			return stateIn.with(SHAPE, TripleShape.MIDDLE);
		}
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		while (worldIn.getBlockState(pos).getBlock() == this) 
		{
			pos = pos.down();
		}
		return worldIn.getBlockState(pos).isAir();
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		while (worldIn.getBlockState(pos).getBlock() == this) 
		{
			pos = pos.down();
		}
		return worldIn.getBlockState(pos).isAir();
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		while (worldIn.getBlockState(pos).getBlock() == this) 
		{
			pos = pos.down();
		}
		worldIn.setBlockState(pos, getDefaultState());
		BlockHelper.setWithoutUpdate(worldIn, pos, getDefaultState());
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
