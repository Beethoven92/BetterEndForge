package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class WallMushroomBlock extends WallPlantBlock
{
	public WallMushroomBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean isValidSupport(IWorldReader world, BlockPos pos, BlockState blockState, Direction direction) 
	{
		return blockState.getMaterial().isSolid() && blockState.isFaceSturdy(world, pos, direction);
	}
}
