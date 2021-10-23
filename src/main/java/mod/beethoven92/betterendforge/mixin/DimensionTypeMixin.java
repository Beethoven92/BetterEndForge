package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.world.generator.BetterEndBiomeProvider;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin 
{
    @Inject(at = @At("HEAD"), method = "getEndChunkGenerator(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/registry/Registry;J)Lnet/minecraft/world/gen/ChunkGenerator;", cancellable = true)
    private static void betterEndGenerator(Registry<Biome> registry, Registry<DimensionSettings> settings, long seed, CallbackInfoReturnable<ChunkGenerator> info) 
    {
    	info.setReturnValue(new NoiseChunkGenerator(
    			new BetterEndBiomeProvider(registry, seed), seed, 
    			() -> settings.getOrThrow(DimensionSettings.field_242737_f)));
    }
	
    @Inject(method = "doesHasDragonFight", at = @At("HEAD"), cancellable = true)
	private void be_hasEnderDragonFight(CallbackInfoReturnable<Boolean> info)
    {
		if (!GeneratorOptions.hasDragonFights())
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
