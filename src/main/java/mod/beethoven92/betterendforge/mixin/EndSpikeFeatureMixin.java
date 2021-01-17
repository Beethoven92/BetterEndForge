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
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;

@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin 
{
	@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
	private void beGenerateSpike(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, 
			EndSpikeFeatureConfig endSpikeFeatureConfig, CallbackInfoReturnable<Boolean> info) 
	{
		if (!CommonConfig.generateObsidianPillars()) 
        {
			info.setReturnValue(false);
		}
	}
}
