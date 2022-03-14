package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipeSerializer;
import mod.beethoven92.betterendforge.common.recipes.AnvilSmithingRecipeSerializer;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipeSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers 
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
			DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterEnd.MOD_ID);
	
	public static final RegistryObject<AlloyingRecipeSerializer<AlloyingRecipe>> ALLOYING = RECIPE_SERIALIZERS.register("alloying", 
			() -> new AlloyingRecipeSerializer<>(AlloyingRecipe::new));
	
	public static final RegistryObject<InfusionRecipeSerializer> INFUSION = RECIPE_SERIALIZERS.register("infusion", 
			() -> new InfusionRecipeSerializer());
	
	public static final RegistryObject<AnvilSmithingRecipeSerializer> ANVIL_SMITHING = RECIPE_SERIALIZERS.register("anvil_smithing", 
			() -> new AnvilSmithingRecipeSerializer());
	
	public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String type) 
	{
		ResourceLocation recipeTypeId = new ResourceLocation(BetterEnd.MOD_ID, type);
		return Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<T>() 
		{
			public String toString() 
			{
				return type;
			}
	    });
	}
}
