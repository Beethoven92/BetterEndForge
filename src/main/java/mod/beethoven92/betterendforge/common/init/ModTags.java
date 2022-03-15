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
	public static final TagKey<Block> END_GROUND = makeModBlockTag("end_ground");
	public static final TagKey<Block> GEN_TERRAIN = makeModBlockTag("gen_terrain");
	public static final TagKey<Block> MINING_HAMMER = makeBlockTag("minecraft", "mineable/hammer");
	
	// MOD ITEM TAGS	
	public static final TagKey<Item> HAMMERS = makeModItemTag("hammers");
	public static final TagKey<Item> FURNACES = makeModItemTag("furnaces");
	
	// CUSTOM FORGE BLOCK TAGS
	
	// Used by the Metal Barrels mod
	public static final TagKey<Block> BLOCK_BARRELS = makeBlockTag("forge", "barrels/wooden");
	
	// CUSTOM FORGE ITEM TAGS
	
	// Used by the Metal Barrels mod
	public static final TagKey<Item> ITEM_BARRELS = makeItemTag("forge", "barrels/wooden");
	
	
	public static TagKey<Block> makeModBlockTag(final String name)
	{
		return BlockTags.bind(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static TagKey<Item> makeModItemTag(final String name)
	{
		return ItemTags.bind(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static TagKey<Block> makeBlockTag(String namespace, String name)
	{
		return BlockTags.bind(new ResourceLocation(namespace, name).toString());
	}
	
	public static TagKey<Item> makeItemTag(String namespace, String name)
	{
		return ItemTags.bind(new ResourceLocation(namespace, name).toString());
	}
	
	public static boolean validGenBlock(BlockState block) 
	{
		return block.is(END_GROUND) || block.is(GEN_TERRAIN);
	}
}
