package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class MurkweedBlock extends PlantBlock
{
	public MurkweedBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble() * 0.5 + 0.5;
		double z = pos.getZ() + rand.nextDouble();
		double v = rand.nextDouble() * 0.1;
		worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, x, y, z, v, v, v);
	}
	
	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) 
	{
		if (entityIn instanceof LivingEntity && !((LivingEntity) entityIn).hasEffect(Effects.BLINDNESS)) 
		{
			((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.BLINDNESS, 50));
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
