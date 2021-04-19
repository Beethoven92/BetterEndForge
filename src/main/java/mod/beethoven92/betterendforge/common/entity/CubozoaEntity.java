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
	private static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(CubozoaEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> SCALE = EntityDataManager.createKey(CubozoaEntity.class, DataSerializers.BYTE);

	public CubozoaEntity(EntityType<CubozoaEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, ILivingEntityData entityData, CompoundNBT entityTag) {
		ILivingEntityData data = super.onInitialSpawn(world, difficulty, spawnReason, entityData, entityTag);
		
		if (ModBiomes.getFromBiome(world.getBiome(getPosition())) == ModBiomes.SULPHUR_SPRINGS) 
		{
			this.dataManager.set(VARIANT, (byte) 1);
		}
		
		if (entityTag != null) {
			if (entityTag.contains("variant"))
				this.dataManager.set(VARIANT, entityTag.getByte("variant"));
			if (entityTag.contains("scale"))
				this.dataManager.set(SCALE, entityTag.getByte("scale"));
		}
		
		this.recalculateSize();
		return data;
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(VARIANT, (byte) 0);
		this.dataManager.register(SCALE, (byte) this.getRNG().nextInt(16));
	}

	@Override
	public void writeAdditional(CompoundNBT tag) {
		super.writeAdditional(tag);
		tag.putByte("Variant", (byte) getVariant());
		tag.putByte("Scale", dataManager.get(SCALE));
	}

	@Override
	public void readAdditional(CompoundNBT tag) {
		super.readAdditional(tag);
		if (tag.contains("Variant")) {
			this.dataManager.set(VARIANT, tag.getByte("Variant"));
		}
		if (tag.contains("Scale")) {
			this.dataManager.set(SCALE, tag.getByte("Scale"));
		}
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return LivingEntity.registerAttributes()
				.createMutableAttribute(Attributes.MAX_HEALTH, 2.0)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5);
	}

	public int getVariant() {
		return (int) this.dataManager.get(VARIANT);
	}

	public float getScale() {
		return this.dataManager.get(SCALE) / 32F + 0.75F;
	}

	public static boolean canSpawn(EntityType<CubozoaEntity> type, IServerWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
		AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
		List<CubozoaEntity> list = world.getEntitiesWithinAABB(CubozoaEntity.class, box, (entity) -> {
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
	protected ItemStack getFishBucket() {
		ItemStack bucket = ModItems.BUCKET_CUBOZOA.get().getDefaultInstance();
		CompoundNBT tag = bucket.getOrCreateTag();
		tag.putByte("variant", dataManager.get(VARIANT));
		tag.putByte("scale", dataManager.get(SCALE));
		return bucket;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.ENTITY_SALMON_FLOP;
	}
	
	@Override
	public void onCollideWithPlayer(PlayerEntity player) {
		if (player instanceof ServerPlayerEntity && player.attackEntityFrom(DamageSource.causeMobDamage(this), 0.5F)) {
			if (!this.isSilent()) {
				((ServerPlayerEntity) player).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241773_j_, 0.0F));
			}
			if (rand.nextBoolean()) {
				player.addPotionEffect(new EffectInstance(Effects.POISON, 20, 0));
			}
		}
	}
}
