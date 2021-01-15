package mod.beethoven92.betterendforge.common.world.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.world.structure.piece.VoxelPiece;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class GiantIceStarStructure extends SDFStructure
{
	private final float minSize = 20;
	private final float maxSize = 35;
	private final int minCount = 25;
	private final int maxCount = 40;
	
	public GiantIceStarStructure(Codec<NoFeatureConfig> codec) 
	{
		super(codec);
	}
	
	@Override
	public Decoration getDecorationStage() 
	{
		return Decoration.SURFACE_STRUCTURES;
	}
	
	@Override
	public String getStructureName() 
	{
		return BetterEnd.MOD_ID + ":giant_ice_star_structure";
	}

	@Override
	protected SDF getSDF(BlockPos pos, Random random)
	{
		float size = ModMathHelper.randRange(minSize, maxSize, random);
		int count = ModMathHelper.randRange(minCount, maxCount, random);
		List<Vector3f> points = getFibonacciPoints(count);
		SDF sdf = null;
		SDF spike = new SDFCappedCone().setRadius1(3 + (size - 5) * 0.2F).setRadius2(0).setHeight(size).setBlock(ModBlocks.DENSE_SNOW.get());
		spike = new SDFTranslate().setTranslate(0, size - 0.5F, 0).setSource(spike);
		for (Vector3f point: points) 
		{
			SDF rotated = spike;
			point = ModMathHelper.normalize(point);
			float angle = ModMathHelper.angle(Vector3f.YP, point);
			if (angle > 0.01F && angle < 3.14F) 
			{
				Vector3f axis = ModMathHelper.normalize(ModMathHelper.cross(Vector3f.YP, point));
				rotated = new SDFRotation().setRotation(axis, angle).setSource(spike);
			}
			else if (angle > 1) 
			{
				rotated = new SDFRotation().setRotation(Vector3f.YP, (float) Math.PI).setSource(spike);
			}
			sdf = (sdf == null) ? rotated : new SDFUnion().setSourceA(sdf).setSourceB(rotated);
		}
		
		final float ancientRadius = size * 0.7F;
		final float denseRadius = size * 0.9F;
		final float iceRadius = size < 7 ? size * 5 : size * 1.3F;
		final float randScale = size * 0.3F;
		
		final BlockPos center = pos;
		final BlockState ice = ModBlocks.EMERALD_ICE.get().getDefaultState();
		final BlockState dense = ModBlocks.DENSE_EMERALD_ICE.get().getDefaultState();
		final BlockState ancient = ModBlocks.ANCIENT_EMERALD_ICE.get().getDefaultState();
		final SDF sdfCopy = sdf;
		
		return sdf.addPostProcess((info) -> {
			BlockPos bpos = info.getPos();
			float px = bpos.getX() - center.getX();
			float py = bpos.getY() - center.getY();
			float pz = bpos.getZ() - center.getZ();
			float distance = ModMathHelper.length(px, py, pz) + sdfCopy.getDistance(px, py, pz) * 0.4F + random.nextFloat() * randScale;
			if (distance < ancientRadius) 
			{
				return ancient;
			}
			else if (distance < denseRadius) 
			{
				return dense;
			}
			else if (distance < iceRadius) 
			{
				return ice;
			}
			return info.getState();
		});
	}

	private List<Vector3f> getFibonacciPoints(int count) 
	{
		float max = count - 1;
		List<Vector3f> result = new ArrayList<Vector3f>(count);
		for (int i = 0; i < count; i++) 
		{
			float y = 1F - (i / max) * 2F;
			float radius = (float) Math.sqrt(1F - y * y);
			float theta = ModMathHelper.PHI * i;
			float x = (float) Math.cos(theta) * radius;
			float z = (float) Math.sin(theta) * radius;
			result.add(new Vector3f(x, y, z));
		}
		return result;
	}
	
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() 
	{
		return Start::new;
	}
	
	public static class Start extends StructureStart<NoFeatureConfig> 
	{
		public Start(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) 
		{
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void func_230364_a_(DynamicRegistries registry, ChunkGenerator chunkGenerator,
				TemplateManager manager, int chunkX, int chunkZ, Biome biome,
				NoFeatureConfig config) 
		{
			int x = (chunkX << 4) | ModMathHelper.randRange(4, 12, rand);
			int z = (chunkZ << 4) | ModMathHelper.randRange(4, 12, rand);
			BlockPos start = new BlockPos(x, ModMathHelper.randRange(32, 128, rand), z);
			VoxelPiece piece = new VoxelPiece((world) -> { ((SDFStructure) this.getStructure()).getSDF(start, this.rand).fillRecursive(world, start); }, rand.nextInt());
			this.components.add(piece);
			this.recalculateStructureSize();
		}

	}
}
