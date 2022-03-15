package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Attribute.class,
			BetterEnd.MOD_ID);

	public static final RegistryObject<Attribute> BLINDNESS_RESISTANCE = ATTRIBUTES.register("blindness_resistance",
			() -> new RangedAttribute("attribute.name.generic.blindness_resistance", 0, 0, 1).setSyncable(true));

}
