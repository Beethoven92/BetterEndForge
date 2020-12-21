package mod.beethoven92.betterendforge.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DragonflyEntityModel extends EntityModel<DragonflyEntity>
{
	private final ModelRenderer model;
	private final ModelRenderer head;
	private final ModelRenderer tail;
	private final ModelRenderer tail_2;
	private final ModelRenderer wing_1;
	private final ModelRenderer wing_2;
	private final ModelRenderer wing_3;
	private final ModelRenderer wing_4;
	private final ModelRenderer legs_1;
	private final ModelRenderer legs_2;
	
	public DragonflyEntityModel()
	{
		super(RenderType::getEntityCutout);
		
		textureWidth = 64;
		textureHeight = 64;

		model = new ModelRenderer(this);
		model.setRotationPoint(2.0F, 21.5F, -4.0F);
		model.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 9.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(-2.0F, -2.0F, 0.0F);
		model.addChild(head);
		//setRotationAngle(head, 0.3491F, 0.0F, 0.0F);
		head.rotateAngleX = 0.3491F;
		head.setTextureOffset(17, 0).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 3.0F, 0.0F);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(-2.0F, -2.0F, 9.0F);
		model.addChild(tail);
		tail.setTextureOffset(26, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, 0.0F);

		tail_2 = new ModelRenderer(this);
		tail_2.setRotationPoint(0.0F, 0.0F, 7.0F);
		tail.addChild(tail_2);
		tail_2.setTextureOffset(36, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 0.0F);

		wing_1 = new ModelRenderer(this);
		wing_1.setRotationPoint(-2.0F, -4.0F, 4.0F);
		model.addChild(wing_1);
		wing_1.setTextureOffset(0, 13).addBox(-15.0F, 0.0F, -3.0F, 15.0F, 0.0F, 4.0F, 0.0F);

		wing_2 = new ModelRenderer(this);
		wing_2.setRotationPoint(-2.0F, -4.0F, 4.0F);
		model.addChild(wing_2);
		wing_2.mirror = true;
		wing_2.setTextureOffset(0, 13).addBox(0.0F, 0.0F, -3.0F, 15.0F, 0.0F, 4.0F, 0.0F);

		wing_3 = new ModelRenderer(this);
		wing_3.setRotationPoint(-2.0F, -4.0F, 8.0F);
		model.addChild(wing_3);
		wing_3.setTextureOffset(4, 17).addBox(-12.0F, 0.0F, -2.5F, 12.0F, 0.0F, 3.0F, 0.0F);

		wing_4 = new ModelRenderer(this);
		wing_4.setRotationPoint(-2.0F, -4.0F, 8.0F);
		model.addChild(wing_4);
		wing_4.mirror = true;
		wing_4.setTextureOffset(4, 17).addBox(0.0F, 0.0F, -2.5F, 12.0F, 0.0F, 3.0F, 0.0F);

		legs_1 = new ModelRenderer(this);
		legs_1.setRotationPoint(-1.0F, 0.0F, 1.0F);
		model.addChild(legs_1);
		//setRotationAngle(legs_1, 0.0F, 0.0F, -0.5236F);
		legs_1.rotateAngleZ = -0.5236F;
		legs_1.setTextureOffset(50, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F);

		legs_2 = new ModelRenderer(this);
		legs_2.setRotationPoint(-3.0F, 0.0F, 1.0F);
		model.addChild(legs_2);
		//setRotationAngle(legs_2, 0.0F, 0.0F, 0.5236F);
		legs_2.rotateAngleZ = 0.5236F;
		legs_2.setTextureOffset(50, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F);
	}
	
	@Override
	public void setRotationAngles(DragonflyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) 
	{
		float progress = ageInTicks * 2F;
		
		wing_1.rotateAngleZ = 0.3491F + (float) Math.sin(progress) * 0.3491F;
		wing_2.rotateAngleZ = -wing_1.rotateAngleZ;
		
		wing_3.rotateAngleZ = 0.3491F + (float) Math.cos(progress) * 0.3491F;
		wing_4.rotateAngleZ = -wing_3.rotateAngleZ;
		
		progress = ageInTicks * 0.05F;
		
		head.rotateAngleX = 0.3491F + (float) Math.sin(progress * 0.7F) * 0.1F;
		tail.rotateAngleX = (float) Math.cos(progress) * 0.05F - 0.05F;
		tail_2.rotateAngleX = -tail.rotateAngleX * 1.5F;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) 
	{
		model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}

}
