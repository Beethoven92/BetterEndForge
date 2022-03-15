package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HelixTreeSaplingBlock extends EndSaplingBlock {
	public HelixTreeSaplingBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected Feature<?> getFeature() {
		return ModFeatures.HELIX_TREE;
	}
}
