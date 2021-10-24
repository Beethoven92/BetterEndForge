package mod.beethoven92.betterendforge.common.init;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.item.CrystaliteArmor;
import mod.beethoven92.betterendforge.common.item.EnchantedPetalItem;
import mod.beethoven92.betterendforge.common.item.EndAnvilItem;
import mod.beethoven92.betterendforge.common.item.GuideBookItem;
import mod.beethoven92.betterendforge.common.item.HammerItem;
import mod.beethoven92.betterendforge.common.item.ModArmorMaterial;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import mod.beethoven92.betterendforge.common.item.ModSpawnEggItem;
import mod.beethoven92.betterendforge.common.item.UmbrellaClusterJuiceItem;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems 
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterEnd.MOD_ID);
	
	// MATERIAL ITEMS
	public final static RegistryObject<Item> AETERNIUM_INGOT = ITEMS.register("aeternium_ingot",() -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> ENDER_DUST = ITEMS.register("ender_dust",() -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> END_LILY_LEAF = ITEMS.register("end_lily_leaf",() -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> END_LILY_LEAF_DRIED = ITEMS.register("end_lily_leaf_dried",() -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> CRYSTAL_SHARDS = ITEMS.register("crystal_shards",() -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> ETERNAL_CRYSTAL = ITEMS.register("eternal_crystal",() -> new Item(new Item.Properties().rarity(Rarity.EPIC).maxStackSize(16).group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> ENDER_SHARD = ITEMS.register("ender_shard", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> RAW_AMBER = ITEMS.register("raw_amber", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AMBER_GEM = ITEMS.register("amber_gem", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> GLOWING_BULB = ITEMS.register("glowing_bulb", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> GELATINE = ITEMS.register("gelatine", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> CRYSTALLINE_SULPHUR = ITEMS.register("crystalline_sulphur", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> HYDRALUX_PETAL = ITEMS.register("hydralux_petal", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> ENCHANTED_PETAL = ITEMS.register("enchanted_petal", () -> new EnchantedPetalItem(new Item.Properties().rarity(Rarity.RARE).maxStackSize(16).group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> LEATHER_STRIPE = ITEMS.register("leather_stripe", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> LEATHER_WRAPPED_STICK = ITEMS.register("leather_wrapped_stick", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> SILK_FIBER = ITEMS.register("silk_fiber", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> LUMECORN_ROD = ITEMS.register("lumecorn_rod", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	
	// ARMOR ITEMS
	public static final RegistryObject<Item> AETERNIUM_HELMET = ITEMS.register("aeternium_helmet", () -> new ArmorItem(ModArmorMaterial.AETERNIUM, EquipmentSlotType.HEAD, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> AETERNIUM_CHESTPLATE = ITEMS.register("aeternium_chestplate", () -> new ArmorItem(ModArmorMaterial.AETERNIUM, EquipmentSlotType.CHEST, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> AETERNIUM_LEGGINGS = ITEMS.register("aeternium_leggings", () -> new ArmorItem(ModArmorMaterial.AETERNIUM, EquipmentSlotType.LEGS, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> AETERNIUM_BOOTS = ITEMS.register("aeternium_boots", () -> new ArmorItem(ModArmorMaterial.AETERNIUM, EquipmentSlotType.FEET, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> CRYSTALITE_HELMET = ITEMS.register("crystalite_helmet", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE, EquipmentSlotType.HEAD, new Item.Properties().rarity(Rarity.UNCOMMON).group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> CRYSTALITE_CHESTPLATE = ITEMS.register("crystalite_chestplate", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE, EquipmentSlotType.CHEST, new Item.Properties().rarity(Rarity.UNCOMMON).group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> CRYSTALITE_LEGGINGS = ITEMS.register("crystalite_leggings", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE, EquipmentSlotType.LEGS, new Item.Properties().rarity(Rarity.UNCOMMON).group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<Item> CRYSTALITE_BOOTS = ITEMS.register("crystalite_boots", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE, EquipmentSlotType.FEET, new Item.Properties().rarity(Rarity.UNCOMMON).group(ModCreativeTabs.CREATIVE_TAB)));

	// TOOL ITEMS
	public static final RegistryObject<SwordItem> AETERNIUM_SWORD = ITEMS.register("aeternium_sword", () -> new SwordItem(ModItemTier.AETERNIUM, 3, -2.4F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ToolItem> AETERNIUM_SHOVEL = ITEMS.register("aeternium_shovel", () -> new ShovelItem(ModItemTier.AETERNIUM, 1.5F, -3.0F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ToolItem> AETERNIUM_PICKAXE = ITEMS.register("aeternium_pickaxe", () -> new PickaxeItem(ModItemTier.AETERNIUM, 1, -2.8F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ToolItem> AETERNIUM_AXE = ITEMS.register("aeternium_axe", () -> new AxeItem(ModItemTier.AETERNIUM, 5.0F, -3.0F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ToolItem> AETERNIUM_HOE = ITEMS.register("aeternium_hoe", () -> new HoeItem(ModItemTier.AETERNIUM, -3, 0.0F, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<HammerItem> AETERNIUM_HAMMER = ITEMS.register("aeternium_hammer", () -> new HammerItem(ModItemTier.AETERNIUM, 6.0F, -3.0F, 0.3D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<HammerItem> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(ItemTier.IRON, 5.0F, -3.2F, 0.2D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<HammerItem> GOLDEN_HAMMER = ITEMS.register("golden_hammer", () -> new HammerItem(ItemTier.GOLD, 4.5F, -3.4F, 0.3D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<HammerItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(ItemTier.DIAMOND, 5.5F, -3.1F, 0.2D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<HammerItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(ItemTier.NETHERITE, 5.0F, -3.0F, 0.2D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	
	// TOOLPARTS
	public final static RegistryObject<Item> AETERNIUM_SHOVEL_HEAD = ITEMS.register("aeternium_shovel_head", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AETERNIUM_PICKAXE_HEAD = ITEMS.register("aeternium_pickaxe_head", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AETERNIUM_AXE_HEAD = ITEMS.register("aeternium_axe_head", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AETERNIUM_HOE_HEAD = ITEMS.register("aeternium_hoe_head", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AETERNIUM_HAMMER_HEAD = ITEMS.register("aeternium_hammer_head", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AETERNIUM_SWORD_BLADE = ITEMS.register("aeternium_sword_blade", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public final static RegistryObject<Item> AETERNIUM_SWORD_HANDLE = ITEMS.register("aeternium_sword_handle", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	
	// SPAWN EGGS
	public static final RegistryObject<ModSpawnEggItem> DRAGONFLY_SPAWN_EGG = ITEMS.register("spawn_egg_dragonfly", () -> new ModSpawnEggItem(() -> ModEntityTypes.DRAGONFLY.get(), ModMathHelper.getColor(32, 42, 176), ModMathHelper.getColor(115, 225, 249), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ModSpawnEggItem> END_FISH_SPAWN_EGG = ITEMS.register("spawn_egg_end_fish", () -> new ModSpawnEggItem(() -> ModEntityTypes.END_FISH.get(), ModMathHelper.getColor(3, 50, 76), ModMathHelper.getColor(120, 206, 255), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ModSpawnEggItem> SHADOW_WALKER_SPAWN_EGG = ITEMS.register("spawn_egg_shadow_walker", () -> new ModSpawnEggItem(() -> ModEntityTypes.SHADOW_WALKER.get(), ModMathHelper.getColor(30, 30, 30), ModMathHelper.getColor(5, 5, 5), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ModSpawnEggItem> END_SLIME_SPAWN_EGG = ITEMS.register("spawn_egg_end_slime", () -> new ModSpawnEggItem(() -> ModEntityTypes.END_SLIME.get(), ModMathHelper.getColor(28, 28, 28), ModMathHelper.getColor(99, 11, 99), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ModSpawnEggItem> CUBOZOA_SPAWN_EGG = ITEMS.register("spawn_egg_cubozoa", () -> new ModSpawnEggItem(() -> ModEntityTypes.CUBOZOA.get(), ModMathHelper.getColor(151, 77, 181), ModMathHelper.getColor(93, 176, 238), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	public static final RegistryObject<ModSpawnEggItem> SILK_MOTH_SPAWN_EGG = ITEMS.register("spawn_egg_silk_moth", () -> new ModSpawnEggItem(() -> ModEntityTypes.SILK_MOTH.get(), ModMathHelper.getColor(0, 0, 0), ModMathHelper.getColor(225, 225, 225), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));

	// FOOD ITEMS
    public final static RegistryObject<Item> SHADOW_BERRY_RAW = ITEMS.register("shadow_berry_raw", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(new Food.Builder().hunger(4).saturation(0.5F).build())));
	public final static RegistryObject<Item> SHADOW_BERRY_COOKED = ITEMS.register("shadow_berry_cooked", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(new Food.Builder().hunger(6).saturation(0.7F).build())));
	public final static RegistryObject<Item> END_FISH_RAW = ITEMS.register("end_fish_raw", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(Foods.SALMON)));
	public final static RegistryObject<Item> END_FISH_COOKED = ITEMS.register("end_fish_cooked", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(Foods.COOKED_SALMON)));
	public final static RegistryObject<Item> SWEET_BERRY_JELLY = ITEMS.register("sweet_berry_jelly", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(new Food.Builder().hunger(3).saturation(0.75f).build())));
	public final static RegistryObject<Item> SHADOW_BERRY_JELLY = ITEMS.register("shadow_berry_jelly", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(new Food.Builder().hunger(4).saturation(0.75f).effect(() -> new EffectInstance(Effects.NIGHT_VISION, 400), 1).build())));
	public final static RegistryObject<Item> BLOSSOM_BERRY = ITEMS.register("blossom_berry", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(Foods.APPLE)));
	public final static RegistryObject<Item> AMBER_ROOT_RAW = ITEMS.register("amber_root_raw", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(new Food.Builder().hunger(2).saturation(0.8F).build())));
	public final static RegistryObject<Item> CHORUS_MUSHROOM_RAW = ITEMS.register("chorus_mushroom_raw", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(new Food.Builder().hunger(3).saturation(0.5F).build())));
	public final static RegistryObject<Item> CHORUS_MUSHROOM_COOKED = ITEMS.register("chorus_mushroom_cooked", () -> new Item(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).food(Foods.MUSHROOM_STEW)));
	
	// DRINK ITEMS
	public final static RegistryObject<Item> UMBRELLA_CLUSTER_JUICE = ITEMS.register("umbrella_cluster_juice", () -> new UmbrellaClusterJuiceItem(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1).food(new Food.Builder().hunger(5).saturation(0.7F).build())));
	
	// MISC ITEMS
	public final static RegistryObject<Item> BUCKET_END_FISH = ITEMS.register("bucket_end_fish", () -> new FishBucketItem(() -> ModEntityTypes.END_FISH.get(), () -> Fluids.WATER, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));
	public final static RegistryObject<Item> BUCKET_CUBOZOA = ITEMS.register("bucket_cubozoa", () -> new FishBucketItem(() -> ModEntityTypes.CUBOZOA.get(), () -> Fluids.WATER, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));
	public final static RegistryObject<Item> GUIDE_BOOK = ITEMS.register("guidebook", () -> new GuideBookItem(new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));
	public final static RegistryObject<Item> FLAMAEA = ITEMS.register("flamaea", () -> new LilyPadItem(ModBlocks.FLAMAEA.get(), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	
	public final static RegistryObject<Item> AETERNIUM_ANVIL = ITEMS.register("aeternium_anvil", () -> new EndAnvilItem(ModBlocks.AETERNIUM_ANVIL.get(), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));

	
	// MUSIC DISCS
	public final static RegistryObject<Item> MUSIC_DISC_STRANGE_AND_ALIEN = ITEMS.register("music_disc_strange_and_alien", () -> new MusicDiscItem(0, ModSoundEvents.RECORD_STRANGE_AND_ALIEN, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));
	public final static RegistryObject<Item> MUSIC_DISC_GRASPING_AT_STARS = ITEMS.register("music_disc_grasping_at_stars", () -> new MusicDiscItem(0, ModSoundEvents.RECORD_GRASPING_AT_STARS, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));
	public final static RegistryObject<Item> MUSIC_DISC_ENDSEEKER = ITEMS.register("music_disc_endseeker", () -> new MusicDiscItem(0, ModSoundEvents.RECORD_ENDSEEKER, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));
	public final static RegistryObject<Item> MUSIC_DISC_EO_DRACONA = ITEMS.register("music_disc_eo_dracona", () -> new MusicDiscItem(0, ModSoundEvents.RECORD_EO_DRACONA, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB).maxStackSize(1)));



	public final static RegistryObject<BlockItem> CHARCOAL_BLOCK = ITEMS.register("charcoal_block", () -> new BlockItem(ModBlocks.CHARCOAL_BLOCK.get(), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB))
	{
		@Override
		public int getBurnTime(ItemStack itemStack) {
			return 14400;
	    }
	});


	//////////////////////////////////////////////////////
	//
	// Item registration helpers
	//
	/////////////////////////////////////////////////////
	
	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<? extends T> itemSupplier)
	{
		return ITEMS.register(name, itemSupplier);
	}
}
