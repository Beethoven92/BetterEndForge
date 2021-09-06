package mod.beethoven92.betterendforge.common.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class ModRecipeManager 
{
	public static final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> RECIPES = Maps.newHashMap();

	public static void addRecipe(IRecipeType<?> type, IRecipe<?> recipe) 
	{
		Map<ResourceLocation, IRecipe<?>> list = RECIPES.get(type);
		if (list == null) 
		{
			list = Maps.newHashMap();
			RECIPES.put(type, list);
		}
		list.put(recipe.getId(), recipe);
	}
	
	public static Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> getMap(Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes) 
	{
		Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> result = Maps.newHashMap();

		for (IRecipeType<?> type : recipes.keySet()) 
		{
			Map<ResourceLocation, IRecipe<?>> typeList = Maps.newHashMap();
			typeList.putAll(recipes.get(type));
			result.put(type, typeList);
		}

		for (IRecipeType<?> type : RECIPES.keySet()) 
		{
			Map<ResourceLocation, IRecipe<?>> list = RECIPES.get(type);
			if (list != null)
			{
				Map<ResourceLocation, IRecipe<?>> typeList = result.get(type);
				if (typeList == null) 
				{
					typeList = Maps.newHashMap();
					result.put(type, typeList);
				}
				for (Entry<ResourceLocation, IRecipe<?>> entry : list.entrySet()) {
					ResourceLocation id = entry.getKey();
					if (!typeList.containsKey(id))
						typeList.put(id, entry.getValue());
				}
			}
		}

		return result;
	}
}
