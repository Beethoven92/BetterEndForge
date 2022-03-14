package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.IForgeShearable;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndLotusSeedBlock extends Block implements BonemealableBlock, LiquidBlockContainer, IForgeShearable
{
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 14, 12);
	
	public EndLotusSeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
			Vec3 vec3d = state.getOffset(worldIn, pos);
			return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.XZ;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.below());
		state = worldIn.getBlockState(pos);
		return isValidTerrain(down) && state.getFluidState().getType().equals(Fluids.WATER.getSource());
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			return Blocks.WATER.defaultBlockState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	protected boolean isValidTerrain(BlockState state) 
	{
		return state.is(ModTags.END_GROUND) || state.getBlock() == ModBlocks.ENDSTONE_DUST.get();
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) 
	{
		performBonemeal(worldIn, random, pos, state);
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		if (rand.nextInt(4) == 0) 
		{
			int age = state.getValue(AGE);
			if (age < 3) 
			{
				worldIn.setBlockAndUpdate(pos, state.setValue(AGE, age + 1));
			}
			else 
			{
				generate(worldIn, rand, pos);
			}
		}
	}
	
	public boolean searchForAirAbove(LevelAccessor worldIn, BlockPos pos) 
	{
		MutableBlockPos bpos = new MutableBlockPos();
		bpos.set(pos);
		while (worldIn.getBlockState(bpos).getFluidState().getType().equals(Fluids.WATER.getSource())) 
		{
			bpos.setY(bpos.getY() + 1);
		}
		return worldIn.isEmptyBlock(bpos) && worldIn.isEmptyBlock(bpos.above());
	}
	
	public void generate(LevelAccessor worldIn, Random rand, BlockPos pos)
	{
		if (searchForAirAbove(worldIn, pos))
		{
			BlockState startLeaf = ModBlocks.END_LOTUS_STEM.get().defaultBlockState().setValue(EndLotusStemBlock.LEAF, true);
			BlockState roots = ModBlocks.END_LOTUS_STEM.get().defaultBlockState().setValue(EndLotusStemBlock.SHAPE, TripleShape.BOTTOM).setValue(EndLotusStemBlock.WATERLOGGED, true);
			BlockState stem = ModBlocks.END_LOTUS_STEM.get().defaultBlockState();
			BlockState flower = ModBlocks.END_LOTUS_FLOWER.get().defaultBlockState();
			
			worldIn.setBlock(pos, roots, 1 | 2 | 16);
			MutableBlockPos bpos = new MutableBlockPos().set(pos);
			bpos.setY(bpos.getY() + 1);
			while (worldIn.getFluidState(bpos).isSource()) 
			{
				worldIn.setBlock(bpos, stem.setValue(EndLotusStemBlock.WATERLOGGED, true), 1 | 2 | 16);
				bpos.setY(bpos.getY() + 1);
			}
			
			int height = rand.nextBoolean() ? 0 : rand.nextBoolean() ? 1 : rand.nextBoolean() ? 1 : -1;
			TripleShape shape = (height == 0) ? TripleShape.TOP : TripleShape.MIDDLE;
			Direction dir = BlockHelper.getRandomHorizontalDirection(rand);
			BlockPos leafCenter = bpos.immutable().relative(dir);
			if (canGenerateLeaf(worldIn, leafCenter)) 
			{
				generateLeaf(worldIn, leafCenter);
				worldIn.setBlock(bpos, startLeaf.setValue(EndLotusStemBlock.SHAPE, shape).setValue(EndLotusStemBlock.FACING, dir), 1 | 2 | 16);
			}
			else 
			{
				worldIn.setBlock(bpos, stem.setValue(EndLotusStemBlock.SHAPE, shape), 1 | 2 | 16);
			}
			
			bpos.setY(bpos.getY() + 1);
			for (int i = 1; i <= height; i++) 
			{
				if (!worldIn.isEmptyBlock(bpos)) 
				{
					bpos.setY(bpos.getY() - 1);
					worldIn.setBlock(bpos, flower, 1 | 2 | 16);
					bpos.setY(bpos.getY() - 1);
					stem = worldIn.getBlockState(bpos);
					worldIn.setBlock(bpos, stem.setValue(EndLotusStemBlock.SHAPE, TripleShape.TOP), 1 | 2 | 16);
					return;
				}
				worldIn.setBlock(bpos, stem, 1 | 2 | 16);
				bpos.setY(bpos.getY() + 1);
			}
			
			if (!worldIn.isEmptyBlock(bpos) || height < 0) 
			{
				bpos.setY(bpos.getY() - 1);
			}
			
			worldIn.setBlock(bpos, flower, 1 | 2 | 16);
			bpos.setY(bpos.getY() - 1);
			stem = worldIn.getBlockState(bpos);
			if (!stem.is(ModBlocks.END_LOTUS_STEM.get())) 
			{
				stem = ModBlocks.END_LOTUS_STEM.get().defaultBlockState();
				if (!worldIn.getBlockState(bpos.north()).getFluidState().isEmpty()) 
				{
					stem = stem.setValue(EndLotusStemBlock.WATERLOGGED, true);
				}
			}
			
			if (worldIn.getBlockState(bpos.relative(dir)).is(ModBlocks.END_LOTUS_LEAF.get())) 
			{
				stem = stem.setValue(EndLotusStemBlock.LEAF, true).setValue(EndLotusStemBlock.FACING, dir);
			}
			
			worldIn.setBlock(bpos, stem.setValue(EndLotusStemBlock.SHAPE, TripleShape.TOP), 1 | 2 | 16);
		}
	}
	
	private boolean canGenerateLeaf(LevelAccessor world, BlockPos pos) 
	{
		MutableBlockPos p = new MutableBlockPos();
		p.setY(pos.getY());
		int count = 0;
		for (int x = -1; x < 2; x ++) 
		{
			p.setX(pos.getX() + x);
			for (int z = -1; z < 2; z ++) 
			{
				p.setZ(pos.getZ() + z);
				if (world.isEmptyBlock(p) && !world.getFluidState(p.below()).isEmpty())
					count ++;
			}
		}
		return count == 9;
	}
	
	private void generateLeaf(LevelAccessor worldIn, BlockPos pos) 	
	{
		MutableBlockPos p = new MutableBlockPos();
		BlockState leaf = ModBlocks.END_LOTUS_LEAF.get().defaultBlockState();
		worldIn.setBlock(pos, leaf.setValue(EndLotusLeafBlock.SHAPE, TripleShape.BOTTOM), 1 | 2 | 16);
		
		for (Direction move: Direction.Plane.HORIZONTAL) 
		{
			worldIn.setBlock(p.set(pos).move(move), leaf.setValue(EndLotusLeafBlock.HORIZONTAL_FACING, move).setValue(EndLotusLeafBlock.SHAPE, TripleShape.MIDDLE), 1 | 2 | 16);
		}
		
		for (int i = 0; i < 4; i ++) 
		{
			Direction d1 = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			Direction d2 = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			worldIn.setBlock(p.set(pos).move(d1).move(d2), leaf.setValue(EndLotusLeafBlock.HORIZONTAL_FACING, d1).setValue(EndLotusLeafBlock.SHAPE, TripleShape.TOP), 1 | 2 | 16);
		}
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) 
	{
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn)
	{
		return false;
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getSource(false);
	}
}
