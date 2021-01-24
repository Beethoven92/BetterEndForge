package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ForgeEvents 
{
	@SubscribeEvent
	public static void giveGuideBookToPlayer(AdvancementEvent event)
	{
		ResourceLocation id = event.getAdvancement().getId();
		if (id.equals(new ResourceLocation("minecraft:end/enter_end_gateway")))
		{
			event.getPlayer().addItemStackToInventory(new ItemStack(ModItems.GUIDE_BOOK.get()));
		}
	}
}
