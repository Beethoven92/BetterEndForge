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
		ScreenManager.register(ModContainerTypes.END_STONE_SMELTER.get(), EndStoneSmelterScreen::new);
	}
	
	private void setRenderLayers()
	{
		// BLOCKS
		RenderTypeLookup.setRenderLayer(ModBlocks.AURORA_CRYSTAL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.RESPAWN_OBELISK.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.SULPHUR_CRYSTAL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.EMERALD_ICE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.DENSE_EMERALD_ICE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.IRON_CHANDELIER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GOLD_CHANDELIER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_STONE_FURNACE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SMARAGDANT_CRYSTAL_SHARD.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_STONE_STALACTITE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get(), RenderType.cutout());
		
		// TREES
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_LEAF.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_STEM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LOTUS_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MOSSY_GLOWSHROOM_FUR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LACUGROVE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LACUGROVE_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PYTHADENDRON_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PYTHADENDRON_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.DRAGON_TREE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.DRAGON_TREE_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_FLOWERS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TENANEA_OUTER_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HELIX_TREE_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HELIX_TREE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_TREE_SAPLING.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_TREE_MEMBRANE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.JELLYSHROOM_CAP_PURPLE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMARANITA_HYMENOPHORE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMARANITA_FUR.get(), RenderType.cutout());
		
		// PLANTS
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_MOSS_TALL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CREEPING_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHORUS_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMBER_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADOW_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_BUSH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_VINE_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_VINE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_VINE_FUR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BUBBLE_CORAL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CYAN_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TAIL_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.DENSE_VINE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_VINE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LILY_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.END_LILY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MURKWEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NEEDLEGRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADOW_BERRY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BULB_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BUSHY_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BULB_VINE_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BULB_VINE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MENGER_SPONGE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.MENGER_SPONGE_WET.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_RED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_PURPLE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_ORANGE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_LIGHT_BLUE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_CYAN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHARNIA_GREEN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TUBE_WORM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HYDRALUX_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HYDRALUX.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LANCELEAF.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LANCELEAF_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_LUMINOPHOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_ROOTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_PILLAR_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_UMBRELLA_MOSS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_VINE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_FERN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SMALL_JELLYSHROOM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOSSOM_BERRY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUMECORN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUMECORN_SEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOOMING_COOKSONIA.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SALTEAGO.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.VAIOLUSH_FERN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FRACTURN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AMBER_ROOT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHORUS_MUSHROOM.get(), RenderType.cutout());
//		RenderTypeLookup.setRenderLayer(ModBlocks.PEARLBERRY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LARGE_AMARANITA_MUSHROOM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SMALL_AMARANITA_MUSHROOM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GLOBULAGUS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CLAWFERN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FILALUX.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUCERNIA_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUCERNIA_OUTER_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUCERNIA_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FLAMAEA.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AERIDIUM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LAMELLARIUM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BOLUX_MUSHROOM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.AURANT_POLYPORE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POND_ANEMONE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.RUSCUS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.ORANGO.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LUTEBUS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NEON_CACTUS.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_FULL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_HEAVY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_THIN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PALLIDIUM_TINY.get(), RenderType.cutout());

		// SKY PLANTS
		RenderTypeLookup.setRenderLayer(ModBlocks.FILALUX_WINGS.get(), RenderType.cutout());
		
		// MISC
		RenderTypeLookup.setRenderLayer(ModBlocks.END_PORTAL_BLOCK.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.SILK_MOTH_NEST.get(), RenderType.cutout());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.IRON_BULB_LANTERN.get(), RenderType.cutout());
		for (Block bulbLantern : ModBlocks.IRON_BULB_LANTERN_COLORED.getBlocks())
			RenderTypeLookup.setRenderLayer(bulbLantern, RenderType.cutout());

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
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.ladder.get(), RenderType.cutout());
	}
	
	private void setStoneMaterialRenderLayers(StoneMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.furnace.get(), RenderType.cutout());
	}
	
	private void setMetalMaterialRenderLayers(MetalMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.chain.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.bars.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.chandelier.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(material.bulb_lantern.get(), RenderType.cutout());
		for (Block bulbLantern : material.bulb_lantern_colored.getBlocks())
			RenderTypeLookup.setRenderLayer(bulbLantern, RenderType.cutout());
	}
	
	private void setFlowerPotRenderLayers()
	{
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof FlowerPotBlock)
			{
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
		});
	}
}
