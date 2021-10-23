package mod.beethoven92.betterendforge.common.world.generator;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;

public class BetterEndBiomeProvider extends BiomeProvider
{
	public static final Codec<BetterEndBiomeProvider> BETTER_END_CODEC = RecordCodecBuilder.create(
			(builder) -> {return builder.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(
					(provider) -> {return provider.lookupRegistry;}), Codec.LONG.fieldOf("seed").stable().forGetter(
							(provider) -> {return provider.seed;})).
					apply(builder, builder.stable(BetterEndBiomeProvider::new));
		   });
	
	private static final OpenSimplexNoise SMALL_NOISE = new OpenSimplexNoise(8324);
	private final SimplexNoiseGenerator generator;
	private final Registry<Biome> lookupRegistry;
	private final Biome centerBiome;
	private final Biome barrens;
	private BiomeMap mapLand;
	private BiomeMap mapVoid;
	private final long seed;

	
	public BetterEndBiomeProvider(Registry<Biome> lookupRegistry, long seed) 
	{
		super(getBiomes(lookupRegistry));
		
		this.mapLand = new BiomeMap(seed, GeneratorOptions.getBiomeSizeLand(), ModBiomes.LAND_BIOMES);
		this.mapVoid = new BiomeMap(seed, GeneratorOptions.getBiomeSizeVoid(), ModBiomes.VOID_BIOMES);
		this.centerBiome = lookupRegistry.getOrThrow(Biomes.THE_END);
		this.barrens = lookupRegistry.getOrThrow(Biomes.END_BARRENS);
		this.lookupRegistry = lookupRegistry;
		this.seed = seed;

		SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
	    sharedseedrandom.skip(17292);
	    this.generator = new SimplexNoiseGenerator(sharedseedrandom);
	    
	    ModBiomes.mutateRegistry(lookupRegistry);
	}
	
	private static List<Biome> getBiomes(Registry<Biome> biomeRegistry) 
	{
		List<Biome> list = Lists.newArrayList();
		biomeRegistry.forEach((biome) -> {
			if (ModBiomes.hasBiome(biomeRegistry.getKey(biome)))
			{
				list.add(biome);
			}
			/*if (biome.getCategory() == Category.THEEND) 
			{
				list.add(biome);
			}*/
		});
		return list;
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) 
	{
		boolean hasVoid = !GeneratorOptions.useNewGenerator() || !GeneratorOptions.noRingVoid();
		
		long i = (long) x * (long) x;
		long j = (long) z * (long) z;
		if (hasVoid && i + j <= 65536L) return this.centerBiome;
		
		if (x == 0 && z == 0) 
		{
			mapLand.clearCache();
			mapVoid.clearCache();
		}
		
		if (GeneratorOptions.useNewGenerator())
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

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec() 
	{
		return BETTER_END_CODEC;
	}

	@Override
	public BiomeProvider getBiomeProvider(long seed) 
	{
		return new BetterEndBiomeProvider(this.lookupRegistry, seed);
	}
	
	public static void register()
	{
		Registry.register(Registry.BIOME_PROVIDER_CODEC, 
				new ResourceLocation(BetterEnd.MOD_ID, "betterend_biome_provider"), BETTER_END_CODEC);
	}
}
