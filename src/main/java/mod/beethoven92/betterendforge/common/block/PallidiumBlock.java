package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.model.IUnbakedModel;


import java.util.Map;

public class PallidiumBlock extends TerrainBlock {
	private final Block nextLevel;
	
	public PallidiumBlock(String thickness, Block nextLevel) {
		super(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).
				setRequiresTool().
				hardnessAndResistance(3.0F, 9.0F).
				sound(SoundType.STONE));
		this.nextLevel = nextLevel;
	}
	
	public boolean canBePotted() {
		return this == ModBlocks.PALLIDIUM_FULL.get();
	}

	

}
