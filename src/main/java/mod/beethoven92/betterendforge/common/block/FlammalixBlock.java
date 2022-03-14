package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;

public class FlammalixBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 14, 14);

	public FlammalixBlock() {
		super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE));
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.PALLIDIUM_FULL.get()) ||
			state.is(ModBlocks.PALLIDIUM_HEAVY.get()) ||
			state.is(ModBlocks.PALLIDIUM_THIN.get()) ||
			state.is(ModBlocks.PALLIDIUM_TINY.get());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}
	
	@Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}

	

}
