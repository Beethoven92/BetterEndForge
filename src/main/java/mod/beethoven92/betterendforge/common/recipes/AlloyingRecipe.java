package mod.beethoven92.betterendforge.common.recipes;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class AlloyingRecipe implements Recipe<Container>
{
	public final static String GROUP = "alloying";
	public final static RecipeType<AlloyingRecipe> TYPE = ModRecipeSerializers.registerRecipeType(GROUP);
	
	protected final RecipeType<?> type;
	protected final ResourceLocation id;
	protected final Ingredient primaryInput;
	protected final Ingredient secondaryInput;
	protected final ItemStack output;
	protected final String group;
	protected final float experience;
	protected final int smeltTime;
	
	public AlloyingRecipe(ResourceLocation id, String group, Ingredient primary, Ingredient secondary, ItemStack output, 
			float experience, int smeltTime) 
	{
		this.group = group;
		this.id = id;
		this.primaryInput = primary;
		this.secondaryInput = secondary;
		this.output = output;
		this.experience = experience;
		this.smeltTime = smeltTime;
		this.type = TYPE;
	}
	
	public float getExperience() 
	{
		return this.experience;
	}
	
	public int getSmeltTime() 
	{
		return this.smeltTime;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() 
	{
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(primaryInput);
		defaultedList.add(secondaryInput);
		
		return defaultedList;
	}
	
	@Override
	public boolean matches(Container inv, Level worldIn) 
	{
		return this.primaryInput.test(inv.getItem(0)) && this.secondaryInput.test(inv.getItem(1)) ||
				this.primaryInput.test(inv.getItem(1)) && this.secondaryInput.test(inv.getItem(0));
	}

	@Override
	public ItemStack assemble(Container inv) 
	{
		return this.output.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	}

	@Override
	public ItemStack getResultItem() 
	{
		return this.output;
	}

	@Override
	public ResourceLocation getId() 
	{
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() 
	{
		return ModRecipeSerializers.ALLOYING.get();
	}

	@Override
	public RecipeType<?> getType() 
	{
		return this.type;
	}
	
	@Override
	public String getGroup() 
	{
		return this.group;
	}
	
	@Override
	public ItemStack getToastSymbol() 
	{
		return new ItemStack(ModBlocks.END_STONE_SMELTER.get());
	}
}
