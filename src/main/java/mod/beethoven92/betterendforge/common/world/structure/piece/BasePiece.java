package mod.beethoven92.betterendforge.common.world.structure.piece;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

public abstract class BasePiece extends StructurePiece {
	protected BasePiece(StructurePieceType type, int i) {
		super(type, i);
	}

	protected BasePiece(StructurePieceType type, CompoundTag tag) {
		super(type, tag);
	}

	
	protected abstract void fromNbt(CompoundTag tag);
}