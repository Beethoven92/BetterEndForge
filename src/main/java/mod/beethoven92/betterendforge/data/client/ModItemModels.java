package mod.beethoven92.betterendforge.data.client;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider
{
	public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) 
	{
		super(generator, BetterEnd.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() 
	{
		// BLOCK ITEMS
        getBuilder("iron_chandelier").parent(new ModelFile.UncheckedModelFile("item/generated")).
        texture("layer0", modLoc("item/iron_chandelier"));
        getBuilder("gold_chandelier").parent(new ModelFile.UncheckedModelFile("item/generated")).
        texture("layer0", modLoc("item/gold_chandelier"));
        
        getBuilder("end_stone_stalactite").parent(new ModelFile.UncheckedModelFile("item/generated")).
        texture("layer0", modLoc("item/end_stone_stalactite"));
        getBuilder("end_stone_stalactite_cavemoss").parent(new ModelFile.UncheckedModelFile("item/generated")).
        texture("layer0", modLoc("item/end_stone_stalactite_cavemoss"));
        
        
        // WOODEN MATERIALS
		registerWoodenMaterialItemModels(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialItemModels(ModBlocks.LACUGROVE);
		registerWoodenMaterialItemModels(ModBlocks.END_LOTUS);
		registerWoodenMaterialItemModels(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialItemModels(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialItemModels(ModBlocks.TENANEA);
		registerWoodenMaterialItemModels(ModBlocks.HELIX_TREE);
		registerWoodenMaterialItemModels(ModBlocks.UMBRELLA_TREE);
		registerWoodenMaterialItemModels(ModBlocks.JELLYSHROOM);
		registerWoodenMaterialItemModels(ModBlocks.LUCERNIA);
		
		// STONE MATERIALS
		registerStoneMaterialItemModels(ModBlocks.FLAVOLITE);
		registerStoneMaterialItemModels(ModBlocks.VIOLECITE);
		registerStoneMaterialItemModels(ModBlocks.SULPHURIC_ROCK);
		registerStoneMaterialItemModels(ModBlocks.VIRID_JADESTONE);
		registerStoneMaterialItemModels(ModBlocks.AZURE_JADESTONE);
		registerStoneMaterialItemModels(ModBlocks.SANDY_JADESTONE);
		registerStoneMaterialItemModels(ModBlocks.UMBRALITH);
		
		// METAL MATERIALS
		registerMetalMaterialItemModels(ModBlocks.THALLASIUM);
		registerMetalMaterialItemModels(ModBlocks.TERMINITE);
		
		simpleItem(ModItems.MUSIC_DISC_ENDSEEKER.get());
		simpleItem(ModItems.MUSIC_DISC_EO_DRACONA.get());
		simpleItem(ModItems.MUSIC_DISC_GRASPING_AT_STARS.get());
		simpleItem(ModItems.MUSIC_DISC_STRANGE_AND_ALIEN.get());
	}
	
	private ItemModelBuilder simpleItem(Item item) {
		String name = item.getRegistryName().getPath();
		return singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(ITEM_FOLDER + "/" + name));
	}
	
	private void registerWoodenMaterialItemModels(WoodenMaterial material)
	{
        getBuilder(material.name + "_door").parent(new ModelFile.UncheckedModelFile("item/generated")).
        		texture("layer0", modLoc("item/" + material.name + "_door"));

        fenceInventory(material.name + "_fence", modLoc("block/" + material.name + "_planks"));
        
        buttonInventory(material.name, modLoc("block/" + material.name + "_planks"));
        
        singleTexture(material.name + "_ladder", mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc("block/" + material.name + "_ladder"));
        
		getBuilder(material.name + "_chest").parent(new ModelFile.UncheckedModelFile(ITEM_FOLDER + "/chest")).texture("particle",
				modLoc("block/" + material.name + "_planks"));
		
        singleTexture(material.name + "_sign", mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc("item/" + material.name + "_sign"));
	}
	
	private void registerStoneMaterialItemModels(StoneMaterial material)
	{
		wallInventory(material.name + "_wall", modLoc("block/" + material.name));
		
		wallInventory(material.name + "_bricks_wall", modLoc("block/" + material.name + "_bricks"));
		
		buttonInventory(material.name, modLoc("block/" + material.name));
	}
	
	private void registerMetalMaterialItemModels(MetalMaterial material)
	{
        getBuilder(material.name + "_door").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_door"));
        
        getBuilder(material.name + "_chain").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_chain"));
       
       getBuilder(material.name + "_bars").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("block/" + material.name + "_bars"));
        
        getBuilder(material.name + "_chandelier").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_chandelier"));
        
        getBuilder(material.name + "_nugget").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_nugget"));
        
        getBuilder(material.name + "_ingot").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_ingot"));
       
        // Tool parts
        getBuilder(material.name + "_shovel_head").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_shovel_head"));

        getBuilder(material.name + "_pickaxe_head").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_pickaxe_head"));

        getBuilder(material.name + "_axe_head").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_axe_head"));

        getBuilder(material.name + "_hoe_head").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_hoe_head"));

        getBuilder(material.name + "_sword_handle").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_sword_handle"));

        getBuilder(material.name + "_sword_blade").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_sword_blade"));

        // Tools and armor
        getBuilder(material.name + "_shovel").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_shovel"));
        
        getBuilder(material.name + "_sword").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_sword"));
        
        getBuilder(material.name + "_pickaxe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_pickaxe"));
        
        getBuilder(material.name + "_axe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_axe"));
        
        getBuilder(material.name + "_hoe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_hoe"));
       
        getBuilder(material.name + "_hammer").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_hammer"));
       
        getBuilder(material.name + "_helmet").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_helmet"));
        
        getBuilder(material.name + "_chestplate").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_chestplate"));
        
        getBuilder(material.name + "_leggings").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_leggings"));
       
        getBuilder(material.name + "_boots").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_boots"));
	}
	
	private void buttonInventory(String material, ResourceLocation texture)
	{
        singleTexture(material + "_button", mcLoc(BLOCK_FOLDER + "/button_inventory"), texture);
	}
}
