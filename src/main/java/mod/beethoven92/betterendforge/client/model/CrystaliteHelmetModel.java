package mod.beethoven92.betterendforge.client.model;

import java.util.Collections;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class CrystaliteHelmetModel extends HumanoidModel<LivingEntity> {

	public CrystaliteHelmetModel(float scale) {
		super(RenderType::entityTranslucent, scale, 0.0F, 64, 48);
		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, scale + 0.5F);
		this.head.setPos(0.0F, 0.0F, 0.0F);
	}
	
	@Override
	protected Iterable<ModelPart> headParts() {
		return Collections::emptyIterator;
	}
	
	@Override
	protected Iterable<ModelPart> bodyParts() {
		return Lists.newArrayList(head);
	}
}
