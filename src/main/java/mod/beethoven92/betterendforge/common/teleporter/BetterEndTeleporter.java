package mod.beethoven92.betterendforge.common.teleporter;

import java.util.function.Function;

import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

// Needed when calling ServerPlayerEntity#changeDimension, in order to
// avoid "Player moved wrongly" situations which results in teleporting player position being misplaced
public class BetterEndTeleporter implements net.minecraftforge.common.util.ITeleporter
{
	BlockPos exitPos;
	
	public BetterEndTeleporter(BlockPos exitPos)
	{
		this.exitPos = exitPos;
	}
	
	@Override
	public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) 
	{
		return repositionEntity.apply(false);
	}
	
	@Override
	public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
			Function<ServerLevel, PortalInfo> defaultPortalInfo) 
	{
		return new PortalInfo(new Vec3(exitPos.getX() + 0.5D, exitPos.getY(), exitPos.getZ() + 0.5D), entity.getDeltaMovement(), entity.yRot, entity.xRot);
	}	
}
