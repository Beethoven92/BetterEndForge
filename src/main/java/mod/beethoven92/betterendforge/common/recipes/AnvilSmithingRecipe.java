package mod.beethoven92.betterendforge.common.recipes;

import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AnvilSmithingRecipe implements IRecipe<IInventory>
{
	public final static String GROUP = "anvil_smithing";
	public final static IRecipeType<AnvilSmithingRecipe> TYPE = ModRecipeSerializers.registerRecipeType(GROUP);
	
	private final ResourceLocation id;
	public final Ingredient input;
	public final ItemStack output;
	public final int damage;
	public final int inputCount;
	public final int level;
	public final int anvilLevel;
	
	public AnvilSmithingRecipe(ResourceLocation identifier, Ingredient input, ItemStack output, int inputCount, 
			int level, int damage, int anvilLevel) 
	{
		this.id = identifier;
		this.input = input;
		this.output = output;
		this.inputCount = inputCount;
		this.level = level;
		this.damage = damage;
		this.anvilLevel = anvilLevel;
	}
	
	@Override
	public boolean matches(IInventory inv, World worldIn) 
	{
		ItemStack hammer = inv.getItem(0);
		if (hammer.isEmpty() || !ModTags.HAMMERS.contains(hammer.getItem())) 
		{
			return false;
		}
		ItemStack material = inv.getItem(1);
		int materialCount = material.getCount();
		
		int level = ((ToolItem)hammer.getItem()).getTier().getLevel();
		return level >= this.level && this.input.test(inv.getItem(1)) && materialCount >= this.inputCount;
	}
	
	public boolean checkHammerDurability(IInventory craftingInventory, PlayerEntity player) 
	{
		if (player.isCreative()) return true;
		ItemStack hammer = craftingInventory.getItem(0);
		int damage = hammer.getDamageValue() + this.damage;
		return damage < hammer.getMaxDamage();
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() 
	{
		NonNullList<Ingredient> defaultedList = NonNullList.create();

		defaultedList.add(Ingredient.of(ModTags.HAMMERS.getValues().stream().filter(hammer -> {
			return ((ToolItem) hammer).getTier().getLevel() >= level;
		}).map(ItemStack::new)));

		// Needed for JEI to display the amount of input items required by this recipe
		ItemStack amount = new ItemStack(input.getItems()[0].getItem(), inputCount);
		defaultedList.add(Ingredient.of(amount));

		return defaultedList;
	}
	
	@Override
	public ItemStack assemble(IInventory inv) 
	{
		return this.output.copy();
	}
	
	public ItemStack craft(IInventory craftingInventory, PlayerEntity player) 
	{
		if (!player.isCreative()) 
		{
			if (!checkHammerDurability(craftingInventory, player)) return ItemStack.EMPTY;
			
			ItemStack hammer = craftingInventory.getItem(0);
			hammer.hurtAndBreak(this.damage, player, entity -> {
				entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
		}
		return this.assemble(craftingInventory);
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
	public IRecipeSerializer<?> getSerializer() 
	{
		return ModRecipeSerializers.ANVIL_SMITHING.get();
	}

	@Override
	public IRecipeType<?> getType() 
	{
		return TYPE;
	}
	
	@Override
	public boolean isSpecial() 
	{
		return true;
	}
}
