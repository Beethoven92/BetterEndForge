package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class ModConfiguredStructures 
{
	public static final ConfiguredStructureFeature<?, ?> MOUNTAIN_STRUCTURE = ModStructures.MOUNTAIN.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> MEGALAKE_STRUCTURE = ModStructures.MEGALAKE.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> MEGALAKE_SMALL_STRUCTURE = ModStructures.MEGALAKE_SMALL.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> GIANT_MOSSY_GLOWSHROOM = ModStructures.GIANT_MOSSY_GLOWSHROOM.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> PAINTED_MOUNTAIN = ModStructures.PAINTED_MOUNTAIN.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> ETERNAL_PORTAL = ModStructures.ETERNAL_PORTAL.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> GIANT_ICE_STAR = ModStructures.GIANT_ICE_STAR.configured(FeatureConfiguration.NONE);
    public static void registerConfiguredStructures() 
	{
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "mountain_structure"), MOUNTAIN_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "megalake_structure"), MEGALAKE_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "megalake_small_structure"), MEGALAKE_SMALL_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "giant_mossy_glowshroom_structure"), GIANT_MOSSY_GLOWSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "painted_mountain_structure"), PAINTED_MOUNTAIN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "eternal_portal_structure"), ETERNAL_PORTAL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "giant_ice_star_structure"), GIANT_ICE_STAR);

        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.MOUNTAIN, MOUNTAIN_STRUCTURE);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.MEGALAKE, MEGALAKE_STRUCTURE);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.MEGALAKE_SMALL, MEGALAKE_SMALL_STRUCTURE);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.GIANT_MOSSY_GLOWSHROOM, GIANT_MOSSY_GLOWSHROOM);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.PAINTED_MOUNTAIN, PAINTED_MOUNTAIN);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.ETERNAL_PORTAL, ETERNAL_PORTAL);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(ModStructures.GIANT_ICE_STAR, GIANT_ICE_STAR);
	}   
}
