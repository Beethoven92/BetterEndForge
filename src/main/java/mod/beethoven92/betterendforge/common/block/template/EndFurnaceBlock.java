package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.tileentity.EndFurnaceTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class EndFurnaceBlock extends FurnaceBlock
{
	public EndFurnaceBlock(Properties builder) 
	{
		super(builder);
	}

	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return new EndFurnaceTileEntity();
	}
	
	@Override
	protected void openContainer(World worldIn, BlockPos pos, PlayerEntity player) 
	{
		TileEntity tileentity = worldIn.getBlockEntity(pos);
	    if (tileentity instanceof EndFurnaceTileEntity) 
	    {
	    	player.openMenu((INamedContainerProvider)tileentity);
	        player.awardStat(Stats.INTERACT_WITH_FURNACE);
	    }
	}
}
