package mod.beethoven92.betterendforge.common.init;

import com.google.common.collect.ImmutableMap;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.structure.*;
import mod.beethoven92.betterendforge.common.world.structure.piece.CavePiece;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.Collection;
import java.util.function.Supplier;

public class ModStructures 
{

	public static final StructureFeature<NoneFeatureConfiguration> MOUNTAIN = new MountainStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> MEGALAKE = new MegaLakeStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> MEGALAKE_SMALL = new MegaLakeStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> GIANT_MOSSY_GLOWSHROOM = new GiantMossyGlowshroomStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> PAINTED_MOUNTAIN = new PaintedMountainStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> ETERNAL_PORTAL = new EternalPortalStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> GIANT_ICE_STAR = new GiantIceStarStructure(NoneFeatureConfiguration.CODEC);

	public static void registerStructures(Register<StructureFeature<?>> event)
    {
    	BetterEnd.register(event.getRegistry(), MOUNTAIN, "mountain_structure");
    	BetterEnd.register(event.getRegistry(), MEGALAKE, "megalake_structure");
    	BetterEnd.register(event.getRegistry(), MEGALAKE_SMALL, "megalake_small_structure");
    	BetterEnd.register(event.getRegistry(), GIANT_MOSSY_GLOWSHROOM, "giant_mossy_glowshroom_structure");
    	BetterEnd.register(event.getRegistry(), PAINTED_MOUNTAIN, "painted_mountain_structure");
    	BetterEnd.register(event.getRegistry(), ETERNAL_PORTAL, "eternal_portal_structure");
    	BetterEnd.register(event.getRegistry(), GIANT_ICE_STAR, "giant_ice_star_structure");

    	setupStructure(MOUNTAIN, new StructureFeatureConfiguration(3, 2, 1234567890));
    	setupStructure(MEGALAKE, new StructureFeatureConfiguration(4, 1, 1237890));
    	setupStructure(MEGALAKE_SMALL, new StructureFeatureConfiguration(4, 1, 1223462190));
    	setupStructure(GIANT_MOSSY_GLOWSHROOM, new StructureFeatureConfiguration(16, 8, 1234560));
    	setupStructure(PAINTED_MOUNTAIN, new StructureFeatureConfiguration(3, 2, 12890));
    	setupStructure(ETERNAL_PORTAL, new StructureFeatureConfiguration(16, 6, 1289052454));
    	setupStructure(GIANT_ICE_STAR, new StructureFeatureConfiguration(16, 8, 128954));

        ModStructurePieces.registerAllPieces();
    }
    
    public static <F extends StructureFeature<?>> void setupStructure(F structure,
    		StructureFeatureConfiguration structureSeparationSettings)
    {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureSeparationSettings)
                        .build();
    }

}
