package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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
	
	
	public static void registerGlobalEntityAttributes()
	{
		GlobalEntityTypeAttributes.put(ModEntityTypes.END_FISH.get(), EndFishEntity.registerAttributes().create());
		GlobalEntityTypeAttributes.put(ModEntityTypes.DRAGONFLY.get(), DragonflyEntity.registerAttributes().create());
		GlobalEntityTypeAttributes.put(ModEntityTypes.SHADOW_WALKER.get(), ShadowWalkerEntity.registerAttributes().create());
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
	}
}
