package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Random;
import java.util.function.Consumer;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.world.structure.StructureWorld;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class VoxelPiece extends StructurePiece
{
	private StructureWorld world;
	
	public VoxelPiece(Consumer<StructureWorld> function, int id) 
	{
		super(ModStructurePieces.VOXEL_PIECE, id);
		world = new StructureWorld();
		function.accept(world);
		this.boundingBox = world.getBounds();
	}
	
	public VoxelPiece(StructureManager p_i50677_1_, CompoundTag nbt) 
	{
		super(ModStructurePieces.VOXEL_PIECE, nbt);
		world = new StructureWorld(nbt.getCompound("world"));
		this.boundingBox = world.getBounds();
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tagCompound) 
	{
		tagCompound.put("world", world.toNBT());
	}

	@Override
	public boolean postProcess(WorldGenLevel worldIn, StructureFeatureManager p_230383_2_, ChunkGenerator p_230383_3_,
			Random p_230383_4_, BoundingBox p_230383_5_, ChunkPos chunkPos, BlockPos p_230383_7_) 
	{
		this.world.placeChunk(worldIn, chunkPos);
		return true;
	}

}
