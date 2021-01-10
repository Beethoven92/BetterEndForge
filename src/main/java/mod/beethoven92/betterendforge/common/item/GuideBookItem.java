package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class GuideBookItem extends Item
{
	public final static ResourceLocation BOOK_ID = new ResourceLocation(BetterEnd.MOD_ID, "guidebook");
	
	public GuideBookItem(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new TranslationTextComponent(String.format("%s.%s", "book.betterend", "subtitle")).
				mergeStyle(TextFormatting.DARK_PURPLE, TextFormatting.ITALIC));
	}
}
