package mod.beethoven92.betterendforge.mixin;

import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;

// TO DO: consider handling this with a forge event
@Mixin(MonsterEntity.class)
public class MonsterEntityMixin 
{
	// Reduces endermen spawn number
	@Inject(method = "canMonsterSpawnInLight", at = @At(value = "RETURN"), cancellable = true)
	private static void endermenCheck(EntityType<? extends MonsterEntity> type, IServerWorld world,
			SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) 
	{
		boolean canSpawn = info.getReturnValue();
		//if (CommonConfig.reduceEndermanSpawn() && canSpawn && spawnReason == SpawnReason.NATURAL && type == EntityType.ENDERMAN)
		//{
			AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
			List<EndermanEntity> entities = world.getEntitiesWithinAABB(EndermanEntity.class, box, (entity) -> { return true; });
			info.setReturnValue(entities.size() < 6);
		//}
	}
}
