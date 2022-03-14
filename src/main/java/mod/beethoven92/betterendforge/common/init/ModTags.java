package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class ModTags 
{
	// MOD BLOCK TAGS	
	public static final Tag.Named<Block> END_GROUND = makeModBlockTag("end_ground");
	public static final Tag.Named<Block> GEN_TERRAIN = makeModBlockTag("gen_terrain");
	
	// MOD ITEM TAGS	
	public static final Tag.Named<Item> HAMMERS = makeModItemTag("hammers");
	public static final Tag.Named<Item> FURNACES = makeModItemTag("furnaces");
	
	// CUSTOM FORGE BLOCK TAGS
	
	// Used by the Metal Barrels mod
	public static final Tag.Named<Block> BLOCK_BARRELS = makeBlockTag("forge", "barrels/wooden");
	
	// CUSTOM FORGE ITEM TAGS
	
	// Used by the Metal Barrels mod
	public static final Tag.Named<Item> ITEM_BARRELS = makeItemTag("forge", "barrels/wooden");
	
	
	public static Tag.Named<Block> makeModBlockTag(final String name) 
	{
		return BlockTags.bind(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static Tag.Named<Item> makeModItemTag(final String name) 
	{
		return ItemTags.bind(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static Tag.Named<Block> makeBlockTag(String namespace, String name) 
	{
		return BlockTags.bind(new ResourceLocation(namespace, name).toString());
	}
	
	public static Tag.Named<Item> makeItemTag(String namespace, String name) 
	{
		return ItemTags.bind(new ResourceLocation(namespace, name).toString());
	}
	
	public static boolean validGenBlock(BlockState block) 
	{
		return block.is(END_GROUND) || block.is(GEN_TERRAIN);
	}
}
