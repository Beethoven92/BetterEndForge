package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.interfaces.TeleportingEntity;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//import mod.beethoven92.betterendforge.common.interfaces.ITeleportingEntity;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin
{
	@Inject(method = "func_242110_a", at = @At("HEAD"), cancellable = true)
	private void be_createEndSpawnPlatform(ServerWorld world, BlockPos centerPos, CallbackInfo info)
	{
		if (!GeneratorOptions.generateObsidianPlatform())
		{
			info.cancel();
		}
	}
}