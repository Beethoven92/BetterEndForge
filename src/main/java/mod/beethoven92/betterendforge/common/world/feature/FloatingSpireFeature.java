package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FloatingSpireFeature extends SpireFeature
{
	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
		NoneFeatureConfiguration config) 
	{
		int minY = world.getHeight(Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		int y = minY > 57 ? ModMathHelper.floor(ModMathHelper.randRange(minY, minY * 2, rand) * 0.5F + 32) : ModMathHelper.randRange(64, 192, rand);
		pos = new BlockPos(pos.getX(), y, pos.getZ());
		
		SDF sdf = new SDFSphere().setRadius(ModMathHelper.randRange(2, 3, rand)).setBlock(Blocks.END_STONE);
		int count = ModMathHelper.randRange(3, 5, rand);
		
		for (int i = 0; i < count; i++) 
		{
			float rMin = (i * 1.3F) + 2.5F;
			sdf = addSegment(sdf, ModMathHelper.randRange(rMin, rMin + 1.5F, rand), rand);
		}
		for (int i = count - 1; i > 0; i--) 
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
			if (info.getStateUp().isAir()) {
				if (rand.nextInt(16) == 0) {
					support.add(info.getPos().above());
				}
				return world.getBiome(info.getPos()).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
			}
			else if (info.getState(Direction.UP, 3).isAir())
			{
				return world.getBiome(info.getPos()).getGenerationSettings().getSurfaceBuilderConfig().getUnderMaterial();
			}
			return info.getState();
		});
		sdf.fillRecursive(world, center);
		
		support.forEach((bpos) -> {
			if (ModBiomes.getFromBiome(world.getBiome(bpos)) == ModBiomes.BLOSSOMING_SPIRES) 
			{
				ModFeatures.TENANEA_BUSH.place(world, generator, rand, bpos, null);
			}
		});
		
		return true;
	}
}
