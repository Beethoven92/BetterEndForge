package mod.beethoven92.betterendforge.data.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.BlockProperties.PedestalState;
import mod.beethoven92.betterendforge.common.block.StalactiteBlock;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.block.template.PillarBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockStates extends BlockStateProvider
{
	public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) 
	{
		super(gen, BetterEnd.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() 
	{
		// BLOCKS
		// BlockItem handled in item model provider
		chandelierBlock(ModBlocks.IRON_CHANDELIER.get());
		// BlockItem handled in item model provider
		chandelierBlock(ModBlocks.GOLD_CHANDELIER.get());
		
		furnaceBlock(ModBlocks.END_STONE_FURNACE.get(), "end_stone");
		makeBlockItemFromExistingModel(ModBlocks.END_STONE_FURNACE.get(), "end_stone_furnace");
		
		simpleBlock(ModBlocks.MISSING_TILE.get());
		makeBlockItemFromExistingModel(ModBlocks.MISSING_TILE.get());
		
		// BlockItem handled in item model provider
		stalactiteBlock(ModBlocks.END_STONE_STALACTITE.get());
		// BlockItem handled in item model provider
		stalactiteBlock(ModBlocks.END_STONE_STALACTITE_CAVEMOSS.get());
		
		// WOODEN MATERIALS
		registerWoodenMaterialBlockStates(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialBlockStates(ModBlocks.LACUGROVE);
		registerWoodenMaterialBlockStates(ModBlocks.END_LOTUS);
		registerWoodenMaterialBlockStates(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialBlockStates(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialBlockStates(ModBlocks.TENANEA);
		registerWoodenMaterialBlockStates(ModBlocks.HELIX_TREE);
		registerWoodenMaterialBlockStates(ModBlocks.UMBRELLA_TREE);
		registerWoodenMaterialBlockStates(ModBlocks.JELLYSHROOM);
		registerWoodenMaterialBlockStates(ModBlocks.LUCERNIA);
		
		// STONE MATERIALS
		registerStoneMaterialBlockStates(ModBlocks.FLAVOLITE);
		registerStoneMaterialBlockStates(ModBlocks.VIOLECITE);
		registerStoneMaterialBlockStates(ModBlocks.SULPHURIC_ROCK);
		registerStoneMaterialBlockStates(ModBlocks.VIRID_JADESTONE);
		registerStoneMaterialBlockStates(ModBlocks.AZURE_JADESTONE);
		registerStoneMaterialBlockStates(ModBlocks.SANDY_JADESTONE);
		registerStoneMaterialBlockStates(ModBlocks.UMBRALITH);

		
		// METAL MATERIALS
		registerMetalMaterialBlockStates(ModBlocks.THALLASIUM);
		registerMetalMaterialBlockStates(ModBlocks.TERMINITE);
		
		// COLORED MATERIALS
		registerColoredMaterialBlockStates(ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED, "block_petal_colored");
		bulbLanterns("iron", ModBlocks.IRON_BULB_LANTERN.get(), ModBlocks.IRON_BULB_LANTERN_COLORED);
		
		// PEDESTALS
		registerPedestals();
		
		// LANTERNS
		registerLanterns();
		
		// FLOWER POTS
		registerFlowerPots();
		
		simpleBlock(ModBlocks.AMARANITA_HYMENOPHORE.get());
		makeBlockItemFromExistingModel(ModBlocks.AMARANITA_HYMENOPHORE.get());
		simpleBlock(ModBlocks.AMARANITA_CAP.get());
		makeBlockItemFromExistingModel(ModBlocks.AMARANITA_CAP.get());
		axisBlock(ModBlocks.DRAGON_BONE_BLOCK.get(), modLoc("block/dragon_bone_block_side"), modLoc("block/dragon_bone_block_top"));
		makeBlockItemFromExistingModel(ModBlocks.DRAGON_BONE_BLOCK.get());
		stairsBlock(ModBlocks.DRAGON_BONE_STAIRS.get(), modLoc("block/dragon_bone_block"));
		makeBlockItemFromExistingModel(ModBlocks.DRAGON_BONE_STAIRS.get());
		slabBlock(ModBlocks.DRAGON_BONE_SLAB.get(), modLoc("block/dragon_bone_block"), modLoc("block/dragon_bone_block"));
		makeBlockItemFromExistingModel(ModBlocks.DRAGON_BONE_SLAB.get());
		
		axisBlock((RotatedPillarBlock) ModBlocks.NEON_CACTUS_BLOCK.get(), modLoc("block/neon_cactus_block_side"), modLoc("block/neon_cactus_block_top"));
		makeBlockItemFromExistingModel(ModBlocks.NEON_CACTUS_BLOCK.get());
		stairsBlock((StairsBlock) ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get(), modLoc("block/neon_cactus_block_side"), modLoc("block/neon_cactus_block_top"), modLoc("block/neon_cactus_block_top"));
		makeBlockItemFromExistingModel(ModBlocks.NEON_CACTUS_BLOCK_STAIRS.get());
		slabBlock((SlabBlock) ModBlocks.NEON_CACTUS_BLOCK_SLAB.get(), modLoc("block/neon_cactus_block"), modLoc("block/neon_cactus_block_side"), modLoc("block/neon_cactus_block_top"), modLoc("block/neon_cactus_block_top"));
		makeBlockItemFromExistingModel(ModBlocks.NEON_CACTUS_BLOCK_SLAB.get());
		
		simpleBlock(ModBlocks.FILALUX_LANTERN.get());
		makeBlockItemFromExistingModel(ModBlocks.FILALUX_LANTERN.get());
	}
	
	private void registerLanterns() 
	{
		lanternBlock(ModBlocks.ANDESITE_LANTERN.get(), "andesite");
		makeBlockItemFromExistingModel(ModBlocks.ANDESITE_LANTERN.get(), "andesite_lantern_ceil");
		lanternBlock(ModBlocks.DIORITE_LANTERN.get(), "diorite");
		makeBlockItemFromExistingModel(ModBlocks.DIORITE_LANTERN.get(), "diorite_lantern_ceil");
		lanternBlock(ModBlocks.GRANITE_LANTERN.get(), "granite");
		makeBlockItemFromExistingModel(ModBlocks.GRANITE_LANTERN.get(), "granite_lantern_ceil");
		lanternBlock(ModBlocks.QUARTZ_LANTERN.get(), "quartz");
		makeBlockItemFromExistingModel(ModBlocks.QUARTZ_LANTERN.get(), "quartz_lantern_ceil");
		lanternBlock(ModBlocks.PURPUR_LANTERN.get(), "purpur");
		makeBlockItemFromExistingModel(ModBlocks.PURPUR_LANTERN.get(), "purpur_lantern_ceil");
		lanternBlock(ModBlocks.END_STONE_LANTERN.get(), "end_stone");
		makeBlockItemFromExistingModel(ModBlocks.END_STONE_LANTERN.get(), "end_stone_lantern_ceil");
		lanternBlock(ModBlocks.BLACKSTONE_LANTERN.get(), "blackstone");
		makeBlockItemFromExistingModel(ModBlocks.BLACKSTONE_LANTERN.get(), "blackstone_lantern_ceil");
	}

	private void registerPedestals() 
	{
		pedestalBlock(ModBlocks.ANDESITE_PEDESTAL.get(), "andesite", mcLoc("block/polished_andesite"), mcLoc("block/andesite_pillar"));
		makeBlockItemFromExistingModel(ModBlocks.ANDESITE_PEDESTAL.get(), "andesite_pedestal_default");
		pedestalBlock(ModBlocks.DIORITE_PEDESTAL.get(), "diorite", mcLoc("block/polished_diorite"), mcLoc("block/diorite_pillar"));
		makeBlockItemFromExistingModel(ModBlocks.DIORITE_PEDESTAL.get(), "diorite_pedestal_default");
		pedestalBlock(ModBlocks.GRANITE_PEDESTAL.get(), "granite", mcLoc("block/polished_granite"), mcLoc("block/granite_pillar"));
		makeBlockItemFromExistingModel(ModBlocks.GRANITE_PEDESTAL.get(), "granite_pedestal_default");
		pedestalBlock(ModBlocks.PURPUR_PEDESTAL.get(), "purpur", mcLoc("block/purpur_block"), mcLoc("block/purpur_pillar"));
		makeBlockItemFromExistingModel(ModBlocks.PURPUR_PEDESTAL.get(), "purpur_pedestal_default");
		pedestalBlock(ModBlocks.QUARTZ_PEDESTAL.get(), "quartz", mcLoc("block/quartz_block_side"), mcLoc("block/quartz_pillar"));
		makeBlockItemFromExistingModel(ModBlocks.QUARTZ_PEDESTAL.get(), "quartz_pedestal_default");
	}
	
	private void registerFlowerPots()
	{
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
			if (block instanceof FlowerPotBlock)
			{
				flowerPotBlock(block, ((FlowerPotBlock) block).getFlower());
			}
		});
	}

	private void registerWoodenMaterialBlockStates(WoodenMaterial material)
	{
		ResourceLocation planksTexture = modLoc("block/" + material.name + "_planks");
		
		simpleBlock(material.planks.get());
		makeBlockItemFromExistingModel(material.planks.get());
		
		stairsBlock((StairsBlock) material.stairs.get(), planksTexture);
		makeBlockItemFromExistingModel(material.stairs.get());
		
		slabBlock((SlabBlock) material.slab.get(), material.planks.get().getRegistryName(), planksTexture);
		makeBlockItemFromExistingModel(material.slab.get());
        
		// BlockItem handled in item model provider
		fenceBlock((FenceBlock) material.fence.get(), planksTexture);
        
		fenceGateBlock((FenceGateBlock) material.gate.get(), planksTexture);
		makeBlockItemFromExistingModel(material.gate.get());

		// BlockItem handled in item model provider
		doorBlock((DoorBlock) material.door.get(), modLoc("block/" + material.name + "_door_bottom"), modLoc("block/" + material.name + "_door_top"));
        
        trapdoorBlock((TrapDoorBlock) material.trapdoor.get(), modLoc("block/" + material.name + "_trapdoor"), true);		
	    makeBlockItemFromExistingModel(material.trapdoor.get(), "block/" + material.name + "_trapdoor_bottom");	    	
	
	    // BlockItem handled in item model provider
	    buttonBlock((WoodButtonBlock)material.button.get(), material.name, planksTexture);

		pressurePlateBlock((PressurePlateBlock)material.pressurePlate.get(), material.name, planksTexture);
		makeBlockItemFromExistingModel(material.pressurePlate.get());
		
	    composterBlock((ComposterBlock)material.composter.get(), material.name);
		makeBlockItemFromExistingModel(material.composter.get());
	    craftingTableBlock((CraftingTableBlock)material.craftingTable.get(), material.name);
		makeBlockItemFromExistingModel(material.craftingTable.get());
	    // BlockItem handled in item model provider
	    ladderBlock((LadderBlock)material.ladder.get(), material.name);
	    // BlockItem handled in item model provider
	    chestBlock(material.chest.get(), material.name);
	    // BlockItem handled in item model provider
	    signBlock(material.sign.get(), material.name);
	    barrelBlock(material.barrel.get(), material.name);
		makeBlockItemFromExistingModel(material.barrel.get());
	    shelfBlock(material.shelf.get(), material.name);
		makeBlockItemFromExistingModel(material.shelf.get());
	}
	
	private void registerStoneMaterialBlockStates(StoneMaterial material)
	{
		ResourceLocation stoneTexture = modLoc("block/" + material.name);
		
		simpleBlock(material.stone.get());
		makeBlockItemFromExistingModel(material.stone.get());
		
		simpleBlock(material.polished.get());
		makeBlockItemFromExistingModel(material.polished.get());
		
		simpleBlock(material.tiles.get());
		makeBlockItemFromExistingModel(material.tiles.get());
		
		axisBlock((PillarBlockTemplate)material.pillar.get(), modLoc("block/" + material.name + "_pillar_side"), modLoc("block/" + material.name + "_pillar_top"));
		makeBlockItemFromExistingModel(material.pillar.get());
		
		stairsBlock((StairsBlock) material.stairs.get(), modLoc("block/" + material.name));
		makeBlockItemFromExistingModel(material.stairs.get());
		
		slabBlock((SlabBlock) material.slab.get(), material.stone.get().getRegistryName(), modLoc("block/" + material.name));
		makeBlockItemFromExistingModel(material.slab.get());

		// BlockItem handled in item model provider
		wallBlock((WallBlock) material.wall.get(), stoneTexture);
		
		simpleBlock(material.bricks.get());
		makeBlockItemFromExistingModel(material.bricks.get());
		
		stairsBlock((StairsBlock) material.brick_stairs.get(), modLoc("block/" + material.name + "_bricks"));
		makeBlockItemFromExistingModel(material.brick_stairs.get());
		
		slabBlock((SlabBlock) material.brick_slab.get(), material.bricks.get().getRegistryName(), modLoc("block/" + material.name + "_bricks"));
		makeBlockItemFromExistingModel(material.brick_slab.get());
		
		// BlockItem handled in item model provider
		wallBlock((WallBlock) material.brick_wall.get(), modLoc("block/" + material.name + "_bricks"));
		
		// BlockItem handled in item model provider
		buttonBlock((StoneButtonBlock)material.button.get(), material.name, modLoc("block/" + material.name));
		
		pressurePlateBlock((PressurePlateBlock)material.pressure_plate.get(), material.name, stoneTexture);
		makeBlockItemFromExistingModel(material.pressure_plate.get());
		
		lanternBlock(material.lantern.get(), material.name);
		makeBlockItemFromExistingModel(material.lantern.get(), material.name + "_lantern_ceil");

		pedestalBlock(material.pedestal.get(), material.name, modLoc("block/" + material.name + "_polished"), modLoc("block/" + material.name + "_pillar_side"));
		makeBlockItemFromExistingModel(material.pedestal.get(), material.name + "_pedestal_default");
		
		furnaceBlock(material.furnace.get(), material.name);
		makeBlockItemFromExistingModel(material.furnace.get(), material.name + "_furnace");
	}	
	
	private void registerMetalMaterialBlockStates(MetalMaterial material)
	{
		if (material.hasOre)
		{		
			simpleBlock(material.ore.get());
			makeBlockItemFromExistingModel(material.ore.get());
		}
		
		simpleBlock(material.block.get());
		makeBlockItemFromExistingModel(material.block.get());
		
		simpleBlock(material.tile.get());
		makeBlockItemFromExistingModel(material.tile.get());
		
		stairsBlock((StairsBlock) material.stairs.get(), modLoc("block/" + material.name + "_tile"));
		makeBlockItemFromExistingModel(material.stairs.get());
		
		slabBlock((SlabBlock) material.slab.get(), material.tile.get().getRegistryName(), modLoc("block/" + material.name + "_tile"));
		makeBlockItemFromExistingModel(material.slab.get());
		
		// BlockItem handled in item model provider
		doorBlock((DoorBlock) material.door.get(), modLoc("block/" + material.name + "_door_bottom"), modLoc("block/" + material.name + "_door_top"));
        
		trapdoorBlock((TrapDoorBlock) material.trapdoor.get(), modLoc("block/" + material.name + "_trapdoor"), true);		
	    makeBlockItemFromExistingModel(material.trapdoor.get(), "block/" + material.name + "_trapdoor_bottom");
	    
		pressurePlateBlock((PressurePlateBlock)material.pressure_plate.get(), material.name, modLoc("block/" + material.name + "_block"));
		makeBlockItemFromExistingModel(material.pressure_plate.get());
		
		// BlockItem handled in item model provider
		chainBlock(material.chain.get());
		
		// BlockItem handled in item model provider
		barsBlock(material.bars.get());
		
		// BlockItem handled in item model provider
		chandelierBlock(material.chandelier.get());
		
		bulbLanterns(material.name, material.bulb_lantern.get(), material.bulb_lantern_colored);
		
		anvilBlock(material.anvil.get(), material.name);
		makeBlockItemFromExistingModel(material.anvil.get(), "block/" + material.name + "_anvil_0");
	}
	
	private void registerColoredMaterialBlockStates(ColoredMaterial material, String blockModel)
	{
		for (Block block : material.getBlocks())
		{
			ModelFile model = models().getExistingFile(modLoc("block/" + blockModel));
			simpleBlock(block, model);
			makeBlockItemFromExistingModel(block, "block/" + blockModel);
		}
	}
	
	private void makeBlockItemFromExistingModel(Block block)
	{
		final ModelFile model = models().getExistingFile(block.getRegistryName());
		simpleBlockItem(block, model);
	}
	
	private void makeBlockItemFromExistingModel(Block block, String name)
	{
		final ModelFile model = models().getExistingFile(modLoc(name));
		simpleBlockItem(block, model);
	}
	
    private void buttonBlock(AbstractButtonBlock block, String material, ResourceLocation texture) 
    {
    	ModelFile button = models().singleTexture(material + "_button", mcLoc("block/button"), texture);
    	ModelFile buttonPressed = models().singleTexture(material + "_button_pressed", mcLoc("block/button_pressed"), texture);
    	int angleOffset = 180;
        getVariantBuilder(block)
            .forAllStates(state -> {
               boolean powered = state.get(WoodButtonBlock.POWERED);

               return ConfiguredModel.builder()
               .modelFile(powered == true ? buttonPressed : button)
               .rotationX(state.get(BlockStateProperties.FACE).ordinal() * 90)
               .rotationY((((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle() + angleOffset) + (state.get(BlockStateProperties.FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
               .build();
            });
    }
    
    private void pressurePlateBlock(PressurePlateBlock block, String material, ResourceLocation texture)
    {
    	ModelFile plate = models().singleTexture(material + "_pressure_plate", mcLoc("block/pressure_plate_up"), texture);
    	ModelFile plateDown = models().singleTexture(material + "_pressure_plate_down", mcLoc("block/pressure_plate_down"), texture);
        getVariantBuilder(block)
        .forAllStates(state -> {
           boolean powered = state.get(PressurePlateBlock.POWERED);

           return ConfiguredModel.builder()
           .modelFile(powered == true ? plateDown : plate)
           .build();
        });
    }
    
    private void composterBlock(ComposterBlock block, String material)
    {
    	ModelFile composter = models().withExistingParent(material + "_composter", mcLoc("composter"))
    			.texture("particle", modLoc("block/" + material + "_composter_side"))
    			.texture("top", modLoc("block/" + material + "_composter_top"))
    			.texture("bottom", modLoc("block/" + material + "_composter_bottom"))
    			.texture("side", modLoc("block/" + material + "_composter_side"))
    			.texture("inside", modLoc("block/" + material + "_composter_bottom"));
    	ModelFile composterReady = models().withExistingParent(material + "_composter_contents_ready", mcLoc("composter_contents_ready"))
    			.texture("particle", mcLoc("block/composter_compost"))
    			.texture("inside", mcLoc("block/composter_ready"));
    	ModelFile[] contents = new ModelFile[7];
    	for (int i = 0; i < contents.length; i++)
    		contents[i] = models().withExistingParent(material + "_composter_contents" + (i + 1), mcLoc("composter_contents" + (i + 1)))
	    			.texture("particle", mcLoc("block/composter_compost"))
	    			.texture("inside", mcLoc("block/composter_compost"));
    	getMultipartBuilder(block).part().modelFile(composter).addModel().end();
    	for (int i = 0; i < contents.length; i++)
    		getMultipartBuilder(block).part().modelFile(contents[i]).addModel().condition(ComposterBlock.LEVEL, i + 1).end();
		getMultipartBuilder(block).part().modelFile(composterReady).addModel().condition(ComposterBlock.LEVEL, 8).end();
    }
    
    private void craftingTableBlock(CraftingTableBlock block, String material)
    {
		ModelFile model = models()
				.cube(material + "_crafting_table", modLoc("block/" + material + "_crafting_table_bottom"),
						modLoc("block/" + material + "_crafting_table_top"),
						modLoc("block/" + material + "_crafting_table_front"),
						modLoc("block/" + material + "_crafting_table_side"),
						modLoc("block/" + material + "_crafting_table_side"),
						modLoc("block/" + material + "_crafting_table_front"))
				.texture("particle", modLoc("block/" + material + "_crafting_table_top"));
		simpleBlock(block, model);
    }
    
    private void ladderBlock(LadderBlock block, String material)
    {
    	ModelFile ladder = models().withExistingParent(material + "_ladder", modLoc("block/ladder"))
    			.texture("texture", modLoc("block/" + material + "_ladder"));
    	horizontalBlock(block, ladder);
    }
    
    private void chestBlock(Block block, String material)
    {
    	ModelFile texture = models().getBuilder(material + "_chest").texture("particle", modLoc("block/" + material + "_planks"));
    	simpleBlock(block, texture);
    }
    
    private void signBlock(Block block, String material)
    {
    	ModelFile texture = models().getBuilder(material + "_sign").texture("particle", modLoc("block/" + material + "_planks"));
    	simpleBlock(block, texture);
    }
    
    private void barrelBlock(Block block, String material)
    {
    	ModelFile open = models().withExistingParent(material + "_barrel_open", mcLoc("block/cube_bottom_top"))
    			.texture("top", modLoc("block/" + material + "_barrel_top_open"))
    			.texture("bottom", modLoc("block/" + material + "_barrel_bottom"))
    			.texture("side", modLoc("block/" + material + "_barrel_side"));
    	ModelFile closed = models().withExistingParent(material + "_barrel", mcLoc("block/cube_bottom_top"))
    			.texture("top", modLoc("block/" + material + "_barrel_top"))
    			.texture("bottom", modLoc("block/" + material + "_barrel_bottom"))
    			.texture("side", modLoc("block/" + material + "_barrel_side"));
        getVariantBuilder(block)
        .forAllStates(state -> {
           boolean opened = state.get(BarrelBlock.PROPERTY_OPEN);
           Direction dir = state.get(BarrelBlock.PROPERTY_FACING);
           int x = 0;
           int y = 0;
           switch (dir) {
           case DOWN:
        	   x = 180;
        	   break;
           case EAST:
        	   x = 90;
        	   y = 90;
        	   break;
           case NORTH:
        	   x = 90;
        	   break;
           case SOUTH:
        	   x = 90;
        	   y = 180;
        	   break;
           case UP:
        	   break;
           case WEST:
        	   x = 90;
        	   y = 270;
        	   break;
           }
           
           return ConfiguredModel.builder()
           .modelFile(opened ? open : closed)
           .rotationX(x)
           .rotationY(y)
           .build();
        });
    }
    
    private void shelfBlock(Block block, String material)
    {
		ModelFile texture = models().cubeColumn(material + "_bookshelf", modLoc("block/" + material + "_bookshelf"),
				modLoc("block/" + material + "_planks"));
		simpleBlock(block, texture);
    }
    
    private void lanternBlock(Block block, String material)
    {
    	ModelFile ceil = models().withExistingParent(material + "_lantern_ceil", modLoc("block/stone_lantern_ceil"))
    			.texture("particle", modLoc("block/" + material + "_lantern_side"))
    			.texture("texture", modLoc("block/" + material + "_lantern_side"))
    			.texture("top", modLoc("block/" + material + "_lantern_top"))
    			.texture("crystal", modLoc("block/aurora_crystal"))
    			.texture("bottom", modLoc("block/" + material + "_lantern_bottom"));
    	ModelFile floor = models().withExistingParent(material + "_lantern_floor", modLoc("block/stone_lantern_floor"))
    			.texture("particle", modLoc("block/" + material + "_lantern_side"))
    			.texture("texture", modLoc("block/" + material + "_lantern_side"))
    			.texture("top", modLoc("block/" + material + "_lantern_top"))
    			.texture("crystal", modLoc("block/aurora_crystal"))
    			.texture("bottom", modLoc("block/" + material + "_lantern_bottom"));
        getVariantBuilder(block)
        .forAllStates(state -> {
           boolean isFloor = !state.get(LanternBlock.HANGING);
           return ConfiguredModel.builder()
           .modelFile(isFloor ? floor : ceil)
           .build();
        });
    }
    
    private void pedestalBlock(Block block, String material, ResourceLocation polished, ResourceLocation pillar)
    {
    	Map<PedestalState, ModelFile> models = new HashMap<>();
    	models.put(PedestalState.COLUMN_TOP, models().withExistingParent(material + "_pedestal_column_top", modLoc("block/pedestal_column_top"))
    			.texture("base", polished)
    			.texture("pillar", pillar));
    	models.put(PedestalState.COLUMN, models().withExistingParent(material + "_pedestal_column", modLoc("block/pedestal_column"))
    			.texture("base", polished)
    			.texture("pillar", pillar)
    			.texture("bottom", polished));
    	models.put(PedestalState.PEDESTAL_TOP, models().withExistingParent(material + "_pedestal_top", modLoc("block/pedestal_top"))
    			.texture("top", polished)
    			.texture("base", polished)
    			.texture("pillar", pillar));
    	models.put(PedestalState.BOTTOM, models().withExistingParent(material + "_pedestal_bottom", modLoc("block/pedestal_bottom"))
    			.texture("base", polished)
    			.texture("pillar", pillar)
    			.texture("bottom", polished));
    	models.put(PedestalState.PILLAR, models().withExistingParent(material + "_pedestal_pillar", modLoc("block/pedestal_pillar"))
    			.texture("pillar", pillar));
    	models.put(PedestalState.DEFAULT, models().withExistingParent(material + "_pedestal_default", modLoc("block/pedestal_default"))
    			.texture("top", polished)
    			.texture("base", polished)
    			.texture("pillar", pillar)
    			.texture("bottom", polished));
		getVariantBuilder(block).forAllStates(state -> {
			PedestalState pedestalState = state.get(PedestalBlock.STATE);
			return ConfiguredModel.builder().modelFile(models.get(pedestalState)).build();
		});
    }
    
    private void bulbLanterns(String material, Block bulbLantern, ColoredMaterial bulbLanternColored)
    {
		ModelFile ceil = models().withExistingParent(material + "_bulb_lantern_colored_ceil", modLoc("bulb_lantern_colored_ceil")).
				texture("metal", modLoc("block/" + material + "_bulb_vine_lantern_metal"));
		ModelFile floor = models().withExistingParent(material + "_bulb_lantern_colored_floor", modLoc("bulb_lantern_colored_floor")).
				texture("metal", modLoc("block/" + material + "_bulb_vine_lantern_metal"));
		
		List<Block> lanterns = new ArrayList<>();
		lanterns.add(bulbLantern);
        Collections.addAll(lanterns, bulbLanternColored.getBlocks());
        for (Block lantern : lanterns) {
	        getVariantBuilder(lantern)
	        .forAllStates(state -> {
	           boolean isFloor = !state.get(LanternBlock.HANGING);
	           return ConfiguredModel.builder()
	           .modelFile(isFloor ? floor : ceil)
	           .build();
	        });
			makeBlockItemFromExistingModel(lantern, material + "_bulb_lantern_colored_ceil");
        }
    }
    
    private void flowerPotBlock(Block pot_block, Block plant)
    {
    	ModelFile pot = models().withExistingParent("potted_" + plant.getRegistryName().getPath(), mcLoc("block/flower_pot_cross"))
    			.texture("plant", modLoc("block/" + plant.getRegistryName().getPath()));
    	simpleBlock(pot_block, pot);
    }
    
    private void chainBlock(Block chain_block)
    {
    	ModelFile chain = models().withExistingParent(chain_block.getRegistryName().getPath(), mcLoc("block/chain"))
    			.texture("particle", modLoc("block/" + chain_block.getRegistryName().getPath()))
    			.texture("all", modLoc("block/" + chain_block.getRegistryName().getPath()));
    	axisBlock((RotatedPillarBlock)chain_block, chain, chain);
    }
    
    private void barsBlock(Block barsBlock) 
    {
        ModelFile post = models().withExistingParent(barsBlock.getRegistryName().
        		getPath() + "_post", modLoc("metal_bars_post")).
        		texture("top", modLoc("block/" + barsBlock.getRegistryName().getPath() + "_top"));
        ModelFile side = models().withExistingParent(barsBlock.getRegistryName().
        		getPath() + "_side", modLoc("metal_bars_side")).
        		texture("top", modLoc("block/" + barsBlock.getRegistryName().getPath() + "_top")).
        		texture("side", modLoc("block/" + barsBlock.getRegistryName().getPath()));
       
        MultiPartBlockStateBuilder builder = getMultipartBuilder(barsBlock)
                .part().modelFile(post).addModel().
                condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.NORTH), false).
                condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.SOUTH), false).
                condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.EAST), false).
                condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.WEST), false).end();

        fourWayMultipart(builder, side);
    }
    
    private void chandelierBlock(Block chandelier)
    {
		ModelFile ceil = models().withExistingParent(chandelier.getRegistryName().getPath() + "_ceil", 
				modLoc("chandelier_ceil")).
				texture("rod", modLoc("block/" + chandelier.getRegistryName().getPath() + "_floor")).
				texture("texture", modLoc("block/" + chandelier.getRegistryName().getPath() + "_ceil"));
		ModelFile wall = models().withExistingParent(chandelier.getRegistryName().getPath() + "_wall", 
				modLoc("chandelier_wall")).
				texture("texture", modLoc("block/" + chandelier.getRegistryName().getPath() + "_wall"));
		ModelFile floor = models().withExistingParent(chandelier.getRegistryName().getPath() + "_floor", 
				modLoc("chandelier_floor")).
				texture("texture", modLoc("block/" + chandelier.getRegistryName().getPath() + "_floor"));
		
		getVariantBuilder(chandelier)
        .forAllStates(state -> {
           Direction dir = state.get(AttachedBlock.FACING);
           int y = 0;
           switch (dir) 
           {
           case DOWN:
        	   break;
           case EAST:
        	   y = 270;
        	   break;
           case NORTH:
        	   y = 180;
        	   break;
           case SOUTH:
        	   break;
           case UP:
        	   break;
           case WEST:
        	   y = 90;
        	   break;
           }
           
           return ConfiguredModel.builder()
           .modelFile(dir == Direction.UP ? floor : dir == Direction.DOWN ? ceil : wall)
           .rotationY(y)
           .build();
        });
    }
    
    private void anvilBlock(Block block, String material)
    {
        getVariantBuilder(block)
        .forAllStates(state -> {
           int destruction = state.get(EndAnvilBlock.DESTRUCTION);
       	   
           ModelFile anvil = models().withExistingParent(material + "_anvil_" + destruction, modLoc("block/anvil"))
        		   .texture("front", modLoc("block/" + material + "_anvil_front"))
    			   .texture("bottom", modLoc("block/" + material + "_anvil_bottom"))
    			   .texture("back", modLoc("block/" + material + "_anvil_back"))
    			   .texture("panel", modLoc("block/" + material + "_anvil_panel"))
    			   .texture("top", modLoc("block/" + material + "_anvil_top_" + destruction));
           
           Direction dir = state.get(AnvilBlock.FACING);
           int x = 0;
           int y = 0;
           switch (dir) 
           {
           case EAST:
        	   y = 270;
        	   break;
           case NORTH:
        	   y = 180;
        	   break;
           case SOUTH:
        	   break;
           case WEST:
        	   y = 90;
        	   break;
		   default:
			   break;
           }
           
           return ConfiguredModel.builder()
           .modelFile(anvil)
           .rotationX(x)
           .rotationY(y)
           .build();
        });
    }
    
    private void furnaceBlock(Block block, String material)
    {
        ModelFile furnace = models().withExistingParent(material + "_furnace", mcLoc("block/orientable_with_bottom"))
     		   .texture("top", modLoc("block/" + material + "_furnace_top"))
 			   .texture("front", modLoc("block/" + material + "_furnace_front"))
 			   .texture("side", modLoc("block/" + material + "_furnace_side"))
 			   .texture("bottom", modLoc("block/" + material + "_furnace_top"));
        
        ModelFile furnaceOn = models().withExistingParent(material + "_furnace_on", modLoc("block/furnace_glow"))
      		   .texture("top", modLoc("block/" + material + "_furnace_top"))
  			   .texture("front", modLoc("block/" + material + "_furnace_front_on"))
  			   .texture("side", modLoc("block/" + material + "_furnace_side"))
  			   .texture("glow", modLoc("block/" + material + "_furnace_glow"));
        
        getVariantBuilder(block)
        .forAllStates(state -> {
           boolean isLit = state.get(FurnaceBlock.LIT);
       	           
           Direction dir = state.get(FurnaceBlock.FACING);
           int x = 0;
           int y = 0;
           switch (dir) 
           {
           case EAST:
        	   y = 90;
        	   break;
           case NORTH:
        	   break;
           case SOUTH:
        	   y = 180;
        	   break;
           case WEST:
        	   y = 270;
        	   break;
		   default:
			   break;
           }
           
           return ConfiguredModel.builder()
           .modelFile(isLit? furnaceOn : furnace)
           .rotationX(x)
           .rotationY(y)
           .build();
        });
    }
   
    private void stalactiteBlock(Block block)
    {       
        getVariantBuilder(block)
        .forAllStates(state -> {
           boolean isFloor = state.get(StalactiteBlock.IS_FLOOR);
       	           
           int size = state.get(StalactiteBlock.SIZE);
           
           ModelFile currentSize = models().withExistingParent(block.getRegistryName().getPath() + "_" + size, mcLoc("block/cross"))
         		   .texture("cross", modLoc("block/" + block.getRegistryName().getPath() + "_" + size));
           
           return ConfiguredModel.builder()
           .modelFile(currentSize)
           .rotationX(isFloor? 0 : 180)
           .build();
        });
    }
}
