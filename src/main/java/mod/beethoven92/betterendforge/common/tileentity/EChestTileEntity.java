package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EChestTileEntity extends ChestBlockEntity {
	private Block chest = Blocks.AIR;
	
	public EChestTileEntity(BlockPos pos, BlockState state) {
		super(ModTileEntityTypes.CHEST.get(), pos, state);
	}

	public void setChest(Block chest) {
		this.chest = chest;
	}
	
	public Block getChest() {
		return chest;
	}
	
	public boolean hasChest() {
		return !chest.defaultBlockState().isAir();
	}
}
