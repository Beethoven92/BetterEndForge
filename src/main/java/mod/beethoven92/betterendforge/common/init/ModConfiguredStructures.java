package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures 
{
	public static final StructureFeature<?, ?> MOUNTAIN_STRUCTURE = ModStructures.MOUNTAIN.configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> MEGALAKE_STRUCTURE = ModStructures.MEGALAKE.configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> MEGALAKE_SMALL_STRUCTURE = ModStructures.MEGALAKE_SMALL.configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> GIANT_MOSSY_GLOWSHROOM = ModStructures.GIANT_MOSSY_GLOWSHROOM.configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> PAINTED_MOUNTAIN = ModStructures.PAINTED_MOUNTAIN.configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> ETERNAL_PORTAL = ModStructures.ETERNAL_PORTAL.configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> GIANT_ICE_STAR = ModStructures.GIANT_ICE_STAR.configured(IFeatureConfig.NONE);
    public static void registerConfiguredStructures() 
	{
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "mountain_structure"), MOUNTAIN_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "megalake_structure"), MEGALAKE_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "megalake_small_structure"), MEGALAKE_SMALL_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "giant_mossy_glowshroom_structure"), GIANT_MOSSY_GLOWSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "painted_mountain_structure"), PAINTED_MOUNTAIN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "eternal_portal_structure"), ETERNAL_PORTAL);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "giant_ice_star_structure"), GIANT_ICE_STAR);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.MOUNTAIN, MOUNTAIN_STRUCTURE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.MEGALAKE, MEGALAKE_STRUCTURE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.MEGALAKE_SMALL, MEGALAKE_SMALL_STRUCTURE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.GIANT_MOSSY_GLOWSHROOM, GIANT_MOSSY_GLOWSHROOM);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.PAINTED_MOUNTAIN, PAINTED_MOUNTAIN);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.ETERNAL_PORTAL, ETERNAL_PORTAL);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.GIANT_ICE_STAR, GIANT_ICE_STAR);
	}   
}
