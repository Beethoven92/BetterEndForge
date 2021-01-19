package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.common.interfaces.ITeleportingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin //implements ITeleportingEntity 
{	
	//private long beCooldown;
	
	/*@Inject(method = "func_242110_a", at = @At("HEAD"), cancellable = true)
	private void be_createEndSpawnPlatform(ServerWorld world, BlockPos centerPos, CallbackInfo info) 
	{
		if (!centerPos.equals(world.getSpawnPoint()))// || beCanTeleport()) 
		{
			info.cancel();
		}
	}*/
	
	/*@Inject(method = "tick", at = @At("TAIL"))
	public void baseTick(CallbackInfo info) 
	{
		if (hasCooldown()) 
		{
			this.beCooldown--;
		}
	}
	
	@Override
	public long beGetCooldown()
	{
		return this.beCooldown;
	}

	@Override
	public void beSetCooldown(long time) 
	{
		this.beCooldown = time;
	}

	@Override
	public void beSetExitPos(BlockPos pos) {}

	@Override
	public BlockPos beGetExitPos() 
	{
		return null;
	}*/
}
