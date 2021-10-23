package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
		if (!GeneratorOptions.hasPortal())
		{
			info.setReturnValue(false);
		}
	}
}