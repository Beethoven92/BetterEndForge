package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.lootmodifier.BetterEndMusicDiscLootModifier;
import mod.beethoven92.betterendforge.common.lootmodifier.lootcondition.LootConditions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = BetterEnd.MOD_ID)
@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModLootModifiers 
{
	public static final GlobalLootModifierSerializer<BetterEndMusicDiscLootModifier> BETTER_END_MUSIC_DISC = null;

	@SubscribeEvent
	public static void onRegisterModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		LootConditions.register();
		IForgeRegistry<GlobalLootModifierSerializer<?>> reg = event.getRegistry();

		BetterEndMusicDiscLootModifier.Serializer modifier = new BetterEndMusicDiscLootModifier.Serializer();
		modifier.setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, "better_end_music_disc"));
		reg.register(modifier);
	}
}
