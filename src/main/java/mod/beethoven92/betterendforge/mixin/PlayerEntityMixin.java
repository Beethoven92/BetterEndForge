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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
	private static Direction[] HORIZONTAL;
	
	@Inject(method = "findRespawnPositionAndUseSpawnBlock", at = @At(value = "HEAD"), cancellable = true)
	private static void be_statueRespawn(ServerLevel world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3>> info) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.is(ModBlocks.RESPAWN_OBELISK.get())) {
			info.setReturnValue(beObeliskRespawnPosition(world, pos, blockState));
			info.cancel();
		}
	}

	private static Optional<Vec3> beObeliskRespawnPosition(ServerLevel world, BlockPos pos, BlockState state) {
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
				return Optional.of(Vec3.atLowerCornerOf(p).add(0.5, 0, 0.5));
			}
		}
		return Optional.empty();
	}
}