package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class NBTPiece extends StructurePiece
{
	private ResourceLocation structureID;
	private Rotation rotation;
	private Mirror mirror;
	private Template structure;
	private BlockPos pos;
	private int erosion;
	private boolean cover;
	
	public NBTPiece(ResourceLocation structureID, Template structure, BlockPos pos, int erosion, boolean cover, Random random)
	{
		super(ModStructurePieces.NBT_PIECE, 0);
		this.structureID = structureID;
		this.structure = structure;
		this.rotation = Rotation.randomRotation(random);
		this.mirror = Mirror.values()[random.nextInt(3)];
		this.pos = StructureHelper.offsetPos(pos, structure, rotation, mirror);
		this.erosion = erosion;
		this.cover = cover;
		makeBoundingBox();
	}
	
	public NBTPiece(TemplateManager p_i50677_1_, CompoundNBT nbt) 
	{
		super(ModStructurePieces.NBT_PIECE, nbt);
		
		structureID = new ResourceLocation(nbt.getString("Template"));
		rotation = Rotation.values()[nbt.getInt("rotation")];
		mirror = Mirror.values()[nbt.getInt("mirror")];
		erosion = nbt.getInt("erosion");
		pos = NBTUtil.readBlockPos(nbt.getCompound("pos"));
		cover = nbt.getBoolean("cover");
		structure = StructureHelper.readStructure(structureID);
		makeBoundingBox();
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) 
	{
		tagCompound.putString("Template", structureID.toString());
		tagCompound.putInt("rotation", rotation.ordinal());
		tagCompound.putInt("mirror", mirror.ordinal());
		tagCompound.putInt("erosion", erosion);
		tagCompound.put("pos", NBTUtil.writeBlockPos(pos));
		tagCompound.putBoolean("cover", cover);
	}

	private void makeBoundingBox() 
	{
		this.boundingBox = StructureHelper.getStructureBounds(pos, structure, rotation, mirror);
	}
	
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator chunkGenerator,
			Random random, MutableBoundingBox box, ChunkPos chunkPos, BlockPos blockPos) 
	{
	    MutableBoundingBox bounds = new MutableBoundingBox(box);
		bounds.maxY = this.boundingBox.maxY;
		bounds.minY = this.boundingBox.minY;
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
		structure.func_237152_b_(world, pos, placementData, random);
		if (erosion > 0) 
		{
			bounds.maxX = ModMathHelper.min(bounds.maxX, boundingBox.maxX);
			bounds.minX = ModMathHelper.max(bounds.minX, boundingBox.minX);
			bounds.maxZ = ModMathHelper.min(bounds.maxZ, boundingBox.maxZ);
			bounds.minZ = ModMathHelper.max(bounds.minZ, boundingBox.minZ);
			StructureHelper.erode(world, bounds, erosion, random);
		}
		if (cover) 
		{
			StructureHelper.cover(world, bounds, random);
		}
		return true;
	}
}
