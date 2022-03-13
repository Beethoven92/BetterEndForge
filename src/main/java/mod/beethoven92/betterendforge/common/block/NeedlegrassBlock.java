package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class NeedlegrassBlock extends PlantBlock
{
	public NeedlegrassBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) 
	{
		if (entityIn instanceof LivingEntity)
		{
			entityIn.hurt(DamageSource.CACTUS, 0.1F);
		}
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.is(ModBlocks.SHADOW_GRASS.get());
	}
	
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) 
	{
		return false;
	}
}
