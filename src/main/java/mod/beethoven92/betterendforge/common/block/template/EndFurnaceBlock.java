package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.tileentity.EndFurnaceTileEntity;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndFurnaceBlock extends FurnaceBlock implements EntityBlock
{
	public EndFurnaceBlock(Properties builder) 
	{
		super(builder);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new EndFurnaceTileEntity(pos, state);
	}
	
	@Override
	protected void openContainer(Level worldIn, BlockPos pos, Player player) 
	{
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
	    if (tileentity instanceof EndFurnaceTileEntity) 
	    {
	    	player.openMenu((MenuProvider)tileentity);
	        player.awardStat(Stats.INTERACT_WITH_FURNACE);
	    }
	}
}
