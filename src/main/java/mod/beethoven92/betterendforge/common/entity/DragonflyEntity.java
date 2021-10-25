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
		this.moveController = new FlyingMovementController(this, 20, true);
		this.lookController = new DragonflyLookControl(this);
	    this.setPathPriority(PathNodeType.WATER, -1.0F);
	    this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
		this.experienceValue = 1;
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
		return LivingEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 8.0).
				createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0).
				createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1).
				createMutableAttribute(Attributes.FLYING_SPEED, 1.0);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) 
	{
		return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}
	
	@Override
	protected PathNavigator createNavigator(World worldIn) 
	{
		FlyingPathNavigator flyingNavigator = new FlyingPathNavigator(this, worldIn) 
		{
			@Override
	         public boolean canEntityStandOnPos(BlockPos pos) 
	         {
				BlockState state = this.world.getBlockState(pos);
				return state.isAir() || !state.getMaterial().blocksMovement();
	         }

			 @Override
	         public void tick() 
	         {
	               super.tick();
	         }
	    };
	    flyingNavigator.setCanEnterDoors(false);
	    flyingNavigator.setCanSwim(false);
	    flyingNavigator.setCanOpenDoors(false);
	    return flyingNavigator;
	}
	
	@Override
	public boolean canBePushed() 
	{
		return false;
	}

	@Override
	public boolean hasNoGravity() 
	{
		return true;
	}
	
	 @Override
	public boolean onLivingFall(float distance, float damageMultiplier) 
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
		return ModMathHelper.randRange(0.25F, 0.5F, rand);
	}
		
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) 
	{
		return ModEntityTypes.DRAGONFLY.get().create(world);
	}
	
	public static boolean canSpawn(EntityType<DragonflyEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random)
	{
		AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
		List<DragonflyEntity> list = world.getEntitiesWithinAABB(DragonflyEntity.class, box, (entity) -> { return true; });
		int y = world.getChunk(pos).getTopBlockY(Type.WORLD_SURFACE, pos.getX() & 15, pos.getY() & 15);
		
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
		protected boolean shouldResetPitch() 
		{
			return true;
		}
	}

	class WanderAroundGoal extends Goal
	{
		WanderAroundGoal() 
		{
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() 
		{
			return DragonflyEntity.this.navigator.noPath() && DragonflyEntity.this.rand.nextInt(10) == 0;
		}
		
		@Override
		public boolean shouldContinueExecuting() 
		{
			return DragonflyEntity.this.navigator.hasPath();
		}
		
		@Override
		public void startExecuting()
		{
			Vector3d vec3d = this.getRandomLocation();
			
			if (vec3d != null) 
			{
				BlockPos pos = new BlockPos(vec3d);
				if (!pos.equals(DragonflyEntity.this.getPosition()))
				{
					Path path = DragonflyEntity.this.navigator.getPathToPos(new BlockPos(vec3d), 1);
					if (path != null) 
					{
						DragonflyEntity.this.navigator.setPath(path, 1.0D);
					}
				}
			}
			super.startExecuting();
		}
		
		private Vector3d getRandomLocation() 
		{
			int h = BlockHelper.downRay(DragonflyEntity.this.world, DragonflyEntity.this.getPosition(), 16);

			Vector3d rotation = DragonflyEntity.this.getLook(0.0F);
			
			Vector3d airPos = RandomPositionGenerator.findAirTarget(DragonflyEntity.this, 8, 7, 
					rotation, 1.5707964F, 2, 1);
			
			if (airPos != null) 
			{
				if (isInVoid(airPos)) 
				{
					for (int i = 0; i < 8; i++) 
					{
						airPos = RandomPositionGenerator.findAirTarget(DragonflyEntity.this, 16, 7, rotation, (ModMathHelper.PI2), 2, 1);
						if (airPos != null && !isInVoid(airPos)) 
						{
							return airPos;
						}
					}
					return null;
				}
				if (h > 5 && airPos.getY() >= DragonflyEntity.this.getPosition().getY()) 
				{
					airPos = new Vector3d(airPos.x, airPos.y - h * 0.5, airPos.z);
				}
				return airPos;
			}
			return RandomPositionGenerator.findGroundTarget(DragonflyEntity.this, 8, 4, -2, rotation, 1.5707963705062866D);
		}
		
		private boolean isInVoid(Vector3d pos) 
		{
			int h = BlockHelper.downRay(DragonflyEntity.this.world, new BlockPos(pos), 128);
			return h > 100;
		}
	}
}
