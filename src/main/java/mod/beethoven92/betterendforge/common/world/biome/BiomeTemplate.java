package mod.beethoven92.betterendforge.common.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.DoubleBlockSurfaceBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class BiomeTemplate 
{
	private static final int DEFAULT_FOLIAGE = ModMathHelper.color(197, 210, 112);
	
	private final List<ConfiguredStructureFeature<?, ?>> structures = new ArrayList<>();
	private final List<FeatureInfo> features = Lists.newArrayList();
	private final List<CarverInfo> carvers = Lists.newArrayList();
	private final List<SpawnInfo> mobs = Lists.newArrayList();
	private final List<MobSpawnSettings.SpawnerData> spawns = Lists.newArrayList();
	private Supplier<ConfiguredSurfaceBuilder<?>> surface;
	
	private float depth = 0.1F;
    private float scale = 0.2F;
    private float temperature = 2.0F;
    private float downfall = 0.0F;
    
	private float fogDensity = 1F;;
	private int fogColor = 10518688;;
	private int waterColor = 4159204;;
	private int waterFogColor = 329011;
	private int foliageColor = DEFAULT_FOLIAGE;
	private int grassColor = DEFAULT_FOLIAGE;
	
	private SoundEvent ambient;
	private AmbientMoodSettings mood;
	private AmbientAdditionsSettings additions;
	private SoundEvent music;
	
	private AmbientParticleSettings particle;
	
	private final ResourceLocation id;
	private float genChance = 1F;
	private boolean hasCaves = true;
	private boolean isCaveBiome = false;
	
	public BiomeTemplate(String name)
	{
		this.id = new ResourceLocation(BetterEnd.MOD_ID, name);
	}

	public BiomeTemplate setDepth(float depth)
	{
		this.depth = depth;
		return this;
	}

	public BiomeTemplate setScale(float scale)
	{
		this.scale = scale;
		return this;
	}
	
	public BiomeTemplate setTemperature(float temperature)
	{
		this.temperature = temperature;
		return this;
	}
	
	public BiomeTemplate setDownfall(float downfall)
	{
		this.downfall = downfall;
		return this;
	}
	
	public BiomeTemplate setParticles(ParticleOptions particle, float probability) 
	{
		this.particle = new AmbientParticleSettings(particle, probability);
		return this;
	}
	
	public BiomeTemplate setFogDensity(float density) 
	{
		this.fogDensity = density;
		return this;
	}
	
	public BiomeTemplate setFogColor(int r, int g, int b)
	{
		fogColor = ModMathHelper.getColor(r, g, b);
		return this;
	}
	
	public BiomeTemplate setFogColor(int color)
	{
		fogColor = color;
		return this;
	}

	public BiomeTemplate setWaterColor(int r, int g, int b) 
	{
		this.waterColor = ModMathHelper.getColor(r, g, b);
		return this;
	}
	
	public BiomeTemplate setWaterColor(int color)
	{
		this.waterColor = color;
		return this;
	}

	public BiomeTemplate setWaterFogColor(int r, int g, int b) 
	{
		this.waterFogColor = ModMathHelper.getColor(r, g, b);
		return this;
	}
	
	public BiomeTemplate setWaterFogColor(int color) 
	{
		this.waterFogColor = color;
		return this;
	}
	
	public BiomeTemplate setFoliageColor(int r, int g, int b) 
	{
		this.foliageColor = ModMathHelper.getColor(r, g, b);
		return this;
	}
	
	public BiomeTemplate setFoliageColor(int color) 
	{
		this.foliageColor = color;
		return this;
	}
	
	public BiomeTemplate setGrassColor(int r, int g, int b) 
	{
		this.grassColor = ModMathHelper.getColor(r, g, b);
		return this;
	}
	
	public BiomeTemplate setGrassColor(int color) 
	{
		this.grassColor = color;
		return this;
	}
	
	public BiomeTemplate setAmbientSound(SoundEvent ambient) 
	{
		this.ambient = ambient;
		return this;
	}

	public BiomeTemplate setMoodSound(SoundEvent mood) 
	{
		this.mood = new AmbientMoodSettings(mood, 6000, 8, 2.0D);
		return this;
	}
	
	public BiomeTemplate setAdditionsSounds(SoundEvent additions) 
	{
		this.additions = new AmbientAdditionsSettings(additions, 0.0111D);
		return this;
	}
	
	public BiomeTemplate setMusic(SoundEvent music)
	{
		this.music = music;
		return this;
	}
	
	public BiomeTemplate setCaves(boolean hasCaves) 
	{
		this.hasCaves = hasCaves;
		return this;
	}
	
	public BiomeTemplate setCaveBiome() 
	{
		isCaveBiome = true;
		return this;
	}
	
	public BiomeTemplate setGenChance(float genChance) 
	{
		this.genChance = genChance;
		return this;
	}
	
	public BiomeTemplate addMobSpawn(MobSpawnSettings.SpawnerData entry)
	{
		spawns.add(entry);
		return this;
	}
	
	public BiomeTemplate addMobSpawn(MobCategory type, EntityType<?> entity, 
			int weight, int minCount, int maxCount)
	{
		SpawnInfo info = new SpawnInfo();
		info.type = type;
		info.entity = entity;
		info.weight = weight;
		info.minGroupSize = minCount;
		info.maxGroupSize = maxCount;
		mobs.add(info);
		return this;
	}
	
	public BiomeTemplate setSurface(Block block) 
	{
		setSurface(() -> SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderBaseConfiguration(
				block.defaultBlockState(),
				Blocks.END_STONE.defaultBlockState(),
				Blocks.END_STONE.defaultBlockState()
		)));
		return this;
	}
	
	public BiomeTemplate setSurface(Supplier<ConfiguredSurfaceBuilder<?>> surface)
	{
		this.surface = surface;
		return this;
	}
	
	public BiomeTemplate addStructure(ConfiguredStructureFeature<?,?> structure)
	{
		structures.add(structure);
		return this;
	}

	public BiomeTemplate addFeature(Decoration stage, ConfiguredFeature<?,?> feature)
	{
		FeatureInfo info = new FeatureInfo();
		info.stage = stage;
		info.feature = feature;
		features.add(info);
		return this;
	}

	public BiomeTemplate addCarver(Carving carverStep, ConfiguredWorldCarver<ProbabilityFeatureConfiguration> carver) 
	{
		CarverInfo info = new CarverInfo();
		info.carverStep = carverStep;
		info.carver = carver;
		carvers.add(info);
		return this;
	}
	
	public Biome build()
	{
		BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder();
		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
		MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
		
		// Set mob spawns
		for (SpawnInfo info : mobs)
		{
			spawnSettings.addSpawn(info.type, 
					new MobSpawnSettings.SpawnerData(info.entity, info.weight, info.minGroupSize, info.maxGroupSize));
		}
		
		spawns.forEach((entry) -> {
			spawnSettings.addSpawn(entry.type.getCategory(), entry);
		});
		
		// Set biome general features
		if (surface != null)
		{
			generationSettings.surfaceBuilder(surface);
		}
		else
		{
			generationSettings.surfaceBuilder(SurfaceBuilders.END);
		}
		
		for (CarverInfo info : carvers)
		{
			generationSettings.addCarver(info.carverStep, info.carver);
		}
		for(FeatureInfo info : features)
		{
			generationSettings.addFeature(info.stage, info.feature);
		}
		for(ConfiguredStructureFeature<?,?> structure : structures)
		{
			generationSettings.addStructureStart(structure);
		}
		
		// Set effects
		effects.skyColor(0).
		        waterFogColor(waterFogColor).
		        waterColor(waterColor).
		        fogColor(fogColor).
		        foliageColorOverride(foliageColor).
		        grassColorOverride(grassColor);
		
		// Set sound effects
		if (ambient != null) effects.ambientLoopSound(ambient);
		if (mood != null) effects.ambientMoodSound(mood);
		if (additions != null) effects.ambientAdditionsSound(additions);
		if (music != null)
		{
			effects.backgroundMusic(new Music(music, 600, 2400, true));
		}
		else
		{
			effects.backgroundMusic(Musics.END);
		}
		
		// Set particles
		if (particle != null) effects.ambientParticle(particle);
		
		return new Biome.BiomeBuilder().
				         biomeCategory(isCaveBiome ? BiomeCategory.NONE : BiomeCategory.THEEND).
				         precipitation(Precipitation.NONE).
				         depth(this.depth).
				         scale(this.scale).
				         temperature(this.temperature).
				         temperatureAdjustment(TemperatureModifier.NONE).
				         downfall(this.downfall).
				         specialEffects(effects.build()).
				         generationSettings(generationSettings.build()).
				         mobSpawnSettings(spawnSettings.build()).
				         build();
	}
	
	private static final class SpawnInfo 
	{
		MobCategory type;
		EntityType<?> entity;
		int weight;
		int minGroupSize;
		int maxGroupSize;
	}

	private static final class FeatureInfo 
	{
		Decoration stage;
		ConfiguredFeature<?,?> feature;
	}
	
	private static final class CarverInfo 
	{
		Carving carverStep;
		ConfiguredWorldCarver<ProbabilityFeatureConfiguration> carver;
	}
	
	public ResourceLocation getID() 
	{
		return id;
	}
	
	public float getFogDensity() 
	{
		return fogDensity;
	}
	
	public float getGenChance() 
	{
		return genChance;
	}

	public boolean hasCaves() 
	{
		return hasCaves;
	}
}
