package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class AncientEmeraldIceBlock extends Block
{
	public AncientEmeraldIceBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		Direction dir = BlockHelper.randomDirection(random);
		
		if (random.nextBoolean()) 
		{
			int x = ModMathHelper.randRange(-2, 2, random);
			int y = ModMathHelper.randRange(-2, 2, random);
			int z = ModMathHelper.randRange(-2, 2, random);
			BlockPos p = pos.add(x, y, z);
			if (worldIn.getBlockState(p).isIn(Blocks.WATER)) 
			{
				worldIn.setBlockState(p, ModBlocks.EMERALD_ICE.get().getDefaultState());
				makeParticles(worldIn, p, random);
			}
		}
		
		pos = pos.offset(dir);
		state = worldIn.getBlockState(pos);
		if (state.isIn(Blocks.WATER)) 
		{
			worldIn.setBlockState(pos, ModBlocks.EMERALD_ICE.get().getDefaultState());
			makeParticles(worldIn, pos, random);
		}
		else if (state.isIn(ModBlocks.EMERALD_ICE.get())) 
		{
			worldIn.setBlockState(pos, ModBlocks.DENSE_EMERALD_ICE.get().getDefaultState());
			makeParticles(worldIn, pos, random);
		}
	}
	
	private void makeParticles(ServerWorld world, BlockPos pos, Random random) 
	{
		world.spawnParticle(ModParticleTypes.SNOWFLAKE_PARTICLE.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0);
	}
}
