package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.HydrothermalVentBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

// Is this tile entity necessary?
public class HydrothermalVentTileEntity extends TileEntity implements ITickableTileEntity
{
	public HydrothermalVentTileEntity() 
	{
		super(ModTileEntityTypes.HYDROTHERMAL_VENT.get());
	}

	@Override
	public void tick() 
	{
		if (level.random.nextInt(20) == 0) 
		{
			double x = worldPosition.getX() + level.random.nextDouble();
			double y = worldPosition.getY() + 0.9 + level.random.nextDouble() * 0.3;
			double z = worldPosition.getZ() + level.random.nextDouble();
			BlockState state = this.getBlockState(); 
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
}
