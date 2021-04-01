package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class FilaluxFeature extends SkyScatterFeature {
	public FilaluxFeature() {
		super(10);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) {
		BlockState vine = ModBlocks.FILALUX.get().getDefaultState();
		BlockState wings = ModBlocks.FILALUX_WINGS.get().getDefaultState();
		BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.FILALUX_LANTERN.get());
		BlockHelper.setWithoutUpdate(world, blockPos.up(), wings.with(BlockStateProperties.FACING, Direction.UP));
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
			BlockHelper.setWithoutUpdate(world, blockPos.offset(dir), wings.with(BlockStateProperties.FACING, dir));
		}
		int length = ModMathHelper.randRange(1, 3, random);
		for (int i = 1; i <= length; i++) {
			TripleShape shape = length > 1 ? TripleShape.TOP : TripleShape.BOTTOM;
			if (i > 1) {
				shape = i == length ? TripleShape.BOTTOM : TripleShape.MIDDLE;
			}
			BlockHelper.setWithoutUpdate(world, blockPos.down(i), vine.with(BlockProperties.TRIPLE_SHAPE, shape));
		}
	}
}
