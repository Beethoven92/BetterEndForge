package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.potion.EndVeilEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects 
{
	public static final DeferredRegister<Effect> EFFECTS = 
			DeferredRegister.create(ForgeRegistries.POTIONS, BetterEnd.MOD_ID);

	public static final RegistryObject<EndVeilEffect> END_VEIL = EFFECTS.register("end_veil",
			() -> new EndVeilEffect());
}
