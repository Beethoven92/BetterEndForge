package mod.beethoven92.betterendforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
    /*
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.BooleanValue VANILLA_END_INTEGRATION_ENABLED;
    public static final ForgeConfigSpec.ConfigValue<Integer> BIOME_SIZE_LAND;
    public static final ForgeConfigSpec.ConfigValue<Integer> BIOME_SIZE_VOID;
    public static final ForgeConfigSpec.BooleanValue DRAGON_FIGHT_ENABLED;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VANILLA_PORTAL;
    public static final ForgeConfigSpec.BooleanValue GENERATE_OBSIDIAN_PILLARS;
    public static final ForgeConfigSpec.BooleanValue GENERATE_OBSIDIAN_PLATFORM;
	public static final ForgeConfigSpec.BooleanValue CUSTOM_CHORUS_PLANT_ENABLED;
	public static final ForgeConfigSpec.BooleanValue CHORUS_IN_VANILLA_BIOMES_ENABLED;
    public static final ForgeConfigSpec.BooleanValue NEW_GENERATOR_ENABLED;
    public static final ForgeConfigSpec.BooleanValue NO_RING_VOID;
    public static final ForgeConfigSpec.BooleanValue GENERATE_CENTRAL_ISLAND;
    public static final ForgeConfigSpec.BooleanValue SWAP_OVERWORLD_WITH_END;
    public static final ForgeConfigSpec.BooleanValue GIVE_GUIDE_BOOK;
    public static final ForgeConfigSpec.BooleanValue REDUCE_ENDERMAN_SPAWN;
    public static final ForgeConfigSpec.ConfigValue<Integer> END_CITY_FAIL_CHANCE;

    static
    {
        BUILDER.push("End generation settings");

        BUILDER.comment("\nEnable/disable BetterEnd integration with vanilla End biome provider."
        		+ "\nThis allows BetterEnd to inject its own biome generation infos into the vanilla EndBiomeProvider,"
        		+ "\nmaking BetterEnd biomes integrate with Biomes O' Plenty worlds and already generated worlds."
        		+ "\nIf, for some reason, you don't want this feature, here is the option to change it"
        		+ "\nDefault value: true");
        VANILLA_END_INTEGRATION_ENABLED = BUILDER.define("vanillaEndIntegrationEnabled", true);

        BUILDER.comment("\nLand biome map size.\nDefault value: 256");
        BIOME_SIZE_LAND = BUILDER.define("biomeSizeLand", 256);

        BUILDER.comment("\nVoid biome map size.\nDefault value: 256");
        BIOME_SIZE_VOID = BUILDER.define("biomeSizeVoid", 256);

        BUILDER.comment("\nEnable/disable dragon fight.\nDefault value: true");
        DRAGON_FIGHT_ENABLED = BUILDER.define("dragonFightEnabled", true);

        BUILDER.comment("\nEnable/disable vanilla portal generation.\nDefault value: true");
        GENERATE_VANILLA_PORTAL = BUILDER.define("generateVanillaPortal", true);

        BUILDER.comment("\nEnable/disable obsidian pillars generation.\nDefault value: true");
        GENERATE_OBSIDIAN_PILLARS = BUILDER.define("generateObsidianPillars", true);

        BUILDER.comment("\nEnable/disable generation of the obsidian platform where the player spawns in the End.\nDefault value: true");
        GENERATE_OBSIDIAN_PLATFORM = BUILDER.define("generateObsidianPlatform", true);

        BUILDER.comment("\nEnable/disable custom chorus plant (set this to false if you experience block shifting issues)\nDefault value: true");
        CUSTOM_CHORUS_PLANT_ENABLED = BUILDER.define("customChorusPlantEnabled", true);

        BUILDER.comment("\nEnable/disable chorus generation in vanilla biomes.\nDefault value: true");
        CHORUS_IN_VANILLA_BIOMES_ENABLED = BUILDER.define("chorusInVanillaBiomesEnabled", true);

        BUILDER.comment("\nEnable/disable new End terrain generation.\nDefault value: false");
        NEW_GENERATOR_ENABLED = BUILDER.define("enableNewGenerator", false);

        BUILDER.comment("\nEnable/disable the void ring around central island.\nDefault value: false");
        NO_RING_VOID = BUILDER.define("noRingVoid", false);

        BUILDER.comment("\nEnable/disable generation of the central island.\nDefault value: false");
        GENERATE_CENTRAL_ISLAND = BUILDER.define("generateCentralIsland", false);

        BUILDER.comment("\nAllows the player to spawn in the End instead of the Overworld.\nDefault value: false");
        SWAP_OVERWORLD_WITH_END = BUILDER.define("swapOverWorldWithEnd", false);



        BUILDER.comment("\nSet the chance for end city generation to fail. Higher values means lower chance of spawning\nDefault value: 5");
        END_CITY_FAIL_CHANCE = BUILDER.define("endCityGenerationFailChance", 5);

        BUILDER.pop();

        BUILDER.push("Other");

        BUILDER.comment("\nShould the guide book be given to players when using an end gateway for the first time?\nDefault value: true");
        GIVE_GUIDE_BOOK = BUILDER.define("giveGuideBook", true);

        BUILDER.comment("\nShould natural enderman spawn rate be reduced?\nDefault value: true");
        REDUCE_ENDERMAN_SPAWN = BUILDER.define("reduceEndermanSpawn", true);

        BUILDER.pop();

        COMMON_CONFIG = BUILDER.build();
    }

    public static ForgeConfigSpec getConfig()
    {
        return COMMON_CONFIG;
    }

    public static boolean isVanillaEndIntegrationEnabled()
    {
    	return VANILLA_END_INTEGRATION_ENABLED.get();
    }

    public static int biomeSizeLand()
    {
    	return BIOME_SIZE_LAND.get();
    }

    public static int biomeSizeVoid()
    {
    	return BIOME_SIZE_VOID.get();
    }

    public static boolean isDragonFightEnabled()
    {
    	return DRAGON_FIGHT_ENABLED.get();
    }

    public static boolean shouldGenerateVanillaPortal()
    {
    	return GENERATE_VANILLA_PORTAL.get();
    }

    public static boolean shouldGenerateObsidianPillars()
    {
    	return GENERATE_OBSIDIAN_PILLARS.get();
    }

    public static boolean shouldGenerateObsidianPlatform()
    {
    	return GENERATE_OBSIDIAN_PLATFORM.get();
    }

    public static boolean isCustomChorusPlantEnabled()
    {
    	return CUSTOM_CHORUS_PLANT_ENABLED.get();
    }

    public static boolean isChorusInVanillaBiomesEnabled()
    {
    	return CHORUS_IN_VANILLA_BIOMES_ENABLED.get();
    }

    public static boolean isNewGeneratorEnabled()
    {
        return NEW_GENERATOR_ENABLED.get();
    }

    public static boolean noRingVoid()
    {
        return NO_RING_VOID.get();
    }

    public static boolean shouldGenerateCentralIsland()
    {
        return GENERATE_CENTRAL_ISLAND.get();
    }

    public static boolean swapOverworldWithEnd()
    {
        return SWAP_OVERWORLD_WITH_END.get();
    }

    public static boolean giveGuideBook()
    {
        return GIVE_GUIDE_BOOK.get();
    }

    public static boolean reduceEndermanSpawn()
    {
        return REDUCE_ENDERMAN_SPAWN.get();
    }

    public static int endCityFailChance()
    {
        return END_CITY_FAIL_CHANCE.get();
    }
    */
}
