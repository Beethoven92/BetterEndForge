package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.EndCityFeature;

@Mixin(EndCityFeature.class)
public abstract class EndCityStructureMixin 
{
	@Inject(method = "isFeatureChunk", at = @At("HEAD"), cancellable = true)
	private void be_shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, 
			WorldgenRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos,
			NoneFeatureConfiguration config, CallbackInfoReturnable<Boolean> info) 
	{
		if (GeneratorOptions.useNewGenerator())
		{
			int chance = GeneratorOptions.getEndCityFailChance();
			if (chance == 0) 
			{
				info.setReturnValue(getYPositionForFeature(i, j, chunkGenerator) >= 60);
				info.cancel();
			}
			else if (chunkRandom.nextInt(chance) == 0)
			{
				info.setReturnValue(getYPositionForFeature(i, j, chunkGenerator) >= 60);
				info.cancel();
			}
			else 
			{
				info.setReturnValue(false);
				info.cancel();
			}
		}
	}
	
	@Shadow
	private static int getYPositionForFeature(int chunkX, int chunkY, ChunkGenerator generatorIn)
	{
		return 0;
	}
}
