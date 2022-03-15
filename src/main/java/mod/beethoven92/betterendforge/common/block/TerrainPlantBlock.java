package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class TerrainPlantBlock extends PlantBlock {
	private final Block[] ground;

	public TerrainPlantBlock(Block... ground) {
		super(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).instabreak().noCollission()
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
