package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.block.LumecornBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Lumecorn extends Feature<NoFeatureConfig> {
	public Lumecorn() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator_, Random random,
			BlockPos pos, NoFeatureConfig config) {
		if (!world.getBlockState(pos.down()).getBlock().isIn(ModTags.END_GROUND)) return false;
		
		int height = ModMathHelper.randRange(4, 7, random);
		Mutable mut = new Mutable().setPos(pos);
		for (int i = 1; i < height; i++) {
			mut.move(Direction.UP);
			if (!world.isAirBlock(mut)) {
				return false;
			}
		}
		mut.setPos(pos);
		BlockState topMiddle = ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.LIGHT_TOP_MIDDLE);
		BlockState middle = ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.LIGHT_MIDDLE);
		BlockState bottom = ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.LIGHT_BOTTOM);
		BlockState top = ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.LIGHT_TOP);
		if (height == 4) {
			BlockHelper.setWithoutUpdate(world, mut, ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.BOTTOM_SMALL));
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), bottom);
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), topMiddle);
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), top);
			return true;
		}
		if (random.nextBoolean()) {
			BlockHelper.setWithoutUpdate(world, mut, ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.BOTTOM_SMALL));
		}
		else {
			BlockHelper.setWithoutUpdate(world, mut, ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.BOTTOM_BIG));
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), ModBlocks.LUMECORN.get().getDefaultState().with(LumecornBlock.SHAPE, LumecornShape.MIDDLE));
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
