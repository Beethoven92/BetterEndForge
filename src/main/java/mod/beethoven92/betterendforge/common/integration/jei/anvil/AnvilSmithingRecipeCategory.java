package mod.beethoven92.betterendforge.common.integration.jei.anvil;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.recipes.AnvilSmithingRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;

public class AnvilSmithingRecipeCategory implements IRecipeCategory<AnvilSmithingRecipe>
{
	protected static final int LEFT_INPUT_SLOT = 0;
	protected static final int RIGHT_INPUT_SLOT = 1;
	protected static final int OUTPUT_SLOT = 2;
	protected static final int ANVIL_SLOT = 3;
	
	private final IDrawable background;
	private final IDrawable icon;
	
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/jei/jei_anvil_smithing_gui.png");
	public static final ResourceLocation UID = new ResourceLocation(BetterEnd.MOD_ID, AnvilSmithingRecipe.GROUP);
	
	public AnvilSmithingRecipeCategory(IGuiHelper guiHelper)
	{
		background = guiHelper.drawableBuilder(GUI_TEXTURE, 0, 0, 125, 38).addPadding(0, 0, 0, 0).build();
		icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.ANVIL));
	}
	
	@Override
	public ResourceLocation getUid() 
	{
		return UID;
	}

	@Override
	public Class<? extends AnvilSmithingRecipe> getRecipeClass() 
	{
		return AnvilSmithingRecipe.class;
	}

	@Override
	public String getTitle()
	{
		return new TranslatableComponent("gui.jei.category.anvil_smithing").getString();
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
	public void setIngredients(AnvilSmithingRecipe recipe, IIngredients ingredients) 
	{	
		NonNullList<Ingredient> inputs = NonNullList.create();
		
		// Left and right ingredients
		inputs.addAll(recipe.getIngredients());
		
		// Required anvil level
		inputs.add(Ingredient.of(BlockTags.ANVIL.getValues().stream().filter(anvil -> {
			if (anvil instanceof EndAnvilBlock) 
			{
				return ((EndAnvilBlock) anvil).getCraftingLevel() >= recipe.anvilLevel;
			}
			return recipe.anvilLevel == 1;
		}).map(ItemStack::new)));
		
		ingredients.setInputIngredients(inputs);
		
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AnvilSmithingRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(LEFT_INPUT_SLOT, true, 0, 0);
		guiItemStacks.init(RIGHT_INPUT_SLOT, true, 49, 0);
		guiItemStacks.init(ANVIL_SLOT, true, 24, 20);
		guiItemStacks.init(OUTPUT_SLOT, false, 107, 0);
		
		guiItemStacks.set(ingredients);
	}
	
	protected void drawToolDamage(AnvilSmithingRecipe recipe, PoseStack matrixStack, int y) 
	{
		int damage = recipe.damage;
		if (damage > 0) 
		{
			TranslatableComponent timeString = new TranslatableComponent("gui.jei.category.anvil_smithing.damage", damage);
			Minecraft minecraft = Minecraft.getInstance();
			Font fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(timeString);
			fontRenderer.draw(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}
	
	@Override
	public void draw(AnvilSmithingRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) 
	{
		drawToolDamage(recipe, matrixStack, 27);
	}
}
