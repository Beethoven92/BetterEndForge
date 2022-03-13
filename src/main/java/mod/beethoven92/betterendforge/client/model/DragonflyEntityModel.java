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
		super(RenderType::entityCutout);
		
		texWidth = 64;
		texHeight = 64;

		model = new ModelRenderer(this);
		model.setPos(2.0F, 21.5F, -4.0F);
		model.texOffs(0, 0).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 9.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setPos(-2.0F, -2.0F, 0.0F);
		model.addChild(head);
		//setRotationAngle(head, 0.3491F, 0.0F, 0.0F);
		head.xRot = 0.3491F;
		head.texOffs(17, 0).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 3.0F, 0.0F);

		tail = new ModelRenderer(this);
		tail.setPos(-2.0F, -2.0F, 9.0F);
		model.addChild(tail);
		tail.texOffs(26, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, 0.0F);

		tail_2 = new ModelRenderer(this);
		tail_2.setPos(0.0F, 0.0F, 7.0F);
		tail.addChild(tail_2);
		tail_2.texOffs(36, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 0.0F);

		wing_1 = new ModelRenderer(this);
		wing_1.setPos(-2.0F, -4.0F, 4.0F);
		model.addChild(wing_1);
		wing_1.texOffs(0, 13).addBox(-15.0F, 0.0F, -3.0F, 15.0F, 0.0F, 4.0F, 0.0F);

		wing_2 = new ModelRenderer(this);
		wing_2.setPos(-2.0F, -4.0F, 4.0F);
		model.addChild(wing_2);
		wing_2.mirror = true;
		wing_2.texOffs(0, 13).addBox(0.0F, 0.0F, -3.0F, 15.0F, 0.0F, 4.0F, 0.0F);

		wing_3 = new ModelRenderer(this);
		wing_3.setPos(-2.0F, -4.0F, 8.0F);
		model.addChild(wing_3);
		wing_3.texOffs(4, 17).addBox(-12.0F, 0.0F, -2.5F, 12.0F, 0.0F, 3.0F, 0.0F);

		wing_4 = new ModelRenderer(this);
		wing_4.setPos(-2.0F, -4.0F, 8.0F);
		model.addChild(wing_4);
		wing_4.mirror = true;
		wing_4.texOffs(4, 17).addBox(0.0F, 0.0F, -2.5F, 12.0F, 0.0F, 3.0F, 0.0F);

		legs_1 = new ModelRenderer(this);
		legs_1.setPos(-1.0F, 0.0F, 1.0F);
		model.addChild(legs_1);
		//setRotationAngle(legs_1, 0.0F, 0.0F, -0.5236F);
		legs_1.zRot = -0.5236F;
		legs_1.texOffs(50, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F);

		legs_2 = new ModelRenderer(this);
		legs_2.setPos(-3.0F, 0.0F, 1.0F);
		model.addChild(legs_2);
		//setRotationAngle(legs_2, 0.0F, 0.0F, 0.5236F);
		legs_2.zRot = 0.5236F;
		legs_2.texOffs(50, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F);
	}
	
	@Override
	public void setupAnim(DragonflyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) 
	{
		float progress = ageInTicks * 2F;
		
		wing_1.zRot = 0.3491F + (float) Math.sin(progress) * 0.3491F;
		wing_2.zRot = -wing_1.zRot;
		
		wing_3.zRot = 0.3491F + (float) Math.cos(progress) * 0.3491F;
		wing_4.zRot = -wing_3.zRot;
		
		progress = ageInTicks * 0.05F;
		
		head.xRot = 0.3491F + (float) Math.sin(progress * 0.7F) * 0.1F;
		tail.xRot = (float) Math.cos(progress) * 0.05F - 0.05F;
		tail_2.xRot = -tail.xRot * 1.5F;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) 
	{
		model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}

}
