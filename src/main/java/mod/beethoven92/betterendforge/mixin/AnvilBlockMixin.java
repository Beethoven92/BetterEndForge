package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin 
{
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private static void be_damage(BlockState fallingState, CallbackInfoReturnable<BlockState> info) 
	{
		if (fallingState.getBlock() instanceof EndAnvilBlock) 
		{
			IntegerProperty destructionProperty = ((EndAnvilBlock) fallingState.getBlock()).getDestructionProperty();
			int destruction = fallingState.getValue(destructionProperty);
			try 
			{
				BlockState state = fallingState.setValue(destructionProperty, destruction + 1);
				info.setReturnValue(state);
				info.cancel();
			} 
			catch (Exception ex) 
			{
				info.setReturnValue(null);
				info.cancel();
			}
		}
	}
}
