package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock.Properties;

public class EmeraldIceBlock extends BreakableBlock
{
	public EmeraldIceBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te,
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
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		if (worldIn.getBrightness(LightType.BLOCK, pos) > 11 - state.getLightBlock(worldIn, pos)) 
		{
			this.melt(state, worldIn, pos);
		}
	}
	
	protected void melt(BlockState state, World world, BlockPos pos)
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
