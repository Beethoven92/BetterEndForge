package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.EndBarrelBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class EndBarrelTileEntity extends RandomizableContainerBlockEntity {
	private NonNullList<ItemStack> inventory;
	private int viewerCount;

	private EndBarrelTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
	}
	
	public EndBarrelTileEntity(BlockPos pos, BlockState state) {
		this(ModTileEntityTypes.BARREL.get(), pos, state);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.inventory);
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ContainerHelper.loadAllItems(tag, this.inventory);
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
	protected Component getDefaultName() {
		return new TranslatableComponent("container.barrel");
	}

	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return ChestMenu.threeRows(syncId, playerInventory, this);
	}

	@Override
	public void startOpen(Player player) {
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
		this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}

	public void tick(Level level, BlockPos pos, BlockState state) {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		this.viewerCount = ChestBlockEntity.getOpenCount(level, pos);
		if (this.viewerCount > 0) {
			this.scheduleTick();
		} else {
			BlockState blockState = this.getBlockState();
			if (!(blockState.getBlock() instanceof EndBarrelBlock)) {
				this.setRemoved();
				return;
			}

			boolean bl = blockState.getValue(BarrelBlock.OPEN);
			if (bl) {
				this.playSound(blockState, SoundEvents.BARREL_CLOSE);
				this.setOpen(blockState, false);
			}
		}

	}

	@Override
	public void stopOpen(Player player) {
		if (!player.isSpectator()) {
			--this.viewerCount;
		}

	}

	private void setOpen(BlockState state, boolean open) {
		this.level.setBlock(this.getBlockPos(), state.setValue(BarrelBlock.OPEN, open), 3);
	}

	private void playSound(BlockState blockState, SoundEvent soundEvent) {
		Vec3i vec3i = blockState.getValue(BarrelBlock.FACING).getNormal();
		double d = (double) this.worldPosition.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
		double e = (double) this.worldPosition.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
		double f = (double) this.worldPosition.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
		this.level.playSound((Player) null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F,
				this.level.random.nextFloat() * 0.1F + 0.9F);
	}
}
