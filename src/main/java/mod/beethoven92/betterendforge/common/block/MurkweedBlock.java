package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MurkweedBlock extends PlantBlock
{
	public MurkweedBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) 
	{
		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble() * 0.5 + 0.5;
		double z = pos.getZ() + rand.nextDouble();
		double v = rand.nextDouble() * 0.1;
		worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, x, y, z, v, v, v);
	}
	
	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) 
	{
		if (entityIn instanceof LivingEntity && !((LivingEntity) entityIn).hasEffect(MobEffects.BLINDNESS)) 
		{
			((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50));
		}
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.is(ModBlocks.SHADOW_GRASS.get());
	}
	
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) 
	{
		return false;
	}
}
