package mod.beethoven92.betterendforge.client.model;

import java.util.Collections;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CrystaliteBootsModel extends BipedModel<LivingEntity> {

	public ModelRenderer leftBoot;
	public ModelRenderer rightBoot;
	
	public CrystaliteBootsModel(float scale) {
		super(RenderType::getEntityTranslucent, scale, 0.0F, 64, 48);
		this.leftBoot = new ModelRenderer(this, 0, 32);
		this.leftBoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.25F);
		this.leftBoot.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightBoot = new ModelRenderer(this, 0, 16);
		this.rightBoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.25F);
		this.rightBoot.setRotationPoint(-1.9F, 12.0F, 0.0F);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		this.leftBoot.copyModelAngles(bipedLeftLeg);
		this.rightBoot.copyModelAngles(bipedRightLeg);
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return Lists.newArrayList(leftBoot, rightBoot);
	}
}
