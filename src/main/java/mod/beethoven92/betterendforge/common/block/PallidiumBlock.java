package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.client.renderer.model.IUnbakedModel;


import java.util.Map;

public class PallidiumBlock extends TerrainBlock {
	private final Block nextLevel;
	
	public PallidiumBlock(String thickness, Block nextLevel) {
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).
				requiresCorrectToolForDrops().
				strength(3.0F, 9.0F).
				sound(SoundType.STONE));
		this.nextLevel = nextLevel;
	}
	
	public boolean canBePotted() {
		return this == ModBlocks.PALLIDIUM_FULL.get();
	}

	

}
