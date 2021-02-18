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
	public static final ITag.INamedTag<Block> END_GROUND = makeBlockTag("end_ground");
	public static final ITag.INamedTag<Block> GEN_TERRAIN = makeBlockTag("gen_terrain");
	
	public static final ITag.INamedTag<Item> HAMMERS = makeItemTag("hammers");
	public static final ITag.INamedTag<Item> FURNACES = makeItemTag("furnaces");
	
	public static ITag.INamedTag<Block> makeBlockTag(final String name) 
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}
	
	public static ITag.INamedTag<Item> makeItemTag(final String name) 
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(BetterEnd.MOD_ID, name).toString());
	}

	public static boolean validGenBlock(BlockState block) 
	{
		return block.isIn(END_GROUND) || block.isIn(GEN_TERRAIN);
	}
}
