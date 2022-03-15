package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UpDownPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GlowingPillarRootsBlock extends UpDownPlantBlock {
	public GlowingPillarRootsBlock(Properties properties) {
		super(properties);
	}

	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.AMBER_MOSS.get());
	}
}
