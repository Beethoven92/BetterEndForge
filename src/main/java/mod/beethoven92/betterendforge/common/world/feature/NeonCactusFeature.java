package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class NeonCactusFeature extends Feature<NoFeatureConfig> {
	public NeonCactusFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random random,
			BlockPos pos, NoFeatureConfig config) 
	{
		if (!world.getBlockState(pos.down()).isIn(ModBlocks.ENDSTONE_DUST.get())) {
			return false;
		}

		int h = ModMathHelper.randRange(5, 20, random);
		Mutable mut = new Mutable().setPos(pos);
		Direction hor = BlockHelper.randomHorizontal(random);
		for (int i = 0; i < h; i++) {
			if (!world.getBlockState(mut).getMaterial().isReplaceable()) {
				break;
			}
			int size = (h - i) >> 2;
			BlockHelper.setWithUpdate(world, mut,
					ModBlocks.NEON_CACTUS.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, getBySize(size))
							.with(BlockStateProperties.FACING, Direction.UP));
			if (i > 2 && i < (h - 1) && random.nextBoolean()) {
				int length = h - i - ModMathHelper.randRange(1, 2, random);
				if (length > 0) {
					Direction dir2 = hor;
					hor = hor.rotateY();
					int bsize = i > ((h << 1) / 3) ? 0 : size > 1 ? 1 : size;
					branch(world, mut.offset(dir2), dir2, random, length, bsize);
				}
			}
			mut.move(Direction.UP);
		}

		return true;
	}

	private void branch(ISeedReader world, BlockPos pos, Direction dir, Random random, int length, int size) {
		int rotIndex = length >> 2;
		Mutable mut = new Mutable().setPos(pos);
		Direction hor = BlockHelper.randomHorizontal(random);
		for (int i = 0; i < length; i++) {
			if (!world.getBlockState(mut).getMaterial().isReplaceable()) {
				return;
			}
			BlockHelper.setWithUpdate(world, mut,
					ModBlocks.NEON_CACTUS.get().getDefaultState().with(BlockProperties.TRIPLE_SHAPE, getBySize(size))
							.with(BlockStateProperties.FACING, dir));
			if (i == rotIndex) {
				dir = Direction.UP;
				size--;
			}
			if (i > 1 && i < (length - 1) && random.nextBoolean()) {
				Direction dir2 = dir == Direction.UP ? hor : Direction.UP;
				hor = hor.rotateY();
				branch(world, mut.offset(dir2), dir2, random, ModMathHelper.randRange(length / 4, length / 2, random),
						size > 0 ? size - 1 : size);
			}
			mut.move(dir);
		}
	}

	private TripleShape getBySize(int size) {
		return size < 1 ? TripleShape.TOP : size == 1 ? TripleShape.MIDDLE : TripleShape.BOTTOM;
	}
}
