package mod.beethoven92.betterendforge.common.world.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class IceStarFeature extends Feature<NoFeatureConfig>
{
	private final float minSize;
	private final float maxSize;
	private final int minCount;
	private final int maxCount;
	
	public IceStarFeature(float minSize, float maxSize, int minCount, int maxCount)
	{
		super(NoFeatureConfig.field_236558_a_);
		
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		float size = ModMathHelper.randRange(minSize, maxSize, rand);
		int count = ModMathHelper.randRange(minCount, maxCount, rand);
		List<Vector3f> points = getFibonacciPoints(count);
		SDF sdf = null;
		SDF spike = new SDFCappedCone().setRadius1(3 + (size - 5) * 0.2F).setRadius2(0).setHeight(size).setBlock(ModBlocks.DENSE_SNOW.get());
		spike = new SDFTranslate().setTranslate(0, size - 0.5F, 0).setSource(spike);
		for (Vector3f point: points) 
		{
			SDF rotated = spike;
			point = ModMathHelper.normalize(point);
			float angle = ModMathHelper.angle(Vector3f.YP, point);
			if (angle > 0.01F && angle < 3.14F) 
			{
				Vector3f axis = ModMathHelper.normalize(ModMathHelper.cross(Vector3f.YP, point));
				rotated = new SDFRotation().setRotation(axis, angle).setSource(spike);
			}
			else if (angle > 1) 
			{
				rotated = new SDFRotation().setRotation(Vector3f.YP, (float) Math.PI).setSource(spike);
			}
			sdf = (sdf == null) ? rotated : new SDFUnion().setSourceA(sdf).setSourceB(rotated);
		}
		
		int x1 = (pos.getX() >> 4) << 4;
		int z1 = (pos.getZ() >> 4) << 4;
		pos = new BlockPos(x1 + rand.nextInt(16), ModMathHelper.randRange(32, 128, rand), z1 + rand.nextInt(16));
		
		final float ancientRadius = size * 0.7F;
		final float denseRadius = size * 0.9F;
		final float iceRadius = size < 7 ? size * 5 : size * 1.3F;
		final float randScale = size * 0.3F;
		
		final BlockPos center = pos;
		final BlockState ice = ModBlocks.EMERALD_ICE.get().getDefaultState();
		final BlockState dense = ModBlocks.DENSE_EMERALD_ICE.get().getDefaultState();
		final BlockState ancient = ModBlocks.ANCIENT_EMERALD_ICE.get().getDefaultState();
		final SDF sdfCopy = sdf;
		
		sdf.addPostProcess((info) -> {
			BlockPos bpos = info.getPos();
			float px = bpos.getX() - center.getX();
			float py = bpos.getY() - center.getY();
			float pz = bpos.getZ() - center.getZ();
			float distance = ModMathHelper.length(px, py, pz) + sdfCopy.getDistance(px, py, pz) * 0.4F + rand.nextFloat() * randScale;
			if (distance < ancientRadius) 
			{
				return ancient;
			}
			else if (distance < denseRadius) 
			{
				return dense;
			}
			else if (distance < iceRadius) 
			{
				return ice;
			}
			return info.getState();
		}).fillRecursive(world, pos);

		return true;
	}

	private List<Vector3f> getFibonacciPoints(int count) 
	{
		float max = count - 1;
		List<Vector3f> result = new ArrayList<Vector3f>(count);
		for (int i = 0; i < count; i++) 
		{
			float y = 1F - (i / max) * 2F;
			float radius = (float) Math.sqrt(1F - y * y);
			float theta = ModMathHelper.PHI * i;
			float x = (float) Math.cos(theta) * radius;
			float z = (float) Math.sin(theta) * radius;
			result.add(new Vector3f(x, y, z));
		}
		return result;
	}
}
