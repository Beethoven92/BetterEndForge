package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndLilyBlock extends UnderwaterPlantBlock
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final VoxelShape SHAPE_BOTTOM = Block.box(4, 0, 4, 12, 16, 12);
	private static final VoxelShape SHAPE_TOP = Block.box(2, 0, 2, 14, 6, 14);
	
	public EndLilyBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		Vec3 vec3d = state.getOffset(worldIn, pos);
		VoxelShape shape = state.getValue(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
		return shape.move(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) 
	{
		if (state.getValue(SHAPE) == TripleShape.TOP) 
		{
			return worldIn.getBlockState(pos.below()).getBlock() == this;
		}
		else if (state.getValue(SHAPE) == TripleShape.BOTTOM) 
		{
			return isTerrain(worldIn.getBlockState(pos.below()));
		}
		else 
		{
			BlockState up = worldIn.getBlockState(pos.above());
			BlockState down = worldIn.getBlockState(pos.below());
			return up.getBlock() == this && down.getBlock() == this;
		}
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) 
		{
			return stateIn.getValue(SHAPE) == TripleShape.TOP ? Blocks.AIR.defaultBlockState() : Blocks.WATER.defaultBlockState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.getValue(SHAPE) == TripleShape.TOP ? Fluids.EMPTY.defaultFluidState() : Fluids.WATER.getSource(false);
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos,
			Player player) {
		return ModBlocks.END_LILY_SEED.get().asItem().getDefaultInstance();
	}
}
