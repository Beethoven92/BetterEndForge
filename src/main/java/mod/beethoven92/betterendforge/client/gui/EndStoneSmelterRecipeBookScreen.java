package mod.beethoven92.betterendforge.client.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.recipebook.BlastingRecipeBookComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.NonNullList;

public class EndStoneSmelterRecipeBookScreen extends BlastingRecipeBookComponent
{
	private Iterator<Item> fuelIterator;
	private Set<Item> fuels;
	private Slot fuelSlot;
	private Item currentItem;
	private float frameTime;
	
	@Override
	protected Set<Item> getFuelItems()
	{
		return EndStoneSmelterTileEntity.getAvailableFuels().keySet();
	}	
	
	@Override
	public void slotClicked(Slot slotIn) 
	{
		super.slotClicked(slotIn);
		if (slotIn != null && slotIn.index < this.menu.getSize()) 
		{
			this.fuelSlot = null;
		}
	}
	
	@Override
	public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) 
	{
		this.ghostRecipe.clear();;
		ItemStack result = recipe.getResultItem();
		this.ghostRecipe.setRecipe(recipe);
		this.ghostRecipe.addIngredient(Ingredient.of(result), (slots.get(3)).x, (slots.get(3)).y);
		NonNullList<Ingredient> inputs = recipe.getIngredients();
		Iterator<Ingredient> iterator = inputs.iterator();
		for(int i = 0; i < 2; i++) 
		{
			if (!iterator.hasNext()) 
			{
				return;
			}
			Ingredient ingredient = iterator.next();
			if (!ingredient.isEmpty()) 
			{
				Slot slot = slots.get(i);
				this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
			}
		}
		this.fuelSlot = slots.get(2);
		if (this.fuels == null) 
		{
			this.fuels = this.getFuelItems();
		}

		this.fuelIterator = this.fuels.iterator();
		this.currentItem = null;
	}
	
	@Override
	public void renderGhostRecipe(PoseStack matrixStack, int x, int y, boolean bl, float f) 
	{
		this.ghostRecipe.render(matrixStack, this.minecraft, x, y, bl, f);
		if (fuelSlot != null) 
		{
			if (!Screen.hasControlDown())
			{
				this.frameTime += f;
			}

			int slotX = this.fuelSlot.x + x;
			int slotY = this.fuelSlot.y + y;
			GuiComponent.fill(matrixStack, slotX, slotY, slotX + 16, slotY + 16, 822018048);
			this.minecraft.getItemRenderer().renderAndDecorateItem(minecraft.player, this.getItem().getDefaultInstance(), slotX, slotY);
			RenderSystem.depthFunc(516);
			GuiComponent.fill(matrixStack, slotX, slotY, slotX + 16, slotY + 16, 822083583);
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
					this.fuels = this.getFuelItems();
				}
				this.fuelIterator = this.fuels.iterator();
			}
			this.currentItem = this.fuelIterator.next();
		}
		return this.currentItem;
	}
}
