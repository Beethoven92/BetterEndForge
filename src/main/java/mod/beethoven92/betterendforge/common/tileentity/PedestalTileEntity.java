package mod.beethoven92.betterendforge.common.tileentity;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PedestalTileEntity extends BlockEntity implements TickableBlockEntity
{
	private ItemStack activeItem = ItemStack.EMPTY;
	
	private final int maxAge = 314;
	private int age;
	
	public PedestalTileEntity() 
	{
		super(ModTileEntityTypes.PEDESTAL.get());
	}
	
	public PedestalTileEntity(BlockEntityType<?> tileEntityTypeIn) 
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
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public void removeStack(Level world, BlockState state) 
	{
		world.setBlockAndUpdate(worldPosition, state.setValue(PedestalBlock.HAS_ITEM, false).setValue(PedestalBlock.HAS_LIGHT, false));
		this.activeItem = ItemStack.EMPTY;
		this.setChanged();
	}

	public ItemStack removeStack() 
	{
		ItemStack stored = this.activeItem;
		this.activeItem = ItemStack.EMPTY;
		this.setChanged();
		return stored;
	}
	
	@Override
	public void setChanged() 
	{
		if (level != null && !level.isClientSide) 
		{
			BlockState state = level.getBlockState(worldPosition);
			if (state.getBlock() instanceof PedestalBlock)
			{
				state = state.setValue(PedestalBlock.HAS_ITEM, !isEmpty());
				if (activeItem.getItem() == ModItems.ETERNAL_CRYSTAL.get()) 
				{
					state = state.setValue(PedestalBlock.HAS_LIGHT, true);
				} 
				else 
				{
					state = state.setValue(PedestalBlock.HAS_LIGHT, false);
				}
				level.setBlockAndUpdate(worldPosition, state);
			}
		}
		super.setChanged();
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
	public double getViewDistance() 
	{
		return 256.0D;
	}
	
	@Override
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
	    save(nbtTagCompound);
	    int tileEntityType = 42;
	    return new ClientboundBlockEntityDataPacket(this.worldPosition, tileEntityType, nbtTagCompound);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) 
	{
		BlockState blockState = level.getBlockState(worldPosition);
	    load(blockState, pkt.getTag());
	}

	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
	    save(nbtTagCompound);
	    return nbtTagCompound;
	}
	
	@Override
    public void handleUpdateTag(BlockState blockState, CompoundTag parentNBTTagCompound)
	{
		this.load(blockState, parentNBTTagCompound);
	}
	
	@Override
	public CompoundTag save(CompoundTag compound)
	{
		super.save(compound);
		CompoundTag itemStackNBT = new CompoundTag();
		if (!activeItem.isEmpty())
		{
			activeItem.save(itemStackNBT);
			compound.put("activeItem", itemStackNBT);
		}
		return compound;
	}
	
	@Override
	public void load(BlockState state, CompoundTag nbt) 
	{
		super.load(state, nbt);
        if (nbt.contains("activeItem")) 
        {
            activeItem = ItemStack.of(nbt.getCompound("activeItem"));
        }
        else 
        {
            activeItem = ItemStack.EMPTY;
        }
	}
}
