package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer.SignModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class EndSignTileEntityRenderer implements BlockEntityRenderer<ESignTileEntity> {
	private static final HashMap<Block, RenderType> LAYERS = Maps.newHashMap();
	private static RenderType defaultLayer;
	private final SignModel model = new SignRenderer.SignModel();

	public EndSignTileEntityRenderer(BlockEntityRendererProvider.Context context) {
		super();
		//model = new SignModel(context.bakeLayer(ModelLayers.createSignModelName()))
	}

	public void render(ESignTileEntity signBlockEntity, float tickDelta, PoseStack matrixStack,
			MultiBufferSource provider, int light, int overlay) {
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
		VertexConsumer vertexConsumer = getConsumer(provider, state.getBlock());
		model.root.render(matrixStack, vertexConsumer, light, overlay);
		model.stick.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.popPose();
		Font textRenderer = renderer.getFont();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int m = signBlockEntity.getTextColor().getTextColor();
		int n = (int) (NativeImage.getR(m) * 0.4D);
		int o = (int) (NativeImage.getG(m) * 0.4D);
		int p = (int) (NativeImage.getB(m) * 0.4D);
		int q = NativeImage.combine(0, p, o, n);

		for (int s = 0; s < 4; ++s) {
			FormattedCharSequence orderedText = signBlockEntity.getTextBeingEditedOnRow(s, (text) -> {
				List<FormattedCharSequence> list = textRenderer.split(text, 90);
				return list.isEmpty() ? FormattedCharSequence.EMPTY : (FormattedCharSequence) list.get(0);
			});
			if (orderedText != null) {
				float t = (float) (-textRenderer.width(orderedText) / 2);
				textRenderer.drawInBatch((FormattedCharSequence) orderedText, t, (float) (s * 10 - 20), q, false,
						matrixStack.last().pose(), provider, false, 0, light);
			}
		}

		matrixStack.popPose();
	}

	public static Material getModelTexture(Block block) {
		WoodType signType2;
		if (block instanceof SignBlock) {
			signType2 = ((SignBlock) block).type();
		} else {
			signType2 = WoodType.OAK;
		}

		return Sheets.signTexture(signType2);
	}

	public static VertexConsumer getConsumer(MultiBufferSource provider, Block block) {
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
