package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
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

public class DoublePlantBlock extends Block implements IGrowable
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 2, 4, 12, 16, 12);
	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);
	public static final BooleanProperty TOP = BooleanProperty.create("top");
	
	public DoublePlantBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(TOP, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.XZ;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.down());
		BlockState up = worldIn.getBlockState(pos.up());
		return state.get(TOP) ? down.getBlock() == this : isTerrain(down) && (up.getMaterial().isReplaceable());
	}
	
	protected boolean isTerrain(BlockState state)
	{
		return state.isIn(ModTags.END_GROUND);
	}
	
	public boolean canStayAt(BlockState state, IWorldReader world, BlockPos pos) 
	{
		BlockState down = world.getBlockState(pos.down());
		BlockState up = world.getBlockState(pos.up());
		return state.get(TOP) ? down.getBlock() == this : isTerrain(down) && (up.getBlock() == this);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		int rot = worldIn.rand.nextInt(4);
		BlockState bs = this.getDefaultState().with(ROTATION, rot);
		BlockHelper.setWithoutUpdate(worldIn, pos, bs);
		BlockHelper.setWithoutUpdate(worldIn, pos.up(), bs.with(TOP, true));
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canStayAt(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else {
			return stateIn;
		}
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
	{
		return false;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(ROTATION, TOP);
	}
}
