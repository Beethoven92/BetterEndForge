package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;

public abstract class SkyScatterFeature extends ScatterFeature {
	public SkyScatterFeature(int radius) {
		super(radius);
	}
	
	@Override
	protected int getChance() {
		return 10;
	}

	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		if (!world.isAirBlock(blockPos)) {
			return false;
		}
		
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
			if (!world.isAirBlock(blockPos.offset(dir))) {
				return false;
			}
		}
		
		int maxD = getYOffset() + 2;
		int maxV = getYOffset() - 2;
		
		return BlockHelper.upRay(world, blockPos, maxD) > maxV && BlockHelper.downRay(world, blockPos, maxD) > maxV;
	}
	
	@Override
	protected boolean canSpawn(ISeedReader world, BlockPos pos) {
		return true;
	}
	
	@Override
	protected BlockPos getCenterGround(ISeedReader world, BlockPos pos) {
		return new BlockPos(pos.getX(), ModMathHelper.randRange(32, 192, world.getRandom()), pos.getZ());
	}
	
	protected boolean getGroundPlant(ISeedReader world, Mutable pos) {
		pos.setY(pos.getY() + ModMathHelper.randRange(-getYOffset(), getYOffset(), world.getRandom()));
		return true;
	}
}
