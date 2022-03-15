package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class NBTPiece extends StructurePiece
{
	private ResourceLocation structureID;
	private Rotation rotation;
	private Mirror mirror;
	private StructureTemplate structure;
	private BlockPos pos;
	private int erosion;
	private boolean cover;
	
	public NBTPiece(ResourceLocation structureID, StructureTemplate structure, BlockPos pos, int erosion, boolean cover, Random random)
	{
		super(ModStructurePieces.NBT_PIECE, 0);
		this.structureID = structureID;
		this.structure = structure;
		this.rotation = Rotation.getRandom(random);
		this.mirror = Mirror.values()[random.nextInt(3)];
		this.pos = StructureHelper.offsetPos(pos, structure, rotation, mirror);
		this.erosion = erosion;
		this.cover = cover;
		makeBoundingBox();
	}
	
	public NBTPiece(StructureManager p_i50677_1_, CompoundTag nbt) 
	{
		super(ModStructurePieces.NBT_PIECE, nbt);
		
		structureID = new ResourceLocation(nbt.getString("Template"));
		rotation = Rotation.values()[nbt.getInt("rotation")];
		mirror = Mirror.values()[nbt.getInt("mirror")];
		erosion = nbt.getInt("erosion");
		pos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
		cover = nbt.getBoolean("cover");
		structure = StructureHelper.readStructure(structureID);
		makeBoundingBox();
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tagCompound) 
	{
		tagCompound.putString("Template", structureID.toString());
		tagCompound.putInt("rotation", rotation.ordinal());
		tagCompound.putInt("mirror", mirror.ordinal());
		tagCompound.putInt("erosion", erosion);
		tagCompound.put("pos", NbtUtils.writeBlockPos(pos));
		tagCompound.putBoolean("cover", cover);
	}

	private void makeBoundingBox() 
	{
		this.boundingBox = StructureHelper.getStructureBounds(pos, structure, rotation, mirror);
	}
	
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGenerator,
			Random random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) 
	{
	    BoundingBox bounds = new BoundingBox(box);
		bounds.y1 = this.boundingBox.y1;
		bounds.y0 = this.boundingBox.y0;
		StructurePlaceSettings placementData = new StructurePlaceSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
		structure.placeInWorld(world, pos, placementData, random);
		if (erosion > 0) 
		{
			bounds.x1 = ModMathHelper.min(bounds.x1, boundingBox.x1);
			bounds.x0 = ModMathHelper.max(bounds.x0, boundingBox.x0);
			bounds.z1 = ModMathHelper.min(bounds.z1, boundingBox.z1);
			bounds.z0 = ModMathHelper.max(bounds.z0, boundingBox.z0);
			StructureHelper.erode(world, bounds, erosion, random);
		}
		if (cover) 
		{
			StructureHelper.cover(world, bounds, random);
		}
		return true;
	}
}
