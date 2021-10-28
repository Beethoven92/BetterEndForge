package mod.beethoven92.betterendforge.common.init;

import com.google.common.collect.ImmutableMap;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.structure.*;
import mod.beethoven92.betterendforge.common.world.structure.piece.CavePiece;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.Collection;
import java.util.function.Supplier;

public class ModStructures 
{

	public static final Structure<NoFeatureConfig> MOUNTAIN = new MountainStructure(NoFeatureConfig.field_236558_a_);
	public static final Structure<NoFeatureConfig> MEGALAKE = new MegaLakeStructure(NoFeatureConfig.field_236558_a_);
	public static final Structure<NoFeatureConfig> MEGALAKE_SMALL = new MegaLakeStructure(NoFeatureConfig.field_236558_a_);
	public static final Structure<NoFeatureConfig> GIANT_MOSSY_GLOWSHROOM = new GiantMossyGlowshroomStructure(NoFeatureConfig.field_236558_a_);
	public static final Structure<NoFeatureConfig> PAINTED_MOUNTAIN = new PaintedMountainStructure(NoFeatureConfig.field_236558_a_);
	public static final Structure<NoFeatureConfig> ETERNAL_PORTAL = new EternalPortalStructure(NoFeatureConfig.field_236558_a_);
	public static final Structure<NoFeatureConfig> GIANT_ICE_STAR = new GiantIceStarStructure(NoFeatureConfig.field_236558_a_);

	public static void registerStructures(Register<Structure<?>> event)
    {
    	BetterEnd.register(event.getRegistry(), MOUNTAIN, "mountain_structure");
    	BetterEnd.register(event.getRegistry(), MEGALAKE, "megalake_structure");
    	BetterEnd.register(event.getRegistry(), MEGALAKE_SMALL, "megalake_small_structure");
    	BetterEnd.register(event.getRegistry(), GIANT_MOSSY_GLOWSHROOM, "giant_mossy_glowshroom_structure");
    	BetterEnd.register(event.getRegistry(), PAINTED_MOUNTAIN, "painted_mountain_structure");
    	BetterEnd.register(event.getRegistry(), ETERNAL_PORTAL, "eternal_portal_structure");
    	BetterEnd.register(event.getRegistry(), GIANT_ICE_STAR, "giant_ice_star_structure");

    	setupStructure(MOUNTAIN, new StructureSeparationSettings(3, 2, 1234567890));
    	setupStructure(MEGALAKE, new StructureSeparationSettings(4, 1, 1237890));
    	setupStructure(MEGALAKE_SMALL, new StructureSeparationSettings(4, 1, 1223462190));
    	setupStructure(GIANT_MOSSY_GLOWSHROOM, new StructureSeparationSettings(16, 8, 1234560));
    	setupStructure(PAINTED_MOUNTAIN, new StructureSeparationSettings(3, 2, 12890));
    	setupStructure(ETERNAL_PORTAL, new StructureSeparationSettings(16, 6, 1289052454));
    	setupStructure(GIANT_ICE_STAR, new StructureSeparationSettings(16, 8, 128954));

        ModStructurePieces.registerAllPieces();
    }
    
    public static <F extends Structure<?>> void setupStructure(F structure,
    		StructureSeparationSettings structureSeparationSettings)
    {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }

}
