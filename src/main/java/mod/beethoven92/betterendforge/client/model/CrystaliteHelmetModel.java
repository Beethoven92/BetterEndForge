package mod.beethoven92.betterendforge.client.model;

import java.util.Collections;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CrystaliteHelmetModel extends BipedModel<LivingEntity> {

	public CrystaliteHelmetModel(float scale) {
		super(RenderType::getEntityTranslucent, scale, 0.0F, 64, 48);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, scale + 0.5F);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return Lists.newArrayList(bipedHead);
	}
}
