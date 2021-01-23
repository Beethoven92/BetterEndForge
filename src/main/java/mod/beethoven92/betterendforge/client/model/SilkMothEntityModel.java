package mod.beethoven92.betterendforge.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class SilkMothEntityModel extends BlockBenchModel<SilkMothEntity> {
	private final ModelRenderer legsL;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer legsR;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer head_pivot;
	private final ModelRenderer tendril_r_r1;
	private final ModelRenderer tendril_r_r2;
	private final ModelRenderer bb_main;
	private final ModelRenderer wingR_r1;
	private final ModelRenderer wingL_r1;
	private final ModelRenderer abdomen_r1;

	public SilkMothEntityModel() {
		super(RenderType::getEntityCutout);
		
		textureWidth = 64;
		textureHeight = 64;

		legsL = new ModelRenderer(this);
		legsL.setRotationPoint(1.5F, 19.9F, -0.45F);
		setRotationAngle(legsL, 0.0F, 0.0F, 0.6981F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, -1.0F);
		legsL.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.2182F, 0.3927F);
		cube_r1.setTextureOffset(0, 13).addBox(0.0216F, 0.0F, -0.5976F, 3.0F, 0.0F, 1.0F, 0.0F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.5F, 0.1F, -0.05F);
		legsL.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.3927F);
		cube_r2.setTextureOffset(0, 15).addBox(0.0F, 0.0F, -0.6F, 3.0F, 0.0F, 1.0F, 0.0F);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 0.0F, 0.9F);
		legsL.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -0.2182F, 0.3927F);
		cube_r3.setTextureOffset(0, 14).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, 0.0F);

		legsR = new ModelRenderer(this);
		legsR.setRotationPoint(-1.5F, 19.9F, -0.55F);
		setRotationAngle(legsR, 0.0F, 3.1416F, -0.6545F);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 0.0F, -1.0F);
		legsR.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.2182F, 0.3927F);
		cube_r4.setTextureOffset(0, 10).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, 0.0F);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.5F, 0.1F, -0.05F);
		legsR.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, 0.3927F);
		cube_r5.setTextureOffset(0, 11).addBox(0.0F, 0.0F, -0.4F, 3.0F, 0.0F, 1.0F, 0.0F);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 0.0F, 0.9F);
		legsR.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, -0.2182F, 0.3927F);
		cube_r6.setTextureOffset(0, 12).addBox(0.0216F, 0.0F, -0.4024F, 3.0F, 0.0F, 1.0F, 0.0F);

		head_pivot = new ModelRenderer(this);
		head_pivot.setRotationPoint(0.0F, 18.0F, -3.0F);
		head_pivot.setTextureOffset(15, 10).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F);

		tendril_r_r1 = new ModelRenderer(this);
		tendril_r_r1.setRotationPoint(1.0F, -1.15F, -1.0F);
		head_pivot.addChild(tendril_r_r1);
		setRotationAngle(tendril_r_r1, 0.0F, 0.0F, 0.3927F);
		tendril_r_r1.setTextureOffset(23, 0).addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, true);

		tendril_r_r2 = new ModelRenderer(this);
		tendril_r_r2.setRotationPoint(-1.0F, -1.15F, -1.0F);
		head_pivot.addChild(tendril_r_r2);
		setRotationAngle(tendril_r_r2, 0.0F, 0.0F, -0.3927F);
		tendril_r_r2.setTextureOffset(23, 0).addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(19, 19).addBox(-2.5F, -8.5F, -3.0F, 5.0F, 5.0F, 3.0F, 0.0F);

		wingR_r1 = new ModelRenderer(this);
		wingR_r1.setRotationPoint(-1.5F, -6.5F, 0.5F);
		bb_main.addChild(wingR_r1);
		setRotationAngle(wingR_r1, 0.0F, 0.0F, 0.3927F);
		wingR_r1.setTextureOffset(0, 5).addBox(-7.0F, 0.0F, -3.0F, 9.0F, 0.0F, 5.0F, 0.0F, true);

		wingL_r1 = new ModelRenderer(this);
		wingL_r1.setRotationPoint(1.5F, -6.5F, 0.5F);
		bb_main.addChild(wingL_r1);
		setRotationAngle(wingL_r1, 0.0F, 0.0F, -0.3927F);
		wingL_r1.setTextureOffset(0, 5).addBox(-2.0F, 0.0F, -3.0F, 9.0F, 0.0F, 5.0F, 0.0F);

		abdomen_r1 = new ModelRenderer(this);
		abdomen_r1.setRotationPoint(1.0F, -3.9F, 0.0F);
		bb_main.addChild(abdomen_r1);
		setRotationAngle(abdomen_r1, -0.3927F, 0.0F, 0.0F);
		abdomen_r1.setTextureOffset(0, 10).addBox(-3.0F, -4.0F, -1.0F, 4.0F, 4.0F, 7.0F, 0.0F);
	}


	@Override
	public void setRotationAngles(SilkMothEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		wingR_r1.rotateAngleZ = MathHelper.sin(animationProgress * 2F) * 0.4F + 0.3927F;
		wingL_r1.rotateAngleZ = -wingR_r1.rotateAngleZ;
		head_pivot.rotateAngleX = MathHelper.sin(animationProgress * 0.03F) * 0.1F;
		tendril_r_r1.rotateAngleZ = MathHelper.sin(animationProgress * 0.07F) * 0.2F + 0.3927F;
		tendril_r_r2.rotateAngleZ = -tendril_r_r1.rotateAngleZ;
		abdomen_r1.rotateAngleX = MathHelper.sin(animationProgress * 0.05F) * 0.1F - 0.3927F;
		legsR.rotateAngleZ = MathHelper.sin(animationProgress * 0.07F) * 0.1F - 0.6545F;
		legsL.rotateAngleZ = -legsR.rotateAngleZ;
	}


	@Override
	public void render(MatrixStack matrices, IVertexBuilder vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertices, light, overlay);
		head_pivot.render(matrices, vertices, light, overlay);
		legsL.render(matrices, vertices, light, overlay);
		legsR.render(matrices, vertices, light, overlay);
	}
}
