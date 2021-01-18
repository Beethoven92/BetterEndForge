package mod.beethoven92.betterendforge.config.jsons;

import java.io.File;
import java.nio.file.Path;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.JsonFactory;
import net.minecraftforge.fml.loading.FMLPaths;

public class JsonConfigWriter 
{
	private final static Path GAME_CONFIG_DIR = new File(String.valueOf(FMLPaths.CONFIGDIR.get())).toPath();
	//public final static File MOD_CONFIG_DIR = new File(String.valueOf(FMLPaths.CONFIGDIR.get().resolve(BetterEnd.MOD_ID)));
	public final static File MOD_CONFIG_DIR = new File(GAME_CONFIG_DIR.toFile(), BetterEnd.MOD_ID);
	
	private final File configFile;
	private JsonObject configObject;
	
	static 
	{
		if (!MOD_CONFIG_DIR.exists()) 
		{
			MOD_CONFIG_DIR.mkdirs();
		}
	}
	
	public JsonConfigWriter(String configFile) 
	{
		this.configFile = new File(MOD_CONFIG_DIR, configFile + ".json");
		this.load();
	}
	
	public JsonObject getConfig()
	{
		return configObject;
	}
	
	public void save() 
	{
		if (configObject == null) 
		{
			return;
		}
		save(configFile, configObject);
	}
	
	public JsonObject load() 
	{
		if (configObject == null) 
		{
			configObject = load(configFile);
		}
		return configObject;
	}
	
	public void save(JsonElement config) 
	{
		this.configObject = config.getAsJsonObject();
		save(configFile, config);
	}
	
	public static JsonObject load(File configFile) 
	{
		return JsonFactory.getJsonObject(configFile);
	}
	
	public static void save(File configFile, JsonElement config) 
	{
		JsonFactory.storeJson(configFile, config);
	}

	public static String scrubFileName(String input) 
	{
		input = input.replaceAll("[/\\ ]+", "_");
		input = input.replaceAll("[,:&\"\\|\\<\\>\\?\\*]", "_");

		return input;
	}
}
