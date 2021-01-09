package mod.beethoven92.betterendforge.common.block.material;

import mod.beethoven92.betterendforge.common.block.template.BarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.PillarBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableBarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableLogBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.fml.RegistryObject;

// TO DO? Make all wooden blocks flammable so they can take and spread fire
public class WoodenMaterial 
{
	public final String name;
	
	public final RegistryObject<Block> log;
	public final RegistryObject<Block> bark;

	public final RegistryObject<Block> log_stripped;
	public final RegistryObject<Block> bark_stripped;

	public final RegistryObject<Block> planks;

	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> fence;
	public final RegistryObject<Block> gate;
	public final RegistryObject<Block> button;
	public final RegistryObject<Block> pressurePlate;
	public final RegistryObject<Block> trapdoor;
	public final RegistryObject<Block> door;
	
	//public final RegistryObject<Block> craftingTable;
	//public final RegistryObject<Block> ladder;
	//public final RegistryObject<Block> sign;
	
	//public final RegistryObject<Block> chest;
	//public final RegistryObject<Block> barrel;
	//public final RegistryObject<Block> shelf;
	public final RegistryObject<Block> composter;
	
	public final ITag.INamedTag<Block> logBlockTag;
	public final ITag.INamedTag<Item> logItemTag;
	
	public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) 
	{
		this.name = name;
		
		logBlockTag = ModTags.makeBlockTag(name + "_logs");
		logItemTag = ModTags.makeItemTag(name + "_logs");

		AbstractBlock.Properties materialPlanks = AbstractBlock.Properties.create(Material.WOOD, planksColor).
				                                                           hardnessAndResistance(2.0F, 3.0F).
				                                                           sound(SoundType.WOOD);
		AbstractBlock.Properties materialPlanksNotSolid = AbstractBlock.Properties.create(Material.WOOD, planksColor).
                hardnessAndResistance(2.0F, 3.0F).
                sound(SoundType.WOOD).
                notSolid();
		
		log_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_log", 
				() -> new PillarBlockTemplate(materialPlanks));
		bark_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_bark", 
				() -> new BarkBlockTemplate(materialPlanks));
		
		log = ModBlocks.registerBlockWithDefaultItem(name + "_log", 
				() -> new StripableLogBlockTemplate(woodColor, log_stripped.get()));
		bark = ModBlocks.registerBlockWithDefaultItem(name + "_bark", 
				() -> new StripableBarkBlockTemplate(woodColor, bark_stripped.get()));
		
		planks = ModBlocks.registerBlockWithDefaultItem(name + "_planks", 
				() -> new Block(materialPlanks));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", 
				() -> new StairsBlock(() -> planks.get().getDefaultState(), materialPlanks));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", 
				() -> new SlabBlock(materialPlanks));
		fence = ModBlocks.registerBlockWithDefaultItem(name + "_fence", 
				() -> new FenceBlock(materialPlanks));
		gate = ModBlocks.registerBlockWithDefaultItem(name + "_gate", 
				() -> new FenceGateBlock(materialPlanks));
		button = ModBlocks.registerBlockWithDefaultItem(name + "_button", 
				() -> new WoodButtonBlock(materialPlanks));
		pressurePlate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", 
				() -> new PressurePlateBlock(Sensitivity.EVERYTHING, materialPlanks));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor", 
				() -> new TrapDoorBlock(materialPlanksNotSolid));
		door = ModBlocks.registerBlockWithDefaultItem(name + "_door", 
				() -> new DoorBlock(materialPlanksNotSolid));
		
		composter = ModBlocks.registerBlockWithDefaultItem(name + "_composter", 
				() -> new ComposterBlock(materialPlanksNotSolid));
	}
	
	public boolean isTreeLog(Block block) 
	{
		return block == log.get() || block == bark.get();
	}
	
	public boolean isTreeLog(BlockState state) 
	{
		return isTreeLog(state.getBlock());
	}
}
