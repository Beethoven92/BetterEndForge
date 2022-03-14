package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.block.LumecornBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class Lumecorn extends Feature<NoneFeatureConfiguration> {
	public Lumecorn() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator_, Random random,
			BlockPos pos, NoneFeatureConfiguration config) {
		if (!world.getBlockState(pos.below()).getBlock().is(ModTags.END_GROUND)) return false;
		
		int height = ModMathHelper.randRange(4, 7, random);
		MutableBlockPos mut = new MutableBlockPos().set(pos);
		for (int i = 1; i < height; i++) {
			mut.move(Direction.UP);
			if (!world.isEmptyBlock(mut)) {
				return false;
			}
		}
		mut.set(pos);
		BlockState topMiddle = ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.LIGHT_TOP_MIDDLE);
		BlockState middle = ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.LIGHT_MIDDLE);
		BlockState bottom = ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.LIGHT_BOTTOM);
		BlockState top = ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.LIGHT_TOP);
		if (height == 4) {
			BlockHelper.setWithoutUpdate(world, mut, ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.BOTTOM_SMALL));
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), bottom);
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), topMiddle);
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), top);
			return true;
		}
		if (random.nextBoolean()) {
			BlockHelper.setWithoutUpdate(world, mut, ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.BOTTOM_SMALL));
		}
		else {
			BlockHelper.setWithoutUpdate(world, mut, ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.BOTTOM_BIG));
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), ModBlocks.LUMECORN.get().defaultBlockState().setValue(LumecornBlock.SHAPE, LumecornShape.MIDDLE));
			height --;
		}
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), bottom);
		for (int i = 4; i < height; i++) {
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), middle);
		}
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), topMiddle);
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), top);
		return false;
	}
}
