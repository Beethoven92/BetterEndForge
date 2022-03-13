package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WetSpongeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class MengerSpongeWetBlock extends WetSpongeBlock
{
	public MengerSpongeWetBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) 
	{
		if (worldIn.dimensionType().ultraWarm()) 
		{
			worldIn.setBlock(pos, ModBlocks.MENGER_SPONGE.get().defaultBlockState(), 3);
	        worldIn.levelEvent(2009, pos, 0);
	        worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + worldIn.getRandom().nextFloat() * 0.2F) * 0.7F);
	    }
	}

	/*@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) 
	{
		worldIn.destroyBlock(pos, !player.isCreative());
		BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR);
	}*/
	
	@Override
	public void destroy(IWorld worldIn, BlockPos pos, BlockState state) 
	{
		BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getSource(false);
	}
}
