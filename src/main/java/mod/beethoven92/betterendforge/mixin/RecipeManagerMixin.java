package mod.beethoven92.betterendforge.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;

import mod.beethoven92.betterendforge.common.recipes.ModRecipeManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin 
{
	@Shadow
	private Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes;

	@Inject(method = "apply", at = @At(value = "RETURN"))
	private void beSetRecipes(Map<ResourceLocation, JsonElement> map, IResourceManager resourceManager, 
			IProfiler profiler, CallbackInfo info)
	{
		recipes = ModRecipeManager.getMap(recipes);
	}
	
	@Shadow
	private <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, IRecipe<C>> getRecipes(IRecipeType<T> type) 
	{
		return null;
	}

	@Overwrite
	public <C extends IInventory, T extends IRecipe<C>> Optional<T> getRecipe(IRecipeType<T> type, 
			C inventory, World world) 
	{
		Collection<IRecipe<C>> values = getRecipes(type).values();
		List<IRecipe<C>> list = new ArrayList<IRecipe<C>>(values);
		list.sort((v1, v2) -> {
			boolean b1 = v1.getId().getNamespace().equals("minecraft");
			boolean b2 = v2.getId().getNamespace().equals("minecraft");
			return b1 ^ b2 ? (b1 ? 1 : -1) : 0;
		});

		return list.stream().flatMap((recipe) -> {
			return Util.streamOptional(type.matches(recipe, world, inventory));
		}).findFirst();
	}
}
