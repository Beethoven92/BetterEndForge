package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags 
{
	// MOD BLOCK TAGS	
	public static final ITag.INamedTag<Block> END_GROUND = makeModBlockTag("end_ground");
	public static final ITag.INamedTag<Block> GEN_TERRAIN = makeModBlockTag("gen_terrain");
	
	// MOD ITEM TAGS	
	public static final ITag.INamedTag<Item> HAMMERS = makeModItemTag("hammers");
	public static final ITag.INamedTag<Item> FURNACES = makeModItemTag("furnaces");
	
	// CUSTOM FORGE BLOCK TAGS
	
	// Used by the Metal Barrels mod
	public static final ITag.INamedTag<Block> BLOCK_BARRELS = makeBlockTag("forge", "barrels/wooden");
	
	// CUSTOM FORGE ITEM TAGS
	
	// Used by the Metal Barrels mod
	public static final ITag.INamedTag<Item> ITEM_BARRELS = makeItemTag("forge", "barrels/wooden");
	
	
	public static ITag.INamedTag<Block> makeModBlockTag(final String name) 
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static ITag.INamedTag<Item> makeModItemTag(final String name) 
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static ITag.INamedTag<Block> makeBlockTag(String namespace, String name) 
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(namespace, name).toString());
	}
	
	public static ITag.INamedTag<Item> makeItemTag(String namespace, String name) 
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(namespace, name).toString());
	}
	
	public static boolean validGenBlock(BlockState block) 
	{
		return block.isIn(END_GROUND) || block.isIn(GEN_TERRAIN);
	}
}
