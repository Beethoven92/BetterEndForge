package mod.beethoven92.betterendforge.client.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.recipebook.BlastFurnaceRecipeGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class EndStoneSmelterRecipeBookScreen extends BlastFurnaceRecipeGui
{
	private Iterator<Item> fuelIterator;
	private Set<Item> fuels;
	private Slot fuelSlot;
	private Item currentItem;
	private float frameTime;
	
	@Override
	protected Set<Item> func_212958_h()
	{
		return EndStoneSmelterTileEntity.getAvailableFuels().keySet();
	}	
	
	@Override
	public void slotClicked(Slot slotIn) 
	{
		super.slotClicked(slotIn);
		if (slotIn != null && slotIn.slotNumber < this.field_201522_g.getSize()) 
		{
			this.fuelSlot = null;
		}
	}
	
	@Override
	public void setupGhostRecipe(IRecipe<?> recipe, List<Slot> slots) 
	{
		this.ghostRecipe.clear();;
		ItemStack result = recipe.getRecipeOutput();
		this.ghostRecipe.setRecipe(recipe);
		this.ghostRecipe.addIngredient(Ingredient.fromStacks(result), (slots.get(3)).xPos, (slots.get(3)).yPos);
		NonNullList<Ingredient> inputs = recipe.getIngredients();
		Iterator<Ingredient> iterator = inputs.iterator();
		for(int i = 0; i < 2; i++) 
		{
			if (!iterator.hasNext()) 
			{
				return;
			}
			Ingredient ingredient = iterator.next();
			if (!ingredient.hasNoMatchingItems()) 
			{
				Slot slot = slots.get(i);
				this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
			}
		}
		this.fuelSlot = slots.get(2);
		if (this.fuels == null) 
		{
			this.fuels = this.func_212958_h();
		}

		this.fuelIterator = this.fuels.iterator();
		this.currentItem = null;
	}
	
	@Override
	public void func_230477_a_(MatrixStack matrixStack, int x, int y, boolean bl, float f) 
	{
		this.ghostRecipe.func_238922_a_(matrixStack, this.mc, x, y, bl, f);
		if (fuelSlot != null) 
		{
			if (!Screen.hasControlDown())
			{
				this.frameTime += f;
			}

			int slotX = this.fuelSlot.xPos + x;
			int slotY = this.fuelSlot.yPos + y;
			AbstractGui.fill(matrixStack, slotX, slotY, slotX + 16, slotY + 16, 822018048);
			this.mc.getItemRenderer().renderItemAndEffectIntoGUI(mc.player, this.getItem().getDefaultInstance(), slotX, slotY);
			RenderSystem.depthFunc(516);
			AbstractGui.fill(matrixStack, slotX, slotY, slotX + 16, slotY + 16, 822083583);
			RenderSystem.depthFunc(515);
		}
	}
	
	private Item getItem() 
	{
		if (this.currentItem == null || this.frameTime > 30.0F) 
		{
			this.frameTime = 0.0F;
			if (this.fuelIterator == null || !this.fuelIterator.hasNext()) 
			{
				if (this.fuels == null) 
				{
					this.fuels = this.func_212958_h();
				}
				this.fuelIterator = this.fuels.iterator();
			}
			this.currentItem = this.fuelIterator.next();
		}
		return this.currentItem;
	}
}
