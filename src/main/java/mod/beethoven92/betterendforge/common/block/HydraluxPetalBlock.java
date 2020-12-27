package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockReader;

public class HydraluxPetalBlock extends Block
{
	public HydraluxPetalBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void onLanded(IBlockReader worldIn, Entity entityIn) 
	{
	}
}
