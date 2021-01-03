package mod.beethoven92.betterendforge.client.event;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.AuroraCrystalBlock;
import mod.beethoven92.betterendforge.common.block.HelixTreeLeavesBlock;
import mod.beethoven92.betterendforge.common.block.RespawnObeliskBlock;
import mod.beethoven92.betterendforge.common.block.TenaneaFlowersBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.item.BlockItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler 
{
	@SubscribeEvent
	public static void BlockColorHandler(ColorHandlerEvent.Block event)
	{
		event.getBlockColors().register((state, reader, pos, color) -> 
		                 {return AuroraCrystalBlock.getBlockColor(pos);}, ModBlocks.AURORA_CRYSTAL.get());
		
		event.getBlockColors().register((state, reader, pos, color) -> 
		                 {return TenaneaFlowersBlock.getBlockColor(pos);}, ModBlocks.TENANEA_FLOWERS.get());
		
		event.getBlockColors().register((state, reader, pos, color) -> 
        				 {return RespawnObeliskBlock.getBlockColor(pos);}, ModBlocks.RESPAWN_OBELISK.get());
		
		event.getBlockColors().register((state, reader, pos, color) -> 
		                 {return state.getBlock().getMaterialColor().colorValue;}, 
		                 ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED.getBlocks());

		event.getBlockColors().register((state, reader, pos, color) ->
		 				 {return HelixTreeLeavesBlock.getBlockColor(state);}, 
		 				 ModBlocks.HELIX_TREE_LEAVES.get());
	}
	
	@SubscribeEvent
	public static void ItemColorHandler(ColorHandlerEvent.Item event)
	{
		event.getItemColors().register((stack, tintIndex) -> 
		                 {return AuroraCrystalBlock.getItemColor();}, ModBlocks.AURORA_CRYSTAL.get());
		
		event.getItemColors().register((stack, tintIndex) -> 
                         {return TenaneaFlowersBlock.getItemColor();}, ModBlocks.TENANEA_FLOWERS.get());
		
		event.getItemColors().register((stack, tintIndex) -> 
        {return HelixTreeLeavesBlock.getItemColor();}, ModBlocks.HELIX_TREE_LEAVES.get());
		
		event.getItemColors().register((stack, color) -> {
			return ((SpawnEggItem) stack.getItem()).getColor(color);
		}, ModItems.DRAGONFLY_SPAWN_EGG.get(), ModItems.END_FISH_SPAWN_EGG.get(),
				ModItems.SHADOW_WALKER_SPAWN_EGG.get(), ModItems.END_SLIME_SPAWN_EGG.get(),
				ModItems.CUBOZOA_SPAWN_EGG.get());
		
		event.getItemColors().register((stack, tintIndex) -> 
                         {return ((BlockItem)stack.getItem()).getBlock().getMaterialColor().colorValue;}, 
                         ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED.getBlocks());
	}
}
