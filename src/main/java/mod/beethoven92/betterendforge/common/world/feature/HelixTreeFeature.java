package mod.beethoven92.betterendforge.common.world.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.block.HelixTreeLeavesBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.PosInfo;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSmoothUnion;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class HelixTreeFeature extends Feature<NoFeatureConfig> {
	private static final Function<PosInfo, BlockState> POST;
	
	public HelixTreeFeature() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}
	
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		if (!world.getBlockState(pos.down()).getBlock().isIn(ModTags.END_GROUND)) return false;
		BlockHelper.setWithoutUpdate(world, pos, Blocks.AIR.getDefaultState());
		
		float angle = rand.nextFloat() * ModMathHelper.PI2;
		float radiusRange = ModMathHelper.randRange(4.5F, 6F, rand);
		float scale = ModMathHelper.randRange(0.5F, 1F, rand);
		
		float dx;
		float dz;
		List<Vector3f> spline = new ArrayList<Vector3f>(10);
		for (int i = 0; i < 10; i++) {
			float radius = (0.9F - i * 0.1F) * radiusRange;
			dx = (float) Math.sin(i + angle) * radius;
			dz = (float) Math.cos(i + angle) * radius;
			spline.add(new Vector3f(dx, i * 2, dz));
		}
		SDF sdf = SplineHelper.buildSDF(spline, 1.7F, 0.5F, (p) -> { return ModBlocks.HELIX_TREE.bark.get().getDefaultState(); });
		SDF rotated = new SDFRotation().setRotation(Vector3f.YP, (float) Math.PI).setSource(sdf);
		sdf = new SDFUnion().setSourceA(rotated).setSourceB(sdf);
		
		Vector3f lastPoint = spline.get(spline.size() - 1);
		List<Vector3f> spline2 = SplineHelper.makeSpline(0, 0, 0, 0, 20, 0, 5);
		SDF stem = SplineHelper.buildSDF(spline2, 1.0F, 0.5F, (p) -> { return ModBlocks.HELIX_TREE.bark.get().getDefaultState(); });
		stem = new SDFTranslate().setTranslate(lastPoint.getX(), lastPoint.getY(), lastPoint.getZ()).setSource(stem);
		sdf = new SDFSmoothUnion().setRadius(3).setSourceA(sdf).setSourceB(stem);
		
		sdf = new SDFScale().setScale(scale).setSource(sdf);
		dx = 30 * scale;
		float dy1 = -20 * scale;
		float dy2 = 100 * scale;
		sdf.addPostProcess(POST).fillArea(world, pos, new AxisAlignedBB(pos.add(-dx, dy1, -dx), pos.add(dx, dy2, dx)));
		SplineHelper.scale(spline, scale);
		SplineHelper.fillSplineForce(spline, world, ModBlocks.HELIX_TREE.bark.get().getDefaultState(), pos, (state) -> {
			return state.getMaterial().isReplaceable();
		});
		SplineHelper.rotateSpline(spline, (float) Math.PI);
		SplineHelper.fillSplineForce(spline, world, ModBlocks.HELIX_TREE.bark.get().getDefaultState(), pos, (state) -> {
			return state.getMaterial().isReplaceable();
		});
		SplineHelper.scale(spline2, scale);
		BlockPos leafStart = pos.add(lastPoint.getX() + 0.5, lastPoint.getY() + 0.5, lastPoint.getZ() + 0.5);
		SplineHelper.fillSplineForce(spline2, world, ModBlocks.HELIX_TREE.log.get().getDefaultState(), leafStart, (state) -> {
			return state.getMaterial().isReplaceable();
		});
		
		spline.clear();
		//for (int i = 0; i <= 20; i++) {
			//float radius = 1 - i * 0.05F;
		float rad = ModMathHelper.randRange(8F, 11F, rand);
		int count = ModMathHelper.randRange(20, 30, rand);
		float scaleM = 20F / (float) count * scale * 1.75F;
		float hscale = 20F / (float) count * 0.05F;
		
		for (int i = 0; i <= count; i++)
		{
			float radius = 1 - i * hscale;
			radius = radius * radius * 2 - 1;
			radius *= radius;
			//radius = (1 - radius) * 8F * scale;
			radius = (1 - radius) * rad * scale;
			dx = (float) Math.sin(i * 0.45F + angle) * radius;
			dz = (float) Math.cos(i * 0.45F + angle) * radius;
			//spline.add(new Vector3f(dx, i * scale * 1.75F, dz));
			spline.add(new Vector3f(dx, i * scaleM, dz));
		}
		
		Vector3f start = new Vector3f();
		Vector3f end = new Vector3f();
		lastPoint = spline.get(0);
		BlockState leaf = ModBlocks.HELIX_TREE_LEAVES.get().getDefaultState();
		for (int i = 1; i < spline.size(); i++) {
			Vector3f point = spline.get(i);
			int minY = ModMathHelper.floor(lastPoint.getY());
			int maxY = ModMathHelper.floor(point.getY());
			float div = point.getY() - lastPoint.getY();
			for (float py = minY; py <= maxY; py += 0.2F) {
				start.set(0, py, 0);
				float delta = (float) (py - minY) / div;
				float px = MathHelper.lerp(delta, lastPoint.getX(), point.getX());
				float pz = MathHelper.lerp(delta, lastPoint.getZ(), point.getZ());
				end.set(px, py, pz);
				fillLine(start, end, world, leaf, leafStart, i / 2 - 1);
				float ax = Math.abs(px);
				float az = Math.abs(pz);
				if (ax > az) {
					start.set(start.getX(), start.getY(), start.getZ() + az > 0 ? 1 : -1);
					end.set(end.getX(), end.getY(), end.getZ() + az > 0 ? 1 : -1);
				}
				else {
					start.set(start.getX() + ax > 0 ? 1 : -1, start.getY(), start.getZ());
					end.set(end.getX() + ax > 0 ? 1 : -1, end.getY(), end.getZ());
				}
				fillLine(start, end, world, leaf, leafStart, i / 2 - 1);
			}
			lastPoint = point;
		}
		
		leaf = leaf.with(HelixTreeLeavesBlock.COLOR, 7);
		leafStart = leafStart.add(0, lastPoint.getY(), 0);
		if (world.getBlockState(leafStart).isAir()) {
			BlockHelper.setWithoutUpdate(world, leafStart, leaf);
			leafStart = leafStart.up();
			if (world.getBlockState(leafStart).isAir()) {
				BlockHelper.setWithoutUpdate(world, leafStart, leaf);
				leafStart = leafStart.up();
						if (world.getBlockState(leafStart).isAir()) {
							BlockHelper.setWithoutUpdate(world, leafStart, leaf);
						}
			}
		}
		
		return true;
	}
	
	private void fillLine(Vector3f start, Vector3f end, ISeedReader world, BlockState state, BlockPos pos, int offset) {
		float dx = end.getX() - start.getX();
		float dy = end.getY() - start.getY();
		float dz = end.getZ() - start.getZ();
		float max = ModMathHelper.max(Math.abs(dx), Math.abs(dy), Math.abs(dz));
		int count = ModMathHelper.floor(max + 1);
		dx /= max;
		dy /= max;
		dz /= max;
		float x = start.getX();
		float y = start.getY();
		float z = start.getZ();
		
		Mutable bPos = new Mutable();
		for (int i = 0; i < count; i++) {
			bPos.setPos(x + pos.getX(), y + pos.getY(), z + pos.getZ());
			int color = ModMathHelper.floor((float) i / (float) count * 7F + 0.5F) + offset;
			color = MathHelper.clamp(color, 0, 7);
			if (world.getBlockState(bPos).getMaterial().isReplaceable()) {
				BlockHelper.setWithoutUpdate(world, bPos, state.with(HelixTreeLeavesBlock.COLOR, color));
			}
			x += dx;
			y += dy;
			z += dz;
		}
		bPos.setPos(end.getX() + pos.getX(), end.getY() + pos.getY(), end.getZ() + pos.getZ());
		if (world.getBlockState(bPos).getMaterial().isReplaceable()) {
			BlockHelper.setWithoutUpdate(world, bPos, state.with(HelixTreeLeavesBlock.COLOR, 7));
		}
	}
	
	static {
		POST = (info) -> {
			if (ModBlocks.HELIX_TREE.isTreeLog(info.getStateUp()) && ModBlocks.HELIX_TREE.isTreeLog(info.getStateDown())) {
				return ModBlocks.HELIX_TREE.log.get().getDefaultState();
			}
			return info.getState();
		};
	}
}
