package mod.beethoven92.betterendforge.common.world.feature;

import net.minecraft.block.Block;

public class CharniaFeature extends UnderwaterPlantFeature
{
	public CharniaFeature(Block plant) 
	{
		super(plant, 6);
	}

	@Override
	protected int getChance() 
	{
		return 3;
	}
}
