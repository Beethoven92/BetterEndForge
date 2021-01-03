package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.PentaShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

public class LanceleafSeedBlock extends PlantBlockWithAge {
	public LanceleafSeedBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void growAdult(ISeedReader world, Random random, BlockPos pos) {
		int height = ModMathHelper.randRange(4, 6, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height + 1) {
			return;
		}
		int rotation = random.nextInt(4);
		Mutable mut = new Mutable().setPos(pos);
		BlockState plant = ModBlocks.LANCELEAF.get().getDefaultState().with(BlockProperties.ROTATION, rotation);
		BlockHelper.setWithoutUpdate(world, mut, plant.with(BlockProperties.PENTA_SHAPE, PentaShape.BOTTOM));
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.with(BlockProperties.PENTA_SHAPE, PentaShape.PRE_BOTTOM));
		for (int i = 2; i < height - 2; i++) {
			BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.with(BlockProperties.PENTA_SHAPE, PentaShape.MIDDLE));
		}
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.with(BlockProperties.PENTA_SHAPE, PentaShape.PRE_TOP));
		BlockHelper.setWithoutUpdate(world, mut.move(Direction.UP), plant.with(BlockProperties.PENTA_SHAPE, PentaShape.TOP));
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.AMBER_MOSS.get());
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
}
