package mod.beethoven92.betterendforge.common.world;

import com.mojang.serialization.Lifecycle;

import mod.beethoven92.betterendforge.common.world.generator.BetterEndBiomeProvider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraftforge.common.world.ForgeWorldType;

public class TerraforgedIntegrationWorldType extends ForgeWorldType 
{ 
	public TerraforgedIntegrationWorldType() 
	{
        super(null);
    }

	@Override
	public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry,
			Registry<NoiseGeneratorSettings> dimensionSettingsRegistry, long seed, String generatorSettings) 
	{
		return new NoiseBasedChunkGenerator(new OverworldBiomeSource(seed, false, false, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
	}

    public static MappedRegistry<LevelStem> getDefaultSimpleRegistry(Registry<DimensionType> lookUpRegistryDimensionType, Registry<Biome> registry, Registry<NoiseGeneratorSettings> dimensionSettings, long seed) 
    {
        MappedRegistry<LevelStem> simpleregistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.stable());
        
        simpleregistry.register(LevelStem.OVERWORLD, new LevelStem(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.OVERWORLD_LOCATION), new NoiseBasedChunkGenerator(new OverworldBiomeSource(seed, false, false, registry), seed, () -> dimensionSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD))), Lifecycle.stable());

        //simpleregistry.register(Dimension.THE_NETHER, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.THE_NETHER), new NoiseChunkGenerator(NetherBiomeProvider.Preset.DEFAULT_NETHER_PROVIDER_PRESET.build(registry, seed), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.NETHER))), Lifecycle.stable());
        
        simpleregistry.register(LevelStem.END, new LevelStem(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.END_LOCATION), new NoiseBasedChunkGenerator(new BetterEndBiomeProvider(registry, seed), seed, () -> dimensionSettings.getOrThrow(NoiseGeneratorSettings.END))), Lifecycle.stable());

        return simpleregistry;
    }

    @Override
    public WorldGenSettings createSettings(RegistryAccess dynamicRegistries, long seed, 
    		boolean generateStructures, boolean generateLoot, String generatorSettings) 
    {
        return new CustomSettings(seed, generateStructures, generateLoot, getDefaultSimpleRegistry(dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY), dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), seed));
    }

    public static class CustomSettings extends WorldGenSettings 
    {
        public CustomSettings(long seed, boolean generateFeatures, boolean bonusChest, MappedRegistry<LevelStem> dimensionSimpleRegistry) 
        {
            super(seed, generateFeatures, bonusChest, dimensionSimpleRegistry);
        }

        // This hides our custom world type from the world type selection screen.
        // So its not possible for a player to create a new world using this world type by normal means,
        // as this was added only to ensure integration with the Terraforged mod
        @Override
        public boolean isDebug() 
        {
            return true;
        }
    }

}
