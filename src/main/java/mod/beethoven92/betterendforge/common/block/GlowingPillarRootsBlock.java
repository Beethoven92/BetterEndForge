package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UpDownPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;

public class GlowingPillarRootsBlock extends UpDownPlantBlock {
	public GlowingPillarRootsBlock(Properties properties) {
		super(properties);
	}

	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.AMBER_MOSS.get());
	}
}
