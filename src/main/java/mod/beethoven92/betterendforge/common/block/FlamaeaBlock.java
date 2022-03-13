package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class FlamaeaBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

	public FlamaeaBlock() {
		super(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).instabreak());
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(Blocks.WATER);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { 
		return SHAPE;
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}
}
