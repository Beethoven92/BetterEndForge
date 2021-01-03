package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ToolType;

public class HydraluxPetalBlockColored extends HydraluxPetalBlock implements IDyedBlock
{
	public HydraluxPetalBlockColored() 
	{
		super(AbstractBlock.Properties.from(ModBlocks.HYDRALUX_PETAL_BLOCK.get()));
	}
	
	public HydraluxPetalBlockColored(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public Block createFromColor(DyeColor color) 
	{
		return new HydraluxPetalBlockColored(AbstractBlock.Properties.create(Material.PLANTS, color).
					                                                  sound(SoundType.WART).
					                                                  hardnessAndResistance(1F).
					                                                  harvestTool(ToolType.AXE));
	}
}
