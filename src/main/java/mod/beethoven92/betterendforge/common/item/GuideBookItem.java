package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import vazkii.patchouli.api.PatchouliAPI;

import net.minecraft.world.item.Item.Properties;

public class GuideBookItem extends Item
{
	private final static ResourceLocation BOOK_ID = new ResourceLocation(BetterEnd.MOD_ID, "guidebook");
	
	public GuideBookItem(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) 
	{
    	if (!worldIn.isClientSide && playerIn instanceof ServerPlayer)
    	{
    		if (ModList.get().isLoaded("patchouli"))
    		{
    			PatchouliAPI.get().openBookGUI((ServerPlayer) playerIn, BOOK_ID);
    			return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    		}
    		else
    		{
    			playerIn.displayClientMessage(new TranslatableComponent("message.betterendforge.patchouli_missing"), true);
    			return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
    		}
        }
        return InteractionResultHolder.consume(playerIn.getItemInHand(handIn));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) 
	{
		tooltip.add(new TranslatableComponent(String.format("%s.%s", "book.betterendforge", "subtitle")).
				withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
	}
}
