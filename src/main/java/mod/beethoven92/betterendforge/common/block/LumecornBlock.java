package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

public class LumecornBlock extends Block {
	public static final EnumProperty<LumecornShape> SHAPE = EnumProperty.create("shape", LumecornShape.class);
	private static final VoxelShape SHAPE_BOTTOM = Block.box(6, 0, 6, 10, 16, 10);
	private static final VoxelShape SHAPE_TOP = Block.box(6, 0, 6, 10, 8, 10);
	
	public LumecornBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) { 
		return state.getValue(SHAPE) == LumecornShape.LIGHT_TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) { 
		LumecornShape shape = state.getValue(SHAPE);
		if (shape == LumecornShape.BOTTOM_BIG || shape == LumecornShape.BOTTOM_SMALL) {
			return world.getBlockState(pos.below()).is(ModTags.END_GROUND);
		}
		else if (shape == LumecornShape.LIGHT_TOP) {
			return world.getBlockState(pos.below()).is(this);
		}
		else {
			return world.getBlockState(pos.below()).is(this) && world.getBlockState(pos.above()).is(this);
		}
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!canSurvive(stateIn, worldIn, currentPos)) {
			return Blocks.AIR.defaultBlockState();
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
