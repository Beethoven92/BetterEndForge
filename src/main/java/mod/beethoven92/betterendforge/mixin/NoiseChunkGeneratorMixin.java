package mod.beethoven92.betterendforge.mixin;

import java.util.function.Supplier;

import net.minecraft.world.level.levelgen.StructureSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import mod.beethoven92.betterendforge.common.world.generator.TerrainGenerator;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.world.level.biome.BiomeSource;

import java.util.function.Supplier;


@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator
{
	@Final
	@Shadow
	protected Supplier<NoiseGeneratorSettings> settings;

	public NoiseChunkGeneratorMixin(BiomeSource populationSource, BiomeSource biomeSource, StructureSettings structuresConfig, long worldSeed) {
		super(populationSource, biomeSource, structuresConfig, worldSeed);
	}

	@Inject(method = "<init>(Lnet/minecraft/world/biome/provider/BiomeProvider;Lnet/minecraft/world/biome/provider/BiomeProvider;JLjava/util/function/Supplier;)V", at = @At("TAIL"))
	private void beOnInit(BiomeSource populationSource, BiomeSource biomeSource, long seed, Supplier<NoiseGeneratorSettings> settings, CallbackInfo info) 
	{
		TerrainGenerator.initNoise(seed);
	}
	
	@Inject(method = "fillNoiseColumn([DII)V", at = @At("HEAD"), cancellable = true, allow = 2)
	private void beFillNoiseColumn(double[] buffer, int x, int z, CallbackInfo info)
	{
		if (GeneratorOptions.useNewGenerator() && settings.get().stable(NoiseGeneratorSettings.END)) // End settings
		{

			TerrainGenerator.fillTerrainDensity(buffer, x, z, getBiomeSource());
			info.cancel();

		}
	}
}
