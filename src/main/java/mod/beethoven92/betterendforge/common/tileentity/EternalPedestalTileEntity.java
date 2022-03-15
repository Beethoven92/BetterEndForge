package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class EternalPedestalTileEntity extends PedestalTileEntity
{
	private EternalRitual linkedRitual;
	
	public EternalPedestalTileEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntityTypes.ETERNAL_PEDESTAL.get(), pos, state);
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
	public void setLevel(Level world)
	{
		super.setLevel(world);
		if (hasRitual()) 
		{
			this.linkedRitual.setWorld(world);
		}
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new EternalRitual(level);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		if (this.hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundTag()));
		}
	}
}
