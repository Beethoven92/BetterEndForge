package mod.beethoven92.betterendforge.common.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = Bus.MOD)
public class ModSpawnEggItem extends SpawnEggItem {

	private static final Logger LOGGER = LogManager.getLogger();

	private static final Map<Supplier<EntityType<?>>, SpawnEggItem> MOD_EGGS = new HashMap<>();

	private static final DefaultDispenseItemBehavior SPAWN_EGG_BEHAVIOR = new DefaultDispenseItemBehavior() {
		public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
			Direction direction = source.getBlockState().get(DispenserBlock.FACING);
			((SpawnEggItem) stack.getItem()).getType(stack.getTag()).spawn(source.getWorld(), stack, null,
					source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
			stack.shrink(1);
			return stack;
		}
	};

	private Supplier<EntityType<?>> type;

	public ModSpawnEggItem(Supplier<EntityType<?>> typeIn, int primaryColorIn, int secondaryColorIn,
			Properties builder) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.type = typeIn;
		DispenserBlock.registerDispenseBehavior(this, SPAWN_EGG_BEHAVIOR);
		MOD_EGGS.put(typeIn, this);
	}

	@Override
	public EntityType<?> getType(CompoundNBT nbt) {
		if (nbt != null && nbt.contains("EntityTag", 10)) {
			CompoundNBT compoundnbt = nbt.getCompound("EntityTag");
			if (compoundnbt.contains("id", 8)) {
				return EntityType.byKey(compoundnbt.getString("id")).orElse(type.get());
			}
		}
		return type.get();
	}

	// Use reflection to add the mod spawn eggs to the EGGS map in SpawnEggItem
	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			try {
			Map<EntityType<?>, SpawnEggItem> eggs = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,
					null, "field_195987_b");
			for (Entry<Supplier<EntityType<?>>, SpawnEggItem> entry : MOD_EGGS.entrySet())
				eggs.put(entry.getKey().get(), entry.getValue());
			} catch (Exception e) {
				LOGGER.warn("Unable to access SpawnEggItem.EGGS");
			} 
		});
	}
}
