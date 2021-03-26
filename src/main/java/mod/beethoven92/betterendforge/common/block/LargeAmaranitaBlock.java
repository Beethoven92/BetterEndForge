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

public class LargeAmaranitaBlock extends PlantBlock {
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(4, 0, 4, 12, 14, 12);
	private static final VoxelShape SHAPE_TOP = VoxelShapes.or(Block.makeCuboidShape(1, 3, 1, 15, 16, 15),
			SHAPE_BOTTOM);

	public LargeAmaranitaBlock() {
		super(AbstractBlock.Properties.create(Material.PLANTS)
				.setLightLevel((state) -> (state.get(SHAPE) == TripleShape.TOP) ? 15 : 0).sound(SoundType.PLANT)
				.zeroHardnessAndResistance());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.SANGNUM.get()) || state.isIn(ModBlocks.MOSSY_OBSIDIAN.get()) || state.isIn(ModBlocks.MOSSY_DRAGON_BONE.get());
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		TripleShape shape = state.get(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			return isTerrain(world.getBlockState(pos.down())) && world.getBlockState(pos.up()).isIn(this);
		} else if (shape == TripleShape.TOP) {
			return world.getBlockState(pos.down()).isIn(this);
		} else {
			return world.getBlockState(pos.down()).isIn(this) && world.getBlockState(pos.up()).isIn(this);
		}
	}

	@Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}
}
