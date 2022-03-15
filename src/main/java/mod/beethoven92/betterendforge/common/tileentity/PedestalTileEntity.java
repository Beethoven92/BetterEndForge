package mod.beethoven92.betterendforge.common.tileentity;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PedestalTileEntity extends BlockEntity implements Container
{
	private ItemStack activeItem = ItemStack.EMPTY;
	
	private final int maxAge = 314;
	private int age;
	
	public PedestalTileEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntityTypes.PEDESTAL.get(), pos, state);
	}
	
	public PedestalTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state)
	{
		super(tileEntityTypeIn, pos, state);
	}

	protected void toTag(CompoundTag tag)
	{
		if (activeItem != ItemStack.EMPTY) {
			tag.put("active_item", activeItem.save(new CompoundTag()));
		}
	}

	protected void fromTag(CompoundTag tag)
	{
		if (tag.contains("active_item")) {
			CompoundTag itemTag = tag.getCompound("active_item");
			activeItem = ItemStack.of(itemTag);
		}
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

	@Override
	public int getContainerSize()
	{
		return 1;
	}

	public boolean isEmpty()
	{
		return activeItem.isEmpty();
	}

	@Override
	public ItemStack getItem(int slot)
	{
		return activeItem;
	}

	@Override
	public ItemStack removeItem(int slot, int amount)
	{
		return removeItemNoUpdate(slot);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot)
	{
		ItemStack stored = activeItem;
		clearContent();
		return stored;
	}

	@Override
	public void setItem(int slot, ItemStack stack)
	{
		activeItem = stack.split(1);
		setChanged();
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
	public boolean stillValid(Player p_18946_) {
		return false;
	}

	public void tick(Level level, BlockPos pos, BlockState state)
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
	
	/*@OnlyIn(Dist.CLIENT)
	@Override
	public double getViewDistance() 
	{
		return 256.0D;
	}*/
	
	@Override
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
	    saveAdditional(nbtTagCompound);
	    return ClientboundBlockEntityDataPacket.create(this, t -> nbtTagCompound);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) 
	{
	    load(pkt.getTag());
	}

	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
	    saveAdditional(nbtTagCompound);
	    return nbtTagCompound;
	}
	
	@Override
    public void handleUpdateTag(CompoundTag parentNBTTagCompound)
	{
		this.load(parentNBTTagCompound);
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		CompoundTag itemStackNBT = new CompoundTag();
		if (!activeItem.isEmpty())
		{
			activeItem.save(itemStackNBT);
			compound.put("activeItem", itemStackNBT);
		}
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
        if (nbt.contains("activeItem")) 
        {
            activeItem = ItemStack.of(nbt.getCompound("activeItem"));
        }
        else 
        {
            activeItem = ItemStack.EMPTY;
        }
	}

	public static <T extends BlockEntity> void commonTick(Level level, BlockPos pos, BlockState state, T tile) {
		if (tile instanceof PedestalTileEntity tick) {
			tick.tick(level, pos, state);
		}
	}

	@Override
	public void clearContent()
	{
		activeItem = ItemStack.EMPTY;
		setChanged();
	}
}
