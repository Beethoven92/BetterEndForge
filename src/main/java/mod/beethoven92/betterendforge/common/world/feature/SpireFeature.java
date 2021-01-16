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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SpireFeature extends Feature<NoFeatureConfig>
{
	protected static final Function<BlockState, Boolean> REPLACE;
	
	static {
		REPLACE = (state) -> {
			if (state.isIn(ModTags.END_GROUND)) 
			{
				return true;
			}
			if (state.getBlock() instanceof LeavesBlock) 
			{
				return true;
			}
			if (state.getMaterial().equals(Material.PLANTS)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
	
	public SpireFeature()
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config)
	{
		pos = FeatureHelper.getPosOnSurfaceWG(world, pos);
		
		if (pos.getY() < 10 || !world.getBlockState(pos.down(3)).isIn(ModTags.GEN_TERRAIN) || 
				!world.getBlockState(pos.down(6)).isIn(ModTags.GEN_TERRAIN)) 
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
			return (float) (Math.abs(noise.eval(vec.getX() * 0.1, vec.getY() * 0.1, vec.getZ() * 0.1)) * 3F + Math.abs(noise.eval(vec.getX() * 0.3, vec.getY() * 0.3 + 100, vec.getZ() * 0.3)) * 1.3F);
		}).setSource(sdf);
		final BlockPos center = pos;
		List<BlockPos> support = Lists.newArrayList();
		sdf.setReplaceFunction(REPLACE).addPostProcess((info) -> {
			if (info.getStateUp().isAir()) 
			{
				if (rand.nextInt(16) == 0) 
				{
					support.add(info.getPos().up());
				}
				return world.getBiome(info.getPos()).getGenerationSettings().getSurfaceBuilderConfig().getTop();
			}
			else if (info.getState(Direction.UP, 3).isAir()) 
			{
				return world.getBiome(info.getPos()).getGenerationSettings().getSurfaceBuilderConfig().getUnder();
			}
			return info.getState();
		}).fillRecursive(world, center);
		
		support.forEach((bpos) -> {
			if (ModBiomes.getFromBiome(world.getBiome(bpos)) == ModBiomes.BLOSSOMING_SPIRES) 
			{
				ModFeatures.TENANEA_BUSH.generate(world, generator, rand, bpos, null);
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
