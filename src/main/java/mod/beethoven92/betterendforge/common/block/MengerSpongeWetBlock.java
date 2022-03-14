package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MengerSpongeWetBlock extends WetSpongeBlock
{
	public MengerSpongeWetBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) 
	{
		if (worldIn.dimensionType().ultraWarm()) 
		{
			worldIn.setBlock(pos, ModBlocks.MENGER_SPONGE.get().defaultBlockState(), 3);
	        worldIn.levelEvent(2009, pos, 0);
	        worldIn.playSound((Player)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + worldIn.getRandom().nextFloat() * 0.2F) * 0.7F);
	    }
	}

	/*@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) 
	{
		worldIn.destroyBlock(pos, !player.isCreative());
		BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR);
	}*/
	
	@Override
	public void destroy(LevelAccessor worldIn, BlockPos pos, BlockState state) 
	{
		BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return Fluids.WATER.getSource(false);
	}
}
