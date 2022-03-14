package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

public class ShadowWalkerEntity extends Monster
{
	public ShadowWalkerEntity(EntityType<? extends Monster> type, Level worldIn) 
	{
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(2, new AttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}
	
	public static AttributeSupplier.Builder registerAttributes() 
	{
		return Monster.createMonsterAttributes().
				//createMutableAttribute(Attributes.MAX_HEALTH, 8.0).
				add(Attributes.FOLLOW_RANGE, 35.0).
				add(Attributes.MOVEMENT_SPEED, 0.15).
				add(Attributes.ATTACK_DAMAGE, 4.5).
				add(Attributes.ARMOR, 2.0).
				add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		level.addParticle(ParticleTypes.ASH,
				getX() + random.nextGaussian() * 0.2,
				getY() + random.nextGaussian() * 0.5 + 1,
				getZ() + random.nextGaussian() * 0.2,
				0, 0, 0);
		level.addParticle(ParticleTypes.SMOKE,
				getX() + random.nextGaussian() * 0.2,
				getY() + random.nextGaussian() * 0.5 + 1,
				getZ() + random.nextGaussian() * 0.2,
				0, 0, 0);
		level.addParticle(ParticleTypes.ENTITY_EFFECT,
				getX() + random.nextGaussian() * 0.2,
				getY() + random.nextGaussian() * 0.5 + 1,
				getZ() + random.nextGaussian() * 0.2,
				0, 0, 0);
	}
	
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return ModSoundEvents.ENTITY_SHADOW_WALKER.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) 
	{
		return ModSoundEvents.ENTITY_SHADOW_WALKER_DAMAGE.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return ModSoundEvents.ENTITY_SHADOW_WALKER_DEATH.get();
	}
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn)
	{
	}
	
	@Override
	protected float getSoundVolume() 
	{
		return ModMathHelper.randRange(0.25F, 0.5F, random);
	}
	
	@Override
	protected float getVoicePitch() 
	{
		return ModMathHelper.randRange(0.75F, 1.25F, random);
	}
	
	public static boolean canSpawn(EntityType<ShadowWalkerEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason,
			BlockPos pos, Random random) 
	{
		if (Monster.checkMonsterSpawnRules(type, world, spawnReason, pos, random)) 
		{
			AABB box = new AABB(pos).inflate(16);
			List<ShadowWalkerEntity> entities = world.getEntitiesOfClass(ShadowWalkerEntity.class, box, (entity) -> { return true; });
			return entities.size() < 6;
		}
		return false;
	}
	
	@Override
	public boolean doHurtTarget(Entity entityIn) 
	{
		boolean attack = super.doHurtTarget(entityIn);
		if (attack && entityIn instanceof LivingEntity) 
		{
			LivingEntity living = (LivingEntity) entityIn;
			if (!(living.hasEffect(MobEffects.BLINDNESS))) 
			{
				living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
			}
		}
		return attack;
	}
	
	private final class AttackGoal extends MeleeAttackGoal 
	{
		private final ShadowWalkerEntity walker;
		private int ticks;

		public AttackGoal(ShadowWalkerEntity walker, double speed, boolean pauseWhenMobIdle) 
		{
		      super(walker, speed, pauseWhenMobIdle);
		      this.walker = walker;
		}

		@Override
		public void start()
		{
			super.start();
			this.ticks = 0;
		}
		
		@Override
		public void stop() 
		{
			super.stop();
			this.walker.setAggressive(false);
		}

		@Override
		public void tick() 
		{
			super.tick();
			++this.ticks;
			if (this.ticks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) 
			{
				this.walker.setAggressive(true);
			}
			else 
			{
				this.walker.setAggressive(false);
			}
		}
	}
}
