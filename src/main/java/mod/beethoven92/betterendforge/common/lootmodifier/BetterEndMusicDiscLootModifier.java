package mod.beethoven92.betterendforge.common.lootmodifier;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class BetterEndMusicDiscLootModifier extends LootModifier {

	private static final List<Supplier<Item>> DISCS = ImmutableList.of(ModItems.MUSIC_DISC_ENDSEEKER,
			ModItems.MUSIC_DISC_EO_DRACONA, ModItems.MUSIC_DISC_GRASPING_AT_STARS,
			ModItems.MUSIC_DISC_STRANGE_AND_ALIEN);

	private int min, max;

	public BetterEndMusicDiscLootModifier(ILootCondition[] conditionsIn, int min, int max) {
		super(conditionsIn);
		this.min = min;
		this.max = max;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		Random rand = context.getRandom();
		int count = rand.nextInt(max - min) + min;
		for (int i = 0; i < count; i++)
			generatedLoot.add(DISCS.get(rand.nextInt(DISCS.size())).get().getDefaultInstance());
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<BetterEndMusicDiscLootModifier> {

		@Override
		public BetterEndMusicDiscLootModifier read(ResourceLocation name, JsonObject json,
				ILootCondition[] conditionsIn) {
			int min = JSONUtils.getInt(json, "min");
			int max = JSONUtils.getInt(json, "max");
			if (min >= max)
				throw new JsonSyntaxException("min has to be smaller than max");
			return new BetterEndMusicDiscLootModifier(conditionsIn, min, max);
		}

		@Override
		public JsonObject write(BetterEndMusicDiscLootModifier instance) {
			JsonObject json = makeConditions(instance.conditions);
			json.addProperty("min", instance.min);
			json.addProperty("max", instance.max);
			return json;
		}
	}

}
