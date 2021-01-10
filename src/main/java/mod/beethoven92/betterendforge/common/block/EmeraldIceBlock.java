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

public class EmeraldIceBlock extends BreakableBlock
{
	public EmeraldIceBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te,
			ItemStack stack) 
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);
	    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) 
	    {
	    	if (worldIn.getDimensionType().isUltrawarm()) 
	    	{
	    		worldIn.removeBlock(pos, false);
	            return;
	        }

	    	Material material = worldIn.getBlockState(pos.down()).getMaterial();
	        if (material.blocksMovement() || material.isLiquid())
	        {
	        	worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
	        }
	    }
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		if (worldIn.getLightFor(LightType.BLOCK, pos) > 11 - state.getOpacity(worldIn, pos)) 
		{
			this.melt(state, worldIn, pos);
		}
	}
	
	protected void melt(BlockState state, World world, BlockPos pos)
	{
		if (world.getDimensionType().isUltrawarm()) 
		{
			world.removeBlock(pos, false);
		}
		else 
		{
			world.setBlockState(pos, Blocks.WATER.getDefaultState());
			world.neighborChanged(pos, Blocks.WATER, pos);
		}
	}
}
