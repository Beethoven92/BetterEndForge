package mod.beethoven92.betterendforge.mixin;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
//import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
//import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.pattern.BlockPattern;
//import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
//import net.minecraft.world.level.dimension.end.EndDragonFight;
//import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;
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

@Mixin(EndDragonFight.class)
public class EndDragonFightMixin {
	@Shadow
	private DragonRespawnAnimation respawnStage;
	@Shadow
	private boolean dragonKilled;
	@Shadow
	private BlockPos portalLocation;
	@Final
	@Shadow
	private static Logger LOGGER;
	@Final
	@Shadow
	private ServerLevel level;
	
	@Shadow
	private BlockPattern.BlockPatternMatch findExitPortal() {
		return null;
	}
	
	@Shadow
	private void spawnExitPortal(boolean bl) {
	}
	
	@Shadow
	private void respawnDragon(List<EndCrystal> list) {
	}
	
	@Inject(method = "tryRespawn", at = @At("HEAD"), cancellable = true)
	private void be_tryRespawn(CallbackInfo info) {
		if (GeneratorOptions.replacePortal() && GeneratorOptions.hasDragonFights() && this.dragonKilled && this.respawnStage == null) {
			BlockPos blockPos = portalLocation;
			if (blockPos == null) {
				LOGGER.debug("Tried to respawn, but need to find the portal first.");
				BlockPattern.BlockPatternMatch blockPatternMatch = this.findExitPortal();
				if (blockPatternMatch == null) {
					LOGGER.debug("Couldn't find a portal, so we made one.");
					spawnExitPortal(true);
				}
				else {
					LOGGER.debug("Found the exit portal & temporarily using it.");
				}
				
				blockPos = portalLocation;
			}
			
			List<EndCrystal> crystals = Lists.newArrayList();
			BlockPos center = GeneratorOptions.getPortalPos().above(5);
			for (Direction dir : BlockHelper.HORIZONTAL_DIRECTIONS) {
				BlockPos central = center.relative(dir, 4);
				List<EndCrystal> crystalList = level.getEntitiesOfClass(
					EndCrystal.class,
					new AABB(central.below(255).south().west(), central.above(255).north().east())
				);
				
				int count = crystalList.size();
				for (int n = 0; n < count; n++) {
					EndCrystal crystal = crystalList.get(n);
					if (!level.getBlockState(crystal.blockPosition().below()).is(Blocks.BEDROCK)) {
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
