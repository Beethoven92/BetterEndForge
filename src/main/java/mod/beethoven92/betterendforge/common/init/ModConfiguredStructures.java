package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures 
{
	public static final StructureFeature<?, ?> MOUNTAIN_STRUCTURE = ModStructures.MOUNTAIN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static final StructureFeature<?, ?> MEGALAKE_STRUCTURE = ModStructures.MEGALAKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static final StructureFeature<?, ?> GIANT_MOSSY_GLOWSHROOM = ModStructures.GIANT_MOSSY_GLOWSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static final StructureFeature<?, ?> PAINTED_MOUNTAIN = ModStructures.PAINTED_MOUNTAIN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static final StructureFeature<?, ?> ETERNAL_PORTAL = ModStructures.ETERNAL_PORTAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    
    public static void registerConfiguredStructures() 
	{
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "mountain_structure"), MOUNTAIN_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "megalake_structure"), MEGALAKE_STRUCTURE);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "giant_mossy_glowshroom_structure"), GIANT_MOSSY_GLOWSHROOM);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "painted_mountain_structure"), PAINTED_MOUNTAIN);
        Registry.register(registry, new ResourceLocation(BetterEnd.MOD_ID, "eternal_portal_structure"), ETERNAL_PORTAL);
	}   
}
