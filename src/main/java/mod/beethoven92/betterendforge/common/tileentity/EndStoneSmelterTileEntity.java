package mod.beethoven92.betterendforge.common.tileentity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import mod.beethoven92.betterendforge.common.block.EndStoneSmelter;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EndStoneSmelterTileEntity extends LockableTileEntity implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator, ISidedInventory
{
	private static final int[] TOP_SLOTS = new int[] { 0, 1 };
	private static final int[] BOTTOM_SLOTS = new int[] { 2, 3 };
	private static final int[] SIDE_SLOTS = new int[] { 2 };
	private static final Map<Item, Integer> AVAILABLE_FUELS = Maps.newHashMap();
	
	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
	protected NonNullList<ItemStack> items;
	protected final IIntArray data;
	private IRecipe<?> lastRecipe;
	private int smeltTimeTotal;
	private int smeltTime;
	private int burnTime;
	private int fuelTime;

	public EndStoneSmelterTileEntity()
	{
		super(ModTileEntityTypes.END_STONE_SMELTER.get());
		
		this.recipesUsed = new Object2IntOpenHashMap<ResourceLocation>();
		this.items= NonNullList.withSize(4, ItemStack.EMPTY);
		this.data = new IIntArray() 
		{
		 	public int get(int index) 
		 	{
		 		switch(index) 
		 		{
			 		case 0:
			 			return EndStoneSmelterTileEntity.this.burnTime;
			 		case 1:
			 			return EndStoneSmelterTileEntity.this.fuelTime;
			 		case 2:
			 			return EndStoneSmelterTileEntity.this.smeltTime;
			 		case 3:
			 			return EndStoneSmelterTileEntity.this.smeltTimeTotal;
			 		default:
			 			return 0;
		 		}
		 	}

		 	public void set(int index, int value) 
		 	{
		 		switch(index) 
		 		{
			 		case 0:
			 			EndStoneSmelterTileEntity.this.burnTime = value;
			 			break;
			 		case 1:
			 			EndStoneSmelterTileEntity.this.fuelTime = value;
			 			break;
			 		case 2:
			 			EndStoneSmelterTileEntity.this.smeltTime = value;
			 			break;
			 		case 3:
			 			EndStoneSmelterTileEntity.this.smeltTimeTotal = value;
		 		}
		 	}

		 	public int size() {
		 		return 4;
		 	}
		 };
		 
		 this.registerFuels();
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		compound.putShort("BurnTime", (short) burnTime);
		compound.putShort("FuelTime", (short) fuelTime);
		compound.putShort("SmeltTime", (short) smeltTime);
		compound.putShort("SmeltTimeTotal", (short) smeltTimeTotal);
		ItemStackHelper.saveAllItems(compound, this.items);
		CompoundNBT usedRecipes = new CompoundNBT();
		this.recipesUsed.forEach((identifier, integer) -> {
			usedRecipes.putInt(identifier.toString(), integer);
		});
		compound.put("RecipesUsed", usedRecipes);
		
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt)
	{	
		super.read(state, nbt);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, items);
		this.burnTime = nbt.getShort("BurnTime");
		this.fuelTime = nbt.getShort("FuelTime");
		this.smeltTime = nbt.getShort("SmeltTime");
		this.smeltTimeTotal = nbt.getShort("SmeltTimeTotal");
		CompoundNBT compound = nbt.getCompound("RecipesUsed");
		Iterator<String> recipes = compound.keySet().iterator();
		while(recipes.hasNext()) 
		{
			String id = recipes.next();
			this.recipesUsed.put(new ResourceLocation(id), compound.getInt(id));
		}
	}
	
	private void registerFuels() 
	{
		registerFuel(Items.LAVA_BUCKET, 16000);
		registerFuel(Blocks.COAL_BLOCK, 12000);
		registerFuel(Items.BLAZE_ROD, 2000);
	}
	
	private boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	public void dropExperience(PlayerEntity player) 
	{
		List<IRecipe<?>> list = Lists.newArrayList();
		ObjectIterator<Entry<ResourceLocation>> usedRecipes = this.recipesUsed.object2IntEntrySet().iterator();
		while(usedRecipes.hasNext()) 
		{
			Entry<ResourceLocation> entry = usedRecipes.next();
			world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				if (recipe instanceof AlloyingRecipe) 
				{
					AlloyingRecipe alloying = (AlloyingRecipe) recipe;
					this.dropExperience(player.world, player.getPositionVec(), entry.getIntValue(), alloying.getExperience());
				} else 
				{
					BlastingRecipe blasting = (BlastingRecipe) recipe;
					this.dropExperience(player.world, player.getPositionVec(), entry.getIntValue(), blasting.getExperience());
				}
			});
		}
		player.unlockRecipes(list);
		this.recipesUsed.clear();
	}
	
	private void dropExperience(World world, Vector3d vec3d, int i, float f) 
	{
		int j = MathHelper.floor(i * f);
		float g = MathHelper.frac(i * f);
		if (g != 0.0F && Math.random() < g) {
			j++;
		}

		while(j > 0) 
		{
			int k = ExperienceOrbEntity.getXPSplit(j);
			j -= k;
			world.addEntity(new ExperienceOrbEntity(world, vec3d.x, vec3d.y, vec3d.z, k));
		}
	}
	
	@Override
	public void tick() 
	{
		boolean initialBurning = this.isBurning();
		if (initialBurning) 
		{
			this.burnTime--;
		}
		
		boolean burning = this.isBurning();
		if (!this.world.isRemote) 
		{
			ItemStack fuel = this.items.get(2);
			if (!burning && (fuel.isEmpty() || items.get(0).isEmpty() && items.get(1).isEmpty())) 
			{
				if (!burning && smeltTime > 0) 
				{
					this.smeltTime = MathHelper.clamp(smeltTime - 2, 0, smeltTimeTotal);
				}
			} 
			else 
			{
				IRecipe<?> recipe = this.world.getRecipeManager().getRecipe(AlloyingRecipe.TYPE, this, world).orElse(null);
				if (recipe == null) 
				{
					recipe = this.world.getRecipeManager().getRecipe(IRecipeType.BLASTING, this, world).orElse(null);
				}
				boolean accepted = this.canAcceptRecipeOutput(recipe);
				if (!burning && accepted) 
				{
					this.burnTime = this.getBurnTime(fuel);
					this.fuelTime = this.burnTime;
					burning = this.isBurning();
					if (burning)
					{
						if (!fuel.isEmpty()) 
						{
							Item item = fuel.getItem();
							fuel.shrink(1);
							if (fuel.isEmpty()) 
							{
								Item remainFuel = item.getContainerItem();
								this.items.set(2, remainFuel == null ? ItemStack.EMPTY : new ItemStack(remainFuel));
							}
						}
						this.markDirty();
					}
				}

				if (burning && accepted) 
				{
					this.smeltTime++;
					if (smeltTime == smeltTimeTotal) 
					{
						this.smeltTime = 0;
						this.smeltTimeTotal = this.getSmeltTime();
						this.craftRecipe(recipe);
						this.markDirty();
					}
				} 
				else 
				{
					this.smeltTime = 0;
				}
			}

			if (initialBurning != burning) 
			{
				this.world.setBlockState(pos, world.getBlockState(pos).with(EndStoneSmelter.LIT, burning), 3);
				this.markDirty();
			}
		}
	}

	protected boolean canAcceptRecipeOutput(IRecipe<?> recipe) 
	{
		if (recipe == null) return false;
		boolean validInput = false;
		if (recipe instanceof AlloyingRecipe) 
		{
			validInput = !items.get(0).isEmpty() &&
					!items.get(1).isEmpty();
		} 
		else 
		{
			validInput = !items.get(0).isEmpty() ||
					!items.get(1).isEmpty();
		}
		if (validInput) 
		{
			ItemStack result = recipe.getRecipeOutput();
			if (result.isEmpty()) 
			{
				return false;
			} 
			else 
			{
				ItemStack output = this.items.get(3);
				int outCount = output.getCount();
				int total = outCount + result.getCount();
				if (output.isEmpty()) 
				{
					return true;
				} 
				else if (!output.isItemEqualIgnoreDurability(result)) 
				{
					return false;
				} 
				else if (outCount < this.getInventoryStackLimit() && outCount < output.getMaxStackSize()) 
				{
					return this.getInventoryStackLimit() >= total;
				} 
				else 
				{
					return output.getCount() < result.getMaxStackSize();
				}
			}
		}
		
		return false;
	}

	private void craftRecipe(IRecipe<?> recipe) 
	{
		if (recipe == null || !canAcceptRecipeOutput(recipe)) return;
		
		ItemStack result = recipe.getRecipeOutput();
		ItemStack output = this.items.get(3);
		if (output.isEmpty())
		{
			this.items.set(3, result.copy());
		} 
		else if (output.getItem() == result.getItem()) 
		{
			output.grow(result.getCount());
		}

		if (!this.world.isRemote)
		{
			this.setRecipeUsed(recipe);
		}
		
		if (recipe instanceof AlloyingRecipe) 
		{
			this.items.get(0).shrink(1);
			this.items.get(1).shrink(1);
		} 
		else
		{
			if (!this.items.get(0).isEmpty()) 
			{
				this.items.get(0).shrink(1);
			} 
			else 
			{
				this.items.get(1).shrink(1);
			}
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.items.size();
	}

	@Override
	public boolean isEmpty() 
	{
		for(ItemStack itemstack : this.items) 
		{
			if (!itemstack.isEmpty())
			{
				return false;
	        }
	    }		
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		return this.items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{	
		ItemStack itemStack = this.items.get(index);
		boolean stackValid = !stack.isEmpty() && stack.isItemEqualIgnoreDurability(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) 
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		if ((index == 0 || index == 1) && !stackValid) 
		{
			this.smeltTimeTotal = this.getSmeltTime();
			this.smeltTime = 0;
			this.markDirty();
		}
	}
	
	protected int getSmeltTime()
	{
		int smeltTime = this.world.getRecipeManager().getRecipe(AlloyingRecipe.TYPE, this, world)
				.map(AlloyingRecipe::getSmeltTime).orElse(0);
		if (smeltTime == 0) 
		{
			smeltTime = this.world.getRecipeManager().getRecipe(IRecipeType.BLASTING, this, world)
				.map(BlastingRecipe::getCookTime).orElse(200);
			smeltTime /= 1.5;
		}
		return smeltTime;
	}
	
	@Override
	public int[] getSlotsForFace(Direction side) 
	{
		if (side == Direction.DOWN) 
		{
			return BOTTOM_SLOTS;
		} 
		else 
		{
			return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) 
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) 
	{
		if (direction == Direction.DOWN && index == 2) 
		{
			if (stack.getItem() != Items.BUCKET && stack.getItem() != Items.WATER_BUCKET) 
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) 
	{
		if (this.world.getTileEntity(this.pos) != this) 
		{
			return false;
		} 
		else 
		{
			return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clear()
	{
		this.items.clear();
	}

	@Override
	protected ITextComponent getDefaultName() 
	{
		return new TranslationTextComponent("container.betterendforge.end_stone_smelter");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) 
	{
		return new EndStoneSmelterContainer(id, player, this, this.data);
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) 
	{
		for(ItemStack itemstack : this.items) 
		{
			helper.accountStack(itemstack);
		}
	}

	@Override
	public void setRecipeUsed(IRecipe<?> recipe) 
	{
		if (recipe != null) 
		{
			ResourceLocation recipeId = recipe.getId();
			this.recipesUsed.addTo(recipeId, 1);
			this.lastRecipe = recipe;
		}
	}
	   
	@Override
	public IRecipe<?> getRecipeUsed() 
	{
		return this.lastRecipe;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		if (index == 3) 
		{
			return false;
		}
		else if (index != 2) 
		{
			return true;
		} 
		else 
		{
			ItemStack itemStack = this.items.get(2);
			return isFuel(stack) || stack.getItem() == Items.BUCKET && itemStack.getItem() != Items.BUCKET;
		}
	}
	
	public static boolean isFuel(ItemStack stack) 
	{
		return AVAILABLE_FUELS.containsKey(stack.getItem()) && net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
	}
	
	protected int getBurnTime(ItemStack fuel) 
	{
		if (fuel.isEmpty()) 
		{
			return 0;
		} 
		else 
		{
			Item item = fuel.getItem();
			return AVAILABLE_FUELS.getOrDefault(item, 0);
		}
	}
	
	public static void registerFuel(IItemProvider fuel, int time) 
	{
		if (AVAILABLE_FUELS.containsKey(fuel))
		{
			AVAILABLE_FUELS.replace(fuel.asItem(), time);
		} 
		else 
		{
			AVAILABLE_FUELS.put(fuel.asItem(), time);
		}
	}
	
	public static void registerFuel(ITag<Item> tag, int time)
	{
		Iterator<Item> tagItems = tag.getAllElements().iterator();
		tagItems.forEachRemaining(item -> registerFuel(item, time));
	}
	
	public static Map<Item, Integer> getAvailableFuels() 
	{
		return AVAILABLE_FUELS;
	}
	
	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
			net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing)
	{
		if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (facing == Direction.UP) return handlers[0].cast();
			else if (facing == Direction.DOWN) return handlers[1].cast();
			else return handlers[2].cast();
	    }
		return super.getCapability(capability, facing);
	}

	@Override
    protected void invalidateCaps() 
	{
		super.invalidateCaps();
	    for (int x = 0; x < handlers.length; x++) handlers[x].invalidate();
	}
}
