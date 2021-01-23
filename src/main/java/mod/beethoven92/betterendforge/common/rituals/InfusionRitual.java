package mod.beethoven92.betterendforge.common.rituals;

import java.awt.Point;

import mod.beethoven92.betterendforge.common.particles.InfusionParticleData;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import mod.beethoven92.betterendforge.common.tileentity.InfusionPedestalTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class InfusionRitual implements IInventory
{
	private static final Point[] PEDESTALS_MAP = new Point[] {
			new Point(0, 3), new Point(2, 2), new Point(3, 0), new Point(2, -2),
			new Point(0, -3), new Point(-2, -2), new Point(-3, 0), new Point(-2, 2)
		};

	private World world;
	private BlockPos worldPos;
	private InfusionRecipe activeRecipe;
	private boolean isDirty = false;
	private boolean hasRecipe = false;
	private int progress = 0;
	private int time = 0;
		
	private InfusionPedestalTileEntity input;
	private PedestalTileEntity[] catalysts = new PedestalTileEntity[8];
	
	public InfusionRitual(World world, BlockPos pos) 
	{
		this.world = world;
		this.worldPos = pos;
		this.configure();
	}
	
	public static Point[] getMap() 
	{
		return PEDESTALS_MAP;
	}
	
	public void configure() 
	{
		if (world == null || world.isRemote || worldPos == null) return;
		TileEntity inputEntity = world.getTileEntity(worldPos);
		if (inputEntity instanceof InfusionPedestalTileEntity) 
		{
			this.input = (InfusionPedestalTileEntity) inputEntity;
		}
		int i = 0;
		for(Point point : PEDESTALS_MAP) 
		{
			BlockPos.Mutable checkPos = worldPos.toMutable().move(Direction.EAST, point.x).move(Direction.NORTH, point.y);
			TileEntity catalystEntity = world.getTileEntity(checkPos);
			if (catalystEntity instanceof PedestalTileEntity) 
			{
				catalysts[i] = (PedestalTileEntity) catalystEntity;
				i++;
			} 
			else 
			{
				break;
			}
		}
	}
	
	public boolean checkRecipe() 
	{
		if (!isValid()) return false;
		InfusionRecipe recipe = this.world.getRecipeManager().getRecipe(InfusionRecipe.TYPE, this, world).orElse(null);
		if (hasRecipe()) 
		{
			if (recipe == null) 
			{
				this.stop();
				return false;
			} 
			else if (recipe.getInfusionTime() != time) 
			{
				this.activeRecipe = recipe;
				this.time = this.activeRecipe.getInfusionTime();
				this.progress = 0;
				this.markDirty();
			} 
			else if (activeRecipe == null) 
			{
				this.activeRecipe = recipe;
			}
			return true;
		}
		if (recipe != null) 
		{
			this.activeRecipe = recipe;
			this.time = this.activeRecipe.getInfusionTime();
			this.hasRecipe = true;
			this.progress = 0;
			this.markDirty();
			return true;
		}
		return false;
	}
	
	public void stop() 
	{
		this.activeRecipe = null;
		this.hasRecipe = false;
		this.progress = 0;
		this.time = 0;
		this.markDirty();
	}
	
	public void tick() 
	{
		if (isDirty)
		{
			this.configure();
			this.isDirty = false;
		}
		if (!isValid() || !hasRecipe()) return;
		if (!checkRecipe()) return;
		this.progress++;
		if (progress == time) 
		{
			BlockState inputState = world.getBlockState(input.getPos());
			this.input.removeStack(world, inputState);
			this.input.setStack(activeRecipe.getCraftingResult(this));
			for (PedestalTileEntity catalyst : catalysts) 
			{
				catalyst.removeStack(world, world.getBlockState(catalyst.getPos()));
			}
			this.stop();
		} 
		else 
		{
			ServerWorld world = (ServerWorld) this.world;
			BlockPos target = this.worldPos.up();
			double tx = target.getX() + 0.5;
			double ty = target.getY() + 0.5;
			double tz = target.getZ() + 0.5;
			for (PedestalTileEntity catalyst : catalysts) 
			{
				ItemStack stack = catalyst.getStack();
				if (!stack.isEmpty()) 
				{
					BlockPos start = catalyst.getPos();
					double sx = start.getX() + 0.5;
					double sy = start.getY() + 1.25;
					double sz = start.getZ() + 0.5;					
					world.spawnParticle(new InfusionParticleData(stack), sx, sy, sz, 0, tx - sx, ty - sy, tz - sz, 0.5);
				}
			}
		}		
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		return this.isValid();
	}
	
	public boolean isValid()
	{
		if (world == null || world.isRemote || worldPos == null || input == null) return false;
		for (PedestalTileEntity catalyst : catalysts) 
		{
			if (catalyst == null) return false;
		}
		return true;
	}
	
	public boolean hasRecipe() 
	{
		return this.hasRecipe;
	}

	public void setLocation(World world, BlockPos pos) 
	{
		this.world = world;
		this.worldPos = pos;
		this.isDirty = true;
	}
	
	@Override
	public void clear() 
	{
		if (!isValid()) return;
		this.input.clear();
		for (PedestalTileEntity catalyst : catalysts)
		{
			catalyst.clear();
		}
	}

	@Override
	public int getSizeInventory() 
	{
		return 9;
	}

	@Override
	public boolean isEmpty() 
	{
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		if (index > 8) return ItemStack.EMPTY;
		if (index== 0) 
		{
			return this.input.getStack();
		} 
		else
		{
			return this.catalysts[index - 1].getStack();
		}
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		return this.removeStackFromSlot(index);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (index > 8) return ItemStack.EMPTY;
		if (index == 0) 
		{
			return this.input.removeStack();
		} 
		else 
		{
			return this.catalysts[index - 1].getStack();
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
		if (index > 8) return;
		if (index == 0) 
		{
			this.input.setStack(stack);
		} 
		else 
		{
			this.catalysts[index - 1].setStack(stack);
		}
	}

	@Override
	public void markDirty() 
	{	
		if (isValid()) 
		{
			this.input.markDirty();
			for (PedestalTileEntity catalyst : catalysts) 
			{
				catalyst.markDirty();
			}
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) 
	{
		return true;
	}

	public void read(CompoundNBT tag) 
	{
		if (tag.contains("recipe")) 
		{
			this.hasRecipe = tag.getBoolean("recipe");
			this.progress = tag.getInt("progress");
			this.time = tag.getInt("time");
		}
	}

	public CompoundNBT write(CompoundNBT tag)
	{
		if (hasRecipe()) 
		{
			tag.putBoolean("recipe", this.hasRecipe);
			tag.putInt("progress", progress);
			tag.putInt("time", time);
		}
		return tag;
	}
}
