package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.StalactiteBlock;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StalactiteFeature extends Feature<NoneFeatureConfiguration>
{
	private final boolean ceiling;
	private final Block[] ground;
	private final Block block;
	
	public StalactiteFeature(boolean ceiling, Block block, Block... ground) 
	{
		super(NoneFeatureConfiguration.CODEC);
		this.ceiling = ceiling;
		this.ground = ground;
		this.block = block;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoneFeatureConfiguration config) 
	{
		if (!isGround(world.getBlockState(ceiling ? pos.above() : pos.below()).getBlock()))
		{
			return false;
		}
		
		MutableBlockPos mut = new MutableBlockPos().set(pos);
		int height = rand.nextInt(16);
		int dir = ceiling ? -1 : 1;
		boolean stalagnate = false;
		
		for (int i = 1; i <= height; i++)
		{
			mut.setY(pos.getY() + i * dir);
			BlockState state = world.getBlockState(mut);
			if (!state.getMaterial().isReplaceable())
			{
				stalagnate = state.is(ModTags.GEN_TERRAIN);
				height = i;
				break;
			}
		}
		
		if (!stalagnate && height > 7) 
		{
			height = rand.nextInt(8);
		}
		
		float center = height * 0.5F;
		for (int i = 0; i < height; i++) 
		{
			mut.setY(pos.getY() + i * dir);
			int size = stalagnate ? Mth.clamp((int) (Mth.abs(i - center) + 1), 1, 7) : height - i - 1;
			boolean waterlogged = !world.getFluidState(mut).isEmpty();
			BlockState base = block.defaultBlockState().setValue(StalactiteBlock.SIZE, size).setValue(BlockStateProperties.WATERLOGGED, waterlogged);
			BlockState state = stalagnate ? base.setValue(StalactiteBlock.IS_FLOOR, dir > 0 ? i < center : i > center) : base.setValue(StalactiteBlock.IS_FLOOR, dir > 0);
			BlockHelper.setWithoutUpdate(world, mut, state);
		}
		
		return true;
	}
	
	private boolean isGround(Block block) 
	{
		for (Block b : ground) 
		{
			if (b == block) 
			{
				return true;
			}
		}
		return false;
	}
}
