package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.StalactiteBlock;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class StalactiteFeature extends Feature<NoFeatureConfig>
{
	private final boolean ceiling;
	private final Block[] ground;
	private final Block block;
	
	public StalactiteFeature(boolean ceiling, Block block, Block... ground) 
	{
		super(NoFeatureConfig.field_236558_a_);
		this.ceiling = ceiling;
		this.ground = ground;
		this.block = block;
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		if (!isGround(world.getBlockState(ceiling ? pos.up() : pos.down()).getBlock()))
		{
			return false;
		}
		
		Mutable mut = new Mutable().setPos(pos);
		int height = rand.nextInt(16);
		int dir = ceiling ? -1 : 1;
		boolean stalagnate = false;
		
		for (int i = 1; i <= height; i++)
		{
			mut.setY(pos.getY() + i * dir);
			BlockState state = world.getBlockState(mut);
			if (!state.getMaterial().isReplaceable())
			{
				stalagnate = state.isIn(ModTags.GEN_TERRAIN);
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
			int size = stalagnate ? MathHelper.clamp((int) (MathHelper.abs(i - center) + 1), 1, 7) : height - i - 1;
			boolean waterlogged = !world.getFluidState(mut).isEmpty();
			BlockState base = block.getDefaultState().with(StalactiteBlock.SIZE, size).with(BlockStateProperties.WATERLOGGED, waterlogged);
			BlockState state = stalagnate ? base.with(StalactiteBlock.IS_FLOOR, dir > 0 ? i < center : i > center) : base.with(StalactiteBlock.IS_FLOOR, dir > 0);
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
