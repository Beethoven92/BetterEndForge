package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.interfaces.TeleportingEntity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

@Mixin(Entity.class)
public abstract class EntityMixin implements TeleportingEntity
{	
	private BlockPos be_exitPos;
	private long be_cooldown;
	
	@Shadow
	public float yRot;
	@Shadow
	public float xRot;
	@Shadow
	public boolean removed;
	@Shadow
	public Level level;
	
	@Final
	@Shadow
	public abstract void unRide();
	
	@Shadow
	public abstract Vec3 getDeltaMovement();
	
	@Shadow
	public abstract EntityType<?> getType();
	
	@Shadow
	protected abstract PortalInfo findDimensionEntryPoint(ServerLevel destination);

	@Inject(method = "changeDimension", at = @At("HEAD"), cancellable = true)
	public void be_changeDimension(ServerLevel destination, CallbackInfoReturnable<Entity> info)
	{
		if (!removed && beCanTeleport() && level instanceof ServerLevel)
		{
			this.unRide();
			this.level.getProfiler().push("changeDimension");
			this.level.getProfiler().push("reposition");
			PortalInfo teleportTarget = this.findDimensionEntryPoint(destination);
			if (teleportTarget != null) 
			{
				this.level.getProfiler().popPush("reloading");
				Entity entity = this.getType().create(destination);
				if (entity != null) 
				{
					entity.restoreFrom(Entity.class.cast(this));
					entity.moveTo(teleportTarget.pos.x, teleportTarget.pos.y, teleportTarget.pos.z, teleportTarget.yRot, entity.xRot);
					entity.setDeltaMovement(teleportTarget.speed);
					destination.addFromAnotherDimension(entity);
				}
				this.removed = true;
				this.level.getProfiler().pop();
				((ServerLevel) level).resetEmptyTime();
				destination.resetEmptyTime();
				this.level.getProfiler().pop();
				beResetExitPos();
				info.setReturnValue(entity);
				//info.cancel();
			}
		}
	}
	
	@Inject(method = "findDimensionEntryPoint", at = @At("HEAD"), cancellable = true)
	protected void be_getTeleportTarget(ServerLevel destination, CallbackInfoReturnable<PortalInfo> info)
	{
		if (beCanTeleport()) 
		{
			info.setReturnValue(new PortalInfo(new Vec3(be_exitPos.getX() + 0.5D, be_exitPos.getY(), be_exitPos.getZ() + 0.5D), getDeltaMovement(), yRot, xRot));
			//beResetExitPos();
			//info.cancel();
		}
	}
	
	@Inject(method = "baseTick", at = @At("TAIL"))
	public void be_baseTick(CallbackInfo info)
	{
		if (hasCooldown())
		{
			this.be_cooldown--;
		}
	}
	
	@Override
	public long beGetCooldown()
	{
		return this.be_cooldown;
	}

	@Override
	public void beSetCooldown(long time)
	{
		this.be_cooldown = time;
	}

	@Override
	public void beSetExitPos(BlockPos pos)
	{
		this.be_exitPos = pos.immutable();
	}

	@Override
	public BlockPos beGetExitPos()
	{
		return this.be_exitPos;
	}
	
	@Override
	public void beResetExitPos()
	{
		this.be_exitPos = null;
	}
	
	@Override
	public boolean beCanTeleport()
	{
		return this.be_exitPos != null;
	}
}