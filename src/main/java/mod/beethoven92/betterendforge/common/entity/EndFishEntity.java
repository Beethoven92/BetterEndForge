package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

public class EndFishEntity extends AbstractSchoolingFish
{
	public static final int VARIANTS_NORMAL = 5;
	public static final int VARIANTS_SULPHUR = 3;
	public static final int VARIANTS = VARIANTS_NORMAL + VARIANTS_SULPHUR;
	
	private static final EntityDataAccessor<Byte> VARIANT = SynchedEntityData.defineId(EndFishEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> SCALE = SynchedEntityData.defineId(EndFishEntity.class, EntityDataSerializers.BYTE);
	
	public EndFishEntity(EntityType<? extends AbstractSchoolingFish> type, Level worldIn) 
	{
		super(type, worldIn);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,
			SpawnGroupData spawnDataIn, CompoundTag dataTag) 
	{
		SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		
		if (ModBiomes.getFromBiome(level.getBiome(blockPosition())) == ModBiomes.SULPHUR_SPRINGS) {
			this.entityData.set(VARIANT, (byte) (random.nextInt(VARIANTS_SULPHUR) + VARIANTS_NORMAL));
		}

		if (dataTag != null) {
			if (dataTag.contains("variant"))
				this.entityData.set(VARIANT, dataTag.getByte("variant"));
			if (dataTag.contains("scale"))
				this.entityData.set(SCALE, dataTag.getByte("scale"));
		}
		
		this.refreshDimensions();
		return data;
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, (byte)this.random.nextInt(VARIANTS));
		this.entityData.define(SCALE, (byte)this.random.nextInt(16));
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) 
	{
		super.addAdditionalSaveData(compound);
		compound.putByte("Variant", (byte)this.getVariant());
		compound.putByte("Scale", entityData.get(SCALE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) 
	{
		super.readAdditionalSaveData(compound);
		if (compound.contains("Variant")) 
		{
			this.entityData.set(VARIANT, compound.getByte("Variant"));
		}
		if (compound.contains("Scale")) 
		{
			this.entityData.set(SCALE, compound.getByte("Scale"));
		}
	}
	
	@Override
	protected ItemStack getBucketItemStack() 
	{
		ItemStack bucket = ModItems.BUCKET_END_FISH.get().getDefaultInstance();
		CompoundTag tag = bucket.getOrCreateTag();
		tag.putByte("variant", entityData.get(VARIANT));
		tag.putByte("scale", entityData.get(SCALE));
		return bucket;
	}

	@Override
	protected SoundEvent getFlopSound() 
	{
		return SoundEvents.TROPICAL_FISH_FLOP;
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.SALMON_DEATH;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) 
	{
		return SoundEvents.SALMON_HURT;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if (random.nextInt(8) == 0 && getFeetBlockState().is(Blocks.WATER)) 
		{
			double x = this.getX() + random.nextGaussian() * 0.2;
			double y = this.getY() + random.nextGaussian() * 0.2;
			double z = this.getZ() + random.nextGaussian() * 0.2;
			level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0, 0);
		}
	}

	public static AttributeSupplier.Builder registerAttributes() 
	{
		return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 2.0).
				add(Attributes.FOLLOW_RANGE, 16.0).
				add(Attributes.MOVEMENT_SPEED, 0.75);
	}
	
	public int getVariant() 
	{
		return (int) this.entityData.get(VARIANT);
	}
	
	public float getScale() 
	{
		return this.entityData.get(SCALE) / 32F + 0.75F;
	}

	public static boolean canSpawn(EntityType<EndFishEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason,
			BlockPos pos, Random random) 
	{
		AABB box = new AABB(pos).inflate(16);
		List<EndFishEntity> list = world.getEntitiesOfClass(EndFishEntity.class, box, (entity) -> { return true; });
		return list.size() < 9;
	}
}
