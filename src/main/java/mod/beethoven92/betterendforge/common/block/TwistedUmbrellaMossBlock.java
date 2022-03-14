package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TwistedUmbrellaMossBlock extends PlantBlock
{
	public TwistedUmbrellaMossBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) 
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
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return worldIn.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}
	
	@Override
	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) 
	{
    	int rot = worldIn.random.nextInt(4);
		BlockState bs = ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get().defaultBlockState().setValue(DoublePlantBlock.ROTATION, rot);
		BlockHelper.setWithoutUpdate(worldIn, pos, bs);
		BlockHelper.setWithoutUpdate(worldIn, pos.above(), bs.setValue(DoublePlantBlock.TOP, true));
	}
}
