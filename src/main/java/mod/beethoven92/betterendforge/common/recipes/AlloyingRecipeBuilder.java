package mod.beethoven92.betterendforge.common.recipes;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class AlloyingRecipeBuilder 
{	
	private ResourceLocation id;
	private Ingredient primaryInput;
	private Ingredient secondaryInput;
	private Item output;
	private String group;
	private float experience;
	private int smeltTime;
	private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
	private final AlloyingRecipeSerializer<?> recipeSerializer;
	//private boolean alright = true;

	private AlloyingRecipeBuilder(IItemProvider output, Ingredient primary, Ingredient secondary, float experienceIn,
			int smeltTime, AlloyingRecipeSerializer<?> serializer) 
	{
		this.output = output.asItem();
	    this.primaryInput = primary;
	    this.secondaryInput = secondary;
		this.experience = experienceIn;
		this.smeltTime = smeltTime;
		this.recipeSerializer = serializer;
	}
	
	public static AlloyingRecipeBuilder alloyingRecipe(IItemProvider output, Ingredient primary, Ingredient secondary, 
			float experienceIn,int smeltTime)
	{
		return new AlloyingRecipeBuilder(output, primary, secondary, experienceIn, smeltTime, ModRecipeSerializers.ALLOYING.get());
	}
	
	public AlloyingRecipeBuilder addCriterion(String name, ICriterionInstance criterionIn) 
	{
		this.advancementBuilder.withCriterion(name, criterionIn);
		return this;
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) 
	{
		this.build(consumerIn, Registry.ITEM.getKey(this.output));
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, String save) 
	{
		ResourceLocation resourcelocation = Registry.ITEM.getKey(this.output);
		ResourceLocation resourcelocation1 = new ResourceLocation(save);
		if (resourcelocation1.equals(resourcelocation)) 
		{
			throw new IllegalStateException("Recipe " + resourcelocation1 + " should remove its 'save' argument");
		} 
		else 
		{
			this.build(consumerIn, resourcelocation1);
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) 
	{
		this.validate(id);
		this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
		consumerIn.accept(new AlloyingRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.primaryInput, this.secondaryInput, this.output, this.experience, this.smeltTime, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.output.getGroup().getPath() + "/" + id.getPath()), this.recipeSerializer));
	}
	
	private void validate(ResourceLocation id) 
	{
		if (this.advancementBuilder.getCriteria().isEmpty()) 
		{
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
	
	public static class Result implements IFinishedRecipe 
	{
		private final ResourceLocation id;
		private final String group;
		private final Ingredient primaryInput;
		private final Ingredient secondaryInput;
		private final Item output;
		private final float experience;
		private final int smeltTime;
		private final Advancement.Builder advancementBuilder;
		private final ResourceLocation advancementId;
		private final IRecipeSerializer<? extends AlloyingRecipe> serializer;

		public Result(ResourceLocation id, String group, Ingredient primary, Ingredient secondary, Item output, 
				float experience, int smeltTime, Advancement.Builder advancementBuilder, 
				ResourceLocation advancementId, IRecipeSerializer<? extends AlloyingRecipe> serializer) 
		{
			this.id = id;
			this.group = group;
			this.primaryInput = primary;
			this.secondaryInput = secondary;
			this.output = output;
			this.experience = experience;
			this.smeltTime = smeltTime;
			this.advancementBuilder = advancementBuilder;
			this.advancementId = advancementId;
			this.serializer = serializer;
		}

		public void serialize(JsonObject json) 
		{
			if (!this.group.isEmpty()) 
			{
				json.addProperty("group", this.group);
		    }

			json.add("primary", this.primaryInput.serialize());
			json.add("secondary", this.secondaryInput.serialize());
			json.addProperty("output", Registry.ITEM.getKey(this.output).toString());
			json.addProperty("experience", this.experience);
			json.addProperty("smelttime", this.smeltTime);
		}

		public IRecipeSerializer<?> getSerializer() 
		{
			return this.serializer;
		}

		public ResourceLocation getID()
		{
			return this.id;
		}
		
		@Nullable
		public JsonObject getAdvancementJson() 
		{
			return this.advancementBuilder.serialize();
		}

		@Nullable
		public ResourceLocation getAdvancementID() 
		{
			return this.advancementId;
		}
	}
}
