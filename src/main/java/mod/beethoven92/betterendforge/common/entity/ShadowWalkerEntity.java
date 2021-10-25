package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ShadowWalkerEntity extends MonsterEntity
{
	public ShadowWalkerEntity(EntityType<? extends MonsterEntity> type, World worldIn) 
	{
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(2, new AttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}
	
	public static AttributeModifierMap.MutableAttribute registerAttributes() 
	{
		return MonsterEntity.func_234295_eP_().
				//createMutableAttribute(Attributes.MAX_HEALTH, 8.0).
				createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0).
				createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15).
				createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.5).
				createMutableAttribute(Attributes.ARMOR, 2.0).
				createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		world.addParticle(ParticleTypes.ASH,
				getPosX() + rand.nextGaussian() * 0.2,
				getPosY() + rand.nextGaussian() * 0.5 + 1,
				getPosZ() + rand.nextGaussian() * 0.2,
				0, 0, 0);
		world.addParticle(ParticleTypes.SMOKE,
				getPosX() + rand.nextGaussian() * 0.2,
				getPosY() + rand.nextGaussian() * 0.5 + 1,
				getPosZ() + rand.nextGaussian() * 0.2,
				0, 0, 0);
		world.addParticle(ParticleTypes.ENTITY_EFFECT,
				getPosX() + rand.nextGaussian() * 0.2,
				getPosY() + rand.nextGaussian() * 0.5 + 1,
				getPosZ() + rand.nextGaussian() * 0.2,
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
		return ModMathHelper.randRange(0.25F, 0.5F, rand);
	}
	
	@Override
	protected float getSoundPitch() 
	{
		return ModMathHelper.randRange(0.75F, 1.25F, rand);
	}
	
	public static boolean canSpawn(EntityType<ShadowWalkerEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) 
	{
		if (MonsterEntity.canMonsterSpawnInLight(type, world, spawnReason, pos, random)) 
		{
			AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
			List<ShadowWalkerEntity> entities = world.getEntitiesWithinAABB(ShadowWalkerEntity.class, box, (entity) -> { return true; });
			return entities.size() < 6;
		}
		return false;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) 
	{
		boolean attack = super.attackEntityAsMob(entityIn);
		if (attack && entityIn instanceof LivingEntity) 
		{
			LivingEntity living = (LivingEntity) entityIn;
			if (!(living.isPotionActive(Effects.BLINDNESS))) 
			{
				living.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60));
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
		public void startExecuting()
		{
			super.startExecuting();
			this.ticks = 0;
		}
		
		@Override
		public void resetTask() 
		{
			super.resetTask();
			this.walker.setAggroed(false);
		}

		@Override
		public void tick() 
		{
			super.tick();
			++this.ticks;
			if (this.ticks >= 5 && this.func_234041_j_() < this.func_234042_k_() / 2) 
			{
				this.walker.setAggroed(true);
			}
			else 
			{
				this.walker.setAggroed(false);
			}
		}
	}
}
