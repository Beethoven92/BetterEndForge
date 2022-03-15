package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ObsidianBoulderFeature extends Feature<NoneFeatureConfiguration> {
	
	public ObsidianBoulderFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, Random random,
			BlockPos pos, NoneFeatureConfiguration config) {
		pos = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(pos.getX() + random.nextInt(16), pos.getY(), pos.getZ() + random.nextInt(16)));
		if (!world.getBlockState(pos.below()).is(ModTags.END_GROUND)) {
			return false;
		}
		
		int count = ModMathHelper.randRange(1, 5, random);
		for (int i = 0; i < count; i++) {
			BlockPos p = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(pos.getX() + random.nextInt(16) - 8, pos.getY(), pos.getZ() + random.nextInt(16) - 8));
			makeBoulder(world, p, random);
		}
		
		return true;
	}
	
	private void makeBoulder(WorldGenLevel world, BlockPos pos, Random random) {
		if (!world.getBlockState(pos.below()).is(ModTags.END_GROUND)) {
			return;
		}
		
		float radius = ModMathHelper.randRange(1F, 5F, random);
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(Blocks.OBSIDIAN);
		float sx = ModMathHelper.randRange(0.7F, 1.3F, random);
		float sy = ModMathHelper.randRange(0.7F, 1.3F, random);
		float sz = ModMathHelper.randRange(0.7F, 1.3F, random);
		sphere = new SDFScale3D().setScale(sx, sy, sz).setSource(sphere);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return (float) (noise.eval(vec.x() * 0.2, vec.y() * 0.2, vec.z() * 0.2) * 1.5F);
		}).setSource(sphere);
		
		BlockState mossy = ModBlocks.MOSSY_OBSIDIAN.get().defaultBlockState();
		sphere.addPostProcess((info) -> {
			if (info.getStateUp().isAir() && random.nextFloat() > 0.1F) {
				return mossy;
			}
			return info.getState();
		}).setReplaceFunction((state) -> {
			return state.getMaterial().isReplaceable() || state.is(ModTags.GEN_TERRAIN) || state.getMaterial().equals(Material.PLANT);
		}).fillRecursive(world, pos);
	}
}
