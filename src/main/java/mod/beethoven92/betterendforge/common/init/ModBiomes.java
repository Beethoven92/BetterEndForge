package mod.beethoven92.betterendforge.common.init;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.JsonFactory;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.biome.ChorusForestBiome;
import mod.beethoven92.betterendforge.common.world.biome.CrystalMountainsBiome;
import mod.beethoven92.betterendforge.common.world.biome.DustWastelandsBiome;
import mod.beethoven92.betterendforge.common.world.biome.FoggyMushroomlandBiome;
import mod.beethoven92.betterendforge.common.world.biome.MegalakeBiome;
import mod.beethoven92.betterendforge.common.world.biome.MegalakeGroveBiome;
import mod.beethoven92.betterendforge.common.world.biome.PaintedMountainsBiome;
import mod.beethoven92.betterendforge.common.world.biome.ShadowForestBiome;
import mod.beethoven92.betterendforge.common.world.generator.BiomePicker;
import mod.beethoven92.betterendforge.common.world.generator.EndBiomeType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;

public class ModBiomes 
{
	//public static List<Biome> BIOME_LIST = Lists.newArrayList();
	
	private static final HashMap<ResourceLocation, BetterEndBiome> ID_MAP = Maps.newHashMap();
	private static final HashMap<Biome, BetterEndBiome> CLIENT = Maps.newHashMap();
	
	public static final BiomePicker LAND_BIOMES = new BiomePicker();
	public static final BiomePicker VOID_BIOMES = new BiomePicker();
	public static final List<BetterEndBiome> SUBBIOMES = Lists.newArrayList();
	private static final JsonObject EMPTY_JSON = new JsonObject();
	
	private static Registry<Biome> biomeRegistry;
	private static Set<Integer> occupiedIDs = Sets.newHashSet();
	private static int incID = 8196;
	
	// Vanilla Land
	public static final BetterEndBiome END = registerBiome(Biomes.THE_END, EndBiomeType.LAND, 1F);
	public static final BetterEndBiome END_MIDLANDS = registerSubBiome(Biomes.END_MIDLANDS, END, 0.5F);
	public static final BetterEndBiome END_HIGHLANDS = registerSubBiome(Biomes.END_HIGHLANDS, END, 0.5F);
		
	// Vanilla Void
	public static final BetterEndBiome END_BARRENS = registerBiome(Biomes.END_BARRENS, EndBiomeType.VOID, 1F);
	public static final BetterEndBiome SMALL_END_ISLANDS = registerBiome(Biomes.SMALL_END_ISLANDS, EndBiomeType.VOID, 1);
	
	// Better End Land
	public static final BetterEndBiome MEGALAKE = registerBiome(new MegalakeBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome CRYSTAL_MOUNTAINS = registerBiome(new CrystalMountainsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome FOGGY_MUSHROOMLAND = registerBiome(new FoggyMushroomlandBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome MEGALAKE_GROVE = registerSubBiome(new MegalakeGroveBiome(), MEGALAKE);
	public static final BetterEndBiome DUST_WASTELANDS = registerBiome(new DustWastelandsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome PAINTED_MOUNTAINS = registerSubBiome(new PaintedMountainsBiome(), DUST_WASTELANDS);
	public static final BetterEndBiome CHORUS_FOREST = registerBiome(new ChorusForestBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome SHADOW_FOREST = registerBiome(new ShadowForestBiome(), EndBiomeType.LAND);
	
	public static void register() {}
	
	public static void mutateRegistry(Registry<Biome> biomeRegistry) 
	{
		ModBiomes.biomeRegistry = biomeRegistry;
		
		LAND_BIOMES.clearMutables();
		VOID_BIOMES.clearMutables();
		
		Map<String, JsonObject> configs = Maps.newHashMap();
		biomeRegistry.forEach((biome) -> {
			if (biome.getCategory() == Category.THEEND) 
			{
				ResourceLocation id = biomeRegistry.getKey(biome);
				
				if (!LAND_BIOMES.containsImmutable(id) && !VOID_BIOMES.containsImmutable(id)) 
				{
					JsonObject config = configs.get(id.getNamespace());
					if (config == null) {
						config = loadJsonConfig(id.getNamespace());
						configs.put(id.getNamespace(), config);
					}
					float fog = 1F;
					float chance = 1F;
					boolean isVoid = false;
					boolean hasCaves = true;
					JsonElement element = config.get(id.getPath());
					if (element != null && element.isJsonObject()) 
					{
						fog = JsonFactory.getFloat(element.getAsJsonObject(), "fogDensity", 1);
						chance = JsonFactory.getFloat(element.getAsJsonObject(), "genChance", 1);
						isVoid = JsonFactory.getString(element.getAsJsonObject(), "type", "land").equals("void");
						hasCaves = JsonFactory.getBoolean(element.getAsJsonObject(), "has_caves", true);
					}
					BetterEndBiome endBiome = new BetterEndBiome(id, biome, fog, chance, hasCaves);
					if (isVoid) 
					{
						VOID_BIOMES.addBiomeMutable(endBiome);
					}
					else 
					{
						LAND_BIOMES.addBiomeMutable(endBiome);
					}
					ID_MAP.put(id, endBiome);
				}
			}
		});
		
		LAND_BIOMES.rebuild();
		VOID_BIOMES.rebuild();
		
		LAND_BIOMES.getBiomes().forEach((endBiome) -> {
			Biome biome = biomeRegistry.getOrDefault(endBiome.getID());
			endBiome.setActualBiome(biome);
		});
		
		VOID_BIOMES.getBiomes().forEach((endBiome) -> {
			Biome biome = biomeRegistry.getOrDefault(endBiome.getID());
			endBiome.setActualBiome(biome);
		});
		
		SUBBIOMES.forEach((endBiome) -> {
			Biome biome = biomeRegistry.getOrDefault(endBiome.getID());
			endBiome.setActualBiome(biome);
		});
		
		CLIENT.clear();
	}
	
	private static JsonObject loadJsonConfig(String namespace) 
	{
		InputStream inputstream = ModBiomes.class.getResourceAsStream("/data/" + namespace + "/end_biome_properties.json");
		if (inputstream != null) 
		{
			return JsonFactory.getJsonObject(inputstream);
		}
		else 
		{
			return EMPTY_JSON;
		}
	}
	
	public static void initRegistry(MinecraftServer server) 
	{
		if (biomeRegistry == null) 
		{
			biomeRegistry = server.func_244267_aX().getRegistry(Registry.BIOME_KEY);
		}
	}
	
	// REGISTER A BETTER END BIOME FROM AN EXISTING BIOME
	public static BetterEndBiome registerBiome(Biome biome, EndBiomeType type, float genChance) 
	{
		return registerBiome(biome, type, 1, genChance);
	}
	
	public static BetterEndBiome registerBiome(Biome biome, EndBiomeType type, float fogDensity, float genChance) 
	{
		BetterEndBiome endBiome = new BetterEndBiome(WorldGenRegistries.BIOME.getKey(biome), biome, fogDensity, genChance, true);
		addToPicker(endBiome, type);
		return endBiome;
	}
	
	// REGISTER A BETTER END BIOME FROM AN EXISTING BIOME AND SET IT AS SUB BIOME FOR THE SELECTED PARENT
	public static BetterEndBiome registerSubBiome(Biome biome, BetterEndBiome parent, float genChance, boolean hasCaves) 
	{
		return registerSubBiome(biome, parent, 1, genChance, hasCaves);
	}
	
	public static BetterEndBiome registerSubBiome(Biome biome, BetterEndBiome parent, float fogDensity, float genChance, boolean hasCaves) 
	{
		BetterEndBiome endBiome = new BetterEndBiome(WorldGenRegistries.BIOME.getKey(biome), biome, fogDensity, genChance, hasCaves);
		parent.addSubBiome(endBiome);
		SUBBIOMES.add(endBiome);
		ID_MAP.put(endBiome.getID(), endBiome);
		return endBiome;
	}
	
	// REGISTER A NEW BETTER END BIOME
	public static BetterEndBiome registerBiome(BetterEndBiome biome, EndBiomeType type) 
	{
		registerBiomeDirect(biome);
		addToPicker(biome, type);
		ID_MAP.put(biome.getID(), biome);
		return biome;
	}
	
	public static BetterEndBiome registerSubBiome(BetterEndBiome biome, BetterEndBiome parent) {
		registerBiomeDirect(biome);
		parent.addSubBiome(biome);
		SUBBIOMES.add(biome);
		ID_MAP.put(biome.getID(), biome);
		return biome;
	}
	
	private static BetterEndBiome registerBiome(RegistryKey<Biome> key, EndBiomeType type, float genChance) 
	{
		return registerBiome(WorldGenRegistries.BIOME.getOrThrow(key), type, genChance);
	}
	
	private static BetterEndBiome registerSubBiome(RegistryKey<Biome> key, BetterEndBiome parent, float genChance) 
	{
		return registerSubBiome(WorldGenRegistries.BIOME.getOrThrow(key), parent, genChance, true);
	}
	
	private static void addToPicker(BetterEndBiome biome, EndBiomeType type) 
	{
		if (type == EndBiomeType.LAND)
			LAND_BIOMES.addBiome(biome);
		else
			VOID_BIOMES.addBiome(biome);
	}
	
	private static void fillSet() 
	{
		if (occupiedIDs.isEmpty())
		{
			WorldGenRegistries.BIOME.getEntries().forEach((entry) -> {
				int id = WorldGenRegistries.BIOME.getId(entry.getValue());
				occupiedIDs.add(id);
			});
		}
	}
	
	private static void registerBiomeDirect(BetterEndBiome biome) 
	{
		fillSet();
		int possibleID = incID++;
		if (occupiedIDs.contains(possibleID)) 
		{
			String message = "ID for biome " + biome.getID() + " is already occupied, changing biome ID from " + possibleID + " to ";
			while (occupiedIDs.contains(possibleID)) 
			{
				possibleID ++;
			}
			BetterEnd.LOGGER.info(message + possibleID);
		}
		// Can not mess with vanilla registries anymore
		//Registry.register(WorldGenRegistries.BIOME, possibleID, biome.getID().toString(), biome.getBiome());
		biome.getBiome().setRegistryName(biome.getID());
		//BIOME_LIST.add(biome.getBiome());
	}
	
	public static BetterEndBiome getFromBiome(Biome biome) 
	{
		return ID_MAP.getOrDefault(biomeRegistry.getKey(biome), END);
	}
	
	public static ResourceLocation getBiomeID(Biome biome) 
	{
		ResourceLocation id = biomeRegistry.getKey(biome);
		return id == null ? END.getID() : id;
	}

	public static BetterEndBiome getBiome(ResourceLocation biomeID) 
	{
		return ID_MAP.getOrDefault(biomeID, END);
	}

	public static List<BetterEndBiome> getModBiomes() 
	{
		List<BetterEndBiome> result = Lists.newArrayList();
		result.addAll(LAND_BIOMES.getBiomes());
		result.addAll(VOID_BIOMES.getBiomes());
		result.addAll(SUBBIOMES);
		return result;
	}
}
