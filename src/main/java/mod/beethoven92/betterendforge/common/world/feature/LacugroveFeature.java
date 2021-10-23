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
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class LacugroveFeature extends Feature<NoFeatureConfig>
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
			if (ModBlocks.LACUGROVE.isTreeLog(state)) 
			{
				return true;
			}
			if (state.getBlock() == ModBlocks.LACUGROVE_LEAVES.get()) 
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
			return ModBlocks.LACUGROVE.isTreeLog(state);
		};
		
		POST = (info) -> {
			if (ModBlocks.LACUGROVE.isTreeLog(info.getStateUp()) && ModBlocks.LACUGROVE.isTreeLog(info.getStateDown()))
			{
				return ModBlocks.LACUGROVE.log.get().getDefaultState();
			}
			return info.getState();
		};
	}
	
	public LacugroveFeature() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos blockPos, NoFeatureConfig config) 
	{
		if (!world.getBlockState(blockPos.down()).isIn(ModTags.END_GROUND)) return false;
		
		float size = ModMathHelper.randRange(15, 25, random);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, size, 0, 6);
		SplineHelper.offsetParts(spline, random, 1F, 0, 1F);
		
		if (!SplineHelper.canGenerate(spline, blockPos, world, REPLACE))
		{
			return false;
		}
		
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		
		float radius = ModMathHelper.randRange(6F, 8F, random);
		radius *= (size - 15F) / 20F + 1F;
		Vector3f center = spline.get(4);
		leavesBall(world, blockPos.add(center.getX(), center.getY(), center.getZ()), radius, random, noise);
		
		radius = ModMathHelper.randRange(1.2F, 1.8F, random);
		SDF function = SplineHelper.buildSDF(spline, radius, 0.7F, (bpos) -> {
			return ModBlocks.LACUGROVE.bark.get().getDefaultState();
		});
		
		function.setReplaceFunction(REPLACE);
		function.addPostProcess(POST);
		function.fillRecursive(world, blockPos);
		
		spline = spline.subList(4, 6);
		SplineHelper.fillSpline(spline, world, ModBlocks.LACUGROVE.bark.get().getDefaultState(), blockPos, REPLACE);
		
		Mutable mut = new Mutable();
		int offset = random.nextInt(2);
		for (int i = 0; i < 100; i++) 
		{
			double px = blockPos.getX() + ModMathHelper.randRange(-5, 5, random);
			double pz = blockPos.getZ() + ModMathHelper.randRange(-5, 5, random);
			mut.setX(ModMathHelper.floor(px + 0.5));
			mut.setZ(ModMathHelper.floor(pz + 0.5));
			if (((mut.getX() + mut.getZ() + offset) & 1) == 0) 
			{
				double distance = 3.5 - ModMathHelper.length(px - blockPos.getX(), pz - blockPos.getZ()) * 0.5;
				if (distance > 0) 
				{
					int minY = ModMathHelper.floor(blockPos.getY() - distance * 0.5);
					int maxY = ModMathHelper.floor(blockPos.getY() + distance + random.nextDouble());
					boolean generate = false;
					for (int y = minY; y < maxY; y++) 
					{
						mut.setY(y);
						if (world.getBlockState(mut).isIn(ModTags.END_GROUND)) 
						{
							generate = true;
							break;
						}
					}
					if (generate)
					{
						int top = maxY - 1;
						for (int y = top; y >= minY; y--) 
						{
							mut.setY(y);
							BlockState state = world.getBlockState(mut);
							if (state.getMaterial().isReplaceable() || state.getMaterial().equals(Material.PLANTS) || state.isIn(ModTags.END_GROUND))
							{
								BlockHelper.setWithoutUpdate(world, mut, y == top ? ModBlocks.LACUGROVE.bark.get() : ModBlocks.LACUGROVE.log.get());
							}
							else {
								break;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private void leavesBall(IServerWorld world, BlockPos pos, float radius, Random random, OpenSimplexNoise noise)
	{
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(ModBlocks.LACUGROVE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE, 6));
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return random.nextFloat() * 3F - 1.5F; }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius - 2, 0).setSource(sphere));
		Mutable mut = new Mutable();
		sphere.addPostProcess((info) -> {
			if (random.nextInt(5) == 0) 
			{
				for (Direction dir: Direction.values()) 
				{
					BlockState state = info.getState(dir, 2);
					if (state.isAir()) 
					{
						return info.getState();
					}
				}
				info.setState(ModBlocks.LACUGROVE.bark.get().getDefaultState());
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
									if (d < distance) {
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
		
		if (radius > 5) {
			int count = (int) (radius * 2.5F);
			for (int i = 0; i < count; i++) 
			{
				BlockPos p = pos.add(random.nextGaussian() * 1, random.nextGaussian() * 1, random.nextGaussian() * 1);
				boolean place = true;
				for (Direction d: Direction.values()) 
				{
					BlockState state = world.getBlockState(p.offset(d));
					if (!ModBlocks.LACUGROVE.isTreeLog(state) && !state.isIn(ModBlocks.LACUGROVE_LEAVES.get())) 
					{
						place = false;
						break;
					}
				}
				if (place) 
				{
					BlockHelper.setWithoutUpdate(world, p, ModBlocks.LACUGROVE.bark.get());
				}
			}
		}
		
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.LACUGROVE.bark.get());
	}
}
