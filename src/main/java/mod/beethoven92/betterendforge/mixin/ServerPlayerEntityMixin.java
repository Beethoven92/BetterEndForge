package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin 
{	
	@Inject(method = "func_242110_a", at = @At("HEAD"), cancellable = true)
	private void be_createEndSpawnPlatform(ServerWorld world, BlockPos centerPos, CallbackInfo info) 
	{
		if (!CommonConfig.shouldGenerateObsidianPlatform()) 
		{
			info.cancel();
		}
	}
}
