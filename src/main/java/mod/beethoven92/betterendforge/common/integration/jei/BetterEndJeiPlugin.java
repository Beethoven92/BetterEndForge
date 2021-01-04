package mod.beethoven92.betterendforge.common.integration.jei;

import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.integration.jei.alloying.AlloyingRecipeCategory;
import mod.beethoven92.betterendforge.common.integration.jei.infusion.InfusionRecipeCategory;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class BetterEndJeiPlugin implements IModPlugin
{
	@Override
	public ResourceLocation getPluginUid() 
	{
		return new ResourceLocation(BetterEnd.MOD_ID, "jei_plugin");
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_SMELTER.get()), AlloyingRecipeCategory.UID);
		//registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_SMELTER.get()), AlloyingRecipeCategory.UID, VanillaRecipeCategoryUid.FUEL);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.INFUSION_PEDESTAL.get()), InfusionRecipeCategory.UID);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) 
	{
		// TO DO: move the recipes lists in a separate utility class
		ClientWorld world = Objects.requireNonNull(Minecraft.getInstance().world);
		
		Set<AlloyingRecipe> alloyingRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(AlloyingRecipe.TYPE));
		registration.addRecipes(alloyingRecipes, AlloyingRecipeCategory.UID);
		
		Set<InfusionRecipe> infusionRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(InfusionRecipe.TYPE));
		registration.addRecipes(infusionRecipes, InfusionRecipeCategory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) 
	{
		registration.addRecipeCategories(new AlloyingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new InfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}
}
