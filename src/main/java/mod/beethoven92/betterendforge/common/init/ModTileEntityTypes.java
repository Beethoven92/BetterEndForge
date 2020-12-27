package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.HydrothermalVentTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.InfusionPedestalTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes 
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
			DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BetterEnd.MOD_ID);

	public static final RegistryObject<TileEntityType<PedestalTileEntity>> PEDESTAL =
			TILE_ENTITY_TYPES.register("pedestal_tile_entity", 
					() -> TileEntityType.Builder.create(PedestalTileEntity::new, 
							ModBlocks.PURPUR_PEDESTAL.get(), ModBlocks.QUARTZ_PEDESTAL.get()).build(null));
	
	public static final RegistryObject<TileEntityType<EternalPedestalTileEntity>> ETERNAL_PEDESTAL =
			TILE_ENTITY_TYPES.register("eternal_pedestal_tile_entity", 
					() -> TileEntityType.Builder.create(EternalPedestalTileEntity::new, 
							ModBlocks.ETERNAL_PEDESTAL.get()).build(null));
	
	public static final RegistryObject<TileEntityType<InfusionPedestalTileEntity>> INFUSION_PEDESTAL =
			TILE_ENTITY_TYPES.register("infusion_pedestal_tile_entity", 
					() -> TileEntityType.Builder.create(InfusionPedestalTileEntity::new, 
							ModBlocks.INFUSION_PEDESTAL.get()).build(null));
	
	public static final RegistryObject<TileEntityType<EndStoneSmelterTileEntity>> END_STONE_SMELTER =
			TILE_ENTITY_TYPES.register("end_stone_smelter_tile_entity", 
					() -> TileEntityType.Builder.create(EndStoneSmelterTileEntity::new, 
							ModBlocks.END_STONE_SMELTER.get()).build(null));
	
	public static final RegistryObject<TileEntityType<HydrothermalVentTileEntity>> HYDROTHERMAL_VENT =
			TILE_ENTITY_TYPES.register("hydrothermal_vent_tile_entity", 
					() -> TileEntityType.Builder.create(HydrothermalVentTileEntity::new, 
							ModBlocks.HYDROTHERMAL_VENT.get()).build(null));
}
