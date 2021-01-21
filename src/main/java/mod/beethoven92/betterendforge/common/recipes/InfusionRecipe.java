package mod.beethoven92.betterendforge.common.recipes;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfusionRecipe implements IRecipe<InfusionRitual>
{
	public final static String GROUP = "infusion";
	public final static IRecipeType<InfusionRecipe> TYPE = ModRecipeSerializers.registerRecipeType(GROUP);
	
	public final ResourceLocation id;
	public Ingredient input;
	public ItemStack output;
	public int time = 1;
	public Ingredient[] catalysts = new Ingredient[8];
	public Map<Integer, Ingredient> ingredientPositions = Maps.newHashMap();
	
	public InfusionRecipe(ResourceLocation id) 
	{
		this(id, null, null);
	}
	
	public InfusionRecipe(ResourceLocation id, Ingredient input, ItemStack output) 
	{
		this.id = id;
		this.input = input;
		this.output = output;
		Arrays.fill(catalysts, Ingredient.EMPTY);
	}
	
	public int getInfusionTime() 
	{
		return this.time;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() 
	{
		NonNullList<Ingredient> list = NonNullList.create();
		list.add(input);
		for (Ingredient catalyst : catalysts) 
		{
			list.add(catalyst);
		}
		return list;
	}
	
	@Override
	public boolean matches(InfusionRitual inv, World worldIn) 
	{
		boolean valid = this.input.test(inv.getStackInSlot(0));
		if (!valid) return false;
		for (int i = 0; i < 8; i++) 
		{
			valid &= this.catalysts[i].test(inv.getStackInSlot(i + 1));
		}
		return valid;
	}

	@Override
	public ItemStack getCraftingResult(InfusionRitual inv) 
	{
		return this.output.copy();
	}
	
	@Override
	public boolean canFit(int width, int height) 
	{
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() 
	{
		return this.output;
	}

	@Override
	public ResourceLocation getId() 
	{
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() 
	{
		return ModRecipeSerializers.INFUSION.get();
	}

	@Override
	public IRecipeType<?> getType() 
	{
		return TYPE;
	}

	// Used to create recipes that cannot be defined by a json.
	// Eg. create a recipe which has input/ouput ingredients containing NBT data (enchanted books, potions etc..)
	public static class Builder 
	{
		private final static Builder INSTANCE = new Builder();
		//private static boolean exist;
		
		public static Builder create(String id) 
		{
			return create(new ResourceLocation(BetterEnd.MOD_ID, id));
		}
		
		public static Builder create(ResourceLocation id) 
		{
			INSTANCE.id = id;
			INSTANCE.input = null;
			INSTANCE.output = null;
			INSTANCE.time = 1;
			
			Arrays.fill(INSTANCE.catalysts, Ingredient.EMPTY);
			
			return INSTANCE;
		}
		
		private ResourceLocation id;
		private Ingredient input;
		private ItemStack output;
		//private String group;
		private int time = 1;
		private Ingredient[] catalysts = new Ingredient[8];
		
		private Builder() 
		{
			Arrays.fill(catalysts, Ingredient.EMPTY);
		}
		
		public Builder setGroup(String group) 
		{
			//this.group = group;
			return this;
		}
		
		public Builder setInput(IItemProvider input) 
		{
			this.input = Ingredient.fromItems(input);
			return this;
		}
		
		public Builder setOutput(IItemProvider output) 
		{
			this.output = new ItemStack(output);
			this.output.setCount(1);
			return this;
		}
		
		public Builder setOutput(ItemStack output) 
		{
			this.output = output;
			this.output.setCount(1);
			return this;
		}
		
		public Builder setTime(int time) 
		{
			this.time = time;
			return this;
		}
		
		public Builder addCatalyst(int slot, IItemProvider... items) 
		{
			if (slot > 7) return this;
			this.catalysts[slot] = Ingredient.fromItems(items);
			return this;
		}
		
		public void build() 
		{
			if (input == null)
			{
				BetterEnd.LOGGER.warn("BETTER_END_FORGE: Input for Infusion recipe can't be 'null', recipe {} will be ignored!", id);
				return;
			}
			if (output == null) 
			{
				BetterEnd.LOGGER.warn("BETTER_END_FORGE: Output for Infusion recipe can't be 'null', recipe {} will be ignored!", id);
				return;
			}
			InfusionRecipe recipe = new InfusionRecipe(id, input, output);
			//recipe.group = group != null ? group : GROUP;
			recipe.time = time;
			int empty = 0;
			for (int i = 0; i < catalysts.length; i++) 
			{
				if (catalysts[i].hasNoMatchingItems()) empty++;
				else recipe.catalysts[i] = catalysts[i];
			}
			if (empty == catalysts.length) 
			{
				BetterEnd.LOGGER.warn("BETTER_END_FORGE: At least one catalyst must be non empty, recipe {} will be ignored!", id);
				return;
			}

			ModRecipeManager.addRecipe(TYPE, recipe);
		}
	}
}
