package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = Bus.MOD)
public class ModEvents {

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		for (EntityType<? extends LivingEntity> type : event.getTypes())
			event.add(type, ModAttributes.BLINDNESS_RESISTANCE.get());
	}
}
