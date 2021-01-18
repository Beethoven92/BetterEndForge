package mod.beethoven92.betterendforge.config.jsons;

public class JsonConfigs 
{
	public static final JsonIdConfig BIOME_CONFIG = new JsonEntryConfig("biomes");
	
	public static void saveConfigs() 
	{
		BIOME_CONFIG.saveChanges();
	}
}
