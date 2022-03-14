package mod.beethoven92.betterendforge.common.util.sdf.operator;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

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
