package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.interfaces.TeleportingEntity;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.server.level.ServerLevel;
@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin
{
	@Inject(method = "createEndPlatform", at = @At("HEAD"), cancellable = true)
	private void be_createEndSpawnPlatform(ServerLevel world, BlockPos centerPos, CallbackInfo info)
	{
		if (!GeneratorOptions.generateObsidianPlatform())
		{
			info.cancel();
		}
	}
}