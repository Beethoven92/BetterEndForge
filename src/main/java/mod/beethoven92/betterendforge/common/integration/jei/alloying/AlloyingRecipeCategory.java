package mod.beethoven92.betterendforge.common.integration.jei.alloying;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class AlloyingRecipeCategory implements IRecipeCategory<AlloyingRecipe>
{	
	protected static final int FIRST_INPUT_SLOT = 0;
	protected static final int SECOND_INPUT_SLOT = 1;
	protected static final int FUEL_SLOT = 2;
	protected static final int OUTPUT_SLOT = 3;
	
	private final IDrawable background;
	private final IDrawable icon;
	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
	
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/jei/jei_smelter_gui.png");
	public static final ResourceLocation UID = new ResourceLocation(BetterEnd.MOD_ID, AlloyingRecipe.GROUP);
	
	public AlloyingRecipeCategory(IGuiHelper guiHelper)
	{
		icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.END_STONE_SMELTER.get()));
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 106, 54);
		staticFlame = guiHelper.createDrawable(GUI_TEXTURE, 106, 0, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		this.cachedArrows = CacheBuilder.newBuilder()
				.maximumSize(25)
				.build(new CacheLoader<Integer, IDrawableAnimated>() 
				{
					@Override
					public IDrawableAnimated load(Integer cookTime) {
						return guiHelper.drawableBuilder(GUI_TEXTURE, 106, 14, 24, 17)
							.buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
					}
				});
	}
	
	@Override
	public ResourceLocation getUid() 
	{
		return UID;
	}

	@Override
	public Class<? extends AlloyingRecipe> getRecipeClass() 
	{
		return AlloyingRecipe.class;
	}

	@Override
	public String getTitle() 
	{
		return new TranslationTextComponent("gui.jei.category.alloying").getString();
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
	public void setIngredients(AlloyingRecipe recipe, IIngredients ingredients) 
	{
		NonNullList<Ingredient> inputs = NonNullList.create();
		
		// Left and right ingredients
		inputs.addAll(recipe.getIngredients());
		
		// Available fuels
		inputs.add(Ingredient.fromItems(Blocks.COAL_BLOCK, Items.BLAZE_ROD, Items.LAVA_BUCKET));
		
		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AlloyingRecipe recipe, IIngredients ingredients) 
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(FIRST_INPUT_SLOT, true, 0, 0);
		guiItemStacks.init(SECOND_INPUT_SLOT, true, 22, 0);
		guiItemStacks.init(FUEL_SLOT, true, 11, 36);
		
		guiItemStacks.init(OUTPUT_SLOT, false, 84, 18);
		
		guiItemStacks.set(ingredients);
	}
	
	protected IDrawableAnimated getArrow(AlloyingRecipe recipe) 
	{
		int smeltTime = recipe.getSmeltTime();

		return this.cachedArrows.getUnchecked(smeltTime);
	}
	
	protected void drawSmeltTime(AlloyingRecipe recipe, MatrixStack matrixStack, int y) 
	{
		int smeltTime = recipe.getSmeltTime();
		if (smeltTime > 0) 
		{
			int smeltTimeSeconds = smeltTime / 20;
			TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category.alloying.time.seconds", smeltTimeSeconds);
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
			fontRenderer.func_243248_b(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}
	
	protected void drawExperience(AlloyingRecipe recipe, MatrixStack matrixStack, int y) 
	{
		float experience = recipe.getExperience();
		if (experience > 0) 
		{
			TranslationTextComponent experienceString = new TranslationTextComponent("gui.jei.category.alloying.experience", experience);
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringPropertyWidth(experienceString);
			fontRenderer.func_243248_b(matrixStack, experienceString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}
	
	@Override
	public void draw(AlloyingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		animatedFlame.draw(matrixStack, 12, 20);

		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(matrixStack, 47, 18);

	    drawExperience(recipe, matrixStack, 0);
		drawSmeltTime(recipe, matrixStack, 45);
	}
}
