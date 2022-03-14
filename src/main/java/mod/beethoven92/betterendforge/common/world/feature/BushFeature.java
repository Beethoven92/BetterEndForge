package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.Block;
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

public class BushFeature extends Feature<NoneFeatureConfiguration>
{
	private static final Function<BlockState, Boolean> REPLACE;
	private final Block leaves;
	private final Block stem;
	
	static
	{
		REPLACE = (state) -> {
			if (state.getMaterial().equals(Material.PLANT)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
	
	public BushFeature(Block leaves, Block stem) 
	{
		super(NoneFeatureConfiguration.CODEC);
		this.leaves = leaves;
		this.stem = stem;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator_, Random random,
			BlockPos pos, NoneFeatureConfiguration config) 
	{
		if (!world.getBlockState(pos.below()).getBlock().is(ModTags.END_GROUND) && !world.getBlockState(pos.above()).getBlock().is(ModTags.END_GROUND)) return false;
		
		float radius = ModMathHelper.randRange(1.8F, 3.5F, random);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextInt());
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(this.leaves);
		sphere = new SDFScale3D().setScale(1, 0.5F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.x() * 0.2, vec.y() * 0.2, vec.z() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return ModMathHelper.randRange(-2F, 2F, random); }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		sphere.setReplaceFunction(REPLACE);
		sphere.addPostProcess((info) -> {
			if (info.getState().getBlock() instanceof LeavesBlock) 
			{
				int distance = info.getPos().distManhattan(pos);
				if (distance < 7) 
				{
					return info.getState().setValue(LeavesBlock.DISTANCE, distance);
				}
				else 
				{
					return Blocks.AIR.defaultBlockState();
				}
			}
			return info.getState();
		});
		sphere.fillRecursive(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, stem);
		for (Direction d: Direction.values()) 
		{
			BlockPos p = pos.relative(d);
			if (world.isEmptyBlock(p)) 
			{
				if (leaves instanceof LeavesBlock) 
				{
					BlockHelper.setWithoutUpdate(world, p, leaves.defaultBlockState().setValue(LeavesBlock.DISTANCE, 1));
				}
				else 
				{
					BlockHelper.setWithoutUpdate(world, p, leaves.defaultBlockState());
				}
			}
		}		
		return true;
	}
}
