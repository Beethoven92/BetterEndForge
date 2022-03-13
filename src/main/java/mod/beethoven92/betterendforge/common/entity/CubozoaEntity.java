package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class CubozoaEntity extends AbstractGroupFishEntity {
	public static final int VARIANTS = 2;
	private static final DataParameter<Byte> VARIANT = EntityDataManager.defineId(CubozoaEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> SCALE = EntityDataManager.defineId(CubozoaEntity.class, DataSerializers.BYTE);

	public CubozoaEntity(EntityType<CubozoaEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, ILivingEntityData entityData, CompoundNBT entityTag) {
		ILivingEntityData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
		
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
	public void addAdditionalSaveData(CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("Variant", (byte) getVariant());
		tag.putByte("Scale", entityData.get(SCALE));
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("Variant")) {
			this.entityData.set(VARIANT, tag.getByte("Variant"));
		}
		if (tag.contains("Scale")) {
			this.entityData.set(SCALE, tag.getByte("Scale"));
		}
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
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

	public static boolean canSpawn(EntityType<CubozoaEntity> type, IServerWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
		AxisAlignedBB box = new AxisAlignedBB(pos).inflate(16);
		List<CubozoaEntity> list = world.getEntitiesOfClass(CubozoaEntity.class, box, (entity) -> {
			return true;
		});
		return list.size() < 9;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize dimensions) {
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
	protected ItemStack getBucketItemStack() {
		ItemStack bucket = ModItems.BUCKET_CUBOZOA.get().getDefaultInstance();
		CompoundNBT tag = bucket.getOrCreateTag();
		tag.putByte("variant", entityData.get(VARIANT));
		tag.putByte("scale", entityData.get(SCALE));
		return bucket;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.SALMON_FLOP;
	}
	
	@Override
	public void playerTouch(PlayerEntity player) {
		if (player instanceof ServerPlayerEntity && player.hurt(DamageSource.mobAttack(this), 0.5F)) {
			if (!this.isSilent()) {
				((ServerPlayerEntity) player).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.PUFFER_FISH_STING, 0.0F));
			}
			if (random.nextBoolean()) {
				player.addEffect(new EffectInstance(Effects.POISON, 20, 0));
			}
		}
	}
}
