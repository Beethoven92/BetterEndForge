package mod.beethoven92.betterendforge.client.event;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.AuroraCrystalBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;

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
	}
	
	@SubscribeEvent
	public static void ItemColorHandler(ColorHandlerEvent.Item event)
	{
		event.getItemColors().register((stack, tintIndex) -> 
		                 {return AuroraCrystalBlock.getItemColor();}, ModBlocks.AURORA_CRYSTAL.get());
	}
}
