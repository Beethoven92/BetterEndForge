package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class EndFurnaceTileEntity extends AbstractFurnaceBlockEntity
{
	public EndFurnaceTileEntity()
	{
		super(ModTileEntityTypes.FURNACE.get(), RecipeType.SMELTING);
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
