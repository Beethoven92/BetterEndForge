package mod.beethoven92.betterendforge.client.model;

import java.util.Collections;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CrystaliteLeggingsModel extends BipedModel<LivingEntity> {

	public CrystaliteLeggingsModel(float scale) {
		super(RenderType::getEntityTranslucent, scale, 0.0F, 64, 48);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, scale);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 32);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return Lists.newArrayList(bipedBody, bipedLeftLeg, bipedRightLeg);
	}
}
