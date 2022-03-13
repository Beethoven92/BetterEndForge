package mod.beethoven92.betterendforge.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class InfusionRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<InfusionRecipe> {
	@Override
	public InfusionRecipe fromJson(ResourceLocation id, JsonObject json) {
		InfusionRecipe recipe = new InfusionRecipe(id);
		recipe.input = Ingredient.fromJson(json.get("input"));
		recipe.output = readOutput(json);
		recipe.time = JSONUtils.getAsInt(json, "time", 1);

		JsonArray catalysts = JSONUtils.getAsJsonArray(json, "catalysts");

		for (int i = 0; i < catalysts.size(); i++) {
			JsonObject indexedIngredient = catalysts.get(i).getAsJsonObject();
			int index = JSONUtils.getAsInt(indexedIngredient, "index");
			if (index < 0 || index > 7) {
				throw new IllegalStateException(
						"BETTER_END_FORGE: Infusion recipe ingredient index out of bounds, must be between 0 and 8 (excluded)");
			}
			Ingredient item = readIngredient(indexedIngredient);
			recipe.ingredientPositions.put(index, item);
		}
		for (int i = 0; i < 8; i++) {
			if (recipe.ingredientPositions.containsKey(i)) {
				recipe.catalysts[i] = recipe.ingredientPositions.get(i);
			} else
				recipe.catalysts[i] = Ingredient.EMPTY;
		}
		return recipe;
	}

	private Ingredient readIngredient(JsonObject json) {
		if ((json.has("item") && json.get("item").isJsonPrimitive()) || json.has("tag"))
			return Ingredient.fromJson(json);
		else if (json.has("item"))
			return Ingredient.fromJson(json.get("item"));

		throw new JsonSyntaxException("Catalyst needs to have either item or tag" + json);
	}

	private ItemStack readOutput(JsonObject json) {
		JsonElement outputElem = json.get("output");
		if (outputElem.isJsonObject())
			return ItemStack.CODEC.parse(JsonOps.INSTANCE, outputElem).result().get();
		else
			return new ItemStack(JSONUtils.convertToItem(outputElem, "output"));
	}

	@Override
	public InfusionRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
		InfusionRecipe recipe = new InfusionRecipe(id);
		recipe.input = Ingredient.fromNetwork(buffer);
		recipe.output = buffer.readItem();
		recipe.time = buffer.readVarInt();

		int[] index_array = new int[8];

		// Ugly: need to improve
		for (int i = 0; i < 8; i++) {
			index_array[i] = buffer.readVarInt();
		}
		for (int i = 0; i < 8; i++) {
			if (index_array[i] != -1) {
				int index = buffer.readVarInt();
				recipe.catalysts[i] = Ingredient.fromNetwork(buffer);
			} else
				recipe.catalysts[i] = Ingredient.EMPTY;
		}
		return recipe;
	}

	@Override
	public void toNetwork(PacketBuffer buffer, InfusionRecipe recipe) {
		recipe.input.toNetwork(buffer);
		buffer.writeItem(recipe.output);
		buffer.writeVarInt(recipe.time);

		// Ugly: need to improve
		// Allows for a flexible index-ingredient list in the json
		for (int i = 0; i < 8; i++) {
			if (recipe.catalysts[i] != Ingredient.EMPTY) {
				buffer.writeVarInt(i);
			} else {
				buffer.writeVarInt(-1);
			}
		}
		for (int i = 0; i < 8; i++) {
			if (recipe.catalysts[i] != Ingredient.EMPTY) {
				buffer.writeVarInt(i); // position of the ingredient
				recipe.catalysts[i].toNetwork(buffer);
			}
		}
	}
}
