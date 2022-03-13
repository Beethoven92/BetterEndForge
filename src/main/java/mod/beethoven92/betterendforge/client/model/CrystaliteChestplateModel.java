package mod.beethoven92.betterendforge.client.model;

import java.util.Collections;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public class CrystaliteChestplateModel extends BipedModel<LivingEntity> {

	public ModelRenderer leftShoulder;
	public ModelRenderer rightShoulder;
	private boolean thinArms;
	
	public CrystaliteChestplateModel(float scale, boolean thinArms) {
		super(RenderType::entityTranslucent, scale, 0.0F, 64, 48);
		this.thinArms = thinArms;
		this.body = new ModelRenderer(this, 16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, scale + 0.25F);
		this.body.setPos(0.0F, 0.0F, 0.0F);
		if (thinArms) {
			this.leftShoulder = new ModelRenderer(this, 41, 32);
			this.leftShoulder.addBox(-1.0F, -2.5F, -2.0F, 3.0F, 12.0F, 4.0F, scale + 0.35F);
			this.leftShoulder.setPos(5.0F, 2.5F, 0.0F);
			this.leftShoulder.mirror = true;
			this.rightShoulder = new ModelRenderer(this, 41, 16);
			this.rightShoulder.addBox(-2.0F, -2.5F, -2.0F, 3.0F, 12.0F, 4.0F, scale + 0.35F);
			this.rightShoulder.setPos(-5.0F, 2.5F, 0.0F);
		} else {
			this.leftShoulder = new ModelRenderer(this, 40, 32);
			this.leftShoulder.addBox(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.45F);
			this.leftShoulder.setPos(5.0F, 2.0F, 0.0F);
			this.leftShoulder.mirror = true;
			this.rightShoulder = new ModelRenderer(this, 40, 16);
			this.rightShoulder.addBox(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.45F);
			this.rightShoulder.setPos(-5.0F, 2.0F, 0.0F);
		}
	}
	
	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		this.leftShoulder.copyFrom(leftArm);
		this.rightShoulder.copyFrom(rightArm);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	@Override
	protected Iterable<ModelRenderer> headParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return Lists.newArrayList(body, leftShoulder, rightShoulder);
	}
	
	@Override
	public void translateToHand(HandSide arm, MatrixStack matrices) {
		ModelRenderer modelPart = this.getArm(arm);
		if (this.thinArms) {
			float f = 0.5F * (float)(arm == HandSide.RIGHT ? 1 : -1);
			modelPart.x += f;
			modelPart.translateAndRotate(matrices);
			modelPart.x -= f;
		} else {
			modelPart.translateAndRotate(matrices);
		}
	}
}
