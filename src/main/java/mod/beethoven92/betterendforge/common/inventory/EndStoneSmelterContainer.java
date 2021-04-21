package mod.beethoven92.betterendforge.common.inventory;

import mod.beethoven92.betterendforge.common.init.ModContainerTypes;
import mod.beethoven92.betterendforge.common.inventory.slot.SmelterFuelSlot;
import mod.beethoven92.betterendforge.common.inventory.slot.SmelterOutputSlot;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndStoneSmelterContainer extends RecipeBookContainer<IInventory>
{
	private final IInventory inventory;
	private final IIntArray data;
	protected final World world;
	
	public EndStoneSmelterContainer(int id, PlayerInventory inventory, net.minecraft.network.PacketBuffer extraData) 
	{
		this(id, inventory, new Inventory(4), new IntArray(4));
	}
	
	public EndStoneSmelterContainer(int syncId, PlayerInventory playerInventory, IInventory inventory, IIntArray data) 
	{
		super(ModContainerTypes.END_STONE_SMELTER.get(), syncId);
		this.inventory = inventory;
		this.data = data;
		this.world = playerInventory.player.world;
		
		this.trackIntArray(data);
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
	public void fillStackedContents(RecipeItemHelper itemHelperIn) 
	{
		if (inventory instanceof IRecipeHelperPopulator) 
		{
			((IRecipeHelperPopulator) inventory).fillStackedContents(itemHelperIn);
		}
	}

	@Override
	public void clear()
	{	
		this.inventory.clear();
	}

	@Override
	public boolean matches(IRecipe<? super IInventory> recipeIn) 
	{
		return recipeIn.matches(this.inventory, this.world);
	}

	@Override
	public int getOutputSlot() 
	{
		return 3;
	}

	@Override
	public int getWidth() 
	{
		return 2;
	}

	@Override
	public int getHeight() 
	{
		return 1;
	}

	@Override
	public int getSize() 
	{
		return 4;
	}

	@Override
	public RecipeBookCategory func_241850_m() 
	{
		return RecipeBookCategory.BLAST_FURNACE;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) 
	{
		return this.inventory.isUsableByPlayer(playerIn);
	}

	protected boolean isSmeltable(ItemStack itemStack) 
	{
		return this.world.getRecipeManager().getRecipe(AlloyingRecipe.TYPE, new Inventory(new ItemStack[]{itemStack}), this.world).isPresent();
	}

	public boolean isFuel(ItemStack itemStack) 
	{
		return EndStoneSmelterTileEntity.isFuel(itemStack);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) 
	{
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) 
		{
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index == 3) 
			{
				if (!this.mergeItemStack(itemStack2, 4, 40, true)) 
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemStack2, itemStack);
			} 
			else if (index != 2 && index != 1 && index != 0) 
			{
				if (isSmeltable(itemStack2)) 
				{
					if (!this.mergeItemStack(itemStack2, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (isFuel(itemStack2)) 
				{
					if (!this.mergeItemStack(itemStack2, 2, 3, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (index >= 4 && index < 31) 
				{
					if (!this.mergeItemStack(itemStack2, 31, 40, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (index >= 31 && index < 40 && !this.mergeItemStack(itemStack2, 4, 31, false)) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemStack2, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) 
			{
				slot.putStack(ItemStack.EMPTY);
			} 
			else 
			{
				slot.onSlotChanged();
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
