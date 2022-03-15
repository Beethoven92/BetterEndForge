package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class FilaluxBlock extends EndVineBlock {
	public FilaluxBlock() {
		super(BlockBehaviour.Properties.of(Material.PLANT).
                instabreak().
                noCollission().
                lightLevel((state) -> {return state.getValue(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;}).
                sound(SoundType.GRASS));
	}
	
	@Override
	public BlockBehaviour.OffsetType getOffsetType() {
		return BlockBehaviour.OffsetType.NONE;
	}
}
