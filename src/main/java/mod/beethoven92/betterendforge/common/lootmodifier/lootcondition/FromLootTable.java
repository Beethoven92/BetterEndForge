package mod.beethoven92.betterendforge.common.lootmodifier.lootcondition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class FromLootTable implements ILootCondition {

	private ResourceLocation table;
	
	public FromLootTable(ResourceLocation table) {
		this.table = table;
	}

	@Override
	public boolean test(LootContext t) {
		return t.getQueriedLootTableId().equals(table);
	}

	@Override
	public LootConditionType func_230419_b_() {
		return LootConditions.FROM_LOOT_TABLE;
	}

	public static class Serializer implements ILootSerializer<FromLootTable> {
		public void serialize(JsonObject json, FromLootTable instance, JsonSerializationContext context) {
			json.addProperty("loot_table", instance.table.toString());
		}

		public FromLootTable deserialize(JsonObject json, JsonDeserializationContext context) {
			ResourceLocation table = new ResourceLocation(JSONUtils.getString(json, "loot_table"));
			return new FromLootTable(table);
		}
	}

}
