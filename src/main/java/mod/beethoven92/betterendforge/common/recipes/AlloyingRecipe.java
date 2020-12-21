package mod.beethoven92.betterendforge.common.recipes;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AlloyingRecipe implements IRecipe<IInventory>
{
	public final static String GROUP = "alloying";
	public final static IRecipeType<AlloyingRecipe> TYPE = ModRecipeSerializers.registerRecipeType(GROUP);
	
	protected final IRecipeType<?> type;
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
	public boolean matches(IInventory inv, World worldIn) 
	{
		return this.primaryInput.test(inv.getStackInSlot(0)) && this.secondaryInput.test(inv.getStackInSlot(1)) ||
				this.primaryInput.test(inv.getStackInSlot(1)) && this.secondaryInput.test(inv.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) 
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
		return ModRecipeSerializers.ALLOYING.get();
	}

	@Override
	public IRecipeType<?> getType() 
	{
		return this.type;
	}
	
	@Override
	public String getGroup() 
	{
		return this.group;
	}
	
	@Override
	public ItemStack getIcon() 
	{
		return new ItemStack(ModBlocks.END_STONE_SMELTER.get());
	}
}
