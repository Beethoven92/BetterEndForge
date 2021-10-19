package mod.beethoven92.betterendforge.data;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.TerrainBlock;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ModItemTagsProvider extends ItemTagsProvider
{
	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) 
	{
		super(dataGenerator, blockTagProvider, BetterEnd.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerTags() 
	{
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof TerrainBlock)
			{
				getOrCreateBuilder(Tags.Items.END_STONES).add(block.asItem());
			}
			if (block instanceof LeavesBlock)
			{
				getOrCreateBuilder(ItemTags.LEAVES).add(block.asItem());
			}
			if (block instanceof EndSaplingBlock)
			{
				getOrCreateBuilder(ItemTags.SAPLINGS).add(block.asItem());
			}
			if (block instanceof EndCropBlock || block instanceof PlantBlockWithAge || block instanceof UnderwaterPlantBlockWithAge)
			{
				getOrCreateBuilder(Tags.Items.SEEDS).add(block.asItem());
			}
		});
		
		// Misc Forge tags
		getOrCreateBuilder(Tags.Items.DUSTS).add(ModItems.ENDER_DUST.get());

		getOrCreateBuilder(Tags.Items.INGOTS).add(ModItems.AETERNIUM_INGOT.get());
		
		getOrCreateBuilder(Tags.Items.MUSHROOMS).add(ModBlocks.BOLUX_MUSHROOM.get().asItem());
		getOrCreateBuilder(Tags.Items.MUSHROOMS).add(ModItems.CHORUS_MUSHROOM_RAW.get());
		
		getOrCreateBuilder(Tags.Items.ORES).add(ModBlocks.ENDER_ORE.get().asItem());
		getOrCreateBuilder(Tags.Items.ORES).add(ModBlocks.AMBER_ORE.get().asItem());
		
		getOrCreateBuilder(Tags.Items.SEEDS).add(ModBlocks.END_LOTUS_SEED.get().asItem());
		
		getOrCreateBuilder(Tags.Items.STORAGE_BLOCKS).add(ModBlocks.AETERNIUM_BLOCK.get().asItem());
		getOrCreateBuilder(Tags.Items.STORAGE_BLOCKS).add(ModBlocks.AMBER_BLOCK.get().asItem());
		
		getOrCreateBuilder(ItemTags.createOptional(frl("cooked_fishes"))).add(ModItems.END_FISH_COOKED.get());
		getOrCreateBuilder(ItemTags.createOptional(frl("fruits"))).add(ModItems.BLOSSOM_BERRY.get(), ModItems.SHADOW_BERRY_RAW.get());
		getOrCreateBuilder(ItemTags.createOptional(frl("ice"))).add(ModBlocks.ANCIENT_EMERALD_ICE.get().asItem(), ModBlocks.DENSE_EMERALD_ICE.get().asItem(), ModBlocks.EMERALD_ICE.get().asItem());
		getOrCreateBuilder(ItemTags.createOptional(frl("raw_fishes"))).add(ModItems.END_FISH_RAW.get());
		getOrCreateBuilder(ItemTags.createOptional(frl("saplings"))).add(ModBlocks.DRAGON_TREE_SAPLING.get().asItem(), ModBlocks.HELIX_TREE_SAPLING.get().asItem(), ModBlocks.HYDRALUX_SAPLING.get().asItem(), ModBlocks.LACUGROVE_SAPLING.get().asItem(), ModBlocks.LUCERNIA_SAPLING.get().asItem(), ModBlocks.MOSSY_GLOWSHROOM_SAPLING.get().asItem(), ModBlocks.PYTHADENDRON_SAPLING.get().asItem(), ModBlocks.TENANEA_SAPLING.get().asItem(), ModBlocks.UMBRELLA_TREE_SAPLING.get().asItem());
		getOrCreateBuilder(ItemTags.createOptional(frl("vegetables"))).add(ModItems.AMBER_ROOT_RAW.get());
		
		// Misc Minecraft tags
		getOrCreateBuilder(ItemTags.ANVIL).add(ModBlocks.AETERNIUM_ANVIL.get().asItem());
		
		getOrCreateBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.AETERNIUM_INGOT.get());
		
		getOrCreateBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_HAMMER.get());
		
		getOrCreateBuilder(ItemTags.SLABS).add(ModBlocks.DRAGON_BONE_SLAB.get().asItem());
		getOrCreateBuilder(ItemTags.SLABS).add(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_SLABS).add(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(ModBlocks.DRAGON_BONE_STAIRS.get().asItem());
		getOrCreateBuilder(ItemTags.STAIRS).add(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_STAIRS).add(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get().asItem());
		
		// Mod Tags
		getOrCreateBuilder(ModTags.HAMMERS).add(ModItems.IRON_HAMMER.get());
		getOrCreateBuilder(ModTags.HAMMERS).add(ModItems.GOLDEN_HAMMER.get());
		getOrCreateBuilder(ModTags.HAMMERS).add(ModItems.DIAMOND_HAMMER.get());
		getOrCreateBuilder(ModTags.HAMMERS).add(ModItems.NETHERITE_HAMMER.get());
		getOrCreateBuilder(ModTags.HAMMERS).add(ModItems.AETERNIUM_HAMMER.get());
		
		getOrCreateBuilder(ModTags.FURNACES).add(Blocks.FURNACE.asItem());
		getOrCreateBuilder(ModTags.FURNACES).add(ModBlocks.END_STONE_FURNACE.get().asItem());
		
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

		
		// METAL MATERIALS
		registerMetalMaterialTags(ModBlocks.THALLASIUM);
		registerMetalMaterialTags(ModBlocks.TERMINITE);
	}
	
	private ResourceLocation frl(String tag) {
		return new ResourceLocation(ForgeVersion.MOD_ID, tag);
	}

	private void registerWoodenMaterialTags(WoodenMaterial material)
	{
		this.copy(material.logBlockTag, material.logItemTag);
	
		getOrCreateBuilder(ItemTags.PLANKS).add(material.planks.get().asItem());
		
		getOrCreateBuilder(ItemTags.LOGS).add(material.log.get().asItem(), material.bark.get().asItem(), material.log_stripped.get().asItem(), material.bark_stripped.get().asItem());
		
		getOrCreateBuilder(ItemTags.LOGS_THAT_BURN).add(material.log.get().asItem(), material.bark.get().asItem(), material.log_stripped.get().asItem(), material.bark_stripped.get().asItem());
		
		getOrCreateBuilder(ItemTags.BUTTONS).add(material.button.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_BUTTONS).add(material.button.get().asItem());
		
		getOrCreateBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(material.pressurePlate.get().asItem());
		
		getOrCreateBuilder(ItemTags.DOORS).add(material.door.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_DOORS).add(material.door.get().asItem());
		
		getOrCreateBuilder(ItemTags.FENCES).add(material.fence.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_FENCES).add(material.fence.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_SLABS).add(material.slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_STAIRS).add(material.stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.TRAPDOORS).add(material.trapdoor.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_TRAPDOORS).add(material.trapdoor.get().asItem());
		
		getOrCreateBuilder(ItemTags.SIGNS).add(material.sign.get().asItem());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.FENCES).add(material.fence.get().asItem());
		getOrCreateBuilder(Tags.Items.FENCES_WOODEN).add(material.fence.get().asItem());

		getOrCreateBuilder(Tags.Items.FENCE_GATES).add(material.gate.get().asItem());
		getOrCreateBuilder(Tags.Items.FENCE_GATES_WOODEN).add(material.gate.get().asItem());
		
		getOrCreateBuilder(Tags.Items.CHESTS).add(material.chest.get().asItem());
		getOrCreateBuilder(Tags.Items.CHESTS_WOODEN).add(material.chest.get().asItem());
		
		getOrCreateBuilder(Tags.Items.BOOKSHELVES).add(material.shelf.get().asItem());
		getOrCreateBuilder(ItemTags.createOptional(frl("workbench"))).add(material.craftingTable.get().asItem());
		
		// Used by the Metal Barrels mod
		getOrCreateBuilder(ModTags.ITEM_BARRELS).add(material.barrel.get().asItem());
	}
	
	private void registerStoneMaterialTags(StoneMaterial material)
	{
		getOrCreateBuilder(ItemTags.STONE_BRICKS).add(material.bricks.get().asItem());
		
		getOrCreateBuilder(ItemTags.WALLS).add(material.wall.get().asItem());
		getOrCreateBuilder(ItemTags.WALLS).add(material.brick_wall.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		getOrCreateBuilder(ItemTags.SLABS).add(material.brick_slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		getOrCreateBuilder(ItemTags.STAIRS).add(material.brick_stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(material.stone.get().asItem());
		getOrCreateBuilder(ItemTags.STONE_TOOL_MATERIALS).add(material.stone.get().asItem());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.STONE).add(material.stone.get().asItem());
		
		// Mod Tags
		getOrCreateBuilder(ModTags.FURNACES).add(material.furnace.get().asItem());
	}
	
	private void registerMetalMaterialTags(MetalMaterial material)
	{
		getOrCreateBuilder(ItemTags.DOORS).add(material.door.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.TRAPDOORS).add(material.trapdoor.get().asItem());
		
		getOrCreateBuilder(ItemTags.ANVIL).add(material.anvil.get().asItem());
		
		getOrCreateBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(material.ingot.get());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.NUGGETS).add(material.nugget.get());
		
		getOrCreateBuilder(Tags.Items.INGOTS).add(material.ingot.get());
		
		if (material.hasOre)
		{
			getOrCreateBuilder(Tags.Items.ORES).add(material.ore.get().asItem());
		}
		
		getOrCreateBuilder(Tags.Items.STORAGE_BLOCKS).add(material.block.get().asItem());
		
		// Mod Tags
		getOrCreateBuilder(ModTags.HAMMERS).add(material.hammer.get());
	}
}
