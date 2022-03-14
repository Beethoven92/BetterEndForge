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
	public void setLevelAndPosition(Level world, BlockPos pos) 
	{
		super.setLevelAndPosition(world, pos);
		if (hasRitual()) 
		{
			this.linkedRitual.setWorld(world);
		}
	}
	
	@Override
	public void load(BlockState state, CompoundTag nbt) 
	{
		super.load(state, nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new EternalRitual(level);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
	
	@Override
	public CompoundTag save(CompoundTag compound) 
	{
		super.save(compound);
		if (this.hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundTag()));
		}
		return compound;
	}
}
