package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SmaragdantCrystalFeature extends Feature<NoFeatureConfig>
{
	public SmaragdantCrystalFeature() 
	{
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		if (!world.getBlockState(pos.below()).is(ModTags.GEN_TERRAIN)) 
		{
			return false;
		}
		
		Mutable mut = new Mutable();
		int count = ModMathHelper.randRange(15, 30, rand);
		BlockState crystal = ModBlocks.SMARAGDANT_CRYSTAL.get().defaultBlockState();
		BlockState shard = ModBlocks.SMARAGDANT_CRYSTAL_SHARD.get().defaultBlockState();
		for (int i = 0; i < count; i++) 
		{
			mut.set(pos).move(ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5), 5, ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5));
			int dist = ModMathHelper.floor(1.5F - ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ())) + rand.nextInt(3);
			if (dist > 0) 
			{
				BlockState state = world.getBlockState(mut);
				for (int n = 0; n < 10 && state.isAir(); n++) 
				{
					mut.setY(mut.getY() - 1);
					state = world.getBlockState(mut);
				}
				if (state.is(ModTags.GEN_TERRAIN) && !world.getBlockState(mut.above()).is(crystal.getBlock())) 
				{
					for (int j = 0; j <= dist; j++) 
					{
						BlockHelper.setWithoutUpdate(world, mut, crystal);
						mut.setY(mut.getY() + 1);
					}
					boolean waterlogged = !world.getFluidState(mut).isEmpty();
					BlockHelper.setWithoutUpdate(world, mut, shard.setValue(BlockStateProperties.WATERLOGGED, waterlogged));
				}
			}
		}
		
		return true;
	}

}
