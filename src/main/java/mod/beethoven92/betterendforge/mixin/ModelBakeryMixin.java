package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin 
{
	@ModifyVariable(method = "loadBlockstate", ordinal = 2, at = @At(value = "INVOKE"))
	public ResourceLocation be_SwitchModel(ResourceLocation id) 
	{
		if (CommonConfig.isCustomChorusPlantEnabled() && id.getNamespace().equals("minecraft") && 
				id.getPath().startsWith("blockstates/") && id.getPath().contains("chorus") && 
				!id.getPath().contains("custom_"))
		{
			id = new ResourceLocation(id.getPath().replace("chorus", "custom_chorus"));
		}
		return id;
	}
}
