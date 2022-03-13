package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ToolType;

import net.minecraft.block.AbstractBlock.Properties;

public class HydraluxPetalBlockColored extends HydraluxPetalBlock implements IDyedBlock
{
	public HydraluxPetalBlockColored() 
	{
		super(AbstractBlock.Properties.copy(ModBlocks.HYDRALUX_PETAL_BLOCK.get()));
	}
	
	public HydraluxPetalBlockColored(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public Block createFromColor(DyeColor color) 
	{
		return new HydraluxPetalBlockColored(AbstractBlock.Properties.of(Material.PLANT, color).
					                                                  sound(SoundType.WART_BLOCK).
					                                                  strength(1F).
					                                                  harvestTool(ToolType.AXE));
	}
}
