package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.SilkMothEntityModel;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SilkMothEntityRenderer extends MobRenderer<SilkMothEntity, SilkMothEntityModel> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/silk_moth.png");
	
    public SilkMothEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SilkMothEntityModel(), 0.5f);
    }
 
    @Override
    public ResourceLocation getTextureLocation(SilkMothEntity entity) {
        return TEXTURE;
    }
}