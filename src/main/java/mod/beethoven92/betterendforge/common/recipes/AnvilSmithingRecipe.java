package mod.beethoven92.betterendforge.common.recipes;

import mod.beethoven92.betterendforge.common.init.ModRecipeSerializers;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class AnvilSmithingRecipe implements Recipe<Container>
{
	public final static String GROUP = "anvil_smithing";
	public final static RecipeType<AnvilSmithingRecipe> TYPE = ModRecipeSerializers.registerRecipeType(GROUP);
	
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
	public boolean matches(Container inv, Level worldIn) 
	{
		ItemStack hammer = inv.getItem(0);
		if (hammer.isEmpty() || !ModTags.HAMMERS.contains(hammer.getItem())) 
		{
			return false;
		}
		ItemStack material = inv.getItem(1);
		int materialCount = material.getCount();
		
		int level = ((DiggerItem)hammer.getItem()).getTier().getLevel();
		return level >= this.level && this.input.test(inv.getItem(1)) && materialCount >= this.inputCount;
	}
	
	public boolean checkHammerDurability(Container craftingInventory, Player player) 
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
			return ((DiggerItem) hammer).getTier().getLevel() >= level;
		}).map(ItemStack::new)));

		// Needed for JEI to display the amount of input items required by this recipe
		ItemStack amount = new ItemStack(input.getItems()[0].getItem(), inputCount);
		defaultedList.add(Ingredient.of(amount));

		return defaultedList;
	}
	
	@Override
	public ItemStack assemble(Container inv) 
	{
		return this.output.copy();
	}
	
	public ItemStack craft(Container craftingInventory, Player player) 
	{
		if (!player.isCreative()) 
		{
			if (!checkHammerDurability(craftingInventory, player)) return ItemStack.EMPTY;
			
			ItemStack hammer = craftingInventory.getItem(0);
			hammer.hurtAndBreak(this.damage, player, entity -> {
				entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
	public RecipeSerializer<?> getSerializer() 
	{
		return ModRecipeSerializers.ANVIL_SMITHING.get();
	}

	@Override
	public RecipeType<?> getType() 
	{
		return TYPE;
	}
	
	@Override
	public boolean isSpecial() 
	{
		return true;
	}
}
