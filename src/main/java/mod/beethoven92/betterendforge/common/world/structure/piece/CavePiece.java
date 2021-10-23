package mod.beethoven92.betterendforge.common.world.structure.piece;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.init.ModStructures;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class CavePiece extends BasePiece {
	private OpenSimplexNoise noise;
	private BlockPos center;
	private float radius;
	
	public CavePiece(BlockPos center, float radius, Random random) {
		super(ModStructurePieces.CAVE_PIECE, random.nextInt());
		this.center = center;
		this.radius = radius;
		this.noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, center.getX(), center.getZ()));
		makeBoundingBox();
	}

	public CavePiece(TemplateManager templateManager, CompoundNBT tag) {
		super(ModStructurePieces.CAVE_PIECE, tag);
		makeBoundingBox();
	}




    @Override
	public boolean func_230383_a_(ISeedReader world, StructureManager arg, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox blockBox, ChunkPos chunkPos, BlockPos blockPos) {
		int x1 = ModMathHelper.max(this.getBoundingBox().minX, blockBox.minX);
		int z1 = ModMathHelper.max(this.getBoundingBox().minZ, blockBox.minZ);
		int x2 = ModMathHelper.min(this.getBoundingBox().maxX, blockBox.maxX);
		int z2 = ModMathHelper.min(this.getBoundingBox().maxZ, blockBox.maxZ);
		int y1 = this.getBoundingBox().minY;
		int y2 = this.getBoundingBox().maxY;
		
		double hr = radius * 0.75;
		double nr = radius * 0.25;
		BlockPos.Mutable pos = new BlockPos.Mutable();
		for (int x = x1; x <= x2; x++) {
			int xsq = x - center.getX();
			xsq *= xsq;
			pos.setX(x);
			for (int z = z1; z <= z2; z++) {
				int zsq = z - center.getZ();
				zsq *= zsq;
				pos.setZ(z);
				for (int y = y1; y <= y2; y++) {
					int ysq = y - center.getY();
					ysq *= 1.6;
					ysq *= ysq;
					pos.setY(y);
					double r = noise.eval(x * 0.1, y * 0.1, z * 0.1) * nr + hr;
					double r2 = r - 4.5;
					double dist = xsq + ysq + zsq;
					if (dist < r2 * r2) {
						if (world.getBlockState(pos).isIn(ModTags.END_GROUND)) {
							BlockHelper.setWithoutUpdate(world, pos, CAVE_AIR);
						}
					}
					else if (dist < r * r) {
						if (world.getBlockState(pos).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, pos, Blocks.END_STONE);
						}
					}
				}
			}
		}
		
		return true;
	}

	
	@Override
	protected void readAdditional(CompoundNBT tag) {
		center = NBTUtil.readBlockPos(tag.getCompound("center"));
		radius = tag.getFloat("radius");
	}


	private void makeBoundingBox() {
		int minX = ModMathHelper.floor(center.getX() - radius);
		int minY = ModMathHelper.floor(center.getY() - radius);
		int minZ = ModMathHelper.floor(center.getZ() - radius);
		int maxX = ModMathHelper.floor(center.getX() + radius + 1);
		int maxY = ModMathHelper.floor(center.getY() + radius + 1);
		int maxZ = ModMathHelper.floor(center.getZ() + radius + 1);
		this.boundingBox = new MutableBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	protected void fromNbt(CompoundNBT tag) {
		tag.put("center", NBTUtil.writeBlockPos(center));
		tag.putFloat("radius", radius);
		noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, center.getX(), center.getZ()));


	}


	/**
	 * (abstract) Helper method to read subclass data from NBT
	 *
	 * @param tagCompound
	 */


}
