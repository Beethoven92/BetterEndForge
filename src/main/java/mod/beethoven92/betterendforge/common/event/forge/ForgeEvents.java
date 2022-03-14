package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.capability.EndData;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ForgeEvents {
	@SubscribeEvent
	public static void giveGuideBookToPlayer(AdvancementEvent event) {
		ResourceLocation id = event.getAdvancement().getId();
	}

	@SubscribeEvent
	public static void loginEvent(PlayerLoggedInEvent event) {
		EndData.playerLogin((ServerPlayer) event.getPlayer());
	}

	@SubscribeEvent
	public static void respawnEvent(PlayerRespawnEvent event) {
		EndData.playerRespawn((ServerPlayer) event.getPlayer());
	}

	@SubscribeEvent
	public static void removeBlindness(PotionApplicableEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getPotionEffect().getEffect() == MobEffects.BLINDNESS
				&& entity.getAttributes().hasAttribute(ModAttributes.BLINDNESS_RESISTANCE.get())
				&& entity.getAttributeValue(ModAttributes.BLINDNESS_RESISTANCE.get()) > 0)
			event.setResult(Result.DENY);
	}

}
