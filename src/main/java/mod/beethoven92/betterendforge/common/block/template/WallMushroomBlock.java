package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WallMushroomBlock extends WallPlantBlock
{
	public WallMushroomBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean isValidSupport(LevelReader world, BlockPos pos, BlockState blockState, Direction direction) 
	{
		return blockState.getMaterial().isSolid() && blockState.isFaceSturdy(world, pos, direction);
	}
}
