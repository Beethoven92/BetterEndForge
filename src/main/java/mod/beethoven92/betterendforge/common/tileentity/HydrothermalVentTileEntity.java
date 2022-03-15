package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.HydrothermalVentBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;

// Is this tile entity necessary?
public class HydrothermalVentTileEntity extends BlockEntity
{
	public HydrothermalVentTileEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntityTypes.HYDROTHERMAL_VENT.get(), pos, state);
	}

	public void tick(Level level, BlockPos pos, BlockState state)
	{
		if (level.random.nextInt(20) == 0) 
		{
			double x = pos.getX() + level.random.nextDouble();
			double y = pos.getY() + 0.9 + level.random.nextDouble() * 0.3;
			double z = pos.getZ() + level.random.nextDouble();
			if (state.is(ModBlocks.HYDROTHERMAL_VENT.get()) && state.getValue(HydrothermalVentBlock.ACTIVATED))
			{
				if (state.getValue(HydrothermalVentBlock.WATERLOGGED)) 
				{
					level.addParticle(ModParticleTypes.GEYSER_PARTICLE.get(), x, y, z, 0, 0, 0);
				}
				else 
				{
					level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0, 0);
				}
			}
		}
	}

	public static <T extends BlockEntity> void commonTick(Level level, BlockPos pos, BlockState state, T tile) {
		if (tile instanceof HydrothermalVentTileEntity tick) {
			tick.tick(level, pos, state);
		}
	}
}
