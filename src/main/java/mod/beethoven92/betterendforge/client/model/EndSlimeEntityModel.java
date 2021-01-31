package mod.beethoven92.betterendforge.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class EndSlimeEntityModel<T extends EndSlimeEntity> extends SegmentedModel<T> {
	private final ModelRenderer flower;
	private final ModelRenderer crop;
	private final ModelRenderer innerCube;
	private final ModelRenderer rightEye;
	private final ModelRenderer leftEye;
	private final ModelRenderer mouth;
	
	public EndSlimeEntityModel(boolean onlyShell) {
		super(RenderType::getEntityCutout);
		
		this.innerCube = new ModelRenderer(this, 0, 16);
		this.rightEye = new ModelRenderer(this, 32, 0);
		this.leftEye = new ModelRenderer(this, 32, 4);
		this.mouth = new ModelRenderer(this, 32, 8);
		this.flower = new ModelRenderer(this);
		this.crop = new ModelRenderer(this);

		if (onlyShell) {
			this.innerCube.setTextureOffset(0, 0);
			this.innerCube.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		}
		else {
			this.innerCube.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
			this.rightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
			this.leftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
			this.mouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
			
			for (int i = 0; i < 4; i++) {
				ModelRenderer petalRot = new ModelRenderer(this);
				petalRot.rotateAngleY = ModMathHelper.degreesToRadians(i * 45F);
				
				ModelRenderer petal = new ModelRenderer(this, 40, 0);
				petal.setRotationPoint(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F);
				
				this.flower.addChild(petalRot);
				petalRot.addChild(petal);
			}
			
			for (int i = 0; i < 2; i++) {
				ModelRenderer petalRot = new ModelRenderer(this);
				petalRot.rotateAngleY = ModMathHelper.degreesToRadians(i * 90F + 45F);
				
				ModelRenderer petal = new ModelRenderer(this, 40, 0);
				petal.setRotationPoint(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F);
				
				this.crop.addChild(petalRot);
				petalRot.addChild(petal);
			}
		}
	}
	
	@Override
	public void setRotationAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
	
	public void renderFlower(MatrixStack matrices, IVertexBuilder vertices, int light, int overlay) {
		flower.render(matrices, vertices, light, overlay);
	}
	
	public void renderCrop(MatrixStack matrices, IVertexBuilder vertices, int light, int overlay) {
		crop.render(matrices, vertices, light, overlay);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.innerCube, this.rightEye, this.leftEye, this.mouth);
	}
}
