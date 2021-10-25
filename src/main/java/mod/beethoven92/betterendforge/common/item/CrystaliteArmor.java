package mod.beethoven92.betterendforge.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.CrystaliteBootsModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteChestplateModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteHelmetModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteLeggingsModel;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrystaliteArmor extends ArmorItem {

	private static final UUID[] UUIDS = new UUID[] { UUID.fromString("38c9722b-d905-4b84-940d-551c803100af"),
			UUID.fromString("a7bab7bb-9f3b-4787-9ff7-f138c9c17f6c"),
			UUID.fromString("ccf857e1-86e3-40d5-9f51-fe624d54cc33"),
			UUID.fromString("ed88885e-1882-4989-a4b3-8aa32e2b02d3") };

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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot,
			ItemStack stack) {
		Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers(equipmentSlot);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(modifiers);
		Item item = stack.getItem();
		if (item == ModItems.CRYSTALITE_HELMET.get() && equipmentSlot == EquipmentSlotType.HEAD)
			builder.put(ModAttributes.BLINDNESS_RESISTANCE.get(), new AttributeModifier(UUIDS[equipmentSlot.getIndex()],
					"Helmet blindness resistance", 1, AttributeModifier.Operation.ADDITION));
		else if (item == ModItems.CRYSTALITE_LEGGINGS.get() && equipmentSlot == EquipmentSlotType.LEGS)
			builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUIDS[equipmentSlot.getIndex()],
					"Armor health boost", 4.0, AttributeModifier.Operation.ADDITION));
		return builder.build();
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.ticksExisted % 60 == 0) {
			Item item = stack.getItem();

			if (item == ModItems.CRYSTALITE_BOOTS.get())
				player.addPotionEffect(new EffectInstance(Effects.SPEED, 80, 0, true, false, true));
			else if (item == ModItems.CRYSTALITE_CHESTPLATE.get())
				player.addPotionEffect(new EffectInstance(Effects.HASTE, 80, 0, true, false, true));

			if (hasFullSet(player))
				player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 80, 0, true, false, true));
		}
	}

	private boolean hasFullSet(PlayerEntity player) {
		for (ItemStack armorStack : player.getArmorInventoryList()) {
			if (!(armorStack.getItem() instanceof CrystaliteArmor)) {
				return false;
			}
		}
		return true;
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
