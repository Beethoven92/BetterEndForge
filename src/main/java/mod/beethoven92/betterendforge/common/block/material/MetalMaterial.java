package mod.beethoven92.betterendforge.common.block.material;

import mod.beethoven92.betterendforge.common.block.BulbVineLanternBlock;
import mod.beethoven92.betterendforge.common.block.template.ChandelierBlock;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.block.template.MetalPaneBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.item.EndAnvilItem;
import mod.beethoven92.betterendforge.common.item.HammerItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
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
	public final RegistryObject<Block> bars;
	public final RegistryObject<Block> pressure_plate;
	public final RegistryObject<Block> door;
	public final RegistryObject<Block> trapdoor;
	public final RegistryObject<Block> anvil;
	public final RegistryObject<Block> chain;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	
	public final RegistryObject<Block> chandelier;
	public final RegistryObject<Block> bulb_lantern;
	public final ColoredMaterial bulb_lantern_colored;
	
	public final RegistryObject<Item> nugget;
	public final RegistryObject<Item> ingot;
	
	public final RegistryObject<Item> shovelHead;
	public final RegistryObject<Item> pickaxeHead;
	public final RegistryObject<Item> axeHead;
	public final RegistryObject<Item> hoeHead;
	public final RegistryObject<Item> swordBlade;
	public final RegistryObject<Item> swordHandle;
	
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
	
	public MetalMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings, Item.Properties itemSettings, IItemTier material, IArmorMaterial armor) 
	{		
		this.hasOre = hasOre;
		
		this.name = name;
		
		final int anvilLevel = material.getHarvestLevel();
		
		ore = hasOre ? ModBlocks.registerBlockWithDefaultItem(name + "_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE))) : null;
		block = ModBlocks.registerBlockWithDefaultItem(name + "_block", () -> new Block(blockSettings));
		tile = ModBlocks.registerBlockWithDefaultItem(name + "_tile", () -> new Block(blockSettings));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", () -> new StairsBlock(() -> tile.get().getDefaultState(), blockSettings));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(blockSettings));
		door = ModBlocks.registerBlockWithDefaultItem(name + "_door", () -> new DoorBlock(AbstractBlock.Properties.from(block.get()).notSolid()));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.from(block.get()).notSolid()));
		anvil = ModBlocks.registerBlock(name + "_anvil", () -> new EndAnvilBlock(AbstractBlock.Properties.create(Material.ANVIL, block.get().getMaterialColor()).setRequiresTool().hardnessAndResistance(5.0F, 1200.0F).sound(SoundType.ANVIL), anvilLevel));
		bars = ModBlocks.registerBlockWithDefaultItem(name + "_bars", () -> new MetalPaneBlock(blockSettings.hardnessAndResistance(5.0F, 6.0F).notSolid()));
		chain = ModBlocks.registerBlockWithDefaultItem(name + "_chain", () -> new ChainBlock(AbstractBlock.Properties.create(Material.IRON, block.get().getMaterialColor()).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.CHAIN).notSolid()));
		pressure_plate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, blockSettings));
		
		ModItems.registerItem(name + "_anvil", () -> new EndAnvilItem(anvil.get(), itemSettings));

		
		chandelier = ModBlocks.registerBlockWithDefaultItem(name + "_chandelier", () -> new ChandelierBlock(AbstractBlock.Properties.from(block.get()).notSolid().doesNotBlockMovement().setRequiresTool().setLightLevel((state) -> 15)));
		bulb_lantern = ModBlocks.registerBlockWithDefaultItem(name + "_bulb_lantern", () -> new BulbVineLanternBlock(blockSettings));
		bulb_lantern_colored = new ColoredMaterial(name + "_bulb_lantern", () -> new BulbVineLanternBlock(), bulb_lantern, false);
		
		nugget = ModItems.registerItem(name + "_nugget", () -> new Item(itemSettings));
		ingot = ModItems.registerItem(name + "_ingot", () -> new Item(itemSettings));
		
		shovelHead = ModItems.registerItem(name + "_shovel_head", () -> new Item(itemSettings));
		pickaxeHead = ModItems.registerItem(name + "_pickaxe_head", () -> new Item(itemSettings));
		axeHead = ModItems.registerItem(name + "_axe_head", () -> new Item(itemSettings));
		hoeHead = ModItems.registerItem(name + "_hoe_head", () -> new Item(itemSettings));
		swordHandle = ModItems.registerItem(name + "_sword_handle", () -> new Item(itemSettings));
		swordBlade = ModItems.registerItem(name + "_sword_blade", () -> new Item(itemSettings));
		
		// FIX: cannot use the same item settings instance for every tool type here, 
		// because in each tool's constructor the respective tool type is added to the same Item.Properties
		shovel = ModItems.registerItem(name + "_shovel", () -> new ShovelItem(material, 1.5F, -3.0F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		sword = ModItems.registerItem(name + "_sword", () -> new SwordItem(material, 3, -2.4F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		pickaxe = ModItems.registerItem(name + "_pickaxe", () -> new PickaxeItem(material, 1, -2.8F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		axe = ModItems.registerItem(name + "_axe", () -> new AxeItem(material, 6.0F, -3.0F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		hoe = ModItems.registerItem(name + "_hoe", () -> new HoeItem(material, -3, 0.0F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		hammer = ModItems.registerItem(name + "_hammer", () -> new HammerItem(material, 5.0F, -3.2F, 0.3D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		
		helmet = ModItems.registerItem(name + "_helmet", () -> new ArmorItem(armor, EquipmentSlotType.HEAD, itemSettings));
		chestplate = ModItems.registerItem(name + "_chestplate", () -> new ArmorItem(armor, EquipmentSlotType.CHEST, itemSettings));
		leggings = ModItems.registerItem(name + "_leggings", () -> new ArmorItem(armor, EquipmentSlotType.LEGS, itemSettings));
		boots = ModItems.registerItem(name + "_boots", () -> new ArmorItem(armor, EquipmentSlotType.FEET, itemSettings));
	}
}
