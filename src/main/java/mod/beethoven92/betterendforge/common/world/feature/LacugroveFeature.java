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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.IWorld;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LacugroveFeature extends Feature<NoneFeatureConfiguration>
{
	private static final Function<BlockState, Boolean> REPLACE;
	private static final Function<BlockState, Boolean> IGNORE;
	private static final Function<PosInfo, BlockState> POST;
	
	static
	{
		REPLACE = (state) -> {
			if (state.is(ModTags.END_GROUND)) 
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
			if (state.getMaterial().equals(Material.PLANT)) 
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
				return ModBlocks.LACUGROVE.log.get().defaultBlockState();
			}
			return info.getState();
		};
	}
	
	public LacugroveFeature() 
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, Random random,
			BlockPos blockPos, NoneFeatureConfiguration config) 
	{
		if (!world.getBlockState(blockPos.below()).is(ModTags.END_GROUND)) return false;
		
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
		leavesBall(world, blockPos.offset(center.x(), center.y(), center.z()), radius, random, noise);
		
		radius = ModMathHelper.randRange(1.2F, 1.8F, random);
		SDF function = SplineHelper.buildSDF(spline, radius, 0.7F, (bpos) -> {
			return ModBlocks.LACUGROVE.bark.get().defaultBlockState();
		});
		
		function.setReplaceFunction(REPLACE);
		function.addPostProcess(POST);
		function.fillRecursive(world, blockPos);
		
		spline = spline.subList(4, 6);
		SplineHelper.fillSpline(spline, world, ModBlocks.LACUGROVE.bark.get().defaultBlockState(), blockPos, REPLACE);
		
		MutableBlockPos mut = new MutableBlockPos();
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
						if (world.getBlockState(mut).is(ModTags.END_GROUND)) 
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
							if (state.getMaterial().isReplaceable() || state.getMaterial().equals(Material.PLANT) || state.is(ModTags.END_GROUND))
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
	
	private void leavesBall(ServerLevelAccessor world, BlockPos pos, float radius, Random random, OpenSimplexNoise noise)
	{
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(ModBlocks.LACUGROVE_LEAVES.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 6));
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.x() * 0.2, vec.y() * 0.2, vec.z() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return random.nextFloat() * 3F - 1.5F; }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius - 2, 0).setSource(sphere));
		MutableBlockPos mut = new MutableBlockPos();
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
				info.setState(ModBlocks.LACUGROVE.bark.get().defaultBlockState());
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
									int distance = state.getValue(LeavesBlock.DISTANCE);
									if (d < distance) {
										info.setState(mut, state.setValue(LeavesBlock.DISTANCE, d));
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
				BlockPos p = pos.offset(random.nextGaussian() * 1, random.nextGaussian() * 1, random.nextGaussian() * 1);
				boolean place = true;
				for (Direction d: Direction.values()) 
				{
					BlockState state = world.getBlockState(p.relative(d));
					if (!ModBlocks.LACUGROVE.isTreeLog(state) && !state.is(ModBlocks.LACUGROVE_LEAVES.get())) 
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
