package mod.beethoven92.betterendforge.common.inventory;

import mod.beethoven92.betterendforge.common.init.ModContainerTypes;
import mod.beethoven92.betterendforge.common.inventory.slot.SmelterFuelSlot;
import mod.beethoven92.betterendforge.common.inventory.slot.SmelterOutputSlot;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndStoneSmelterContainer extends RecipeBookMenu<Container>
{
	private final Container inventory;
	private final ContainerData data;
	protected final Level world;
	
	public EndStoneSmelterContainer(int id, Inventory inventory, net.minecraft.network.FriendlyByteBuf extraData) 
	{
		this(id, inventory, new SimpleContainer(4), new SimpleContainerData(4));
	}
	
	public EndStoneSmelterContainer(int syncId, Inventory playerInventory, Container inventory, ContainerData data) 
	{
		super(ModContainerTypes.END_STONE_SMELTER.get(), syncId);
		this.inventory = inventory;
		this.data = data;
		this.world = playerInventory.player.level;
		
		this.addDataSlots(data);
		this.addSlot(new Slot(inventory, 0, 45, 17));
		this.addSlot(new Slot(inventory, 1, 67, 17));
		this.addSlot(new SmelterFuelSlot(this, inventory, 2, 56, 53));
		this.addSlot(new SmelterOutputSlot(playerInventory.player, inventory, 3, 129, 35));

		for(int i = 0; i < 3; ++i) 
		{
			for(int j = 0; j < 9; ++j) 
			{
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; ++i) 
		{
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void fillCraftSlotsStackedContents(StackedContents itemHelperIn) 
	{
		if (inventory instanceof StackedContentsCompatible) 
		{
			((StackedContentsCompatible) inventory).fillStackedContents(itemHelperIn);
		}
	}

	@Override
	public void clearCraftingContent()
	{	
		this.inventory.clearContent();
	}

	@Override
	public boolean recipeMatches(Recipe<? super Container> recipeIn) 
	{
		return recipeIn.matches(this.inventory, this.world);
	}

	@Override
	public int getResultSlotIndex() 
	{
		return 3;
	}

	@Override
	public int getGridWidth() 
	{
		return 2;
	}

	@Override
	public int getGridHeight() 
	{
		return 1;
	}

	@Override
	public int getSize() 
	{
		return 4;
	}

	@Override
	public RecipeBookType getRecipeBookType() 
	{
		return RecipeBookType.BLAST_FURNACE;
	}

	@Override
	public boolean stillValid(Player playerIn) 
	{
		return this.inventory.stillValid(playerIn);
	}

	protected boolean isSmeltable(ItemStack itemStack) 
	{
		return this.world.getRecipeManager().getRecipeFor(AlloyingRecipe.TYPE, new SimpleContainer(new ItemStack[]{itemStack}), this.world).isPresent();
	}

	public boolean isFuel(ItemStack itemStack) 
	{
		return EndStoneSmelterTileEntity.isFuel(itemStack);
	}
	
	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) 
	{
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) 
		{
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();
			if (index == 3) 
			{
				if (!this.moveItemStackTo(itemStack2, 4, 40, true)) 
				{
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(itemStack2, itemStack);
			} 
			else if (index != 2 && index != 1 && index != 0) 
			{
				if (isSmeltable(itemStack2)) 
				{
					if (!this.moveItemStackTo(itemStack2, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (isFuel(itemStack2)) 
				{
					if (!this.moveItemStackTo(itemStack2, 2, 3, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (index >= 4 && index < 31) 
				{
					if (!this.moveItemStackTo(itemStack2, 31, 40, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemStack2, 4, 31, false)) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(itemStack2, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) 
			{
				slot.set(ItemStack.EMPTY);
			} 
			else 
			{
				slot.setChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) 
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemStack2);
		}

		return itemStack;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getSmeltProgress() 
	{
		int time = this.data.get(2);
		int timeTotal = this.data.get(3);
		return timeTotal != 0 && time != 0 ? time * 24 / timeTotal : 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getFuelProgress()
	{
		int fuelTime = this.data.get(1);
		if (fuelTime == 0) {
			fuelTime = 200;
		}
		return this.data.get(0) * 13 / fuelTime;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() 
	{
		return this.data.get(0) > 0;
	}
}
