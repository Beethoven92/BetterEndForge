package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.PentaShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class LanceleafSeedBlock extends PlantBlockWithAge {
	public LanceleafSeedBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void growAdult(WorldGenLevel world, Random random, BlockPos pos) {
		int height = ModMathHelper.randRange(4, 6, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height + 1) {
			return;
		}
		int rotation = random.nextInt(4);
		MutableBlockPos mut = new MutableBlockPos().set(pos);
		BlockState plant = ModBlocks.LANCELEAF.get().defaultBlockState().setValue(BlockProperties.ROTATION, rotation);
		BlockHelper.setWithoutUpdate(world, mut, plant.setValue(BlockProperties.PENTA_SHAPE, PentaShape.BOTTOM));
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.setValue(BlockProperties.PENTA_SHAPE, PentaShape.PRE_BOTTOM));
		for (int i = 2; i < height - 2; i++) {
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.setValue(BlockProperties.PENTA_SHAPE, PentaShape.MIDDLE));
		}
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.setValue(BlockProperties.PENTA_SHAPE, PentaShape.PRE_TOP));
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.setValue(BlockProperties.PENTA_SHAPE, PentaShape.TOP));
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.AMBER_MOSS.get());
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
