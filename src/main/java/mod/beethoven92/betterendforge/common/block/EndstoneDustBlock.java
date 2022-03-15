package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndstoneDustBlock extends FallingBlock
{
	private static final int COLOR = ModMathHelper.color(226, 239, 168);
	
	public EndstoneDustBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public int getDustColor(BlockState state, BlockGetter reader, BlockPos pos) 
	{
		return COLOR;
	}
}
