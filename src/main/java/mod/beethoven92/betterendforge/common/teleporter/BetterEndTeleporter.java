package mod.beethoven92.betterendforge.common.teleporter;

import java.util.function.Function;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

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
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) 
	{
		return repositionEntity.apply(false);
	}
	
	@Override
	public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld,
			Function<ServerWorld, PortalInfo> defaultPortalInfo) 
	{
		return new PortalInfo(new Vector3d(exitPos.getX() + 0.5D, exitPos.getY(), exitPos.getZ() + 0.5D), entity.getMotion(), entity.rotationYaw, entity.rotationPitch);
	}	
}
