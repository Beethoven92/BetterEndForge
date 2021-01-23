package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.SilkMothEntityModel;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SilkMothEntityRenderer extends MobRenderer<SilkMothEntity, SilkMothEntityModel> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/silk_moth.png");
	
    public SilkMothEntityRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher, new SilkMothEntityModel(), 0.5f);
    }
 
    @Override
    public ResourceLocation getEntityTexture(SilkMothEntity entity) {
        return TEXTURE;
    }
}