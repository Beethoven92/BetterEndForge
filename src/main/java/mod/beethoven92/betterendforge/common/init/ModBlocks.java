package mod.beethoven92.betterendforge.common.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.*;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.ChandelierBlock;
import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
import mod.beethoven92.betterendforge.common.block.template.EndFurnaceBlock;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterWallPlantBlock;
import mod.beethoven92.betterendforge.common.block.template.WallMushroomBlock;
import mod.beethoven92.betterendforge.common.block.template.WallPlantBlock;
import mod.beethoven92.betterendforge.common.item.ModArmorMaterial;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class ModBlocks 
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterEnd.MOD_ID);
	
	// TERRAINS
	public static final RegistryObject<Block> CRYSTAL_MOSS = registerBlockWithDefaultItem("crystal_moss",
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).
					                                        requiresCorrectToolForDrops().
					                                        strength(3.0F, 9.0F).
					                                        sound(SoundType.GRAVEL).
					                                        randomTicks()));
	
	public static final RegistryObject<Block> END_MYCELIUM = registerBlockWithDefaultItem("end_mycelium", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).
					                                        requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> END_MOSS = registerBlockWithDefaultItem("end_moss", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> CHORUS_NYLIUM = registerBlockWithDefaultItem("chorus_nylium", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_MAGENTA).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.STONE).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> CAVE_MOSS = registerBlockWithDefaultItem("cave_moss", 
			() -> new TripleTerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PURPLE).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> SHADOW_GRASS = registerBlockWithDefaultItem("shadow_grass", 
			() -> new ShadowGrassBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).
                                                                requiresCorrectToolForDrops().
                                                                strength(3.0F, 9.0F).
                                                                sound(SoundType.GRAVEL).
                                                                randomTicks()));
	
	public static final RegistryObject<Block> PINK_MOSS = registerBlockWithDefaultItem("pink_moss", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> AMBER_MOSS = registerBlockWithDefaultItem("amber_moss", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> JUNGLE_MOSS = registerBlockWithDefaultItem("jungle_moss", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> SANGNUM = registerBlockWithDefaultItem("sangnum", 
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));
	
	public static final RegistryObject<Block> RUTISCUS = registerBlockWithDefaultItem("rutiscus",
			() -> new TerrainBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).
                                                            requiresCorrectToolForDrops().
                                                            strength(3.0F, 9.0F).
                                                            sound(SoundType.GRAVEL).
                                                            randomTicks()));

	public static final RegistryObject<Block> PALLIDIUM_FULL = registerBlockWithDefaultItem("pallidium_full",
			() -> new PallidiumBlock("full", null));

	public static final RegistryObject<Block> PALLIDIUM_HEAVY = registerBlockWithDefaultItem("pallidium_heavy",
			() -> new PallidiumBlock("heavy", PALLIDIUM_FULL.get()));

	public static final RegistryObject<Block> PALLIDIUM_THIN = registerBlockWithDefaultItem("pallidium_thin",
			() -> new PallidiumBlock("thin", PALLIDIUM_HEAVY.get()));

	public static final RegistryObject<Block> PALLIDIUM_TINY = registerBlockWithDefaultItem("pallidium_tiny",
			() -> new PallidiumBlock("tiny", PALLIDIUM_THIN.get()));


	public static final RegistryObject<Block> FLAMMALIX = registerBlockWithDefaultItem("flammalix",
			() -> new FlammalixBlock());


	public static final RegistryObject<Block> ENDSTONE_DUST = registerBlockWithDefaultItem("endstone_dust", 
			() -> new EndstoneDustBlock(BlockBehaviour.Properties.of(Material.SAND, Blocks.END_STONE.defaultMaterialColor()).
					                                             strength(0.5F).
					                                             sound(SoundType.SAND)));
	
	public static final RegistryObject<Block> SILK_MOTH_NEST = registerBlockWithDefaultItem("silk_moth_nest", 
			() -> new SilkMothNestBlock(BlockBehaviour.Properties.of(Material.WOOL).strength(0.5f, 0.1f).sound(SoundType.WOOL).noOcclusion().randomTicks()));
	
	//PATHS
	public static final RegistryObject<Block> CRYSTAL_MOSS_PATH = registerBlockWithDefaultItem("crystal_moss_path",
			() -> new PathBlock(CRYSTAL_MOSS.get()));
	
	public static final RegistryObject<Block> END_MYCELIUM_PATH = registerBlockWithDefaultItem("end_mycelium_path", 
			() -> new PathBlock(END_MYCELIUM.get()));
	
	public static final RegistryObject<Block> END_MOSS_PATH = registerBlockWithDefaultItem("end_moss_path", 
			() -> new PathBlock(END_MOSS.get()));
	
	public static final RegistryObject<Block> CHORUS_NYLIUM_PATH = registerBlockWithDefaultItem("chorus_nylium_path", 
			() -> new PathBlock(CHORUS_NYLIUM.get()));
	
	public static final RegistryObject<Block> CAVE_MOSS_PATH = registerBlockWithDefaultItem("cave_moss_path", 
			() -> new PathBlock(CAVE_MOSS.get()));
	
	public static final RegistryObject<Block> SHADOW_GRASS_PATH = registerBlockWithDefaultItem("shadow_grass_path", 
			() -> new PathBlock(SHADOW_GRASS.get()));
	
	public static final RegistryObject<Block> PINK_MOSS_PATH = registerBlockWithDefaultItem("pink_moss_path", 
			() -> new PathBlock(PINK_MOSS.get()));
	
	public static final RegistryObject<Block> AMBER_MOSS_PATH = registerBlockWithDefaultItem("amber_moss_path", 
			() -> new PathBlock(AMBER_MOSS.get()));
	
	public static final RegistryObject<Block> JUNGLE_MOSS_PATH = registerBlockWithDefaultItem("jungle_moss_path", 
			() -> new PathBlock(JUNGLE_MOSS.get()));
	
	public static final RegistryObject<Block> SANGNUM_PATH = registerBlockWithDefaultItem("sangnum_path", 
			() -> new PathBlock(SANGNUM.get()));
	
	public static final RegistryObject<Block> RUTISCUS_PATH = registerBlockWithDefaultItem("rutiscus_path", 
			() -> new PathBlock(RUTISCUS.get()));
	
	
	public static final RegistryObject<Block> MOSSY_OBSIDIAN = registerBlockWithDefaultItem("mossy_obsidian", 
			() -> new MossyObsidianBlock());
	
	public static final RegistryObject<RotatedPillarBlock> DRAGON_BONE_BLOCK = registerBlockWithDefaultItem("dragon_bone_block", 
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK)));
	
	public static final RegistryObject<StairBlock> DRAGON_BONE_STAIRS = registerBlockWithDefaultItem("dragon_bone_stairs", 
			() -> new StairBlock(() -> DRAGON_BONE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(DRAGON_BONE_BLOCK.get())));
	
	public static final RegistryObject<SlabBlock> DRAGON_BONE_SLAB = registerBlockWithDefaultItem("dragon_bone_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(DRAGON_BONE_BLOCK.get())));

	public static final RegistryObject<Block> MOSSY_DRAGON_BONE = registerBlockWithDefaultItem("mossy_dragon_bone", 
			() -> new MossyDragonBoneBlock());

	// MATERIALS
	public static final RegistryObject<Block> AETERNIUM_BLOCK = registerBlockWithDefaultItem("aeternium_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).
					                                 strength(65F, 1200F).
					                                 requiresCorrectToolForDrops().
					                                 sound(SoundType.NETHERITE_BLOCK)));
	
	public static final RegistryObject<Block> ENDER_BLOCK = registerBlockWithDefaultItem("ender_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).
					                                 strength(5F, 6F).
					                                 requiresCorrectToolForDrops().
					                                 sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> AURORA_CRYSTAL = registerBlockWithDefaultItem("aurora_crystal", 
			() -> new AuroraCrystalBlock(BlockBehaviour.Properties.of(Material.GLASS).
					                                              harvestTool(ToolType.PICKAXE).
					                                              strength(1F).
					                                              sound(SoundType.GLASS).
					                                              lightLevel((value) -> {return 15;}).
					                                              isSuffocating((state, world, pos) -> { return false; }).
					                                              isRedstoneConductor((state, world, pos) -> { return false; }).
					                                              noOcclusion()));
	
	public static final RegistryObject<Block> AMBER_BLOCK = registerBlockWithDefaultItem("amber_block", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).
					requiresCorrectToolForDrops().
					strength(5.0F, 6.0F).
					sound(SoundType.METAL)));
	
	public static final RegistryObject<Block> SMARAGDANT_CRYSTAL = registerBlockWithDefaultItem("smaragdant_crystal", 
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GREEN).
					                                 harvestTool(ToolType.PICKAXE).
					                                 strength(1F).
					                                 lightLevel((value) -> {return 15;}).
					                                 sound(SoundType.GLASS)));
	
	public static final RegistryObject<Block> SMARAGDANT_CRYSTAL_SHARD = registerBlockWithDefaultItem("smaragdant_crystal_shard", 
			() -> new SmaragdantCrystalShardBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GREEN).
					                                                       harvestTool(ToolType.PICKAXE).
					                                                       requiresCorrectToolForDrops().
					                                                       lightLevel((value) -> {return 15;}).
					                                                       noCollission().     
					                                                       sound(SoundType.GLASS)));
	
	// LANTERNS
	public static final RegistryObject<Block> ANDESITE_LANTERN = registerBlockWithDefaultItem("andesite_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE).
                    lightLevel(s -> 15)));
	public static final RegistryObject<Block> DIORITE_LANTERN = registerBlockWithDefaultItem("diorite_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE).
                    lightLevel(s -> 15)));
	public static final RegistryObject<Block> GRANITE_LANTERN = registerBlockWithDefaultItem("granite_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE).
                    lightLevel(s -> 15)));
	public static final RegistryObject<Block> QUARTZ_LANTERN = registerBlockWithDefaultItem("quartz_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).
                    lightLevel(s -> 15)));
	public static final RegistryObject<Block> PURPUR_LANTERN = registerBlockWithDefaultItem("purpur_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK).
                    lightLevel(s -> 15)));
	public static final RegistryObject<Block> END_STONE_LANTERN = registerBlockWithDefaultItem("end_stone_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).
                    lightLevel(s -> 15)));
	public static final RegistryObject<Block> BLACKSTONE_LANTERN = registerBlockWithDefaultItem("blackstone_lantern", 
			() -> new ModLanternBlock(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE).
                    lightLevel(s -> 15)));  
	  
    public static final RegistryObject<Block> IRON_BULB_LANTERN = registerBlockWithDefaultItem("iron_bulb_lantern", 
    		() -> new BulbVineLanternBlock());
    
	public static final RegistryObject<Block> IRON_CHANDELIER = registerBlockWithDefaultItem("iron_chandelier", 
			() -> new ChandelierBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).
					                                           noCollission().
					                                           requiresCorrectToolForDrops().
					                                           lightLevel((state) -> 15)));
	
	public static final RegistryObject<Block> GOLD_CHANDELIER = registerBlockWithDefaultItem("gold_chandelier", 
			() -> new ChandelierBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).
					                                           noCollission().
					                                           requiresCorrectToolForDrops().
					                                           lightLevel((state) -> 15)));
	
	// ORES
	public static final RegistryObject<Block> ENDER_ORE = registerBlockWithDefaultItem("ender_ore",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).
					                                 strength(3F, 9F).
					                                 requiresCorrectToolForDrops().
					                                 sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> AMBER_ORE = registerBlockWithDefaultItem("amber_ore",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).
					                                 strength(3F, 9F).
					                                 requiresCorrectToolForDrops().
					                                 sound(SoundType.STONE)));
	
	// ROCKS	
	public static final RegistryObject<Block> BRIMSTONE = registerBlockWithDefaultItem("brimstone",
			() -> new BrimstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).
					                                          requiresCorrectToolForDrops().
					                                          strength(3.0F, 9.0F).
					                                          randomTicks()));
	
	public static final RegistryObject<Block> SULPHUR_CRYSTAL = registerBlockWithDefaultItem("sulphur_crystal",
			() -> new SulphurCrystalBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).
					                                          requiresCorrectToolForDrops().
					                                          noCollission().
					                                          sound(SoundType.GLASS)));
	
	public static final RegistryObject<Block> HYDROTHERMAL_VENT = registerBlockWithDefaultItem("hydrothermal_vent",
			() -> new HydrothermalVentBlock(BlockBehaviour.Properties.of(Material.STONE).
                                                                     requiresCorrectToolForDrops().
                                                                     randomTicks().
                                                                     noCollission().
                                                                     sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> FLAVOLITE_RUNED = registerBlockWithDefaultItem("flavolite_runed",
			() -> new RunedFlavoliteBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).
					                                               requiresCorrectToolForDrops().
					                                               strength(3.0F, Blocks.OBSIDIAN.getExplosionResistance())));
	
	public static final RegistryObject<Block> FLAVOLITE_RUNED_ETERNAL = registerBlockWithDefaultItem("flavolite_runed_eternal", 
			() -> new RunedFlavoliteBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).
					                                               strength(-1.0F, 3600000.0F).
					                                               noDrops()));
	
	public static final RegistryObject<Block> QUARTZ_PEDESTAL = registerBlockWithDefaultItem("quartz_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).lightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> PURPUR_PEDESTAL = registerBlockWithDefaultItem("purpur_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK).lightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> ANDESITE_PEDESTAL = registerBlockWithDefaultItem("andesite_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE).lightLevel(PedestalBlock.light())));

	public static final RegistryObject<Block> DIORITE_PEDESTAL = registerBlockWithDefaultItem("diorite_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE).lightLevel(PedestalBlock.light())));

	public static final RegistryObject<Block> GRANITE_PEDESTAL = registerBlockWithDefaultItem("granite_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE).lightLevel(PedestalBlock.light())));

	public static final RegistryObject<Block> END_STONE_STALACTITE = registerBlockWithDefaultItem("end_stone_stalactite", 
			() -> new StalactiteBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).noOcclusion()));
	
	public static final RegistryObject<Block> END_STONE_STALACTITE_CAVEMOSS = registerBlockWithDefaultItem("end_stone_stalactite_cavemoss", 
			() -> new StalactiteBlock(BlockBehaviour.Properties.copy(CAVE_MOSS.get()).noOcclusion()));		
	
	// PLANTS
	public static final RegistryObject<Block> UMBRELLA_MOSS = registerBlockWithDefaultItem("umbrella_moss", 
			() -> new UmbrellaMossBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             lightLevel((value) -> {return 11;}).
					                                             noCollission().
					                                             sound(SoundType.GRASS).
					                                             emissiveRendering((state, reader, pos) -> {return true;})));
			
	public static final RegistryObject<Block> UMBRELLA_MOSS_TALL = registerBlockWithDefaultItem("umbrella_moss_tall", 
			() -> new UmbrellaMossTallBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                                 instabreak().
                                                                     lightLevel((state) -> { return state.getValue(UmbrellaMossTallBlock.TOP) ? 12 : 0; }).
                                                                     noCollission().
                                                                     sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> CREEPING_MOSS = registerBlockWithDefaultItem("creeping_moss",
			() -> new GlowingMossBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                instabreak().
                                                                lightLevel((value) -> {return 11;}).
                                                                noCollission().
                                                                sound(SoundType.GRASS).
                                                                emissiveRendering((state, reader, pos) -> {return true;})));
	
	public static final RegistryObject<Block> CHORUS_GRASS = registerBlockWithDefaultItem("chorus_grass", 
			() -> new ChorusGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).
                                                                instabreak().
                                                                noCollission().
                                                                sound(SoundType.GRASS)));

	public static final RegistryObject<Block> CHARCOAL_BLOCK = registerBlockWithNoItem("charcoal_block",
			() -> new CharcoalBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).
					strength(3.0F, 5.0F).
					sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> CAVE_GRASS = registerBlockWithDefaultItem("cave_grass", 
			() -> new TerrainPlantBlock(CAVE_MOSS.get()));
	
	public static final RegistryObject<Block> CRYSTAL_GRASS = registerBlockWithDefaultItem("crystal_grass", 
			() -> new TerrainPlantBlock(CRYSTAL_MOSS.get()));
	
	public static final RegistryObject<Block> AMBER_GRASS = registerBlockWithDefaultItem("amber_grass", 
			() -> new TerrainPlantBlock(AMBER_MOSS.get()));
	
	public static final RegistryObject<Block> SHADOW_PLANT = registerBlockWithDefaultItem("shadow_plant", 
			() -> new TerrainPlantBlock(SHADOW_GRASS.get()));
	
	public static final RegistryObject<Block> BUSHY_GRASS = registerBlockWithDefaultItem("bushy_grass", 
			() -> new TerrainPlantBlock(PINK_MOSS.get()));
	
	public static final RegistryObject<Block> JUNGLE_GRASS = registerBlockWithDefaultItem("jungle_grass", 
			() -> new TerrainPlantBlock(JUNGLE_MOSS.get()));
	
	public static final RegistryObject<Block> BLOOMING_COOKSONIA = registerBlockWithDefaultItem("blooming_cooksonia", 
			() -> new TerrainPlantBlock(END_MOSS.get()));
	
	public static final RegistryObject<Block> SALTEAGO = registerBlockWithDefaultItem("salteago", 
			() -> new TerrainPlantBlock(END_MOSS.get()));
	
	public static final RegistryObject<Block> VAIOLUSH_FERN = registerBlockWithDefaultItem("vaiolush_fern", 
			() -> new TerrainPlantBlock(END_MOSS.get()));
	
	public static final RegistryObject<Block> FRACTURN = registerBlockWithDefaultItem("fracturn", 
			() -> new TerrainPlantBlock(END_MOSS.get()));
	
	public static final RegistryObject<Block> GLOBULAGUS = registerBlockWithDefaultItem("globulagus", 
			() -> new TerrainPlantBlock(SANGNUM.get(), MOSSY_OBSIDIAN.get(), MOSSY_DRAGON_BONE.get()));

	public static final RegistryObject<Block> INFLEXIA = registerBlockWithDefaultItem("inflexia",
			() -> new TerrainPlantBlock(PALLIDIUM_FULL.get(), PALLIDIUM_HEAVY.get(), PALLIDIUM_THIN.get(), PALLIDIUM_TINY.get()));

	public static final RegistryObject<Block> CLAWFERN = registerBlockWithDefaultItem("clawfern", 
			() -> new TerrainPlantBlock(SANGNUM.get(), MOSSY_OBSIDIAN.get(), MOSSY_DRAGON_BONE.get()));

	public static final RegistryObject<Block> AERIDIUM = registerBlockWithDefaultItem("aeridium", 
			() -> new TerrainPlantBlock(RUTISCUS.get()));
	
	public static final RegistryObject<Block> ORANGO = registerBlockWithDefaultItem("orango", 
			() -> new TerrainPlantBlock(RUTISCUS.get()));
	
	public static final RegistryObject<Block> LUTEBUS = registerBlockWithDefaultItem("lutebus", 
			() -> new TerrainPlantBlock(RUTISCUS.get()));
	
	public static final RegistryObject<Block> LAMELLARIUM = registerBlockWithDefaultItem("lamellarium", 
			() -> new TerrainPlantBlock(RUTISCUS.get()));
	
	public static final RegistryObject<Block> BOLUX_MUSHROOM = registerBlockWithDefaultItem("bolux_mushroom", 
			() -> new BoluxMushroomBlock());


	
	public static final RegistryObject<Block> BLUE_VINE_SEED = registerBlockWithDefaultItem("blue_vine_seed", 
			() -> new BlueVineSeedBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             randomTicks().
					                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> BLUE_VINE = registerBlock("blue_vine", 
			() -> new BlueVineBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                             instabreak().
                                                             noCollission().
                                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> BLUE_VINE_LANTERN = registerBlockWithDefaultItem("blue_vine_lantern", 
			() -> new BlueVineLanternBlock(BlockBehaviour.Properties.of(Material.WOOD).
                                                                    instabreak().
                                                                    harvestTool(ToolType.AXE).
                                                                    requiresCorrectToolForDrops().
                                                                    lightLevel((value) -> {return 15;}).
                                                                    sound(SoundType.WART_BLOCK)));
	
	public static final RegistryObject<Block> BLUE_VINE_FUR = registerBlockWithDefaultItem("blue_vine_fur", 
			() -> new FurBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).
                                                               instabreak().
                                                               lightLevel((value) -> {return 15;}).
                                                               noCollission().
                                                               sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> CAVE_BUSH = registerBlockWithDefaultItem("cave_bush",
			() -> new Block(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_MAGENTA).
                                                           strength(0.2F).randomTicks().
                                                           sound(SoundType.GRASS).
                                                           noOcclusion().
                                                           isSuffocating((state, world, pos) -> { return false;}).
                                                           isValidSpawn((state, reader, pos, entity) -> {return false;}).
                                                           isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> END_LILY_SEED = registerBlockWithDefaultItem("end_lily_seed",
			() -> new EndLilySeedBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                            sound(SoundType.WET_GRASS).
					                                            randomTicks().
					                                            noCollission().
					                                            instabreak()));
	
	public static final RegistryObject<Block> END_LILY = registerBlock("end_lily",
			() -> new EndLilyBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission().
					                                        lightLevel((state) -> {return state.getValue(EndLilyBlock.SHAPE) == TripleShape.TOP ? 13 : 0;}).
					                                        instabreak()));
	
	public static final RegistryObject<Block> END_LOTUS_SEED = registerBlockWithDefaultItem("end_lotus_seed",
			() -> new EndLotusSeedBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                             sound(SoundType.WET_GRASS).
					                                             randomTicks().
					                                             noCollission().
					                                             instabreak()));
	
	public static final RegistryObject<Block> END_LOTUS_STEM = registerBlockWithDefaultItem("end_lotus_stem",
			() -> new EndLotusStemBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
	
	public static final RegistryObject<Block> END_LOTUS_LEAF = registerBlock("end_lotus_leaf",
			() -> new EndLotusLeafBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             isRedstoneConductor((state, world, pos) -> { return false; }).
					                                             noOcclusion().
					                                             sound(SoundType.WET_GRASS)));
		
	public static final RegistryObject<Block> END_LOTUS_FLOWER = registerBlock("end_lotus_flower",
			() -> new EndLotusFlowerBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                               isRedstoneConductor((state, world, pos) -> { return false; }).
					                                               lightLevel((value) -> {return 15;})));
	
	public static final RegistryObject<Block> BUBBLE_CORAL = registerBlockWithDefaultItem("bubble_coral",
			() -> new BubbleCoralBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                            instabreak().
					                                            sound(SoundType.CORAL_BLOCK).
					                                            noOcclusion()));
	
	public static final RegistryObject<Block> MURKWEED = registerBlockWithDefaultItem("murkweed", 
			() -> new MurkweedBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> NEEDLEGRASS = registerBlockWithDefaultItem("needlegrass", 
			() -> new NeedlegrassBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> MENGER_SPONGE = registerBlockWithDefaultItem("menger_sponge",
			() -> new MengerSpongeBlock(BlockBehaviour.Properties.copy(Blocks.SPONGE).noOcclusion()));
	
	public static final RegistryObject<Block> MENGER_SPONGE_WET = registerBlockWithDefaultItem("menger_sponge_wet",
			() -> new MengerSpongeWetBlock(BlockBehaviour.Properties.copy(Blocks.WET_SPONGE).noOcclusion()));
	
	public static final RegistryObject<Block> CHARNIA_RED = registerBlockWithDefaultItem("charnia_red",
			() -> new CharniaBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        instabreak().
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission()));
	
	public static final RegistryObject<Block> CHARNIA_PURPLE = registerBlockWithDefaultItem("charnia_purple",
			() -> new CharniaBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        instabreak().
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission()));
	
	public static final RegistryObject<Block> CHARNIA_ORANGE = registerBlockWithDefaultItem("charnia_orange",
			() -> new CharniaBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        instabreak().
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission()));
	
	public static final RegistryObject<Block> CHARNIA_LIGHT_BLUE = registerBlockWithDefaultItem("charnia_light_blue",
			() -> new CharniaBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        instabreak().
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission()));
	
	public static final RegistryObject<Block> CHARNIA_CYAN = registerBlockWithDefaultItem("charnia_cyan",
			() -> new CharniaBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        instabreak().
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission()));
	
	public static final RegistryObject<Block> CHARNIA_GREEN = registerBlockWithDefaultItem("charnia_green",
			() -> new CharniaBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                        instabreak().
					                                        sound(SoundType.WET_GRASS).
					                                        noCollission()));
	
	public static final RegistryObject<Block> FLAMAEA = registerBlock("flamaea",
			() -> new FlamaeaBlock());
	
	public static final RegistryObject<Block> HYDRALUX_SAPLING = registerBlockWithDefaultItem("hydralux_sapling",
			() -> new HydraluxSaplingBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                                instabreak().
					                                                sound(SoundType.WET_GRASS).
					                                                randomTicks().
					                                                noCollission()));
	
	public static final RegistryObject<Block> HYDRALUX = registerBlock("hydralux",
			() -> new HydraluxBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
					                                                instabreak().
					                                                sound(SoundType.WET_GRASS).
					                                                lightLevel((state) -> {return (state.getValue(HydraluxBlock.SHAPE).hasGlow()) ? 15 : 0;}).
					                                                noCollission()));
	
	public static final RegistryObject<Block> HYDRALUX_PETAL_BLOCK = registerBlockWithDefaultItem("hydralux_petal_block",
			() -> new HydraluxPetalBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.PLANT).
					                                              sound(SoundType.WART_BLOCK).
					                                              strength(1F).
					                                              harvestTool(ToolType.AXE)));
	
	public static final RegistryObject<Block> LANCELEAF_SEED = registerBlockWithDefaultItem("lanceleaf_seed", 
			() -> new LanceleafSeedBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             randomTicks().
					                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> LANCELEAF = registerBlock("lanceleaf", 
			() -> new LanceleafBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                             instabreak().
                                                             noCollission().
                                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> LUMECORN_SEED = registerBlockWithDefaultItem("lumecorn_seed", 
			() -> new LumecornSeedBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             randomTicks().
					                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> LUMECORN = registerBlock("lumecorn", 
			() -> new LumecornBlock(BlockBehaviour.Properties.of(Material.WOOD).
												                 harvestTool(ToolType.AXE).
												                 strength(0.5f).
												                 lightLevel(s -> s.getValue(LumecornBlock.SHAPE).getLight()).
                                                                 sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_SEED = registerBlockWithDefaultItem("glowing_pillar_seed", 
			() -> new GlowingPillarSeedBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             randomTicks().
					                                             sound(SoundType.GRASS).
					                                             lightLevel((s) -> s.getValue(PlantBlockWithAge.AGE) * 3 + 3)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_ROOTS = registerBlock("glowing_pillar_roots", 
			() -> new GlowingPillarRootsBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                             instabreak().
                                                             noCollission().
                                                             sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_LUMINOPHOR = registerBlockWithDefaultItem("glowing_pillar_luminophor", 
			() -> new GlowingPillarLuminophorBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_ORANGE).
					                                             sound(SoundType.GRASS).
					                                             strength(0.2f).
					                                             lightLevel((s) -> 15)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_LEAVES = registerBlockWithDefaultItem("glowing_pillar_leaves", 
			() -> new FurBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).
					                                           instabreak().
					                                           lightLevel((value) -> 15).
					                                           noCollission().
					                                           sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> TWISTED_UMBRELLA_MOSS = registerBlockWithDefaultItem("twisted_umbrella_moss", 
			() -> new TwistedUmbrellaMossBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             lightLevel((value) -> {return 11;}).
					                                             noCollission().
					                                             sound(SoundType.GRASS).
					                                             emissiveRendering((state, reader, pos) -> {return true;})));
	
	public static final RegistryObject<Block> TWISTED_UMBRELLA_MOSS_TALL = registerBlockWithDefaultItem("twisted_umbrella_moss_tall", 
			() -> new TwistedUmbrellaMossTallBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                                 instabreak().
                                                                     lightLevel((state) -> { return state.getValue(TwistedUmbrellaMossTallBlock.TOP) ? 12 : 0; }).
                                                                     noCollission().
                                                                     sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> SMALL_JELLYSHROOM = registerBlockWithDefaultItem("small_jellyshroom", 
			() -> new SmallJellyshroomBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                                 instabreak().
                                                                     noCollission().
                                                                     sound(SoundType.NETHER_WART)));
	
	public static final RegistryObject<Block> NEON_CACTUS = registerBlockWithDefaultItem("neon_cactus", 
			() -> new NeonCactusPlantBlock());
	
	public static final RegistryObject<Block> NEON_CACTUS_BLOCK = registerBlockWithDefaultItem("neon_cactus_block", 
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS).lightLevel(s -> { return 15; } )));
	
	public static final RegistryObject<Block> NEON_CACTUS_BLOCK_STAIRS = registerBlockWithDefaultItem("neon_cactus_stairs", 
			() -> new StairBlock(() -> NEON_CACTUS_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CACTUS).lightLevel(s -> { return 15; })));
	
	public static final RegistryObject<Block> NEON_CACTUS_BLOCK_SLAB = registerBlockWithDefaultItem("neon_cactus_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS).lightLevel(s -> { return 15; })));	
	// CROPS
	public static final RegistryObject<Block> SHADOW_BERRY = registerBlockWithDefaultItem("shadow_berry", 
			() -> new ShadowBerryBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             instabreak().
					                                             noCollission().
					                                             randomTicks().
					                                             sound(SoundType.GRASS)));


	
	public static final RegistryObject<Block> BLOSSOM_BERRY = registerBlockWithDefaultItem("blossom_berry_seed", 
			() -> new EndCropBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                            instabreak().
                                                            noCollission().
                                                            randomTicks().
                                                            sound(SoundType.GRASS),
                                                            PINK_MOSS.get()));
	
	public static final RegistryObject<Block> AMBER_ROOT = registerBlockWithDefaultItem("amber_root_seed", 
			() -> new EndCropBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                            instabreak().
                                                            noCollission().
                                                            randomTicks().
                                                            sound(SoundType.GRASS),
                                                            AMBER_MOSS.get()));
	
	public static final RegistryObject<Block> CHORUS_MUSHROOM = registerBlockWithDefaultItem("chorus_mushroom_seed", 
			() -> new EndCropBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                            instabreak().
                                                            noCollission().
                                                            randomTicks().
                                                            sound(SoundType.GRASS),
                                                            CHORUS_NYLIUM.get()));
	
//	public static final RegistryObject<Block> PEARLBERRY = registerBlockWithDefaultItem("pearlberry_seed",
//			() -> new EndCropBlock(AbstractBlock.Properties.create(Material.PLANTS).
//                                                            zeroHardnessAndResistance().
//                                                            doesNotBlockMovement().
//                                                            tickRandomly().
//                                                            sound(SoundType.PLANT),
//                                                            END_MOSS.get(), END_MYCELIUM.get()));



	// WALL PLANTS
	public static final RegistryObject<Block> PURPLE_POLYPORE = registerBlockWithDefaultItem("purple_polypore", 
			() -> new WallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             harvestTool(ToolType.AXE).
					                                             strength(0.2F).
                                                                 noCollission().
                                                                 sound(SoundType.WOOD).
                                                                 lightLevel((value) -> {return 13;})));
	
	public static final RegistryObject<Block> AURANT_POLYPORE = registerBlockWithDefaultItem("aurant_polypore", 
			() -> new WallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                             harvestTool(ToolType.AXE).
					                                             strength(0.2F).
                                                                 noCollission().
                                                                 sound(SoundType.WOOD).
                                                                 lightLevel((value) -> {return 13;})));
	
	public static final RegistryObject<Block> TAIL_MOSS = registerBlockWithDefaultItem("tail_moss", 
			() -> new WallPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                              instabreak().
                                                              noCollission().
                                                              sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> CYAN_MOSS = registerBlockWithDefaultItem("cyan_moss", 
			() -> new WallPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                              instabreak().
                                                              noCollission().
                                                              sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> TWISTED_MOSS = registerBlockWithDefaultItem("twisted_moss", 
			() -> new WallPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                              instabreak().
                                                              noCollission().
                                                              sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> BULB_MOSS = registerBlockWithDefaultItem("bulb_moss", 
			() -> new WallPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                              instabreak().
                                                              noCollission().
                                                              sound(SoundType.GRASS).
                                                              lightLevel((a) -> 12)));
	
	public static final RegistryObject<Block> TUBE_WORM = registerBlockWithDefaultItem("tube_worm", 
			() -> new UnderwaterWallPlantBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).
                                                                        instabreak().
                                                                        noCollission().
                                                                        sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> JUNGLE_FERN = registerBlockWithDefaultItem("jungle_fern", 
			() -> new WallPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                              instabreak().
                                                              noCollission().
                                                              sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> RUSCUS = registerBlockWithDefaultItem("ruscus", 
			() -> new WallPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                              instabreak().
                                                              noCollission().
                                                              sound(SoundType.GRASS)));

	
	public static final RegistryObject<Block> POND_ANEMONE = registerBlockWithDefaultItem("pond_anemone", 
			() -> new PondAnemoneBlock());
	
	// VINES
	public static final RegistryObject<Block> DENSE_VINE = registerBlockWithDefaultItem("dense_vine",
			() -> new EndVineBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                        instabreak().
					                                        noCollission().
					                                        lightLevel((state) -> {return state.getValue(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;}).
					                                        sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> TWISTED_VINE = registerBlockWithDefaultItem("twisted_vine",
			() -> new EndVineBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                        instabreak().
					                                        noCollission().
					                                        sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> BULB_VINE = registerBlockWithDefaultItem("bulb_vine",
			() -> new BulbVineBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                        instabreak().
					                                        noCollission().
					                                        lightLevel((state) -> {return state.getValue(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;}).
					                                        sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> BULB_VINE_SEED = registerBlockWithDefaultItem("bulb_vine_seed",
			() -> new BulbVineSeedBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                        instabreak().
					                                        noCollission().
					                                        randomTicks().
					                                        sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> JUNGLE_VINE = registerBlockWithDefaultItem("jungle_vine",
			() -> new EndVineBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                        instabreak().
					                                        noCollission().
					                                        sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> RUBINEA = registerBlockWithDefaultItem("rubinea", 
			() -> new EndVineBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                            instabreak().
                                                            noCollission().
                                                            sound(SoundType.GRASS)));
	
	// TREES
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_SAPLING = registerBlockWithDefaultItem("mossy_glowshroom_sapling", 
			() -> new MossyGlowshroomSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                                       instabreak().
					                                                       noCollission().
					                                                       sound(SoundType.GRASS).
					                                                       lightLevel((value) -> {return 7;}).
					                                                       randomTicks()));
	
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_CAP = registerBlockWithDefaultItem("mossy_glowshroom_cap", 
			() -> new MossyGlowshroomCapBlock(BlockBehaviour.Properties.of(Material.WOOD).
					                                                   harvestTool(ToolType.AXE).
					                                                   requiresCorrectToolForDrops().
					                                                   sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_HYMENOPHORE = registerBlockWithDefaultItem("mossy_glowshroom_hymenophore", 
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).
                                                     harvestTool(ToolType.AXE).
                                                     requiresCorrectToolForDrops().
                                                     sound(SoundType.WART_BLOCK).
                                                     lightLevel((value) -> {return 15;})));
	
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_FUR = registerBlockWithDefaultItem("mossy_glowshroom_fur", 
			() -> new FurBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).
					                                           instabreak().
					                                           lightLevel((value) -> {return 15;}).
					                                           noCollission().
					                                           sound(SoundType.WET_GRASS)));
		
	public static final RegistryObject<Block> LACUGROVE_SAPLING = registerBlockWithDefaultItem("lacugrove_sapling", 
			() -> new LacugroveSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                     instabreak().
                                                                     noCollission().
                                                                     sound(SoundType.GRASS).
                                                                     randomTicks()));
	
	public static final RegistryObject<Block> LACUGROVE_LEAVES = registerBlockWithDefaultItem("lacugrove_leaves", 
			() -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_CYAN).
                                                           strength(0.2F).randomTicks().
					                                       sound(SoundType.GRASS).
					                                       noOcclusion().
					                                       isSuffocating((state, world, pos) -> { return false; }).
					                                       isValidSpawn((state, reader, pos, entity) -> {return false;}).
					                                       isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> PYTHADENDRON_SAPLING = registerBlockWithDefaultItem("pythadendron_sapling", 
			() -> new PythadendronSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                        instabreak().
                                                                        noCollission().
                                                                        sound(SoundType.GRASS).
                                                                        randomTicks()));
	
	public static final RegistryObject<Block> PYTHADENDRON_LEAVES = registerBlockWithDefaultItem("pythadendron_leaves", 
			() -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_MAGENTA).
					                                       strength(0.2F).
					                                       randomTicks().
                                                           sound(SoundType.GRASS).
                                                           noOcclusion().
                                                           isSuffocating((state, world, pos) -> { return false; }).
                                                           isValidSpawn((state, reader, pos, entity) -> {return false;}).
                                                           isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> DRAGON_TREE_SAPLING = registerBlockWithDefaultItem("dragon_tree_sapling", 
			() -> new DragonTreeSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                      instabreak().
                                                                      noCollission().
                                                                      sound(SoundType.GRASS).
                                                                      randomTicks()));
	
	public static final RegistryObject<Block> DRAGON_TREE_LEAVES = registerBlockWithDefaultItem("dragon_tree_leaves", 
			() -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_MAGENTA).
					                                       strength(0.2F).
					                                       randomTicks().
                                                           sound(SoundType.GRASS).
                                                           noOcclusion().
                                                           isSuffocating((state, world, pos) -> { return false; }).
                                                           isValidSpawn((state, reader, pos, entity) -> {return false;}).
                                                           isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> TENANEA_SAPLING = registerBlockWithDefaultItem("tenanea_sapling", 
			() -> new TenaneaSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                   instabreak().
                                                                   noCollission().
                                                                   sound(SoundType.GRASS).
                                                                   randomTicks()));
	
	public static final RegistryObject<Block> TENANEA_LEAVES = registerBlockWithDefaultItem("tenanea_leaves", 
			() -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_PINK).
					                                       strength(0.2F).
					                                       randomTicks().
                                                           sound(SoundType.GRASS).
                                                           noOcclusion().
                                                           isSuffocating((state, world, pos) -> { return false; }).
                                                           isValidSpawn((state, reader, pos, entity) -> {return false;}).
                                                           isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> TENANEA_FLOWERS = registerBlockWithDefaultItem("tenanea_flowers",
			() -> new TenaneaFlowersBlock(BlockBehaviour.Properties.of(Material.PLANT).
					                                               instabreak().
					                                               noCollission().
					                                               lightLevel((state) -> {return 15;}).
					                                               sound(SoundType.GRASS)));
	
	public static final RegistryObject<Block> TENANEA_OUTER_LEAVES = registerBlockWithDefaultItem("tenanea_outer_leaves", 
			() -> new FurBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).
					                                           instabreak().
					                                           noCollission().
					                                           sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> HELIX_TREE_SAPLING = registerBlockWithDefaultItem("helix_tree_sapling",
			() -> new HelixTreeSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                   instabreak().
                                                                   noCollission().
                                                                   sound(SoundType.GRASS).
                                                                   randomTicks()));
	
	public static final RegistryObject<Block> HELIX_TREE_LEAVES = registerBlockWithDefaultItem("helix_tree_leaves",
			() -> new HelixTreeLeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_ORANGE).
					                                       strength(0.2F).
					                                       randomTicks().
                                                           sound(SoundType.NETHER_WART).
                                                           noOcclusion().
                                                           isSuffocating((state, world, pos) -> { return false; }).
                                                           isValidSpawn((state, reader, pos, entity) -> {return false;}).
                                                           isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_SAPLING = registerBlockWithDefaultItem("umbrella_tree_sapling",
			() -> new UmbrellaTreeSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                                                                   instabreak().
                                                                   noCollission().
                                                                   sound(SoundType.GRASS).
                                                                   randomTicks()));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_MEMBRANE = registerBlockWithDefaultItem("umbrella_tree_membrane",
			() -> new UmbrellaTreeMembraneBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_CLUSTER = registerBlockWithDefaultItem("umbrella_tree_cluster",
			() -> new UmbrellaTreeClusterBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).
					                                                    strength(1.0F).
					                                                    lightLevel((value) -> {return 15;}).
					                                                    sound(SoundType.WART_BLOCK)));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_CLUSTER_EMPTY = registerBlockWithDefaultItem("umbrella_tree_cluster_empty",
			() -> new UmbrellaTreeClusterBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).
					                                                    strength(1.0F).
					                                                    randomTicks().
					                                                    sound(SoundType.WART_BLOCK)));
	
	public static final RegistryObject<Block> SMALL_AMARANITA_MUSHROOM = registerBlockWithDefaultItem("small_amaranita_mushroom",
			() -> new SmallAmaranitaBlock());
	
	public static final RegistryObject<Block> LARGE_AMARANITA_MUSHROOM = registerBlock("large_amaranita_mushroom",
			() -> new LargeAmaranitaBlock());
	
	public static final RegistryObject<Block> AMARANITA_STEM = registerBlockWithDefaultItem("amaranita_stem",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> AMARANITA_HYPHAE = registerBlockWithDefaultItem("amaranita_hyphae",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> AMARANITA_HYMENOPHORE = registerBlockWithDefaultItem("amaranita_hymenophore",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> AMARANITA_LANTERN = registerBlockWithDefaultItem("amaranita_lantern",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WART_BLOCK).lightLevel(s -> 15)));
	
	public static final RegistryObject<Block> AMARANITA_FUR = registerBlockWithDefaultItem("amaranita_fur",
			() -> new FurBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).
                    instabreak().
                    lightLevel((value) -> {return 15;}).
                    noCollission().
                    sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> AMARANITA_CAP = registerBlockWithDefaultItem("amaranita_cap",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
	
	
	public static final RegistryObject<Block> JELLYSHROOM_CAP_PURPLE = registerBlockWithDefaultItem("jellyshroom_cap_purple", 
			() -> new JellyshroomCapBlock(217, 142, 255, 164, 0, 255));
	
	// BLOCKS WITH TILE ENTITY
	public static final RegistryObject<Block> ETERNAL_PEDESTAL = registerBlockWithDefaultItem("eternal_pedestal", 
			() -> new EternalPedestal(BlockBehaviour.Properties.copy(FLAVOLITE_RUNED_ETERNAL.get()).
					                                           lightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> INFUSION_PEDESTAL = registerBlockWithDefaultItem("infusion_pedestal", 
			() -> new InfusionPedestal(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).
					                                            lightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> END_STONE_SMELTER = registerBlockWithDefaultItem("end_stone_smelter", 
			() -> new EndStoneSmelter(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).
					                                           strength(4F, 100F).
					                                           requiresCorrectToolForDrops().
					                                           sound(SoundType.STONE).
					                                           lightLevel((state) -> {
					                                        	   return state.getValue(BlockStateProperties.LIT) ? 13 : 0;}
					                                           )));
	
	public static final RegistryObject<Block> END_STONE_FURNACE = registerBlockWithDefaultItem("end_stone_furnace", 
			() -> new EndFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).
					                                           lightLevel((state) -> {
					                                        	   return state.getValue(BlockStateProperties.LIT) ? 13 : 0;}
					                                           )));
	
	// MISC
	public static final RegistryObject<Block> AETERNIUM_ANVIL = registerBlock("aeternium_anvil", 
			() -> new AeterniumAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
	
	public static final RegistryObject<Block> DENSE_SNOW = registerBlockWithDefaultItem("dense_snow", 
			() -> new Block(BlockBehaviour.Properties.of(Material.SNOW).
					                                 strength(0.2F).
					                                 sound(SoundType.SNOW)));
	
	public static final RegistryObject<Block> EMERALD_ICE = registerBlockWithDefaultItem("emerald_ice", 
			() -> new EmeraldIceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
	
	public static final RegistryObject<Block> DENSE_EMERALD_ICE = registerBlockWithDefaultItem("dense_emerald_ice", 
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
	
	public static final RegistryObject<Block> ANCIENT_EMERALD_ICE = registerBlockWithDefaultItem("ancient_emerald_ice", 
			() -> new AncientEmeraldIceBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE).
					                                                  randomTicks()));
	
	public static final RegistryObject<Block> END_PORTAL_BLOCK = registerBlock("end_portal_block", 
			() -> new EndPortalBlock(BlockBehaviour.Properties.of(Material.PORTAL).
					                                          noCollission().
					                                          randomTicks().
					                                          strength(-1.0F).
					                                          sound(SoundType.GLASS).
					                                          lightLevel((state) -> {return 12;}).
					                                          noDrops()));
	
	public static final RegistryObject<Block> RESPAWN_OBELISK = registerBlockWithDefaultItem("respawn_obelisk",
			() -> new RespawnObeliskBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).
					requiresCorrectToolForDrops().
					strength(3.0F, 9.0F).
					lightLevel((state) -> {
						return (state.getValue(RespawnObeliskBlock.SHAPE) == TripleShape.BOTTOM) ? 0 : 15;
					})));
	
	public static final RegistryObject<Block> VENT_BUBBLE_COLUMN = registerBlock("vent_bubble_column",
			() -> new VentBubbleColumnBlock(BlockBehaviour.Properties.of(Material.BUBBLE_COLUMN).
                                                                     noCollission().
                                                                     isRedstoneConductor((state, world, pos) -> { return false; }).
                                                                     noDrops()));
	
	public static final RegistryObject<Block> MISSING_TILE = registerBlockWithDefaultItem("missing_tile",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)));
	
	// FLOWER POT BLOCKS
	public static final RegistryObject<Block> POTTED_MOSSY_GLOWSHROOM_SAPLING = registerFlowerPotBlock("potted_mossy_glowshroom_sapling", MOSSY_GLOWSHROOM_SAPLING);
	public static final RegistryObject<Block> POTTED_LACUGROVE_SAPLING = registerFlowerPotBlock("potted_lacugrove_sapling", LACUGROVE_SAPLING);
	public static final RegistryObject<Block> POTTED_PYTHADENDRON_SAPLING = registerFlowerPotBlock("potted_pythadendron_sapling", PYTHADENDRON_SAPLING);
	public static final RegistryObject<Block> POTTED_DRAGON_TREE_SAPLING = registerFlowerPotBlock("potted_dragon_tree_sapling", DRAGON_TREE_SAPLING);
	public static final RegistryObject<Block> POTTED_TENANEA_SAPLING = registerFlowerPotBlock("potted_tenanea_sapling", TENANEA_SAPLING);
	public static final RegistryObject<Block> POTTED_HELIX_TREE_SAPLING = registerFlowerPotBlock("potted_helix_tree_sapling", HELIX_TREE_SAPLING);
	public static final RegistryObject<Block> POTTED_UMBRELLA_TREE_SAPLING = registerFlowerPotBlock("potted_umbrella_tree_sapling", UMBRELLA_TREE_SAPLING);

	// WOODEN MATERIALS
	private static List<WoodenMaterial> woodenMaterials;
	public static List<WoodenMaterial> getWoodenMaterials() {
		return ImmutableList.copyOf(woodenMaterials);
	}
	public static final WoodenMaterial MOSSY_GLOWSHROOM = createWoodenMaterial("mossy_glowshroom", MaterialColor.COLOR_GRAY, MaterialColor.WOOD);
	public static final WoodenMaterial LACUGROVE = createWoodenMaterial("lacugrove", MaterialColor.COLOR_BROWN, MaterialColor.COLOR_YELLOW);
	public static final WoodenMaterial END_LOTUS = createWoodenMaterial("end_lotus", MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.COLOR_CYAN);
	public static final WoodenMaterial PYTHADENDRON = createWoodenMaterial("pythadendron", MaterialColor.COLOR_MAGENTA, MaterialColor.COLOR_PURPLE);
	public static final WoodenMaterial DRAGON_TREE = createWoodenMaterial("dragon_tree", MaterialColor.COLOR_BLACK, MaterialColor.COLOR_MAGENTA);
	public static final WoodenMaterial TENANEA = createWoodenMaterial("tenanea", MaterialColor.COLOR_BROWN, MaterialColor.COLOR_PINK);
	public static final WoodenMaterial HELIX_TREE = createWoodenMaterial("helix_tree", MaterialColor.COLOR_GRAY, MaterialColor.COLOR_ORANGE);
	public static final WoodenMaterial UMBRELLA_TREE = createWoodenMaterial("umbrella_tree", MaterialColor.COLOR_BLUE, MaterialColor.COLOR_GREEN);
	public static final WoodenMaterial JELLYSHROOM = createWoodenMaterial("jellyshroom", MaterialColor.COLOR_PURPLE, MaterialColor.COLOR_LIGHT_BLUE);
	public static final WoodenMaterial LUCERNIA = createWoodenMaterial("lucernia", MaterialColor.COLOR_ORANGE, MaterialColor.COLOR_ORANGE);
	
	public static final RegistryObject<Block> LUCERNIA_SAPLING = registerBlockWithDefaultItem("lucernia_sapling",
			() -> new LucerniaSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).
                    instabreak().
                    noCollission().
                    sound(SoundType.GRASS).
                    randomTicks()));
	
	public static final RegistryObject<Block> LUCERNIA_LEAVES = registerBlockWithDefaultItem("lucernia_leaves",
			() -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_ORANGE).
                    strength(0.2F).randomTicks().
                    sound(SoundType.GRASS).
                    noOcclusion().
                    isSuffocating((state, world, pos) -> { return false; }).
                    isValidSpawn((state, reader, pos, entity) -> {return false;}).
                    isViewBlocking((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> LUCERNIA_OUTER_LEAVES = registerBlockWithDefaultItem("lucernia_outer_leaves",
			() -> new FurBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.COLOR_ORANGE).
                    instabreak().
                    lightLevel((value) -> {return 15;}).
                    noCollission().
                    sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> FILALUX = registerBlockWithDefaultItem("filalux",
			() -> new FilaluxBlock());
	public static final RegistryObject<Block> FILALUX_WINGS = registerBlockWithDefaultItem("filalux_wings",
			() -> new FilaluxWingsBlock());
	public static final RegistryObject<Block> FILALUX_LANTERN = registerBlockWithDefaultItem("filalux_lantern",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).lightLevel(b -> 15).sound(SoundType.WOOD).harvestTool(ToolType.AXE)));

	
	// STONE MATERIALS
	private static List<StoneMaterial> stoneMaterials;
	public static List<StoneMaterial> getStoneMaterials() {
		return ImmutableList.copyOf(stoneMaterials);
	}
    public static final StoneMaterial FLAVOLITE = createStoneMaterial("flavolite", MaterialColor.SAND);
    public static final StoneMaterial VIOLECITE = createStoneMaterial("violecite", MaterialColor.COLOR_PURPLE);
    public static final StoneMaterial SULPHURIC_ROCK = createStoneMaterial("sulphuric_rock", MaterialColor.COLOR_BROWN);
	public static final StoneMaterial VIRID_JADESTONE = createStoneMaterial("virid_jadestone", MaterialColor.COLOR_GREEN);
	public static final StoneMaterial AZURE_JADESTONE = createStoneMaterial("azure_jadestone", MaterialColor.COLOR_LIGHT_BLUE);
	public static final StoneMaterial SANDY_JADESTONE = createStoneMaterial("sandy_jadestone", MaterialColor.COLOR_YELLOW);
	public static final StoneMaterial UMBRALITH = new StoneMaterial("umbralith", MaterialColor.COLOR_BLACK);


	// METAL MATERIALS
	private static List<MetalMaterial> metalMaterials;
	public static List<MetalMaterial> getMetalMaterials() {
		return ImmutableList.copyOf(metalMaterials);
	}
    public static final MetalMaterial THALLASIUM = createMetalMaterial("thallasium", true, BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB), ModItemTier.THALLASIUM, ModArmorMaterial.THALLASIUM);
    public static final MetalMaterial TERMINITE = createMetalMaterial("terminite", false, BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(7, 9).sound(SoundType.METAL), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB), ModItemTier.TERMINITE, ModArmorMaterial.TERMINITE);

    
    // COLORED MATERIALS
    public static final ColoredMaterial HYDRALUX_PETAL_BLOCK_COLORED = new ColoredMaterial("hydralux_petal_block", 
    		() -> new HydraluxPetalBlockColored(), HYDRALUX_PETAL_BLOCK, true);
   
    public static final ColoredMaterial IRON_BULB_LANTERN_COLORED = new ColoredMaterial("iron_bulb_lantern", 
    		() -> new BulbVineLanternBlock(), IRON_BULB_LANTERN, false);
    
	
	//////////////////////////////////////////////////////
	//
	// Block registration helpers
	//
	/////////////////////////////////////////////////////
	
	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> blockSupplier)
	{
		return BLOCKS.register(name, blockSupplier);
	}
	
	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)));
		return block;
	}

	public static <T extends Block> RegistryObject<T> registerBlockWithNoItem(String name, Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		return block;
	}


	
	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier, 
			CreativeModeTab group)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group)));
		return block;
	}
	
	public static RegistryObject<Block> registerFlowerPotBlock(String name, Supplier<? extends Block> plant)
	{
		RegistryObject<Block> flowerPot = BLOCKS.register(name, () -> new FlowerPotBlock(plant.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
		return flowerPot;
	}
	
	public static WoodenMaterial createWoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
		if (woodenMaterials == null)
			woodenMaterials = new ArrayList<>();
		
		WoodenMaterial material = new WoodenMaterial(name, woodColor, planksColor);
		woodenMaterials.add(material);
		return material;
	}
	
	public static StoneMaterial createStoneMaterial(String name, MaterialColor color) {
		if (stoneMaterials == null)
			stoneMaterials = new ArrayList<>();
		
		StoneMaterial material = new StoneMaterial(name, color);
		stoneMaterials.add(material);
		return material;
	}
	
	public static MetalMaterial createMetalMaterial(String name, boolean hasOre, BlockBehaviour.Properties blockSettings, Item.Properties itemSettings, Tier itemTier, ArmorMaterial armor) {
		if (metalMaterials == null)
			metalMaterials = new ArrayList<>();
		
		MetalMaterial material = new MetalMaterial(name, hasOre, blockSettings, itemSettings, itemTier, armor);
		metalMaterials.add(material);
		return material;
	}
}
