package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;

public class ChestItemTileEntityRenderer extends BlockEntityWithoutLevelRenderer {
	private EChestTileEntity chest;

	@Override
	public void renderByItem(ItemStack stack, TransformType p_239207_2_, PoseStack matrixStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		if (chest == null)
			chest = new EChestTileEntity();
		chest.setChest(Block.byItem(stack.getItem()));
		BlockEntityRenderDispatcher.instance.renderItem(chest, matrixStack, buffer, combinedLight, combinedOverlay);
	}

}
