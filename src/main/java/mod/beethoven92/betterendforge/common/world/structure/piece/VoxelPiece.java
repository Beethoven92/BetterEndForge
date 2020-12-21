package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Random;
import java.util.function.Consumer;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.world.structure.StructureWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

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
	
	public VoxelPiece(TemplateManager p_i50677_1_, CompoundNBT nbt) 
	{
		super(ModStructurePieces.VOXEL_PIECE, nbt);
		world = new StructureWorld(nbt.getCompound("world"));
		this.boundingBox = world.getBounds();
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) 
	{
		tagCompound.put("world", world.toNBT());
	}

	@Override
	public boolean func_230383_a_(ISeedReader worldIn, StructureManager p_230383_2_, ChunkGenerator p_230383_3_,
			Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos chunkPos, BlockPos p_230383_7_) 
	{
		this.world.placeChunk(worldIn, chunkPos);
		return true;
	}

}
