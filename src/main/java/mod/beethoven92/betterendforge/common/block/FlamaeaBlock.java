package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class FlamaeaBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

	public FlamaeaBlock() {
		super(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).instabreak());
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(Blocks.WATER);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) { 
		return SHAPE;
	}

	@Override
	public BlockBehaviour.OffsetType getOffsetType() {
		return BlockBehaviour.OffsetType.NONE;
	}
}
