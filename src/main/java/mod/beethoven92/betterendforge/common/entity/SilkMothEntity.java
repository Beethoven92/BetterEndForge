package mod.beethoven92.betterendforge.common.entity;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.SilkMothNestBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class SilkMothEntity extends AnimalEntity implements IFlyingAnimal {
	private BlockPos hivePos;
	private BlockPos entrance;
	private World hiveWorld;

	public SilkMothEntity(EntityType<? extends SilkMothEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new FlyingMovementController(this, 20, true);
		this.lookControl = new MothLookControl(this);
		this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
		this.xpReward = 1;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 2.0D)
				.add(Attributes.FOLLOW_RANGE, 16.0D)
				.add(Attributes.FLYING_SPEED, 0.4D)
				.add(Attributes.MOVEMENT_SPEED, 0.1D);
	}

	public void setHive(World world, BlockPos hive) {
		this.hivePos = hive;
		this.hiveWorld = world;
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
		if (hivePos != null) {
			tag.put("HivePos", NBTUtil.writeBlockPos(hivePos));
			tag.putString("HiveWorld", hiveWorld.dimension().location().toString());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("HivePos")) {
			hivePos = NBTUtil.readBlockPos(tag.getCompound("HivePos"));
			ResourceLocation worldID = new ResourceLocation(tag.getString("HiveWorld"));
			try {
				hiveWorld = level.getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, worldID));
			} catch (Exception e) {
				BetterEnd.LOGGER.warn("Silk Moth Hive World {} is missing!", worldID);
				hivePos = null;
			}
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new ReturnToHiveGoal());
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(8, new WanderAroundGoal());
		this.goalSelector.addGoal(9, new SwimGoal(this));
	}

	@Override
	protected PathNavigator createNavigation(World world) {
		FlyingPathNavigator birdNavigation = new FlyingPathNavigator(this, world) {
			@Override
			public boolean isStableDestination(BlockPos pos) {
				BlockState state = this.level.getBlockState(pos);
				return state.isAir() || !state.getMaterial().blocksMotion();
			}

			@Override
			public void tick() {
				super.tick();
			}
		};
		birdNavigation.setCanPassDoors(false);
		birdNavigation.setCanFloat(false);
		birdNavigation.setCanOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean isMovementNoisy() {
		return false;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
		return ModEntityTypes.SILK_MOTH.get().create(world);
	}

	public static boolean canSpawn(EntityType<SilkMothEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		int y = world.getChunk(pos).getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX() & 15, pos.getY() & 15);
		return y > 0 && pos.getY() >= y;
	}

	class MothLookControl extends LookController {
		MothLookControl(MobEntity entity) {
			super(entity);
		}

		@Override
		protected boolean resetXRotOnTick() {
			return true;
		}
	}

	class WanderAroundGoal extends Goal {
		WanderAroundGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return SilkMothEntity.this.navigation.isDone() && SilkMothEntity.this.random.nextInt(10) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			return SilkMothEntity.this.navigation.isInProgress();
		}

		@Override
		public void start() {
			Vector3d vec3d = this.getRandomLocation();
			if (vec3d != null) {
				try {
					SilkMothEntity.this.navigation
							.moveTo(SilkMothEntity.this.navigation.createPath(new BlockPos(vec3d), 1), 1.0D);
				} catch (Exception e) {
				}
			}
		}

		@Nullable
		private Vector3d getRandomLocation() {
			Vector3d vec3d3 = SilkMothEntity.this.getViewVector(0.0F);
			Vector3d vec3d4 = RandomPositionGenerator.getAboveLandPos(SilkMothEntity.this, 8, 7, vec3d3, 1.5707964F, 2, 1);
			return vec3d4 != null ? vec3d4
					: RandomPositionGenerator.getAirPos(SilkMothEntity.this, 8, 4, -2, vec3d3, 1.5707963705062866D);
		}
	}

	class ReturnToHiveGoal extends Goal
	{
		ReturnToHiveGoal() 
		{
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() 
		{
			return SilkMothEntity.this.hivePos != null && SilkMothEntity.this.hiveWorld == SilkMothEntity.this.level
					&& SilkMothEntity.this.navigation.isDone() && SilkMothEntity.this.random.nextInt(16) == 0
					&& SilkMothEntity.this.position().distanceToSqr(SilkMothEntity.this.hivePos.getX(),
							SilkMothEntity.this.hivePos.getY(), SilkMothEntity.this.hivePos.getZ()) < 32;
		}

		@Override
		public boolean canContinueToUse() 
		{
			return SilkMothEntity.this.navigation.isInProgress() && level.getBlockState(entrance).isAir()
					&& level.getBlockState(hivePos).is(ModBlocks.SILK_MOTH_NEST.get());
		}

		@Override
		public void start() 
		{
			BlockState state = SilkMothEntity.this.level.getBlockState(SilkMothEntity.this.hivePos);
			if (!state.is(ModBlocks.SILK_MOTH_NEST.get())) 
			{
				SilkMothEntity.this.hivePos = null;
				SilkMothEntity.this.entrance = null;
			}
			else
			{
				SilkMothEntity.this.entrance = SilkMothEntity.this.hivePos.relative(state.getValue(SilkMothNestBlock.FACING));
				SilkMothEntity.this.navigation.moveTo(SilkMothEntity.this.navigation.createPath(entrance, 1), 1.0D);
			}
		}

		@Override
		public void tick() 
		{
			if (SilkMothEntity.this.hivePos != null && SilkMothEntity.this.entrance != null)
			{
				super.tick();
			    double dx = Math.abs(SilkMothEntity.this.entrance.getX() - SilkMothEntity.this.getX());
			    double dy = Math.abs(SilkMothEntity.this.entrance.getY() - SilkMothEntity.this.getY());
			    double dz = Math.abs(SilkMothEntity.this.entrance.getZ() - SilkMothEntity.this.getZ());
			    if (dx + dy + dz < 1) 
			    {
			    	BlockState state = SilkMothEntity.this.level.getBlockState(hivePos);
				    if (state.is(ModBlocks.SILK_MOTH_NEST.get())) 
				    {
				    	int fullness = state.getValue(BlockProperties.FULLNESS);
					    if (fullness < 3 && SilkMothEntity.this.random.nextBoolean()) 
					    {
					    	fullness++;
						    BlockHelper.setWithUpdate(SilkMothEntity.this.hiveWorld, SilkMothEntity.this.hivePos, state);
					    }
					    SilkMothEntity.this.level.playSound(null, SilkMothEntity.this.entrance,
							SoundEvents.BEEHIVE_ENTER, SoundCategory.BLOCKS, 1, 1);
					    SilkMothEntity.this.remove();
				    }
			    }
			}
		}
	}
}
