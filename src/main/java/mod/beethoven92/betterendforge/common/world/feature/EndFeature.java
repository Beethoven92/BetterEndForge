package mod.beethoven92.betterendforge.common.world.feature;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class EndFeature {
    private Feature<?> feature;
    private ConfiguredFeature<?, ?> featureConfigured;

    private GenerationStage.Decoration featureStep;

    protected EndFeature() {}

    public EndFeature(Feature<?> feature, ConfiguredFeature<?, ?> configuredFeature, GenerationStage.Decoration featureStep) {
        this.featureStep = featureStep;
        this.feature = feature;
        this.featureConfigured = configuredFeature;
    }

    public EndFeature(String name, Feature<NoFeatureConfig> feature, GenerationStage.Decoration featureStep, ConfiguredFeature<?, ?> configuredFeature) {
        ResourceLocation id = BetterEnd.makeID(name);
        this.featureStep = featureStep;
        this.feature = Registry.register(Registry.FEATURE, id, feature);
        this.featureConfigured = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public EndFeature(String name, Feature<NoFeatureConfig> feature) {
        ResourceLocation id = BetterEnd.makeID(name);
        this.featureStep = GenerationStage.Decoration.VEGETAL_DECORATION;
        this.feature = Registry.register(Registry.FEATURE, id, feature);
        this.featureConfigured = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, id, feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE.configure(new ChanceConfig(100))));
    }

    public EndFeature(String name, Feature<NoFeatureConfig> feature, int density) {
        ResourceLocation id = BetterEnd.makeID(name);
        this.featureStep = GenerationStage.Decoration.VEGETAL_DECORATION;
        this.feature = Registry.register(Registry.FEATURE, id, feature);
        this.featureConfigured = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, id, feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(density));
    }


    public static EndFeature makeChunkFeature(String name, Feature<NoFeatureConfig> feature) {
        ConfiguredFeature<?, ?> configured = feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT.configure(new FeatureSpreadConfig(1)));
        return new EndFeature(name, feature, GenerationStage.Decoration.LOCAL_MODIFICATIONS, configured);
    }
}
