package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.player.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin 
{	
	/*@Inject(method = "func_242110_a", at = @At("HEAD"), cancellable = true)
	private void be_createEndSpawnPlatform(ServerWorld world, BlockPos centerPos, CallbackInfo info) 
	{
	}*/
}
