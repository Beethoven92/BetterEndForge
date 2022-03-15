package mod.beethoven92.betterendforge.common.block;

import java.util.Queue;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MengerSpongeBlock extends SpongeBlock
{
	public MengerSpongeBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected void tryAbsorbWater(Level worldIn, BlockPos pos) 
	{
		if (this.absorb(worldIn, pos)) 
		{
			worldIn.setBlock(pos, ModBlocks.MENGER_SPONGE_WET.get().defaultBlockState(), 2);
	        worldIn.levelEvent(2001, pos, Block.getId(Blocks.WATER.defaultBlockState()));
	    }
	}
	
	private boolean absorb(Level worldIn, BlockPos pos) 
	{
		Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
		queue.add(new Tuple<>(pos, 0));
		int i = 0;

		while(!queue.isEmpty()) 
		{
			Tuple<BlockPos, Integer> tuple = queue.poll();
		    BlockPos blockpos = tuple.getA();
		    int j = tuple.getB();

		    for(Direction direction : Direction.values()) 
		    {
		    	BlockPos blockpos1 = blockpos.relative(direction);
		        BlockState blockstate = worldIn.getBlockState(blockpos1);
		        FluidState fluidstate = worldIn.getFluidState(blockpos1);
		        Material material = blockstate.getMaterial();
		        if (fluidstate.is(FluidTags.WATER)) 
		        {
		        	if (blockstate.getBlock() instanceof BucketPickup && ((BucketPickup)blockstate.getBlock()).takeLiquid(worldIn, blockpos1, blockstate) != Fluids.EMPTY) 
		        	{
		        		++i;
		                if (j < 6) 
		                {
		                	queue.add(new Tuple<>(blockpos1, j + 1));
		                }
		            } 
		        	else if (blockstate.getBlock() instanceof LiquidBlock) 
		        	{
		        		worldIn.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
		                ++i;
		                if (j < 6) 
		                {
		                	queue.add(new Tuple<>(blockpos1, j + 1));
		                }
		            } 
		        	else if (material == Material.WATER_PLANT || material == Material.REPLACEABLE_WATER_PLANT) 
		        	{
		        		BlockEntity tileentity = blockstate.hasTileEntity() ? worldIn.getBlockEntity(blockpos1) : null;
		                dropResources(blockstate, worldIn, blockpos1, tileentity);
		                worldIn.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
		                ++i;
		                if (j < 6) 
		                {
		                	queue.add(new Tuple<>(blockpos1, j + 1));
		                }
		            }
		        }
		    }

		    if (i > 64) 
		    {
		    	break;
		    }
		}

		return i > 0;
	}
}
