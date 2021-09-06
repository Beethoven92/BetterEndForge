package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.data.InfusionRecipes;
import mod.beethoven92.betterendforge.data.ModBlockTagsProvider;
import mod.beethoven92.betterendforge.data.ModItemTagsProvider;
import mod.beethoven92.betterendforge.data.ModLootModifierProvider;
import mod.beethoven92.betterendforge.data.ModLootTableProvider;
import mod.beethoven92.betterendforge.data.ModRecipes;
import mod.beethoven92.betterendforge.data.client.ModBlockStates;
import mod.beethoven92.betterendforge.data.client.ModItemModels;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModData 
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator dataGenerator = event.getGenerator();
		
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		
		if (event.includeClient())
		{
			dataGenerator.addProvider(new ModItemModels(dataGenerator, existingFileHelper));
			
			dataGenerator.addProvider(new ModBlockStates(dataGenerator, existingFileHelper));
		}
		if (event.includeServer())
		{
			ModBlockTagsProvider blockTags = new ModBlockTagsProvider(dataGenerator, existingFileHelper);
			dataGenerator.addProvider(blockTags);
			dataGenerator.addProvider(new ModItemTagsProvider(dataGenerator, blockTags, existingFileHelper));
			
			dataGenerator.addProvider(new ModRecipes(dataGenerator));
			dataGenerator.addProvider(new InfusionRecipes(dataGenerator));
			
			dataGenerator.addProvider(new ModLootTableProvider(dataGenerator));
			dataGenerator.addProvider(new ModLootModifierProvider(dataGenerator));
		}
	}
}
