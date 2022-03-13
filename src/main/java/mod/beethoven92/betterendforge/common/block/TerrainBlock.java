package mod.beethoven92.betterendforge.common.block;

import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock.Properties;

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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		if (pathBlock != null && player.getMainHandItem().getItem() instanceof ShovelItem) 
		{
			worldIn.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isClientSide()) 
			{
				worldIn.setBlockAndUpdate(pos, pathBlock.defaultBlockState());
				if (player != null && !player.isCreative()) 
				{
					player.getMainHandItem().hurt(1, worldIn.random, (ServerPlayerEntity) player);
				}
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		if (random.nextInt(16) == 0 && !canSurvive(state, worldIn, pos)) 
		{
			worldIn.setBlockAndUpdate(pos, Blocks.END_STONE.defaultBlockState());
		}
	}
	
	public static boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) 
	{
	      BlockPos blockPos = pos.above();
	      BlockState blockState = world.getBlockState(blockPos);
	      if (blockState.is(Blocks.SNOW) && (Integer)blockState.getValue(SnowBlock.LAYERS) == 1) 
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
