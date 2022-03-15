package mod.beethoven92.betterendforge.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndStoneSmelterScreen extends AbstractContainerScreen<EndStoneSmelterContainer> implements RecipeUpdateListener
{
	private final static ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	private final static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/smelter_gui.png");
	
	public final EndStoneSmelterRecipeBookScreen recipeBook;
	private boolean narrow;
	
	public EndStoneSmelterScreen(EndStoneSmelterContainer screenContainer, Inventory inv,
			Component titleIn)
	{
		super(screenContainer, inv, titleIn);
		this.recipeBook = new EndStoneSmelterRecipeBookScreen();
	}

	@Override
	protected void init() 
	{
		super.init();
		this.narrow = this.width < 379;
		this.recipeBook.init(width, height, minecraft, narrow, menu);
		this.leftPos = this.recipeBook.updateScreenPosition(narrow, width, imageWidth);
		this.addButton(new ImageButton(this.leftPos + 20, height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (buttonWidget) -> {
			this.recipeBook.initVisuals(narrow);
			this.recipeBook.toggleVisibility();
			this.leftPos = this.recipeBook.updateScreenPosition(narrow, width, imageWidth);
			((ImageButton) buttonWidget).setPosition(this.leftPos + 20, height / 2 - 49);
		}));
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
	    this.recipeBook.tick();
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) 
	{
		this.renderBackground(matrixStack);
		if (this.recipeBook.isVisible() && this.narrow) 
		{
			this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
			this.recipeBook.render(matrixStack, mouseX, mouseY, partialTicks);
		} 
		else 
		{
			this.recipeBook.render(matrixStack, mouseX, mouseY, partialTicks);
			super.render(matrixStack, mouseX, mouseY, partialTicks);
			this.recipeBook.renderGhostRecipe(matrixStack, leftPos, topPos, true, partialTicks);
		}

		this.renderTooltip(matrixStack, mouseX, mouseY);
		this.recipeBook.renderTooltip(matrixStack, leftPos, topPos, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) 
	{		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);
	    int i = this.leftPos;
	    int j = this.topPos;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		int p;
		if (this.menu.isBurning()) 
		{
			p = this.menu.getFuelProgress();
			this.blit(matrixStack, i + 56, j + 36 + 12 - p, 176, 12 - p, 14, p + 1);
		}
		p = this.menu.getSmeltProgress();
		this.blit(matrixStack, i + 92, j + 34, 176, 14, p + 1, 16);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) 
		{
			return true;
		} 
		else 
		{
			return this.narrow && this.recipeBook.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) 
	{
		super.slotClicked(slotIn, slotId, mouseButton, type);
		this.recipeBook.slotClicked(slotIn);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) 
	{
		return this.recipeBook.keyPressed(keyCode, scanCode, modifiers) ? false : super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) 
	{
		boolean isMouseOut = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.imageWidth) || mouseY >= (double)(guiTopIn + this.imageHeight);
		return this.recipeBook.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && isMouseOut;
	}
	
	@Override
	public boolean charTyped(char codePoint, int modifiers) 
	{
		return this.recipeBook.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
	}
	
	@Override
	public void recipesUpdated() 
	{
		this.recipeBook.recipesUpdated();
	}

	@Override
	public RecipeBookComponent getRecipeBookComponent() 
	{
		return this.recipeBook;
	}
	
	@Override
	public void removed() 
	{
	      this.recipeBook.removed();
	      super.removed();
	}
}
