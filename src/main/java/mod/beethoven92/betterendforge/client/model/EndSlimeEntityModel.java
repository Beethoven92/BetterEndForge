package mod.beethoven92.betterendforge.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;

public class EndSlimeEntityModel<T extends EndSlimeEntity> extends ListModel<T> {
	private final ModelPart flower;
	private final ModelPart crop;
	private final ModelPart innerCube;
	private final ModelPart rightEye;
	private final ModelPart leftEye;
	private final ModelPart mouth;
	
	public EndSlimeEntityModel(boolean onlyShell) {
		super(RenderType::entityCutout);
		
		this.innerCube = new ModelPart(this, 0, 16);
		this.rightEye = new ModelPart(this, 32, 0);
		this.leftEye = new ModelPart(this, 32, 4);
		this.mouth = new ModelPart(this, 32, 8);
		this.flower = new ModelPart(this);
		this.crop = new ModelPart(this);

		if (onlyShell) {
			this.innerCube.texOffs(0, 0);
			this.innerCube.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		}
		else {
			this.innerCube.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
			this.rightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
			this.leftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
			this.mouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
			
			for (int i = 0; i < 4; i++) {
				ModelPart petalRot = new ModelPart(this);
				petalRot.yRot = ModMathHelper.degreesToRadians(i * 45F);
				
				ModelPart petal = new ModelPart(this, 40, 0);
				petal.setPos(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F);
				
				this.flower.addChild(petalRot);
				petalRot.addChild(petal);
			}
			
			for (int i = 0; i < 2; i++) {
				ModelPart petalRot = new ModelPart(this);
				petalRot.yRot = ModMathHelper.degreesToRadians(i * 90F + 45F);
				
				ModelPart petal = new ModelPart(this, 40, 0);
				petal.setPos(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F);
				
				this.crop.addChild(petalRot);
				petalRot.addChild(petal);
			}
		}
	}
	
	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
	
	public void renderFlower(PoseStack matrices, VertexConsumer vertices, int light, int overlay) {
		flower.render(matrices, vertices, light, overlay);
	}
	
	public void renderCrop(PoseStack matrices, VertexConsumer vertices, int light, int overlay) {
		crop.render(matrices, vertices, light, overlay);
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(this.innerCube, this.rightEye, this.leftEye, this.mouth);
	}
}
