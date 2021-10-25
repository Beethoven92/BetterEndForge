package mod.beethoven92.betterendforge.config;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.config.jsons.JsonEntryConfig;
import mod.beethoven92.betterendforge.config.jsons.JsonIdConfig;
import mod.beethoven92.betterendforge.config.jsons.JsonPathConfig;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
//import ru.bclib.BCLib;
//import ru.bclib.config.EntryConfig;
//import ru.bclib.config.IdConfig;
//import ru.bclib.config.PathConfig;
//import ru.betterend.BetterEnd;

public class Configs {
	public static final JsonPathConfig ENTITY_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "entities");
	public static final JsonPathConfig BLOCK_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "blocks");
	public static final JsonPathConfig ITEM_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "items");
	public static final JsonIdConfig BIOME_CONFIG = new JsonEntryConfig(BetterEnd.MOD_ID, "biomes");
	public static final JsonPathConfig GENERATOR_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "generator", false);
	public static final JsonPathConfig RECIPE_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "recipes");

	public static final JsonPathConfig CLIENT_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "client", false);
	
	public static void saveConfigs() {
		ENTITY_CONFIG.saveChanges();
		BLOCK_CONFIG.saveChanges();
		BIOME_CONFIG.saveChanges();
		ITEM_CONFIG.saveChanges();
		GENERATOR_CONFIG.saveChanges();
		RECIPE_CONFIG.saveChanges();

		if (BetterEnd.isClient()) {
			CLIENT_CONFIG.saveChanges();
		}
	}
}
