package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.PosInfo;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class PythadendronFeature extends Feature<NoFeatureConfig>
{
	private static final Function<BlockState, Boolean> REPLACE;
	private static final Function<BlockState, Boolean> IGNORE;
	private static final Function<PosInfo, BlockState> POST;
	static 
	{
		REPLACE = (state) -> {
			if (state.isIn(ModTags.END_GROUND)) 
			{
				return true;
			}
			if (state.getBlock() == ModBlocks.PYTHADENDRON_LEAVES.get()) 
			{
				return true;
			}
			if (state.getMaterial().equals(Material.PLANTS)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
		
		IGNORE = (state) -> {
			return ModBlocks.PYTHADENDRON.isTreeLog(state);
		};
		
		POST = (info) -> {
			if (ModBlocks.PYTHADENDRON.isTreeLog(info.getStateUp()) && ModBlocks.PYTHADENDRON.isTreeLog(info.getStateDown()))
			{
				return ModBlocks.PYTHADENDRON.log.get().getDefaultState();
			}
			return info.getState();
		};
	}
	
	public PythadendronFeature()
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos pos, NoFeatureConfig config) 
	{
		if (world.getBlockState(pos.down()).getBlock() != ModBlocks.CHORUS_NYLIUM.get() &&
				world.getBlockState(pos.down()).getBlock() != ModBlocks.ENDSTONE_DUST.get())
		{
			return false;			
		}
		
		BlockHelper.setWithoutUpdate(world, pos, Blocks.AIR);
		
		float size = ModMathHelper.randRange(10, 20, random);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, size, 0, 4);
		SplineHelper.offsetParts(spline, random, 0.7F, 0, 0.7F);
		Vector3f last = spline.get(spline.size() - 1);
		
		int depth = ModMathHelper.floor((size - 10F) * 3F / 10F + 1F);
		float bsize = (10F - (size - 10F)) / 10F + 1.5F;
		branch(last.getX(), last.getY(), last.getZ(), size * bsize, ModMathHelper.randRange(0, ModMathHelper.PI2, random), random, depth, world, pos);
		
		SDF function = SplineHelper.buildSDF(spline, 1.7F, 1.1F, (bpos) -> {
			return ModBlocks.PYTHADENDRON.bark.get().getDefaultState();
		});
		function.setReplaceFunction(REPLACE);
		function.addPostProcess(POST);
		function.fillRecursive(world, pos);
		
		return true;
	}
	
	private void branch(float x, float y, float z, float size, float angle, Random random, int depth, ISeedReader world, BlockPos pos) 
	{
		if (depth == 0) return;
		
		float dx = (float) Math.cos(angle) * size * 0.15F;
		float dz = (float) Math.sin(angle) * size * 0.15F;
		
		float x1 = x + dx;
		float z1 = z + dz;
		float x2 = x - dx;
		float z2 = z - dz;
		
		List<Vector3f> spline = SplineHelper.makeSpline(x, y, z, x1, y, z1, 5);
		SplineHelper.powerOffset(spline, size * ModMathHelper.randRange(1.0F, 2.0F, random), 4);
		SplineHelper.offsetParts(spline, random, 0.3F, 0, 0.3F);
		Vector3f pos1 = spline.get(spline.size() - 1);
		
		boolean s1 = SplineHelper.fillSpline(spline, world, ModBlocks.PYTHADENDRON.bark.get().getDefaultState(), pos, REPLACE);
		
		spline = SplineHelper.makeSpline(x, y, z, x2, y, z2, 5);
		SplineHelper.powerOffset(spline, size * ModMathHelper.randRange(1.0F, 2.0F, random), 4);
		SplineHelper.offsetParts(spline, random, 0.3F, 0, 0.3F);
		Vector3f pos2 = spline.get(spline.size() - 1);
		
		boolean s2 = SplineHelper.fillSpline(spline, world, ModBlocks.PYTHADENDRON.bark.get().getDefaultState(), pos, REPLACE);
		
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextInt());
		if (depth < 3) 
		{
			if (s1) {
				leavesBall(world, pos.add(pos1.getX(), pos1.getY(), pos1.getZ()), random, noise);
			}
			if (s2) {
				leavesBall(world, pos.add(pos2.getX(), pos2.getY(), pos2.getZ()), random, noise);
			}
		}
		
		float size1 = size * ModMathHelper.randRange(0.75F, 0.95F, random);
		float size2 = size * ModMathHelper.randRange(0.75F, 0.95F, random);
		float angle1 = angle + (float) Math.PI * 0.5F + ModMathHelper.randRange(-0.1F, 0.1F, random);
		float angle2 = angle + (float) Math.PI * 0.5F + ModMathHelper.randRange(-0.1F, 0.1F, random);
		
		if (s1) 
		{
			branch(pos1.getX(), pos1.getY(), pos1.getZ(), size1, angle1, random, depth - 1, world, pos);
		}
		if (s2) 
		{
			branch(pos2.getX(), pos2.getY(), pos2.getZ(), size2, angle2, random, depth - 1, world, pos);
		}
	}
	
	private void leavesBall(ISeedReader world, BlockPos pos, Random random, OpenSimplexNoise noise)
	{
		float radius = ModMathHelper.randRange(4.5F, 6.5F, random);
		
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(ModBlocks.PYTHADENDRON_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE, 6));
		sphere = new SDFScale3D().setScale(1, 0.6F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return random.nextFloat() * 3F - 1.5F; }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		Mutable mut = new Mutable();
		sphere.addPostProcess((info) -> {
			if (random.nextInt(5) == 0) 
			{
				for (Direction dir: Direction.values()) 
				{
					BlockState state = info.getState(dir, 2);
					if (state.isAir()) {
						return info.getState();
					}
				}
				info.setState(ModBlocks.PYTHADENDRON.bark.get().getDefaultState());
				for (int x = -6; x < 7; x++) 
				{
					int ax = Math.abs(x);
					mut.setX(x + info.getPos().getX());
					for (int z = -6; z < 7; z++) 
					{
						int az = Math.abs(z);
						mut.setZ(z + info.getPos().getZ());
						for (int y = -6; y < 7; y++) 
						{
							int ay = Math.abs(y);
							int d = ax + ay + az;
							if (d < 7) {
								mut.setY(y + info.getPos().getY());
								BlockState state = info.getState(mut);
								if (state.getBlock() instanceof LeavesBlock) 
								{
									int distance = state.get(LeavesBlock.DISTANCE);
									if (d < distance) 
									{
										info.setState(mut, state.with(LeavesBlock.DISTANCE, d));
									}
								}
							}
						}
					}
				}
			}
			return info.getState();
		});
		sphere.fillRecursiveIgnore(world, pos, IGNORE);
	}
}
