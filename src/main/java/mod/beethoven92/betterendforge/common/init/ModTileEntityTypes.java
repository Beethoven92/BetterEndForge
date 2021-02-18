package mod.beethoven92.betterendforge.common.init;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.template.EndFurnaceBlock;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EndBarrelTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EndFurnaceTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.HydrothermalVentTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.InfusionPedestalTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.item.BlockItem;
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
							getPedestals()).build(null));
	
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
	
	public static final RegistryObject<TileEntityType<EChestTileEntity>> CHEST =
			TILE_ENTITY_TYPES.register("chest", 
					() -> TileEntityType.Builder.create(EChestTileEntity::new, 
							getChests()).build(null));
	
	public static final RegistryObject<TileEntityType<ESignTileEntity>> SIGN =
			TILE_ENTITY_TYPES.register("sign", 
					() -> TileEntityType.Builder.create(ESignTileEntity::new, 
							getSigns()).build(null));
	
	public static final RegistryObject<TileEntityType<EndBarrelTileEntity>> BARREL =
			TILE_ENTITY_TYPES.register("barrel", 
					() -> TileEntityType.Builder.create(EndBarrelTileEntity::new, 
							getBarrels()).build(null));
	
	public static final RegistryObject<TileEntityType<EndFurnaceTileEntity>> FURNACE =
			TILE_ENTITY_TYPES.register("furnace", 
					() -> TileEntityType.Builder.create(EndFurnaceTileEntity::new, 
							getFurnaces()).build(null));
	
	static Block[] getPedestals() 
	{
		List<Block> result = new ArrayList<>();
		for (StoneMaterial m : StoneMaterial.getMaterials())
			result.add(m.pedestal.get());
		result.add(ModBlocks.PURPUR_PEDESTAL.get());
		result.add(ModBlocks.QUARTZ_PEDESTAL.get());
		result.add(ModBlocks.ANDESITE_PEDESTAL.get());
		result.add(ModBlocks.DIORITE_PEDESTAL.get());
		result.add(ModBlocks.GRANITE_PEDESTAL.get());
		return result.toArray(new Block[] {});
	}
	
	static Block[] getChests() 
	{
		List<Block> result = Lists.newArrayList();
		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) 
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof ChestBlock) 
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[] {});
	}
	
	static Block[] getSigns() 
	{
		List<Block> result = Lists.newArrayList();
		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) 
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof EndSignBlock) 
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[] {});
	}
	
	static Block[] getBarrels() 
	{
		List<Block> result = Lists.newArrayList();
		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) 
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof BarrelBlock) 
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[] {});
	}
	
	static Block[] getFurnaces() 
	{
		List<Block> result = Lists.newArrayList();
		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) 
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof EndFurnaceBlock) 
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[] {});
	}
}
