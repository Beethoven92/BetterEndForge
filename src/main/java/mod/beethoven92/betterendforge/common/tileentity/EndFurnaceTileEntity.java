package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class EndFurnaceTileEntity extends AbstractFurnaceBlockEntity
{
	public EndFurnaceTileEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntityTypes.FURNACE.get(), pos, state, RecipeType.SMELTING);
	}

	@Override
	protected Component getDefaultName() 
	{
		return new TranslatableComponent("container.furnace");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player)
	{
		return new FurnaceMenu(id, player, this, this.dataAccess);
	}

}
