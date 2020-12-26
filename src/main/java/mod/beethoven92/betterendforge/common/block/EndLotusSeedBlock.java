package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

public class EndLotusSeedBlock extends Block implements IGrowable, ILiquidContainer, IForgeShearable
{
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 14, 12);
	
	public EndLotusSeedBlock(Properties properties) 
	{
		super(properties);
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
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.down());
		state = worldIn.getBlockState(pos);
		return isValidTerrain(down) && state.getFluidState().getFluid().equals(Fluids.WATER.getStillFluid());
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.WATER.getDefaultState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	protected boolean isValidTerrain(BlockState state) 
	{
		return state.isIn(ModTags.END_GROUND) || state.getBlock() == ModBlocks.ENDSTONE_DUST.get();
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		grow(worldIn, random, pos, state);
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		if (rand.nextInt(4) == 0) 
		{
			int age = state.get(AGE);
			if (age < 3) 
			{
				worldIn.setBlockState(pos, state.with(AGE, age + 1));
			}
			else 
			{
				generate(worldIn, rand, pos);
			}
		}
	}
	
	public boolean searchForAirAbove(IWorld worldIn, BlockPos pos) 
	{
		Mutable bpos = new Mutable();
		bpos.setPos(pos);
		while (worldIn.getBlockState(bpos).getFluidState().getFluid().equals(Fluids.WATER.getStillFluid())) 
		{
			bpos.setY(bpos.getY() + 1);
		}
		return worldIn.isAirBlock(bpos) && worldIn.isAirBlock(bpos.up());
	}
	
	public void generate(IWorld worldIn, Random rand, BlockPos pos)
	{
		if (searchForAirAbove(worldIn, pos))
		{
			BlockState startLeaf = ModBlocks.END_LOTUS_STEM.get().getDefaultState().with(EndLotusStemBlock.LEAF, true);
			BlockState roots = ModBlocks.END_LOTUS_STEM.get().getDefaultState().with(EndLotusStemBlock.SHAPE, TripleShape.BOTTOM).with(EndLotusStemBlock.WATERLOGGED, true);
			BlockState stem = ModBlocks.END_LOTUS_STEM.get().getDefaultState();
			BlockState flower = ModBlocks.END_LOTUS_FLOWER.get().getDefaultState();
			
			worldIn.setBlockState(pos, roots, 1 | 2 | 16);
			Mutable bpos = new Mutable().setPos(pos);
			bpos.setY(bpos.getY() + 1);
			while (worldIn.getFluidState(bpos).isSource()) 
			{
				worldIn.setBlockState(bpos, stem.with(EndLotusStemBlock.WATERLOGGED, true), 1 | 2 | 16);
				bpos.setY(bpos.getY() + 1);
			}
			
			int height = rand.nextBoolean() ? 0 : rand.nextBoolean() ? 1 : rand.nextBoolean() ? 1 : -1;
			TripleShape shape = (height == 0) ? TripleShape.TOP : TripleShape.MIDDLE;
			Direction dir = BlockHelper.getRandomHorizontalDirection(rand);
			BlockPos leafCenter = bpos.toImmutable().offset(dir);
			if (canGenerateLeaf(worldIn, leafCenter)) 
			{
				generateLeaf(worldIn, leafCenter);
				worldIn.setBlockState(bpos, startLeaf.with(EndLotusStemBlock.SHAPE, shape).with(EndLotusStemBlock.FACING, dir), 1 | 2 | 16);
			}
			else 
			{
				worldIn.setBlockState(bpos, stem.with(EndLotusStemBlock.SHAPE, shape), 1 | 2 | 16);
			}
			
			bpos.setY(bpos.getY() + 1);
			for (int i = 1; i <= height; i++) 
			{
				if (!worldIn.isAirBlock(bpos)) 
				{
					bpos.setY(bpos.getY() - 1);
					worldIn.setBlockState(bpos, flower, 1 | 2 | 16);
					bpos.setY(bpos.getY() - 1);
					stem = worldIn.getBlockState(bpos);
					worldIn.setBlockState(bpos, stem.with(EndLotusStemBlock.SHAPE, TripleShape.TOP), 1 | 2 | 16);
					return;
				}
				worldIn.setBlockState(bpos, stem, 1 | 2 | 16);
				bpos.setY(bpos.getY() + 1);
			}
			
			if (!worldIn.isAirBlock(bpos) || height < 0) 
			{
				bpos.setY(bpos.getY() - 1);
			}
			
			worldIn.setBlockState(bpos, flower, 1 | 2 | 16);
			bpos.setY(bpos.getY() - 1);
			stem = worldIn.getBlockState(bpos);
			if (!stem.isIn(ModBlocks.END_LOTUS_STEM.get())) 
			{
				stem = ModBlocks.END_LOTUS_STEM.get().getDefaultState();
				if (!worldIn.getBlockState(bpos.north()).getFluidState().isEmpty()) 
				{
					stem = stem.with(EndLotusStemBlock.WATERLOGGED, true);
				}
			}
			
			if (worldIn.getBlockState(bpos.offset(dir)).isIn(ModBlocks.END_LOTUS_LEAF.get())) 
			{
				stem = stem.with(EndLotusStemBlock.LEAF, true).with(EndLotusStemBlock.FACING, dir);
			}
			
			worldIn.setBlockState(bpos, stem.with(EndLotusStemBlock.SHAPE, TripleShape.TOP), 1 | 2 | 16);
		}
	}
	
	private boolean canGenerateLeaf(IWorld world, BlockPos pos) 
	{
		Mutable p = new Mutable();
		p.setY(pos.getY());
		int count = 0;
		for (int x = -1; x < 2; x ++) 
		{
			p.setX(pos.getX() + x);
			for (int z = -1; z < 2; z ++) 
			{
				p.setZ(pos.getZ() + z);
				if (world.isAirBlock(p) && !world.getFluidState(p.down()).isEmpty())
					count ++;
			}
		}
		return count == 9;
	}
	
	private void generateLeaf(IWorld worldIn, BlockPos pos) 	
	{
		Mutable p = new Mutable();
		BlockState leaf = ModBlocks.END_LOTUS_LEAF.get().getDefaultState();
		worldIn.setBlockState(pos, leaf.with(EndLotusLeafBlock.SHAPE, TripleShape.BOTTOM), 1 | 2 | 16);
		
		for (Direction move: Direction.Plane.HORIZONTAL) 
		{
			worldIn.setBlockState(p.setPos(pos).move(move), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, move).with(EndLotusLeafBlock.SHAPE, TripleShape.MIDDLE), 1 | 2 | 16);
		}
		
		for (int i = 0; i < 4; i ++) 
		{
			Direction d1 = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			Direction d2 = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			worldIn.setBlockState(p.setPos(pos).move(d1).move(d2), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, d1).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP), 1 | 2 | 16);
		}
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) 
	{
		return false;
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn)
	{
		return false;
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getStillFluidState(false);
	}
}
