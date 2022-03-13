package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class EndFishEntity extends AbstractGroupFishEntity
{
	public static final int VARIANTS_NORMAL = 5;
	public static final int VARIANTS_SULPHUR = 3;
	public static final int VARIANTS = VARIANTS_NORMAL + VARIANTS_SULPHUR;
	
	private static final DataParameter<Byte> VARIANT = EntityDataManager.defineId(EndFishEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> SCALE = EntityDataManager.defineId(EndFishEntity.class, DataSerializers.BYTE);
	
	public EndFishEntity(EntityType<? extends AbstractGroupFishEntity> type, World worldIn) 
	{
		super(type, worldIn);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) 
	{
		ILivingEntityData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		
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
	public void addAdditionalSaveData(CompoundNBT compound) 
	{
		super.addAdditionalSaveData(compound);
		compound.putByte("Variant", (byte)this.getVariant());
		compound.putByte("Scale", entityData.get(SCALE));
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) 
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
		CompoundNBT tag = bucket.getOrCreateTag();
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

	public static AttributeModifierMap.MutableAttribute registerAttributes() 
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

	public static boolean canSpawn(EntityType<EndFishEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) 
	{
		AxisAlignedBB box = new AxisAlignedBB(pos).inflate(16);
		List<EndFishEntity> list = world.getEntitiesOfClass(EndFishEntity.class, box, (entity) -> { return true; });
		return list.size() < 9;
	}
}
