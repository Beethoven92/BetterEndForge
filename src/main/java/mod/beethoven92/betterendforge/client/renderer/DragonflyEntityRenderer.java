package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.DragonflyEntityModel;
import mod.beethoven92.betterendforge.client.renderer.layer.EyesLayer;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DragonflyEntityRenderer extends MobRenderer<DragonflyEntity, DragonflyEntityModel>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, 
			"textures/entity/dragonfly.png");
	private static final RenderType GLOW = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, 
			"textures/entity/dragonfly_glow.png"));
	
	public DragonflyEntityRenderer(EntityRenderDispatcher renderManagerIn) 
	{
		super(renderManagerIn, new DragonflyEntityModel(), 0.5F);
		
		this.addLayer(new EyesLayer<DragonflyEntity, DragonflyEntityModel>(this) 
	    {
			@Override
			public RenderType renderType() 
			{
				return GLOW;
			}
	    });
	}

	@Override
	public ResourceLocation getTextureLocation(DragonflyEntity entity) 
	{
		return TEXTURE;
	}
}
