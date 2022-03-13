package mod.beethoven92.betterendforge.config.jsons;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.common.util.JsonFactory;
import net.minecraft.util.JSONUtils;

public final class JsonConfigKeeper 
{
	private final Map<JsonConfigKey, Entry<?>> configEntries = Maps.newHashMap();
	
	private final JsonObject configObject;
	private final JsonConfigWriter writer;
	
	private boolean changed = false;
	
	public JsonConfigKeeper(String group)
	{
		this.writer = new JsonConfigWriter(group);
		this.configObject = writer.load();
	}
	
	public void save() 
	{
		if (!changed) return;
		this.writer.save();
		this.changed = false;
	}
	
	private <T, E extends Entry<T>> void initializeEntry(JsonConfigKey key, E entry) 
	{
		if (configObject == null) 
		{
			return;
		}
		String[] path = key.getPath();
		JsonObject obj = configObject;
		
		if (!key.isRoot()) 
		{
			for (String group: path) 
			{
				JsonElement element = obj.get(group);
				if (element == null || !element.isJsonObject())
				{
					element = new JsonObject();
					obj.add(group, element);
				}
				obj = element.getAsJsonObject();
			}
		}
		
		String paramKey = key.getEntry();
		paramKey += " [default: " + entry.getDefault() + "]";
		
		this.changed |= entry.setLocation(obj, paramKey);
	}
	
	private <T, E extends Entry<T>> void storeValue(E entry, T value) 
	{
		if (configObject == null) 
		{
			return;
		}
		T val = entry.getValue();
		if (value.equals(val)) return;
		entry.toJson(value);
		this.changed = true;
	}
	
	private <T, E extends Entry<T>> T getValue(E entry) 
	{
		if (!entry.hasLocation()) 
		{
			return entry.getDefault();
		}
		return entry.fromJson();
	}
	
	@Nullable
	public <T, E extends Entry<T>> E getEntry(JsonConfigKey key, Class<E> type) 
	{
		Entry<?> entry = this.configEntries.get(key);
		if (type.isInstance(entry)) 
		{
			return type.cast(entry);
		}
		return null;
	}
	
	@Nullable
	public <T, E extends Entry<T>> T getValue(JsonConfigKey key, Class<E> type) 
	{
		Entry<T> entry = this.getEntry(key, type);
		if (entry == null) 
		{
			return null;
		}
		return entry.getValue();
	}

	public <T, E extends Entry<T>> E registerEntry(JsonConfigKey key, E entry) 
	{
		entry.setWriter(value -> this.storeValue(entry, value));
		entry.setReader(() -> { return this.getValue(entry); });
		this.initializeEntry(key, entry);
		this.configEntries.put(key, entry);
		return entry;
	}
	
	public static class BooleanEntry extends Entry<Boolean> 
	{
		public BooleanEntry(Boolean defaultValue) {
			super(defaultValue);
		}

		@Override
		public Boolean fromJson() 
		{
			return JSONUtils.getAsBoolean(location, key, defaultValue);
		}

		@Override
		public void toJson(Boolean value)
		{
			this.location.addProperty(key, value);
		}
	}
	
	public static class FloatEntry extends Entry<Float> 
	{
		public FloatEntry(Float defaultValue) 
		{
			super(defaultValue);
		}

		@Override
		public Float fromJson() 
		{
			return JSONUtils.getAsFloat(location, key, defaultValue);
		}

		@Override
		public void toJson(Float value) 
		{
			this.location.addProperty(key, value);
		}
	}
	
	public static class FloatRange extends RangeEntry<Float> 
	{
		public FloatRange(Float defaultValue, float minVal, float maxVal) 
		{
			super(defaultValue, minVal, maxVal);
		}

		@Override
		public Float fromJson() 
		{
			return JSONUtils.getAsFloat(location, key, defaultValue);
		}

		@Override
		public void toJson(Float value) 
		{
			this.location.addProperty(key, value);
		}
	}
	
	public static class IntegerEntry extends Entry<Integer> 
	{
		public IntegerEntry(Integer defaultValue) 
		{
			super(defaultValue);
		}

		@Override
		public Integer getDefault() 
		{
			return this.defaultValue;
		}

		@Override
		public Integer fromJson() 
		{
			return JSONUtils.getAsInt(location, key, defaultValue);
		}

		@Override
		public void toJson(Integer value) 
		{
			this.location.addProperty(key, value);
		}
	}
	
	public static class IntegerRange extends RangeEntry<Integer> 
	{
		public IntegerRange(Integer defaultValue, int minVal, int maxVal)
		{
			super(defaultValue, minVal, maxVal);
		}

		@Override
		public Integer fromJson() 
		{
			return JSONUtils.getAsInt(location, key, defaultValue);
		}

		@Override
		public void toJson(Integer value) 
		{
			this.location.addProperty(key, value);
		}
	}
	
	public static class StringEntry extends Entry<String> 
	{
		public StringEntry(String defaultValue) 
		{
			super(defaultValue);
		}

		@Override
		public String fromJson()
		{
			return JSONUtils.getAsString(location, key, defaultValue);
		}

		@Override
		public void toJson(String value) 
		{
			this.location.addProperty(key, value);
		}

	}
	
	public static class EnumEntry<T extends Enum<T>> extends Entry<T> 
	{
		private final Type type;
		
		public EnumEntry(T defaultValue) 
		{
			super(defaultValue);
			TypeToken<T> token = new TypeToken<T>()
			{
				private static final long serialVersionUID = 1L;
			};
			this.type = token.getType();
		}

		@Override
		public T getDefault() {
			return this.defaultValue;
		}

		@Override
		public T fromJson() 
		{
			return JsonFactory.GSON.fromJson(location.get(key), type);
		}

		@Override
		public void toJson(T value) 
		{
			location.addProperty(key, JsonFactory.GSON.toJson(value, type));
		}
	}
	
	public static abstract class RangeEntry<T extends Comparable<T>> extends Entry<T> 
	{
		private final T min, max;

		public RangeEntry(T defaultValue, T minVal, T maxVal)
		{
			super(defaultValue);
			this.min = minVal;
			this.max = maxVal;
		}

		@Override
		public void setValue(T value) 
		{
			super.setValue(value.compareTo(min) < 0 ? min : value.compareTo(max) > 0 ? max : value);
		}
		
		public T minValue()
		{
			return this.min;
		}
		
		public T maxValue() 
		{
			return this.max;
		}
	}
	
	public static abstract class Entry<T>
	{		
		protected final T defaultValue;
		protected Consumer<T> writer;
		protected Supplier<T> reader;
		protected JsonObject location;
		protected String key;
		
		public abstract T fromJson();
		public abstract void toJson(T value);
		
		public Entry (T defaultValue) 
		{
			this.defaultValue = defaultValue;
		}
		
		protected void setWriter(Consumer<T> writer) 
		{
			this.writer = writer;
		}
		
		protected void setReader(Supplier<T> reader) 
		{
			this.reader = reader;
		}
		
		protected boolean setLocation(JsonObject location, String key) 
		{
			this.location = location;
			this.key = key;
			if (!location.has(key)) 
			{
				this.toJson(defaultValue);
				return true;
			}
			return false;
		}
		
		protected boolean hasLocation() 
		{
			return this.location != null &&
				   this.key != null;
		}
		
		public T getValue() 
		{
			return this.reader.get();
		}
		
		public void setValue(T value) 
		{
			this.writer.accept(value);
		}
		
		public T getDefault() 
		{
			return this.defaultValue;
		}
		
		public void setDefault() 
		{
			this.setValue(defaultValue);
		}
	}
}
