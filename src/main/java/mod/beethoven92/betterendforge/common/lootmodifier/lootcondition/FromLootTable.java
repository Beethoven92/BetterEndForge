package mod.beethoven92.betterendforge.common.lootmodifier.lootcondition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

public class FromLootTable implements LootItemCondition {

	private ResourceLocation table;
	
	public FromLootTable(ResourceLocation table) {
		this.table = table;
	}

	@Override
	public boolean test(LootContext t) {
		return t.getQueriedLootTableId().equals(table);
	}

	@Override
	public LootItemConditionType getType() {
		return LootConditions.FROM_LOOT_TABLE;
	}

	public static class Serializer implements Serializer<FromLootTable> {
		public void serialize(JsonObject json, FromLootTable instance, JsonSerializationContext context) {
			json.addProperty("loot_table", instance.table.toString());
		}

		public FromLootTable deserialize(JsonObject json, JsonDeserializationContext context) {
			ResourceLocation table = new ResourceLocation(GsonHelper.getAsString(json, "loot_table"));
			return new FromLootTable(table);
		}
	}

}
