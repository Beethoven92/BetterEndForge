package mod.beethoven92.betterendforge.common.world;

import com.mojang.serialization.Lifecycle;

import mod.beethoven92.betterendforge.common.world.generator.BetterEndBiomeProvider;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.ForgeWorldType;

public class TerraforgedIntegrationWorldType extends ForgeWorldType 
{ 
	public TerraforgedIntegrationWorldType() 
	{
        super(null);
    }

	@Override
	public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry,
			Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) 
	{
		return new NoiseChunkGenerator(new OverworldBiomeProvider(seed, false, false, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(DimensionSettings.field_242734_c));
	}

    public static SimpleRegistry<Dimension> getDefaultSimpleRegistry(Registry<DimensionType> lookUpRegistryDimensionType, Registry<Biome> registry, Registry<DimensionSettings> dimensionSettings, long seed) 
    {
        SimpleRegistry<Dimension> simpleregistry = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.stable());
        
        simpleregistry.register(Dimension.OVERWORLD, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.OVERWORLD), new NoiseChunkGenerator(new OverworldBiomeProvider(seed, false, false, registry), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.field_242734_c))), Lifecycle.stable());

        //simpleregistry.register(Dimension.THE_NETHER, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.THE_NETHER), new NoiseChunkGenerator(NetherBiomeProvider.Preset.DEFAULT_NETHER_PROVIDER_PRESET.build(registry, seed), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.field_242736_e))), Lifecycle.stable());
        
        simpleregistry.register(Dimension.THE_END, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.THE_END), new NoiseChunkGenerator(new BetterEndBiomeProvider(registry, seed), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.field_242737_f))), Lifecycle.stable());

        return simpleregistry;
    }

    @Override
    public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, 
    		boolean generateStructures, boolean generateLoot, String generatorSettings) 
    {
        return new CustomSettings(seed, generateStructures, generateLoot, getDefaultSimpleRegistry(dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY), dynamicRegistries.getRegistry(Registry.BIOME_KEY), dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY), seed));
    }

    public static class CustomSettings extends DimensionGeneratorSettings 
    {
        public CustomSettings(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> dimensionSimpleRegistry) 
        {
            super(seed, generateFeatures, bonusChest, dimensionSimpleRegistry);
        }

        // This hides our custom world type from the world type selection screen.
        // So its not possible for a player to create a new world using this world type by normal means,
        // as this was added only to ensure integration with the Terraforged mod
        @Override
        public boolean func_236227_h_() 
        {
            return true;
        }
    }

}
