package mod.beethoven92.betterendforge.config.jsons;

public class JsonEntryConfig extends JsonIdConfig
{
	public JsonEntryConfig(String modID, String group) {
		super(modID, group, (id, entry) -> {
			return new JsonConfigKey(entry, id);
		});
	}
}
