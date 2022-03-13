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

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class EndVineBlock extends Block implements IGrowable, IForgeShearable
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final VoxelShape VOXEL_SHAPE = Block.box(2, 0, 2, 14, 16, 14);
	
	public EndVineBlock(Properties properties) 
	{
		super(properties);
		
		this.registerDefaultState(this.defaultBlockState().setValue(SHAPE, TripleShape.BOTTOM));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return VOXEL_SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.XZ;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		return isValidSupport(state, worldIn, pos);
	}
	
	protected boolean isValidSupport(BlockState state, IWorldReader world, BlockPos pos) 
	{
		BlockState up = world.getBlockState(pos.above()); 
		return up.is(this) || up.is(BlockTags.LEAVES) || canSupportCenter(world, pos.above(), Direction.DOWN);
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
			if (worldIn.getBlockState(currentPos.below()).getBlock() != this)
				return stateIn.setValue(SHAPE, TripleShape.BOTTOM);
			else if (worldIn.getBlockState(currentPos.above()).getBlock() != this)
				return stateIn.setValue(SHAPE, TripleShape.TOP);
			return stateIn.setValue(SHAPE, TripleShape.MIDDLE);
		}
	}
	
	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		while (worldIn.getBlockState(pos).getBlock() == this) 
		{
			pos = pos.below();
		}
		return worldIn.getBlockState(pos).isAir();
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		while (worldIn.getBlockState(pos).getBlock() == this) 
		{
			pos = pos.below();
		}
		return worldIn.getBlockState(pos).isAir();
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		while (worldIn.getBlockState(pos).getBlock() == this) 
		{
			pos = pos.below();
		}
		worldIn.setBlockAndUpdate(pos, defaultBlockState());
		BlockHelper.setWithoutUpdate(worldIn, pos, defaultBlockState());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
}
