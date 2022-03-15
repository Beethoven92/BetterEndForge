package mod.beethoven92.betterendforge.common.inventory.slot;

import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SmelterOutputSlot extends Slot
{
	private Player player;
	private int amount;

	public SmelterOutputSlot(Player player, Container inventoryIn, int index, int xPosition, int yPosition) 
	{
		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
	}	
	
	@Override
	public boolean mayPlace(ItemStack stack) 
	{
		return false;
	}
	
	@Override
	public ItemStack remove(int amount)
	{
		if (this.hasItem()) 
		{
			this.amount += Math.min(amount, this.getItem().getCount());
		}

		return super.remove(amount);
	}
	
	@Override
	public ItemStack onTake(Player thePlayer, ItemStack stack) 
	{
		this.checkTakeAchievements(stack);
		super.onTake(player, stack);
		return stack;
	}
	
	@Override
	protected void onQuickCraft(ItemStack stack, int amount) 
	{
		this.amount += amount;
		this.checkTakeAchievements(stack);
	}
	
	@Override
	protected void checkTakeAchievements(ItemStack stack) 
	{
		stack.onCraftedBy(this.player.level, this.player, this.amount);
		if (!this.player.level.isClientSide && this.container instanceof EndStoneSmelterTileEntity) 
		{
			((EndStoneSmelterTileEntity) this.container).dropExperience(player);
		}
		this.amount = 0;
	}
}
