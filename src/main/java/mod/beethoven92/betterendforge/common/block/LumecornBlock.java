package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class LumecornBlock extends Block {
	public static final EnumProperty<LumecornShape> SHAPE = EnumProperty.create("shape", LumecornShape.class);
	private static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
	private static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(6, 0, 6, 10, 8, 10);
	
	public LumecornBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { 
		return state.get(SHAPE) == LumecornShape.LIGHT_TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) { 
		LumecornShape shape = state.get(SHAPE);
		if (shape == LumecornShape.BOTTOM_BIG || shape == LumecornShape.BOTTOM_SMALL) {
			return world.getBlockState(pos.down()).isIn(ModTags.END_GROUND);
		}
		else if (shape == LumecornShape.LIGHT_TOP) {
			return world.getBlockState(pos.down()).isIn(this);
		}
		else {
			return world.getBlockState(pos.down()).isIn(this) && world.getBlockState(pos.up()).isIn(this);
		}
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) {
			return Blocks.AIR.getDefaultState();
		}
		else {
			return stateIn;
		}
	}
	
//	@Override
//	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
//		LumecornShape shape = state.get(SHAPE);
//		if (shape == LumecornShape.BOTTOM_BIG || shape == LumecornShape.BOTTOM_SMALL || shape == LumecornShape.MIDDLE) {
//			if (MHelper.RANDOM.nextBoolean()) {
//				return Collections.singletonList(new ItemStack(EndBlocks.LUMECORN_SEED));
//			}
//			else {
//				return Collections.emptyList();
//			}
//		}
//		if (MHelper.RANDOM.nextBoolean()) {
//			return Collections.singletonList(new ItemStack(EndItems.LUMECORN_ROD));
//		}
//		else {
//			return Collections.emptyList();
//		}
//	}
}
