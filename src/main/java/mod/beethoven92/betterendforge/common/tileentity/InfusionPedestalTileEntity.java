package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfusionPedestalTileEntity extends PedestalTileEntity
{
	private InfusionRitual linkedRitual;
	
	public InfusionPedestalTileEntity() 
	{
		super(ModTileEntityTypes.INFUSION_PEDESTAL.get());
	}
	
	@Override
	public void setLevelAndPosition(World world, BlockPos pos) 
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
	public CompoundNBT save(CompoundNBT compound) 
	{
		super.save(compound);
		if (hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundNBT()));
		}
		return compound;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) 
	{
		super.load(state, nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new InfusionRitual(level, worldPosition);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
}
