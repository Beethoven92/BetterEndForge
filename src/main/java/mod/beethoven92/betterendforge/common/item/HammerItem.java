package mod.beethoven92.betterendforge.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import net.minecraft.item.Item.Properties;

public class HammerItem extends ToolItem
{
	public final static UUID ATTACK_KNOCKBACK_MODIFIER = MathHelper.createInsecureUUID(ThreadLocalRandom.current());
	
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	
	public HammerItem(IItemTier tier, float attackDamage, float attackSpeed, double knockback, Properties builderIn) 
	{
		super(attackDamage, attackSpeed, tier, Sets.newHashSet(), builderIn.addToolType(ToolType.get("hammer"), tier.getLevel()));
		
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", knockback, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) 
	{
		return state.getMaterial().equals(Material.STONE) ||
				   state.getMaterial().equals(Material.GLASS) ||
				   state.is(Blocks.DIAMOND_BLOCK) ||
				   state.is(Blocks.EMERALD_BLOCK) ||
				   state.is(Blocks.LAPIS_BLOCK) ||
				   state.is(Blocks.REDSTONE_BLOCK);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) 
	{
		stack.hurtAndBreak(1, attacker, ((entity) -> {
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		}));
		
		return true;
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) 
	{
		if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) 
		{
			stack.hurtAndBreak(1, entityLiving, ((entity) -> {
				entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			}));
		}

		return true;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) 
	{
		if (state.getMaterial().equals(Material.GLASS)) 
		{
			return this.getTier().getSpeed() * 2.0F;
		}
		if (this.isCorrectToolForDrops(state))
		{
			float mult = 1.0F;
			if (state.is(Blocks.DIAMOND_BLOCK) || state.is(Blocks.EMERALD_BLOCK) || state.is(Blocks.LAPIS_BLOCK) 
					|| state.is(Blocks.REDSTONE_BLOCK)) 
			{
				mult = this.getTier().getSpeed();
			} 
			else 
			{
				mult = this.getTier().getSpeed() / 2.0F;
			}
			return mult > 1.0F ? mult : 1.0F;
		}
		return 1.0F;
	}
	
	@Override
	public boolean isCorrectToolForDrops(BlockState state) 
	{
		if (state.getMaterial().equals(Material.GLASS)) 
		{
			return true;
		}
		if (!state.is(Blocks.REDSTONE_BLOCK) && !state.is(Blocks.DIAMOND_BLOCK) && !state.is(Blocks.EMERALD_BLOCK) && !state.is(Blocks.LAPIS_BLOCK) && !state.getMaterial().equals(Material.STONE)) 
		{
			return false;
		}
		int level = this.getTier().getLevel();
		if (state.is(Blocks.IRON_ORE) || state.is(Blocks.LAPIS_BLOCK) || state.is(Blocks.LAPIS_ORE)) 
		{
			return level >= 1;
		}
		if (state.is(Blocks.DIAMOND_BLOCK) && !state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.EMERALD_ORE) || state.is(Blocks.EMERALD_BLOCK) || state.is(Blocks.GOLD_ORE) || state.is(Blocks.REDSTONE_ORE))
		{
			return level >= 2;
		}
		if (state.is(Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN) || state.is(Blocks.RESPAWN_ANCHOR) || state.is(Blocks.ANCIENT_DEBRIS)) 
		{
			return level >= 3;
		}
		return true;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) 
	{
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
	}
}
