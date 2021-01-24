package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SilkMothNestFeature extends Feature<NoFeatureConfig> {
	public SilkMothNestFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	private static final Mutable POS = new Mutable();
	
	private boolean canGenerate(ISeedReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos.up());
		if (state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.LOGS)) {
			state = world.getBlockState(pos);
			if ((state.isAir() || state.isIn(ModBlocks.TENANEA_OUTER_LEAVES.get())) && world.isAirBlock(pos.down())) {
				for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
					if (world.getBlockState(pos.down().offset(dir)).getMaterial().blocksMovement()) {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{		
		int maxY = world.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(pos.getX(), 0, pos.getZ()), maxY);
		POS.setPos(pos);
		for (int y = maxY; y > minY; y--) {
			POS.setY(y);
			if (canGenerate(world, POS)) {
				Direction dir = BlockHelper.randomHorizontal(rand);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, dir).with(BlockProperties.ACTIVATED, false));
				POS.setY(y - 1);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, dir));
				return true;
			}
		}
		return false;
	}
}
