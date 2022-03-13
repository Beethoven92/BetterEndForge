package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.OffsetType;

public class FlammalixBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 14, 14);

	public FlammalixBlock() {
		super(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE));
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.PALLIDIUM_FULL.get()) ||
			state.is(ModBlocks.PALLIDIUM_HEAVY.get()) ||
			state.is(ModBlocks.PALLIDIUM_THIN.get()) ||
			state.is(ModBlocks.PALLIDIUM_TINY.get());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return SHAPE;
	}
	
	@Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}

	

}
