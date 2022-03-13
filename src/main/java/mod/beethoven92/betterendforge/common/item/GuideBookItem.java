package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import vazkii.patchouli.api.PatchouliAPI;

import net.minecraft.item.Item.Properties;

public class GuideBookItem extends Item
{
	private final static ResourceLocation BOOK_ID = new ResourceLocation(BetterEnd.MOD_ID, "guidebook");
	
	public GuideBookItem(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
    	if (!worldIn.isClientSide && playerIn instanceof ServerPlayerEntity)
    	{
    		if (ModList.get().isLoaded("patchouli"))
    		{
    			PatchouliAPI.get().openBookGUI((ServerPlayerEntity) playerIn, BOOK_ID);
    			return ActionResult.success(playerIn.getItemInHand(handIn));
    		}
    		else
    		{
    			playerIn.displayClientMessage(new TranslationTextComponent("message.betterendforge.patchouli_missing"), true);
    			return ActionResult.fail(playerIn.getItemInHand(handIn));
    		}
        }
        return ActionResult.consume(playerIn.getItemInHand(handIn));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new TranslationTextComponent(String.format("%s.%s", "book.betterendforge", "subtitle")).
				withStyle(TextFormatting.DARK_PURPLE, TextFormatting.ITALIC));
	}
}
