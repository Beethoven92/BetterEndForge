package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.common.ToolType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HydraluxPetalBlockColored extends HydraluxPetalBlock implements IDyedBlock
{
	public HydraluxPetalBlockColored() 
	{
		super(BlockBehaviour.Properties.copy(ModBlocks.HYDRALUX_PETAL_BLOCK.get()));
	}
	
	public HydraluxPetalBlockColored(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public Block createFromColor(DyeColor color) 
	{
		return new HydraluxPetalBlockColored(BlockBehaviour.Properties.of(Material.PLANT, color).
					                                                  sound(SoundType.WART_BLOCK).
					                                                  strength(1F).
					                                                  harvestTool(ToolType.AXE));
	}
}
