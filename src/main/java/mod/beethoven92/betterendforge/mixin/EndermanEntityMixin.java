package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModEffects;
import mod.beethoven92.betterendforge.common.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin 
{
	@Inject(at = @At("HEAD"), method = "shouldAttackPlayer", cancellable = true)
	private void shouldAttackPlayer(PlayerEntity player, CallbackInfoReturnable<Boolean> info) 
	{
		if (player.isCreative() || player.isPotionActive(ModEffects.END_VEIL.get()) ||
				EnchantmentHelper.getEnchantmentLevel(ModEnchantments.END_VEIL.get(), 
						player.getItemStackFromSlot(EquipmentSlotType.HEAD)) > 0) 
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
}