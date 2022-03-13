package mod.beethoven92.betterendforge.common.recipes;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class AnvilSmithingRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AnvilSmithingRecipe>
{
	@Override
	public AnvilSmithingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		Ingredient input = Ingredient.fromJson(json.get("input"));
		String resultStr = JSONUtils.getAsString(json, "result");
		ResourceLocation resultId = new ResourceLocation(resultStr);
		ItemStack output = new ItemStack(Registry.ITEM.getOptional(resultId).orElseThrow(() -> {
			return new IllegalStateException("Item: " + resultStr + " does not exists!");
		}));
		int inputCount = JSONUtils.getAsInt(json, "inputCount", 1);
		int level = JSONUtils.getAsInt(json, "level", 1);
		int damage = JSONUtils.getAsInt(json, "damage", 1);
		int anvilLevel = JSONUtils.getAsInt(json, "anvilLevel", 1);
		
		return new AnvilSmithingRecipe(id, input, output, inputCount, level, damage, anvilLevel);
	}

	@Override
	public AnvilSmithingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) 
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
	public void toNetwork(PacketBuffer buffer, AnvilSmithingRecipe recipe) 
	{	
		recipe.input.toNetwork(buffer);
		buffer.writeItem(recipe.output);
		buffer.writeVarInt(recipe.inputCount);
		buffer.writeVarInt(recipe.level);
		buffer.writeVarInt(recipe.damage);
		buffer.writeVarInt(recipe.anvilLevel);
	}
}
