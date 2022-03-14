package mod.beethoven92.betterendforge.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.DyeColor;

public interface IDyedBlock 
{
	public abstract Block createFromColor(DyeColor color);
}
