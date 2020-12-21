package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.recipes.AnvilSmithingRecipe;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

@Mixin(RepairContainer.class)
public abstract class RepairContainerMixin extends AbstractRepairContainer
{
	private final World world = this.field_234645_f_.world;
	private final RecipeManager recipeManager = this.world.getRecipeManager();
	private AnvilSmithingRecipe currentRecipe;
	
	public RepairContainerMixin(ContainerType<?> p_i231587_1_, int p_i231587_2_, PlayerInventory p_i231587_3_,
			IWorldPosCallable p_i231587_4_) {
		super(p_i231587_1_, p_i231587_2_, p_i231587_3_, p_i231587_4_);
	}
	
	@Shadow
	public abstract void updateRepairOutput();
	
	@Inject(method = "func_230303_b_", at = @At("HEAD"), cancellable = true)
	protected void func_230303_b_(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> info) 
	{
		if (currentRecipe != null) 
		{
			ItemStack output = this.currentRecipe.craft(this.field_234643_d_, player);
			if (!output.isEmpty()) 
			{
				info.setReturnValue(true);
				info.cancel();
			}
		}
	}
	
	@Inject(method = "func_230301_a_", at = @At("HEAD"), cancellable = true)
	protected void func_230301_a_(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<ItemStack> info) 
	{
		if (currentRecipe != null)
		{
			this.field_234643_d_.getStackInSlot(1).shrink(1);
			this.updateRepairOutput();
			this.field_234644_e_.consume((world, blockPos) -> {
				BlockState anvilState = world.getBlockState(blockPos);
				if (!player.abilities.isCreativeMode && anvilState.isIn(BlockTags.ANVIL) && player.getRNG().nextFloat() < 0.12F)
				{
					BlockState landingState = AnvilBlock.damage(anvilState);
					if (landingState == null) 
					{
						world.removeBlock(blockPos, false);
						world.playEvent(1029, blockPos, 0);
					} 
					else 
					{
						world.setBlockState(blockPos, landingState, 2);
						world.playEvent(1030, blockPos, 0);
					}
				} 
				else 
				{
					world.playEvent(1030, blockPos, 0);
				}

			});
			info.setReturnValue(stack);
			info.cancel();
		}
	}
	
	@Inject(method = "updateRepairOutput", at = @At("HEAD"), cancellable = true)
	public void updateRepairOutput(CallbackInfo info) 
	{
		this.currentRecipe = this.recipeManager.getRecipe(AnvilSmithingRecipe.TYPE, this.field_234643_d_, world).orElse(null);
		if (currentRecipe != null) 
		{
			this.field_234642_c_.setInventorySlotContents(0, currentRecipe.getCraftingResult(this.field_234643_d_));
			this.detectAndSendChanges();
			info.cancel();
		}
	}
	
	@Inject(method = "updateItemName", at = @At("HEAD"), cancellable = true)
	public void updateItemName(String string, CallbackInfo info)
	{
		if (currentRecipe != null) 
		{
			info.cancel();
		}
	}
}
