package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents 
{
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = 
			DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BetterEnd.MOD_ID);
	
	// MUSIC TRACKS
	public static final RegistryObject<SoundEvent> MUSIC_FOREST = 
			registerSoundEvent("betterendforge.music.forest");
	public static final RegistryObject<SoundEvent> MUSIC_WATER = 
			registerSoundEvent("betterendforge.music.water");
	public static final RegistryObject<SoundEvent> MUSIC_DARK = 
			registerSoundEvent("betterendforge.music.dark");
	public static final RegistryObject<SoundEvent> MUSIC_OPENSPACE = 
			registerSoundEvent("betterendforge.music.openspace");
	
	// AMBIENT SOUNDS
	public static final RegistryObject<SoundEvent> AMBIENT_FOGGY_MUSHROOMLAND = 
			registerSoundEvent("betterendforge.ambient.foggy_mushroomland");
	public static final RegistryObject<SoundEvent> AMBIENT_CHORUS_FOREST = 
			registerSoundEvent("betterendforge.ambient.chorus_forest");
	public static final RegistryObject<SoundEvent> AMBIENT_MEGALAKE = 
			registerSoundEvent("betterendforge.ambient.megalake");
	public static final RegistryObject<SoundEvent> AMBIENT_DUST_WASTELANDS = 
			registerSoundEvent("betterendforge.ambient.dust_wastelands");
	public static final RegistryObject<SoundEvent> AMBIENT_MEGALAKE_GROVE = 
			registerSoundEvent("betterendforge.ambient.megalake_grove");
	public static final RegistryObject<SoundEvent> AMBIENT_BLOSSOMING_SPIRES = 
			registerSoundEvent("betterendforge.ambient.blossoming_spires");
	public static final RegistryObject<SoundEvent> AMBIENT_SULPHUR_SPRINGS = 
			registerSoundEvent("betterendforge.ambient.sulphur_springs");
	public static final RegistryObject<SoundEvent> AMBIENT_UMBRELLA_JUNGLE = 
			registerSoundEvent("betterendforge.ambient.umbrella_jungle");
	public static final RegistryObject<SoundEvent> AMBIENT_GLOWING_GRASSLANDS = 
			registerSoundEvent("betterendforge.ambient.glowing_grasslands");
	
	// ENTITY SOUNDS
	public static final RegistryObject<SoundEvent> ENTITY_DRAGONFLY = 
			registerSoundEvent("betterendforge.entity.dragonfly");
	public static final RegistryObject<SoundEvent> ENTITY_SHADOW_WALKER = 
			registerSoundEvent("betterendforge.entity.shadow_walker");
	public static final RegistryObject<SoundEvent> ENTITY_SHADOW_WALKER_DAMAGE = 
			registerSoundEvent("betterendforge.entity.shadow_walker_damage");
	public static final RegistryObject<SoundEvent> ENTITY_SHADOW_WALKER_DEATH = 
			registerSoundEvent("betterendforge.entity.shadow_walker_death");

	private static RegistryObject<SoundEvent> registerSoundEvent(String name)
	{
		return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(BetterEnd.MOD_ID, name)));
	}
}
