package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.EndLotusSeedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;

public class EndLotusFeature extends ScatterFeature
{
	private static final Mutable POS = new Mutable();
	
	public EndLotusFeature(int radius) 
	{
		super(radius);
	}
	
	@Override
	protected BlockPos getCenterGround(ISeedReader world, BlockPos pos) 
	{
		POS.setX(pos.getX());
		POS.setZ(pos.getZ());
		POS.setY(0);
		return getGround(world, POS).toImmutable();
	}
	
	@Override
	protected boolean canSpawn(ISeedReader world, BlockPos pos)
	{
		return !world.getFluidState(pos).isEmpty();
	}
	
	@Override
	protected boolean getGroundPlant(ISeedReader world, Mutable pos) 
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
	
	private BlockPos getGround(ISeedReader world, Mutable pos) 
	{
		while (pos.getY() < 128 && world.getFluidState(pos).isEmpty()) 
		{
			pos.setY(pos.getY() + 1);
		}
		return pos;
	}
	
	@Override
	public boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.getBlockState(blockPos).isIn(Blocks.WATER);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
	{
		EndLotusSeedBlock seed = (EndLotusSeedBlock) ModBlocks.END_LOTUS_SEED.get();
		seed.generate(world, random, blockPos);
	}
}
