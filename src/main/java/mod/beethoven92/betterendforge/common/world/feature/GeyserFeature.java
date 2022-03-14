package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.block.HydrothermalVentBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFCoordModify;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFInvert;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSmoothUnion;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFFlatland;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFPrimitive;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GeyserFeature extends Feature<NoneFeatureConfiguration>
{
	protected static final Function<BlockState, Boolean> REPLACE1;
	protected static final Function<BlockState, Boolean> REPLACE2;
	private static final Function<BlockState, Boolean> IGNORE;
	private static final Direction[] HORIZONTAL = BlockHelper.makeHorizontal();
	
	static 
	{
		REPLACE1 = (state) -> {
			return state.isAir() || (state.is(ModTags.GEN_TERRAIN));
		};
		
		REPLACE2 = (state) -> {
			if (state.is(ModTags.GEN_TERRAIN) || state.is(ModBlocks.HYDROTHERMAL_VENT.get()) || state.is(ModBlocks.SULPHUR_CRYSTAL.get()))
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
			return state.is(Blocks.WATER) || state.is(Blocks.CAVE_AIR) || state.is(ModBlocks.SULPHURIC_ROCK.stone.get()) || state.is(ModBlocks.BRIMSTONE.get());
		};
	}
	
	public GeyserFeature() 
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config) 
	{
		pos = FeatureHelper.getPosOnSurfaceWG(world, pos);
		
		if (pos.getY() < 10) 
		{
			return false;
		}
		
		MutableBlockPos bpos = new MutableBlockPos().set(pos);
		bpos.setY(bpos.getY() - 1);
		BlockState state = world.getBlockState(bpos);
		while (state.is(ModTags.GEN_TERRAIN) || !state.getFluidState().isEmpty() && bpos.getY() > 5) 
		{
			bpos.setY(bpos.getY() - 1);
			state = world.getBlockState(bpos);
		}
		
		if (pos.getY() - bpos.getY() < 25) 
		{
			return false;
		}

		int halfHeight = ModMathHelper.randRange(10, 20, rand);
		float radius1 = halfHeight * 0.5F;
		float radius2 = halfHeight * 0.1F + 0.5F;
		SDF sdf = new SDFCappedCone().setHeight(halfHeight).setRadius1(radius1).setRadius2(radius2).setBlock(ModBlocks.SULPHURIC_ROCK.stone.get());
		sdf = new SDFTranslate().setTranslate(0, halfHeight - 3, 0).setSource(sdf);

		int count = halfHeight;
		for (int i = 0; i < count; i++) 
		{
			int py = i << 1;
			float delta = (float) i / (float) (count - 1);
			float radius = Mth.lerp(delta, radius1, radius2) * 1.3F;

			SDF bowl = new SDFCappedCone().setHeight(radius).setRadius1(0).setRadius2(radius).setBlock(ModBlocks.SULPHURIC_ROCK.stone.get());

			SDF brimstone = new SDFCappedCone().setHeight(radius).setRadius1(0).setRadius2(radius).setBlock(ModBlocks.BRIMSTONE.get());
			brimstone = new SDFTranslate().setTranslate(0, 2F, 0).setSource(brimstone);
			bowl = new SDFSubtraction().setSourceA(bowl).setSourceB(brimstone);
			bowl = new SDFUnion().setSourceA(brimstone).setSourceB(bowl);

			SDF water = new SDFCappedCone().setHeight(radius).setRadius1(0).setRadius2(radius).setBlock(Blocks.WATER);
			water = new SDFTranslate().setTranslate(0, 4, 0).setSource(water);
			bowl = new SDFSubtraction().setSourceA(bowl).setSourceB(water);
			bowl = new SDFUnion().setSourceA(water).setSourceB(bowl);

			final OpenSimplexNoise noise1 = new OpenSimplexNoise(rand.nextLong());
			final OpenSimplexNoise noise2 = new OpenSimplexNoise(rand.nextLong());

			bowl = new SDFCoordModify().setFunction((vec) -> {
				float dx = (float) noise1.eval(vec.x() * 0.1, vec.y() * 0.1, vec.z() * 0.1);
				float dz = (float) noise2.eval(vec.x() * 0.1, vec.y() * 0.1, vec.z() * 0.1);
				vec.set(vec.x() + dx, vec.y(), vec.z() + dz);
			}).setSource(bowl);

			SDF cut = new SDFFlatland().setBlock(Blocks.AIR);
			cut = new SDFInvert().setSource(cut);
			cut = new SDFTranslate().setTranslate(0, radius - 2, 0).setSource(cut);
			bowl = new SDFSubtraction().setSourceA(bowl).setSourceB(cut);

			bowl = new SDFTranslate().setTranslate(radius, py - radius, 0).setSource(bowl);
			bowl = new SDFRotation().setRotation(Vector3f.YP, i * 4F).setSource(bowl);
			sdf = new SDFUnion().setSourceA(sdf).setSourceB(bowl);
		}
		sdf.setReplaceFunction(REPLACE2).fillRecursive(world, pos);

		radius2 = radius2 * 0.5F;
		if (radius2 < 0.7F) 
		{
			radius2 = 0.7F;
		}
		final OpenSimplexNoise noise = new OpenSimplexNoise(rand.nextLong());

		SDFPrimitive obj1;
		SDFPrimitive obj2;

		obj1 = new SDFCappedCone().setHeight(halfHeight + 5).setRadius1(radius1 * 0.5F).setRadius2(radius2);
		sdf = new SDFTranslate().setTranslate(0, halfHeight - 13, 0).setSource(obj1);
		sdf = new SDFDisplacement().setFunction((vec) -> {
			return (float) noise.eval(vec.x() * 0.3F, vec.y() * 0.3F, vec.z() * 0.3F) * 0.5F;
		}).setSource(sdf);

		obj2 = new SDFSphere().setRadius(radius1);
		SDF cave = new SDFScale3D().setScale(1.5F, 1, 1.5F).setSource(obj2);
		cave = new SDFDisplacement().setFunction((vec) -> {
			return (float) noise.eval(vec.x() * 0.1F, vec.y() * 0.1F, vec.z() * 0.1F) * 2F;
		}).setSource(cave);
		cave = new SDFTranslate().setTranslate(0, -halfHeight - 10, 0).setSource(cave);

		sdf = new SDFSmoothUnion().setRadius(5).setSourceA(cave).setSourceB(sdf);

		obj1.setBlock(Blocks.WATER);
		obj2.setBlock(Blocks.WATER);
		sdf.setReplaceFunction(REPLACE2);
		sdf.fillRecursive(world, pos);

		obj1.setBlock(ModBlocks.BRIMSTONE.get());
		obj2.setBlock(ModBlocks.BRIMSTONE.get());
		new SDFDisplacement().setFunction((vec) -> {
			return -2F;
		}).setSource(sdf).setReplaceFunction(REPLACE1).fillRecursiveIgnore(world, pos, IGNORE);

		obj1.setBlock(ModBlocks.SULPHURIC_ROCK.stone.get());
		obj2.setBlock(ModBlocks.SULPHURIC_ROCK.stone.get());
		new SDFDisplacement().setFunction((vec) -> {
			return -4F;
		}).setSource(cave).setReplaceFunction(REPLACE1).fillRecursiveIgnore(world, pos, IGNORE);

		obj1.setBlock(Blocks.END_STONE);
		obj2.setBlock(Blocks.END_STONE);
		new SDFDisplacement().setFunction((vec) -> {
			return -6F;
		}).setSource(cave).setReplaceFunction(REPLACE1).fillRecursiveIgnore(world, pos, IGNORE);

		BlockHelper.setWithoutUpdate(world, pos, Blocks.WATER);
		MutableBlockPos mut = new MutableBlockPos().set(pos);
		count = FeatureHelper.getYOnSurface(world, pos.getX(), pos.getZ()) - pos.getY();
		for (int i = 0; i < count; i++) 
		{
			BlockHelper.setWithoutUpdate(world, mut, Blocks.WATER);
			for (Direction dir : BlockHelper.HORIZONTAL_DIRECTIONS) 
			{
				BlockHelper.setWithoutUpdate(world, mut.relative(dir), Blocks.WATER);
			}
			mut.setY(mut.getY() + 1);
		}

		for (int i = 0; i < 150; i++) 
		{
			mut.set(pos).move(ModMathHelper.floor(rand.nextGaussian() * 4 + 0.5), -halfHeight - 10, ModMathHelper.floor(rand.nextGaussian() * 4 + 0.5));
			float distRaw = ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ());
			int dist = ModMathHelper.floor(6 - distRaw) + rand.nextInt(2);
			if (dist >= 0) 
			{
				state = world.getBlockState(mut);
				while (!state.getFluidState().isEmpty() || state.getMaterial().equals(Material.WATER_PLANT)) 
				{
					mut.setY(mut.getY() - 1);
					state = world.getBlockState(mut);
				}
				if (state.is(ModTags.GEN_TERRAIN) && !world.getBlockState(mut.above()).is(ModBlocks.HYDROTHERMAL_VENT.get())) 
				{
					for (int j = 0; j <= dist; j++) 
					{
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.SULPHURIC_ROCK.stone.get());
						ModMathHelper.shuffle(HORIZONTAL, rand);
						for (Direction dir : HORIZONTAL) 
						{
							BlockPos p = mut.relative(dir);
							if (rand.nextBoolean() && world.getBlockState(p).is(Blocks.WATER)) 
							{
								BlockHelper.setWithoutUpdate(world, p, ModBlocks.TUBE_WORM.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, dir));
							}
						}
						mut.setY(mut.getY() + 1);
					}
					state = ModBlocks.HYDROTHERMAL_VENT.get().defaultBlockState().setValue(HydrothermalVentBlock.ACTIVATED, distRaw < 2);
					BlockHelper.setWithoutUpdate(world, mut, state);
					mut.setY(mut.getY() + 1);
					state = world.getBlockState(mut);
					while (state.is(Blocks.WATER)) 
					{
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.VENT_BUBBLE_COLUMN.get().defaultBlockState());
						mut.setY(mut.getY() + 1);
						state = world.getBlockState(mut);
					}
				}
			}
		}

		for (int i = 0; i < 10; i++) 
		{
			mut.set(pos).move(ModMathHelper.floor(rand.nextGaussian() * 0.7 + 0.5), -halfHeight - 10, ModMathHelper.floor(rand.nextGaussian() * 0.7 + 0.5));
			float distRaw = ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ());
			int dist = ModMathHelper.floor(6 - distRaw) + rand.nextInt(2);
			if (dist >= 0) 
			{
				state = world.getBlockState(mut);
				while (state.is(Blocks.WATER)) 
				{
					mut.setY(mut.getY() - 1);
					state = world.getBlockState(mut);
				}
				if (state.is(ModTags.GEN_TERRAIN)) 
				{
					for (int j = 0; j <= dist; j++) 
					{
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.SULPHURIC_ROCK.stone.get());
						mut.setY(mut.getY() + 1);
					}
					state = ModBlocks.HYDROTHERMAL_VENT.get().defaultBlockState().setValue(HydrothermalVentBlock.ACTIVATED, distRaw < 2);
					BlockHelper.setWithoutUpdate(world, mut, state);
					mut.setY(mut.getY() + 1);
					state = world.getBlockState(mut);
					while (state.is(Blocks.WATER)) 
					{
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.VENT_BUBBLE_COLUMN.get().defaultBlockState());
						mut.setY(mut.getY() + 1);
						state = world.getBlockState(mut);
					}
				}
			}
		}

		ModFeatures.SULPHURIC_LAKE.place(world, generator, rand, pos, null);

		double distance = radius1 * 1.7;
		BlockPos start = pos.offset(-distance, -halfHeight - 15 - distance, -distance);
		BlockPos end = pos.offset(distance, -halfHeight - 5 + distance, distance);
		BlockHelper.fixBlocks(world, start, end);

		return true;
	}
}
