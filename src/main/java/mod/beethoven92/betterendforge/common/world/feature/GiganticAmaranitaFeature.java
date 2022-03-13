package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.PosInfo;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GiganticAmaranitaFeature extends Feature<NoFeatureConfig> {
	
	public GiganticAmaranitaFeature() {
		super(NoFeatureConfig.CODEC);
	}

	private static final Function<BlockState, Boolean> REPLACE;
	private static final Function<BlockState, Boolean> IGNORE;
	private static final Function<PosInfo, BlockState> POST;
	
	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos,
			NoFeatureConfig config) {
		if (!world.getBlockState(pos.below()).getBlock().is(ModTags.END_GROUND)) return false;
		
		float size = ModMathHelper.randRange(5, 10, random);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, size, 0, 5);
		SplineHelper.offsetParts(spline, random, 0.7F, 0, 0.7F);
		
		if (!SplineHelper.canGenerate(spline, pos, world, REPLACE)) {
			return false;
		}
		BlockHelper.setWithoutUpdate(world, pos, Blocks.AIR.defaultBlockState());
		
		float radius = size * 0.17F;//MHelper.randRange(0.8F, 1.2F, random);
		SDF function = SplineHelper.buildSDF(spline, radius, 0.2F, (bpos) -> {
			return ModBlocks.AMARANITA_STEM.get().defaultBlockState();
		});
		
		Vector3f capPos = spline.get(spline.size() - 1);
		makeHead(world, pos.offset(capPos.x() + 0.5F, capPos.y() + 1.5F ,capPos.z() + 0.5F), MathHelper.floor(size / 1.6F));
		
		function.setReplaceFunction(REPLACE);
		function.addPostProcess(POST);
		function.fillRecursiveIgnore(world, pos, IGNORE);
		
		for (int i = 0; i < 3; i++) {
			List<Vector3f> copy = SplineHelper.copySpline(spline);
			SplineHelper.offsetParts(copy, random, 0.2F, 0, 0.2F);
			SplineHelper.fillSplineForce(copy, world, ModBlocks.AMARANITA_HYPHAE.get().defaultBlockState(), pos, REPLACE);
		}
		
		return true;
	}
	
	private void makeHead(ISeedReader world, BlockPos pos, int radius) {
		Mutable mut = new Mutable();
		if (radius < 2) {
			for (int i = -1; i < 2; i++) {
				mut.set(pos).move(Direction.NORTH, 2).move(Direction.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.set(pos).move(Direction.SOUTH, 2).move(Direction.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.set(pos).move(Direction.EAST, 2).move(Direction.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.set(pos).move(Direction.WEST, 2).move(Direction.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
			}
			for (int x = -1; x < 2; x++) {
				for (int z = -1; z < 2; z++) {
					mut.set(pos).move(x, 0, z);
					if (world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN.get());
						mut.move(Direction.DOWN);
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN.get());
						mut.move(Direction.DOWN);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_FUR.get().defaultBlockState().setValue(AttachedBlock.FACING, Direction.DOWN));
						}
					}
				}
			}
			
			int h = radius + 1;
			for (int y = 0; y < h; y++) {
				mut.setY(pos.getY() + y + 1);
				for (int x = -1; x < 2; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -1; z < 2; z++) {
						mut.setZ(pos.getZ() + z);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
						}
					}
				}
			}
			
			mut.setY(pos.getY() + h + 1);
			for (int x = -1; x < 2; x++) {
				mut.setX(pos.getX() + x);
				for (int z = -1; z < 2; z++) {
					mut.setZ(pos.getZ() + z);
					if ((x == 0 || z == 0) && world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
					}
				}
			}
		}
		else if (radius < 4) {
			pos = pos.offset(-1, 0, -1);
			for (int i = -2; i < 2; i++) {
				mut.set(pos).move(Direction.NORTH, 2).move(Direction.WEST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.set(pos).move(Direction.SOUTH, 3).move(Direction.WEST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.set(pos).move(Direction.EAST, 3).move(Direction.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.set(pos).move(Direction.WEST, 2).move(Direction.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
			}
			for (int x = -1; x < 3; x++) {
				for (int z = -1; z < 3; z++) {
					mut.set(pos).move(x, 0, z);
					if (world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN.get());
						mut.move(Direction.DOWN);
						if ((x >> 1) == 0 || (z >> 1) == 0) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN.get());
							Axis axis = x < 0 || x > 1 ? Axis.X : Axis.Z;
							int distance = axis == Axis.X ? x < 0 ? -1 : 1 : z < 0 ? -1 : 1;
							BlockPos offseted = mut.relative(axis, distance);
							if (world.getBlockState(offseted).getMaterial().isReplaceable()) {
								Direction dir = Direction.fromAxisAndDirection(axis, distance < 0 ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE);
								BlockHelper.setWithoutUpdate(world, offseted, ModBlocks.AMARANITA_FUR.get().defaultBlockState().setValue(AttachedBlock.FACING, dir));
							}
							mut.move(Direction.DOWN);
						}
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_FUR.get().defaultBlockState().setValue(AttachedBlock.FACING, Direction.DOWN));
						}
					}
				}
			}
			
			int h = radius - 1;
			for (int y = 0; y < h; y++) {
				mut.setY(pos.getY() + y + 1);
				for (int x = -1; x < 3; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -1; z < 3; z++) {
						mut.setZ(pos.getZ() + z);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
						}
					}
				}
			}
			
			mut.setY(pos.getY() + h + 1);
			for (int x = -1; x < 3; x++) {
				mut.setX(pos.getX() + x);
				for (int z = -1; z < 3; z++) {
					mut.setZ(pos.getZ() + z);
					if (((x >> 1) == 0 || (z >> 1) == 0) && world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
					}
				}
			}
		}
		else {
			for (int i = -2; i < 3; i++) {
				mut.set(pos).move(Direction.NORTH, 3).move(Direction.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.NORTH);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				
				mut.set(pos).move(Direction.SOUTH, 3).move(Direction.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.SOUTH);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				
				mut.set(pos).move(Direction.EAST, 3).move(Direction.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.EAST);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				
				mut.set(pos).move(Direction.WEST, 3).move(Direction.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
				mut.move(Direction.WEST);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
			}
			
			for (int i = 0; i < 4; i++) {
				mut.set(pos).move(Direction.UP).move(BlockHelper.HORIZONTAL_DIRECTIONS[i], 3).move(BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3], 3);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE.get());
				}
			}
			
			for (int x = -2; x < 3; x++) {
				for (int z = -2; z < 3; z++) {
					mut.set(pos).move(x, 0, z);
					if (world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN.get());
						mut.move(Direction.DOWN);
						if ((x / 2) == 0 || (z / 2) == 0) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN.get());
							Axis axis = x < 0 || x > 1 ? Axis.X : Axis.Z;
							int distance = axis == Axis.X ? x < 0 ? -1 : 1 : z < 0 ? -1 : 1;
							BlockPos offseted = mut.relative(axis, distance);
							if (world.getBlockState(offseted).getMaterial().isReplaceable()) {
								Direction dir = Direction.fromAxisAndDirection(axis, distance < 0 ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE);
								BlockHelper.setWithoutUpdate(world, offseted, ModBlocks.AMARANITA_FUR.get().defaultBlockState().setValue(AttachedBlock.FACING, dir));
							}
							mut.move(Direction.DOWN);
						}
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_FUR.get().defaultBlockState().setValue(AttachedBlock.FACING, Direction.DOWN));
						}
					}
				}
			}
			
			for (int y = 0; y < 3; y++) {
				mut.setY(pos.getY() + y + 1);
				for (int x = -2; x < 3; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -2; z < 3; z++) {
						mut.setZ(pos.getZ() + z);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
						}
					}
				}
			}
			
			int h = radius + 1;
			for (int y = 4; y < h; y++) {
				mut.setY(pos.getY() + y);
				for (int x = -2; x < 3; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -2; z < 3; z++) {
						mut.setZ(pos.getZ() + z);
						if (y < 6) {
							if (((x / 2) == 0 || (z / 2) == 0) && world.getBlockState(mut).getMaterial().isReplaceable()) {
								BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
							}
						}
						else {
							if ((x == 0 || z == 0) && (Math.abs(x) < 2 && Math.abs(z) < 2) && world.getBlockState(mut).getMaterial().isReplaceable()) {
								BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP.get());
							}
						}
					}
				}
			}
		}
	}
	
	static {
		REPLACE = (state) -> {
			if (state.is(ModTags.END_GROUND) || state.getMaterial().equals(Material.PLANT)) {
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
		
		IGNORE = (state) -> {
			return ModBlocks.DRAGON_TREE.isTreeLog(state);
		};
		
		POST = (info) -> {
			if (!info.getStateUp().is(ModBlocks.AMARANITA_STEM.get()) || !info.getStateDown().is(ModBlocks.AMARANITA_STEM.get())) {
				return ModBlocks.AMARANITA_HYPHAE.get().defaultBlockState();
			}
			return info.getState();
		};
	}
}
