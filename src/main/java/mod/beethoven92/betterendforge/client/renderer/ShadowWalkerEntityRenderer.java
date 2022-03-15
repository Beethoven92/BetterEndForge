package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.resources.ResourceLocation;

public class ShadowWalkerEntityRenderer extends HumanoidMobRenderer<ShadowWalkerEntity, HumanoidModel<ShadowWalkerEntity>>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/shadow_walker.png");
	
	public ShadowWalkerEntityRenderer(EntityRendererProvider.Context context)
	{
		super(context, new PlayerModel<ShadowWalkerEntity>(0.0F, false), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(ShadowWalkerEntity entity) 
	{
		return TEXTURE;
	}
}
