package mod.beethoven92.betterendforge.data;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModLootModifiers;
import mod.beethoven92.betterendforge.common.lootmodifier.BetterEndMusicDiscLootModifier;
import mod.beethoven92.betterendforge.common.lootmodifier.lootcondition.FromLootTable;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

	public ModLootModifierProvider(DataGenerator gen) {
		super(gen, BetterEnd.MOD_ID);
	}

	@Override
	protected void start() {
		add("better_end_music_disc", ModLootModifiers.BETTER_END_MUSIC_DISC,
				new BetterEndMusicDiscLootModifier(new LootItemCondition[] { LootItemRandomChanceCondition.randomChance(0.1f).build(),
						new FromLootTable(BuiltInLootTables.END_CITY_TREASURE) }, 0, 5));
	}

}
