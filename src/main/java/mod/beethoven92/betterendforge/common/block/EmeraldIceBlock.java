package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EmeraldIceBlock extends HalfTransparentBlock
{
	public EmeraldIceBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, BlockEntity te,
			ItemStack stack) 
	{
		super.playerDestroy(worldIn, player, pos, state, te, stack);
	    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) 
	    {
	    	if (worldIn.dimensionType().ultraWarm()) 
	    	{
	    		worldIn.removeBlock(pos, false);
	            return;
	        }

	    	Material material = worldIn.getBlockState(pos.below()).getMaterial();
	        if (material.blocksMotion() || material.isLiquid())
	        {
	        	worldIn.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
	        }
	    }
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) 
	{
		if (worldIn.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(worldIn, pos)) 
		{
			this.melt(state, worldIn, pos);
		}
	}
	
	protected void melt(BlockState state, Level world, BlockPos pos)
	{
		if (world.dimensionType().ultraWarm()) 
		{
			world.removeBlock(pos, false);
		}
		else 
		{
			world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
			world.neighborChanged(pos, Blocks.WATER, pos);
		}
	}
}
