package mod.beethoven92.betterendforge.common.integration.jei.infusion;

import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class InfusionRecipeCategory implements IRecipeCategory<InfusionRecipe>
{
	protected static final int INPUT_SLOT = 0;
	protected static final int CATALYST_SLOT_1 = 1;
	protected static final int CATALYST_SLOT_2 = 2;
	protected static final int CATALYST_SLOT_3 = 3;
	protected static final int CATALYST_SLOT_4 = 4;
	protected static final int CATALYST_SLOT_5 = 5;
	protected static final int CATALYST_SLOT_6 = 6;
	protected static final int CATALYST_SLOT_7 = 7;
	protected static final int CATALYST_SLOT_8 = 8;
	protected static final int OUTPUT_SLOT = 9;
	private final IDrawable background;
	private final IDrawable icon;
	
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/jei/jei_infusion_gui.png");
	public static final ResourceLocation UID = new ResourceLocation(BetterEnd.MOD_ID, InfusionRecipe.GROUP);
	
	public InfusionRecipeCategory(IGuiHelper guiHelper)
	{
		icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.INFUSION_PEDESTAL.get()));
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 146, 98);
	}
	
	@Override
	public ResourceLocation getUid() 
	{
		return UID;
	}

	@Override
	public Class<? extends InfusionRecipe> getRecipeClass() 
	{
		return InfusionRecipe.class;
	}

	@Override
	public String getTitle() 
	{
		return new TranslationTextComponent("gui.jei.category.infusion").getString();
	}

	@Override
	public IDrawable getBackground() 
	{
		return background;
	}

	@Override
	public IDrawable getIcon() 
	{
		return icon;
	}

	@Override
	public void setIngredients(InfusionRecipe recipe, IIngredients ingredients) 
	{
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, InfusionRecipe recipe, IIngredients ingredients) 
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		// The main input of the recipe, placed in the the central slot
		guiItemStacks.init(INPUT_SLOT, true, 38, 46);
		// Catalysts are placed clockwise starting from the north side (top slot)
		guiItemStacks.init(CATALYST_SLOT_1, true, 38, 18);
		guiItemStacks.init(CATALYST_SLOT_2, true, 62, 22);
		guiItemStacks.init(CATALYST_SLOT_3, true, 66, 46);
		guiItemStacks.init(CATALYST_SLOT_4, true, 62, 70);
		guiItemStacks.init(CATALYST_SLOT_5, true, 38, 74);
		guiItemStacks.init(CATALYST_SLOT_6, true, 14, 70);
		guiItemStacks.init(CATALYST_SLOT_7, true, 10, 46);
		guiItemStacks.init(CATALYST_SLOT_8, true, 14, 22);
		guiItemStacks.init(OUTPUT_SLOT, false, 118, 46);
		
		guiItemStacks.set(ingredients);
	}
	
	protected void drawInfusionTime(InfusionRecipe recipe, MatrixStack matrixStack, int y) 
	{
		int infusionTime = recipe.time;
		if (infusionTime > 0) 
		{
			TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category.infusion.time", infusionTime);
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
			fontRenderer.func_243248_b(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}
	
	@Override
	public void draw(InfusionRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		drawInfusionTime(recipe, matrixStack, 0);
	}
}
