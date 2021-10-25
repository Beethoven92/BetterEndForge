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
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks 
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterEnd.MOD_ID);
	
	// TERRAINS
	public static final RegistryObject<Block> CRYSTAL_MOSS = registerBlockWithDefaultItem("crystal_moss",
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK).
					                                        setRequiresTool().
					                                        hardnessAndResistance(3.0F, 9.0F).
					                                        sound(SoundType.GROUND).
					                                        tickRandomly()));
	
	public static final RegistryObject<Block> END_MYCELIUM = registerBlockWithDefaultItem("end_mycelium", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).
					                                        setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> END_MOSS = registerBlockWithDefaultItem("end_moss", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CYAN).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> CHORUS_NYLIUM = registerBlockWithDefaultItem("chorus_nylium", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.MAGENTA).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.STONE).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> CAVE_MOSS = registerBlockWithDefaultItem("cave_moss", 
			() -> new TripleTerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> SHADOW_GRASS = registerBlockWithDefaultItem("shadow_grass", 
			() -> new ShadowGrassBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).
                                                                setRequiresTool().
                                                                hardnessAndResistance(3.0F, 9.0F).
                                                                sound(SoundType.GROUND).
                                                                tickRandomly()));
	
	public static final RegistryObject<Block> PINK_MOSS = registerBlockWithDefaultItem("pink_moss", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> AMBER_MOSS = registerBlockWithDefaultItem("amber_moss", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> JUNGLE_MOSS = registerBlockWithDefaultItem("jungle_moss", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> SANGNUM = registerBlockWithDefaultItem("sangnum", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> RUTISCUS = registerBlockWithDefaultItem("rutiscus",
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).
                                                            setRequiresTool().
                                                            hardnessAndResistance(3.0F, 9.0F).
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));

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
			() -> new EndstoneDustBlock(AbstractBlock.Properties.create(Material.SAND, Blocks.END_STONE.getMaterialColor()).
					                                             hardnessAndResistance(0.5F).
					                                             sound(SoundType.SAND)));
	
	public static final RegistryObject<Block> SILK_MOTH_NEST = registerBlockWithDefaultItem("silk_moth_nest", 
			() -> new SilkMothNestBlock(AbstractBlock.Properties.create(Material.WOOL).hardnessAndResistance(0.5f, 0.1f).sound(SoundType.CLOTH).notSolid().tickRandomly()));
	
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
			() -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(2.0F).sound(SoundType.BONE)));
	
	public static final RegistryObject<StairsBlock> DRAGON_BONE_STAIRS = registerBlockWithDefaultItem("dragon_bone_stairs", 
			() -> new StairsBlock(() -> DRAGON_BONE_BLOCK.get().getDefaultState(), AbstractBlock.Properties.from(DRAGON_BONE_BLOCK.get())));
	
	public static final RegistryObject<SlabBlock> DRAGON_BONE_SLAB = registerBlockWithDefaultItem("dragon_bone_slab", 
			() -> new SlabBlock(AbstractBlock.Properties.from(DRAGON_BONE_BLOCK.get())));

	public static final RegistryObject<Block> MOSSY_DRAGON_BONE = registerBlockWithDefaultItem("mossy_dragon_bone", 
			() -> new MossyDragonBoneBlock());

	// MATERIALS
	public static final RegistryObject<Block> AETERNIUM_BLOCK = registerBlockWithDefaultItem("aeternium_block",
			() -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GRAY).
					                                 hardnessAndResistance(65F, 1200F).
					                                 setRequiresTool().
					                                 sound(SoundType.NETHERITE)));
	
	public static final RegistryObject<Block> ENDER_BLOCK = registerBlockWithDefaultItem("ender_block",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).
					                                 hardnessAndResistance(5F, 6F).
					                                 setRequiresTool().
					                                 sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> AURORA_CRYSTAL = registerBlockWithDefaultItem("aurora_crystal", 
			() -> new AuroraCrystalBlock(AbstractBlock.Properties.create(Material.GLASS).
					                                              harvestTool(ToolType.PICKAXE).
					                                              hardnessAndResistance(1F).
					                                              sound(SoundType.GLASS).
					                                              setLightLevel((value) -> {return 15;}).
					                                              setSuffocates((state, world, pos) -> { return false; }).
					                                              setOpaque((state, world, pos) -> { return false; }).
					                                              notSolid()));
	
	public static final RegistryObject<Block> AMBER_BLOCK = registerBlockWithDefaultItem("amber_block", 
			() -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).
					setRequiresTool().
					hardnessAndResistance(5.0F, 6.0F).
					sound(SoundType.METAL)));
	
	public static final RegistryObject<Block> SMARAGDANT_CRYSTAL = registerBlockWithDefaultItem("smaragdant_crystal", 
			() -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GREEN).
					                                 harvestTool(ToolType.PICKAXE).
					                                 hardnessAndResistance(1F).
					                                 setLightLevel((value) -> {return 15;}).
					                                 sound(SoundType.GLASS)));
	
	public static final RegistryObject<Block> SMARAGDANT_CRYSTAL_SHARD = registerBlockWithDefaultItem("smaragdant_crystal_shard", 
			() -> new SmaragdantCrystalShardBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GREEN).
					                                                       harvestTool(ToolType.PICKAXE).
					                                                       setRequiresTool().
					                                                       setLightLevel((value) -> {return 15;}).
					                                                       doesNotBlockMovement().     
					                                                       sound(SoundType.GLASS)));
	
	// LANTERNS
	public static final RegistryObject<Block> ANDESITE_LANTERN = registerBlockWithDefaultItem("andesite_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.ANDESITE).
                    setLightLevel(s -> 15)));
	public static final RegistryObject<Block> DIORITE_LANTERN = registerBlockWithDefaultItem("diorite_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.DIORITE).
                    setLightLevel(s -> 15)));
	public static final RegistryObject<Block> GRANITE_LANTERN = registerBlockWithDefaultItem("granite_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.GRANITE).
                    setLightLevel(s -> 15)));
	public static final RegistryObject<Block> QUARTZ_LANTERN = registerBlockWithDefaultItem("quartz_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_BLOCK).
                    setLightLevel(s -> 15)));
	public static final RegistryObject<Block> PURPUR_LANTERN = registerBlockWithDefaultItem("purpur_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.PURPUR_BLOCK).
                    setLightLevel(s -> 15)));
	public static final RegistryObject<Block> END_STONE_LANTERN = registerBlockWithDefaultItem("end_stone_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.END_STONE).
                    setLightLevel(s -> 15)));
	public static final RegistryObject<Block> BLACKSTONE_LANTERN = registerBlockWithDefaultItem("blackstone_lantern", 
			() -> new ModLanternBlock(AbstractBlock.Properties.from(Blocks.BLACKSTONE).
                    setLightLevel(s -> 15)));  
	  
    public static final RegistryObject<Block> IRON_BULB_LANTERN = registerBlockWithDefaultItem("iron_bulb_lantern", 
    		() -> new BulbVineLanternBlock());
    
	public static final RegistryObject<Block> IRON_CHANDELIER = registerBlockWithDefaultItem("iron_chandelier", 
			() -> new ChandelierBlock(AbstractBlock.Properties.from(Blocks.IRON_BLOCK).
					                                           doesNotBlockMovement().
					                                           setRequiresTool().
					                                           setLightLevel((state) -> 15)));
	
	public static final RegistryObject<Block> GOLD_CHANDELIER = registerBlockWithDefaultItem("gold_chandelier", 
			() -> new ChandelierBlock(AbstractBlock.Properties.from(Blocks.GOLD_BLOCK).
					                                           doesNotBlockMovement().
					                                           setRequiresTool().
					                                           setLightLevel((state) -> 15)));
	
	// ORES
	public static final RegistryObject<Block> ENDER_ORE = registerBlockWithDefaultItem("ender_ore",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                 hardnessAndResistance(3F, 9F).
					                                 setRequiresTool().
					                                 sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> AMBER_ORE = registerBlockWithDefaultItem("amber_ore",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                 hardnessAndResistance(3F, 9F).
					                                 setRequiresTool().
					                                 sound(SoundType.STONE)));
	
	// ROCKS	
	public static final RegistryObject<Block> BRIMSTONE = registerBlockWithDefaultItem("brimstone",
			() -> new BrimstoneBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN).
					                                          setRequiresTool().
					                                          hardnessAndResistance(3.0F, 9.0F).
					                                          tickRandomly()));
	
	public static final RegistryObject<Block> SULPHUR_CRYSTAL = registerBlockWithDefaultItem("sulphur_crystal",
			() -> new SulphurCrystalBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.YELLOW).
					                                          setRequiresTool().
					                                          doesNotBlockMovement().
					                                          sound(SoundType.GLASS)));
	
	public static final RegistryObject<Block> HYDROTHERMAL_VENT = registerBlockWithDefaultItem("hydrothermal_vent",
			() -> new HydrothermalVentBlock(AbstractBlock.Properties.create(Material.ROCK).
                                                                     setRequiresTool().
                                                                     tickRandomly().
                                                                     doesNotBlockMovement().
                                                                     sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> FLAVOLITE_RUNED = registerBlockWithDefaultItem("flavolite_runed",
			() -> new RunedFlavoliteBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                               setRequiresTool().
					                                               hardnessAndResistance(3.0F, Blocks.OBSIDIAN.getExplosionResistance())));
	
	public static final RegistryObject<Block> FLAVOLITE_RUNED_ETERNAL = registerBlockWithDefaultItem("flavolite_runed_eternal", 
			() -> new RunedFlavoliteBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                               hardnessAndResistance(-1.0F, 3600000.0F).
					                                               noDrops()));
	
	public static final RegistryObject<Block> QUARTZ_PEDESTAL = registerBlockWithDefaultItem("quartz_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_BLOCK).setLightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> PURPUR_PEDESTAL = registerBlockWithDefaultItem("purpur_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.PURPUR_BLOCK).setLightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> ANDESITE_PEDESTAL = registerBlockWithDefaultItem("andesite_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.ANDESITE).setLightLevel(PedestalBlock.light())));

	public static final RegistryObject<Block> DIORITE_PEDESTAL = registerBlockWithDefaultItem("diorite_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.DIORITE).setLightLevel(PedestalBlock.light())));

	public static final RegistryObject<Block> GRANITE_PEDESTAL = registerBlockWithDefaultItem("granite_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.GRANITE).setLightLevel(PedestalBlock.light())));

	public static final RegistryObject<Block> END_STONE_STALACTITE = registerBlockWithDefaultItem("end_stone_stalactite", 
			() -> new StalactiteBlock(AbstractBlock.Properties.from(Blocks.END_STONE).notSolid()));
	
	public static final RegistryObject<Block> END_STONE_STALACTITE_CAVEMOSS = registerBlockWithDefaultItem("end_stone_stalactite_cavemoss", 
			() -> new StalactiteBlock(AbstractBlock.Properties.from(CAVE_MOSS.get()).notSolid()));		
	
	// PLANTS
	public static final RegistryObject<Block> UMBRELLA_MOSS = registerBlockWithDefaultItem("umbrella_moss", 
			() -> new UmbrellaMossBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             setLightLevel((value) -> {return 11;}).
					                                             doesNotBlockMovement().
					                                             sound(SoundType.PLANT).
					                                             setEmmisiveRendering((state, reader, pos) -> {return true;})));
			
	public static final RegistryObject<Block> UMBRELLA_MOSS_TALL = registerBlockWithDefaultItem("umbrella_moss_tall", 
			() -> new UmbrellaMossTallBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                                 zeroHardnessAndResistance().
                                                                     setLightLevel((state) -> { return state.get(UmbrellaMossTallBlock.TOP) ? 12 : 0; }).
                                                                     doesNotBlockMovement().
                                                                     sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> CREEPING_MOSS = registerBlockWithDefaultItem("creeping_moss",
			() -> new GlowingMossBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                zeroHardnessAndResistance().
                                                                setLightLevel((value) -> {return 11;}).
                                                                doesNotBlockMovement().
                                                                sound(SoundType.PLANT).
                                                                setEmmisiveRendering((state, reader, pos) -> {return true;})));
	
	public static final RegistryObject<Block> CHORUS_GRASS = registerBlockWithDefaultItem("chorus_grass", 
			() -> new ChorusGrassBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
                                                                zeroHardnessAndResistance().
                                                                doesNotBlockMovement().
                                                                sound(SoundType.PLANT)));

	public static final RegistryObject<Block> CHARCOAL_BLOCK = registerBlockWithNoItem("charcoal_block",
			() -> new CharcoalBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).
					hardnessAndResistance(3.0F, 5.0F).
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
			() -> new BlueVineSeedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             tickRandomly().
					                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> BLUE_VINE = registerBlock("blue_vine", 
			() -> new BlueVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                             zeroHardnessAndResistance().
                                                             doesNotBlockMovement().
                                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> BLUE_VINE_LANTERN = registerBlockWithDefaultItem("blue_vine_lantern", 
			() -> new BlueVineLanternBlock(AbstractBlock.Properties.create(Material.WOOD).
                                                                    zeroHardnessAndResistance().
                                                                    harvestTool(ToolType.AXE).
                                                                    setRequiresTool().
                                                                    setLightLevel((value) -> {return 15;}).
                                                                    sound(SoundType.WART)));
	
	public static final RegistryObject<Block> BLUE_VINE_FUR = registerBlockWithDefaultItem("blue_vine_fur", 
			() -> new FurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
                                                               zeroHardnessAndResistance().
                                                               setLightLevel((value) -> {return 15;}).
                                                               doesNotBlockMovement().
                                                               sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> CAVE_BUSH = registerBlockWithDefaultItem("cave_bush",
			() -> new Block(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.MAGENTA).
                                                           hardnessAndResistance(0.2F).tickRandomly().
                                                           sound(SoundType.PLANT).
                                                           notSolid().
                                                           setSuffocates((state, world, pos) -> { return false;}).
                                                           setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
                                                           setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> END_LILY_SEED = registerBlockWithDefaultItem("end_lily_seed",
			() -> new EndLilySeedBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                            sound(SoundType.WET_GRASS).
					                                            tickRandomly().
					                                            doesNotBlockMovement().
					                                            zeroHardnessAndResistance()));
	
	public static final RegistryObject<Block> END_LILY = registerBlock("end_lily",
			() -> new EndLilyBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement().
					                                        setLightLevel((state) -> {return state.get(EndLilyBlock.SHAPE) == TripleShape.TOP ? 13 : 0;}).
					                                        zeroHardnessAndResistance()));
	
	public static final RegistryObject<Block> END_LOTUS_SEED = registerBlockWithDefaultItem("end_lotus_seed",
			() -> new EndLotusSeedBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                             sound(SoundType.WET_GRASS).
					                                             tickRandomly().
					                                             doesNotBlockMovement().
					                                             zeroHardnessAndResistance()));
	
	public static final RegistryObject<Block> END_LOTUS_STEM = registerBlockWithDefaultItem("end_lotus_stem",
			() -> new EndLotusStemBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
	
	public static final RegistryObject<Block> END_LOTUS_LEAF = registerBlock("end_lotus_leaf",
			() -> new EndLotusLeafBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             setOpaque((state, world, pos) -> { return false; }).
					                                             notSolid().
					                                             sound(SoundType.WET_GRASS)));
		
	public static final RegistryObject<Block> END_LOTUS_FLOWER = registerBlock("end_lotus_flower",
			() -> new EndLotusFlowerBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                               setOpaque((state, world, pos) -> { return false; }).
					                                               setLightLevel((value) -> {return 15;})));
	
	public static final RegistryObject<Block> BUBBLE_CORAL = registerBlockWithDefaultItem("bubble_coral",
			() -> new BubbleCoralBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                            zeroHardnessAndResistance().
					                                            sound(SoundType.CORAL).
					                                            notSolid()));
	
	public static final RegistryObject<Block> MURKWEED = registerBlockWithDefaultItem("murkweed", 
			() -> new MurkweedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> NEEDLEGRASS = registerBlockWithDefaultItem("needlegrass", 
			() -> new NeedlegrassBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> MENGER_SPONGE = registerBlockWithDefaultItem("menger_sponge",
			() -> new MengerSpongeBlock(AbstractBlock.Properties.from(Blocks.SPONGE).notSolid()));
	
	public static final RegistryObject<Block> MENGER_SPONGE_WET = registerBlockWithDefaultItem("menger_sponge_wet",
			() -> new MengerSpongeWetBlock(AbstractBlock.Properties.from(Blocks.WET_SPONGE).notSolid()));
	
	public static final RegistryObject<Block> CHARNIA_RED = registerBlockWithDefaultItem("charnia_red",
			() -> new CharniaBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        zeroHardnessAndResistance().
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement()));
	
	public static final RegistryObject<Block> CHARNIA_PURPLE = registerBlockWithDefaultItem("charnia_purple",
			() -> new CharniaBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        zeroHardnessAndResistance().
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement()));
	
	public static final RegistryObject<Block> CHARNIA_ORANGE = registerBlockWithDefaultItem("charnia_orange",
			() -> new CharniaBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        zeroHardnessAndResistance().
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement()));
	
	public static final RegistryObject<Block> CHARNIA_LIGHT_BLUE = registerBlockWithDefaultItem("charnia_light_blue",
			() -> new CharniaBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        zeroHardnessAndResistance().
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement()));
	
	public static final RegistryObject<Block> CHARNIA_CYAN = registerBlockWithDefaultItem("charnia_cyan",
			() -> new CharniaBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        zeroHardnessAndResistance().
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement()));
	
	public static final RegistryObject<Block> CHARNIA_GREEN = registerBlockWithDefaultItem("charnia_green",
			() -> new CharniaBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                        zeroHardnessAndResistance().
					                                        sound(SoundType.WET_GRASS).
					                                        doesNotBlockMovement()));
	
	public static final RegistryObject<Block> FLAMAEA = registerBlock("flamaea",
			() -> new FlamaeaBlock());
	
	public static final RegistryObject<Block> HYDRALUX_SAPLING = registerBlockWithDefaultItem("hydralux_sapling",
			() -> new HydraluxSaplingBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                                zeroHardnessAndResistance().
					                                                sound(SoundType.WET_GRASS).
					                                                tickRandomly().
					                                                doesNotBlockMovement()));
	
	public static final RegistryObject<Block> HYDRALUX = registerBlock("hydralux",
			() -> new HydraluxBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
					                                                zeroHardnessAndResistance().
					                                                sound(SoundType.WET_GRASS).
					                                                setLightLevel((state) -> {return (state.get(HydraluxBlock.SHAPE).hasGlow()) ? 15 : 0;}).
					                                                doesNotBlockMovement()));
	
	public static final RegistryObject<Block> HYDRALUX_PETAL_BLOCK = registerBlockWithDefaultItem("hydralux_petal_block",
			() -> new HydraluxPetalBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.FOLIAGE).
					                                              sound(SoundType.WART).
					                                              hardnessAndResistance(1F).
					                                              harvestTool(ToolType.AXE)));
	
	public static final RegistryObject<Block> LANCELEAF_SEED = registerBlockWithDefaultItem("lanceleaf_seed", 
			() -> new LanceleafSeedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             tickRandomly().
					                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> LANCELEAF = registerBlock("lanceleaf", 
			() -> new LanceleafBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                             zeroHardnessAndResistance().
                                                             doesNotBlockMovement().
                                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> LUMECORN_SEED = registerBlockWithDefaultItem("lumecorn_seed", 
			() -> new LumecornSeedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             tickRandomly().
					                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> LUMECORN = registerBlock("lumecorn", 
			() -> new LumecornBlock(AbstractBlock.Properties.create(Material.WOOD).
												                 harvestTool(ToolType.AXE).
												                 hardnessAndResistance(0.5f).
												                 setLightLevel(s -> s.get(LumecornBlock.SHAPE).getLight()).
                                                                 sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_SEED = registerBlockWithDefaultItem("glowing_pillar_seed", 
			() -> new GlowingPillarSeedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             tickRandomly().
					                                             sound(SoundType.PLANT).
					                                             setLightLevel((s) -> s.get(PlantBlockWithAge.AGE) * 3 + 3)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_ROOTS = registerBlock("glowing_pillar_roots", 
			() -> new GlowingPillarRootsBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                             zeroHardnessAndResistance().
                                                             doesNotBlockMovement().
                                                             sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_LUMINOPHOR = registerBlockWithDefaultItem("glowing_pillar_luminophor", 
			() -> new GlowingPillarLuminophorBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.ADOBE).
					                                             sound(SoundType.PLANT).
					                                             hardnessAndResistance(0.2f).
					                                             setLightLevel((s) -> 15)));
	
	public static final RegistryObject<Block> GLOWING_PILLAR_LEAVES = registerBlockWithDefaultItem("glowing_pillar_leaves", 
			() -> new FurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
					                                           zeroHardnessAndResistance().
					                                           setLightLevel((value) -> 15).
					                                           doesNotBlockMovement().
					                                           sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> TWISTED_UMBRELLA_MOSS = registerBlockWithDefaultItem("twisted_umbrella_moss", 
			() -> new TwistedUmbrellaMossBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             setLightLevel((value) -> {return 11;}).
					                                             doesNotBlockMovement().
					                                             sound(SoundType.PLANT).
					                                             setEmmisiveRendering((state, reader, pos) -> {return true;})));
	
	public static final RegistryObject<Block> TWISTED_UMBRELLA_MOSS_TALL = registerBlockWithDefaultItem("twisted_umbrella_moss_tall", 
			() -> new TwistedUmbrellaMossTallBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                                 zeroHardnessAndResistance().
                                                                     setLightLevel((state) -> { return state.get(TwistedUmbrellaMossTallBlock.TOP) ? 12 : 0; }).
                                                                     doesNotBlockMovement().
                                                                     sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> SMALL_JELLYSHROOM = registerBlockWithDefaultItem("small_jellyshroom", 
			() -> new SmallJellyshroomBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                                 zeroHardnessAndResistance().
                                                                     doesNotBlockMovement().
                                                                     sound(SoundType.NETHER_WART)));
	
	public static final RegistryObject<Block> NEON_CACTUS = registerBlockWithDefaultItem("neon_cactus", 
			() -> new NeonCactusPlantBlock());
	
	public static final RegistryObject<Block> NEON_CACTUS_BLOCK = registerBlockWithDefaultItem("neon_cactus_block", 
			() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.CACTUS).setLightLevel(s -> { return 15; } )));
	
	public static final RegistryObject<Block> NEON_CACTUS_BLOCK_STAIRS = registerBlockWithDefaultItem("neon_cactus_stairs", 
			() -> new StairsBlock(() -> NEON_CACTUS_BLOCK.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.CACTUS).setLightLevel(s -> { return 15; })));
	
	public static final RegistryObject<Block> NEON_CACTUS_BLOCK_SLAB = registerBlockWithDefaultItem("neon_cactus_slab", 
			() -> new SlabBlock(AbstractBlock.Properties.from(Blocks.CACTUS).setLightLevel(s -> { return 15; })));	
	// CROPS
	public static final RegistryObject<Block> SHADOW_BERRY = registerBlockWithDefaultItem("shadow_berry", 
			() -> new ShadowBerryBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             tickRandomly().
					                                             sound(SoundType.PLANT)));


	
	public static final RegistryObject<Block> BLOSSOM_BERRY = registerBlockWithDefaultItem("blossom_berry_seed", 
			() -> new EndCropBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                            zeroHardnessAndResistance().
                                                            doesNotBlockMovement().
                                                            tickRandomly().
                                                            sound(SoundType.PLANT),
                                                            PINK_MOSS.get()));
	
	public static final RegistryObject<Block> AMBER_ROOT = registerBlockWithDefaultItem("amber_root_seed", 
			() -> new EndCropBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                            zeroHardnessAndResistance().
                                                            doesNotBlockMovement().
                                                            tickRandomly().
                                                            sound(SoundType.PLANT),
                                                            AMBER_MOSS.get()));
	
	public static final RegistryObject<Block> CHORUS_MUSHROOM = registerBlockWithDefaultItem("chorus_mushroom_seed", 
			() -> new EndCropBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                            zeroHardnessAndResistance().
                                                            doesNotBlockMovement().
                                                            tickRandomly().
                                                            sound(SoundType.PLANT),
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
			() -> new WallMushroomBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             harvestTool(ToolType.AXE).
					                                             hardnessAndResistance(0.2F).
                                                                 doesNotBlockMovement().
                                                                 sound(SoundType.WOOD).
                                                                 setLightLevel((value) -> {return 13;})));
	
	public static final RegistryObject<Block> AURANT_POLYPORE = registerBlockWithDefaultItem("aurant_polypore", 
			() -> new WallMushroomBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             harvestTool(ToolType.AXE).
					                                             hardnessAndResistance(0.2F).
                                                                 doesNotBlockMovement().
                                                                 sound(SoundType.WOOD).
                                                                 setLightLevel((value) -> {return 13;})));
	
	public static final RegistryObject<Block> TAIL_MOSS = registerBlockWithDefaultItem("tail_moss", 
			() -> new WallPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                              zeroHardnessAndResistance().
                                                              doesNotBlockMovement().
                                                              sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> CYAN_MOSS = registerBlockWithDefaultItem("cyan_moss", 
			() -> new WallPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                              zeroHardnessAndResistance().
                                                              doesNotBlockMovement().
                                                              sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> TWISTED_MOSS = registerBlockWithDefaultItem("twisted_moss", 
			() -> new WallPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                              zeroHardnessAndResistance().
                                                              doesNotBlockMovement().
                                                              sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> BULB_MOSS = registerBlockWithDefaultItem("bulb_moss", 
			() -> new WallPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                              zeroHardnessAndResistance().
                                                              doesNotBlockMovement().
                                                              sound(SoundType.PLANT).
                                                              setLightLevel((a) -> 12)));
	
	public static final RegistryObject<Block> TUBE_WORM = registerBlockWithDefaultItem("tube_worm", 
			() -> new UnderwaterWallPlantBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).
                                                                        zeroHardnessAndResistance().
                                                                        doesNotBlockMovement().
                                                                        sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> JUNGLE_FERN = registerBlockWithDefaultItem("jungle_fern", 
			() -> new WallPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                              zeroHardnessAndResistance().
                                                              doesNotBlockMovement().
                                                              sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> RUSCUS = registerBlockWithDefaultItem("ruscus", 
			() -> new WallPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                              zeroHardnessAndResistance().
                                                              doesNotBlockMovement().
                                                              sound(SoundType.PLANT)));

	
	public static final RegistryObject<Block> POND_ANEMONE = registerBlockWithDefaultItem("pond_anemone", 
			() -> new PondAnemoneBlock());
	
	// VINES
	public static final RegistryObject<Block> DENSE_VINE = registerBlockWithDefaultItem("dense_vine",
			() -> new EndVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        setLightLevel((state) -> {return state.get(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;}).
					                                        sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> TWISTED_VINE = registerBlockWithDefaultItem("twisted_vine",
			() -> new EndVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> BULB_VINE = registerBlockWithDefaultItem("bulb_vine",
			() -> new BulbVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        setLightLevel((state) -> {return state.get(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;}).
					                                        sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> BULB_VINE_SEED = registerBlockWithDefaultItem("bulb_vine_seed",
			() -> new BulbVineSeedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        tickRandomly().
					                                        sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> JUNGLE_VINE = registerBlockWithDefaultItem("jungle_vine",
			() -> new EndVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> RUBINEA = registerBlockWithDefaultItem("rubinea", 
			() -> new EndVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                            zeroHardnessAndResistance().
                                                            doesNotBlockMovement().
                                                            sound(SoundType.PLANT)));
	
	// TREES
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_SAPLING = registerBlockWithDefaultItem("mossy_glowshroom_sapling", 
			() -> new MossyGlowshroomSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                                       zeroHardnessAndResistance().
					                                                       doesNotBlockMovement().
					                                                       sound(SoundType.PLANT).
					                                                       setLightLevel((value) -> {return 7;}).
					                                                       tickRandomly()));
	
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_CAP = registerBlockWithDefaultItem("mossy_glowshroom_cap", 
			() -> new MossyGlowshroomCapBlock(AbstractBlock.Properties.create(Material.WOOD).
					                                                   harvestTool(ToolType.AXE).
					                                                   setRequiresTool().
					                                                   sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_HYMENOPHORE = registerBlockWithDefaultItem("mossy_glowshroom_hymenophore", 
			() -> new Block(AbstractBlock.Properties.create(Material.WOOD).
                                                     harvestTool(ToolType.AXE).
                                                     setRequiresTool().
                                                     sound(SoundType.WART).
                                                     setLightLevel((value) -> {return 15;})));
	
	public static final RegistryObject<Block> MOSSY_GLOWSHROOM_FUR = registerBlockWithDefaultItem("mossy_glowshroom_fur", 
			() -> new FurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
					                                           zeroHardnessAndResistance().
					                                           setLightLevel((value) -> {return 15;}).
					                                           doesNotBlockMovement().
					                                           sound(SoundType.WET_GRASS)));
		
	public static final RegistryObject<Block> LACUGROVE_SAPLING = registerBlockWithDefaultItem("lacugrove_sapling", 
			() -> new LacugroveSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                     zeroHardnessAndResistance().
                                                                     doesNotBlockMovement().
                                                                     sound(SoundType.PLANT).
                                                                     tickRandomly()));
	
	public static final RegistryObject<Block> LACUGROVE_LEAVES = registerBlockWithDefaultItem("lacugrove_leaves", 
			() -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.CYAN).
                                                           hardnessAndResistance(0.2F).tickRandomly().
					                                       sound(SoundType.PLANT).
					                                       notSolid().
					                                       setSuffocates((state, world, pos) -> { return false; }).
					                                       setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
					                                       setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> PYTHADENDRON_SAPLING = registerBlockWithDefaultItem("pythadendron_sapling", 
			() -> new PythadendronSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                        zeroHardnessAndResistance().
                                                                        doesNotBlockMovement().
                                                                        sound(SoundType.PLANT).
                                                                        tickRandomly()));
	
	public static final RegistryObject<Block> PYTHADENDRON_LEAVES = registerBlockWithDefaultItem("pythadendron_leaves", 
			() -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.MAGENTA).
					                                       hardnessAndResistance(0.2F).
					                                       tickRandomly().
                                                           sound(SoundType.PLANT).
                                                           notSolid().
                                                           setSuffocates((state, world, pos) -> { return false; }).
                                                           setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
                                                           setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> DRAGON_TREE_SAPLING = registerBlockWithDefaultItem("dragon_tree_sapling", 
			() -> new DragonTreeSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                      zeroHardnessAndResistance().
                                                                      doesNotBlockMovement().
                                                                      sound(SoundType.PLANT).
                                                                      tickRandomly()));
	
	public static final RegistryObject<Block> DRAGON_TREE_LEAVES = registerBlockWithDefaultItem("dragon_tree_leaves", 
			() -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.MAGENTA).
					                                       hardnessAndResistance(0.2F).
					                                       tickRandomly().
                                                           sound(SoundType.PLANT).
                                                           notSolid().
                                                           setSuffocates((state, world, pos) -> { return false; }).
                                                           setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
                                                           setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> TENANEA_SAPLING = registerBlockWithDefaultItem("tenanea_sapling", 
			() -> new TenaneaSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                   zeroHardnessAndResistance().
                                                                   doesNotBlockMovement().
                                                                   sound(SoundType.PLANT).
                                                                   tickRandomly()));
	
	public static final RegistryObject<Block> TENANEA_LEAVES = registerBlockWithDefaultItem("tenanea_leaves", 
			() -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.PINK).
					                                       hardnessAndResistance(0.2F).
					                                       tickRandomly().
                                                           sound(SoundType.PLANT).
                                                           notSolid().
                                                           setSuffocates((state, world, pos) -> { return false; }).
                                                           setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
                                                           setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> TENANEA_FLOWERS = registerBlockWithDefaultItem("tenanea_flowers",
			() -> new TenaneaFlowersBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                               zeroHardnessAndResistance().
					                                               doesNotBlockMovement().
					                                               setLightLevel((state) -> {return 15;}).
					                                               sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> TENANEA_OUTER_LEAVES = registerBlockWithDefaultItem("tenanea_outer_leaves", 
			() -> new FurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
					                                           zeroHardnessAndResistance().
					                                           doesNotBlockMovement().
					                                           sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> HELIX_TREE_SAPLING = registerBlockWithDefaultItem("helix_tree_sapling",
			() -> new HelixTreeSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                   zeroHardnessAndResistance().
                                                                   doesNotBlockMovement().
                                                                   sound(SoundType.PLANT).
                                                                   tickRandomly()));
	
	public static final RegistryObject<Block> HELIX_TREE_LEAVES = registerBlockWithDefaultItem("helix_tree_leaves",
			() -> new HelixTreeLeavesBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.ADOBE).
					                                       hardnessAndResistance(0.2F).
					                                       tickRandomly().
                                                           sound(SoundType.NETHER_WART).
                                                           notSolid().
                                                           setSuffocates((state, world, pos) -> { return false; }).
                                                           setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
                                                           setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_SAPLING = registerBlockWithDefaultItem("umbrella_tree_sapling",
			() -> new UmbrellaTreeSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                                                                   zeroHardnessAndResistance().
                                                                   doesNotBlockMovement().
                                                                   sound(SoundType.PLANT).
                                                                   tickRandomly()));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_MEMBRANE = registerBlockWithDefaultItem("umbrella_tree_membrane",
			() -> new UmbrellaTreeMembraneBlock(AbstractBlock.Properties.from(Blocks.SLIME_BLOCK)));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_CLUSTER = registerBlockWithDefaultItem("umbrella_tree_cluster",
			() -> new UmbrellaTreeClusterBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.PURPLE).
					                                                    hardnessAndResistance(1.0F).
					                                                    setLightLevel((value) -> {return 15;}).
					                                                    sound(SoundType.WART)));
	
	public static final RegistryObject<Block> UMBRELLA_TREE_CLUSTER_EMPTY = registerBlockWithDefaultItem("umbrella_tree_cluster_empty",
			() -> new UmbrellaTreeClusterBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.PURPLE).
					                                                    hardnessAndResistance(1.0F).
					                                                    tickRandomly().
					                                                    sound(SoundType.WART)));
	
	public static final RegistryObject<Block> SMALL_AMARANITA_MUSHROOM = registerBlockWithDefaultItem("small_amaranita_mushroom",
			() -> new SmallAmaranitaBlock());
	
	public static final RegistryObject<Block> LARGE_AMARANITA_MUSHROOM = registerBlock("large_amaranita_mushroom",
			() -> new LargeAmaranitaBlock());
	
	public static final RegistryObject<Block> AMARANITA_STEM = registerBlockWithDefaultItem("amaranita_stem",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.LIME).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> AMARANITA_HYPHAE = registerBlockWithDefaultItem("amaranita_hyphae",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.LIME).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> AMARANITA_HYMENOPHORE = registerBlockWithDefaultItem("amaranita_hymenophore",
			() -> new Block(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> AMARANITA_LANTERN = registerBlockWithDefaultItem("amaranita_lantern",
			() -> new Block(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WART).setLightLevel(s -> 15)));
	
	public static final RegistryObject<Block> AMARANITA_FUR = registerBlockWithDefaultItem("amaranita_fur",
			() -> new FurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
                    zeroHardnessAndResistance().
                    setLightLevel((value) -> {return 15;}).
                    doesNotBlockMovement().
                    sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> AMARANITA_CAP = registerBlockWithDefaultItem("amaranita_cap",
			() -> new Block(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
	
	
	public static final RegistryObject<Block> JELLYSHROOM_CAP_PURPLE = registerBlockWithDefaultItem("jellyshroom_cap_purple", 
			() -> new JellyshroomCapBlock(217, 142, 255, 164, 0, 255));
	
	// BLOCKS WITH TILE ENTITY
	public static final RegistryObject<Block> ETERNAL_PEDESTAL = registerBlockWithDefaultItem("eternal_pedestal", 
			() -> new EternalPedestal(AbstractBlock.Properties.from(FLAVOLITE_RUNED_ETERNAL.get()).
					                                           setLightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> INFUSION_PEDESTAL = registerBlockWithDefaultItem("infusion_pedestal", 
			() -> new InfusionPedestal(AbstractBlock.Properties.from(Blocks.OBSIDIAN).
					                                            setLightLevel(PedestalBlock.light())));
	
	public static final RegistryObject<Block> END_STONE_SMELTER = registerBlockWithDefaultItem("end_stone_smelter", 
			() -> new EndStoneSmelter(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).
					                                           hardnessAndResistance(4F, 100F).
					                                           setRequiresTool().
					                                           sound(SoundType.STONE).
					                                           setLightLevel((state) -> {
					                                        	   return state.get(BlockStateProperties.LIT) ? 13 : 0;}
					                                           )));
	
	public static final RegistryObject<Block> END_STONE_FURNACE = registerBlockWithDefaultItem("end_stone_furnace", 
			() -> new EndFurnaceBlock(AbstractBlock.Properties.from(Blocks.END_STONE).
					                                           setLightLevel((state) -> {
					                                        	   return state.get(BlockStateProperties.LIT) ? 13 : 0;}
					                                           )));
	
	// MISC
	public static final RegistryObject<Block> AETERNIUM_ANVIL = registerBlock("aeternium_anvil", 
			() -> new AeterniumAnvil(AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(5.0F, 1200.0F).sound(SoundType.ANVIL)));
	
	public static final RegistryObject<Block> DENSE_SNOW = registerBlockWithDefaultItem("dense_snow", 
			() -> new Block(AbstractBlock.Properties.create(Material.SNOW_BLOCK).
					                                 hardnessAndResistance(0.2F).
					                                 sound(SoundType.SNOW)));
	
	public static final RegistryObject<Block> EMERALD_ICE = registerBlockWithDefaultItem("emerald_ice", 
			() -> new EmeraldIceBlock(AbstractBlock.Properties.from(Blocks.ICE)));
	
	public static final RegistryObject<Block> DENSE_EMERALD_ICE = registerBlockWithDefaultItem("dense_emerald_ice", 
			() -> new Block(AbstractBlock.Properties.from(Blocks.PACKED_ICE)));
	
	public static final RegistryObject<Block> ANCIENT_EMERALD_ICE = registerBlockWithDefaultItem("ancient_emerald_ice", 
			() -> new AncientEmeraldIceBlock(AbstractBlock.Properties.from(Blocks.BLUE_ICE).
					                                                  tickRandomly()));
	
	public static final RegistryObject<Block> END_PORTAL_BLOCK = registerBlock("end_portal_block", 
			() -> new EndPortalBlock(AbstractBlock.Properties.create(Material.PORTAL).
					                                          doesNotBlockMovement().
					                                          tickRandomly().
					                                          hardnessAndResistance(-1.0F).
					                                          sound(SoundType.GLASS).
					                                          setLightLevel((state) -> {return 12;}).
					                                          noDrops()));
	
	public static final RegistryObject<Block> RESPAWN_OBELISK = registerBlockWithDefaultItem("respawn_obelisk",
			() -> new RespawnObeliskBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					setRequiresTool().
					hardnessAndResistance(3.0F, 9.0F).
					setLightLevel((state) -> {
						return (state.get(RespawnObeliskBlock.SHAPE) == TripleShape.BOTTOM) ? 0 : 15;
					})));
	
	public static final RegistryObject<Block> VENT_BUBBLE_COLUMN = registerBlock("vent_bubble_column",
			() -> new VentBubbleColumnBlock(AbstractBlock.Properties.create(Material.BUBBLE_COLUMN).
                                                                     doesNotBlockMovement().
                                                                     setOpaque((state, world, pos) -> { return false; }).
                                                                     noDrops()));
	
	public static final RegistryObject<Block> MISSING_TILE = registerBlockWithDefaultItem("missing_tile",
			() -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE)));
	
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
	public static final WoodenMaterial MOSSY_GLOWSHROOM = createWoodenMaterial("mossy_glowshroom", MaterialColor.GRAY, MaterialColor.WOOD);
	public static final WoodenMaterial LACUGROVE = createWoodenMaterial("lacugrove", MaterialColor.BROWN, MaterialColor.YELLOW);
	public static final WoodenMaterial END_LOTUS = createWoodenMaterial("end_lotus", MaterialColor.LIGHT_BLUE, MaterialColor.CYAN);
	public static final WoodenMaterial PYTHADENDRON = createWoodenMaterial("pythadendron", MaterialColor.MAGENTA, MaterialColor.PURPLE);
	public static final WoodenMaterial DRAGON_TREE = createWoodenMaterial("dragon_tree", MaterialColor.BLACK, MaterialColor.MAGENTA);
	public static final WoodenMaterial TENANEA = createWoodenMaterial("tenanea", MaterialColor.BROWN, MaterialColor.PINK);
	public static final WoodenMaterial HELIX_TREE = createWoodenMaterial("helix_tree", MaterialColor.GRAY, MaterialColor.ADOBE);
	public static final WoodenMaterial UMBRELLA_TREE = createWoodenMaterial("umbrella_tree", MaterialColor.BLUE, MaterialColor.GREEN);
	public static final WoodenMaterial JELLYSHROOM = createWoodenMaterial("jellyshroom", MaterialColor.PURPLE, MaterialColor.LIGHT_BLUE);
	public static final WoodenMaterial LUCERNIA = createWoodenMaterial("lucernia", MaterialColor.ADOBE, MaterialColor.ADOBE);
	
	public static final RegistryObject<Block> LUCERNIA_SAPLING = registerBlockWithDefaultItem("lucernia_sapling",
			() -> new LucerniaSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).
                    zeroHardnessAndResistance().
                    doesNotBlockMovement().
                    sound(SoundType.PLANT).
                    tickRandomly()));
	
	public static final RegistryObject<Block> LUCERNIA_LEAVES = registerBlockWithDefaultItem("lucernia_leaves",
			() -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.ADOBE).
                    hardnessAndResistance(0.2F).tickRandomly().
                    sound(SoundType.PLANT).
                    notSolid().
                    setSuffocates((state, world, pos) -> { return false; }).
                    setAllowsSpawn((state, reader, pos, entity) -> {return false;}).
                    setBlocksVision((state, reader, pos) -> {return false;})));
	
	public static final RegistryObject<Block> LUCERNIA_OUTER_LEAVES = registerBlockWithDefaultItem("lucernia_outer_leaves",
			() -> new FurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.ADOBE).
                    zeroHardnessAndResistance().
                    setLightLevel((value) -> {return 15;}).
                    doesNotBlockMovement().
                    sound(SoundType.WET_GRASS)));
	
	public static final RegistryObject<Block> FILALUX = registerBlockWithDefaultItem("filalux",
			() -> new FilaluxBlock());
	public static final RegistryObject<Block> FILALUX_WINGS = registerBlockWithDefaultItem("filalux_wings",
			() -> new FilaluxWingsBlock());
	public static final RegistryObject<Block> FILALUX_LANTERN = registerBlockWithDefaultItem("filalux_lantern",
			() -> new Block(AbstractBlock.Properties.create(Material.WOOD).setLightLevel(b -> 15).sound(SoundType.WOOD).harvestTool(ToolType.AXE)));

	
	// STONE MATERIALS
	private static List<StoneMaterial> stoneMaterials;
	public static List<StoneMaterial> getStoneMaterials() {
		return ImmutableList.copyOf(stoneMaterials);
	}
    public static final StoneMaterial FLAVOLITE = createStoneMaterial("flavolite", MaterialColor.SAND);
    public static final StoneMaterial VIOLECITE = createStoneMaterial("violecite", MaterialColor.PURPLE);
    public static final StoneMaterial SULPHURIC_ROCK = createStoneMaterial("sulphuric_rock", MaterialColor.BROWN);
	public static final StoneMaterial VIRID_JADESTONE = createStoneMaterial("virid_jadestone", MaterialColor.GREEN);
	public static final StoneMaterial AZURE_JADESTONE = createStoneMaterial("azure_jadestone", MaterialColor.LIGHT_BLUE);
	public static final StoneMaterial SANDY_JADESTONE = createStoneMaterial("sandy_jadestone", MaterialColor.YELLOW);
	public static final StoneMaterial UMBRALITH = new StoneMaterial("umbralith", MaterialColor.BLACK);


	// METAL MATERIALS
	private static List<MetalMaterial> metalMaterials;
	public static List<MetalMaterial> getMetalMaterials() {
		return ImmutableList.copyOf(metalMaterials);
	}
    public static final MetalMaterial THALLASIUM = createMetalMaterial("thallasium", true, AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB), ModItemTier.THALLASIUM, ModArmorMaterial.THALLASIUM);
    public static final MetalMaterial TERMINITE = createMetalMaterial("terminite", false, AbstractBlock.Properties.create(Material.IRON, MaterialColor.GREEN).setRequiresTool().hardnessAndResistance(7, 9).sound(SoundType.METAL), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB), ModItemTier.TERMINITE, ModArmorMaterial.TERMINITE);

    
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
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		return block;
	}

	public static <T extends Block> RegistryObject<T> registerBlockWithNoItem(String name, Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		return block;
	}


	
	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier, 
			ItemGroup group)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
		return block;
	}
	
	public static RegistryObject<Block> registerFlowerPotBlock(String name, Supplier<? extends Block> plant)
	{
		RegistryObject<Block> flowerPot = BLOCKS.register(name, () -> new FlowerPotBlock(plant.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
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
	
	public static MetalMaterial createMetalMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings, Item.Properties itemSettings, IItemTier itemTier, IArmorMaterial armor) {
		if (metalMaterials == null)
			metalMaterials = new ArrayList<>();
		
		MetalMaterial material = new MetalMaterial(name, hasOre, blockSettings, itemSettings, itemTier, armor);
		metalMaterials.add(material);
		return material;
	}
}
