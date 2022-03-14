package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class InfusionPedestalTileEntity extends PedestalTileEntity
{
	private InfusionRitual linkedRitual;
	
	public InfusionPedestalTileEntity() 
	{
		super(ModTileEntityTypes.INFUSION_PEDESTAL.get());
	}
	
	@Override
	public void setLevelAndPosition(Level world, BlockPos pos) 
	{
		super.setLevelAndPosition(world, pos);
		if (hasRitual()) 
		{
			this.linkedRitual.setLocation(world, pos);
		}
	}
	
	public void linkRitual(InfusionRitual ritual)
	{
		this.linkedRitual = ritual;
	}
	
	public InfusionRitual getRitual() 
	{
		return this.linkedRitual;
	}
	
	public boolean hasRitual() 
	{
		return this.linkedRitual != null;
	}
	
	@Override
	public void tick() 
	{
		if (hasRitual()) 
		{
			this.linkedRitual.tick();
		}
		super.tick();
	}
	
	@Override
	public CompoundTag save(CompoundTag compound) 
	{
		super.save(compound);
		if (hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundTag()));
		}
		return compound;
	}
	
	@Override
	public void load(BlockState state, CompoundTag nbt) 
	{
		super.load(state, nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new InfusionRitual(level, worldPosition);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
}
