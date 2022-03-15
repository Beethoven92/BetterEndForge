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
	
	public InfusionPedestalTileEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntityTypes.INFUSION_PEDESTAL.get(), pos, state);
	}

	@Override
	public void setLevel(Level world)
	{
		super.setLevel(world);
		if (hasRitual()) {
			linkedRitual.setLocation(world, this.getBlockPos());
		}
		else {
			linkRitual(new InfusionRitual(this, world, this.getBlockPos()));
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
	public void tick(Level level, BlockPos pos, BlockState state)
	{
		if (hasRitual()) 
		{
			this.linkedRitual.tick();
		}
		super.tick(level, pos, state);
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		if (hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundTag()));
		}
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new InfusionRitual(this, level, worldPosition);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
}
