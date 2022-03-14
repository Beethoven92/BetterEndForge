package mod.beethoven92.betterendforge.data;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.TerrainBlock;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ModBlockTagsProvider extends BlockTagsProvider
{
	public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) 
	{
		super(generatorIn, BetterEnd.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() 
	{
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof TerrainBlock)
			{
				tag(BlockTags.NYLIUM).add(block);
				tag(Tags.Blocks.END_STONES).add(block);
			}
			if (block instanceof LeavesBlock)
			{
				tag(BlockTags.LEAVES).add(block);
			}
			if (block instanceof EndVineBlock)
			{
				tag(BlockTags.CLIMBABLE).add(block);
			}
			if (block instanceof EndSaplingBlock)
			{
				tag(BlockTags.SAPLINGS).add(block);
			}
			if (block instanceof FlowerPotBlock)
			{
				tag(BlockTags.FLOWER_POTS).add(block);
			}
		});
				
		// Misc Forge tags
		tag(Tags.Blocks.ORES).add(ModBlocks.ENDER_ORE.get());
		tag(Tags.Blocks.ORES).add(ModBlocks.AMBER_ORE.get());
		
		tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.AETERNIUM_BLOCK.get());
		tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.AMBER_BLOCK.get());
		
		// Misc Minecraft tags
		tag(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.AETERNIUM_BLOCK.get());
		
		tag(BlockTags.ICE).add(ModBlocks.EMERALD_ICE.get());
		tag(BlockTags.ICE).add(ModBlocks.DENSE_EMERALD_ICE.get());
		tag(BlockTags.ICE).add(ModBlocks.ANCIENT_EMERALD_ICE.get());
		
		tag(BlockTags.ANVIL).add(ModBlocks.AETERNIUM_ANVIL.get());
		
		tag(BlockTags.SLABS).add(ModBlocks.DRAGON_BONE_SLAB.get());
		tag(BlockTags.SLABS).add(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get());
		tag(BlockTags.WOODEN_SLABS).add(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get());

		tag(BlockTags.STAIRS).add(ModBlocks.DRAGON_BONE_STAIRS.get());
		tag(BlockTags.STAIRS).add(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get());
		tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get());
		
		// WOODEN MATERIALS
		registerWoodenMaterialTags(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialTags(ModBlocks.LACUGROVE);
		registerWoodenMaterialTags(ModBlocks.END_LOTUS);
		registerWoodenMaterialTags(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialTags(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialTags(ModBlocks.TENANEA);
		registerWoodenMaterialTags(ModBlocks.HELIX_TREE);
		registerWoodenMaterialTags(ModBlocks.UMBRELLA_TREE);
		registerWoodenMaterialTags(ModBlocks.JELLYSHROOM);
		registerWoodenMaterialTags(ModBlocks.LUCERNIA);
		
		// STONE MATERIALS
		registerStoneMaterialTags(ModBlocks.FLAVOLITE);
		registerStoneMaterialTags(ModBlocks.VIOLECITE);
		registerStoneMaterialTags(ModBlocks.SULPHURIC_ROCK);
		registerStoneMaterialTags(ModBlocks.VIRID_JADESTONE);
		registerStoneMaterialTags(ModBlocks.AZURE_JADESTONE);
		registerStoneMaterialTags(ModBlocks.SANDY_JADESTONE);
		registerStoneMaterialTags(ModBlocks.UMBRALITH);
		
		// METAL MATERIALS
		registerMetalMaterialTags(ModBlocks.THALLASIUM);
		registerMetalMaterialTags(ModBlocks.TERMINITE);
	}
	
	private void registerWoodenMaterialTags(WoodenMaterial material)
	{
		tag(material.logBlockTag).add(material.log.get(), material.bark.get(), material.log_stripped.get(), material.bark_stripped.get());
		
		tag(BlockTags.PLANKS).add(material.planks.get());
		
		tag(BlockTags.LOGS).add(material.log.get(), material.bark.get(), material.log_stripped.get(), material.bark_stripped.get());
		
		tag(BlockTags.LOGS_THAT_BURN).add(material.log.get(), material.bark.get(), material.log_stripped.get(), material.bark_stripped.get());
		
		tag(BlockTags.BUTTONS).add(material.button.get());
		tag(BlockTags.WOODEN_BUTTONS).add(material.button.get());
		
		tag(BlockTags.PRESSURE_PLATES).add(material.pressurePlate.get());
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(material.pressurePlate.get());
		
		tag(BlockTags.DOORS).add(material.door.get());
		tag(BlockTags.WOODEN_DOORS).add(material.door.get());
		
		tag(BlockTags.FENCES).add(material.fence.get());
		tag(BlockTags.WOODEN_FENCES).add(material.fence.get());
		
		tag(BlockTags.SLABS).add(material.slab.get());
		tag(BlockTags.WOODEN_SLABS).add(material.slab.get());
		
		tag(BlockTags.STAIRS).add(material.stairs.get());
		tag(BlockTags.WOODEN_STAIRS).add(material.stairs.get());

		tag(BlockTags.TRAPDOORS).add(material.trapdoor.get());
		tag(BlockTags.WOODEN_TRAPDOORS).add(material.trapdoor.get());
		
		tag(BlockTags.SIGNS).add(material.sign.get());
		
		tag(BlockTags.CLIMBABLE).add(material.ladder.get());
		
		tag(BlockTags.GUARDED_BY_PIGLINS).add(material.chest.get());	
		tag(BlockTags.GUARDED_BY_PIGLINS).add(material.barrel.get());
		
		// Forge Tags
		tag(Tags.Blocks.FENCES).add(material.fence.get());
		tag(Tags.Blocks.FENCES_WOODEN).add(material.fence.get());

		tag(Tags.Blocks.FENCE_GATES).add(material.gate.get());
		tag(Tags.Blocks.FENCE_GATES_WOODEN).add(material.gate.get());
		
		tag(Tags.Blocks.CHESTS).add(material.chest.get());
		tag(Tags.Blocks.CHESTS_WOODEN).add(material.chest.get());
		
		tag(Tags.Blocks.CHESTS_WOODEN).add(material.chest.get());
		
		tag(BlockTags.createOptional(frl("workbench"))).add(material.craftingTable.get());
		
		// Used by the Metal Barrels mod
		tag(ModTags.BLOCK_BARRELS).add(material.barrel.get());
	}
	
	private ResourceLocation frl(String tag) {
		return new ResourceLocation(ForgeVersion.MOD_ID, tag);
	}
	
	private void registerStoneMaterialTags(StoneMaterial material)
	{
		tag(BlockTags.STONE_BRICKS).add(material.bricks.get());
		
		tag(BlockTags.WALLS).add(material.wall.get());
		tag(BlockTags.WALLS).add(material.brick_wall.get());
		
		tag(BlockTags.SLABS).add(material.slab.get());
		tag(BlockTags.SLABS).add(material.brick_slab.get());
		
		tag(BlockTags.STAIRS).add(material.stairs.get());
		tag(BlockTags.STAIRS).add(material.brick_stairs.get());
		
		tag(BlockTags.PRESSURE_PLATES).add(material.pressure_plate.get());
		tag(BlockTags.STONE_PRESSURE_PLATES).add(material.pressure_plate.get());
		
		// Forge Tags
		tag(Tags.Blocks.STONE).add(material.stone.get());
	}
	
	private void registerMetalMaterialTags(MetalMaterial material)
	{	
		tag(BlockTags.BEACON_BASE_BLOCKS).add(material.block.get());
		
		tag(BlockTags.DOORS).add(material.door.get());
		
		tag(BlockTags.STAIRS).add(material.stairs.get());

		tag(BlockTags.TRAPDOORS).add(material.trapdoor.get());
		
		tag(BlockTags.SLABS).add(material.slab.get());
		
		tag(BlockTags.PRESSURE_PLATES).add(material.pressure_plate.get());
		
		tag(BlockTags.ANVIL).add(material.anvil.get());
		
		// Forge Tags
		if (material.hasOre)
		{
			tag(Tags.Blocks.ORES).add(material.ore.get());
		}
		
		tag(Tags.Blocks.STORAGE_BLOCKS).add(material.block.get());
	}
}
