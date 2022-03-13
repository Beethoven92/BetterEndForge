package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.EndBarrelBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EndBarrelTileEntity extends LockableLootTileEntity {
	private NonNullList<ItemStack> inventory;
	private int viewerCount;

	private EndBarrelTileEntity(TileEntityType<?> type) {
		super(type);
		this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
	}
	
	public EndBarrelTileEntity() {
		this(ModTileEntityTypes.BARREL.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		super.save(tag);
		if (!this.trySaveLootTable(tag)) {
			ItemStackHelper.saveAllItems(tag, this.inventory);
		}

		return tag;
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		super.load(state, tag);
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ItemStackHelper.loadAllItems(tag, this.inventory);
		}

	}

	@Override
	public int getContainerSize() {
		return 27;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> list) {
		this.inventory = list;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.barrel");
	}

	@Override
	protected Container createMenu(int syncId, PlayerInventory playerInventory) {
		return ChestContainer.threeRows(syncId, playerInventory, this);
	}

	@Override
	public void startOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}

			++this.viewerCount;
			BlockState blockState = this.getBlockState();
			boolean bl = (Boolean) blockState.getValue(BarrelBlock.OPEN);
			if (!bl) {
				this.playSound(blockState, SoundEvents.BARREL_OPEN);
				this.setOpen(blockState, true);
			}

			this.scheduleTick();
		}

	}

	private void scheduleTick() {
		this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}

	public void tick() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		this.viewerCount = ChestTileEntity.getOpenCount(this.level, this, i, j, k);
		if (this.viewerCount > 0) {
			this.scheduleTick();
		} else {
			BlockState blockState = this.getBlockState();
			if (!(blockState.getBlock() instanceof EndBarrelBlock)) {
				this.setRemoved();
				return;
			}

			boolean bl = (Boolean) blockState.getValue(BarrelBlock.OPEN);
			if (bl) {
				this.playSound(blockState, SoundEvents.BARREL_CLOSE);
				this.setOpen(blockState, false);
			}
		}

	}

	@Override
	public void stopOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.viewerCount;
		}

	}

	private void setOpen(BlockState state, boolean open) {
		this.level.setBlock(this.getBlockPos(), (BlockState) state.setValue(BarrelBlock.OPEN, open), 3);
	}

	private void playSound(BlockState blockState, SoundEvent soundEvent) {
		Vector3i vec3i = ((Direction) blockState.getValue(BarrelBlock.FACING)).getNormal();
		double d = (double) this.worldPosition.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
		double e = (double) this.worldPosition.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
		double f = (double) this.worldPosition.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
		this.level.playSound((PlayerEntity) null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F,
				this.level.random.nextFloat() * 0.1F + 0.9F);
	}
}
