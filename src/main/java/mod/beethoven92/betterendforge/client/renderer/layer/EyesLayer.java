package mod.beethoven92.betterendforge.client.renderer.layer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EyesLayer<T extends Entity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M>
{
	public EyesLayer(IEntityRenderer<T, M> p_i226039_1_) 
	{
		super(p_i226039_1_);
	}

	@Override
	public RenderType getRenderType() 
	{
		return null;
	}

}
