package mod.beethoven92.betterendforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

// TEMPORARY MEASURE TO SOLVE SHADERS ISSUE WHEN RENDERING THE CUSTOM SKY
public class ClientConfig 
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;
    
    public static final ForgeConfigSpec.BooleanValue SKY_ENABLED;
    
    static 
    {
        BUILDER.push("Settings");
        SKY_ENABLED = BUILDER.comment("Enable/disable better end sky. Set this to false if you are experiencing "
        		+ "issues with shaders (temporary solution)").define("enabled", true);
        BUILDER.pop();
        
        CLIENT_CONFIG = BUILDER.build();
    }
    
    public static ForgeConfigSpec getConfig() 
    {
        return CLIENT_CONFIG;
    }
    
    public static boolean shouldCustomSkyRender() 
    {
        return SKY_ENABLED.get();
    }
}
