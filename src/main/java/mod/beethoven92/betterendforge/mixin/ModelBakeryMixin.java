package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.util.ResourceLocation;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin 
{
	@ModifyVariable(method = "loadBlockstate", ordinal = 2, at = @At(value = "INVOKE"))
	public ResourceLocation be_SwitchModel(ResourceLocation id) 
	{
		if (GeneratorOptions.changeChorusPlant() && id.getNamespace().equals("minecraft") &&
				id.getPath().startsWith("blockstates/") && id.getPath().contains("chorus") && 
				!id.getPath().contains("custom_"))
		{
			id = new ResourceLocation(id.getPath().replace("chorus", "custom_chorus"));
		}
		return id;
	}

	private boolean be_changeModel(ResourceLocation id) {
		return id.getNamespace().equals("minecraft")
				&& id.getPath().startsWith("blockstates/")
				&& id.getPath().contains("chorus")
				&& !id.getPath().contains("custom_");
	}
}
