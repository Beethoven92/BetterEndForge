package mod.beethoven92.betterendforge.common.lootmodifier.lootcondition;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

public class LootConditions {
	
	protected static LootItemConditionType FROM_LOOT_TABLE;


	public static void register() {
		FROM_LOOT_TABLE = Registry.register(Registry.LOOT_CONDITION_TYPE,
				new ResourceLocation(BetterEnd.MOD_ID, "from_loot_table"),
				new LootItemConditionType(new FromLootTable.Serializer()));

	}
}
