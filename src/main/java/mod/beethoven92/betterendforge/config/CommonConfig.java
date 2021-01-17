package mod.beethoven92.betterendforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig 
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
   
    public static final ForgeConfigSpec.BooleanValue HAS_DRAGON_FIGHT;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VANILLA_PORTAL;
    public static final ForgeConfigSpec.BooleanValue GENERATE_OBSIDIAN_PILLARS;
    
    public static final ForgeConfigSpec.BooleanValue NEW_GENERATOR;
    public static final ForgeConfigSpec.BooleanValue NO_RING_VOID;
    public static final ForgeConfigSpec.BooleanValue GENERATE_CENTRAL_ISLAND;
    
    static 
    {
        BUILDER.push("Common settings");
       
        BUILDER.comment("enable/disable dragon fight [default: true]");
        HAS_DRAGON_FIGHT = BUILDER.define("hasDragonFight", true);

        BUILDER.comment("enable/disable vanilla portal generation [default: true]");
        GENERATE_VANILLA_PORTAL = BUILDER.define("generateVanillaPortal", true);
        
        BUILDER.comment("enable/disable obsidian pillars generation [default: true]");
        GENERATE_OBSIDIAN_PILLARS = BUILDER.define("generateObsidianPillars", true);
        
        BUILDER.comment("enable/disable new terrain generation [default: false]");
        NEW_GENERATOR = BUILDER.define("newGenerator", false);

        BUILDER.comment("Enable/disable the void ring around central island [default: false]");
        NO_RING_VOID = BUILDER.define("noRingVoid", false);
        
        BUILDER.comment("Enable/disable generation of the main island [default: false]");
        GENERATE_CENTRAL_ISLAND = BUILDER.define("generateCentralIsland", false);
        
        BUILDER.pop();
        
        COMMON_CONFIG = BUILDER.build();
    }
    
    public static ForgeConfigSpec getConfig() 
    {
        return COMMON_CONFIG;
    }
    
    public static boolean hasDragonFight()
    {
    	return HAS_DRAGON_FIGHT.get();
    }
    
    public static boolean generateVanillaPortal()
    {
    	return GENERATE_VANILLA_PORTAL.get();
    }
    
    public static boolean generateObsidianPillars()
    {
    	return GENERATE_OBSIDIAN_PILLARS.get();
    }
    
    public static boolean newGenerator() 
    {
        return NEW_GENERATOR.get();
    }
    
    public static boolean noRingVoid() 
    {
        return NO_RING_VOID.get();
    }
    
    public static boolean generateCentralIsland() 
    {
        return GENERATE_CENTRAL_ISLAND.get();
    }
}
