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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BushWithOuterFeature extends Feature<NoFeatureConfig> {
	private static final Direction[] DIRECTIONS = Direction.values();
	private static final Function<BlockState, Boolean> REPLACE;
	private final Block outer_leaves;
	private final Block leaves;
	private final Block stem;

	public BushWithOuterFeature(Block leaves, Block outer_leaves, Block stem) {
		super(NoFeatureConfig.field_236558_a_);
		this.outer_leaves = outer_leaves;
		this.leaves = leaves;
		this.stem = stem;
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator_, Random random, BlockPos pos,
			NoFeatureConfig config) {
		if (!world.getBlockState(pos.down()).getBlock().isIn(ModTags.END_GROUND)
				&& !world.getBlockState(pos.up()).getBlock().isIn(ModTags.END_GROUND))
			return false;

		float radius = ModMathHelper.randRange(1.8F, 3.5F, random);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextInt());
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(this.leaves);
		sphere = new SDFScale3D().setScale(1, 0.5F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 3;
		}).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return ModMathHelper.randRange(-2F, 2F, random);
		}).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere)
				.setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		sphere.setReplaceFunction(REPLACE);
		sphere.addPostProcess((info) -> {
			if (info.getState().getBlock() instanceof LeavesBlock) {
				int distance = info.getPos().manhattanDistance(pos);
				if (distance < 7) {
					return info.getState().with(LeavesBlock.DISTANCE, distance);
				} else {
					return Blocks.AIR.getDefaultState();
				}
			}
			return info.getState();
		}).addPostProcess((info) -> {
			if (info.getState().getBlock() instanceof LeavesBlock) {
				ModMathHelper.shuffle(DIRECTIONS, random);
				for (Direction dir : DIRECTIONS) {
					if (info.getState(dir).isAir()) {
						info.setBlockPos(info.getPos().offset(dir),
								outer_leaves.getDefaultState().with(BlockStateProperties.FACING, dir));
					}
				}
			}
			return info.getState();
		});
		sphere.fillRecursive(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, stem);
		for (Direction d : Direction.values()) {
			BlockPos p = pos.offset(d);
			if (world.isAirBlock(p)) {
				if (leaves instanceof LeavesBlock) {
					BlockHelper.setWithoutUpdate(world, p, leaves.getDefaultState().with(LeavesBlock.DISTANCE, 1));
				} else {
					BlockHelper.setWithoutUpdate(world, p, leaves.getDefaultState());
				}
			}
		}

		return true;
	}

	static {
		REPLACE = (state) -> {
			if (state.getMaterial().equals(Material.PLANTS)) {
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
}
