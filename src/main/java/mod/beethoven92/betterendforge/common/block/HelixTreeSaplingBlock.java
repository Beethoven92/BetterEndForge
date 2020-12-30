package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.world.gen.feature.Feature;

public class HelixTreeSaplingBlock extends EndSaplingBlock {
	public HelixTreeSaplingBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected Feature<?> getFeature() {
		return ModFeatures.HELIX_TREE;
	}
}
