package mod.beethoven92.betterendforge.client;

import mod.beethoven92.betterendforge.IPhysicalSide;
import mod.beethoven92.betterendforge.client.gui.EndStoneSmelterScreen;
import mod.beethoven92.betterendforge.client.renderer.DragonflyEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.EndFishEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.EndSlimeEntityRenderer;
import mod.beethoven92.betterendforge.client.renderer.PedestalRenderer;
import mod.beethoven92.betterendforge.client.renderer.ShadowWalkerEntityRenderer;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModContainerTypes;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
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
		
		// Entity renderers
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.END_FISH.get(), EndFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DRAGONFLY.get(), DragonflyEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHADOW_WALKER.get(), ShadowWalkerEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.END_SLIME.get(), EndSlimeEntityRenderer::new);
	}
	
	private void registerGUIs()
	{	
		ScreenManager.registerFactory(ModContainerTypes.END_STONE_SMELTER.get(), EndStoneSmelterScreen::new);
	}
	
	private void setRenderLayers()
	{
		// BLOCKS
		RenderTypeLookup.setRenderLayer(ModBlocks.AURORA_CRYSTAL.get(), RenderType.getTranslucent());
		
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
		// PLANTS
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.UMBRELLA_MOSS_TALL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CREEPING_MOSS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CHORUS_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_GRASS.get(), RenderType.getCutout());
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
		RenderTypeLookup.setRenderLayer(ModBlocks.BUSHY_GRASS.get(), RenderType.getCutout());
		// MISC
		RenderTypeLookup.setRenderLayer(ModBlocks.END_PORTAL_BLOCK.get(), RenderType.getTranslucent());
		
		// WOODEN MATERIALS
		setWoodenMaterialRenderLayers(ModBlocks.MOSSY_GLOWSHROOM);
		setWoodenMaterialRenderLayers(ModBlocks.LACUGROVE);
		setWoodenMaterialRenderLayers(ModBlocks.END_LOTUS);
		setWoodenMaterialRenderLayers(ModBlocks.PYTHADENDRON);
		setWoodenMaterialRenderLayers(ModBlocks.DRAGON_TREE);
		setWoodenMaterialRenderLayers(ModBlocks.TENANEA);
	}
	
	private void setWoodenMaterialRenderLayers(WoodenMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.getCutout());
	}
}
