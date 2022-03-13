package mod.beethoven92.betterendforge.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;

public class DragonflyEntity extends AnimalEntity implements IFlyingAnimal
{
	public DragonflyEntity(EntityType<? extends AnimalEntity> type, World worldIn) 
	{
		super(type, worldIn);
		this.moveControl = new FlyingMovementController(this, 20, true);
		this.lookControl = new DragonflyLookControl(this);
	    this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
	    this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
		this.xpReward = 1;
	}
	
	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new WanderAroundGoal());
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() 
	{
		return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 8.0).
				add(Attributes.FOLLOW_RANGE, 16.0).
				add(Attributes.MOVEMENT_SPEED, 0.1).
				add(Attributes.FLYING_SPEED, 1.0);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) 
	{
		return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}
	
	@Override
	protected PathNavigator createNavigation(World worldIn) 
	{
		FlyingPathNavigator flyingNavigator = new FlyingPathNavigator(this, worldIn) 
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
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) 
	{
		return ModEntityTypes.DRAGONFLY.get().create(level);
	}
	
	public static boolean canSpawn(EntityType<DragonflyEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random)
	{
		AxisAlignedBB box = new AxisAlignedBB(pos).inflate(16);
		List<DragonflyEntity> list = world.getEntitiesOfClass(DragonflyEntity.class, box, (entity) -> { return true; });
		int y = world.getChunk(pos).getHeight(Type.WORLD_SURFACE, pos.getX() & 15, pos.getY() & 15);
		
		// FIX dragonfly spawning too much and preventing other entities to spawn
		return y > 0 && pos.getY() >= y && list.size() < 9;
	}
	
	public class DragonflyLookControl extends LookController 
	{
		DragonflyLookControl(MobEntity entity) 
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
			Vector3d vec3d = this.getRandomLocation();
			
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
		
		private Vector3d getRandomLocation() 
		{
			int h = BlockHelper.downRay(DragonflyEntity.this.level, DragonflyEntity.this.blockPosition(), 16);

			Vector3d rotation = DragonflyEntity.this.getViewVector(0.0F);
			
			Vector3d airPos = RandomPositionGenerator.getAboveLandPos(DragonflyEntity.this, 8, 7, 
					rotation, 1.5707964F, 2, 1);
			
			if (airPos != null) 
			{
				if (isInVoid(airPos)) 
				{
					for (int i = 0; i < 8; i++) 
					{
						airPos = RandomPositionGenerator.getAboveLandPos(DragonflyEntity.this, 16, 7, rotation, (ModMathHelper.PI2), 2, 1);
						if (airPos != null && !isInVoid(airPos)) 
						{
							return airPos;
						}
					}
					return null;
				}
				if (h > 5 && airPos.y() >= DragonflyEntity.this.blockPosition().getY()) 
				{
					airPos = new Vector3d(airPos.x, airPos.y - h * 0.5, airPos.z);
				}
				return airPos;
			}
			return RandomPositionGenerator.getAirPos(DragonflyEntity.this, 8, 4, -2, rotation, 1.5707963705062866D);
		}
		
		private boolean isInVoid(Vector3d pos) 
		{
			int h = BlockHelper.downRay(DragonflyEntity.this.level, new BlockPos(pos), 128);
			return h > 100;
		}
	}
}
