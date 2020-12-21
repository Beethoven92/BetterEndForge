package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFCoordModify;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class OreLayerFeature extends Feature<NoFeatureConfig>
{
	private static final SDFSphere SPHERE;
	private static final SDFCoordModify NOISE;
	private static final SDF FUNCTION;
	
	private final BlockState state;
	private final float radius;
	private final int minY;
	private final int maxY;
	private OpenSimplexNoise noise;
	
	static 
	{
		SPHERE = new SDFSphere();
		NOISE = new SDFCoordModify();
		
		SDF body = SPHERE;
		body = new SDFScale3D().setScale(1, 0.2F, 1).setSource(body);
		body = NOISE.setSource(body);
		body.setReplaceFunction((state) -> {
			return state.isIn(Blocks.END_STONE);
		});
		
		FUNCTION = body;
	}
	
	public OreLayerFeature(BlockState state, float radius, int minY, int maxY)
	{
		super(NoFeatureConfig.field_236558_a_);
		this.state = state;
		this.radius = radius;
		this.minY = minY;
		this.maxY = maxY;
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos blockPos, NoFeatureConfig config) 
	{
		float radius = this.radius * 0.5F;
		int r = ModMathHelper.floor(radius + 1);
		int posX = ModMathHelper.randRange(Math.max(r - 16, 0), Math.min(31 - r, 15), random) + blockPos.getX();
		int posZ = ModMathHelper.randRange(Math.max(r - 16, 0), Math.min(31 - r, 15), random) + blockPos.getZ();
		int posY = ModMathHelper.randRange(minY, maxY, random);
		
		if (noise == null) 
		{
			noise = new OpenSimplexNoise(world.getSeed());
		}
		
		SPHERE.setRadius(radius).setBlock(state);
		NOISE.setFunction((vec) -> {
			double x = (vec.getX() + blockPos.getX()) * 0.1;
			double z = (vec.getZ() + blockPos.getZ()) * 0.1;
			double offset = noise.eval(x, z);
			vec.set(vec.getX(), vec.getY() + (float) offset * 8, vec.getZ());
		});
		FUNCTION.fillRecursive(world, new BlockPos(posX, posY, posZ));
		return true;
	}
}
