package mod.beethoven92.betterendforge.data.client;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
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
		// WOODEN MATERIALS
		registerWoodenMaterialItemModels(ModBlocks.MOSSY_GLOWSHROOM);
		registerWoodenMaterialItemModels(ModBlocks.LACUGROVE);
		registerWoodenMaterialItemModels(ModBlocks.END_LOTUS);
		registerWoodenMaterialItemModels(ModBlocks.PYTHADENDRON);
		registerWoodenMaterialItemModels(ModBlocks.DRAGON_TREE);
		registerWoodenMaterialItemModels(ModBlocks.TENANEA);
		
		// STONE MATERIALS
		registerStoneMaterialItemModels(ModBlocks.FLAVOLITE);
	}
	
	private void registerWoodenMaterialItemModels(WoodenMaterial material)
	{
        getBuilder(material.name + "_door").parent(new ModelFile.UncheckedModelFile("item/generated")).
        		texture("layer0", modLoc("item/" + material.name + "_door"));

        fenceInventory(material.name + "_fence", modLoc("block/" + material.name + "_planks"));
        
        buttonInventory(material.name, modLoc("block/" + material.name + "_planks"));
	}
	
	private void registerStoneMaterialItemModels(StoneMaterial material)
	{
		wallInventory(material.name + "_wall", modLoc("block/" + material.name));
		
		wallInventory(material.name + "_bricks_wall", modLoc("block/" + material.name + "_bricks"));
		
		buttonInventory(material.name, modLoc("block/" + material.name));
	}
	
	private void buttonInventory(String material, ResourceLocation texture)
	{
        singleTexture(material + "_button", mcLoc(BLOCK_FOLDER + "/button_inventory"), texture);
	}
}
