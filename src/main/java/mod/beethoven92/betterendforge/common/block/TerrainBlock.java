package mod.beethoven92.betterendforge.common.block;

import java.util.Map;
import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class TerrainBlock extends Block
{
	private Block pathBlock;
	
	public TerrainBlock(Properties properties) 
	{
		super(properties);
	}

	public void setPathBlock(Block block)
	{
		pathBlock = block;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) 
	{
		if (pathBlock != null && player.getMainHandItem().getItem() instanceof ShovelItem) 
		{
			worldIn.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isClientSide()) 
			{
				worldIn.setBlockAndUpdate(pos, pathBlock.defaultBlockState());
				if (player != null && !player.isCreative()) 
				{
					player.getMainHandItem().hurt(1, worldIn.random, (ServerPlayer) player);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) 
	{
		if (random.nextInt(16) == 0 && !canSurvive(state, worldIn, pos)) 
		{
			worldIn.setBlockAndUpdate(pos, Blocks.END_STONE.defaultBlockState());
		}
	}
	
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos)
	{
	      BlockPos blockPos = pos.above();
	      BlockState blockState = world.getBlockState(blockPos);
	      if (blockState.is(Blocks.SNOW) && (Integer)blockState.getValue(SnowLayerBlock.LAYERS) == 1) 
	      {
	         return true;
	      } 
	      else if (blockState.getFluidState().getAmount() == 8) 
	      {
	         return false;
	      } 
	      else 
	      {
	         //int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
	         //return i < 5;
	    	  return true;
	      }
	   }

}
