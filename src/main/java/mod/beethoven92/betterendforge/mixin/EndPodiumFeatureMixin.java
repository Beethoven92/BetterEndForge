package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

@Mixin(EndPodiumFeature.class)
public abstract class EndPodiumFeatureMixin 
{
	@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
	private void beGeneratePortal(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, 
			NoFeatureConfig config, CallbackInfoReturnable<Boolean> info) 
	{
		if (!CommonConfig.shouldGenerateVanillaPortal()) 
		{
			info.setReturnValue(false);
		}
	}
}
