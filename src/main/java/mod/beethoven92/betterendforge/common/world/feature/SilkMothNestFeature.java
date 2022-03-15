package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SilkMothNestFeature extends Feature<NoneFeatureConfiguration> {
	public SilkMothNestFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	private static final MutableBlockPos POS = new MutableBlockPos();
	
	private boolean canGenerate(WorldGenLevel world, BlockPos pos) {
		BlockState state = world.getBlockState(pos.above());
		if (state.is(BlockTags.LEAVES) || state.is(BlockTags.LOGS)) {
			state = world.getBlockState(pos);
			if ((state.isAir() || state.is(ModBlocks.TENANEA_OUTER_LEAVES.get())) && world.isEmptyBlock(pos.below())) {
				for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
					if (world.getBlockState(pos.below().relative(dir)).getMaterial().blocksMotion()) {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config) 
	{		
		int maxY = world.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(pos.getX(), 0, pos.getZ()), maxY);
		POS.set(pos);
		for (int y = maxY; y > minY; y--) {
			POS.setY(y);
			if (canGenerate(world, POS)) {
				Direction dir = BlockHelper.randomHorizontal(rand);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir).setValue(BlockProperties.ACTIVATED, false));
				POS.setY(y - 1);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir));
				return true;
			}
		}
		return false;
	}
}
