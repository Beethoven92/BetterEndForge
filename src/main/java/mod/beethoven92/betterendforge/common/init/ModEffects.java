package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.potion.EndVeilEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects 
{
	public static final DeferredRegister<MobEffect> EFFECTS = 
			DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BetterEnd.MOD_ID);

	public static final RegistryObject<EndVeilEffect> END_VEIL = EFFECTS.register("end_veil",
			() -> new EndVeilEffect());
}
