package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFHexPrism;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BigAuroraCrystalFeature extends Feature<NoneFeatureConfiguration>
{

	public BigAuroraCrystalFeature()
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config) 
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
					|| bState.is(ModTags.GEN_TERRAIN)
					|| bState.getMaterial().equals(Material.PLANT)
					|| bState.getMaterial().equals(Material.LEAVES);
		});
		prism.fillRecursive(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.AURORA_CRYSTAL.get());
		
		return true;
	}
}
