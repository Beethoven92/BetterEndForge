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
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.world.level.Level;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin 
{
	@Shadow
	private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

	@Inject(method = "apply", at = @At(value = "RETURN"))
	private void beSetRecipes(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, 
			ProfilerFiller profiler, CallbackInfo info)
	{
		recipes = ModRecipeManager.getMap(recipes);
	}
	
	@Shadow
	private <C extends Container, T extends Recipe<C>> Map<ResourceLocation, Recipe<C>> byType(RecipeType<T> type)
	{
		return null;
	}

	@Overwrite
	public <C extends Container, T extends Recipe<C>> Optional<T> getRecipeFor(RecipeType<T> type,
			C inventory, Level world) 
	{
		Collection<Recipe<C>> values = byType(type).values();
		List<Recipe<C>> list = new ArrayList<Recipe<C>>(values);
		list.sort((v1, v2) -> {
			boolean b1 = v1.getId().getNamespace().equals("minecraft");
			boolean b2 = v2.getId().getNamespace().equals("minecraft");
			return b1 ^ b2 ? (b1 ? 1 : -1) : 0;
		});

		return list.stream().flatMap((recipe) -> {
			return Util.toStream(type.tryMatch(recipe, world, inventory));
		}).findFirst();
	}
}
