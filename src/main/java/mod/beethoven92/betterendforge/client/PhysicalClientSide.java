package mod.beethoven92.betterendforge.client;

import mod.beethoven92.betterendforge.IPhysicalSide;
import mod.beethoven92.betterendforge.client.gui.EndStoneSmelterScreen;
import mod.beethoven92.betterendforge.client.renderer.CubozoaRenderer;
import mod.beethoven92.betterendforge.client.renderer.DragonflyEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.EndChestTileEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.EndFishEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.EndSignTileEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.EndSlimeEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.PedestalRenderer;
import mod.beethoven92.betterendforge.client.renderer.ShadowWalkerEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.SilkMothEntityRenderer;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModContainerTypes;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PhysicalClientSide implements IPhysicalSide
{
	@Override
	public void setup(IEventBus modEventBus, IEventBus forgeEventBus) 
	{
		modEventBus.addListener(this::clientSetup);
	}
	
	private void clientSetup(FMLClientSetupEvent event)
	{
		registerRenderers();
		registerGUIs();
		setRenderLayers();
	}
		
	private void registerRenderers()
	{
		// Tile entity renderers
		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.ETERNAL_PEDESTAL.get(), PedestalRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.INFUSION_PEDESTAL.get(), PedestalRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.PEDESTAL.get(), PedestalRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.CHEST.get(), EndChestTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.SIGN.get(), EndSignTileEntityRenderer::new);
		
		// Entity renderers
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.END_FISH.get(), EndFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DRAGONFLY.get(), DragonflyEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHADOW_WALKER.get(), ShadowWalkerEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.END_SLIME.get(), EndSlimeEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CUBOZOA.get(), CubozoaRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SILK_MOTH.get(), SilkMothEntityRenderer::new);
	}
	
	private void registerGUIs()
	{	
		ScreenManager.registerFactory(ModContainerTypes.END_STONE_SMELTER.get(), EndStoneSmelterScreen::new);
	}
	
	private void setRenderLayers()
	{
		// BLOCKS
		RenderTypeLookup.setRenderLayer(ModBlocks.AURORA_CRYSTAL.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.RESPAWN_OBELISK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.SULPHUR_CRYSTAL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.EMERALD_ICE.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.DENSE_EMERALD_ICE.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.IRON_CHANDELIER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GOLD_CHANDELIER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_STONE_FURNACE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SMARAGDANT_CRYSTAL_SHARD.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_STONE_STALACTITE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get(), RenderType.getCutout());
		
		// TREES
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_LEAF.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_STEM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_FLOWER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MOSSY_GLOWSHROOM_FUR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LACUGROVE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LACUGROVE_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PYTHADENDRON_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PYTHADENDRON_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.DRAGON_TREE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.DRAGON_TREE_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_FLOWERS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_OUTER_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HELIX_TREE_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HELIX_TREE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_TREE_SAPLING.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_TREE_MEMBRANE.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.JELLYSHROOM_CAP_PURPLE.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMARANITA_HYMENOPHORE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMARANITA_FUR.get(), RenderType.getCutout());
		
		// PLANTS
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_MOSS_TALL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CREEPING_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHORUS_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMBER_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADOW_PLANT.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_BUSH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_VINE_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_VINE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_VINE_FUR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BUBBLE_CORAL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CYAN_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TAIL_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.DENSE_VINE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_VINE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LILY_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LILY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MURKWEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NEEDLEGRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADOW_BERRY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BULB_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BUSHY_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BULB_VINE_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BULB_VINE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MENGER_SPONGE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MENGER_SPONGE_WET.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_RED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_PURPLE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_ORANGE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_LIGHT_BLUE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_CYAN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_GREEN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TUBE_WORM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HYDRALUX_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HYDRALUX.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LANCELEAF.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LANCELEAF_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_LUMINOPHOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_ROOTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_UMBRELLA_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_VINE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_FERN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SMALL_JELLYSHROOM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOSSOM_BERRY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUMECORN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUMECORN_SEED.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOOMING_COOKSONIA.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SALTEAGO.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.VAIOLUSH_FERN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FRACTURN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMBER_ROOT.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHORUS_MUSHROOM.get(), RenderType.getCutout());
//		RenderTypeLookup.setRenderLayer(ModBlocks.PEARLBERRY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LARGE_AMARANITA_MUSHROOM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SMALL_AMARANITA_MUSHROOM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOBULAGUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CLAWFERN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FILALUX.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUCERNIA_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUCERNIA_OUTER_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUCERNIA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FLAMAEA.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AERIDIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LAMELLARIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BOLUX_MUSHROOM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AURANT_POLYPORE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POND_ANEMONE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.RUSCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.ORANGO.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUTEBUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NEON_CACTUS.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_FULL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_HEAVY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_THIN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_TINY.get(), RenderType.getCutout());

		// SKY PLANTS
		RenderTypeLookup.setRenderLayer(ModBlocks.FILALUX_WINGS.get(), RenderType.getCutout());
		
		// MISC
		RenderTypeLookup.setRenderLayer(ModBlocks.END_PORTAL_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.SILK_MOTH_NEST.get(), RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.IRON_BULB_LANTERN.get(), RenderType.getCutout());
		for (Block bulbLantern : ModBlocks.IRON_BULB_LANTERN_COLORED.getBlocks())
			RenderTypeLookup.setRenderLayer(bulbLantern, RenderType.getCutout());

		// FLOWER POTS
		setFlowerPotRenderLayers();
		
		// WOODEN MATERIALS
		setWoodenMaterialRenderLayers(ModBlocks.MOSSY_GLOWSHROOM);
		setWoodenMaterialRenderLayers(ModBlocks.LACUGROVE);
		setWoodenMaterialRenderLayers(ModBlocks.END_LOTUS);
		setWoodenMaterialRenderLayers(ModBlocks.PYTHADENDRON);
		setWoodenMaterialRenderLayers(ModBlocks.DRAGON_TREE);
		setWoodenMaterialRenderLayers(ModBlocks.TENANEA);
		setWoodenMaterialRenderLayers(ModBlocks.HELIX_TREE);
		setWoodenMaterialRenderLayers(ModBlocks.UMBRELLA_TREE);
		setWoodenMaterialRenderLayers(ModBlocks.JELLYSHROOM);
		setWoodenMaterialRenderLayers(ModBlocks.LUCERNIA);
		
		// STONE MATERIALS
		setStoneMaterialRenderLayers(ModBlocks.FLAVOLITE);
		setStoneMaterialRenderLayers(ModBlocks.VIOLECITE);
		setStoneMaterialRenderLayers(ModBlocks.SULPHURIC_ROCK);
		setStoneMaterialRenderLayers(ModBlocks.VIRID_JADESTONE);
		setStoneMaterialRenderLayers(ModBlocks.AZURE_JADESTONE);
		setStoneMaterialRenderLayers(ModBlocks.SANDY_JADESTONE);
		setStoneMaterialRenderLayers(ModBlocks.UMBRALITH);
		
		// METAL MATERIALS
		setMetalMaterialRenderLayers(ModBlocks.THALLASIUM);
		setMetalMaterialRenderLayers(ModBlocks.TERMINITE);
	}
	
	private void setWoodenMaterialRenderLayers(WoodenMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.ladder.get(), RenderType.getCutout());
	}
	
	private void setStoneMaterialRenderLayers(StoneMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.furnace.get(), RenderType.getCutout());
	}
	
	private void setMetalMaterialRenderLayers(MetalMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.chain.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.bars.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.chandelier.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.bulb_lantern.get(), RenderType.getCutout());
		for (Block bulbLantern : material.bulb_lantern_colored.getBlocks())
			RenderTypeLookup.setRenderLayer(bulbLantern, RenderType.getCutout());
	}
	
	private void setFlowerPotRenderLayers()
	{
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof FlowerPotBlock)
			{
				RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
			}
		});
	}
}
