package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.Level;

public class GlowingPillarSeedBlock extends PlantBlockWithAge {
	public GlowingPillarSeedBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void growAdult(WorldGenLevel world, Random random, BlockPos pos) {
		int height = ModMathHelper.randRange(1, 2, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height) {
			return;
		}

		MutableBlockPos mut = new MutableBlockPos().set(pos);
		BlockState roots = ModBlocks.GLOWING_PILLAR_ROOTS.get().defaultBlockState();
		if (height < 2) {
			BlockHelper.setWithUpdate(world, mut, roots.setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
			mut.move(Direction.UP);
		} else {
			BlockHelper.setWithUpdate(world, mut, roots.setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
			mut.move(Direction.UP);
			BlockHelper.setWithUpdate(world, mut, roots.setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
			mut.move(Direction.UP);
		}
		BlockHelper.setWithUpdate(world, mut,
				ModBlocks.GLOWING_PILLAR_LUMINOPHOR.get().defaultBlockState().setValue(BlueVineLanternBlock.NATURAL, true));
		for (Direction dir : BlockHelper.DIRECTIONS) {
			pos = mut.relative(dir);
			if (world.isEmptyBlock(pos)) {
				BlockHelper.setWithUpdate(world, pos,
						ModBlocks.GLOWING_PILLAR_LEAVES.get().defaultBlockState().setValue(BlockStateProperties.FACING, dir));
			}
		}
		mut.move(Direction.UP);
		if (world.isEmptyBlock(mut)) {
			BlockHelper.setWithUpdate(world, mut, ModBlocks.GLOWING_PILLAR_LEAVES.get().defaultBlockState()
					.setValue(BlockStateProperties.FACING, Direction.UP));
		}
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.AMBER_MOSS.get());
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}
}
