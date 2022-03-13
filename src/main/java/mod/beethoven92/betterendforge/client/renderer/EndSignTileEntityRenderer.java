package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer.SignModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class EndSignTileEntityRenderer extends TileEntityRenderer<ESignTileEntity> {
	private static final HashMap<Block, RenderType> LAYERS = Maps.newHashMap();
	private static RenderType defaultLayer;
	private final SignModel model = new SignTileEntityRenderer.SignModel();

	public EndSignTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	public void render(ESignTileEntity signBlockEntity, float tickDelta, MatrixStack matrixStack,
			IRenderTypeBuffer provider, int light, int overlay) {
		BlockState state = signBlockEntity.getBlockState();
		matrixStack.pushPose();

		matrixStack.translate(0.5D, 0.5D, 0.5D);
		float angle = -((float) ((Integer) state.getValue(StandingSignBlock.ROTATION) * 360) / 16.0F);

		BlockState blockState = signBlockEntity.getBlockState();
		if (blockState.getValue(EndSignBlock.FLOOR)) {
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(angle));
			this.model.stick.visible = true;
		} else {
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(angle + 180));
			matrixStack.translate(0.0D, -0.3125D, -0.4375D);
			this.model.stick.visible = false;
		}

		matrixStack.pushPose();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		IVertexBuilder vertexConsumer = getConsumer(provider, state.getBlock());
		model.sign.render(matrixStack, vertexConsumer, light, overlay);
		model.stick.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.popPose();
		FontRenderer textRenderer = renderer.getFont();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int m = signBlockEntity.getTextColor().getTextColor();
		int n = (int) (NativeImage.getR(m) * 0.4D);
		int o = (int) (NativeImage.getG(m) * 0.4D);
		int p = (int) (NativeImage.getB(m) * 0.4D);
		int q = NativeImage.combine(0, p, o, n);

		for (int s = 0; s < 4; ++s) {
			IReorderingProcessor orderedText = signBlockEntity.getTextBeingEditedOnRow(s, (text) -> {
				List<IReorderingProcessor> list = textRenderer.split(text, 90);
				return list.isEmpty() ? IReorderingProcessor.EMPTY : (IReorderingProcessor) list.get(0);
			});
			if (orderedText != null) {
				float t = (float) (-textRenderer.width(orderedText) / 2);
				textRenderer.drawInBatch((IReorderingProcessor) orderedText, t, (float) (s * 10 - 20), q, false,
						matrixStack.last().pose(), provider, false, 0, light);
			}
		}

		matrixStack.popPose();
	}

	public static RenderMaterial getModelTexture(Block block) {
		WoodType signType2;
		if (block instanceof AbstractSignBlock) {
			signType2 = ((AbstractSignBlock) block).type();
		} else {
			signType2 = WoodType.OAK;
		}

		return Atlases.signTexture(signType2);
	}

	public static IVertexBuilder getConsumer(IRenderTypeBuffer provider, Block block) {
		return provider.getBuffer(LAYERS.getOrDefault(block, defaultLayer));
	}

	static {
		defaultLayer = RenderType.entitySolid(new ResourceLocation("textures/entity/sign/oak.png"));

		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) {
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof EndSignBlock) {
					String name = block.getRegistryName().getPath();
					RenderType layer = RenderType.entitySolid(
							new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/sign/" + name + ".png"));
					LAYERS.put(block, layer);
				}
			}
		});
	}

}
