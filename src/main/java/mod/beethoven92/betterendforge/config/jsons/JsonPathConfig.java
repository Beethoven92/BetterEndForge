package mod.beethoven92.betterendforge.config.jsons;

//import org.jetbrains.annotations.Nullable;
//import ru.bclib.config.ConfigKeeper.Entry;
//import ru.bclib.config.ConfigKeeper.FloatRange;
//import ru.bclib.config.ConfigKeeper.IntegerRange;

import javax.annotation.Nullable;

public class JsonPathConfig extends JsonConfig {

    public JsonPathConfig(String modID, String group, boolean autoSync, boolean diffContent) {
        super(modID, group, autoSync, diffContent);
    }

    public JsonPathConfig(String modID, String group, boolean autoSync) {
        super(modID, group, autoSync);
    }

    public JsonPathConfig(String modID, String group) {
        super(modID, group);
    }

    @Override
    protected void registerEntries() {}

    protected static JsonConfigKey createKey(String category, String key) {
        return new JsonConfigKey(key, category.split("\\."));
    }

    protected static JsonConfigKey createKey(String key) {
        return createKey("", key);
    }

    @Nullable
    public <T, E extends JsonConfigKeeper.Entry<T>> E getEntry(String category, String key, Class<E> type) {
        return this.getEntry(createKey(category, key), type);
    }

    @Nullable
    public <T, E extends JsonConfigKeeper.Entry<T>> T getDefault(String category, String key, Class<E> type) {
        return this.getDefault(createKey(category, key), type);
    }

    public String getString(String category, String key, String defaultValue) {
        return this.getString(createKey(category, key), defaultValue);
    }

    public String getString(String category, String key) {
        return this.getString(createKey(category, key));
    }

    public boolean setString(String category, String key, String value) {
        return this.setString(createKey(category, key), value);
    }

    public int getInt(String category, String key, int defaultValue) {
        return this.getInt(createKey(category, key), defaultValue);
    }

    public int getInt(String category, String key) {
        return this.getInt(createKey(category, key));
    }

    public boolean setInt(String category, String key, int value) {
        return this.setInt(createKey(category, key), value);
    }

    public boolean setRangedInt(String category, String key, int value) {
        return this.setRanged(createKey(category, key), value, JsonConfigKeeper.IntegerRange.class);
    }

    public boolean setRangedFloat(String category, String key, float value) {
        return this.setRanged(createKey(category, key), value, JsonConfigKeeper.FloatRange.class);
    }

    public float getFloat(String category, String key, float defaultValue) {
        return this.getFloat(createKey(category, key), defaultValue);
    }

    public float getFloat(String category, String key) {
        return this.getFloat(createKey(category, key));
    }

    public boolean setFloat(String category, String key, float value) {
        return this.setFloat(createKey(category, key), value);
    }

    public boolean getBoolean(String category, String key, boolean defaultValue) {
        return this.getBoolean(createKey(category, key), defaultValue);
    }

    public boolean getBoolean(String category, String key) {
        return this.getBoolean(createKey(category, key));
    }

    public boolean setBoolean(String category, String key, boolean value) {
        return this.setBoolean(createKey(category, key), value);
    }

    // From Root

    public String getStringRoot(String key, String defaultValue) {
        return this.getString(createKey(key), defaultValue);
    }

    public String getStringRoot(String key) {
        return this.getString(createKey(key));
    }

    public boolean setStringRoot(String key, String value) {
        return this.setString(createKey(key), value);
    }

    public int getIntRoot(String key, int defaultValue) {
        return this.getInt(createKey(key), defaultValue);
    }

    public int getIntRoot(String key) {
        return this.getInt(createKey(key));
    }

    public boolean setIntRoot(String key, int value) {
        return this.setInt(createKey(key), value);
    }

    public boolean setRangedIntRoot(String key, int value) {
        return this.setRanged(createKey(key), value, JsonConfigKeeper.IntegerRange.class);
    }

    public boolean setRangedFloatRoot(String key, float value) {
        return this.setRanged(createKey(key), value, JsonConfigKeeper.FloatRange.class);
    }

    public float getFloatRoot(String key, float defaultValue) {
        return this.getFloat(createKey(key), defaultValue);
    }

    public float getFloatRoot(String key) {
        return this.getFloat(createKey(key));
    }

    public boolean setFloatRoot(String key, float value) {
        return this.setFloat(createKey(key), value);
    }

    public boolean getBooleanRoot(String key, boolean defaultValue) {
        return this.getBoolean(createKey(key), defaultValue);
    }

    public boolean getBooleanRoot(String key) {
        return this.getBoolean(createKey(key));
    }

    public boolean setBooleanRoot(String key, boolean value) {
        return this.setBoolean(createKey(key), value);
    }
}