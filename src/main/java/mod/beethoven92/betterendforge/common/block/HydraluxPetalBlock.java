package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class HydraluxPetalBlock extends Block
{
	public HydraluxPetalBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void updateEntityAfterFallOn(IBlockReader worldIn, Entity entityIn) 
	{
	}
}
