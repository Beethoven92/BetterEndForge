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
		if (world.rand.nextInt(20) == 0) 
		{
			double x = pos.getX() + world.rand.nextDouble();
			double y = pos.getY() + 0.9 + world.rand.nextDouble() * 0.3;
			double z = pos.getZ() + world.rand.nextDouble();
			BlockState state = this.getBlockState(); 
			if (state.isIn(ModBlocks.HYDROTHERMAL_VENT.get()) && state.get(HydrothermalVentBlock.ACTIVATED))
			{
				if (state.get(HydrothermalVentBlock.WATERLOGGED)) 
				{
					world.addParticle(ModParticleTypes.GEYSER_PARTICLE.get(), x, y, z, 0, 0, 0);
				}
				else 
				{
					world.addParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0, 0);
				}
			}
		}
	}
}
