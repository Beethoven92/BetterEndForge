package mod.beethoven92.betterendforge.common.block.material;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

import mod.beethoven92.betterendforge.common.block.ModLanternBlock;
import mod.beethoven92.betterendforge.common.block.template.EndFurnaceBlock;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.block.template.PillarBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.RegistryObject;

public class StoneMaterial 
{
	private static final List<StoneMaterial> MATERIALS = new ArrayList<>();
	
	public final String name;
	
	public final RegistryObject<Block> stone;
	
	public final RegistryObject<Block> polished;
	public final RegistryObject<Block> tiles;
	public final RegistryObject<Block> pillar;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> wall;
	public final RegistryObject<Block> button;
	public final RegistryObject<Block> pressure_plate;
	public final RegistryObject<Block> pedestal;
	public final RegistryObject<Block> lantern;
	
	public final RegistryObject<Block> bricks;
	public final RegistryObject<Block> brick_stairs;
	public final RegistryObject<Block> brick_slab;
	public final RegistryObject<Block> brick_wall;
	
	public final RegistryObject<Block> furnace;
	
	public StoneMaterial(String name, MaterialColor color) 
	{
		this.name = name;
		
		BlockBehaviour.Properties material = BlockBehaviour.Properties.of(Material.STONE, color).
				                                                     requiresCorrectToolForDrops().
				                                                     strength(3.0F, 9.0F);
		
		stone = ModBlocks.registerBlockWithDefaultItem(name, 
				() -> new Block(material));
		polished = ModBlocks.registerBlockWithDefaultItem(name + "_polished", 
				() -> new Block(material));
		tiles = ModBlocks.registerBlockWithDefaultItem(name + "_tiles", 
				() -> new Block(material));
		pillar = ModBlocks.registerBlockWithDefaultItem(name + "_pillar", 
				() -> new PillarBlockTemplate(material));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", 
				() -> new StairBlock(() -> stone.get().defaultBlockState(), material));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", 
				() -> new SlabBlock(material));
		wall = ModBlocks.registerBlockWithDefaultItem(name + "_wall", 
				() -> new WallBlock(material));
		button = ModBlocks.registerBlockWithDefaultItem(name + "_button", 
				() -> new StoneButtonBlock(material));
		pressure_plate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", 
				() -> new PressurePlateBlock(Sensitivity.MOBS, material));
		pedestal = ModBlocks.registerBlockWithDefaultItem(name + "_pedestal", 
				() -> new PedestalBlock(BlockBehaviour.Properties.of(Material.STONE, color).
                        requiresCorrectToolForDrops().
                        strength(3.0F, 9.0F).
                        lightLevel(PedestalBlock.light())));
		lantern = ModBlocks.registerBlockWithDefaultItem(name + "_lantern", 
				() -> new ModLanternBlock(BlockBehaviour.Properties.of(Material.STONE, color).
                        requiresCorrectToolForDrops().
                        strength(3.0F, 9.0F).
                        lightLevel(s -> 15)));
		
		bricks = ModBlocks.registerBlockWithDefaultItem(name + "_bricks", 
				() -> new Block(material));
		brick_stairs = ModBlocks.registerBlockWithDefaultItem(name + "_bricks_stairs", 
				() -> new StairBlock(() -> bricks.get().defaultBlockState(), material));
		brick_slab = ModBlocks.registerBlockWithDefaultItem(name + "_bricks_slab", 
				() -> new SlabBlock(material));
		brick_wall = ModBlocks.registerBlockWithDefaultItem(name + "_bricks_wall", 
				() -> new WallBlock(material));
		
		furnace = ModBlocks.registerBlockWithDefaultItem(name + "_furnace", 
				() -> new EndFurnaceBlock(BlockBehaviour.Properties.copy(bricks.get()).
						lightLevel((state) -> {return state.getValue(BlockStateProperties.LIT) ? 13 : 0;})));
		
		MATERIALS.add(this);
	}
	
	public static Iterable<StoneMaterial> getMaterials() {
		return Iterables.unmodifiableIterable(MATERIALS);
	}
}
