package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.PentaShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class LanceleafBlock extends PlantBlock {
	public static final EnumProperty<PentaShape> SHAPE = BlockProperties.PENTA_SHAPE;
	public static final IntegerProperty ROTATION = BlockProperties.ROTATION;

	public LanceleafBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(SHAPE, ROTATION);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		PentaShape shape = state.getValue(SHAPE);
		if (shape == PentaShape.TOP) {
			return world.getBlockState(pos.below()).is(this);
		} else if (shape == PentaShape.BOTTOM) {
			return world.getBlockState(pos.below()).is(ModBlocks.AMBER_MOSS.get())
					&& world.getBlockState(pos.above()).is(this);
		} else {
			return world.getBlockState(pos.below()).is(this) && world.getBlockState(pos.above()).is(this);
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		if (!canSurvive(state, world, pos)) {
			return Blocks.AIR.defaultBlockState();
		}
		else {
			return state;
		}
	}
}
