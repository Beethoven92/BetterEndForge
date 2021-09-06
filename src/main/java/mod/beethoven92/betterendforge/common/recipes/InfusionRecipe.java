package mod.beethoven92.betterendforge.common.recipes;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfusionRecipe implements IRecipe<InfusionRitual> {
	public final static String GROUP = "infusion";
	public final static IRecipeType<InfusionRecipe> TYPE = ModRecipeSerializers.registerRecipeType(GROUP);

	public final ResourceLocation id;
	public Ingredient input;
	public ItemStack output;
	public int time = 1;
	public Ingredient[] catalysts = new Ingredient[8];
	public Map<Integer, Ingredient> ingredientPositions = Maps.newHashMap();

	public InfusionRecipe(ResourceLocation id) {
		this(id, null, null);
	}

	public InfusionRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
		this.id = id;
		this.input = input;
		this.output = output;
		Arrays.fill(catalysts, Ingredient.EMPTY);
	}

	public int getInfusionTime() {
		return this.time;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		list.add(input);
		for (Ingredient catalyst : catalysts) {
			list.add(catalyst);
		}
		return list;
	}

	@Override
	public boolean matches(InfusionRitual inv, World worldIn) {
		boolean valid = this.input.test(inv.getStackInSlot(0));
		if (!valid)
			return false;
		for (int i = 0; i < 8; i++) {
			valid &= this.catalysts[i].test(inv.getStackInSlot(i + 1));
		}
		return valid;
	}

	@Override
	public ItemStack getCraftingResult(InfusionRitual inv) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.INFUSION.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return TYPE;
	}

	public static class Builder {

		public static Builder create() {
			return new Builder();
		}

		private Ingredient input;
		private ItemStack output;
		private int time = 1;
		private Ingredient[] catalysts = new Ingredient[8];

		private Builder() {
			Arrays.fill(catalysts, Ingredient.EMPTY);
		}

		public Builder setInput(IItemProvider input) {
			this.input = Ingredient.fromItems(input);
			return this;
		}

		public Builder setOutput(IItemProvider output) {
			this.output = new ItemStack(output);
			this.output.setCount(1);
			return this;
		}

		public Builder setOutput(ItemStack output) {
			this.output = output;
			this.output.setCount(1);
			return this;
		}

		public Builder setTime(int time) {
			this.time = time;
			return this;
		}
		
		public Builder setGroup(String group) {
			return this;
		}

		public Builder addCatalyst(int slot, IItemProvider... items) {
			if (slot > 7)
				return this;
			this.catalysts[slot] = Ingredient.fromItems(items);
			return this;
		}

		public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
			validate(id);
			consumerIn.accept(new Result(id, input, output, time, catalysts));
		}

		private void validate(ResourceLocation id) {
			if (input == null)
				Illegal("Input for Infusion recipe can't be null, recipe %s", id);
			if (output == null)
				Illegal("Output for Infusion recipe can't be null, recipe %s", id);
			int empty = 0;
			for (int i = 0; i < catalysts.length; i++) {
				if (catalysts[i].hasNoMatchingItems())
					empty++;
			}
			if (empty == catalysts.length) {
				Illegal("At least one catalyst must be non empty, recipe %s", id);
			}
		}
		
		private void Illegal(String s, ResourceLocation id) {
			throw new IllegalArgumentException(String.format(s, id.toString()));
		}

		public static class Result implements IFinishedRecipe {
			private ResourceLocation id;
			private Ingredient input;
			private ItemStack output;
			private int time;
			private Ingredient[] catalysts = new Ingredient[8];
			
			private Result(ResourceLocation id, Ingredient input, ItemStack output, int time, Ingredient[] catalysts) {
				this.id = id;
				this.input = input;
				this.output = output;
				this.time = time;
				this.catalysts = catalysts;
			}

			@Override
			public void serialize(JsonObject json) {
				json.add("input", input.serialize());
				json.add("output", ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, output).result().get());
				json.addProperty("time", time);
				JsonArray jsonCatalysts = new JsonArray();
				for (int i = 0; i < catalysts.length; i++) {
					if (catalysts[i] == Ingredient.EMPTY)
						continue;
					JsonObject catalyst = new JsonObject();
					catalyst.add("item", catalysts[i].serialize());
					catalyst.addProperty("index", i);
					jsonCatalysts.add(catalyst);
				}
				json.add("catalysts", jsonCatalysts);
			}

			@Override
			public ResourceLocation getID() {
				return id;
			}

			@Override
			public IRecipeSerializer<?> getSerializer() {
				return ModRecipeSerializers.INFUSION.get();
			}

			@Override
			public JsonObject getAdvancementJson() {
				return null;
			}

			@Override
			public ResourceLocation getAdvancementID() {
				return null;
			}

		}
	}
}
