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
		super(RenderType::entityTranslucent, scale, 0.0F, 64, 48);
		this.leftBoot = new ModelRenderer(this, 0, 32);
		this.leftBoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.25F);
		this.leftBoot.setPos(1.9F, 12.0F, 0.0F);
		this.rightBoot = new ModelRenderer(this, 0, 16);
		this.rightBoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.25F);
		this.rightBoot.setPos(-1.9F, 12.0F, 0.0F);
	}
	
	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		this.leftBoot.copyFrom(leftLeg);
		this.rightBoot.copyFrom(rightLeg);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return Lists.newArrayList(leftBoot, rightBoot);
	}
}
