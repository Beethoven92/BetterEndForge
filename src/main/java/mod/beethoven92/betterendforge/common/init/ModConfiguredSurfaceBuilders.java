package mod.beethoven92.betterendforge.common.init;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

// NEEDS RE-WRITE
public class ModConfiguredSurfaceBuilders 
{
	public static ResourceKey<ConfiguredSurfaceBuilder<?>> CRYSTAL_SURFACE = makeKey("crystal_surface");
	public static ResourceKey<ConfiguredSurfaceBuilder<?>> MEGALAKE_SURFACE = makeKey("megalake_surface");
	public static ResourceKey<ConfiguredSurfaceBuilder<?>> MUSHROOMLAND_SURFACE = makeKey("mushroomland_surface");
	public static ResourceKey<ConfiguredSurfaceBuilder<?>> SULPHURIC_SURFACE = makeKey("sulphuric_surface");
	public static ResourceKey<ConfiguredSurfaceBuilder<?>> NEON_OASUS_SURFACE = makeKey("neon_oasis_surface");
	public static ResourceKey<ConfiguredSurfaceBuilder<?>> UMBRA_SURFACE = makeKey("umbra_surface");
	
	private static ResourceKey<ConfiguredSurfaceBuilder<?>> makeKey(final String name) 
	{
		return ResourceKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY, 
				new ResourceLocation(BetterEnd.MOD_ID, name));
	}
	
	public static Supplier<ConfiguredSurfaceBuilder<?>> get(final ResourceKey<ConfiguredSurfaceBuilder<?>> key) 
	{
		return () -> BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(key);
	}
	
	@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = Bus.MOD)
	public static class RegistrationHandler 
	{
		// Ensure this is run after the SurfaceBuilder DeferredRegister in ModSurfaceBuilders
		@SubscribeEvent(priority = EventPriority.LOW)
		public static void register(final RegistryEvent.Register<SurfaceBuilder<?>> event) 
		{
			register(CRYSTAL_SURFACE, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, ModSurfaceBuilders.Configs.CRYSTAL_SURFACE));
			register(MEGALAKE_SURFACE, new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.MEGALAKE_SURFACE.get(), ModSurfaceBuilders.Configs.DUMMY));
			register(MUSHROOMLAND_SURFACE, new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.MUSHROOMLAND_SURFACE.get(), ModSurfaceBuilders.Configs.DUMMY));
			register(SULPHURIC_SURFACE, new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.SULPHURIC_SURFACE.get(), ModSurfaceBuilders.Configs.DUMMY));
			register(NEON_OASUS_SURFACE, new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.NEON_OASIS_SURFACE.get(), ModSurfaceBuilders.Configs.DUMMY));
			register(UMBRA_SURFACE, new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.UMBRA_SURFACE.get(), ModSurfaceBuilders.Configs.DUMMY));

		}

		private static void register(final ResourceKey<ConfiguredSurfaceBuilder<?>> key, 
				final ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) 
		{
			Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, key.location(), configuredSurfaceBuilder);
		}
	}
}
