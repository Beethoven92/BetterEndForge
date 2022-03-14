package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.EndLotusSeedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class EndLotusFeature extends ScatterFeature
{
	private static final MutableBlockPos POS = new MutableBlockPos();
	
	public EndLotusFeature(int radius) 
	{
		super(radius);
	}
	
	@Override
	protected BlockPos getCenterGround(WorldGenLevel world, BlockPos pos) 
	{
		POS.setX(pos.getX());
		POS.setZ(pos.getZ());
		POS.setY(0);
		return getGround(world, POS).immutable();
	}
	
	@Override
	protected boolean canSpawn(WorldGenLevel world, BlockPos pos)
	{
		return !world.getFluidState(pos).isEmpty();
	}
	
	@Override
	protected boolean getGroundPlant(WorldGenLevel world, MutableBlockPos pos) 
	{
		return getGround(world, pos).getY() < 128;
	}
	
	@Override
	protected int getYOffset() 
	{
		return -5;
	}
	
	@Override
	protected int getChance() 
	{
		return 15;
	}
	
	private BlockPos getGround(WorldGenLevel world, MutableBlockPos pos) 
	{
		while (pos.getY() < 128 && world.getFluidState(pos).isEmpty()) 
		{
			pos.setY(pos.getY() + 1);
		}
		return pos;
	}
	
	@Override
	public boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.getBlockState(blockPos).is(Blocks.WATER);
	}

	@Override
	public void generate(WorldGenLevel world, Random random, BlockPos blockPos) 
	{
		EndLotusSeedBlock seed = (EndLotusSeedBlock) ModBlocks.END_LOTUS_SEED.get();
		seed.generate(world, random, blockPos);
	}
}
