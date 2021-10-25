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
	
	private static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EndFishEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> SCALE = EntityDataManager.createKey(EndFishEntity.class, DataSerializers.BYTE);
	
	public EndFishEntity(EntityType<? extends AbstractGroupFishEntity> type, World worldIn) 
	{
		super(type, worldIn);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) 
	{
		ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		
		if (ModBiomes.getFromBiome(world.getBiome(getPosition())) == ModBiomes.SULPHUR_SPRINGS) {
			this.dataManager.set(VARIANT, (byte) (rand.nextInt(VARIANTS_SULPHUR) + VARIANTS_NORMAL));
		}

		if (dataTag != null) {
			if (dataTag.contains("variant"))
				this.dataManager.set(VARIANT, dataTag.getByte("variant"));
			if (dataTag.contains("scale"))
				this.dataManager.set(SCALE, dataTag.getByte("scale"));
		}
		
		this.recalculateSize();
		return data;
	}
	
	@Override
	protected void registerData() 
	{
		super.registerData();
		this.dataManager.register(VARIANT, (byte)this.rand.nextInt(VARIANTS));
		this.dataManager.register(SCALE, (byte)this.rand.nextInt(16));
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) 
	{
		super.writeAdditional(compound);
		compound.putByte("Variant", (byte)this.getVariant());
		compound.putByte("Scale", dataManager.get(SCALE));
	}

	@Override
	public void readAdditional(CompoundNBT compound) 
	{
		super.readAdditional(compound);
		if (compound.contains("Variant")) 
		{
			this.dataManager.set(VARIANT, compound.getByte("Variant"));
		}
		if (compound.contains("Scale")) 
		{
			this.dataManager.set(SCALE, compound.getByte("Scale"));
		}
	}
	
	@Override
	protected ItemStack getFishBucket() 
	{
		ItemStack bucket = ModItems.BUCKET_END_FISH.get().getDefaultInstance();
		CompoundNBT tag = bucket.getOrCreateTag();
		tag.putByte("variant", dataManager.get(VARIANT));
		tag.putByte("scale", dataManager.get(SCALE));
		return bucket;
	}

	@Override
	protected SoundEvent getFlopSound() 
	{
		return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.ENTITY_SALMON_DEATH;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) 
	{
		return SoundEvents.ENTITY_SALMON_HURT;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if (rand.nextInt(8) == 0 && getBlockState().isIn(Blocks.WATER)) 
		{
			double x = this.getPosX() + rand.nextGaussian() * 0.2;
			double y = this.getPosY() + rand.nextGaussian() * 0.2;
			double z = this.getPosZ() + rand.nextGaussian() * 0.2;
			world.addParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0, 0);
		}
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() 
	{
		return LivingEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 2.0).
				createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0).
				createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.75);
	}
	
	public int getVariant() 
	{
		return (int) this.dataManager.get(VARIANT);
	}
	
	public float getScale() 
	{
		return this.dataManager.get(SCALE) / 32F + 0.75F;
	}

	public static boolean canSpawn(EntityType<EndFishEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) 
	{
		AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
		List<EndFishEntity> list = world.getEntitiesWithinAABB(EndFishEntity.class, box, (entity) -> { return true; });
		return list.size() < 9;
	}
}
