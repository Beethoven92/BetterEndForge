package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class MossyObsidianBlock extends Block {
	public MossyObsidianBlock() {
		super(AbstractBlock.Properties.from(Blocks.OBSIDIAN).hardnessAndResistance(3).tickRandomly());
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(16) == 0 && !canSurvive(state, world, pos)) {
			world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
		}
	}

	public static boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isIn(Blocks.SNOW) && (Integer) blockState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getLevel() == 8) {
			return false;
		} else {
			int i = LightEngine.func_215613_a(world, state, pos, blockState, blockPos, Direction.UP,
					state.getOpacity(world, blockPos));
			return i < 5;
		}
	}
}
