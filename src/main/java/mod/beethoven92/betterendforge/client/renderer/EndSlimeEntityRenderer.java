package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;

public class EndSlimeEntityRenderer extends SlimeRenderer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime.png");
	private static final ResourceLocation TEXTURE_MOSSY = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime_mossy.png");
	private static final RenderType GLOW = RenderType.getEyes(new ResourceLocation(BetterEnd.MOD_ID, ("textures/entity/end_slime_glow.png")));
	
    public EndSlimeEntityRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.addLayer(new AbstractEyesLayer<SlimeEntity, SlimeModel<SlimeEntity>>(this) {
			@Override
			public RenderType getRenderType() {
				return GLOW;
			}
        });
    }
 
    @Override
    public ResourceLocation getEntityTexture(SlimeEntity entity) {
        return ((EndSlimeEntity) entity).isMossy() ? TEXTURE_MOSSY : TEXTURE;
    }
}