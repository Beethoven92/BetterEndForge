package mod.beethoven92.betterendforge.common.recipes;

import com.google.gson.JsonObject;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

public class AnvilSmithingRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AnvilSmithingRecipe>
{
	@Override
	public AnvilSmithingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		Ingredient input = Ingredient.fromJson(json.get("input"));
		String resultStr = GsonHelper.getAsString(json, "result");
		ResourceLocation resultId = new ResourceLocation(resultStr);
		ItemStack output = new ItemStack(Registry.ITEM.getOptional(resultId).orElseThrow(() -> {
			return new IllegalStateException("Item: " + resultStr + " does not exists!");
		}));
		int inputCount = GsonHelper.getAsInt(json, "inputCount", 1);
		int level = GsonHelper.getAsInt(json, "level", 1);
		int damage = GsonHelper.getAsInt(json, "damage", 1);
		int anvilLevel = GsonHelper.getAsInt(json, "anvilLevel", 1);
		
		return new AnvilSmithingRecipe(id, input, output, inputCount, level, damage, anvilLevel);
	}

	@Override
	public AnvilSmithingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) 
	{
		Ingredient input = Ingredient.fromNetwork(buffer);
		ItemStack output = buffer.readItem();
		int inputCount = buffer.readVarInt();
		int level = buffer.readVarInt();
		int damage = buffer.readVarInt();
		int anvilLevel = buffer.readVarInt();
		
		return new AnvilSmithingRecipe(id, input, output, inputCount, level, damage, anvilLevel);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, AnvilSmithingRecipe recipe) 
	{	
		recipe.input.toNetwork(buffer);
		buffer.writeItem(recipe.output);
		buffer.writeVarInt(recipe.inputCount);
		buffer.writeVarInt(recipe.level);
		buffer.writeVarInt(recipe.damage);
		buffer.writeVarInt(recipe.anvilLevel);
	}
}
