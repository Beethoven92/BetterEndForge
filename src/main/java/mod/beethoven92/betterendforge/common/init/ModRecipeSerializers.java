package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipeSerializer;
import mod.beethoven92.betterendforge.common.recipes.AnvilSmithingRecipeSerializer;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers 
{
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = 
			DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterEnd.MOD_ID);
	
	public static final RegistryObject<AlloyingRecipeSerializer<AlloyingRecipe>> ALLOYING = RECIPE_SERIALIZERS.register("alloying", 
			() -> new AlloyingRecipeSerializer<>(AlloyingRecipe::new));
	
	public static final RegistryObject<InfusionRecipeSerializer> INFUSION = RECIPE_SERIALIZERS.register("infusion", 
			() -> new InfusionRecipeSerializer());
	
	public static final RegistryObject<AnvilSmithingRecipeSerializer> ANVIL_SMITHING = RECIPE_SERIALIZERS.register("anvil_smithing", 
			() -> new AnvilSmithingRecipeSerializer());
	
	public static <T extends IRecipe<?>> IRecipeType<T> registerRecipeType(String type) 
	{
		ResourceLocation recipeTypeId = new ResourceLocation(BetterEnd.MOD_ID, type);
		return Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new IRecipeType<T>() 
		{
			public String toString() 
			{
				return type;
			}
	    });
	}
}
