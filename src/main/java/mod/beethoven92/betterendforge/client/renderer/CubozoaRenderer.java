package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.CubozoaModel;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CubozoaRenderer extends MobRenderer<CubozoaEntity, CubozoaModel> {
	private static final ResourceLocation[] TEXTURE = new ResourceLocation[2];
	private static final RenderType[] GLOW = new RenderType[2];

	public CubozoaRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new CubozoaModel(), 0.5f);
		this.addLayer(new EyesLayer<CubozoaEntity, CubozoaModel>(this) {
			@Override
			public RenderType renderType() {
				return GLOW[0];
			}

			@Override
			public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, CubozoaEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
				VertexConsumer ivertexbuilder = vertexConsumers.getBuffer(GLOW[entity.getVariant()]);
				this.getParentModel().renderToBuffer(matrices, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
        });
	}

	@Override
	protected void scale(CubozoaEntity entity, PoseStack matrixStack, float f) {
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
	

	@Override
	public ResourceLocation getTextureLocation(CubozoaEntity entity) {
		return TEXTURE[entity.getVariant()];
	}
	
	static {
		TEXTURE[0] = new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa.png"));
		TEXTURE[1] = new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa_sulphur.png"));
		
		GLOW[0] = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa_glow.png")));
		GLOW[1] = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa_sulphur_glow.png")));
	}
}