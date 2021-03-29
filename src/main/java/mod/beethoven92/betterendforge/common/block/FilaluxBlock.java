package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FilaluxBlock extends EndVineBlock {
	public FilaluxBlock() {
		super(AbstractBlock.Properties.create(Material.PLANTS).
                zeroHardnessAndResistance().
                doesNotBlockMovement().
                setLightLevel((state) -> {return state.get(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;}).
                sound(SoundType.PLANT));
	}
	
	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}
}
