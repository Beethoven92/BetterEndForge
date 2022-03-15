package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.world.generator.BetterEndBiomeProvider;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin 
{
    @Inject(at = @At("HEAD"), method = "defaultEndGenerator(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/registry/Registry;J)Lnet/minecraft/world/gen/ChunkGenerator;", cancellable = true)
    private static void betterEndGenerator(Registry<Biome> registry, Registry<NoiseGeneratorSettings> settings, long seed, CallbackInfoReturnable<ChunkGenerator> info) 
    {
    	info.setReturnValue(new NoiseBasedChunkGenerator(
    			new BetterEndBiomeProvider(registry, seed), seed, 
    			() -> settings.getOrThrow(NoiseGeneratorSettings.END)));
    }
	
    @Inject(method = "createDragonFight", at = @At("HEAD"), cancellable = true)
	private void be_hasEnderDragonFight(CallbackInfoReturnable<Boolean> info)
    {
		if (!GeneratorOptions.hasDragonFights())
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
