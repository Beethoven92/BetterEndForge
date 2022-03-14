package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.EndFishEntityModel;
import mod.beethoven92.betterendforge.client.renderer.layer.EyesLayer;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndFishEntityRenderer extends MobRenderer<EndFishEntity, EndFishEntityModel> 
{
	private static final ResourceLocation[] TEXTURE = new ResourceLocation[EndFishEntity.VARIANTS];
	private static final RenderType[] GLOW = new RenderType[EndFishEntity.VARIANTS];
	
	public EndFishEntityRenderer(EntityRenderDispatcher renderManagerIn) 
	{
		super(renderManagerIn, new EndFishEntityModel(), 0.5f);
	    
		this.addLayer(new EyesLayer<EndFishEntity, EndFishEntityModel>(this) 
	    {
			@Override
			public RenderType renderType() 
			{
				return GLOW[0];
			}

			@Override
			public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
					EndFishEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks,
					float ageInTicks, float netHeadYaw, float headPitch) 
			{
				VertexConsumer iVertexBuilder = bufferIn.getBuffer(GLOW[entityIn.getVariant()]);

				this.getParentModel().renderToBuffer(matrixStackIn, iVertexBuilder, packedLightIn, 
						OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
	    });
	}

	@Override
	protected void scale(EndFishEntity entity, PoseStack matrixStack, float f) 
	{
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EndFishEntity entity) 
	{
		return TEXTURE[entity.getVariant()];
	}
    
	static 
	{
    	for (int i = 0; i < EndFishEntity.VARIANTS; i++) 
    	{
    		TEXTURE[i] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_fish/end_fish_" + i + ".png");
    		GLOW[i] = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_fish/end_fish_" + i + "_glow.png"));
    	}
    }
}
