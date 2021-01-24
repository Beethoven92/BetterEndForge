package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.EndFishEntityModel;
import mod.beethoven92.betterendforge.client.renderer.layer.EyesLayer;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndFishEntityRenderer extends MobRenderer<EndFishEntity, EndFishEntityModel> 
{
	private static final ResourceLocation[] TEXTURE = new ResourceLocation[EndFishEntity.VARIANTS];
	private static final RenderType[] GLOW = new RenderType[EndFishEntity.VARIANTS];
	
	public EndFishEntityRenderer(EntityRendererManager renderManagerIn) 
	{
		super(renderManagerIn, new EndFishEntityModel(), 0.5f);
	    
		this.addLayer(new EyesLayer<EndFishEntity, EndFishEntityModel>(this) 
	    {
			@Override
			public RenderType getRenderType() 
			{
				return GLOW[0];
			}

			@Override
			public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
					EndFishEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks,
					float ageInTicks, float netHeadYaw, float headPitch) 
			{
				IVertexBuilder iVertexBuilder = bufferIn.getBuffer(GLOW[entityIn.getVariant()]);

				this.getEntityModel().render(matrixStackIn, iVertexBuilder, packedLightIn, 
						OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
	    });
	}

	@Override
	protected void preRenderCallback(EndFishEntity entity, MatrixStack matrixStack, float f) 
	{
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EndFishEntity entity) 
	{
		return TEXTURE[entity.getVariant()];
	}
    
	static 
	{
    	for (int i = 0; i < EndFishEntity.VARIANTS; i++) 
    	{
    		TEXTURE[i] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_fish/end_fish_" + i + ".png");
    		GLOW[i] = RenderType.getEyes(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_fish/end_fish_" + i + "_glow.png"));
    	}
    }
}
