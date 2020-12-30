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

public class LanceleafBlock extends PlantBlock {
	public static final EnumProperty<PentaShape> SHAPE = BlockProperties.PENTA_SHAPE;
	public static final IntegerProperty ROTATION = BlockProperties.ROTATION;

	public LanceleafBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(SHAPE, ROTATION);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		PentaShape shape = state.get(SHAPE);
		if (shape == PentaShape.TOP) {
			return world.getBlockState(pos.down()).isIn(this);
		} else if (shape == PentaShape.BOTTOM) {
			return world.getBlockState(pos.down()).isIn(ModBlocks.AMBER_MOSS.get())
					&& world.getBlockState(pos.up()).isIn(this);
		} else {
			return world.getBlockState(pos.down()).isIn(this) && world.getBlockState(pos.up()).isIn(this);
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		if (!isValidPosition(state, world, pos)) {
			return Blocks.AIR.getDefaultState();
		}
		else {
			return state;
		}
	}
}
