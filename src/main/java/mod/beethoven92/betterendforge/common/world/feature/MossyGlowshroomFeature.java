package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.block.MossyGlowshroomCapBlock;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFBinary;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFCoordModify;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFFlatWave;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSmoothUnion;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFPrimitive;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MossyGlowshroomFeature extends Feature<NoneFeatureConfiguration>
{
	private static final Function<BlockState, Boolean> REPLACE;
	private static final Vector3f CENTER = new Vector3f();
	private static final SDFBinary FUNCTION;
	private static final SDFTranslate HEAD_POS;
	private static final SDFFlatWave ROOTS_ROT;
	
	private static final SDFPrimitive CONE1;
	private static final SDFPrimitive CONE2;
	private static final SDFPrimitive CONE_GLOW;
	private static final SDFPrimitive ROOTS;
	
	static 
	{
		SDFCappedCone cone1 = new SDFCappedCone().setHeight(2.5F).setRadius1(1.5F).setRadius2(2.5F);
		SDFCappedCone cone2 = new SDFCappedCone().setHeight(3F).setRadius1(2.5F).setRadius2(13F);
		SDF posedCone2 = new SDFTranslate().setTranslate(0, 5, 0).setSource(cone2);
		SDF posedCone3 = new SDFTranslate().setTranslate(0, 12F, 0).setSource(new SDFScale().setScale(2).setSource(cone2));
		SDF upCone = new SDFSubtraction().setSourceA(posedCone2).setSourceB(posedCone3);
		SDF wave = new SDFFlatWave().setRaysCount(12).setIntensity(1.3F).setSource(upCone);
		SDF cones = new SDFSmoothUnion().setRadius(3).setSourceA(cone1).setSourceB(wave);
		
		CONE1 = cone1;
		CONE2 = cone2;
		
		SDF innerCone = new SDFTranslate().setTranslate(0, 1.25F, 0).setSource(upCone);
		innerCone = new SDFScale3D().setScale(1.2F, 1F, 1.2F).setSource(innerCone);
		cones = new SDFUnion().setSourceA(cones).setSourceB(innerCone);
		
		SDF glowCone = new SDFCappedCone().setHeight(3F).setRadius1(2F).setRadius2(12.5F);
		CONE_GLOW = (SDFPrimitive) glowCone;
		glowCone = new SDFTranslate().setTranslate(0, 4.25F, 0).setSource(glowCone);
		glowCone = new SDFSubtraction().setSourceA(glowCone).setSourceB(posedCone3);
		
		cones = new SDFUnion().setSourceA(cones).setSourceB(glowCone);
		
		OpenSimplexNoise noise = new OpenSimplexNoise(1234);
		cones = new SDFCoordModify().setFunction((pos) -> {
			float dist = ModMathHelper.length(pos.x(), pos.z());
			float y = pos.y() + (float) noise.eval(pos.x() * 0.1 + CENTER.x(), pos.z() * 0.1 + CENTER.z()) * dist * 0.3F - dist * 0.15F;
			pos.set(pos.x(), y, pos.z());
		}).setSource(cones);
		
		HEAD_POS = (SDFTranslate) new SDFTranslate().setSource(new SDFTranslate().setTranslate(0, 2.5F, 0).setSource(cones));
		
		SDF roots = new SDFSphere().setRadius(4F);
		ROOTS = (SDFPrimitive) roots;
		roots = new SDFScale3D().setScale(1, 0.7F, 1).setSource(roots);
		ROOTS_ROT = (SDFFlatWave) new SDFFlatWave().setRaysCount(5).setIntensity(1.5F).setSource(roots);
		
		FUNCTION = new SDFSmoothUnion().setRadius(4).setSourceB(new SDFUnion().setSourceA(HEAD_POS).setSourceB(ROOTS_ROT));
		
		REPLACE = (state) -> {
			if (state.is(ModTags.END_GROUND)) 
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
	
	public MossyGlowshroomFeature() 
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, Random random,
			BlockPos blockPos, NoneFeatureConfiguration config) 
	{
		BlockState down = world.getBlockState(blockPos.below());
		if (!down.is(ModBlocks.END_MYCELIUM.get()) && !down.is(ModBlocks.END_MOSS.get()))
		{
			return false;
		}
		
		CONE1.setBlock(ModBlocks.MOSSY_GLOWSHROOM_CAP.get());
		CONE2.setBlock(ModBlocks.MOSSY_GLOWSHROOM_CAP.get());
		CONE_GLOW.setBlock(ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE.get());
		ROOTS.setBlock(ModBlocks.MOSSY_GLOWSHROOM.bark.get());
		
		float height = ModMathHelper.randRange(10F, 25F, random);
		int count = Mth.floor(height / 4);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, height, 0, count);
		SplineHelper.offsetParts(spline, random, 1F, 0, 1F);
		SDF sdf = SplineHelper.buildSDF(spline, 2.1F, 1.5F, (pos) -> {
			return ModBlocks.MOSSY_GLOWSHROOM.log.get().defaultBlockState();
		});
		Vector3f pos = spline.get(spline.size() - 1);
		float scale = ModMathHelper.randRange(0.75F, 1.1F, random);
		
		if (!SplineHelper.canGenerate(spline, scale, blockPos, world, REPLACE)) 
		{
			return false;
		}
		world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 1 | 2 | 16);
		
		CENTER.set(blockPos.getX(), 0, blockPos.getZ());
		HEAD_POS.setTranslate(pos.x(), pos.y(), pos.z());
		ROOTS_ROT.setAngle(random.nextFloat() * ModMathHelper.PI2);
		FUNCTION.setSourceA(sdf);
	
		new SDFScale().setScale(scale)
				.setSource(FUNCTION)
				.setReplaceFunction(REPLACE)
				.addPostProcess((info) -> {
					if (ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getState())) 
					{
						if (random.nextBoolean() && info.getStateUp().getBlock() == ModBlocks.MOSSY_GLOWSHROOM_CAP.get()) 
						{
							info.setState(ModBlocks.MOSSY_GLOWSHROOM_CAP.get().defaultBlockState().setValue(MossyGlowshroomCapBlock.TRANSITION, true));
							return info.getState();
						}
						else if (!ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getStateUp()) || !ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getStateDown()))
						{
							info.setState(ModBlocks.MOSSY_GLOWSHROOM.bark.get().defaultBlockState());
							return info.getState();
						}
					}
					else if (info.getState().getBlock() == ModBlocks.MOSSY_GLOWSHROOM_CAP.get())
					{
						if (ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getStateDown().getBlock())) 
						{
							info.setState(ModBlocks.MOSSY_GLOWSHROOM_CAP.get().defaultBlockState().setValue(MossyGlowshroomCapBlock.TRANSITION, true));
							return info.getState();
						}
						
						info.setState(ModBlocks.MOSSY_GLOWSHROOM_CAP.get().defaultBlockState());
						return info.getState();
					}
					else if (info.getState().getBlock() == ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE.get()) 
					{
						for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
						{
							if (info.getState(dir) == Blocks.AIR.defaultBlockState()) 
							{
								info.setBlockPos(info.getPos().relative(dir), ModBlocks.MOSSY_GLOWSHROOM_FUR.get().defaultBlockState().setValue(FurBlock.FACING, dir));
							}
						}
						
						if (info.getStateDown().getBlock() != ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE.get()) 
						{
							info.setBlockPos(info.getPos().below(), ModBlocks.MOSSY_GLOWSHROOM_FUR.get().defaultBlockState().setValue(FurBlock.FACING, Direction.DOWN));
						}
					}
					return info.getState();
				})
				.fillRecursive(world, blockPos);
		
		return true;
	}
}
