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

		registration.addRecipeCatalyst(new ItemStack(Blocks.ANVIL), AnvilSmithingRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.THALLASIUM.anvil.get()), AnvilSmithingRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.TERMINITE.anvil.get()), AnvilSmithingRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.AETERNIUM_ANVIL.get()), AnvilSmithingRecipeCategory.UID);

		registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.FLAVOLITE.furnace.get()), VanillaRecipeCategoryUid.FURNACE);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.VIOLECITE.furnace.get()), VanillaRecipeCategoryUid.FURNACE);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.SULPHURIC_ROCK.furnace.get()), VanillaRecipeCategoryUid.FURNACE);

		registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.FLAVOLITE.furnace.get()), VanillaRecipeCategoryUid.FUEL);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.VIOLECITE.furnace.get()), VanillaRecipeCategoryUid.FUEL);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.SULPHURIC_ROCK.furnace.get()), VanillaRecipeCategoryUid.FUEL);

		registration.addRecipeCatalyst(new ItemStack(ModBlocks.MOSSY_GLOWSHROOM.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.LACUGROVE.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_LOTUS.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.PYTHADENDRON.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.DRAGON_TREE.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.TENANEA.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.HELIX_TREE.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.UMBRELLA_TREE.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.JELLYSHROOM.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.LUCERNIA.craftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
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
