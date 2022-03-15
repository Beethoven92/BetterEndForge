package mod.beethoven92.betterendforge.common.inventory.slot;

import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SmelterFuelSlot extends Slot
{
	private final EndStoneSmelterContainer handler;
	
	public SmelterFuelSlot(EndStoneSmelterContainer handler, Container inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		
		this.handler = handler;
	}

	@Override
	public boolean mayPlace(ItemStack stack)
	{
		return this.handler.isFuel(stack) || FurnaceFuelSlot.isBucket(stack);
	}
	
	@Override
	public int getMaxStackSize(ItemStack stack)
	{
		return FurnaceFuelSlot.isBucket(stack) ? 1 : super.getMaxStackSize(stack);
	}
}
