package mod.beethoven92.betterendforge.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;

import mod.beethoven92.betterendforge.common.interfaces.ExtendedRepairContainer;
import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.client.gui.screen.inventory.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin extends AbstractRepairScreen<RepairContainer>
{
	@Shadow
	private TextFieldWidget name;
	
	private final List<AbstractButton> be_buttons = Lists.newArrayList();
	private ExtendedRepairContainer be_anvilHandler;
	
	public AnvilScreenMixin(RepairContainer container, PlayerInventory playerInventory, ITextComponent title,
			ResourceLocation guiTexture) 
	{
		super(container, playerInventory, title, guiTexture);
	}

	@Inject(method = "subInit", at = @At("TAIL"))
	protected void be_setup(CallbackInfo info) 
	{
		this.be_buttons.clear();
		int x = (width - imageWidth) / 2;
	    int y = (height - imageHeight) / 2;
	    this.be_anvilHandler = (ExtendedRepairContainer) this.menu;
	    this.be_buttons.add(new Button(x + 8, y + 45, 15, 20, new StringTextComponent("<"), (b) -> be_previousRecipe()));
		this.be_buttons.add(new Button(x + 154, y + 45, 15, 20, new StringTextComponent(">"), (b) -> be_nextRecipe()));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) 
	{
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		this.be_buttons.forEach(button -> {
			button.render(matrixStack, mouseX, mouseY, partialTicks);
		});
	}
	
	@Inject(method = "slotChanged", at = @At("HEAD"), cancellable = true)
	public void be_slotChanged(Container handler, int slotId, ItemStack stack, CallbackInfo info)
	{
		ExtendedRepairContainer anvilHandler = (ExtendedRepairContainer) handler;
		if (anvilHandler.be_getCurrentRecipe() != null) 
		{
			if (anvilHandler.be_getRecipes().size() > 1) 
			{
				this.be_buttons.forEach(button -> button.visible = true);
			}
			else 
			{
				this.be_buttons.forEach(button -> button.visible = false);
			}
			this.name.setValue("");
			info.cancel();
		} 
		else
		{
			this.be_buttons.forEach(button -> button.visible = false);
		}
	}
	
	private void be_nextRecipe() 
	{
		this.be_anvilHandler.be_nextRecipe();
	}
	
	private void be_previousRecipe() 
	{
		this.be_anvilHandler.be_previousRecipe();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) 
	{
		if (minecraft != null) 
		{
			for (AbstractButton elem : be_buttons) 
			{
				if (elem.visible && elem.mouseClicked(mouseX, mouseY, button))
				{
					if (minecraft.gameMode != null) 
					{
						int i = be_buttons.indexOf(elem);
						this.minecraft.gameMode.handleInventoryButtonClick(menu.containerId, i);
						return true;
					}
				}
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
