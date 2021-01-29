package mod.beethoven92.betterendforge.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.generator.BiomeMap;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import mod.beethoven92.betterendforge.common.world.generator.TerrainGenerator;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;

// By injecting our custom biome generation info into the vanilla EndBiomeProvider
// we avoid incompatibilities when other mods (see BOP for example) use their own world type,
// and that world type uses vanilla EndBiomeProvider by default.
// We need to change our mixin priority to ensure our injection into the getNoiseBiome method
// has precedence over other mixins injecting into the same method, for example AbnormalsCore's EndBiomeProviderMixin.
// This allows compatibility with mods like Endergetic Expansion and Outer End,
// as their biomes will be included in world generation by BetterEnd,
// and ensures that BetterEnd is always in control of the End generation.
@Mixin(value = EndBiomeProvider.class, priority = 100)
public abstract class EndBiomeProviderMixin extends BiomeProvider
{
	private static final OpenSimplexNoise SMALL_NOISE = new OpenSimplexNoise(8324);
	private Biome centerBiome;
	private Biome barrens;
	private BiomeMap mapLand;
	private BiomeMap mapVoid;
	
	@Shadow
	@Final
	private Registry<Biome> lookupRegistry;
	@Shadow
	@Final
	private SimplexNoiseGenerator generator;
	@Shadow
	@Final
	private long seed;
	
	private EndBiomeProviderMixin(List<Biome> biomes) 
	{
		super(biomes);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	private void be_init(Registry<Biome> lookupRegistry, long seed, CallbackInfo info)
	{
		// The "biomes" list is filled when the EndBiomeProvider constructor is called
		// and by default is hardcoded to contain only vanilla End biomes.
		// We need to reset this list to include all End biomes (vanilla and modded) that are found in the registry.
		this.biomes = be_getBiomes(lookupRegistry);
		
		this.mapLand = new BiomeMap(seed, CommonConfig.biomeSizeLand(), ModBiomes.LAND_BIOMES);
		this.mapVoid = new BiomeMap(seed, CommonConfig.biomeSizeVoid(), ModBiomes.VOID_BIOMES);
		this.centerBiome = lookupRegistry.getOrThrow(Biomes.THE_END);
		this.barrens = lookupRegistry.getOrThrow(Biomes.END_BARRENS);
	    
	    ModBiomes.mutateRegistry(lookupRegistry);
	}
	
	// Gathers all available End biomes in the registry
	private List<Biome> be_getBiomes(Registry<Biome> biomeRegistry) 
	{
		List<Biome> list = Lists.newArrayList();
		biomeRegistry.forEach((biome) -> {
			if (biome.getCategory() == Category.THEEND) 
			{
				list.add(biome);
			}
		});
		return list;
	}
	
	/*@Overwrite
	public Biome getNoiseBiome(int x, int y, int z) 
	{
		boolean hasVoid = !CommonConfig.isNewGeneratorEnabled() || !CommonConfig.noRingVoid();
		
		long i = (long) x * (long) x;
		long j = (long) z * (long) z;
		if (hasVoid && i + j <= 65536L) return this.centerBiome;
		
		if (x == 0 && z == 0) 
		{
			mapLand.clearCache();
			mapVoid.clearCache();
		}
		
		if (CommonConfig.isNewGeneratorEnabled()) 
		{
			if (TerrainGenerator.isLand(x, z)) 
			{
				return mapLand.getBiome(x << 2, z << 2).getActualBiome();
			}
			else 
			{
				return mapVoid.getBiome(x << 2, z << 2).getActualBiome();
			}
		}
		else 
		{
			float height = EndBiomeProvider.getRandomNoise(generator, (x >> 1) + 1, (z >> 1) + 1) + (float) SMALL_NOISE.eval(x, z) * 5;
	
			if (height > -20F && height < -5F) 
			{
				return barrens;
			}
			
			BetterEndBiome endBiome = height < -10F ? mapVoid.getBiome(x << 2, z << 2) : mapLand.getBiome(x << 2, z << 2);
			return endBiome.getActualBiome();
		}
	}*/
	
	// Since @Overwrite of the getNoiseBiome appears to not work when another mod injects its own code
	// into the same method before us, we are forced to make an @Inject, 
	// that also acts as an overwrite of the original method.
	@Inject(at = @At("HEAD"), method = "getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;", cancellable = true)
	private void be_getNoiseBiome(int x, int y, int z, CallbackInfoReturnable<Biome> info) 
	{
		boolean hasVoid = !CommonConfig.isNewGeneratorEnabled() || !CommonConfig.noRingVoid();
		
		long i = (long) x * (long) x;
		long j = (long) z * (long) z;
		if (hasVoid && i + j <= 65536L)
		{
			info.setReturnValue(this.centerBiome);
			info.cancel();
			return;
		}
		
		if (x == 0 && z == 0) 
		{
			mapLand.clearCache();
			mapVoid.clearCache();
		}
		
		if (CommonConfig.isNewGeneratorEnabled()) 
		{
			if (TerrainGenerator.isLand(x, z)) 
			{
				info.setReturnValue(mapLand.getBiome(x << 2, z << 2).getActualBiome());
				info.cancel();
				return;
			}
			else 
			{
				info.setReturnValue(mapVoid.getBiome(x << 2, z << 2).getActualBiome());
				info.cancel();
				return;
			}
		}
		else 
		{
			float height = EndBiomeProvider.getRandomNoise(generator, (x >> 1) + 1, (z >> 1) + 1) + (float) SMALL_NOISE.eval(x, z) * 5;
	
			if (height > -20F && height < -5F) 
			{
				info.setReturnValue(barrens);
				info.cancel();
				return;
			}
			
			BetterEndBiome endBiome = height < -10F ? mapVoid.getBiome(x << 2, z << 2) : mapLand.getBiome(x << 2, z << 2);
			info.setReturnValue(endBiome.getActualBiome());
			info.cancel();
			return;
		}
	}
	
	@Overwrite
	public BiomeProvider getBiomeProvider(long seed) 
	{
		return new EndBiomeProvider(this.lookupRegistry, seed);
	}
}
