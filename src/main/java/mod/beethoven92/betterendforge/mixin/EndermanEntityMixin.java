package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModEffects;
import mod.beethoven92.betterendforge.common.init.ModEnchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;

@Mixin(EnderMan.class)
public abstract class EndermanEntityMixin 
{
	@Inject(at = @At("HEAD"), method = "isLookingAtMe", cancellable = true)
	private void be_isLookingAtMe(Player player, CallbackInfoReturnable<Boolean> info)
	{
		if (player.isCreative() || player.hasEffect(ModEffects.END_VEIL.get()) ||
				EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.END_VEIL.get(), 
						player.getItemBySlot(EquipmentSlot.HEAD)) > 0) 
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
}