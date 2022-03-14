package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class FilaluxFeature extends SkyScatterFeature {
	public FilaluxFeature() {
		super(10);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) {
		BlockState vine = ModBlocks.FILALUX.get().defaultBlockState();
		BlockState wings = ModBlocks.FILALUX_WINGS.get().defaultBlockState();
		BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.FILALUX_LANTERN.get());
		BlockHelper.setWithoutUpdate(world, blockPos.above(), wings.setValue(BlockStateProperties.FACING, Direction.UP));
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
			BlockHelper.setWithoutUpdate(world, blockPos.relative(dir), wings.setValue(BlockStateProperties.FACING, dir));
		}
		int length = ModMathHelper.randRange(1, 3, random);
		for (int i = 1; i <= length; i++) {
			TripleShape shape = length > 1 ? TripleShape.TOP : TripleShape.BOTTOM;
			if (i > 1) {
				shape = i == length ? TripleShape.BOTTOM : TripleShape.MIDDLE;
			}
			BlockHelper.setWithoutUpdate(world, blockPos.below(i), vine.setValue(BlockProperties.TRIPLE_SHAPE, shape));
		}
	}
}
