package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
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

public class LargeAmaranitaFeature extends Feature<NoFeatureConfig> {
	
	public LargeAmaranitaFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos pos, NoFeatureConfig config) {
		if (!world.getBlockState(pos.down()).getBlock().isIn(ModTags.END_GROUND)) return false;
		
		Mutable mut = new Mutable().setPos(pos);
		int height = ModMathHelper.randRange(2, 3, random);
		for (int i = 1; i < height; i++) {
			mut.setY(mut.getY() + 1);
			if (!world.isAirBlock(mut)) {
				return false;
			}
		}
		mut.setPos(pos);
		
		BlockState state =  ModBlocks.LARGE_AMARANITA_MUSHROOM.get().getDefaultState();
		BlockHelper.setWithUpdate(world, mut, state.with(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		if (height > 2) {
			BlockHelper.setWithUpdate(world, mut.move(Direction.UP), state.with(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
		}
		BlockHelper.setWithUpdate(world, mut.move(Direction.UP), state.with(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
		
		return true;
	}
}
