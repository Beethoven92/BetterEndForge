package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.tileentity.HydrothermalVentTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HydrothermalVentBlock extends Block implements IWaterLoggable
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 1, 1, 15, 16, 15);
	
	public HydrothermalVentBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(getDefaultState().with(WATERLOGGED, true).with(ACTIVATED, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return new HydrothermalVentTileEntity();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if (worldIn instanceof ServerWorld && state.get(WATERLOGGED) && worldIn.getBlockState(pos.up()).isIn(Blocks.WATER))
		{
			randomTick(state,(ServerWorld) worldIn, pos, worldIn.rand);
		}
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		state = worldIn.getBlockState(pos.down());
		return state.isIn(ModBlocks.SULPHURIC_ROCK.stone.get());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getPos();
		return this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER);
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.WATER.getDefaultState();
		}
		else if (stateIn.get(WATERLOGGED) && facing == Direction.UP && facingState.isIn(Blocks.WATER)) 
		{
			worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 20);
		}
		return stateIn;
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		if (!stateIn.get(ACTIVATED) && rand.nextBoolean()) 
		{
			double x = pos.getX() + rand.nextDouble();
			double y = pos.getY() + 0.9 + rand.nextDouble() * 0.3;
			double z = pos.getZ() + rand.nextDouble();
			if (stateIn.get(WATERLOGGED)) 
			{
				worldIn.addParticle(ModParticleTypes.GEYSER_PARTICLE.get(), x, y, z, 0, 0, 0);
			}
			else 
			{
				worldIn.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
			}
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		BlockPos up = pos.up();
		if (worldIn.getBlockState(up).isIn(Blocks.WATER)) 
		{
			BlockHelper.setWithoutUpdate(worldIn, up, ModBlocks.VENT_BUBBLE_COLUMN.get());
			worldIn.getPendingBlockTicks().scheduleTick(up, ModBlocks.VENT_BUBBLE_COLUMN.get(), 5);
		}
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(WATERLOGGED, ACTIVATED);
	}
}
