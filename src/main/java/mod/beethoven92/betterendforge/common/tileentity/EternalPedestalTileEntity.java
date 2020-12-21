package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EternalPedestalTileEntity extends PedestalTileEntity
{
	private EternalRitual linkedRitual;
	
	public EternalPedestalTileEntity() 
	{
		super(ModTileEntityTypes.ETERNAL_PEDESTAL.get());
	}
	
	public boolean hasRitual() 
	{
		return this.linkedRitual != null;
	}
	
	public void linkRitual(EternalRitual ritual) 
	{
		this.linkedRitual = ritual;
	}
	
	public EternalRitual getRitual() 
	{
		return this.linkedRitual;
	}
	
	@Override
	public void setWorldAndPos(World world, BlockPos pos) 
	{
		super.setWorldAndPos(world, pos);
		if (hasRitual()) 
		{
			this.linkedRitual.setWorld(world);
		}
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) 
	{
		super.read(state, nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new EternalRitual(world);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		super.write(compound);
		if (this.hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundNBT()));
		}
		return compound;
	}
}
