package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BoluxMushroomBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 9, 15);

	public BoluxMushroomBlock() {
		super(AbstractBlock.Properties.create(Material.TALL_PLANTS).zeroHardnessAndResistance().doesNotBlockMovement()
				.sound(SoundType.PLANT).setLightLevel(s -> 10));
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.RUTISCUS.get());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}
}
