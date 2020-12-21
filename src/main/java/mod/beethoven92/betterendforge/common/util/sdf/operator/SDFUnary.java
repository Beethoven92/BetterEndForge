package mod.beethoven92.betterendforge.common.util.sdf.operator;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public abstract class SDFUnary extends SDF
{
	protected SDF source;
	
	public SDFUnary setSource(SDF source) 
	{
		this.source = source;
		return this;
	}
	
	@Override
	public BlockState getBlockState(BlockPos pos) 
	{
		return source.getBlockState(pos);
	}
}
