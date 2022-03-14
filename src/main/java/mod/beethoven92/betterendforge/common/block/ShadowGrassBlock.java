package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ShadowGrassBlock extends TerrainBlock
{
	public ShadowGrassBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(32) == 0) 
		{
			worldIn.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + rand.nextDouble(), (double) pos.getY() + 1.1D, (double) pos.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}
