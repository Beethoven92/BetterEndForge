package mod.beethoven92.betterendforge.client.renderer.layer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EyesLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M>
{
	public EyesLayer(RenderLayerParent<T, M> p_i226039_1_) 
	{
		super(p_i226039_1_);
	}

	@Override
	public RenderType renderType() 
	{
		return null;
	}

}
