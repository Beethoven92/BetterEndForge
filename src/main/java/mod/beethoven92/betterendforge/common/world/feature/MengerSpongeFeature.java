package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class MengerSpongeFeature extends UnderwaterPlantScatter
{
	private static final Function<BlockState, Boolean> REPLACE;
	
	static 
	{
		REPLACE = (state) -> {
			if (state.isIn(ModBlocks.END_LOTUS_STEM.get())) 
			{
				return false;
			}
			/*if (state.isIn(ModBlocks.END_LILY.get()))
			{
				return false;
			}*/
			return !state.getFluidState().isEmpty() || state.getMaterial().isReplaceable();
		};
	}
	
	public MengerSpongeFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public void generate(ISeedReader world, Random random, BlockPos blockPos) 
	{
		BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.MENGER_SPONGE_WET.get());
		if (random.nextBoolean()) 
		{
			for (Direction dir: BlockHelper.DIRECTIONS) 
			{
				BlockPos pos = blockPos.offset(dir);
				if (REPLACE.apply(world.getBlockState(pos))) 
				{
					BlockHelper.setWithoutUpdate(world, pos, ModBlocks.MENGER_SPONGE_WET.get());
				}
			}
		}
	}
}
