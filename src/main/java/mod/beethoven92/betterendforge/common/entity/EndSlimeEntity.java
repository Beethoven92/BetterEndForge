package mod.beethoven92.betterendforge.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EndSlimeEntity extends SlimeEntity {
	private static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EndSlimeEntity.class,
			DataSerializers.BYTE);
	private static final Mutable POS = new Mutable();

	public EndSlimeEntity(EntityType<EndSlimeEntity> entityType, World world) {
		super(entityType, world);
		this.moveController = new EndSlimeMoveControl(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimmingGoal());
		this.goalSelector.addGoal(2, new FaceTowardTargetGoal());
		this.goalSelector.addGoal(3, new RandomLookGoal());
		this.goalSelector.addGoal(5, new MoveGoal());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true,
				false, (livingEntity) -> {
					return Math.abs(livingEntity.getPosY() - this.getPosY()) <= 4.0D;
				}));
		this.targetSelector.addGoal(3,
				new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return LivingEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 1.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason,
			ILivingEntityData entityData, CompoundNBT entityTag) {
		ILivingEntityData data = super.onInitialSpawn(world, difficulty, spawnReason, entityData, entityTag);
		BetterEndBiome biome = ModBiomes.getFromBiome(world.getBiome(getPosition()));
		if (biome == ModBiomes.FOGGY_MUSHROOMLAND) {
			this.setMossy();
		}
		else if (biome == ModBiomes.MEGALAKE || biome == ModBiomes.MEGALAKE_GROVE) {
			this.setLake();
		}
		else if (biome == ModBiomes.AMBER_LAND) {
			this.setAmber(true);
		}
		this.recalculateSize();
		return data;
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(VARIANT, (byte) 0);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putByte("Variant", (byte) getSlimeType());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Variant")) {
			this.dataManager.set(VARIANT, compound.getByte("Variant"));
		}
	}

	protected BasicParticleType getParticles() {
		return ParticleTypes.PORTAL;
	}

	@Override
	public void remove(boolean keepData) {
		int i = this.getSlimeSize();
		if (!this.world.isRemote && i > 1 && getShouldBeDead()) {
			ITextComponent text = this.getCustomName();
			boolean bl = this.isAIDisabled();
			float f = (float) i / 4.0F;
			int j = i / 2;
			int k = 2 + this.rand.nextInt(3);
			int type = this.getSlimeType();

			for (int l = 0; l < k; ++l) {
				float g = ((float) (l % 2) - 0.5F) * f;
				float h = ((float) (l / 2) - 0.5F) * f;
				EndSlimeEntity slimeEntity = (EndSlimeEntity) this.getType().create(this.world);
				if (this.isNoDespawnRequired()) {
					slimeEntity.enablePersistence();
				}

				slimeEntity.setSlimeType(type);
				slimeEntity.setCustomName(text);
				slimeEntity.setNoAI(bl);
				slimeEntity.setInvulnerable(this.isInvulnerable());
				slimeEntity.setSlimeSize(j, true);
				slimeEntity.recalculateSize();
				slimeEntity.setLocationAndAngles(this.getPosX() + (double) g, this.getPosY() + 0.5D,
						this.getPosZ() + (double) h, this.rand.nextFloat() * 360.0F, 0.0F);
				this.world.addEntity(slimeEntity);
			}
		}
		this.removed = true;
		super.remove(keepData);
	}

	/*
	 * @Override protected void dropLoot(DamageSource source, boolean
	 * causedByPlayer) { int maxCount = this.getSlimeSize(); int minCount = maxCount
	 * >> 1; if (minCount < 1) { minCount = 1; } if (causedByPlayer &&
	 * this.attackingPlayer != null) { int looting =
	 * EnchantmentHelper.getLootingModifier(this.attackingPlayer); minCount +=
	 * looting; } int count = minCount < maxCount ?
	 * ModMathHelper.randRange(minCount, maxCount, rand) : maxCount; ItemEntity drop
	 * = new ItemEntity(world, getPosX(), getPosY(), getPosZ(), new
	 * ItemStack(Items.SLIME_BALL, count)); this.world.addEntity(drop); }
	 */

	public int getSlimeType() {
		return this.dataManager.get(VARIANT).intValue();
	}

	public void setSlimeType(int value) {
		this.dataManager.set(VARIANT, (byte) value);
	}

	protected void setMossy() {
		setSlimeType(1);
	}

	public boolean isMossy() {
		return getSlimeType() == 1;
	}
	
	protected void setLake() {
		setSlimeType(2);
	}

	public boolean isLake() {
		return getSlimeType() == 2;
	}

	protected void setAmber(boolean mossy) {
		this.dataManager.set(VARIANT, (byte) 3);
	}

	public boolean isAmber() {
		return this.dataManager.get(VARIANT) == 3;
	}

	public boolean isChorus() {
		return this.dataManager.get(VARIANT) == 0;
	}

	public static boolean canSpawn(EntityType<EndSlimeEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		return isPermanentBiome(world, pos) || (notManyEntities(world, pos, 32, 3) && isWaterNear(world, pos, 32, 8));
	}

	private static boolean isPermanentBiome(IServerWorld world, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		return ModBiomes.getFromBiome(biome) == ModBiomes.CHORUS_FOREST;
	}

	private static boolean notManyEntities(IServerWorld world, BlockPos pos, int radius, int maxCount) {
		AxisAlignedBB box = new AxisAlignedBB(pos).grow(radius);
		List<EndSlimeEntity> list = world.getEntitiesWithinAABB(EndSlimeEntity.class, box, (entity) -> {
			return true;
		});
		return list.size() <= maxCount;
	}

	private static boolean isWaterNear(IServerWorld world, BlockPos pos, int radius, int radius2) {
		for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
			POS.setX(x);
			for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {
				POS.setZ(z);
				for (int y = pos.getY() - radius2; y <= pos.getY() + radius2; y++) {
					POS.setY(y);
					if (world.getBlockState(POS).getBlock() == Blocks.WATER) {
						return true;
					}
				}
			}
		}
		return false;
	}

	class MoveGoal extends Goal {
		public MoveGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() {
			if (EndSlimeEntity.this.isPassenger()) {
				return false;
			}

			float yaw = EndSlimeEntity.this.getRotationYawHead();
			float speed = EndSlimeEntity.this.getAIMoveSpeed();
			if (speed > 0.1) {
				float dx = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
				float dz = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
				BlockPos pos = EndSlimeEntity.this.getPosition().add(dx * speed * 4, 0, dz * speed * 4);
				int down = BlockHelper.downRay(EndSlimeEntity.this.world, pos, 16);
				return down < 5;
			}

			return true;
		}

		@Override
		public void tick() {
			((EndSlimeMoveControl) EndSlimeEntity.this.getMoveHelper()).move(1.0D);
		}
	}

	class SwimmingGoal extends Goal {
		public SwimmingGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
			EndSlimeEntity.this.getNavigator().setCanSwim(true);
		}

		@Override
		public boolean shouldExecute() {
			return (EndSlimeEntity.this.isInWater() || EndSlimeEntity.this.isInLava())
					&& EndSlimeEntity.this.getMoveHelper() instanceof EndSlimeMoveControl;
		}

		@Override
		public void tick() {
			if (EndSlimeEntity.this.getRNG().nextFloat() < 0.8F) {
				EndSlimeEntity.this.getJumpController().setJumping();
				;
			}

			((EndSlimeMoveControl) EndSlimeEntity.this.getMoveHelper()).move(1.2D);
		}
	}

	class RandomLookGoal extends Goal {
		private float targetYaw;
		private int timer;

		public RandomLookGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		@Override
		public boolean shouldExecute() {
			return EndSlimeEntity.this.getAttackTarget() == null
					&& (EndSlimeEntity.this.onGround || EndSlimeEntity.this.isInWater()
							|| EndSlimeEntity.this.isInLava() || EndSlimeEntity.this.isPotionActive(Effects.LEVITATION))
					&& EndSlimeEntity.this.getMoveHelper() instanceof EndSlimeMoveControl;
		}

		@Override
		public void tick() {
			if (--this.timer <= 0) {
				this.timer = 40 + EndSlimeEntity.this.getRNG().nextInt(60);
				this.targetYaw = (float) EndSlimeEntity.this.getRNG().nextInt(360);
			}

			((EndSlimeMoveControl) EndSlimeEntity.this.getMoveHelper()).look(this.targetYaw, false);
		}
	}

	class FaceTowardTargetGoal extends Goal {
		private int ticksLeft;

		public FaceTowardTargetGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		@Override
		public boolean shouldExecute() {
			LivingEntity livingEntity = EndSlimeEntity.this.getAttackTarget();
			if (livingEntity == null) {
				return false;
			} else if (!livingEntity.isAlive()) {
				return false;
			} else {
				return livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.disableDamage
						? false
						: EndSlimeEntity.this.getMoveHelper() instanceof EndSlimeMoveControl;
			}
		}

		@Override
		public void startExecuting() {
			this.ticksLeft = 300;
			super.startExecuting();
		}

		@Override
		public boolean shouldContinueExecuting() {
			LivingEntity livingEntity = EndSlimeEntity.this.getAttackTarget();
			if (livingEntity == null) {
				return false;
			} else if (!livingEntity.isAlive()) {
				return false;
			} else if (livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.disableDamage) {
				return false;
			} else {
				return --this.ticksLeft > 0;
			}
		}

		@Override
		public void tick() {
			EndSlimeEntity.this.faceEntity(EndSlimeEntity.this.getAttackTarget(), 10.0F, 10.0F);
			((EndSlimeMoveControl) EndSlimeEntity.this.getMoveHelper()).look(EndSlimeEntity.this.rotationYaw,
					EndSlimeEntity.this.canDamagePlayer());
		}
	}

	class EndSlimeMoveControl extends MovementController {
		private float targetYaw;
		private int ticksUntilJump;
		private boolean jumpOften;

		public EndSlimeMoveControl(EndSlimeEntity slime) {
			super(slime);
			this.targetYaw = 180.0F * slime.rotationYaw / 3.1415927F;
		}

		public void look(float targetYaw, boolean jumpOften) {
			this.targetYaw = targetYaw;
			this.jumpOften = jumpOften;
		}

		public void move(double speed) {
			this.speed = speed;
			this.action = MovementController.Action.MOVE_TO;
		}

		@Override
		public void tick() {
			this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.targetYaw, 90.0F);
			this.mob.rotationYawHead = this.mob.rotationYaw;
			this.mob.renderYawOffset = this.mob.rotationYaw;
			if (this.action != MovementController.Action.MOVE_TO) {
				this.mob.setMoveForward(0.0F);
			} else {
				this.action = MovementController.Action.WAIT;
				if (this.mob.isOnGround()) {
					this.mob.setAIMoveSpeed(
							(float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					if (this.ticksUntilJump-- <= 0) {
						this.ticksUntilJump = EndSlimeEntity.this.getJumpDelay();
						if (this.jumpOften) {
							this.ticksUntilJump /= 3;
						}

						EndSlimeEntity.this.getJumpController().setJumping();
						if (EndSlimeEntity.this.makesSoundOnJump()) {
							EndSlimeEntity.this.playSound(EndSlimeEntity.this.getJumpSound(),
									EndSlimeEntity.this.getSoundVolume(), getJumpSoundPitch());
						}
					} else {
						EndSlimeEntity.this.moveStrafing = 0.0F;
						EndSlimeEntity.this.moveForward = 0.0F;
						this.mob.setAIMoveSpeed(0.0F);
					}
				} else {
					this.mob.setAIMoveSpeed(
							(float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				}

			}
		}

		private float getJumpSoundPitch() {
			float f = EndSlimeEntity.this.isSmallSlime() ? 1.4F : 0.8F;
			return ((EndSlimeEntity.this.rand.nextFloat() - EndSlimeEntity.this.rand.nextFloat()) * 0.2F + 1.0F) * f;
		}
	}
}
