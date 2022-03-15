package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

public class CubozoaEntity extends AbstractSchoolingFish {
	public static final int VARIANTS = 2;
	private static final EntityDataAccessor<Byte> VARIANT = SynchedEntityData.defineId(CubozoaEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> SCALE = SynchedEntityData.defineId(CubozoaEntity.class, EntityDataSerializers.BYTE);

	public CubozoaEntity(EntityType<CubozoaEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityTag) {
		SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
		
		if (ModBiomes.getFromBiome(world.getBiome(blockPosition())) == ModBiomes.SULPHUR_SPRINGS) 
		{
			this.entityData.set(VARIANT, (byte) 1);
		}
		
		if (entityTag != null) {
			if (entityTag.contains("variant"))
				this.entityData.set(VARIANT, entityTag.getByte("variant"));
			if (entityTag.contains("scale"))
				this.entityData.set(SCALE, entityTag.getByte("scale"));
		}
		
		this.refreshDimensions();
		return data;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, (byte) 0);
		this.entityData.define(SCALE, (byte) this.getRandom().nextInt(16));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("Variant", (byte) getVariant());
		tag.putByte("Scale", entityData.get(SCALE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("Variant")) {
			this.entityData.set(VARIANT, tag.getByte("Variant"));
		}
		if (tag.contains("Scale")) {
			this.entityData.set(SCALE, tag.getByte("Scale"));
		}
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return LivingEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 2.0)
				.add(Attributes.FOLLOW_RANGE, 16.0)
				.add(Attributes.MOVEMENT_SPEED, 0.5);
	}

	public int getVariant() {
		return (int) this.entityData.get(VARIANT);
	}

	public float getScale() {
		return this.entityData.get(SCALE) / 32F + 0.75F;
	}

	public static boolean canSpawn(EntityType<CubozoaEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
		AABB box = new AABB(pos).inflate(16);
		List<CubozoaEntity> list = world.getEntitiesOfClass(CubozoaEntity.class, box, (entity) -> {
			return true;
		});
		return list.size() < 9;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return dimensions.height * 0.5F;
	}
	
	/*@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		int count = rand.nextInt(3);
		if (count > 0) {
			ItemEntity drop = new ItemEntity(world, getPosX(), getPosY(), getPosZ(), new ItemStack(ModItems.GELATINE.get(), count));
			this.world.addEntity(drop);
		}
	}*/

	@Override
	public ItemStack getBucketItemStack() {
		ItemStack bucket = ModItems.BUCKET_CUBOZOA.get().getDefaultInstance();
		CompoundTag tag = bucket.getOrCreateTag();
		tag.putByte("variant", entityData.get(VARIANT));
		tag.putByte("scale", entityData.get(SCALE));
		return bucket;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.SALMON_FLOP;
	}
	
	@Override
	public void playerTouch(Player player) {
		if (player instanceof ServerPlayer && player.hurt(DamageSource.mobAttack(this), 0.5F)) {
			if (!this.isSilent()) {
				((ServerPlayer) player).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
			}
			if (random.nextBoolean()) {
				player.addEffect(new MobEffectInstance(MobEffects.POISON, 20, 0));
			}
		}
	}
}
