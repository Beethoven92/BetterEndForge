package mod.beethoven92.betterendforge.common.block.material;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.item.HammerItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;

public class MetalMaterial 
{
	public final RegistryObject<Block> ore;
	public final RegistryObject<Block> block;
	public final RegistryObject<Block> tile;
	//public final RegistryObject<Block> bars;
	public final RegistryObject<Block> pressure_plate;
	public final RegistryObject<Block> door;
	public final RegistryObject<Block> trapdoor;
	//public final RegistryObject<Block> anvil;
	public final RegistryObject<Block> chain;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	
	//public final RegistryObject<Block> chandelier;
	//public final RegistryObject<Block> bulb_lantern;
	//public final ColoredMaterial bulb_lantern_colored;
	
	public final RegistryObject<Item> nugget;
	public final RegistryObject<Item> ingot;
	public final RegistryObject<Item> shovel;
	public final RegistryObject<Item> sword;
	public final RegistryObject<Item> pickaxe;
	public final RegistryObject<Item> axe;
	public final RegistryObject<Item> hoe;
	public final RegistryObject<Item> hammer;
	
	public final RegistryObject<Item> helmet;
	public final RegistryObject<Item> chestplate;
	public final RegistryObject<Item> leggings;
	public final RegistryObject<Item> boots;
	
	public final boolean hasOre;
	
	public final String name;
	
	public static MetalMaterial makeNormal(String name, MaterialColor color, IItemTier material, IArmorMaterial armor) 
	{
		return new MetalMaterial(name, true, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public static MetalMaterial makeNormal(String name, MaterialColor color, float hardness, float resistance, IItemTier material, IArmorMaterial armor) 
	{
		return new MetalMaterial(name, true, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(hardness, resistance).sound(SoundType.METAL), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public static MetalMaterial makeOreless(String name, MaterialColor color, IItemTier material, IArmorMaterial armor)
	{
		return new MetalMaterial(name, false, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public static MetalMaterial makeOreless(String name, MaterialColor color, float hardness, float resistance, IItemTier material, IArmorMaterial armor) {
		return new MetalMaterial(name, false, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(hardness, resistance).sound(SoundType.METAL), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	private MetalMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings, Item.Properties itemSettings, IItemTier material, IArmorMaterial armor) 
	{
		AbstractBlock.Properties lantern = blockSettings.sound(SoundType.LANTERN).hardnessAndResistance(1).setLightLevel((state) -> 15);
		
		this.hasOre = hasOre;
		
		this.name = name;
		
		ore = hasOre ? ModBlocks.registerBlockWithDefaultItem(name + "_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE))) : null;
		block = ModBlocks.registerBlockWithDefaultItem(name + "_block", () -> new Block(blockSettings));
		tile = ModBlocks.registerBlockWithDefaultItem(name + "_tile", () -> new Block(blockSettings));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", () -> new StairsBlock(() -> tile.get().getDefaultState(), blockSettings));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(blockSettings));
		door = ModBlocks.registerBlockWithDefaultItem(name + "_door", () -> new DoorBlock(blockSettings));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor", () -> new TrapDoorBlock(blockSettings));
		//anvil = ModBlocks.registerBlockWithDefaultItem(name + "_anvil", new AnvilBlock(block.getDefaultMaterialColor()));
		//bars = ModBlocks.registerBlock(name + "_bars", new EndMetalPaneBlock(block));
		chain = ModBlocks.registerBlockWithDefaultItem(name + "_chain", () -> new ChainBlock(AbstractBlock.Properties.create(Material.IRON, block.get().getMaterialColor()).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.CHAIN).notSolid()));
		pressure_plate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, blockSettings));
		
		//chandelier = ModBlocks.registerBlock(name + "_chandelier", new ChandelierBlock(block));
		//bulb_lantern = ModBlocks.registerBlockWithDefaultItem(name + "_bulb_lantern", () -> new BulbVineLanternBlock(lantern));
		//bulb_lantern_colored = new ColoredMaterial(BulbVineLanternColoredBlock::new, bulb_lantern, false);
		
		nugget = ModItems.registerItem(name + "_nugget", () -> new Item(itemSettings));
		ingot = ModItems.registerItem(name + "_ingot", () -> new Item(itemSettings));
		shovel = ModItems.registerItem(name + "_shovel", () -> new ShovelItem(material, 1.5F, -3.0F, itemSettings));
		sword = ModItems.registerItem(name + "_sword", () -> new SwordItem(material, 3, -2.4F, itemSettings));
		pickaxe = ModItems.registerItem(name + "_pickaxe", () -> new PickaxeItem(material, 1, -2.8F, itemSettings));
		axe = ModItems.registerItem(name + "_axe", () -> new AxeItem(material, 6.0F, -3.0F, itemSettings));
		hoe = ModItems.registerItem(name + "_hoe", () -> new HoeItem(material, -3, 0.0F, itemSettings));
		hammer = ModItems.registerItem(name + "_hammer", () -> new HammerItem(material, 5.0F, -3.2F, 0.3D, itemSettings));
		
		helmet = ModItems.registerItem(name + "_helmet", () -> new ArmorItem(armor, EquipmentSlotType.HEAD, itemSettings));
		chestplate = ModItems.registerItem(name + "_chestplate", () -> new ArmorItem(armor, EquipmentSlotType.CHEST, itemSettings));
		leggings = ModItems.registerItem(name + "_leggings", () -> new ArmorItem(armor, EquipmentSlotType.LEGS, itemSettings));
		boots = ModItems.registerItem(name + "_boots", () -> new ArmorItem(armor, EquipmentSlotType.FEET, itemSettings));
		
		/*if (hasOre) {
			FurnaceRecipe.make(name + "_ingot_furnace", ore, ingot).setGroup("end_ingot").build(true);
			AlloyingRecipe.Builder.create(name + "_ingot_alloy").setInput(ore, ore).setOutput(ingot, 3).setExpiriense(2.1F).build();
		}*/
		
		/*GridRecipe.make(name + "_ingot_from_nuggets", ingot).setShape("###", "###", "###").addMaterial('#', nugget).setGroup("end_metal_ingots_nug").build();
		GridRecipe.make(name + "_block", block).setShape("###", "###", "###").addMaterial('#', ingot).setGroup("end_metal_blocks").build();
		GridRecipe.make(name + "_ingot_from_block", ingot).setOutputCount(9).setList("#").addMaterial('#', block).setGroup("end_metal_ingots").build();
		
		GridRecipe.make(name + "_tile", tile).setOutputCount(4).setShape("##", "##").addMaterial('#', block).setGroup("end_metal_tiles").build();
		GridRecipe.make(name + "_bars", bars).setOutputCount(16).setShape("###", "###").addMaterial('#', ingot).setGroup("end_metal_bars").build();
		GridRecipe.make(name + "_plate", plate).setShape("##").addMaterial('#', ingot).setGroup("end_metal_plates").build();
		GridRecipe.make(name + "_door", door).setOutputCount(3).setOutputCount(16).setShape("##", "##", "##").addMaterial('#', ingot).setGroup("end_metal_doors").build();
		GridRecipe.make(name + "_trapdoor", trapdoor).setShape("##", "##").addMaterial('#', ingot).setGroup("end_metal_trapdoors").build();
		GridRecipe.make(name + "_stairs", stairs).setOutputCount(4).setShape("#  ", "## ", "###").addMaterial('#', block, tile).setGroup("end_metal_stairs").build();
		GridRecipe.make(name + "_slab", slab).setOutputCount(6).setShape("###").addMaterial('#', block, tile).setGroup("end_metal_slabs").build();
		GridRecipe.make(name + "_chain", chain).setShape("N", "#", "N").addMaterial('#', ingot).addMaterial('N', nugget).setGroup("end_metal_chain").build();
		GridRecipe.make(name + "_anvil", anvil).setOutputCount(3).setShape("###", " I ", "III").addMaterial('#', block, tile).addMaterial('I', ingot).setGroup("end_metal_anvil").build();
		GridRecipe.make(name + "bulb_lantern", bulb_lantern).setShape("C", "I", "#").addMaterial('C', chain).addMaterial('I', ingot).addMaterial('#', ModItems.GLOWING_BULB).build();
		
		GridRecipe.make(name + "_axe", axe).setShape("##", "#I", " I").addMaterial('#', ingot).addMaterial('I', Items.STICK).setGroup("end_metal_axes").build();
		GridRecipe.make(name + "_hoe", hoe).setShape("##", " I", " I").addMaterial('#', ingot).addMaterial('I', Items.STICK).setGroup("end_metal_hoes").build();
		GridRecipe.make(name + "_pickaxe", pickaxe).setShape("###", " I ", " I ").addMaterial('#', ingot).addMaterial('I', Items.STICK).setGroup("end_metal_picks").build();
		GridRecipe.make(name + "_sword", sword).setShape("#", "#", "I").addMaterial('#', ingot).addMaterial('I', Items.STICK).setGroup("end_metal_swords").build();
		
		GridRecipe.make(name + "_chandelier", chandelier).setShape("I#I", " # ").addMaterial('#', ingot).addMaterial('I', ModItems.LUMECORN_ROD).setGroup("end_metal_chandelier").build();
		GridRecipe.make(name + "_hammer", hammer).setShape("III", "ISI", " S ").addMaterial('I', ingot).addMaterial('S', Items.STICK).setGroup("end_metal_hammers").build();
		
		FurnaceRecipe.make(name + "_axe_nugget", axe, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_hoe_nugget", hoe, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_pickaxe_nugget", pickaxe, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_sword_nugget", sword, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_hammer_nugget", hammer, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_helmet_nugget", helmet, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_chestplate_nugget", chestplate, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_leggings_nugget", leggings, nugget).setGroup("end_nugget").build(true);
		FurnaceRecipe.make(name + "_boots_nugget", boots, nugget).setGroup("end_nugget").build(true);
		
		TagHelper.addTag(BlockTags.ANVIL, anvil);*/
	}
}
