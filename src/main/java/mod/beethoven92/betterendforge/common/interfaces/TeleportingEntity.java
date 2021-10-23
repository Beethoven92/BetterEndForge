package mod.beethoven92.betterendforge.common.interfaces;

import net.minecraft.util.math.BlockPos;

public interface TeleportingEntity
{
	boolean beCanTeleport();

	public abstract void beSetExitPos(BlockPos pos);
	public abstract BlockPos beGetExitPos();
	void beResetExitPos();

	public abstract long beGetCooldown();
	public abstract void beSetCooldown(long time);

	default boolean hasCooldown()
	{
		return this.beGetCooldown() > 0;
	}
}
