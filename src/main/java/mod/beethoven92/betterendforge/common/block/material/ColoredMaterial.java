package mod.beethoven92.betterendforge.common.block.material;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.IDyedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;

public class ColoredMaterial
{
	public final Map<DyeColor, RegistryObject<Block>> dyedBlocks = Maps.newEnumMap(DyeColor.class);
	
	public final boolean craftEight;
	
	public final RegistryObject<Block> craftMaterial;
	
	public final String name;

	public ColoredMaterial(String name, Supplier<? extends IDyedBlock> source, RegistryObject<Block> craftMaterial, boolean craftEight)
	{	
		this.craftEight = craftEight;
		
		this.craftMaterial = craftMaterial;
		
		this.name = name;

		for (DyeColor color: DyeColor.values()) 
		{
			String coloredName = name + "_" + color.getString();

			RegistryObject<Block> block = ModBlocks.registerBlockWithDefaultItem(coloredName, () -> source.get().createFromColor(color));
			
			dyedBlocks.put(color, block);
		}
	}
	
	public Block[] getBlocks()
	{
		return dyedBlocks.values().stream().map(RegistryObject::get).toArray(Block[]::new);
	}
	
	public Block getByColor(DyeColor color) 
	{
		return dyedBlocks.get(color).get();
	}
}
