package mod.beethoven92.betterendforge.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class AlloyingRecipeSerializer<T extends AlloyingRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T>
{
	private final AlloyingRecipeSerializer.IFactory<T> factory;

	public AlloyingRecipeSerializer(AlloyingRecipeSerializer.IFactory<T> factory) 
	{
		this.factory = factory;
	}
	
	@Override
	public T read(ResourceLocation id, JsonObject json) 
	{
		String group = JSONUtils.getString(json, "group", "");
		JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
		Ingredient primaryInput = Ingredient.deserialize(ingredients.get(0));
		Ingredient secondaryInput = Ingredient.deserialize(ingredients.get(1));
	    ItemStack output;
		// Allows for outputs of more than 1 item in the stack
	    if (json.get("result").isJsonObject()) 
		{
			output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
		}
	    else 
	    {
	    	String result = JSONUtils.getString(json, "result");
	    	ResourceLocation resourcelocation = new ResourceLocation(result);
	        output = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
	        	return new IllegalStateException("Item: " + result + " does not exist");
	      }));
	    }
		float experience = JSONUtils.getFloat(json, "experience", 0.0F);
		int smeltTime = JSONUtils.getInt(json, "smelttime", 350);
		
		return this.factory.create(id, group, primaryInput, secondaryInput, output, experience, smeltTime);
	}

	@Override
	public T read(ResourceLocation id, PacketBuffer buffer) 
	{
		String group = buffer.readString(32767);
		Ingredient primary = Ingredient.read(buffer);
		Ingredient secondary = Ingredient.read(buffer);
		ItemStack output = buffer.readItemStack();
		float experience = buffer.readFloat();
		int smeltTime = buffer.readVarInt();
		
		return this.factory.create(id, group, primary, secondary, output, experience, smeltTime);
	}

	@Override
	public void write(PacketBuffer buffer, T recipe)
	{
		buffer.writeString(recipe.group);
		recipe.primaryInput.write(buffer);
		recipe.secondaryInput.write(buffer);
		buffer.writeItemStack(recipe.output);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.smeltTime);
	}
	
	public interface IFactory<T extends AlloyingRecipe> 
	{
		T create(ResourceLocation id, String group, Ingredient primaryInput, Ingredient secondaryInput, ItemStack output, float experience, int smelttime);
	}
}
