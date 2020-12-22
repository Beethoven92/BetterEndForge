package mod.beethoven92.betterendforge.common.init;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.AuroraCrystalBlock;
import mod.beethoven92.betterendforge.common.block.BlueVineBlock;
import mod.beethoven92.betterendforge.common.block.BlueVineLanternBlock;
import mod.beethoven92.betterendforge.common.block.BlueVineSeedBlock;
import mod.beethoven92.betterendforge.common.block.BubbleCoralBlock;
import mod.beethoven92.betterendforge.common.block.ChorusGrassBlock;
import mod.beethoven92.betterendforge.common.block.DragonTreeSaplingBlock;
import mod.beethoven92.betterendforge.common.block.EndLilyBlock;
import mod.beethoven92.betterendforge.common.block.EndLilySeedBlock;
import mod.beethoven92.betterendforge.common.block.EndLotusFlowerBlock;
import mod.beethoven92.betterendforge.common.block.EndLotusLeafBlock;
import mod.beethoven92.betterendforge.common.block.EndLotusSeedBlock;
import mod.beethoven92.betterendforge.common.block.EndLotusStemBlock;
import mod.beethoven92.betterendforge.common.block.EndPortalBlock;
import mod.beethoven92.betterendforge.common.block.EndStoneSmelter;
import mod.beethoven92.betterendforge.common.block.EndstoneDustBlock;
import mod.beethoven92.betterendforge.common.block.EternalPedestal;
import mod.beethoven92.betterendforge.common.block.GlowingFurBlock;
import mod.beethoven92.betterendforge.common.block.GlowingMossBlock;
import mod.beethoven92.betterendforge.common.block.InfusionPedestal;
import mod.beethoven92.betterendforge.common.block.LacugroveSaplingBlock;
import mod.beethoven92.betterendforge.common.block.MossyGlowshroomCapBlock;
import mod.beethoven92.betterendforge.common.block.MossyGlowshroomSaplingBlock;
import mod.beethoven92.betterendforge.common.block.MurkweedBlock;
import mod.beethoven92.betterendforge.common.block.NeedlegrassBlock;
import mod.beethoven92.betterendforge.common.block.PathBlock;
import mod.beethoven92.betterendforge.common.block.PythadendronSaplingBlock;
import mod.beethoven92.betterendforge.common.block.RunedFlavoliteBlock;
import mod.beethoven92.betterendforge.common.block.ShadowBerryBlock;
import mod.beethoven92.betterendforge.common.block.ShadowGrassBlock;
import mod.beethoven92.betterendforge.common.block.TenaneaFlowersBlock;
import mod.beethoven92.betterendforge.common.block.TenaneaSaplingBlock;
import mod.beethoven92.betterendforge.common.block.TerrainBlock;
import mod.beethoven92.betterendforge.common.block.TerrainPlantBlock;
import mod.beethoven92.betterendforge.common.block.UmbrellaMossBlock;
import mod.beethoven92.betterendforge.common.block.UmbrellaMossTallBlock;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.block.template.WallMushroomBlock;
import mod.beethoven92.betterendforge.common.block.template.WallPlantBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
                                                            sound(SoundType.GROUND).
                                                            tickRandomly()));
	
	public static final RegistryObject<Block> CAVE_MOSS = registerBlockWithDefaultItem("cave_moss", 
			() -> new TerrainBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE).
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

	public static final RegistryObject<Block> ENDSTONE_DUST = registerBlockWithDefaultItem("endstone_dust", 
			() -> new EndstoneDustBlock(AbstractBlock.Properties.create(Material.SAND, Blocks.END_STONE.getMaterialColor()).
					                                             hardnessAndResistance(0.5F).
					                                             sound(SoundType.SAND)));
	
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
	
	// MATERIALS
	public static final RegistryObject<Block> AETERNIUM_BLOCK = registerBlockWithDefaultItem("aeternium_block",
			() -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GRAY).
					                                 hardnessAndResistance(65F, 1200F).
					                                 setRequiresTool().
					                                 sound(SoundType.NETHERITE)));
	
	public static final RegistryObject<Block> TERMINITE_BLOCK = registerBlockWithDefaultItem("terminite_block",
			() -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GREEN).
					                                 hardnessAndResistance(7F, 9F).
					                                 setRequiresTool().
					                                 sound(SoundType.METAL)));
	
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
	
	// ORES
	public static final RegistryObject<Block> ENDER_ORE = registerBlockWithDefaultItem("ender_ore",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                 hardnessAndResistance(3F, 9F).
					                                 setRequiresTool().
					                                 sound(SoundType.STONE)));
	
	// STONES
	public static final RegistryObject<Block> FLAVOLITE_RUNED = registerBlockWithDefaultItem("flavolite_runed",
			() -> new RunedFlavoliteBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                               setRequiresTool().
					                                               hardnessAndResistance(3.0F, Blocks.OBSIDIAN.getExplosionResistance())));
	
	public static final RegistryObject<Block> FLAVOLITE_RUNED_ETERNAL = registerBlockWithDefaultItem("flavolite_runed_eternal", 
			() -> new RunedFlavoliteBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).
					                                               hardnessAndResistance(-1.0F, 3600000.0F).
					                                               noDrops()));
	
	public static final RegistryObject<Block> QUARTZ_PEDESTAL = registerBlockWithDefaultItem("quartz_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_BLOCK)));
	
	public static final RegistryObject<Block> PURPUR_PEDESTAL = registerBlockWithDefaultItem("purpur_pedestal",
			() -> new PedestalBlock(AbstractBlock.Properties.from(Blocks.PURPUR_BLOCK)));
			
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
                                                                     setLightLevel((value) -> {return 12;}).
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
	
	public static final RegistryObject<Block> CAVE_GRASS = registerBlockWithDefaultItem("cave_grass", 
			() -> new TerrainPlantBlock(CAVE_MOSS.get()));
	
	public static final RegistryObject<Block> CRYSTAL_GRASS = registerBlockWithDefaultItem("crystal_grass", 
			() -> new TerrainPlantBlock(CRYSTAL_MOSS.get()));
	
	public static final RegistryObject<Block> SHADOW_PLANT = registerBlockWithDefaultItem("shadow_plant", 
			() -> new TerrainPlantBlock(SHADOW_GRASS.get()));
	
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
			() -> new GlowingFurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
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
	
	// CROPS
	public static final RegistryObject<Block> SHADOW_BERRY = registerBlockWithDefaultItem("shadow_berry", 
			() -> new ShadowBerryBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                             zeroHardnessAndResistance().
					                                             doesNotBlockMovement().
					                                             tickRandomly().
					                                             sound(SoundType.PLANT)));
	
	// WALL PLANTS
	public static final RegistryObject<Block> PURPLE_POLYPORE = registerBlockWithDefaultItem("purple_polypore", 
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
	// VINES
	public static final RegistryObject<Block> DENSE_VINE = registerBlockWithDefaultItem("dense_vine",
			() -> new EndVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        sound(SoundType.PLANT), 15, true));
	
	public static final RegistryObject<Block> TWISTED_VINE = registerBlockWithDefaultItem("twisted_vine",
			() -> new EndVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        sound(SoundType.PLANT), 0, false));
	
	/*public static final RegistryObject<Block> BULB_VINE = registerBlockWithDefaultItem("bulb_vine",
			() -> new BulbVineBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        sound(SoundType.PLANT)));
	
	public static final RegistryObject<Block> BULB_VINE_SEED = registerBlockWithDefaultItem("bulb_vine_seed",
			() -> new BulbVineSeedBlock(AbstractBlock.Properties.create(Material.PLANTS).
					                                        zeroHardnessAndResistance().
					                                        doesNotBlockMovement().
					                                        tickRandomly().
					                                        sound(SoundType.PLANT)));*/
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
			() -> new GlowingFurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
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
					                                               sound(SoundType.PLANT), 15, false));
	
	public static final RegistryObject<Block> TENANEA_OUTER_LEAVES = registerBlockWithDefaultItem("tenanea_outer_leaves", 
			() -> new GlowingFurBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).
					                                           zeroHardnessAndResistance().
					                                           doesNotBlockMovement().
					                                           sound(SoundType.WET_GRASS)));
	
	// BLOCKS WITH TILE ENTITY
	public static final RegistryObject<Block> ETERNAL_PEDESTAL = registerBlockWithDefaultItem("eternal_pedestal", 
			() -> new EternalPedestal(AbstractBlock.Properties.from(FLAVOLITE_RUNED_ETERNAL.get())));
	
	public static final RegistryObject<Block> INFUSION_PEDESTAL = registerBlockWithDefaultItem("infusion_pedestal", 
			() -> new InfusionPedestal(AbstractBlock.Properties.from(Blocks.OBSIDIAN)));
	
	public static final RegistryObject<Block> END_STONE_SMELTER = registerBlockWithDefaultItem("end_stone_smelter", 
			() -> new EndStoneSmelter(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).
					                                                  hardnessAndResistance(4F, 100F).
					                                                  setRequiresTool().
					                                                  sound(SoundType.STONE).
					                                                  setLightLevel((state) -> {
					                                                      return state.get(BlockStateProperties.LIT) ? 13 : 0;}
					                                                  )));
	
	// MISC
	public static final RegistryObject<Block> END_PORTAL_BLOCK = registerBlock("end_portal_block", 
			() -> new EndPortalBlock(AbstractBlock.Properties.create(Material.PORTAL).
					                                          doesNotBlockMovement().
					                                          tickRandomly().
					                                          hardnessAndResistance(-1.0F).
					                                          sound(SoundType.GLASS).
					                                          setLightLevel((state) -> {return 12;}).
					                                          noDrops()));
	
	// WOODEN MATERIALS
	public static final WoodenMaterial MOSSY_GLOWSHROOM = new WoodenMaterial("mossy_glowshroom", MaterialColor.GRAY, MaterialColor.WOOD);
	public static final WoodenMaterial LACUGROVE = new WoodenMaterial("lacugrove", MaterialColor.BROWN, MaterialColor.YELLOW);
	public static final WoodenMaterial END_LOTUS = new WoodenMaterial("end_lotus", MaterialColor.LIGHT_BLUE, MaterialColor.CYAN);
	public static final WoodenMaterial PYTHADENDRON = new WoodenMaterial("pythadendron", MaterialColor.MAGENTA, MaterialColor.PURPLE);
	public static final WoodenMaterial DRAGON_TREE = new WoodenMaterial("dragon_tree", MaterialColor.BLACK, MaterialColor.MAGENTA);
	public static final WoodenMaterial TENANEA = new WoodenMaterial("tenanea", MaterialColor.BROWN, MaterialColor.PINK);
	
	// STONE MATERIALS
    public static final StoneMaterial FLAVOLITE = new StoneMaterial("flavolite", MaterialColor.SAND);
    public static final StoneMaterial VIOLECITE = new StoneMaterial("violecite", MaterialColor.PURPLE);
    
	
	//////////////////////////////////////////////////////
	//
	// Block registration helpers
	//
	/////////////////////////////////////////////////////
	
	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> itemSupplier)
	{
		return BLOCKS.register(name, itemSupplier);
	}
	
	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
		return block;
	}
	
	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier, 
			ItemGroup group)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
		return block;
	}
}
