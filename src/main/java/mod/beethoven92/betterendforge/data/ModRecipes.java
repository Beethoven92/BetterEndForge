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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

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
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) 
	{
		// BLOCKS
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.ENDER_BLOCK.get()).key('#', Items.ENDER_PEARL).patternLine("##").patternLine("##").addCriterion("has_ender_pearl", hasItem(Items.ENDER_PEARL)).build(consumer);	    
	    ShapelessRecipeBuilder.shapelessRecipe(Items.ENDER_PEARL, 4).addIngredient(ModBlocks.ENDER_BLOCK.get()).addCriterion("has_ender_block", hasItem(ModBlocks.ENDER_BLOCK.get())).build(consumer, rl("ender_pearl_from_ender_block"));
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.HYDRALUX_PETAL_BLOCK.get()).key('#', ModItems.HYDRALUX_PETAL.get()).patternLine("##").patternLine("##").addCriterion("has_hydralux_petal", hasItem(ModItems.HYDRALUX_PETAL.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.AMBER_BLOCK.get()).key('#', ModItems.AMBER_GEM.get()).patternLine("##").patternLine("##").addCriterion("has_amber_gem", hasItem(ModItems.AMBER_GEM.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ModItems.AMBER_GEM.get(), 4).addIngredient(ModBlocks.AMBER_BLOCK.get()).addCriterion("has_amber_block", hasItem(ModBlocks.AMBER_BLOCK.get())).build(consumer, rl("amber_gem_from_amber_block"));
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.AURORA_CRYSTAL.get()).key('#', ModItems.CRYSTAL_SHARDS.get()).patternLine("##").patternLine("##").addCriterion("has_crystal_shard", hasItem(ModItems.CRYSTAL_SHARDS.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_LOTUS.log.get()).key('#', ModBlocks.END_LOTUS_STEM.get()).patternLine("##").patternLine("##").addCriterion("has_end_lotus_stem", hasItem(ModBlocks.END_LOTUS_STEM.get())).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_STONE_FURNACE.get()).key('#', Blocks.END_STONE).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_stone_furnaces").addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_STONE_SMELTER.get()).key('#', Blocks.END_STONE_BRICKS).key('V', ModTags.FURNACES).key('T', ModBlocks.THALLASIUM.ingot.get()).patternLine("T#T").patternLine("V V").patternLine("T#T").addCriterion("has_end_stone_bricks", hasItem(Blocks.END_STONE_BRICKS)).build(consumer);
	   
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.RESPAWN_OBELISK.get()).key('C', ModBlocks.AURORA_CRYSTAL.get()).key('S', ModItems.ETERNAL_CRYSTAL.get()).key('A', ModBlocks.AMBER_BLOCK.get()).patternLine("CSC").patternLine("CSC").patternLine("AAA").addCriterion("has_amber_block", hasItem(ModBlocks.AMBER_BLOCK.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.DENSE_EMERALD_ICE.get()).key('#', ModBlocks.EMERALD_ICE.get()).patternLine("##").patternLine("##").addCriterion("has_emerald_ice", hasItem(ModBlocks.EMERALD_ICE.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.ANCIENT_EMERALD_ICE.get()).key('#', ModBlocks.DENSE_EMERALD_ICE.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_dense_emerald_ice", hasItem(ModBlocks.DENSE_EMERALD_ICE.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.IRON_BULB_LANTERN.get()).key('C', Blocks.CHAIN).key('I', Items.IRON_INGOT).key('#', ModItems.GLOWING_BULB.get()).patternLine("C").patternLine("I").patternLine("#").addCriterion("has_glowing_bulb", hasItem(ModItems.GLOWING_BULB.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.IRON_CHANDELIER.get()).key('I', ModItems.LUMECORN_ROD.get()).key('#', Items.IRON_INGOT).patternLine("I#I").patternLine(" # ").setGroup("end_metal_chandelier").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.GOLD_CHANDELIER.get()).key('I', ModItems.LUMECORN_ROD.get()).key('#', Items.GOLD_INGOT).patternLine("I#I").patternLine(" # ").setGroup("end_metal_chandelier").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.MISSING_TILE.get(), 4).key('#', ModBlocks.VIOLECITE.stone.get()).key('P', Blocks.PURPUR_BLOCK).patternLine("#P").patternLine("P#").addCriterion("has_violecite", hasItem(ModBlocks.VIOLECITE.stone.get())).build(consumer);	    
	    
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.FILALUX_LANTERN.get()).key('#', ModBlocks.FILALUX.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_filalux", hasItem(ModBlocks.FILALUX.get())).build(consumer);	    
	    
	    // DYES
		ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(ModBlocks.BLUE_VINE_SEED.get()).setGroup("blue_dye").addCriterion("has_blue_vine_seed", hasItem(ModBlocks.BLUE_VINE_SEED.get())).build(consumer, rl("blue_dye_from_blue_vine_seed"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE).addIngredient(ModBlocks.CREEPING_MOSS.get()).setGroup("cyan_dye").addCriterion("has_creeping_moss", hasItem(ModBlocks.CREEPING_MOSS.get())).build(consumer, rl("cyan_dye_from_creeping_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE).addIngredient(ModBlocks.CYAN_MOSS.get()).setGroup("cyan_dye").addCriterion("has_cyan_moss", hasItem(ModBlocks.CYAN_MOSS.get())).build(consumer, rl("cyan_dye_from_cyan_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(ModBlocks.UMBRELLA_MOSS.get()).setGroup("yellow_dye").addCriterion("has_umbrella_moss", hasItem(ModBlocks.UMBRELLA_MOSS.get())).build(consumer, rl("yellow_dye_from_umbrella_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE, 2).addIngredient(ModBlocks.UMBRELLA_MOSS_TALL.get()).setGroup("yellow_dye").addCriterion("has_umbrella_moss_tall", hasItem(ModBlocks.UMBRELLA_MOSS_TALL.get())).build(consumer, rl("yellow_dye_from_umbrella_moss_tall"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(ModBlocks.SHADOW_PLANT.get()).setGroup("black_dye").addCriterion("has_shadow_plant", hasItem(ModBlocks.SHADOW_PLANT.get())).build(consumer, rl("black_dye_from_shadow_plant"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(ModBlocks.PURPLE_POLYPORE.get()).setGroup("purple_dye").addCriterion("has_purple_polypore", hasItem(ModBlocks.PURPLE_POLYPORE.get())).build(consumer, rl("purple_dye_from_purple_polypore"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE).addIngredient(ModBlocks.TAIL_MOSS.get()).setGroup("gray_dye").addCriterion("has_tail_moss", hasItem(ModBlocks.TAIL_MOSS.get())).build(consumer, rl("gray_dye_from_tail_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE).addIngredient(ModBlocks.BUSHY_GRASS.get()).setGroup("magenta_dye").addCriterion("has_bushy_grass", hasItem(ModBlocks.BUSHY_GRASS.get())).build(consumer, rl("magenta_dye_from_bushy_grass"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE).addIngredient(ModBlocks.TWISTED_MOSS.get()).setGroup("pink_dye").addCriterion("has_twisted_moss", hasItem(ModBlocks.TWISTED_MOSS.get())).build(consumer, rl("pink_dye_from_twisted_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(ModItems.HYDRALUX_PETAL.get()).setGroup("white_dye").addCriterion("has_hydralux_petal", hasItem(ModItems.HYDRALUX_PETAL.get())).build(consumer, rl("white_dye_from_hydralux_petal"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(ModBlocks.TWISTED_UMBRELLA_MOSS.get()).setGroup("purple_dye").addCriterion("has_twisted_umbrella_moss", hasItem(ModBlocks.TWISTED_UMBRELLA_MOSS.get())).build(consumer, rl("purple_dye_from_twisted_umbrella_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE, 2).addIngredient(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get()).setGroup("purple_dye").addCriterion("has_twisted_umbrella_moss_tall", hasItem(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL.get())).build(consumer, rl("purple_dye_from_twisted_umbrella_moss_tall"));
		
		ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(ModBlocks.CHARNIA_RED.get()).setGroup("red_dye").addCriterion("has_red_charnia", hasItem(ModBlocks.CHARNIA_RED.get())).build(consumer, rl("red_dye_from_red_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(ModBlocks.CHARNIA_PURPLE.get()).setGroup("purple_dye").addCriterion("has_purple_charnia", hasItem(ModBlocks.CHARNIA_PURPLE.get())).build(consumer, rl("purple_dye_from_purple_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE).addIngredient(ModBlocks.CHARNIA_ORANGE.get()).setGroup("orange_dye").addCriterion("has_orange_charnia", hasItem(ModBlocks.CHARNIA_ORANGE.get())).build(consumer, rl("orange_dye_from_orange_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE).addIngredient(ModBlocks.CHARNIA_LIGHT_BLUE.get()).setGroup("light_blue_dye").addCriterion("has_light_blue_charnia", hasItem(ModBlocks.CHARNIA_LIGHT_BLUE.get())).build(consumer, rl("light_blue_dye_from_light_blue_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE).addIngredient(ModBlocks.CHARNIA_CYAN.get()).setGroup("cyan_dye").addCriterion("has_cyan_charnia", hasItem(ModBlocks.CHARNIA_CYAN.get())).build(consumer, rl("cyan_dye_from_cyan_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.GREEN_DYE).addIngredient(ModBlocks.CHARNIA_GREEN.get()).setGroup("green_dye").addCriterion("has_green_charnia", hasItem(ModBlocks.CHARNIA_GREEN.get())).build(consumer, rl("green_dye_from_green_charnia"));
		
		// ITEMS
		ShapedRecipeBuilder.shapedRecipe(Items.PAPER, 3).key('#', ModItems.END_LILY_LEAF_DRIED.get()).patternLine("###").addCriterion("has_end_lily_leaf_dried", hasItem(ModItems.END_LILY_LEAF_DRIED.get())).build(consumer, rl("paper_from_end_lily_leaf_dried"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.STICK, 2).addIngredient(ModBlocks.NEEDLEGRASS.get()).addCriterion("has_needlegrass", hasItem(ModBlocks.NEEDLEGRASS.get())).build(consumer, rl("stick_from_needlegrass"));
		ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.SHADOW_BERRY.get(), 4).addIngredient(ModItems.SHADOW_BERRY_RAW.get()).addCriterion("has_shadow_berry_raw", hasItem(ModItems.SHADOW_BERRY_RAW.get())).build(consumer, rl("shadow_berry_from_shadow_berry_raw"));
		ShapelessRecipeBuilder.shapelessRecipe(ModItems.SWEET_BERRY_JELLY.get()).addIngredient(ModItems.GELATINE.get()).addIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER).getItem()).addIngredient(Items.SUGAR).addIngredient(Items.SWEET_BERRIES).addCriterion("has_gelatine", hasItem(ModItems.GELATINE.get())).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(ModItems.SHADOW_BERRY_JELLY.get()).addIngredient(ModItems.GELATINE.get()).addIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER).getItem()).addIngredient(Items.SUGAR).addIngredient(ModItems.SHADOW_BERRY_COOKED.get()).addCriterion("has_gelatine", hasItem(ModItems.GELATINE.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModItems.AMBER_GEM.get()).key('#', ModItems.RAW_AMBER.get()).patternLine("##").patternLine("##").addCriterion("has_raw_amber", hasItem(ModItems.RAW_AMBER.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER).addIngredient(ModItems.CRYSTALLINE_SULPHUR.get()).addIngredient(ItemTags.COALS).addIngredient(Items.BONE_MEAL).addCriterion("has_crystalline_sulphur", hasItem(ModItems.CRYSTALLINE_SULPHUR.get())).build(consumer, rl("gunpowder_from_sulphur"));
	    ShapedRecipeBuilder.shapedRecipe(ModItems.GUIDE_BOOK.get()).key('D', ModItems.ENDER_DUST.get()).key('B', Items.BOOK).key('C', ModItems.CRYSTAL_SHARDS.get()).patternLine("D").patternLine("B").patternLine("C").addCriterion("has_crystal_shards", hasItem(ModItems.CRYSTAL_SHARDS.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ModItems.LEATHER_STRIPE.get(), 3).addIngredient(Items.LEATHER).addCriterion("has_leather", hasItem(Items.LEATHER)).build(consumer, rl("leather_to_stripes"));
	    ShapelessRecipeBuilder.shapelessRecipe(Items.LEATHER).addIngredient(ModItems.LEATHER_STRIPE.get()).addIngredient(ModItems.LEATHER_STRIPE.get()).addIngredient(ModItems.LEATHER_STRIPE.get()).addCriterion("has_leather_stripe", hasItem(ModItems.LEATHER_STRIPE.get())).build(consumer, rl("stripes_to_leather"));
	    ShapelessRecipeBuilder.shapelessRecipe(ModItems.LEATHER_WRAPPED_STICK.get()).addIngredient(Items.STICK).addIngredient(ModItems.LEATHER_STRIPE.get()).addCriterion("has_leather_stripe", hasItem(ModItems.LEATHER_STRIPE.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(Items.ENDER_EYE).key('S', ModItems.CRYSTAL_SHARDS.get()).key('A', ModItems.AMBER_GEM.get()).key('P', Items.ENDER_PEARL).patternLine("SAS").patternLine("APA").patternLine("SAS").addCriterion("has_amber_gem", hasItem(ModItems.AMBER_GEM.get())).build(consumer, rl("ender_eye_from_amber_gem"));
	    ShapedRecipeBuilder.shapedRecipe(Items.STRING, 6).key('#', ModItems.SILK_FIBER.get()).patternLine("#").patternLine("#").patternLine("#").addCriterion("has_silk_fiber", hasItem(ModItems.SILK_FIBER.get())).build(consumer, rl("fiber_string"));
	    
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
		
		ShapedRecipeBuilder.shapedRecipe(ModBlocks.INFUSION_PEDESTAL.get()).key('O', Items.ENDER_PEARL).key('Y', Items.ENDER_EYE).key('#', Blocks.OBSIDIAN).patternLine(" Y ").patternLine("O#O").patternLine(" # ").addCriterion("has_ender_pearl", hasItem(Items.ENDER_PEARL)).addCriterion("has_ender_eye", hasItem(Items.ENDER_EYE)).addCriterion("has_obsidian", hasItem(Blocks.OBSIDIAN)).build(consumer);
		
		// FURNACE
		cookFood(ModItems.END_FISH_RAW.get(), ModItems.END_FISH_COOKED.get(), 0.35F, 200, consumer);
		cookFood(ModItems.END_LILY_LEAF.get(), ModItems.END_LILY_LEAF_DRIED.get(), 0.35F, 200, consumer);
		cookFood(ModItems.SHADOW_BERRY_RAW.get(), ModItems.SHADOW_BERRY_COOKED.get(), 0.35F, 200, consumer);
		cookFood(ModItems.CHORUS_MUSHROOM_RAW.get(), ModItems.CHORUS_MUSHROOM_COOKED.get(), 0.35F, 200, consumer);
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModBlocks.ENDSTONE_DUST.get()), Blocks.GLASS.asItem(), 0.35F, 200).addCriterion("has_end_stone_dust", hasItem(ModBlocks.ENDSTONE_DUST.get())).build(consumer, rl("glass_from_end_stone_dust"));
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModBlocks.JELLYSHROOM_CAP_PURPLE.get()), Items.SLIME_BALL, 0.35F, 200).addCriterion("has_jellyshroom_cap", hasItem(ModBlocks.JELLYSHROOM_CAP_PURPLE.get())).build(consumer, rl("slime_ball_from_jellyshroom_cap"));
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModBlocks.MENGER_SPONGE_WET.get()), ModBlocks.MENGER_SPONGE.get(), 0.35F, 200).addCriterion("has_menger_sponge_wet", hasItem(ModBlocks.MENGER_SPONGE_WET.get())).build(consumer);
		
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
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.NEON_CACTUS_BLOCK.get()).key('#', ModBlocks.NEON_CACTUS.get()).patternLine("##").patternLine("##").addCriterion("has_neon_cactus", hasItem(ModBlocks.NEON_CACTUS.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get(), 6).key('#', ModBlocks.NEON_CACTUS_BLOCK.get()).patternLine("###").addCriterion("has_neon_cactus_block", hasItem(ModBlocks.NEON_CACTUS_BLOCK.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get(), 4).key('#', ModBlocks.NEON_CACTUS_BLOCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_neon_cactus_block", hasItem(ModBlocks.NEON_CACTUS_BLOCK.get())).build(consumer);
		
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
	
	private void makeWoodenMaterialRecipes(WoodenMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		ShapelessRecipeBuilder.shapelessRecipe(material.planks.get(), 4).addIngredient(material.logItemTag).setGroup("end_planks").addCriterion("has_logs", hasItem(material.logItemTag)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.planks.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_planks__stairs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.planks.get()).patternLine("###").setGroup("end_planks_slabs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.fence.get(), 3).key('#', Items.STICK).key('W', material.planks.get()).patternLine("W#W").patternLine("W#W").setGroup("end_planks_fences").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.gate.get()).key('#', Items.STICK).key('W', material.planks.get()).patternLine("#W#").patternLine("#W#").setGroup("end_planks_gates").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button.get()).addIngredient(material.planks.get()).setGroup("end_planks_buttons").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressurePlate.get()).key('#', material.planks.get()).patternLine("##").setGroup("end_planks_plates").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor.get(), 2).key('#', material.planks.get()).patternLine("###").patternLine("###").setGroup("end_trapdoors").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.door.get(), 3).key('#', material.planks.get()).patternLine("##").patternLine("##").patternLine("##").setGroup("end_doors").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bark.get(), 3).key('#', material.log.get()).patternLine("##").patternLine("##").addCriterion("has_log", hasItem(material.log.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bark_stripped.get(), 3).key('#', material.log_stripped.get()).patternLine("##").patternLine("##").addCriterion("has_log_stripped", hasItem(material.log_stripped.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(material.composter.get(), 1).key('#', material.slab.get()).patternLine("# #").patternLine("# #").patternLine("###").setGroup("end_composters").addCriterion("has_slabs", hasItem(material.slab.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.craftingTable.get(), 1).key('#', material.planks.get()).patternLine("##").patternLine("##").setGroup("end_crafting_tables").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.ladder.get(), 3).key('#', material.planks.get()).key('I', Items.STICK).patternLine("I I").patternLine("I#I").patternLine("I I").setGroup("end_ladders").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.chest.get(), 1).key('#', material.planks.get()).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_chests").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.sign.get(), 3).key('#', material.planks.get()).key('I', Items.STICK).patternLine("###").patternLine("###").patternLine(" I ").setGroup("end_signs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.barrel.get(), 1).key('#', material.planks.get()).key('S', material.slab.get()).patternLine("#S#").patternLine("# #").patternLine("#S#").setGroup("end_barrels").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.shelf.get(), 1).key('#', material.planks.get()).key('P', Items.BOOK).patternLine("###").patternLine("PPP").patternLine("###").setGroup("end_bookshelves").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	}
	
	private void makeStoneMaterialRecipes(StoneMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		// Crafting
		ShapedRecipeBuilder.shapedRecipe(material.bricks.get(), 4).key('#', material.stone.get()).patternLine("##").patternLine("##").setGroup("end_bricks").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.polished.get(), 4).key('#', material.bricks.get()).patternLine("##").patternLine("##").setGroup("end_tile").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.tiles.get(), 4).key('#', material.polished.get()).patternLine("##").patternLine("##").setGroup("end_small_tile").addCriterion("has_" + material.polished.get().getRegistryName().getPath(), hasItem(material.polished.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pillar.get()).key('#', material.slab.get()).patternLine("#").patternLine("#").setGroup("end_pillar").addCriterion("has_" + material.slab.get().getRegistryName().getPath(), hasItem(material.slab.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.stone.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.stone.get()).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_stairs.get(), 4).key('#', material.bricks.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_slab.get(), 6).key('#', material.bricks.get()).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.wall.get(), 6).key('#', material.stone.get()).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_wall.get(), 6).key('#', material.bricks.get()).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button.get()).addIngredient(material.stone.get()).setGroup("end_stone_buttons").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate.get()).key('#', material.stone.get()).patternLine("##").setGroup("end_stone_plates").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    registerLantern(material.lantern.get(), material.slab.get(), consumer, material.name);
	    registerPedestal(material.pedestal.get(), material.slab.get(), material.pillar.get(), consumer, material.name);
	    ShapedRecipeBuilder.shapedRecipe(material.furnace.get()).key('#', material.stone.get()).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_stone_furnaces").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);

		// Stonecutting
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.bricks.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.polished.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_polished_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.tiles.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_tiles_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.pillar.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_pillar_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.stairs.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_stairs_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.slab.get(), 2).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_slab_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.brick_stairs.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.brick_slab.get(), 2).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.wall.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_wall_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.brick_wall.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.button.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_button_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks.get()), material.brick_stairs.get()).addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_bricks_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks.get()), material.brick_slab.get(), 2).addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_bricks_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks.get()), material.brick_wall.get()).addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_bricks_stonecutting"));
	}
	
	private void makeMetalMaterialRecipes(MetalMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		// Base
	    makeIngotAndBlockRecipes(material.block.get(), material.ingot.get(), consumer, material.name);
	    ShapedRecipeBuilder.shapedRecipe(material.ingot.get()).key('#', material.nugget.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material.name + "_nugget", hasItem(material.nugget.get())).build(consumer, rl(material.name + "_ingot_from_" + material.name + "_nuggets"));
		
	    // Blocks
	    ShapedRecipeBuilder.shapedRecipe(material.tile.get(), 4).key('#', material.block.get()).patternLine("##").patternLine("##").setGroup("end_metal_tile").addCriterion("has_" + material.name + "_block", hasItem(material.block.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.block.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_metal_stairs").addCriterion("has_" + material.name + "_block", hasItem(material.block.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.block.get()).patternLine("###").setGroup("end_metal_slabs").addCriterion("has_" + material.block + "_block", hasItem(material.block.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.door.get(), 3).key('#', material.ingot.get()).patternLine("##").patternLine("##").patternLine("##").setGroup("end_metal_doors").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor.get()).key('#', material.ingot.get()).patternLine("##").patternLine("##").setGroup("end_metal_trapdoors").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate.get()).key('#', material.ingot.get()).patternLine("##").setGroup("end_metal_plates").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bars.get(), 16).key('#', material.ingot.get()).patternLine("###").patternLine("###").setGroup("end_metal_bars").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.chain.get()).key('N', material.nugget.get()).key('#', material.ingot.get()).patternLine("N").patternLine("#").patternLine("N").setGroup("end_metal_chain").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.anvil.get()).key('#', material.block.get()).key('I', material.ingot.get()).patternLine("###").patternLine(" I ").patternLine("III").setGroup("end_metal_anvil").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.chandelier.get()).key('I', ModItems.LUMECORN_ROD.get()).key('#', material.ingot.get()).patternLine("I#I").patternLine(" # ").setGroup("end_metal_chandelier").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bulb_lantern.get()).key('C', Blocks.CHAIN).key('I', material.ingot.get()).key('#', ModItems.GLOWING_BULB.get()).patternLine("C").patternLine("I").patternLine("#").addCriterion("has_glowing_bulb", hasItem(ModItems.GLOWING_BULB.get())).build(consumer);
	    makeColoredMaterialRecipes(material.bulb_lantern_colored, consumer);
	    ShapedRecipeBuilder.shapedRecipe(Blocks.SMITHING_TABLE).key('I', material.ingot.get()).key('#', ItemTags.PLANKS).patternLine("II").patternLine("##").patternLine("##").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer, rl("smithing_table_from_" + material.name + "_ingot"));
	    
	    // Furnace & blast furnace
	    float exp = 0.35f;
	    int smeltTime = 200;
	    int blastTime = smeltTime / 2;
	    if (material.hasOre)
	    {
	    	CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(material.ore.get()), material.ingot.get(), exp, smeltTime).addCriterion("has_" + material.name + "_ore", hasItem(material.ore.get())).build(consumer, rl(material.name + "_ingot_from_smelting"));
	    	CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(material.ore.get()), material.ingot.get(), exp, blastTime).addCriterion("has_" + material.name + "_ore", hasItem(material.ore.get())).build(consumer, rl(material.name + "_ingot_from_blasting"));
	    }
	    Item[] nuggetables = new Item[] { material.axe.get(), material.pickaxe.get(), material.shovel.get(), material.hoe.get(),material.sword.get(), material.hammer.get(), material.helmet.get(), material.chestplate.get(), material.leggings.get(), material.boots.get() };
	    CookingRecipeBuilder nuggetSmeltingRecipes = CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(nuggetables), material.nugget.get(), exp, smeltTime);
	    CookingRecipeBuilder nuggetBlastingRecipes = CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(nuggetables), material.nugget.get(), exp, blastTime);
	    for (Item nuggetable : nuggetables) {
	    	nuggetSmeltingRecipes.addCriterion("has_" + nuggetable.getRegistryName().getPath(), hasItem(nuggetable));
	    	nuggetBlastingRecipes.addCriterion("has_" + nuggetable.getRegistryName().getPath(), hasItem(nuggetable));
	    }
	    nuggetSmeltingRecipes.build(consumer, rl(material.name + "_nugget_from_smelting"));
	    nuggetBlastingRecipes.build(consumer, rl(material.name + "_nugget_from_blasting"));

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
	
	private void makeColoredMaterialRecipes(ColoredMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		if (material.craftEight)
		{
			for (DyeColor color : DyeColor.values())
			{
				ShapedRecipeBuilder.shapedRecipe(material.getByColor(color), 8).key('#', material.craftMaterial.get()).key('D', DyeItem.getItem(color)).patternLine("###").patternLine("#D#").patternLine("###").addCriterion("has_" + material.name, hasItem(material.craftMaterial.get())).build(consumer);
			}
		}
		else
		{
			for (DyeColor color : DyeColor.values())
			{
				ShapelessRecipeBuilder.shapelessRecipe(material.getByColor(color)).addIngredient(material.craftMaterial.get()).addIngredient(DyeItem.getItem(color)).addCriterion("has_" + material.name, hasItem(material.craftMaterial.get())).build(consumer);
			}
		}
			
	}
	
	private void cookFood(Item in, Item out, float exp, int time, Consumer<IFinishedRecipe> consumer) {
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(in), out, exp, time).addCriterion("has_" + in.getRegistryName().getPath(), hasItem(in)).build(consumer);
	    CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(in), out, exp, time / 2, IRecipeSerializer.SMOKING).addCriterion("has_" + in.getRegistryName().getPath(), hasItem(in)).build(consumer, new ResourceLocation(BetterEnd.MOD_ID, out.getRegistryName().getPath() + "_from_smoking"));
	    CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(in), out, exp, time * 3, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_" + in.getRegistryName().getPath(), hasItem(in)).build(consumer, new ResourceLocation(BetterEnd.MOD_ID, out.getRegistryName().getPath() + "_from_campfire_cooking"));
	}
	
	private void makeSmithingRecipe(Item base, Item addition, Item output, Consumer<IFinishedRecipe> consumer)
	{
		SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(base), Ingredient.fromItems(addition), output).addCriterion("has_" + addition.getRegistryName().getPath(), hasItem(addition)).build(consumer, rl(output.getRegistryName().getPath() + "_smithing"));
	}
	
	private void makeIngotAndBlockRecipes(Block block, Item ingot, Consumer<IFinishedRecipe> consumer, String material)
	{
	    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingot).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material + "_ingot", hasItem(ingot)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ingot, 9).addIngredient(block).setGroup(material + "_ingot").addCriterion("has_" + material + "_block", hasItem(block)).build(consumer, rl(material + "_ingot_from_" + material + "_block"));
	}
	
	private void makeHelmetRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeChestplateRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeLeggingsRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeBootsRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeSwordRecipe(Item sword, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(sword).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makePickaxeRecipe(Item pickaxe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(pickaxe).key('#', Items.STICK).key('X', ingredient).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeAxeRecipe(Item axe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(axe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeShovelRecipe(Item shovel, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(shovel).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeHoeRecipe(Item hoe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hoe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeHammerRecipe(Item hammer, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hammer).key('#', Items.STICK).key('X', ingredient).patternLine("X X").patternLine("X#X").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void registerPedestal(Block pedestal, Block slab, Block pillar, Consumer<IFinishedRecipe> consumer, String material) 
	{
		ShapedRecipeBuilder.shapedRecipe(pedestal, 2).key('S', slab).key('#', pillar).patternLine("S").patternLine("#").patternLine("S").addCriterion("has_" + material + "_slab", hasItem(slab)).addCriterion("has_" + material + "_pillar", hasItem(pillar)).build(consumer);
	}
	
	private void registerLantern(Block lantern, Block slab, Consumer<IFinishedRecipe> consumer, String material) 
	{
		ShapedRecipeBuilder.shapedRecipe(lantern).key('S', slab).key('#', ModItems.CRYSTAL_SHARDS.get()).patternLine("S").patternLine("#").patternLine("S").addCriterion("has_" + material + "_slab", hasItem(slab)).addCriterion("has_crystal_shard", hasItem(ModItems.CRYSTAL_SHARDS.get())).build(consumer);
	}
}
