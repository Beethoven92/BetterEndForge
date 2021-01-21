package mod.beethoven92.betterendforge.common.recipes;

import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class InfusionRecipeBuilder 
{
    private final Ingredient input;
    private final ItemStack output;
    private final int time;
    private final Map<Integer, Item> catalysts = Maps.newHashMap();
    
	public InfusionRecipeBuilder(ItemStack output, IItemProvider input, int time)
	{
		this.output = output;
		this.input = Ingredient.fromItems(input);
		this.time = time;
	}
	
	public static InfusionRecipeBuilder infusionRecipe(ItemStack output, IItemProvider input, int time) 
	{
		return new InfusionRecipeBuilder(output, input, time);
	}

	public InfusionRecipeBuilder addCatalyst(int index, IItemProvider item) 
	{
		if (index < 8 && index >= 0) catalysts.put(index, item.asItem());
		return this;
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) 
	{
		this.build(consumerIn, Registry.ITEM.getKey(this.output.getItem()));
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, String save) 
	{
		ResourceLocation resourcelocation = Registry.ITEM.getKey(this.output.getItem());
		if ((new ResourceLocation(save)).equals(resourcelocation))
		{
			throw new IllegalStateException("Infusion Recipe " + save + " should remove its 'save' argument");
		} 
		else 
		{
			this.build(consumerIn, new ResourceLocation(save));
		}
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) 
	{
		//this.validate(id);
		//this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
		consumerIn.accept(new InfusionRecipeBuilder.Result(id, this.input, this.output, this.time, this.catalysts));//, new ResourceLocation(id.getNamespace(), "recipes/" + this.output.getGroup().getPath() + "/" + id.getPath())));
	}
	
	public static class Result implements IFinishedRecipe
	{
	    private final ResourceLocation id;
	    private final Ingredient input;
	    private final ItemStack output;
	    private final int time;
	    private final Map<Integer, Item> catalysts;
	    
	    public Result(ResourceLocation id, Ingredient input, ItemStack output, int time, Map<Integer, Item> catalysts) 
	    {
	    	this.id = id;
	    	this.input = input;
	    	this.output = output;
	    	this.time = time;
	    	this.catalysts = catalysts;
		}
	    
		@Override
		public void serialize(JsonObject json) 
		{       
			json.add("input", input.serialize());

	        json.addProperty("output", Registry.ITEM.getKey(this.output.getItem()).toString());
	        
	        json.addProperty("time", time);
	        
	        JsonArray catalystInputs = new JsonArray();

	        for (int index : catalysts.keySet())
	        {
	        	JsonObject catalyst = new JsonObject();
	        	catalyst.addProperty("item", Registry.ITEM.getKey(catalysts.get(index)).toString());
	        	catalyst.addProperty("index", index);
	        	catalystInputs.add(catalyst);
	        }
	        json.add("catalysts", catalystInputs);
		}

		@Override
		public ResourceLocation getID() 
		{
			return id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() 
		{
			return ModRecipeSerializers.INFUSION.get();
		}

		@Override
		public JsonObject getAdvancementJson() 
		{
			return null;
		}

		@Override
		public ResourceLocation getAdvancementID() 
		{
			return null;
		}		
	}
}
