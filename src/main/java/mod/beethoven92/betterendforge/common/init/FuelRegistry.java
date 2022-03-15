package mod.beethoven92.betterendforge.common.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;

import java.util.HashMap;
import java.util.Map;

public class FuelRegistry {

	private static Map<ItemLike, Integer> FUELS = new HashMap<>();
	
	public static void onFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
		ItemStack stack = event.getItemStack();
		ItemLike item = stack.getItem();
		if (FUELS.keySet().contains(item)) {
			event.setBurnTime(FUELS.get(item));
		}
	}
	
	public static void addFuel(ItemLike item, int burnTime) {
		FUELS.put(item, burnTime);
	}
	
}
