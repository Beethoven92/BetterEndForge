package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSmoothUnion;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SpireFeature extends Feature<NoneFeatureConfiguration>
{
	protected static final Function<BlockState, Boolean> REPLACE;
	
	static {
		REPLACE = (state) -> {
			if (state.is(ModTags.END_GROUND)) 
			{
				return true;
			}
			if (state.getBlock() instanceof LeavesBlock) 
			{
				return true;
			}
			if (state.getMaterial().equals(Material.PLANT)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
	
	public SpireFeature()
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config)
	{
		pos = FeatureHelper.getPosOnSurfaceWG(world, pos);
		
		if (pos.getY() < 10 || !world.getBlockState(pos.below(3)).is(ModTags.GEN_TERRAIN) || 
				!world.getBlockState(pos.below(6)).is(ModTags.GEN_TERRAIN)) 
		{
			return false;
		}
		
		SDF sdf = new SDFSphere().setRadius(ModMathHelper.randRange(2, 3, rand)).setBlock(Blocks.END_STONE);
		int count = ModMathHelper.randRange(3, 7, rand);
		for (int i = 0; i < count; i++) 
		{
			float rMin = (i * 1.3F) + 2.5F;
			sdf = addSegment(sdf, ModMathHelper.randRange(rMin, rMin + 1.5F, rand), rand);
		}
		OpenSimplexNoise noise = new OpenSimplexNoise(rand.nextLong());
		sdf = new SDFDisplacement().setFunction((vec) -> {
			return (float) (Math.abs(noise.eval(vec.x() * 0.1, vec.y() * 0.1, vec.z() * 0.1)) * 3F + Math.abs(noise.eval(vec.x() * 0.3, vec.y() * 0.3 + 100, vec.z() * 0.3)) * 1.3F);
		}).setSource(sdf);
		final BlockPos center = pos;
		List<BlockPos> support = Lists.newArrayList();
		sdf.setReplaceFunction(REPLACE).addPostProcess((info) -> {
			if (info.getStateUp().isAir()) 
			{
				if (rand.nextInt(16) == 0) 
				{
					support.add(info.getPos().above());
				}
				return world.getBiome(info.getPos()).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
			}
			else if (info.getState(Direction.UP, 3).isAir()) 
			{
				return world.getBiome(info.getPos()).getGenerationSettings().getSurfaceBuilderConfig().getUnderMaterial();
			}
			return info.getState();
		}).fillRecursive(world, center);
		
		support.forEach((bpos) -> {
			if (ModBiomes.getFromBiome(world.getBiome(bpos)) == ModBiomes.BLOSSOMING_SPIRES) 
			{
				ModFeatures.TENANEA_BUSH.place(world, generator, rand, bpos, null);
			}
		});

		return true;
	}
	
	protected SDF addSegment(SDF sdf, float radius, Random random)
	{
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(Blocks.END_STONE);
		SDF offseted = new SDFTranslate().setTranslate(0, radius + random.nextFloat() * 0.25F * radius, 0).setSource(sdf);
		return new SDFSmoothUnion().setRadius(radius * 0.5F).setSourceA(sphere).setSourceB(offseted);
	}
}
