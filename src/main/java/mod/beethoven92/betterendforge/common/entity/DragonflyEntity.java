package mod.beethoven92.betterendforge.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.server.level.ServerLevel;

public class DragonflyEntity extends Animal implements FlyingAnimal
{
	public DragonflyEntity(EntityType<? extends Animal> type, Level worldIn) 
	{
		super(type, worldIn);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.lookControl = new DragonflyLookControl(this);
	    this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
	    this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
		this.xpReward = 1;
	}
	
	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new WanderAroundGoal());
	}

	public static AttributeSupplier.Builder registerAttributes() 
	{
		return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 8.0).
				add(Attributes.FOLLOW_RANGE, 16.0).
				add(Attributes.MOVEMENT_SPEED, 0.1).
				add(Attributes.FLYING_SPEED, 1.0);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) 
	{
		return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}
	
	@Override
	protected PathNavigation createNavigation(Level worldIn) 
	{
		FlyingPathNavigation flyingNavigator = new FlyingPathNavigation(this, worldIn) 
		{
			@Override
	         public boolean isStableDestination(BlockPos pos) 
	         {
				BlockState state = this.level.getBlockState(pos);
				return state.isAir() || !state.getMaterial().blocksMotion();
	         }

			 @Override
	         public void tick() 
	         {
	               super.tick();
	         }
	    };
	    flyingNavigator.setCanPassDoors(false);
	    flyingNavigator.setCanFloat(false);
	    flyingNavigator.setCanOpenDoors(false);
	    return flyingNavigator;
	}
	
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	@Override
	public boolean isNoGravity() 
	{
		return true;
	}
	
	 @Override
	public boolean causeFallDamage(float distance, float damageMultiplier) 
	{
		return false;
	}
	 
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return ModSoundEvents.ENTITY_DRAGONFLY.get();
	}
	@Override
	protected float getSoundVolume() 
	{
		return ModMathHelper.randRange(0.25F, 0.5F, random);
	}
		
	@Override
	public AgableMob getBreedOffspring(ServerLevel p_241840_1_, AgableMob p_241840_2_) 
	{
		return ModEntityTypes.DRAGONFLY.get().create(level);
	}
	
	public static boolean canSpawn(EntityType<DragonflyEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason,
			BlockPos pos, Random random)
	{
		AABB box = new AABB(pos).inflate(16);
		List<DragonflyEntity> list = world.getEntitiesOfClass(DragonflyEntity.class, box, (entity) -> { return true; });
		int y = world.getChunk(pos).getHeight(Types.WORLD_SURFACE, pos.getX() & 15, pos.getY() & 15);
		
		// FIX dragonfly spawning too much and preventing other entities to spawn
		return y > 0 && pos.getY() >= y && list.size() < 9;
	}
	
	public class DragonflyLookControl extends LookControl 
	{
		DragonflyLookControl(Mob entity) 
		{
			super(entity);
		}
		
		@Override
		protected boolean resetXRotOnTick() 
		{
			return true;
		}
	}

	class WanderAroundGoal extends Goal
	{
		WanderAroundGoal() 
		{
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() 
		{
			return DragonflyEntity.this.navigation.isDone() && DragonflyEntity.this.random.nextInt(10) == 0;
		}
		
		@Override
		public boolean canContinueToUse() 
		{
			return DragonflyEntity.this.navigation.isInProgress();
		}
		
		@Override
		public void start()
		{
			Vec3 vec3d = this.getRandomLocation();
			
			if (vec3d != null) 
			{
				BlockPos pos = new BlockPos(vec3d);
				if (!pos.equals(DragonflyEntity.this.blockPosition()))
				{
					Path path = DragonflyEntity.this.navigation.createPath(new BlockPos(vec3d), 1);
					if (path != null) 
					{
						DragonflyEntity.this.navigation.moveTo(path, 1.0D);
					}
				}
			}
			super.start();
		}
		
		private Vec3 getRandomLocation() 
		{
			int h = BlockHelper.downRay(DragonflyEntity.this.level, DragonflyEntity.this.blockPosition(), 16);

			Vec3 rotation = DragonflyEntity.this.getViewVector(0.0F);
			
			Vec3 airPos = RandomPos.getAboveLandPos(DragonflyEntity.this, 8, 7, 
					rotation, 1.5707964F, 2, 1);
			
			if (airPos != null) 
			{
				if (isInVoid(airPos)) 
				{
					for (int i = 0; i < 8; i++) 
					{
						airPos = RandomPos.getAboveLandPos(DragonflyEntity.this, 16, 7, rotation, (ModMathHelper.PI2), 2, 1);
						if (airPos != null && !isInVoid(airPos)) 
						{
							return airPos;
						}
					}
					return null;
				}
				if (h > 5 && airPos.y() >= DragonflyEntity.this.blockPosition().getY()) 
				{
					airPos = new Vec3(airPos.x, airPos.y - h * 0.5, airPos.z);
				}
				return airPos;
			}
			return RandomPos.getAirPos(DragonflyEntity.this, 8, 4, -2, rotation, 1.5707963705062866D);
		}
		
		private boolean isInVoid(Vec3 pos) 
		{
			int h = BlockHelper.downRay(DragonflyEntity.this.level, new BlockPos(pos), 128);
			return h > 100;
		}
	}
}
