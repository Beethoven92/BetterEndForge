package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;

public class EChestTileEntity extends ChestTileEntity {
	private Block chest = Blocks.AIR;
	
	public EChestTileEntity() {
		super(ModTileEntityTypes.CHEST.get());
	}

	public void setChest(Block chest) {
		this.chest = chest;
	}
	
	public Block getChest() {
		return chest;
	}
	
	public boolean hasChest() {
		return !chest.getDefaultState().isAir();
	}
}
