package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.server.level.ServerLevel;

public class MossyDragonBoneBlock extends RotatedPillarBlock {
	public MossyDragonBoneBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).strength(0.5f).randomTicks());
	}
	

	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		if (random.nextInt(16) == 0 && !canSurvive(state, world, pos)) {
			world.setBlockAndUpdate(pos, Blocks.BONE_BLOCK.defaultBlockState().setValue(AXIS, state.getValue(AXIS)));
		}
	}
	
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockPos blockPos = pos.above();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.is(Blocks.SNOW) && (Integer) blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LayerLightEngine.getLightBlockInto(world, state, pos, blockState, blockPos, Direction.UP,
					state.getLightBlock(world, blockPos));
			return i < 5;
		}
	}
}
