package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.capability.EndData;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
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
		EndData.playerLogin((ServerPlayerEntity) event.getPlayer());
	}

	@SubscribeEvent
	public static void respawnEvent(PlayerRespawnEvent event) {
		EndData.playerRespawn((ServerPlayerEntity) event.getPlayer());
	}

}
