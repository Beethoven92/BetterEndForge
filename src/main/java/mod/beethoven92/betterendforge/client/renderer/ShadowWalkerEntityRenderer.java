package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class ShadowWalkerEntityRenderer extends BipedRenderer<ShadowWalkerEntity, BipedModel<ShadowWalkerEntity>>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/shadow_walker.png");
	
	public ShadowWalkerEntityRenderer(EntityRendererManager rendererManager) 
	{
		super(rendererManager, new PlayerModel<ShadowWalkerEntity>(0.0F, false), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(ShadowWalkerEntity entity) 
	{
		return TEXTURE;
	}
}
