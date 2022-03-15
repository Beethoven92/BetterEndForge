package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;

public abstract class SkyScatterFeature extends ScatterFeature {
	public SkyScatterFeature(int radius) {
		super(radius);
	}
	
	@Override
	protected int getChance() {
		return 10;
	}

	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		if (!world.isEmptyBlock(blockPos)) {
			return false;
		}
		
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
			if (!world.isEmptyBlock(blockPos.relative(dir))) {
				return false;
			}
		}
		
		int maxD = getYOffset() + 2;
		int maxV = getYOffset() - 2;
		
		return BlockHelper.upRay(world, blockPos, maxD) > maxV && BlockHelper.downRay(world, blockPos, maxD) > maxV;
	}
	
	@Override
	protected boolean canSpawn(WorldGenLevel world, BlockPos pos) {
		return true;
	}
	
	@Override
	protected BlockPos getCenterGround(WorldGenLevel world, BlockPos pos) {
		return new BlockPos(pos.getX(), ModMathHelper.randRange(32, 192, world.getRandom()), pos.getZ());
	}
	
	protected boolean getGroundPlant(WorldGenLevel world, MutableBlockPos pos) {
		pos.setY(pos.getY() + ModMathHelper.randRange(-getYOffset(), getYOffset(), world.getRandom()));
		return true;
	}
}
