package mod.beethoven92.betterendforge.common.item;

import java.util.function.Supplier;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public class ModSpawnEggItem extends SpawnEggItem {
	
	private static final DefaultDispenseItemBehavior SPAWN_EGG_BEHAVIOR = new DefaultDispenseItemBehavior() {
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            ((SpawnEggItem)stack.getItem()).getType(stack.getTag()).spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
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
}
