package mod.beethoven92.betterendforge.common.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.DoubleBlockSurfaceBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.Biome.TemperatureModifier;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BiomeTemplate 
{
	private static final int DEFAULT_FOLIAGE = ModMathHelper.color(197, 210, 112);
	
	private final List<StructureFeature<?, ?>> structures = new ArrayList<>();
	private final List<FeatureInfo> features = Lists.newArrayList();
	private final List<CarverInfo> carvers = Lists.newArrayList();
	private final List<SpawnInfo> mobs = Lists.newArrayList();
	private final List<MobSpawnInfo.Spawners> spawns = Lists.newArrayList();
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
	private MoodSoundAmbience mood;
	private SoundAdditionsAmbience additions;
	private SoundEvent music;
	
	private ParticleEffectAmbience particle;
	
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
	
	public BiomeTemplate setParticles(IParticleData particle, float probability) 
	{
		this.particle = new ParticleEffectAmbience(particle, probability);
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
		this.mood = new MoodSoundAmbience(mood, 6000, 8, 2.0D);
		return this;
	}
	
	public BiomeTemplate setAdditionsSounds(SoundEvent additions) 
	{
		this.additions = new SoundAdditionsAmbience(additions, 0.0111D);
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
	
	public BiomeTemplate addMobSpawn(MobSpawnInfo.Spawners entry)
	{
		spawns.add(entry);
		return this;
	}
	
	public BiomeTemplate addMobSpawn(EntityClassification type, EntityType<?> entity, 
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
		setSurface(() -> SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(
				block.getDefaultState(),
				Blocks.END_STONE.getDefaultState(),
				Blocks.END_STONE.getDefaultState()
		)));
		return this;
	}
	
	public BiomeTemplate setSurface(Supplier<ConfiguredSurfaceBuilder<?>> surface)
	{
		this.surface = surface;
		return this;
	}
	
	public BiomeTemplate addStructure(StructureFeature<?,?> structure)
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

	public BiomeTemplate addCarver(Carving carverStep, ConfiguredCarver<ProbabilityConfig> carver) 
	{
		CarverInfo info = new CarverInfo();
		info.carverStep = carverStep;
		info.carver = carver;
		carvers.add(info);
		return this;
	}
	
	public Biome build()
	{
		BiomeAmbience.Builder effects = new BiomeAmbience.Builder();
		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
		MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
		
		// Set mob spawns
		for (SpawnInfo info : mobs)
		{
			spawnSettings.withSpawner(info.type, 
					new MobSpawnInfo.Spawners(info.entity, info.weight, info.minGroupSize, info.maxGroupSize));
		}
		
		spawns.forEach((entry) -> {
			spawnSettings.withSpawner(entry.type.getClassification(), entry);
		});
		
		// Set biome general features
		if (surface != null)
		{
			generationSettings.withSurfaceBuilder(surface);
		}
		else
		{
			generationSettings.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e);
		}
		
		for (CarverInfo info : carvers)
		{
			generationSettings.withCarver(info.carverStep, info.carver);
		}
		for(FeatureInfo info : features)
		{
			generationSettings.withFeature(info.stage, info.feature);
		}
		for(StructureFeature<?,?> structure : structures)
		{
			generationSettings.withStructure(structure);
		}
		
		// Set effects
		effects.withSkyColor(0).
		        setWaterFogColor(waterFogColor).
		        setWaterColor(waterColor).
		        setFogColor(fogColor).
		        withFoliageColor(foliageColor).
		        withGrassColor(grassColor);
		
		// Set sound effects
		if (ambient != null) effects.setAmbientSound(ambient);
		if (mood != null) effects.setMoodSound(mood);
		if (additions != null) effects.setAdditionsSound(additions);
		if (music != null)
		{
			effects.setMusic(new BackgroundMusicSelector(music, 600, 2400, true));
		}
		else
		{
			effects.setMusic(BackgroundMusicTracks.END_MUSIC);
		}
		
		// Set particles
		if (particle != null) effects.setParticle(particle);
		
		return new Biome.Builder().
				         category(isCaveBiome ? Category.NONE : Category.THEEND).
				         precipitation(RainType.NONE).
				         depth(this.depth).
				         scale(this.scale).
				         temperature(this.temperature).
				         withTemperatureModifier(TemperatureModifier.NONE).
				         downfall(this.downfall).
				         setEffects(effects.build()).
				         withGenerationSettings(generationSettings.build()).
				         withMobSpawnSettings(spawnSettings.copy()).
				         build();
	}
	
	private static final class SpawnInfo 
	{
		EntityClassification type;
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
		ConfiguredCarver<ProbabilityConfig> carver;
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
