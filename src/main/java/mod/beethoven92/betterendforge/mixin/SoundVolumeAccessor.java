package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;

@Mixin(AbstractSoundInstance.class)
public interface SoundVolumeAccessor 
{
	@Accessor("volume")
	void setVolume(float volume);
}
