package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class MossyDragonBoneBlock extends RotatedPillarBlock {
	public MossyDragonBoneBlock() {
		super(AbstractBlock.Properties.copy(Blocks.BONE_BLOCK).strength(0.5f).randomTicks());
	}
	

	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(16) == 0 && !canSurvive(state, world, pos)) {
			world.setBlockAndUpdate(pos, Blocks.BONE_BLOCK.defaultBlockState().setValue(AXIS, state.getValue(AXIS)));
		}
	}
	
	public static boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos blockPos = pos.above();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.is(Blocks.SNOW) && (Integer) blockState.getValue(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LightEngine.getLightBlockInto(world, state, pos, blockState, blockPos, Direction.UP,
					state.getLightBlock(world, blockPos));
			return i < 5;
		}
	}
}
