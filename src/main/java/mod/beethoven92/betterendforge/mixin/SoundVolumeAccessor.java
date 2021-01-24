package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.audio.LocatableSound;

@Mixin(LocatableSound.class)
public interface SoundVolumeAccessor 
{
	@Accessor("volume")
	void setVolume(float volume);
}
