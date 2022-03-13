package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.OffsetType;

public class LargeAmaranitaBlock extends PlantBlock {
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final VoxelShape SHAPE_BOTTOM = Block.box(4, 0, 4, 12, 14, 12);
	private static final VoxelShape SHAPE_TOP = VoxelShapes.or(Block.box(1, 3, 1, 15, 16, 15),
			SHAPE_BOTTOM);

	public LargeAmaranitaBlock() {
		super(AbstractBlock.Properties.of(Material.PLANT)
				.lightLevel((state) -> (state.getValue(SHAPE) == TripleShape.TOP) ? 15 : 0).sound(SoundType.GRASS)
				.instabreak());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.getValue(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.SANGNUM.get()) || state.is(ModBlocks.MOSSY_OBSIDIAN.get()) || state.is(ModBlocks.MOSSY_DRAGON_BONE.get());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			return isTerrain(world.getBlockState(pos.below())) && world.getBlockState(pos.above()).is(this);
		} else if (shape == TripleShape.TOP) {
			return world.getBlockState(pos.below()).is(this);
		} else {
			return world.getBlockState(pos.below()).is(this) && world.getBlockState(pos.above()).is(this);
		}
	}

	@Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}
}
