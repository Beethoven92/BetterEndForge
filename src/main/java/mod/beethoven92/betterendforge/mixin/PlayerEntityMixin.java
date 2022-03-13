package mod.beethoven92.betterendforge.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	private static Direction[] HORIZONTAL;
	
	@Inject(method = "findRespawnPositionAndUseSpawnBlock", at = @At(value = "HEAD"), cancellable = true)
	private static void be_statueRespawn(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vector3d>> info) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.is(ModBlocks.RESPAWN_OBELISK.get())) {
			info.setReturnValue(beObeliskRespawnPosition(world, pos, blockState));
			info.cancel();
		}
	}

	private static Optional<Vector3d> beObeliskRespawnPosition(ServerWorld world, BlockPos pos, BlockState state) {
		if (state.getValue(BlockProperties.TRIPLE_SHAPE) == TripleShape.TOP) {
			pos = pos.below(2);
		}
		else if (state.getValue(BlockProperties.TRIPLE_SHAPE) == TripleShape.MIDDLE) {
			pos = pos.below();
		}
		if (HORIZONTAL == null) {
			HORIZONTAL = BlockHelper.makeHorizontal();
		}
		ModMathHelper.shuffle(HORIZONTAL, world.getRandom());
		for (Direction dir: HORIZONTAL) {
			BlockPos p = pos.relative(dir);
			BlockState state2 = world.getBlockState(p);
			if (!state2.getMaterial().blocksMotion() && state2.getCollisionShape(world, pos).isEmpty()) {
				return Optional.of(Vector3d.atLowerCornerOf(p).add(0.5, 0, 0.5));
			}
		}
		return Optional.empty();
	}
}