package mod.beethoven92.betterendforge.common.world.feature;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndFeature {
    private Feature<?> feature;
    private ConfiguredFeature<?, ?> featureConfigured;

    private GenerationStep.Decoration featureStep;

    protected EndFeature() {}

    public EndFeature(Feature<?> feature, ConfiguredFeature<?, ?> configuredFeature, GenerationStep.Decoration featureStep) {
        this.featureStep = featureStep;
        this.feature = feature;
        this.featureConfigured = configuredFeature;
    }

    public EndFeature(String name, Feature<NoneFeatureConfiguration> feature, GenerationStep.Decoration featureStep, ConfiguredFeature<?, ?> configuredFeature) {
        ResourceLocation id = BetterEnd.makeID(name);
        this.featureStep = featureStep;
        this.feature = Registry.register(Registry.FEATURE, id, feature);
        this.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public EndFeature(String name, Feature<NoneFeatureConfiguration> feature) {
        ResourceLocation id = BetterEnd.makeID(name);
        this.featureStep = GenerationStep.Decoration.VEGETAL_DECORATION;
        this.feature = Registry.register(Registry.FEATURE, id, feature);
        this.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, feature.configured(FeatureConfiguration.NONE).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(100))));
    }

    public EndFeature(String name, Feature<NoneFeatureConfiguration> feature, int density) {
        ResourceLocation id = BetterEnd.makeID(name);
        this.featureStep = GenerationStep.Decoration.VEGETAL_DECORATION;
        this.feature = Registry.register(Registry.FEATURE, id, feature);
        this.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, feature.configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).chance(density));
    }


    public static EndFeature makeChunkFeature(String name, Feature<NoneFeatureConfiguration> feature) {
        ConfiguredFeature<?, ?> configured = feature.configured(FeatureConfiguration.NONE).decorated(FeatureDecorator.COUNT.configured(new CountConfiguration(1)));
        return new EndFeature(name, feature, GenerationStep.Decoration.LOCAL_MODIFICATIONS, configured);
    }
}
