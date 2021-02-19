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
		this.moveController = new FlyingMovementController(this, 20, true);
		this.lookController = new MothLookControl(this);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
		this.experienceValue = 1;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return LivingEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 2.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.FLYING_SPEED, 0.4D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D);
	}

	public void setHive(World world, BlockPos hive) {
		this.hivePos = hive;
		this.hiveWorld = world;
	}

	@Override
	public void writeAdditional(CompoundNBT tag) {
		super.writeAdditional(tag);
		if (hivePos != null) {
			tag.put("HivePos", NBTUtil.writeBlockPos(hivePos));
			tag.putString("HiveWorld", hiveWorld.getDimensionKey().getLocation().toString());
		}
	}

	@Override
	public void readAdditional(CompoundNBT tag) {
		super.readAdditional(tag);
		if (tag.contains("HivePos")) {
			hivePos = NBTUtil.readBlockPos(tag.getCompound("HivePos"));
			ResourceLocation worldID = new ResourceLocation(tag.getString("HiveWorld"));
			try {
				hiveWorld = world.getServer().getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, worldID));
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
	protected PathNavigator createNavigator(World world) {
		FlyingPathNavigator birdNavigation = new FlyingPathNavigator(this, world) {
			@Override
			public boolean canEntityStandOnPos(BlockPos pos) {
				BlockState state = this.world.getBlockState(pos);
				return state.isAir() || !state.getMaterial().blocksMovement();
			}

			@Override
			public void tick() {
				super.tick();
			}
		};
		birdNavigation.setCanEnterDoors(false);
		birdNavigation.setCanSwim(false);
		birdNavigation.setCanOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

	@Override
	public boolean onLivingFall(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity entity) {
		return ModEntityTypes.SILK_MOTH.get().create(world);
	}

	public static boolean canSpawn(EntityType<SilkMothEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		int y = world.getChunk(pos).getTopBlockY(Heightmap.Type.WORLD_SURFACE, pos.getX() & 15, pos.getY() & 15);
		return y > 0 && pos.getY() >= y;
	}

	class MothLookControl extends LookController {
		MothLookControl(MobEntity entity) {
			super(entity);
		}

		@Override
		protected boolean shouldResetPitch() {
			return true;
		}
	}

	class WanderAroundGoal extends Goal {
		WanderAroundGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() {
			return SilkMothEntity.this.navigator.noPath() && SilkMothEntity.this.rand.nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinueExecuting() {
			return SilkMothEntity.this.navigator.hasPath();
		}

		@Override
		public void startExecuting() {
			Vector3d vec3d = this.getRandomLocation();
			if (vec3d != null) {
				try {
					SilkMothEntity.this.navigator
							.setPath(SilkMothEntity.this.navigator.getPathToPos(new BlockPos(vec3d), 1), 1.0D);
				} catch (Exception e) {
				}
			}
		}

		@Nullable
		private Vector3d getRandomLocation() {
			Vector3d vec3d3 = SilkMothEntity.this.getLook(0.0F);
			Vector3d vec3d4 = RandomPositionGenerator.findAirTarget(SilkMothEntity.this, 8, 7, vec3d3, 1.5707964F, 2, 1);
			return vec3d4 != null ? vec3d4
					: RandomPositionGenerator.findGroundTarget(SilkMothEntity.this, 8, 4, -2, vec3d3, 1.5707963705062866D);
		}
	}

	class ReturnToHiveGoal extends Goal
	{
		ReturnToHiveGoal() 
		{
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() 
		{
			return SilkMothEntity.this.hivePos != null && SilkMothEntity.this.hiveWorld == SilkMothEntity.this.world
					&& SilkMothEntity.this.navigator.noPath() && SilkMothEntity.this.rand.nextInt(16) == 0
					&& SilkMothEntity.this.getPositionVec().squareDistanceTo(SilkMothEntity.this.hivePos.getX(),
							SilkMothEntity.this.hivePos.getY(), SilkMothEntity.this.hivePos.getZ()) < 32;
		}

		@Override
		public boolean shouldContinueExecuting() 
		{
			return SilkMothEntity.this.navigator.hasPath() && world.getBlockState(entrance).isAir()
					&& world.getBlockState(hivePos).isIn(ModBlocks.SILK_MOTH_NEST.get());
		}

		@Override
		public void startExecuting() 
		{
			BlockState state = SilkMothEntity.this.world.getBlockState(SilkMothEntity.this.hivePos);
			if (!state.isIn(ModBlocks.SILK_MOTH_NEST.get())) 
			{
				SilkMothEntity.this.hivePos = null;
				SilkMothEntity.this.entrance = null;
			}
			else
			{
				SilkMothEntity.this.entrance = SilkMothEntity.this.hivePos.offset(state.get(SilkMothNestBlock.FACING));
				SilkMothEntity.this.navigator.setPath(SilkMothEntity.this.navigator.getPathToPos(entrance, 1), 1.0D);
			}
		}

		@Override
		public void tick() 
		{
			if (SilkMothEntity.this.hivePos != null && SilkMothEntity.this.entrance != null)
			{
				super.tick();
			    double dx = Math.abs(SilkMothEntity.this.entrance.getX() - SilkMothEntity.this.getPosX());
			    double dy = Math.abs(SilkMothEntity.this.entrance.getY() - SilkMothEntity.this.getPosY());
			    double dz = Math.abs(SilkMothEntity.this.entrance.getZ() - SilkMothEntity.this.getPosZ());
			    if (dx + dy + dz < 1) 
			    {
			    	BlockState state = SilkMothEntity.this.world.getBlockState(hivePos);
				    if (state.isIn(ModBlocks.SILK_MOTH_NEST.get())) 
				    {
				    	int fullness = state.get(BlockProperties.FULLNESS);
					    if (fullness < 3 && SilkMothEntity.this.rand.nextBoolean()) 
					    {
					    	fullness++;
						    BlockHelper.setWithUpdate(SilkMothEntity.this.hiveWorld, SilkMothEntity.this.hivePos, state);
					    }
					    SilkMothEntity.this.world.playSound(null, SilkMothEntity.this.entrance,
							SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1, 1);
					    SilkMothEntity.this.remove();
				    }
			    }
			}
		}
	}
}
