package mod.beethoven92.betterendforge.mixin;

import java.util.function.Supplier;

import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

import mod.beethoven92.betterendforge.common.world.generator.TerrainGenerator;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.world.biome.provider.BiomeProvider;

import java.util.function.Supplier;


@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator
{
	@Final
	@Shadow
	protected Supplier<DimensionSettings> field_236080_h_;

	public NoiseChunkGeneratorMixin(BiomeProvider populationSource, BiomeProvider biomeSource, DimensionStructuresSettings structuresConfig, long worldSeed) {
		super(populationSource, biomeSource, structuresConfig, worldSeed);
	}

	@Inject(method = "<init>(Lnet/minecraft/world/biome/provider/BiomeProvider;Lnet/minecraft/world/biome/provider/BiomeProvider;JLjava/util/function/Supplier;)V", at = @At("TAIL"))
	private void beOnInit(BiomeProvider populationSource, BiomeProvider biomeSource, long seed, Supplier<DimensionSettings> settings, CallbackInfo info) 
	{
		TerrainGenerator.initNoise(seed);
	}
	
	@Inject(method = "fillNoiseColumn([DII)V", at = @At("HEAD"), cancellable = true, allow = 2)
	private void beFillNoiseColumn(double[] buffer, int x, int z, CallbackInfo info)
	{
		if (GeneratorOptions.useNewGenerator() && field_236080_h_.get().func_242744_a(DimensionSettings.field_242737_f)) // End settings
		{

			TerrainGenerator.fillTerrainDensity(buffer, x, z, getBiomeProvider());
			info.cancel();

		}
	}
}
