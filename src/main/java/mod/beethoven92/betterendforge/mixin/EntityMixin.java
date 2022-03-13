package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.interfaces.TeleportingEntity;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(Entity.class)
public abstract class EntityMixin implements TeleportingEntity
{	
	private BlockPos exitPos;
	private long cooldown;
	
	@Shadow
	public float rotationYaw;
	@Shadow
	public float rotationPitch;
	@Shadow
	public boolean removed;
	@Shadow
	public World world;
	
	@Final
	@Shadow
	public abstract void detach();
	
	@Shadow
	public abstract Vector3d getMotion();
	
	@Shadow
	public abstract EntityType<?> getType();
	
	@Shadow
	protected abstract PortalInfo findDimensionEntryPoint(ServerWorld destination);

	@Inject(method = "changeDimension", at = @At("HEAD"), cancellable = true)
	public void changeDimension(ServerWorld destination, CallbackInfoReturnable<Entity> info) 
	{
		if (!removed && beCanTeleport() && world instanceof ServerWorld)
		{
			this.detach();
			this.world.getProfiler().push("changeDimension");
			this.world.getProfiler().push("reposition");
			PortalInfo teleportTarget = this.findDimensionEntryPoint(destination);
			if (teleportTarget != null) 
			{
				this.world.getProfiler().popPush("reloading");
				Entity entity = this.getType().create(destination);
				if (entity != null) 
				{
					entity.restoreFrom(Entity.class.cast(this));
					entity.moveTo(teleportTarget.pos.x, teleportTarget.pos.y, teleportTarget.pos.z, teleportTarget.yRot, entity.xRot);
					entity.setDeltaMovement(teleportTarget.speed);
					destination.addFromAnotherDimension(entity);
				}
				this.removed = true;
				this.world.getProfiler().pop();
				((ServerWorld) world).resetEmptyTime();
				destination.resetEmptyTime();
				this.world.getProfiler().pop();
				beResetExitPos();
				info.setReturnValue(entity);
				//info.cancel();
			}
		}
	}
	
	@Inject(method = "findDimensionEntryPoint", at = @At("HEAD"), cancellable = true)
	protected void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<PortalInfo> info) 
	{
		if (beCanTeleport()) 
		{
			info.setReturnValue(new PortalInfo(new Vector3d(exitPos.getX() + 0.5D, exitPos.getY(), exitPos.getZ() + 0.5D), getMotion(), rotationYaw, rotationPitch));
			//beResetExitPos();
			//info.cancel();
		}
	}
	
	@Inject(method = "baseTick", at = @At("TAIL"))
	public void baseTick(CallbackInfo info) 
	{
		if (hasCooldown())
		{
			this.cooldown--;
		}
	}
	
	@Override
	public long beGetCooldown()
	{
		return this.cooldown;
	}

	@Override
	public void beSetCooldown(long time)
	{
		this.cooldown = time;
	}

	@Override
	public void beSetExitPos(BlockPos pos)
	{
		this.exitPos = pos.immutable();
	}

	@Override
	public BlockPos beGetExitPos()
	{
		return this.exitPos;
	}
	
	@Override
	public void beResetExitPos()
	{
		this.exitPos = null;
	}
	
	@Override
	public boolean beCanTeleport()
	{
		return this.exitPos != null;
	}
}