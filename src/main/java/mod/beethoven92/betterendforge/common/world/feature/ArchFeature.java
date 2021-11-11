package mod.beethoven92.betterendforge.common.world.feature;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFTorus;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import mod.beethoven92.betterendforge.data.AABBAcc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;


import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ArchFeature extends Feature<NoFeatureConfig> {
	private Function<BlockPos, BlockState> surfaceFunction;
	private Block block;
	
	public ArchFeature(Block block, Function<BlockPos, BlockState> surfaceFunction) {
        super(NoFeatureConfig.field_236558_a_);
        this.surfaceFunction = surfaceFunction;
		this.block = block;
	}
	
	@Override
	public boolean generate(ISeedReader level, ChunkGenerator generator, Random random, BlockPos origin,
                            NoFeatureConfig config) {
		final ISeedReader world = level;

		
		BlockPos pos = FeatureHelper.getPosOnSurfaceWG(
			world,
			new BlockPos((origin.getX() & 0xFFFFFFF0) | 7, 0, (origin.getZ() & 0xFFFFFFF0) | 7)
		);
		if (!world.getBlockState(pos.down(5)).isIn(ModTags.GEN_TERRAIN)) {
			return false;
		}
		
		float bigRadius = ModMathHelper.randRange(10F, 20F, random);
		float smallRadius = ModMathHelper.randRange(3F, 7F, random);
		if (smallRadius + bigRadius > 23) {
			smallRadius = 23 - bigRadius;
		}
		SDF arch = new SDFTorus().setBigRadius(bigRadius).setSmallRadius(smallRadius).setBlock(block);
		arch = new SDFRotation().setRotation(ModMathHelper.randomHorizontal(random), (float) Math.PI * 0.5F).setSource(arch);
		
		final float smallRadiusF = smallRadius;
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		arch = new SDFDisplacement().setFunction((vec) -> {
			return (float) (Math.abs(noise.eval(vec.getX() * 0.1,
				vec.getY() * 0.1,
				vec.getZ() * 0.1
			)) * 3F + Math.abs(noise.eval(
				vec.getX() * 0.3,
				vec.getY() * 0.3 + 100,
				vec.getZ() * 0.3
			)) * 1.3F) - smallRadiusF * Math.abs(1 - vec.getY() / bigRadius);
		}).setSource(arch);
		
		List<BlockPos> surface = Lists.newArrayList();
		arch.addPostProcess((info) -> {
			if (info.getStateUp().isAir()) {
				return surfaceFunction.apply(info.getPos());
			}
			return info.getState();
		});
		
		float side = (bigRadius + smallRadius + 3F) * 2;
		if (side > 47) {
			side = 47;
		}
		arch.fillArea(world, pos, AABBAcc.ofSize(Vector3d.copyCentered(pos), side, side, side));
		
		return true;
	}
}


