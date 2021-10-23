package mod.beethoven92.betterendforge.common.world.structure.piece;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public abstract class BasePiece extends StructurePiece {
	protected BasePiece(IStructurePieceType type, int i) {
		super(type, i);
	}

	protected BasePiece(IStructurePieceType type, CompoundNBT tag) {
		super(type, tag);
	}

	
	protected abstract void fromNbt(CompoundNBT tag);
}