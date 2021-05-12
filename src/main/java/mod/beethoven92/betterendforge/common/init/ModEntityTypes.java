package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = Bus.MOD)
public class ModEntityTypes 
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = 
			DeferredRegister.create(ForgeRegistries.ENTITIES, BetterEnd.MOD_ID);

	public static final RegistryObject<EntityType<EndFishEntity>> END_FISH = ENTITY_TYPES.register("end_fish", 
			() -> EntityType.Builder.<EndFishEntity>create(EndFishEntity::new, EntityClassification.WATER_AMBIENT).
			size(0.5f, 0.5f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "end_fish").toString()));
	
	public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ENTITY_TYPES.register("dragonfly", 
			() -> EntityType.Builder.<DragonflyEntity>create(DragonflyEntity::new, EntityClassification.AMBIENT).
			size(0.6f, 0.5f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "dragonfly").toString()));
	
	public static final RegistryObject<EntityType<ShadowWalkerEntity>> SHADOW_WALKER = ENTITY_TYPES.register("shadow_walker", 
			() -> EntityType.Builder.<ShadowWalkerEntity>create(ShadowWalkerEntity::new, EntityClassification.MONSTER).
			size(0.6f, 1.95f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "shadow_walker").toString()));
	
	public static final RegistryObject<EntityType<EndSlimeEntity>> END_SLIME = ENTITY_TYPES.register("end_slime", 
			() -> EntityType.Builder.<EndSlimeEntity>create(EndSlimeEntity::new, EntityClassification.MONSTER).
			size(2, 2).
			build(new ResourceLocation(BetterEnd.MOD_ID, "end_slime").toString()));
	
	public static final RegistryObject<EntityType<CubozoaEntity>> CUBOZOA = ENTITY_TYPES.register("cubozoa", 
			() -> EntityType.Builder.<CubozoaEntity>create(CubozoaEntity::new, EntityClassification.WATER_AMBIENT).
			size(0.6f, 1f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "cubozoa").toString()));
	
	public static final RegistryObject<EntityType<SilkMothEntity>> SILK_MOTH = ENTITY_TYPES.register("silk_moth", 
			() -> EntityType.Builder.<SilkMothEntity>create(SilkMothEntity::new, EntityClassification.AMBIENT).
			size(0.6F, 0.6F).
			build(new ResourceLocation(BetterEnd.MOD_ID, "silk_moth").toString()));

	
	@SubscribeEvent
	public static void registerGlobalEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntityTypes.END_FISH.get(), EndFishEntity.registerAttributes().create());
		event.put(ModEntityTypes.DRAGONFLY.get(), DragonflyEntity.registerAttributes().create());
		event.put(ModEntityTypes.SHADOW_WALKER.get(), ShadowWalkerEntity.registerAttributes().create());
		event.put(ModEntityTypes.END_SLIME.get(), EndSlimeEntity.registerAttributes().create());
		event.put(ModEntityTypes.CUBOZOA.get(), CubozoaEntity.registerAttributes().create());
		event.put(ModEntityTypes.SILK_MOTH.get(), SilkMothEntity.registerAttributes().create());
	}
	
	public static void registerEntitySpawns()
	{
		EntitySpawnPlacementRegistry.register(ModEntityTypes.END_FISH.get(), 
				EntitySpawnPlacementRegistry.PlacementType.IN_WATER, 
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndFishEntity::canSpawn);
		
		EntitySpawnPlacementRegistry.register(ModEntityTypes.DRAGONFLY.get(), 
				EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, 
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DragonflyEntity::canSpawn);
		
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SHADOW_WALKER.get(), 
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, 
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ShadowWalkerEntity::canSpawn);
		
		EntitySpawnPlacementRegistry.register(ModEntityTypes.END_SLIME.get(), 
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, 
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndSlimeEntity::canSpawn);
		
		EntitySpawnPlacementRegistry.register(ModEntityTypes.CUBOZOA.get(), 
				EntitySpawnPlacementRegistry.PlacementType.IN_WATER, 
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CubozoaEntity::canSpawn);
		
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SILK_MOTH.get(), 
				EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, 
				Heightmap.Type.MOTION_BLOCKING, SilkMothEntity::canSpawn);
	}
}
