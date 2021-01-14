package mod.beethoven92.betterendforge.data.client;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.block.template.PillarBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStates extends BlockStateProvider
{
	public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) 
	{
		super(gen, BetterEnd.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() 
	{
		// WOODEN MATERIALS
		registerWoodenMaterialBlockStates(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialBlockStates(ModBlocks.LACUGROVE);
		registerWoodenMaterialBlockStates(ModBlocks.END_LOTUS);
		registerWoodenMaterialBlockStates(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialBlockStates(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialBlockStates(ModBlocks.TENANEA);
		registerWoodenMaterialBlockStates(ModBlocks.HELIX_TREE);
		registerWoodenMaterialBlockStates(ModBlocks.UMBRELLA_TREE);
		
		// STONE MATERIALS
		registerStoneMaterialBlockStates(ModBlocks.FLAVOLITE);
		//registerStoneMaterialBlockStates(ModBlocks.VIOLECITE);
		registerStoneMaterialBlockStates(ModBlocks.SULPHURIC_ROCK);
		
		// COLORED MATERIALS
		registerColoredMaterialBlockStates(ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED, "block_petal_colored");
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
}
