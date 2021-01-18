package mod.beethoven92.betterendforge.config.jsons;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigKeeper.BooleanEntry;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigKeeper.Entry;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigKeeper.FloatEntry;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigKeeper.IntegerEntry;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigKeeper.RangeEntry;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigKeeper.StringEntry;

public abstract class JsonConfig 
{
	protected final JsonConfigKeeper keeper;
	
	protected abstract void registerEntries();
	
	public JsonConfig(String group) 
	{
		this.keeper = new JsonConfigKeeper(group);
		this.registerEntries();
	}
	
	public void saveChanges() 
	{
		this.keeper.save();
	}
	
	@Nullable
	public <T, E extends Entry<T>> E getEntry(JsonConfigKey key, Class<E> type) 
	{
		return this.keeper.getEntry(key, type);
	}
	
	@Nullable
	public <T, E extends Entry<T>> T getDefault(JsonConfigKey key, Class<E> type) 
	{
		Entry<T> entry = keeper.getEntry(key, type);
		return entry != null ? entry.getDefault() : null;
	}
	
	protected String getString(JsonConfigKey key, String defaultValue) 
	{
		String str = keeper.getValue(key, StringEntry.class);
		if (str == null) {
			StringEntry entry = keeper.registerEntry(key, new StringEntry(defaultValue));
			return entry.getValue();
		}
		return str != null ? str : defaultValue;
	}
	
	protected String getString(JsonConfigKey key)
	{
		String str = keeper.getValue(key, StringEntry.class);
		return str != null ? str : "";
	}
	
	protected boolean setString(JsonConfigKey key, String value) 
	{
		try 
		{
			StringEntry entry = keeper.getEntry(key, StringEntry.class);
			if (entry == null) return false;
			entry.setValue(value);
			return true;
		} 
		catch (NullPointerException ex) 
		{
			BetterEnd.LOGGER.catching(ex);
		}
		return false;
	}
	
	protected int getInt(JsonConfigKey key, int defaultValue) 
	{
		Integer val = keeper.getValue(key, IntegerEntry.class);		
		if (val == null) 
		{
			IntegerEntry entry = keeper.registerEntry(key, new IntegerEntry(defaultValue));
			return entry.getValue();
		}
		return val != null ? val : defaultValue;
	}
	
	protected int getInt(JsonConfigKey key) 
	{
		Integer val = keeper.getValue(key, IntegerEntry.class);		
		return val != null ? val : 0;
	}
	
	protected boolean setInt(JsonConfigKey key, int value) 
	{
		try
		{
			IntegerEntry entry = keeper.getEntry(key, IntegerEntry.class);
			if (entry == null) return false;
			entry.setValue(value);
			return true;
		} 
		catch (NullPointerException ex) 
		{
			BetterEnd.LOGGER.catching(ex);
		}
		return false;
	}
	
	protected <T extends Comparable<T>, RE extends RangeEntry<T>> boolean setRanged(JsonConfigKey key, T value, Class<RE> type)
	{
		try 
		{
			RangeEntry<T> entry = keeper.getEntry(key, type);
			if (entry == null) return false;
			entry.setValue(value);
			return true;
		} 
		catch (NullPointerException | ClassCastException ex) 
		{
			BetterEnd.LOGGER.catching(ex);
		}
		return false;
	}
	
	protected float getFloat(JsonConfigKey key, float defaultValue)
	{
		Float val = keeper.getValue(key, FloatEntry.class);
		if (val == null) 
		{
			FloatEntry entry = keeper.registerEntry(key, new FloatEntry(defaultValue));
			return entry.getValue();
		}
		return val;
	}
	
	protected float getFloat(JsonConfigKey key) 
	{
		Float val = keeper.getValue(key, FloatEntry.class);
		return val != null ? val : 0.0F;
	}
	
	protected boolean setFloat(JsonConfigKey key, float value) 
	{
		try 
		{
			FloatEntry entry = keeper.getEntry(key, FloatEntry.class);
			if (entry == null) return false;
			entry.setValue(value);
			return true;
		} 
		catch (NullPointerException ex)
		{
			BetterEnd.LOGGER.catching(ex);
		}
		return false;
	}
	
	protected boolean getBoolean(JsonConfigKey key, boolean defaultValue)
	{
		Boolean val = keeper.getValue(key, BooleanEntry.class);
		if (val == null)
		{
			BooleanEntry entry = keeper.registerEntry(key, new BooleanEntry(defaultValue));
			return entry.getValue();
		}
		return val;
	}
	
	protected boolean getBoolean(JsonConfigKey key) 
	{
		Boolean val = keeper.getValue(key, BooleanEntry.class);
		return val != null ? val : false;
	}
	
	protected boolean setBoolean(JsonConfigKey key, boolean value)
	{
		try 
		{
			BooleanEntry entry = keeper.getEntry(key, BooleanEntry.class);
			if (entry == null) return false;
			entry.setValue(value);
			return true;
		} 
		catch (NullPointerException ex) 
		{
			BetterEnd.LOGGER.catching(ex);
		}
		return false;
	}
}
