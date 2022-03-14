package mod.beethoven92.betterendforge.client.model;

import java.util.Collections;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class CrystaliteBootsModel extends HumanoidModel<LivingEntity> {

	public ModelPart leftBoot;
	public ModelPart rightBoot;
	
	public CrystaliteBootsModel(float scale) {
		super(RenderType::entityTranslucent, scale, 0.0F, 64, 48);
		this.leftBoot = new ModelPart(this, 0, 32);
		this.leftBoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.25F);
		this.leftBoot.setPos(1.9F, 12.0F, 0.0F);
		this.rightBoot = new ModelPart(this, 0, 16);
		this.rightBoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale + 0.25F);
		this.rightBoot.setPos(-1.9F, 12.0F, 0.0F);
	}
	
	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		this.leftBoot.copyFrom(leftLeg);
		this.rightBoot.copyFrom(rightLeg);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelPart> bodyParts() {
		return Lists.newArrayList(leftBoot, rightBoot);
	}
}
