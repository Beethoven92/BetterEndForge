package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes 
{
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS,
			BetterEnd.MOD_ID);

	public static final RegistryObject<ContainerType<EndStoneSmelterContainer>> END_STONE_SMELTER = CONTAINER_TYPES.register("end_stone_smelter", 
			() -> IForgeContainerType.create(EndStoneSmelterContainer::new));
}
