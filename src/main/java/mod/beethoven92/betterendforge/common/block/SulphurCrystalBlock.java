package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class SulphurCrystalBlock extends AttachedBlock implements IWaterLoggable
{
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	static 
	{
		BOUNDING_SHAPES.put(Direction.UP, VoxelShapes.box(0.125, 0.0, 0.125, 0.875F, 0.5, 0.875F));
		BOUNDING_SHAPES.put(Direction.DOWN, VoxelShapes.box(0.125, 0.5, 0.125, 0.875F, 1.0, 0.875F));
		BOUNDING_SHAPES.put(Direction.NORTH, VoxelShapes.box(0.125, 0.125, 0.5, 0.875F, 0.875F, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, VoxelShapes.box(0.125, 0.125, 0.0, 0.875F, 0.875F, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, VoxelShapes.box(0.5, 0.125, 0.125, 1.0, 0.875F, 0.875F));
		BOUNDING_SHAPES.put(Direction.EAST, VoxelShapes.box(0.0, 0.125, 0.125, 0.5, 0.875F, 0.875F));
	}
	
	public SulphurCrystalBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		Direction direction = (Direction) state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		return worldIn.getBlockState(blockPos).is(ModBlocks.BRIMSTONE.get());
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockState state = super.getStateForPlacement(context);
		if (state != null)
		{
			IWorldReader worldView = context.getLevel();
			BlockPos blockPos = context.getClickedPos();
			boolean water = worldView.getFluidState(blockPos).getType() == Fluids.WATER;
			return state.setValue(WATERLOGGED, water);
		}
		return null;
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		super.createBlockStateDefinition(builder);
		builder.add(AGE, WATERLOGGED);
	}
}
