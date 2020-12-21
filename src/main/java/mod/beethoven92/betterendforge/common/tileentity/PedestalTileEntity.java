package mod.beethoven92.betterendforge.common.tileentity;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PedestalTileEntity extends TileEntity implements ITickableTileEntity
{
	private ItemStack activeItem = ItemStack.EMPTY;
	
	private final int maxAge = 314;
	private int age;
	
	public PedestalTileEntity() 
	{
		super(ModTileEntityTypes.PEDESTAL.get());
	}
	
	public PedestalTileEntity(TileEntityType<?> tileEntityTypeIn) 
	{
		super(tileEntityTypeIn);
	}
	
	public int getAge() 
	{
		return this.age;
	}
	
	public int getMaxAge() 
	{
		return this.maxAge;
	}
	
	public void clear() 
	{
		activeItem = ItemStack.EMPTY;
	}
	
	public boolean isEmpty()
	{
		return activeItem.isEmpty();
	}
	
	public ItemStack getStack()
	{
		return activeItem;
	}
	
	public void setStack(ItemStack split) 
	{
		this.activeItem = split;
		this.markDirty();
		this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public void removeStack(World world, BlockState state) 
	{
		world.setBlockState(pos, state.with(PedestalBlock.HAS_ITEM, false).with(PedestalBlock.HAS_LIGHT, false));
		this.activeItem = ItemStack.EMPTY;
		this.markDirty();
	}

	public ItemStack removeStack() 
	{
		ItemStack stored = this.activeItem;
		this.activeItem = ItemStack.EMPTY;
		this.markDirty();
		return stored;
	}
	
	@Override
	public void markDirty() 
	{
		if (world != null && !world.isRemote) 
		{
			BlockState state = world.getBlockState(pos);
			if (state.getBlock() instanceof PedestalBlock)
			{
				state = state.with(PedestalBlock.HAS_ITEM, !isEmpty());
				if (activeItem.getItem() == ModItems.ETERNAL_CRYSTAL.get()) 
				{
					state = state.with(PedestalBlock.HAS_LIGHT, true);
				} 
				else 
				{
					state = state.with(PedestalBlock.HAS_LIGHT, false);
				}
				world.setBlockState(pos, state);
			}
		}
		super.markDirty();
	}
	
	@Override
	public void tick() 
	{	
		if (!isEmpty()) 
		{
			this.age++;
			if (age > maxAge) 
			{
				this.age = 0;
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() 
	{
		return 256.0D;
	}
	
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
	    write(nbtTagCompound);
	    int tileEntityType = 42;
	    return new SUpdateTileEntityPacket(this.pos, tileEntityType, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) 
	{
		BlockState blockState = world.getBlockState(pos);
	    read(blockState, pkt.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
	    write(nbtTagCompound);
	    return nbtTagCompound;
	}
	
	@Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT parentNBTTagCompound)
	{
		this.read(blockState, parentNBTTagCompound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		CompoundNBT itemStackNBT = new CompoundNBT();
		if (!activeItem.isEmpty())
		{
			activeItem.write(itemStackNBT);
			compound.put("activeItem", itemStackNBT);
		}
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) 
	{
		super.read(state, nbt);
        if (nbt.contains("activeItem")) 
        {
            activeItem = ItemStack.read(nbt.getCompound("activeItem"));
        }
        else 
        {
            activeItem = ItemStack.EMPTY;
        }
	}
}
