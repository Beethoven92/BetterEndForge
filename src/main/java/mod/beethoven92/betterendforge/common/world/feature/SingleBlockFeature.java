package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SingleBlockFeature extends Feature<NoFeatureConfig>
{
	private final Block block;
	
	public SingleBlockFeature(Block block) 
	{
		super(NoFeatureConfig.CODEC);
		this.block = block;
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		if (!world.getBlockState(pos.below()).is(ModTags.GEN_TERRAIN)) 
		{
			return false;
		}
		
		BlockState state = block.defaultBlockState();
		if (block.getStateDefinition().getProperty("waterlogged") != null) 
		{
			boolean waterlogged = !world.getFluidState(pos).isEmpty();
			state = state.setValue(BlockStateProperties.WATERLOGGED, waterlogged);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
		
		return true;
	}
}
