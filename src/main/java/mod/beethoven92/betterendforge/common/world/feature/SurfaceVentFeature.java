package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.HydrothermalVentBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SurfaceVentFeature extends Feature<NoneFeatureConfiguration>
{
	public SurfaceVentFeature() 
	{
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config) 
	{
		pos = FeatureHelper.getPosOnSurface(world, new BlockPos(pos.getX() + rand.nextInt(16), pos.getY(), pos.getZ() + rand.nextInt(16)));

		if (!world.getBlockState(pos.below(3)).is(ModTags.GEN_TERRAIN))
		{
			return false;
		}
		
		MutableBlockPos mut = new MutableBlockPos();
		int count = ModMathHelper.randRange(15, 30, rand);
		BlockState vent = ModBlocks.HYDROTHERMAL_VENT.get().defaultBlockState().setValue(HydrothermalVentBlock.WATERLOGGED, false);
		for (int i = 0; i < count; i++) 
		{
			mut.set(pos).move(ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5), 5, ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5));
			int dist = ModMathHelper.floor(2 - ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ())) + rand.nextInt(2);
			if (dist > 0) 
			{
				BlockState state = world.getBlockState(mut);
				for (int n = 0; n < 10 && state.isAir(); n++) 
				{
					mut.setY(mut.getY() - 1);
					state = world.getBlockState(mut);
				}
				if (state.is(ModTags.GEN_TERRAIN) && !world.getBlockState(mut.above()).is(ModBlocks.HYDROTHERMAL_VENT.get())) 
				{
					for (int j = 0; j <= dist; j++) 
					{
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.SULPHURIC_ROCK.stone.get());
						mut.setY(mut.getY() + 1);
					}
					BlockHelper.setWithoutUpdate(world, mut, vent);
				}
			}
		}
		
		return true;
	}

}
