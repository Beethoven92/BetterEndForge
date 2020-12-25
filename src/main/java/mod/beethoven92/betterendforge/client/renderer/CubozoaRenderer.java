package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.CubozoaModel;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class CubozoaRenderer extends MobRenderer<CubozoaEntity, CubozoaModel> {
	private static final ResourceLocation[] TEXTURE = new ResourceLocation[2];
	private static final RenderType[] GLOW = new RenderType[2];

	public CubozoaRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher, new CubozoaModel(), 0.5f);
		this.addLayer(new AbstractEyesLayer<CubozoaEntity, CubozoaModel>(this) {
			@Override
			public RenderType getRenderType() {
				return GLOW[0];
			}

			@Override
			public void render(MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light, CubozoaEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
				IVertexBuilder ivertexbuilder = vertexConsumers.getBuffer(GLOW[entity.getVariant()]);
				this.getEntityModel().render(matrices, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
        });
	}

	@Override
	protected void preRenderCallback(CubozoaEntity entity, MatrixStack matrixStack, float f) {
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
	

	@Override
	public ResourceLocation getEntityTexture(CubozoaEntity entity) {
		return TEXTURE[entity.getVariant()];
	}
	
	static {
		TEXTURE[0] = new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa.png"));
		TEXTURE[1] = new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa_sulphur.png"));
		
		GLOW[0] = RenderType.getEyes(new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa_glow.png")));
		GLOW[1] = RenderType.getEyes(new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/cubozoa/cubozoa_sulphur_glow.png")));
	}
}