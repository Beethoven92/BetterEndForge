package mod.beethoven92.betterendforge.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HydraluxPetalBlock extends Block
{
	public HydraluxPetalBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) 
	{
	}
}
