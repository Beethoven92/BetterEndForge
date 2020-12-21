package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EndstoneDustBlock extends FallingBlock
{
	private static final int COLOR = ModMathHelper.color(226, 239, 168);
	
	public EndstoneDustBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) 
	{
		return COLOR;
	}
}
