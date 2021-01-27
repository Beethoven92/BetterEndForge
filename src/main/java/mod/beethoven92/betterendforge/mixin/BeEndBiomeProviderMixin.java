package mod.beethoven92.betterendforge.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

@Mixin(value = EndBiomeProvider.class, priority = 1001)
public abstract class BeEndBiomeProviderMixin extends BiomeProvider
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
	
	private BeEndBiomeProviderMixin(List<Biome> biomes) 
	{
		super(biomes);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	private void be_init(Registry<Biome> lookupRegistry, long seed, CallbackInfo info)
	{
		this.biomes = getBiomes(lookupRegistry);
		
		this.mapLand = new BiomeMap(seed, CommonConfig.biomeSizeLand(), ModBiomes.LAND_BIOMES);
		this.mapVoid = new BiomeMap(seed, CommonConfig.biomeSizeVoid(), ModBiomes.VOID_BIOMES);
		this.centerBiome = lookupRegistry.getOrThrow(Biomes.THE_END);
		this.barrens = lookupRegistry.getOrThrow(Biomes.END_BARRENS);
		//this.lookupRegistry = lookupRegistry;
		//this.seed = seed;

		/*SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
	    sharedseedrandom.skip(17292);
	    this.generator = new SimplexNoiseGenerator(sharedseedrandom);*/
	    
	    ModBiomes.mutateRegistry(lookupRegistry);
	}
	
	@Overwrite
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
	}
	
	/*@Inject(at = @At("HEAD"), method = "getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;", cancellable = true)
	private void be_getNoiseBiome(int x, int y, int z, CallbackInfoReturnable<Biome> info) 
	{
		boolean hasVoid = !CommonConfig.isNewGeneratorEnabled() || !CommonConfig.noRingVoid();
		
		long i = (long) x * (long) x;
		long j = (long) z * (long) z;
		if (hasVoid && i + j <= 65536L)
		{
			info.setReturnValue(this.centerBiome);
			info.cancel();
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
			}
			else 
			{
				info.setReturnValue(mapVoid.getBiome(x << 2, z << 2).getActualBiome());
				info.cancel();
			}
		}
		else 
		{
			float height = EndBiomeProvider.getRandomNoise(generator, (x >> 1) + 1, (z >> 1) + 1) + (float) SMALL_NOISE.eval(x, z) * 5;
	
			if (height > -20F && height < -5F) 
			{
				info.setReturnValue(barrens);
				info.cancel();
			}
			
			BetterEndBiome endBiome = height < -10F ? mapVoid.getBiome(x << 2, z << 2) : mapLand.getBiome(x << 2, z << 2);
			info.setReturnValue(endBiome.getActualBiome());
			info.cancel();
		}
		info.cancel();
	}*/
	
	@Overwrite
	public BiomeProvider getBiomeProvider(long seed) 
	{
		return new EndBiomeProvider(this.lookupRegistry, seed);
	}
	
	private static List<Biome> getBiomes(Registry<Biome> biomeRegistry) 
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
}
