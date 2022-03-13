package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class TerrainPlantBlock extends PlantBlock {
	private final Block[] ground;

	public TerrainPlantBlock(Block... ground) {
		super(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).instabreak().noCollission()
				.sound(SoundType.GRASS));

		this.ground = ground;
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		for (Block block : ground) {
			if (state.is(block)) {
				return true;
			}
		}
		return false;
	}
}
