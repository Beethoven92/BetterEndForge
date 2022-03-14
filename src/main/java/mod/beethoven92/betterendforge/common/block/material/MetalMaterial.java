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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
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
	
	public static MetalMaterial makeNormal(String name, MaterialColor color, Tier material, ArmorMaterial armor) 
	{
		return new MetalMaterial(name, true, BlockBehaviour.Properties.of(Material.METAL, color).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public static MetalMaterial makeNormal(String name, MaterialColor color, float hardness, float resistance, Tier material, ArmorMaterial armor) 
	{
		return new MetalMaterial(name, true, BlockBehaviour.Properties.of(Material.METAL, color).requiresCorrectToolForDrops().strength(hardness, resistance).sound(SoundType.METAL), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public static MetalMaterial makeOreless(String name, MaterialColor color, Tier material, ArmorMaterial armor)
	{
		return new MetalMaterial(name, false, BlockBehaviour.Properties.of(Material.METAL, color).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public static MetalMaterial makeOreless(String name, MaterialColor color, float hardness, float resistance, Tier material, ArmorMaterial armor) {
		return new MetalMaterial(name, false, BlockBehaviour.Properties.of(Material.METAL, color).requiresCorrectToolForDrops().strength(hardness, resistance).sound(SoundType.METAL), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB), material, armor);
	}
	
	public MetalMaterial(String name, boolean hasOre, BlockBehaviour.Properties blockSettings, Item.Properties itemSettings, Tier material, ArmorMaterial armor) 
	{		
		this.hasOre = hasOre;
		
		this.name = name;
		
		final int anvilLevel = material.getLevel();
		
		ore = hasOre ? ModBlocks.registerBlockWithDefaultItem(name + "_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE))) : null;
		block = ModBlocks.registerBlockWithDefaultItem(name + "_block", () -> new Block(blockSettings));
		tile = ModBlocks.registerBlockWithDefaultItem(name + "_tile", () -> new Block(blockSettings));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", () -> new StairBlock(() -> tile.get().defaultBlockState(), blockSettings));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(blockSettings));
		door = ModBlocks.registerBlockWithDefaultItem(name + "_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(block.get()).noOcclusion()));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(block.get()).noOcclusion()));
		anvil = ModBlocks.registerBlock(name + "_anvil", () -> new EndAnvilBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, block.get().defaultMaterialColor()).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL), anvilLevel));
		bars = ModBlocks.registerBlockWithDefaultItem(name + "_bars", () -> new MetalPaneBlock(blockSettings.strength(5.0F, 6.0F).noOcclusion()));
		chain = ModBlocks.registerBlockWithDefaultItem(name + "_chain", () -> new ChainBlock(BlockBehaviour.Properties.of(Material.METAL, block.get().defaultMaterialColor()).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion()));
		pressure_plate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, blockSettings));
		
		ModItems.registerItem(name + "_anvil", () -> new EndAnvilItem(anvil.get(), itemSettings));

		
		chandelier = ModBlocks.registerBlockWithDefaultItem(name + "_chandelier", () -> new ChandelierBlock(BlockBehaviour.Properties.copy(block.get()).noOcclusion().noCollission().requiresCorrectToolForDrops().lightLevel((state) -> 15)));
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
		shovel = ModItems.registerItem(name + "_shovel", () -> new ShovelItem(material, 1.5F, -3.0F, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		sword = ModItems.registerItem(name + "_sword", () -> new SwordItem(material, 3, -2.4F, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		pickaxe = ModItems.registerItem(name + "_pickaxe", () -> new PickaxeItem(material, 1, -2.8F, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		axe = ModItems.registerItem(name + "_axe", () -> new AxeItem(material, 6.0F, -3.0F, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		hoe = ModItems.registerItem(name + "_hoe", () -> new HoeItem(material, -3, 0.0F, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		hammer = ModItems.registerItem(name + "_hammer", () -> new HammerItem(material, 5.0F, -3.2F, 0.3D, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		
		helmet = ModItems.registerItem(name + "_helmet", () -> new ArmorItem(armor, EquipmentSlot.HEAD, itemSettings));
		chestplate = ModItems.registerItem(name + "_chestplate", () -> new ArmorItem(armor, EquipmentSlot.CHEST, itemSettings));
		leggings = ModItems.registerItem(name + "_leggings", () -> new ArmorItem(armor, EquipmentSlot.LEGS, itemSettings));
		boots = ModItems.registerItem(name + "_boots", () -> new ArmorItem(armor, EquipmentSlot.FEET, itemSettings));
	}
}
