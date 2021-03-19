package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFHexPrism;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BigAuroraCrystalFeature extends Feature<NoFeatureConfig>
{

	public BigAuroraCrystalFeature()
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		int maxY = pos.getY() + BlockHelper.upRay(world, pos, 16);
		int minY = pos.getY() - BlockHelper.downRay(world, pos, 16);
		
		if (maxY - minY < 10) 
		{
			return false;
		}
		
		int y = ModMathHelper.randRange(minY, maxY, rand);
		pos = new BlockPos(pos.getX(), y, pos.getZ());
		
		int height = ModMathHelper.randRange(5, 25, rand);
		SDF prism = new SDFHexPrism().setHeight(height).setRadius(ModMathHelper.randRange(1.7F, 3F, rand)).setBlock(ModBlocks.AURORA_CRYSTAL.get());
		Vector3f vec = ModMathHelper.randomHorizontal(rand);
		prism = new SDFRotation().setRotation(vec, rand.nextFloat()).setSource(prism);
		prism.setReplaceFunction((bState) -> {
			return bState.getMaterial().isReplaceable()
					|| bState.isIn(ModTags.GEN_TERRAIN)
					|| bState.getMaterial().equals(Material.PLANTS)
					|| bState.getMaterial().equals(Material.LEAVES);
		});
		prism.fillRecursive(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.AURORA_CRYSTAL.get());
		
		return true;
	}
}
