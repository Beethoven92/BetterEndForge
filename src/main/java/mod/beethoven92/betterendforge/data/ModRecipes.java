package mod.beethoven92.betterendforge.data;

import java.util.function.Consumer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class ModRecipes extends RecipeProvider
{
	public ModRecipes(DataGenerator generatorIn) 
	{
		super(generatorIn);
	}
	
	private ResourceLocation rl(String s) {
		return new ResourceLocation(BetterEnd.MOD_ID, s);
	}
	
	@Override
	protected void buildShapelessRecipes(Consumer<FinishedRecipe> consumer) 
	{
		// BLOCKS
	    ShapedRecipeBuilder.shaped(ModBlocks.ENDER_BLOCK).define('#', Items.ENDER_PEARL).pattern("##").pattern("##").unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL)).save(consumer);
	    ShapelessRecipeBuilder.shapeless(Items.ENDER_PEARL, 4).requires(ModBlocks.ENDER_BLOCK).unlockedBy("has_ender_block", has(ModBlocks.ENDER_BLOCK)).save(consumer, rl("ender_pearl_from_ender_block"));
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.HYDRALUX_PETAL_BLOCK).define('#', ModItems.HYDRALUX_PETAL).pattern("##").pattern("##").unlockedBy("has_hydralux_petal", has(ModItems.HYDRALUX_PETAL)).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.AMBER_BLOCK).define('#', ModItems.AMBER_GEM).pattern("##").pattern("##").unlockedBy("has_amber_gem", has(ModItems.AMBER_GEM)).save(consumer);
	    ShapelessRecipeBuilder.shapeless(ModItems.AMBER_GEM, 4).requires(ModBlocks.AMBER_BLOCK).unlockedBy("has_amber_block", has(ModBlocks.AMBER_BLOCK)).save(consumer, rl("amber_gem_from_amber_block"));
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.AURORA_CRYSTAL).define('#', ModItems.CRYSTAL_SHARDS).pattern("##").pattern("##").unlockedBy("has_crystal_shard", has(ModItems.CRYSTAL_SHARDS)).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.END_LOTUS.log).define('#', ModBlocks.END_LOTUS_STEM.get()).pattern("##").pattern("##").unlockedBy("has_end_lotus_stem", has(ModBlocks.END_LOTUS_STEM.get())).save(consumer);

	    ShapedRecipeBuilder.shaped(ModBlocks.END_STONE_FURNACE.get()).define('#', Blocks.END_STONE).pattern("###").pattern("# #").pattern("###").group("end_stone_furnaces").unlockedBy("has_end_stone", has(Blocks.END_STONE)).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.END_STONE_SMELTER.get()).define('#', Blocks.END_STONE_BRICKS).define('V', ModTags.FURNACES).define('T', ModBlocks.THALLASIUM.ingot.get()).pattern("T#T").pattern("V V").pattern("T#T").unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS)).save(consumer);
	   
	    ShapedRecipeBuilder.shaped(ModBlocks.RESPAWN_OBELISK.get()).define('C', ModBlocks.AURORA_CRYSTAL.get()).define('S', ModItems.ETERNAL_CRYSTAL.get()).define('A', ModBlocks.AMBER_BLOCK.get()).pattern("CSC").pattern("CSC").pattern("AAA").unlockedBy("has_amber_block", has(ModBlocks.AMBER_BLOCK.get())).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.DENSE_EMERALD_ICE.get()).define('#', ModBlocks.EMERALD_ICE.get()).pattern("##").pattern("##").unlockedBy("has_emerald_ice", has(ModBlocks.EMERALD_ICE.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(ModBlocks.ANCIENT_EMERALD_ICE.get()).define('#', ModBlocks.DENSE_EMERALD_ICE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_dense_emerald_ice", has(ModBlocks.DENSE_EMERALD_ICE.get())).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.IRON_BULB_LANTERN.get()).define('C', Blocks.CHAIN).define('I', Items.IRON_INGOT).define('#', ModItems.GLOWING_BULB.get()).pattern("C").pattern("I").pattern("#").unlockedBy("has_glowing_bulb", has(ModItems.GLOWING_BULB.get())).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.IRON_CHANDELIER.get()).define('I', ModItems.LUMECORN_ROD.get()).define('#', Items.IRON_INGOT).pattern("I#I").pattern(" # ").group("end_metal_chandelier").unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
	    ShapedRecipeBuilder.shaped(ModBlocks.GOLD_CHANDELIER.get()).define('I', ModItems.LUMECORN_ROD.get()).define('#', Items.GOLD_INGOT).pattern("I#I").pattern(" # ").group("end_metal_chandelier").unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.MISSING_TILE.get(), 4).define('#', ModBlocks.VIOLECITE.stone.get()).define('P', Blocks.PURPUR_BLOCK).pattern("#P").pattern("P#").unlockedBy("has_violecite", has(ModBlocks.VIOLECITE.stone.get())).save(consumer);	    
	    
	    ShapedRecipeBuilder.shaped(ModBlocks.FILALUX_LANTERN.get()).define('#', ModBlocks.FILALUX.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_filalux", has(ModBlocks.FILALUX.get())).save(consumer);	    
	    
	    // DYES
		ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE).requires(ModBlocks.BLUE_VINE_SEED.get()).group("blue_dye").unlockedBy("has_blue_vine_seed", has(ModBlocks.BLUE_VINE_SEED.get())).save(consumer, rl("blue_dye_from_blue_vine_seed"));
		ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE).requires(ModBlocks.CREEPING_MOSS.get()).group("cyan_dye").unlockedBy("has_creeping_moss", has(ModBlocks.CREEPING_MOSS.get())).save(consumer, rl("cyan_dye_from_creeping_moss"));
		ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE).requires(ModBlocks.CYAN_MOSS.get()).group("cyan_dye").unlockedBy("has_cyan_moss", has(ModBlocks.CYAN_MOSS.get())).save(consumer, rl("cyan_dye_from_cyan_moss"));
		ShapelessRecipeBuilder.shapeless(Items.YELLOW_DYE).requires(ModBlocks.UMBRELLA_MOSS.get()).group("yellow_dye").unlockedBy("has_umbrella_moss", has(ModBlocks.UMBRELLA_MOSS.get())).save(consumer, rl("yellow_dye_from_umbrella_moss"));
		ShapelessRecipeBuilder.shapeless(Items.YELLOW_DYE, 2).requires(ModBlocks.UMBRELLA_MOSS_TALL.get()).group("yellow_dye").unlockedBy("has_umbrella_moss_tall", has(ModBlocks.UMBRELLA_MOSS_TALL.get())).save(consumer, rl("yellow_dye_from_umbrella_moss_tall"));
		ShapelessRecipeBuilder.shapeless(Items.BLACK_DYE).requires(ModBlocks.SHADOW_PLANT.get()).group("black_dye").unlockedBy("has_shadow_plant", has(ModBlocks.SHADOW_PLANT.get())).save(consumer, rl("black_dye_from_shadow_plant"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE).requires(ModBlocks.PURPLE_POLYPORE.get()).group("purple_dye").unlockedBy("has_purple_polypore", has(ModBlocks.PURPLE_POLYPORE.get())).save(consumer, rl("purple_dye_from_purple_polypore"));
		ShapelessRecipeBuilder.shapeless(Items.GRAY_DYE).requires(ModBlocks.TAIL_MOSS.get()).group("gray_dye").unlockedBy("has_tail_moss", has(ModBlocks.TAIL_MOSS.get())).save(consumer, rl("gray_dye_from_tail_moss"));
		ShapelessRecipeBuilder.shapeless(Items.MAGENTA_DYE).requires(ModBlocks.BUSHY_GRASS.get()).group("magenta_dye").unlockedBy("has_bushy_grass", has(ModBlocks.BUSHY_GRASS.get())).save(consumer, rl("magenta_dye_from_bushy_grass"));
		ShapelessRecipeBuilder.shapeless(Items.PINK_DYE).requires(ModBlocks.TWISTED_MOSS.get()).group("pink_dye").unlockedBy("has_twisted_moss", has(ModBlocks.TWISTED_MOSS.get())).save(consumer, rl("pink_dye_from_twisted_moss"));
		ShapelessRecipeBuilder.shapeless(Items.WHITE_DYE).requires(ModItems.HYDRALUX_PETAL.get()).group("white_dye").unlockedBy("has_hydralux_petal", has(ModItems.HYDRALUX_PETAL.get())).save(consumer, rl("white_dye_from_hydralux_petal"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE).requires(ModBlocks.TWISTED_UMBRELLA_MOSS.get()).group("purple_dye").unlockedBy("has_twisted_umbrella_moss", has(ModBlocks.TWISTED_UMBRELLA_MOSS.get())).save(consumer, rl("purple_dye_from_twisted_umbrella_moss"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE, 2).requires(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get()).group("purple_dye").unlockedBy("has_twisted_umbrella_moss_tall", has(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get())).save(consumer, rl("purple_dye_from_twisted_umbrella_moss_tall"));
		
		ShapelessRecipeBuilder.shapeless(Items.RED_DYE).requires(ModBlocks.CHARNIA_RED.get()).group("red_dye").unlockedBy("has_red_charnia", has(ModBlocks.CHARNIA_RED.get())).save(consumer, rl("red_dye_from_red_charnia"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE).requires(ModBlocks.CHARNIA_PURPLE.get()).group("purple_dye").unlockedBy("has_purple_charnia", has(ModBlocks.CHARNIA_PURPLE.get())).save(consumer, rl("purple_dye_from_purple_charnia"));
		ShapelessRecipeBuilder.shapeless(Items.ORANGE_DYE).requires(ModBlocks.CHARNIA_ORANGE.get()).group("orange_dye").unlockedBy("has_orange_charnia", has(ModBlocks.CHARNIA_ORANGE.get())).save(consumer, rl("orange_dye_from_orange_charnia"));
		ShapelessRecipeBuilder.shapeless(Items.LIGHT_BLUE_DYE).requires(ModBlocks.CHARNIA_LIGHT_BLUE.get()).group("light_blue_dye").unlockedBy("has_light_blue_charnia", has(ModBlocks.CHARNIA_LIGHT_BLUE.get())).save(consumer, rl("light_blue_dye_from_light_blue_charnia"));
		ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE).requires(ModBlocks.CHARNIA_CYAN.get()).group("cyan_dye").unlockedBy("has_cyan_charnia", has(ModBlocks.CHARNIA_CYAN.get())).save(consumer, rl("cyan_dye_from_cyan_charnia"));
		ShapelessRecipeBuilder.shapeless(Items.GREEN_DYE).requires(ModBlocks.CHARNIA_GREEN.get()).group("green_dye").unlockedBy("has_green_charnia", has(ModBlocks.CHARNIA_GREEN.get())).save(consumer, rl("green_dye_from_green_charnia"));
		
		// ITEMS
		ShapedRecipeBuilder.shaped(Items.PAPER, 3).define('#', ModItems.END_LILY_LEAF_DRIED.get()).pattern("###").unlockedBy("has_end_lily_leaf_dried", has(ModItems.END_LILY_LEAF_DRIED.get())).save(consumer, rl("paper_from_end_lily_leaf_dried"));
		ShapelessRecipeBuilder.shapeless(Items.STICK, 2).requires(ModBlocks.NEEDLEGRASS.get()).unlockedBy("has_needlegrass", has(ModBlocks.NEEDLEGRASS.get())).save(consumer, rl("stick_from_needlegrass"));
		ShapelessRecipeBuilder.shapeless(ModBlocks.SHADOW_BERRY.get(), 4).requires(ModItems.SHADOW_BERRY_RAW.get()).unlockedBy("has_shadow_berry_raw", has(ModItems.SHADOW_BERRY_RAW.get())).save(consumer, rl("shadow_berry_from_shadow_berry_raw"));
		ShapelessRecipeBuilder.shapeless(ModItems.SWEET_BERRY_JELLY.get()).requires(ModItems.GELATINE.get()).requires(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER).getItem()).requires(Items.SUGAR).requires(Items.SWEET_BERRIES).unlockedBy("has_gelatine", has(ModItems.GELATINE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(ModItems.SHADOW_BERRY_JELLY.get()).requires(ModItems.GELATINE.get()).requires(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER).getItem()).requires(Items.SUGAR).requires(ModItems.SHADOW_BERRY_COOKED.get()).unlockedBy("has_gelatine", has(ModItems.GELATINE.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(ModItems.AMBER_GEM.get()).define('#', ModItems.RAW_AMBER.get()).pattern("##").pattern("##").unlockedBy("has_raw_amber", has(ModItems.RAW_AMBER.get())).save(consumer);
	    ShapelessRecipeBuilder.shapeless(Items.GUNPOWDER).requires(ModItems.CRYSTALLINE_SULPHUR.get()).requires(ItemTags.COALS).requires(Items.BONE_MEAL).unlockedBy("has_crystalline_sulphur", has(ModItems.CRYSTALLINE_SULPHUR.get())).save(consumer, rl("gunpowder_from_sulphur"));
	    ShapedRecipeBuilder.shaped(ModItems.GUIDE_BOOK.get()).define('D', ModItems.ENDER_DUST.get()).define('B', Items.BOOK).define('C', ModItems.CRYSTAL_SHARDS.get()).pattern("D").pattern("B").pattern("C").unlockedBy("has_crystal_shards", has(ModItems.CRYSTAL_SHARDS.get())).save(consumer);
	    ShapelessRecipeBuilder.shapeless(ModItems.LEATHER_STRIPE.get(), 3).requires(Items.LEATHER).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer, rl("leather_to_stripes"));
	    ShapelessRecipeBuilder.shapeless(Items.LEATHER).requires(ModItems.LEATHER_STRIPE.get()).requires(ModItems.LEATHER_STRIPE.get()).requires(ModItems.LEATHER_STRIPE.get()).unlockedBy("has_leather_stripe", has(ModItems.LEATHER_STRIPE.get())).save(consumer, rl("stripes_to_leather"));
	    ShapelessRecipeBuilder.shapeless(ModItems.LEATHER_WRAPPED_STICK.get()).requires(Items.STICK).requires(ModItems.LEATHER_STRIPE.get()).unlockedBy("has_leather_stripe", has(ModItems.LEATHER_STRIPE.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(Items.ENDER_EYE).define('S', ModItems.CRYSTAL_SHARDS.get()).define('A', ModItems.AMBER_GEM.get()).define('P', Items.ENDER_PEARL).pattern("SAS").pattern("APA").pattern("SAS").unlockedBy("has_amber_gem", has(ModItems.AMBER_GEM.get())).save(consumer, rl("ender_eye_from_amber_gem"));
	    ShapedRecipeBuilder.shaped(Items.STRING, 6).define('#', ModItems.SILK_FIBER.get()).pattern("#").pattern("#").pattern("#").unlockedBy("has_silk_fiber", has(ModItems.SILK_FIBER.get())).save(consumer, rl("fiber_string"));
	    
	    // LANTERNS
	    registerLantern(ModBlocks.ANDESITE_LANTERN.get(), Blocks.ANDESITE_SLAB, consumer, "andesite");
	    registerLantern(ModBlocks.DIORITE_LANTERN.get(), Blocks.DIORITE_SLAB, consumer, "diorite");
	    registerLantern(ModBlocks.GRANITE_LANTERN.get(), Blocks.GRANITE_SLAB, consumer, "granite");
	    registerLantern(ModBlocks.QUARTZ_LANTERN.get(), Blocks.QUARTZ_SLAB, consumer, "quartz");
	    registerLantern(ModBlocks.PURPUR_LANTERN.get(), Blocks.PURPUR_SLAB, consumer, "purpur");
	    registerLantern(ModBlocks.END_STONE_LANTERN.get(), Blocks.END_STONE_BRICK_SLAB, consumer, "end_stone");
	    registerLantern(ModBlocks.BLACKSTONE_LANTERN.get(), Blocks.BLACKSTONE_SLAB, consumer, "blackstone");
	    
	    // PEDESTALS
		registerPedestal(ModBlocks.QUARTZ_PEDESTAL.get(), Blocks.QUARTZ_SLAB, Blocks.QUARTZ_PILLAR, consumer, "quartz");
		registerPedestal(ModBlocks.PURPUR_PEDESTAL.get(), Blocks.PURPUR_SLAB, Blocks.PURPUR_PILLAR, consumer, "purpur");
		registerPedestal(ModBlocks.ANDESITE_PEDESTAL.get(), Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_ANDESITE, consumer, "andesite");
		registerPedestal(ModBlocks.DIORITE_PEDESTAL.get(), Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_DIORITE, consumer, "diorite");
		registerPedestal(ModBlocks.GRANITE_PEDESTAL.get(), Blocks.POLISHED_GRANITE_SLAB, Blocks.POLISHED_GRANITE, consumer, "granite");
		
		ShapedRecipeBuilder.shaped(ModBlocks.INFUSION_PEDESTAL.get()).define('O', Items.ENDER_PEARL).define('Y', Items.ENDER_EYE).define('#', Blocks.OBSIDIAN).pattern(" Y ").pattern("O#O").pattern(" # ").unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL)).unlockedBy("has_ender_eye", has(Items.ENDER_EYE)).unlockedBy("has_obsidian", has(Blocks.OBSIDIAN)).save(consumer);
		
		// FURNACE
		cookFood(ModItems.END_FISH_RAW.get(), ModItems.END_FISH_COOKED.get(), 0.35F, 200, consumer);
		cookFood(ModItems.END_LILY_LEAF.get(), ModItems.END_LILY_LEAF_DRIED.get(), 0.35F, 200, consumer);
		cookFood(ModItems.SHADOW_BERRY_RAW.get(), ModItems.SHADOW_BERRY_COOKED.get(), 0.35F, 200, consumer);
		cookFood(ModItems.CHORUS_MUSHROOM_RAW.get(), ModItems.CHORUS_MUSHROOM_COOKED.get(), 0.35F, 200, consumer);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.ENDSTONE_DUST.get()), Blocks.GLASS.asItem(), 0.35F, 200).unlockedBy("has_end_stone_dust", has(ModBlocks.ENDSTONE_DUST.get())).save(consumer, rl("glass_from_end_stone_dust"));
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.JELLYSHROOM_CAP_PURPLE.get()), Items.SLIME_BALL, 0.35F, 200).unlockedBy("has_jellyshroom_cap", has(ModBlocks.JELLYSHROOM_CAP_PURPLE.get())).save(consumer, rl("slime_ball_from_jellyshroom_cap"));
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.MENGER_SPONGE_WET.get()), ModBlocks.MENGER_SPONGE.get(), 0.35F, 200).unlockedBy("has_menger_sponge_wet", has(ModBlocks.MENGER_SPONGE_WET.get())).save(consumer);
		
		// ARMORS AND TOOLS
		makeIngotAndBlockRecipes(ModBlocks.AETERNIUM_BLOCK.get(), ModItems.AETERNIUM_INGOT.get(), consumer, "aeternium");
		
		makeHammerRecipe(ModItems.GOLDEN_HAMMER.get(), Items.GOLD_INGOT, consumer, "gold");
		makeHammerRecipe(ModItems.IRON_HAMMER.get(), Items.IRON_INGOT, consumer, "iron");
		makeHammerRecipe(ModItems.DIAMOND_HAMMER.get(), Items.DIAMOND, consumer, "diamond");
		
		// SMITHING TABLE
		makeSmithingRecipe(ModItems.DIAMOND_HAMMER.get(), Items.NETHERITE_INGOT, ModItems.NETHERITE_HAMMER.get(), consumer);
		
		makeSmithingRecipe(ModBlocks.THALLASIUM.anvil.get().asItem(), ModBlocks.TERMINITE.block.get().asItem(), ModBlocks.TERMINITE.anvil.get().asItem(), consumer);
		makeSmithingRecipe(ModBlocks.TERMINITE.anvil.get().asItem(), ModBlocks.AETERNIUM_BLOCK.get().asItem(), ModBlocks.AETERNIUM_ANVIL.get().asItem(), consumer);
		
		makeSmithingRecipe(ModBlocks.TERMINITE.ingot.get(), ModItems.LEATHER_WRAPPED_STICK.get(), ModItems.AETERNIUM_SWORD_HANDLE.get(), consumer);
		makeSmithingRecipe(ModItems.AETERNIUM_SWORD_BLADE.get(), ModItems.AETERNIUM_SWORD_HANDLE.get(), ModItems.AETERNIUM_SWORD.get(), consumer);
		makeSmithingRecipe(ModItems.AETERNIUM_PICKAXE_HEAD.get(), ModItems.LEATHER_WRAPPED_STICK.get(), ModItems.AETERNIUM_PICKAXE.get(), consumer);
		makeSmithingRecipe(ModItems.AETERNIUM_AXE_HEAD.get(), ModItems.LEATHER_WRAPPED_STICK.get(), ModItems.AETERNIUM_AXE.get(), consumer);
		makeSmithingRecipe(ModItems.AETERNIUM_SHOVEL_HEAD.get(), ModItems.LEATHER_WRAPPED_STICK.get(), ModItems.AETERNIUM_SHOVEL.get(), consumer);
		makeSmithingRecipe(ModItems.AETERNIUM_HOE_HEAD.get(), ModItems.LEATHER_WRAPPED_STICK.get(), ModItems.AETERNIUM_HOE.get(), consumer);
		makeSmithingRecipe(ModItems.AETERNIUM_HAMMER_HEAD.get(), ModItems.LEATHER_WRAPPED_STICK.get(), ModItems.AETERNIUM_HAMMER.get(), consumer);
		makeSmithingRecipe(ModBlocks.TERMINITE.helmet.get(), ModItems.AETERNIUM_INGOT.get(), ModItems.AETERNIUM_HELMET.get(), consumer);
		makeSmithingRecipe(ModBlocks.TERMINITE.chestplate.get(), ModItems.AETERNIUM_INGOT.get(), ModItems.AETERNIUM_CHESTPLATE.get(), consumer);
		makeSmithingRecipe(ModBlocks.TERMINITE.leggings.get(), ModItems.AETERNIUM_INGOT.get(), ModItems.AETERNIUM_LEGGINGS.get(), consumer);
		makeSmithingRecipe(ModBlocks.TERMINITE.boots.get(), ModItems.AETERNIUM_INGOT.get(), ModItems.AETERNIUM_BOOTS.get(), consumer);
		
		
		// NEON CACTUS
	    ShapedRecipeBuilder.shaped(ModBlocks.NEON_CACTUS_BLOCK.get()).define('#', ModBlocks.NEON_CACTUS.get()).pattern("##").pattern("##").unlockedBy("has_neon_cactus", has(ModBlocks.NEON_CACTUS.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get(), 6).define('#', ModBlocks.NEON_CACTUS_BLOCK.get()).pattern("###").unlockedBy("has_neon_cactus_block", has(ModBlocks.NEON_CACTUS_BLOCK.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get(), 4).define('#', ModBlocks.NEON_CACTUS_BLOCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_neon_cactus_block", has(ModBlocks.NEON_CACTUS_BLOCK.get())).save(consumer);
		
		// WOODEN MATERIALS
		makeWoodenMaterialRecipes(ModBlocks.MOSSY_GLOWSHROOM, consumer);
		makeWoodenMaterialRecipes(ModBlocks.LACUGROVE, consumer);
		makeWoodenMaterialRecipes(ModBlocks.END_LOTUS, consumer);
		makeWoodenMaterialRecipes(ModBlocks.PYTHADENDRON, consumer);
		makeWoodenMaterialRecipes(ModBlocks.DRAGON_TREE, consumer);
		makeWoodenMaterialRecipes(ModBlocks.TENANEA, consumer);
		makeWoodenMaterialRecipes(ModBlocks.HELIX_TREE, consumer);
		makeWoodenMaterialRecipes(ModBlocks.UMBRELLA_TREE, consumer);
		makeWoodenMaterialRecipes(ModBlocks.JELLYSHROOM, consumer);
		makeWoodenMaterialRecipes(ModBlocks.LUCERNIA, consumer);
		
		// STONE MATERIALS
		makeStoneMaterialRecipes(ModBlocks.FLAVOLITE, consumer);
		makeStoneMaterialRecipes(ModBlocks.VIOLECITE, consumer);
		makeStoneMaterialRecipes(ModBlocks.SULPHURIC_ROCK, consumer);
		makeStoneMaterialRecipes(ModBlocks.VIRID_JADESTONE, consumer);
		makeStoneMaterialRecipes(ModBlocks.AZURE_JADESTONE, consumer);
		makeStoneMaterialRecipes(ModBlocks.SANDY_JADESTONE, consumer);
		makeStoneMaterialRecipes(ModBlocks.UMBRALITH, consumer);
		
		// METAL MATERIALS
		makeMetalMaterialRecipes(ModBlocks.THALLASIUM, consumer);
		makeMetalMaterialRecipes(ModBlocks.TERMINITE, consumer);
		
		// COLORED MATERIALS
		makeColoredMaterialRecipes(ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED, consumer);
		makeColoredMaterialRecipes(ModBlocks.IRON_BULB_LANTERN_COLORED, consumer);
	}
	
	private void makeWoodenMaterialRecipes(WoodenMaterial material, Consumer<FinishedRecipe> consumer)
	{
		ShapelessRecipeBuilder.shapeless(material.planks.get(), 4).requires(material.logItemTag).group("end_planks").unlockedBy("has_logs", has(material.logItemTag)).save(consumer);
		ShapedRecipeBuilder.shaped(material.stairs.get(), 4).define('#', material.planks.get()).pattern("#  ").pattern("## ").pattern("###").group("end_planks__stairs").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
		ShapedRecipeBuilder.shaped(material.slab.get(), 6).define('#', material.planks.get()).pattern("###").group("end_planks_slabs").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.fence.get(), 3).define('#', Items.STICK).define('W', material.planks.get()).pattern("W#W").pattern("W#W").group("end_planks_fences").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.gate.get()).define('#', Items.STICK).define('W', material.planks.get()).pattern("#W#").pattern("#W#").group("end_planks_gates").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapelessRecipeBuilder.shapeless(material.button.get()).requires(material.planks.get()).group("end_planks_buttons").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.pressurePlate.get()).define('#', material.planks.get()).pattern("##").group("end_planks_plates").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.trapdoor.get(), 2).define('#', material.planks.get()).pattern("###").pattern("###").group("end_trapdoors").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.door.get(), 3).define('#', material.planks.get()).pattern("##").pattern("##").pattern("##").group("end_doors").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.bark.get(), 3).define('#', material.log.get()).pattern("##").pattern("##").unlockedBy("has_log", has(material.log.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.bark_stripped.get(), 3).define('#', material.log_stripped.get()).pattern("##").pattern("##").unlockedBy("has_log_stripped", has(material.log_stripped.get())).save(consumer);
	    
	    ShapedRecipeBuilder.shaped(material.composter.get(), 1).define('#', material.slab.get()).pattern("# #").pattern("# #").pattern("###").group("end_composters").unlockedBy("has_slabs", has(material.slab.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.craftingTable.get(), 1).define('#', material.planks.get()).pattern("##").pattern("##").group("end_crafting_tables").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.ladder.get(), 3).define('#', material.planks.get()).define('I', Items.STICK).pattern("I I").pattern("I#I").pattern("I I").group("end_ladders").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.chest.get(), 1).define('#', material.planks.get()).pattern("###").pattern("# #").pattern("###").group("end_chests").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.sign.get(), 3).define('#', material.planks.get()).define('I', Items.STICK).pattern("###").pattern("###").pattern(" I ").group("end_signs").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.barrel.get(), 1).define('#', material.planks.get()).define('S', material.slab.get()).pattern("#S#").pattern("# #").pattern("#S#").group("end_barrels").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.shelf.get(), 1).define('#', material.planks.get()).define('P', Items.BOOK).pattern("###").pattern("PPP").pattern("###").group("end_bookshelves").unlockedBy("has_planks", has(material.planks.get())).save(consumer);
	}
	
	private void makeStoneMaterialRecipes(StoneMaterial material, Consumer<FinishedRecipe> consumer)
	{
		// Crafting
		ShapedRecipeBuilder.shaped(material.bricks.get(), 4).define('#', material.stone.get()).pattern("##").pattern("##").group("end_bricks").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);
		ShapedRecipeBuilder.shaped(material.polished.get(), 4).define('#', material.bricks.get()).pattern("##").pattern("##").group("end_tile").unlockedBy("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer);
		ShapedRecipeBuilder.shaped(material.tiles.get(), 4).define('#', material.polished.get()).pattern("##").pattern("##").group("end_small_tile").unlockedBy("has_" + material.polished.get().getRegistryName().getPath(), has(material.polished.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.pillar.get()).define('#', material.slab.get()).pattern("#").pattern("#").group("end_pillar").unlockedBy("has_" + material.slab.get().getRegistryName().getPath(), has(material.slab.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.stairs.get(), 4).define('#', material.stone.get()).pattern("#  ").pattern("## ").pattern("###").group("end_stone_stairs").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.slab.get(), 6).define('#', material.stone.get()).pattern("###").group("end_stone_slabs").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.brick_stairs.get(), 4).define('#', material.bricks.get()).pattern("#  ").pattern("## ").pattern("###").group("end_stone_stairs").unlockedBy("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.brick_slab.get(), 6).define('#', material.bricks.get()).pattern("###").group("end_stone_slabs").unlockedBy("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.wall.get(), 6).define('#', material.stone.get()).pattern("###").pattern("###").group("end_wall").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.brick_wall.get(), 6).define('#', material.bricks.get()).pattern("###").pattern("###").group("end_wall").unlockedBy("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer);
	    ShapelessRecipeBuilder.shapeless(material.button.get()).requires(material.stone.get()).group("end_stone_buttons").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.pressure_plate.get()).define('#', material.stone.get()).pattern("##").group("end_stone_plates").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);
	    registerLantern(material.lantern.get(), material.slab.get(), consumer, material.name);
	    registerPedestal(material.pedestal.get(), material.slab.get(), material.pillar.get(), consumer, material.name);
	    ShapedRecipeBuilder.shaped(material.furnace.get()).define('#', material.stone.get()).pattern("###").pattern("# #").pattern("###").group("end_stone_furnaces").unlockedBy("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer);

		// Stonecutting
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.bricks.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_bricks_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.polished.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_polished_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.tiles.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_tiles_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.pillar.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_pillar_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.stairs.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_stairs_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.slab.get(), 2).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_slab_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.brick_stairs.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.brick_slab.get(), 2).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.wall.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_wall_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.brick_wall.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.stone.get()), material.button.get()).unlocks("has_" + material.stone.get().getRegistryName().getPath(), has(material.stone.get())).save(consumer, rl(material.name + "_button_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.bricks.get()), material.brick_stairs.get()).unlocks("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_bricks_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.bricks.get()), material.brick_slab.get(), 2).unlocks("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_bricks_stonecutting"));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.bricks.get()), material.brick_wall.get()).unlocks("has_" + material.bricks.get().getRegistryName().getPath(), has(material.bricks.get())).save(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_bricks_stonecutting"));
	}
	
	private void makeMetalMaterialRecipes(MetalMaterial material, Consumer<FinishedRecipe> consumer)
	{
		// Base
	    makeIngotAndBlockRecipes(material.block.get(), material.ingot.get(), consumer, material.name);
	    ShapedRecipeBuilder.shaped(material.ingot.get()).define('#', material.nugget.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + material.name + "_nugget", has(material.nugget.get())).save(consumer, rl(material.name + "_ingot_from_" + material.name + "_nuggets"));
		
	    // Blocks
	    ShapedRecipeBuilder.shaped(material.tile.get(), 4).define('#', material.block.get()).pattern("##").pattern("##").group("end_metal_tile").unlockedBy("has_" + material.name + "_block", has(material.block.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.stairs.get(), 4).define('#', material.block.get()).pattern("#  ").pattern("## ").pattern("###").group("end_metal_stairs").unlockedBy("has_" + material.name + "_block", has(material.block.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.slab.get(), 6).define('#', material.block.get()).pattern("###").group("end_metal_slabs").unlockedBy("has_" + material.block + "_block", has(material.block.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.door.get(), 3).define('#', material.ingot.get()).pattern("##").pattern("##").pattern("##").group("end_metal_doors").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.trapdoor.get()).define('#', material.ingot.get()).pattern("##").pattern("##").group("end_metal_trapdoors").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.pressure_plate.get()).define('#', material.ingot.get()).pattern("##").group("end_metal_plates").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.bars.get(), 16).define('#', material.ingot.get()).pattern("###").pattern("###").group("end_metal_bars").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.chain.get()).define('N', material.nugget.get()).define('#', material.ingot.get()).pattern("N").pattern("#").pattern("N").group("end_metal_chain").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.anvil.get()).define('#', material.block.get()).define('I', material.ingot.get()).pattern("###").pattern(" I ").pattern("III").group("end_metal_anvil").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.chandelier.get()).define('I', ModItems.LUMECORN_ROD.get()).define('#', material.ingot.get()).pattern("I#I").pattern(" # ").group("end_metal_chandelier").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer);
	    ShapedRecipeBuilder.shaped(material.bulb_lantern.get()).define('C', Blocks.CHAIN).define('I', material.ingot.get()).define('#', ModItems.GLOWING_BULB.get()).pattern("C").pattern("I").pattern("#").unlockedBy("has_glowing_bulb", has(ModItems.GLOWING_BULB.get())).save(consumer);
	    makeColoredMaterialRecipes(material.bulb_lantern_colored, consumer);
	    ShapedRecipeBuilder.shaped(Blocks.SMITHING_TABLE).define('I', material.ingot.get()).define('#', ItemTags.PLANKS).pattern("II").pattern("##").pattern("##").unlockedBy("has_" + material.name + "_ingot", has(material.ingot.get())).save(consumer, rl("smithing_table_from_" + material.name + "_ingot"));
	    
	    // Furnace & blast furnace
	    float exp = 0.35f;
	    int smeltTime = 200;
	    int blastTime = smeltTime / 2;
	    if (material.hasOre)
	    {
	    	SimpleCookingRecipeBuilder.smelting(Ingredient.of(material.ore.get()), material.ingot.get(), exp, smeltTime).unlockedBy("has_" + material.name + "_ore", has(material.ore.get())).save(consumer, rl(material.name + "_ingot_from_smelting"));
	    	SimpleCookingRecipeBuilder.blasting(Ingredient.of(material.ore.get()), material.ingot.get(), exp, blastTime).unlockedBy("has_" + material.name + "_ore", has(material.ore.get())).save(consumer, rl(material.name + "_ingot_from_blasting"));
	    }
	    Item[] nuggetables = new Item[] { material.axe.get(), material.pickaxe.get(), material.shovel.get(), material.hoe.get(),material.sword.get(), material.hammer.get(), material.helmet.get(), material.chestplate.get(), material.leggings.get(), material.boots.get() };
	    SimpleCookingRecipeBuilder nuggetSmeltingRecipes = SimpleCookingRecipeBuilder.smelting(Ingredient.of(nuggetables), material.nugget.get(), exp, smeltTime);
	    SimpleCookingRecipeBuilder nuggetBlastingRecipes = SimpleCookingRecipeBuilder.blasting(Ingredient.of(nuggetables), material.nugget.get(), exp, blastTime);
	    for (Item nuggetable : nuggetables) {
	    	nuggetSmeltingRecipes.unlockedBy("has_" + nuggetable.getRegistryName().getPath(), has(nuggetable));
	    	nuggetBlastingRecipes.unlockedBy("has_" + nuggetable.getRegistryName().getPath(), has(nuggetable));
	    }
	    nuggetSmeltingRecipes.save(consumer, rl(material.name + "_nugget_from_smelting"));
	    nuggetBlastingRecipes.save(consumer, rl(material.name + "_nugget_from_blasting"));

	    // Smithing table
	    makeSmithingRecipe(material.block.get().asItem(), Items.STICK, material.hammer.get(), consumer);
		makeSmithingRecipe(material.shovelHead.get(), Items.STICK, material.shovel.get(), consumer);
		makeSmithingRecipe(material.axeHead.get(), Items.STICK, material.axe.get(), consumer);
		makeSmithingRecipe(material.pickaxeHead.get(), Items.STICK, material.pickaxe.get(), consumer);
		makeSmithingRecipe(material.hoeHead.get(), Items.STICK, material.hoe.get(), consumer);
		makeSmithingRecipe(material.ingot.get(), Items.STICK, material.swordHandle.get(), consumer);
		makeSmithingRecipe(material.swordBlade.get(), material.swordHandle.get(), material.sword.get(), consumer);

		// Armor
	    makeHelmetRecipe(material.helmet.get(), material.ingot.get(), consumer, material.name);
		makeChestplateRecipe(material.chestplate.get(), material.ingot.get(), consumer, material.name);
		makeLeggingsRecipe(material.leggings.get(), material.ingot.get(), consumer, material.name);
		makeBootsRecipe(material.boots.get(), material.ingot.get(), consumer, material.name);
	}
	
	private void makeColoredMaterialRecipes(ColoredMaterial material, Consumer<FinishedRecipe> consumer)
	{
		if (material.craftEight)
		{
			for (DyeColor color : DyeColor.values())
			{
				ShapedRecipeBuilder.shaped(material.getByColor(color), 8).define('#', material.craftMaterial.get()).define('D', DyeItem.byColor(color)).pattern("###").pattern("#D#").pattern("###").unlockedBy("has_" + material.name, has(material.craftMaterial.get())).save(consumer);
			}
		}
		else
		{
			for (DyeColor color : DyeColor.values())
			{
				ShapelessRecipeBuilder.shapeless(material.getByColor(color)).requires(material.craftMaterial.get()).requires(DyeItem.byColor(color)).unlockedBy("has_" + material.name, has(material.craftMaterial.get())).save(consumer);
			}
		}
			
	}
	
	private void cookFood(Item in, Item out, float exp, int time, Consumer<FinishedRecipe> consumer) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(in), out, exp, time).unlockedBy("has_" + in.getRegistryName().getPath(), has(in)).save(consumer);
	    SimpleCookingRecipeBuilder.cooking(Ingredient.of(in), out, exp, time / 2, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_" + in.getRegistryName().getPath(), has(in)).save(consumer, new ResourceLocation(BetterEnd.MOD_ID, out.getRegistryName().getPath() + "_from_smoking"));
	    SimpleCookingRecipeBuilder.cooking(Ingredient.of(in), out, exp, time * 3, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_" + in.getRegistryName().getPath(), has(in)).save(consumer, new ResourceLocation(BetterEnd.MOD_ID, out.getRegistryName().getPath() + "_from_campfire_cooking"));
	}
	
	private void makeSmithingRecipe(Item base, Item addition, Item output, Consumer<FinishedRecipe> consumer)
	{
		UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(addition), output).unlocks("has_" + addition.getRegistryName().getPath(), has(addition)).save(consumer, rl(output.getRegistryName().getPath() + "_smithing"));
	}
	
	private void makeIngotAndBlockRecipes(Block block, Item ingot, Consumer<FinishedRecipe> consumer, String material)
	{
	    ShapedRecipeBuilder.shaped(block).define('#', ingot).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + material + "_ingot", has(ingot)).save(consumer);
	    ShapelessRecipeBuilder.shapeless(ingot, 9).requires(block).group(material + "_ingot").unlockedBy("has_" + material + "_block", has(block)).save(consumer, rl(material + "_ingot_from_" + material + "_block"));
	}
	
	private void makeHelmetRecipe(Item result, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(result).define('X', ingredient).pattern("XXX").pattern("X X").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeChestplateRecipe(Item result, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(result).define('X', ingredient).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeLeggingsRecipe(Item result, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(result).define('X', ingredient).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeBootsRecipe(Item result, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(result).define('X', ingredient).pattern("X X").pattern("X X").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeSwordRecipe(Item sword, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(sword).define('#', Items.STICK).define('X', ingredient).pattern("X").pattern("X").pattern("#").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makePickaxeRecipe(Item pickaxe, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(pickaxe).define('#', Items.STICK).define('X', ingredient).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeAxeRecipe(Item axe, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(axe).define('#', Items.STICK).define('X', ingredient).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeShovelRecipe(Item shovel, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(shovel).define('#', Items.STICK).define('X', ingredient).pattern("X").pattern("#").pattern("#").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeHoeRecipe(Item hoe, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(hoe).define('#', Items.STICK).define('X', ingredient).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void makeHammerRecipe(Item hammer, Item ingredient, Consumer<FinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shaped(hammer).define('#', Items.STICK).define('X', ingredient).pattern("X X").pattern("X#X").pattern(" # ").unlockedBy("has_" + material + "_ingot", has(ingredient)).save(consumer);
	}
	
	private void registerPedestal(Block pedestal, Block slab, Block pillar, Consumer<FinishedRecipe> consumer, String material) 
	{
		ShapedRecipeBuilder.shaped(pedestal, 2).define('S', slab).define('#', pillar).pattern("S").pattern("#").pattern("S").unlockedBy("has_" + material + "_slab", has(slab)).unlockedBy("has_" + material + "_pillar", has(pillar)).save(consumer);
	}
	
	private void registerLantern(Block lantern, Block slab, Consumer<FinishedRecipe> consumer, String material) 
	{
		ShapedRecipeBuilder.shaped(lantern).define('S', slab).define('#', ModItems.CRYSTAL_SHARDS.get()).pattern("S").pattern("#").pattern("S").unlockedBy("has_" + material + "_slab", has(slab)).unlockedBy("has_crystal_shard", has(ModItems.CRYSTAL_SHARDS.get())).save(consumer);
	}
}
