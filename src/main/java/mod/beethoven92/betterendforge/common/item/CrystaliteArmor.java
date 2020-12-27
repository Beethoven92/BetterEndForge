package mod.beethoven92.betterendforge.common.item;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.CrystaliteBootsModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteChestplateModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteHelmetModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteLeggingsModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrystaliteArmor extends ArmorItem {

	@OnlyIn(Dist.CLIENT)
	CrystaliteBootsModel bootsModel;
	@OnlyIn(Dist.CLIENT)
	CrystaliteChestplateModel slimChestplateModel;
	@OnlyIn(Dist.CLIENT)
	CrystaliteChestplateModel chestplateModel;
	@OnlyIn(Dist.CLIENT)
	CrystaliteHelmetModel helmetModel;
	@OnlyIn(Dist.CLIENT)
	CrystaliteLeggingsModel leggingsModel;

	public CrystaliteArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return BetterEnd.MOD_ID + ":textures/models/armor/crystalite_layer_"
				+ (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
	}

	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
			EquipmentSlotType armorSlot, A _default) {
		if (bootsModel == null) {
			bootsModel = new CrystaliteBootsModel(1);
			chestplateModel = new CrystaliteChestplateModel(1, false);
			slimChestplateModel = new CrystaliteChestplateModel(1, true);
			helmetModel = new CrystaliteHelmetModel(1);
			leggingsModel = new CrystaliteLeggingsModel(1);
		}

		switch (slot) {
		case HEAD: {
			return (A) helmetModel;
		}
		case CHEST: {
			if (entityLiving instanceof AbstractClientPlayerEntity
					&& ((AbstractClientPlayerEntity) entityLiving).getSkinType().equals("slim")) {
				return (A) slimChestplateModel;
			}
			return (A) chestplateModel;
		}
		case LEGS: {
			return (A) leggingsModel;
		}
		case FEET: {
			return (A) bootsModel;
		}
		default: {
			return _default;
		}
		}
	}

}
