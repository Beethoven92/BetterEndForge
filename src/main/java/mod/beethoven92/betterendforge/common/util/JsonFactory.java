package mod.beethoven92.betterendforge.common.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.BetterEnd;

public class JsonFactory 
{
	public final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static JsonObject getJsonObject(InputStream stream) 
	{
		try 
		{
			Reader reader = new InputStreamReader(stream);
			JsonObject jsonObject = loadJson(reader).getAsJsonObject();
			if (jsonObject == null) 
			{
				return new JsonObject();
			}
			return jsonObject;
		} catch (Exception ex) 
		{
			BetterEnd.LOGGER.catching(ex);
			return new JsonObject();
		}
	}
	
	public static JsonElement loadJson(Reader reader)
	{
		return GSON.fromJson(reader, JsonElement.class);
	}
	
	public static float getFloat(JsonObject object, String member, float def) 
	{
		JsonElement elem = object.get(member);
		return elem == null ? def : elem.getAsFloat();
	}
	
	public static boolean getBoolean(JsonObject object, String member, boolean def) 
	{
		JsonElement elem = object.get(member);
		return elem == null ? def : elem.getAsBoolean();
	}
	
	public static String getString(JsonObject object, String member, String def) 
	{
		JsonElement elem = object.get(member);
		return elem == null ? def : elem.getAsString();
	}
}
