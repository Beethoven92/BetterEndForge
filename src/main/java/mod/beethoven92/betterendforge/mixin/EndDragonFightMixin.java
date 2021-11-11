package mod.beethoven92.betterendforge.mixin;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
import net.minecraft.entity.item.EnderCrystalEntity;
//import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.end.DragonSpawnState;
//import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.pattern.BlockPattern;
//import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
//import net.minecraft.world.level.dimension.end.EndDragonFight;
//import net.minecraft.world.phys.AABB;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import ru.bclib.util.BlocksHelper;
//import ru.betterend.world.generator.GeneratorOptions;

import java.util.List;

@Mixin(DragonFightManager.class)
public class EndDragonFightMixin {
	@Shadow
	private DragonSpawnState respawnState;
	@Shadow
	private boolean dragonKilled;
	@Shadow
	private BlockPos exitPortalLocation;
	@Final
	@Shadow
	private static Logger LOGGER;
	@Final
	@Shadow
	private ServerWorld world;
	
	@Shadow
	private BlockPattern.PatternHelper findExitPortal() {
		return null;
	}
	
	@Shadow
	private void generatePortal(boolean bl) {
	}
	
	@Shadow
	private void respawnDragon(List<EnderCrystalEntity> list) {
	}
	
	@Inject(method = "tryRespawnDragon", at = @At("HEAD"), cancellable = true)
	private void be_tryRespawnDragon(CallbackInfo info) {
		if (GeneratorOptions.replacePortal() && GeneratorOptions.hasDragonFights() && this.dragonKilled && this.respawnState == null) {
			BlockPos blockPos = exitPortalLocation;
			if (blockPos == null) {
				LOGGER.debug("Tried to respawn, but need to find the portal first.");
				BlockPattern.PatternHelper blockPatternMatch = this.findExitPortal();
				if (blockPatternMatch == null) {
					LOGGER.debug("Couldn't find a portal, so we made one.");
					generatePortal(true);
				}
				else {
					LOGGER.debug("Found the exit portal & temporarily using it.");
				}
				
				blockPos = exitPortalLocation;
			}
			
			List<EnderCrystalEntity> crystals = Lists.newArrayList();
			BlockPos center = GeneratorOptions.getPortalPos().up(5);
			for (Direction dir : BlockHelper.HORIZONTAL_DIRECTIONS) {
				BlockPos central = center.offset(dir, 4);
				List<EnderCrystalEntity> crystalList = world.getEntitiesWithinAABB(
					EnderCrystalEntity.class,
					new AxisAlignedBB(central.down(255).south().west(), central.up(255).north().east())
				);
				
				int count = crystalList.size();
				for (int n = 0; n < count; n++) {
					EnderCrystalEntity crystal = crystalList.get(n);
					if (!world.getBlockState(crystal.getPosition().down()).isIn(Blocks.BEDROCK)) {
						crystalList.remove(n);
						count--;
						n--;
					}
				}
				
				if (crystalList.isEmpty()) {
					info.cancel();
					return;
				}
				
				crystals.addAll(crystalList);
			}
			
			LOGGER.debug("Found all crystals, respawning dragon.");
			respawnDragon(crystals);
			info.cancel();
		}
	}
}
