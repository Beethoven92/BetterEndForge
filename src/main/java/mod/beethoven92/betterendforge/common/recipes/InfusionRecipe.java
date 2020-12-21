package mod.beethoven92.betterendforge.common.recipes;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
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

}
