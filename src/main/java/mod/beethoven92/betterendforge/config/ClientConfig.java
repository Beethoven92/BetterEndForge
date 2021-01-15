package mod.beethoven92.betterendforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig 
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;
    
    public static final ForgeConfigSpec.BooleanValue SKY_ENABLED;
    public static final ForgeConfigSpec.BooleanValue FOG_DENSITY_ENABLED;
    public static final ForgeConfigSpec.BooleanValue BIOME_MUSIC_BLEND_ENABLED;
    
    static 
    {
        BUILDER.push("Client settings");
       
        BUILDER.comment("Enable/disable better end sky, set this to false if you are experiencing graphical issues with shaders.");
        SKY_ENABLED = BUILDER.define("customSkyEnabled", true);

        BUILDER.comment("Enable/disable biome fog density, set this to false if you don't want thick fog in certain biomes");
        FOG_DENSITY_ENABLED = BUILDER.define("fogDensityEnabled", true);
        
        BUILDER.comment("Enable/disable background music blending between biomes");
        BIOME_MUSIC_BLEND_ENABLED = BUILDER.define("biomeMusicBlendEnabled", true);
        
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
    
    public static boolean isFogDensityEnabled() 
    {
        return FOG_DENSITY_ENABLED.get();
    }
    
    public static boolean shouldBlendBiomeMusic() 
    {
        return BIOME_MUSIC_BLEND_ENABLED.get();
    }
}
