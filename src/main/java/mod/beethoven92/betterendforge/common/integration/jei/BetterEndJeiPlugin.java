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
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.integration.jei.alloying.AlloyingRecipeCategory;
import mod.beethoven92.betterendforge.common.integration.jei.anvil.AnvilSmithingRecipeCategory;
import mod.beethoven92.betterendforge.common.integration.jei.infusion.InfusionRecipeCategory;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.AnvilSmithingRecipe;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import net.minecraft.block.Blocks;
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

		registration.addRecipeCatalyst(new ItemStack(ModBlocks.INFUSION_PEDESTAL.get()), InfusionRecipeCategory.UID);

		registration.addRecipeCatalyst(new ItemStack(Blocks.ANVIL), VanillaRecipeCategoryUid.ANVIL);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.AETERNIUM_ANVIL.get()), VanillaRecipeCategoryUid.ANVIL);
		for (MetalMaterial material : ModBlocks.getMetalMaterials()) {
			registration.addRecipeCatalyst(new ItemStack(material.anvil.get()), VanillaRecipeCategoryUid.ANVIL);
		}


		registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
		for (StoneMaterial material : ModBlocks.getStoneMaterials()) {
			registration.addRecipeCatalyst(new ItemStack(material.furnace.get()), VanillaRecipeCategoryUid.FUEL);
			registration.addRecipeCatalyst(new ItemStack(material.furnace.get()), VanillaRecipeCategoryUid.FURNACE);
		}

		for (WoodenMaterial material : ModBlocks.getWoodenMaterials())
			registration.addRecipeCatalyst(new ItemStack(material.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) 
	{
		// TO DO: move the recipes lists in a separate utility class
		Minecraft mc = Minecraft.getInstance();
		ClientWorld world = Objects.requireNonNull(mc.world);
		
		Set<AlloyingRecipe> alloyingRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(AlloyingRecipe.TYPE));
		registration.addRecipes(alloyingRecipes, AlloyingRecipeCategory.UID);
		
		Set<InfusionRecipe> infusionRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(InfusionRecipe.TYPE));
		registration.addRecipes(infusionRecipes, InfusionRecipeCategory.UID);
		
		Set<AnvilSmithingRecipe> anvilSmithingRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(AnvilSmithingRecipe.TYPE));
		registration.addRecipes(anvilSmithingRecipes, AnvilSmithingRecipeCategory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) 
	{
		registration.addRecipeCategories(new AlloyingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new InfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new AnvilSmithingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}
}
