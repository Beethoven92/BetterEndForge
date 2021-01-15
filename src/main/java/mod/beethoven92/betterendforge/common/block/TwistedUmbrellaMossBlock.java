package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TwistedUmbrellaMossBlock extends PlantBlock
{
	public TwistedUmbrellaMossBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) 
	{
		return 1F;
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.getBlock() == ModBlocks.END_MOSS.get() || state.getBlock() == ModBlocks.END_MYCELIUM.get() ||
				state.getBlock() == ModBlocks.JUNGLE_MOSS.get();
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return worldIn.getBlockState(pos.up()).isAir();
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
	
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
    	int rot = worldIn.rand.nextInt(4);
		BlockState bs = ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get().getDefaultState().with(DoublePlantBlock.ROTATION, rot);
		BlockHelper.setWithoutUpdate(worldIn, pos, bs);
		BlockHelper.setWithoutUpdate(worldIn, pos.up(), bs.with(DoublePlantBlock.TOP, true));
	}
}
