package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPotions 
{
	public static final DeferredRegister<Potion> POTIONS = 
			DeferredRegister.create(ForgeRegistries.POTION_TYPES, BetterEnd.MOD_ID);
	
	public static final RegistryObject<Potion> END_VEIL = POTIONS.register("end_veil",
			() -> new Potion("end_veil", new MobEffectInstance(ModEffects.END_VEIL.get(), 3600)));
	
	public static final RegistryObject<Potion> END_VEIL_LONG = POTIONS.register("end_veil_long",
			() -> new Potion("long_end_veil", new MobEffectInstance(ModEffects.END_VEIL.get(), 9600)));
}
