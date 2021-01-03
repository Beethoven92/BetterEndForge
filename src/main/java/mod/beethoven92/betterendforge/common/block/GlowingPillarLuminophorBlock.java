package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class GlowingPillarLuminophorBlock extends Block {
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

	public GlowingPillarLuminophorBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(NATURAL, false));
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		return state.get(NATURAL) ? world.getBlockState(pos.down()).isIn(ModBlocks.GLOWING_PILLAR_ROOTS.get()) : true;
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos pos, BlockPos facingPos) {
		if (!isValidPosition(state, world, pos)) {
			return Blocks.AIR.getDefaultState();
		} else {
			return state;
		}
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(NATURAL);
	}
}
