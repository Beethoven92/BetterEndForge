package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.structure.piece.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieces 
{
    public static final IStructurePieceType CAVE_PIECE = CavePiece::new;
	public static final IStructurePieceType VOXEL_PIECE = VoxelPiece::new;
    public static final IStructurePieceType MOUNTAIN_PIECE = MountainPiece::new;
    public static final IStructurePieceType LAKE_PIECE = LakePiece::new;
    public static final IStructurePieceType PAINTED_MOUNTAIN_PIECE = PaintedMountainPiece::new;
    public static final IStructurePieceType NBT_PIECE = NBTPiece::new;

    public static void registerAllPieces() 
    {
        registerStructurePiece(CAVE_PIECE, new ResourceLocation(BetterEnd.MOD_ID, "cave_piece"));
    	registerStructurePiece(VOXEL_PIECE, new ResourceLocation(BetterEnd.MOD_ID, "voxel_piece"));
        registerStructurePiece(MOUNTAIN_PIECE, new ResourceLocation(BetterEnd.MOD_ID, "mountain_piece"));
        registerStructurePiece(LAKE_PIECE, new ResourceLocation(BetterEnd.MOD_ID, "lake_piece"));
        registerStructurePiece(PAINTED_MOUNTAIN_PIECE, new ResourceLocation(BetterEnd.MOD_ID, "painted_mountain_piece"));
        registerStructurePiece(NBT_PIECE, new ResourceLocation(BetterEnd.MOD_ID, "nbt_piece"));
    }

    static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) 
    {
        Registry.register(Registry.STRUCTURE_PIECE, rl, structurePiece);
    }
}
