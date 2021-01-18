package mod.beethoven92.betterendforge.config.jsons;

public class JsonEntryConfig extends JsonIdConfig
{
	public JsonEntryConfig(String group) 
	{
		super(group, (id, entry) -> {
			return new JsonConfigKey(entry, id);
		});
	}
}
