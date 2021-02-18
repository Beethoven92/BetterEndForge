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
	public AnvilSmithingRecipe read(ResourceLocation id, JsonObject json)
	{
		Ingredient input = Ingredient.deserialize(json.get("input"));
		String resultStr = JSONUtils.getString(json, "result");
		ResourceLocation resultId = new ResourceLocation(resultStr);
		ItemStack output = new ItemStack(Registry.ITEM.getOptional(resultId).orElseThrow(() -> {
			return new IllegalStateException("Item: " + resultStr + " does not exists!");
		}));
		int inputCount = JSONUtils.getInt(json, "inputCount", 1);
		int level = JSONUtils.getInt(json, "level", 1);
		int damage = JSONUtils.getInt(json, "damage", 1);
		int anvilLevel = JSONUtils.getInt(json, "anvilLevel", 1);
		
		return new AnvilSmithingRecipe(id, input, output, inputCount, level, damage, anvilLevel);
	}

	@Override
	public AnvilSmithingRecipe read(ResourceLocation id, PacketBuffer buffer) 
	{
		Ingredient input = Ingredient.read(buffer);
		ItemStack output = buffer.readItemStack();
		int inputCount = buffer.readVarInt();
		int level = buffer.readVarInt();
		int damage = buffer.readVarInt();
		int anvilLevel = buffer.readVarInt();
		
		return new AnvilSmithingRecipe(id, input, output, inputCount, level, damage, anvilLevel);
	}

	@Override
	public void write(PacketBuffer buffer, AnvilSmithingRecipe recipe) 
	{	
		recipe.input.write(buffer);
		buffer.writeItemStack(recipe.output);
		buffer.writeVarInt(recipe.inputCount);
		buffer.writeVarInt(recipe.level);
		buffer.writeVarInt(recipe.damage);
		buffer.writeVarInt(recipe.anvilLevel);
	}
}
