package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.client.ClientOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.LocatableSound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(MusicTicker.class)
public class MusicTickerMixin 
{
	@Shadow
	@Final
	private Minecraft client;
	
	@Shadow
	@Final
	private Random random;
	
	@Shadow
	private ISound currentMusic;
	
	@Shadow
	private int timeUntilNextMusic;
	
	private static float volume = 1;
	private static float srcVolume = 0;
	private static long time;

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void be_onTick(CallbackInfo info) 
	{
		if (ClientOptions.blendBiomeMusic())
		{
			BackgroundMusicSelector musicSound = client.getBackgroundMusicSelector();
			if (volume > 0 && beIsInEnd() && beShouldChangeSound(musicSound)) 
			{
				if (volume > 0) 
				{
					if (srcVolume < 0)
					{
						srcVolume = currentMusic.getVolume();
					}
					if (currentMusic instanceof LocatableSound) 
					{
						((SoundVolumeAccessor)currentMusic).setVolume(volume);
					}
					client.getSoundHandler().setSoundLevel(currentMusic.getCategory(), currentMusic.getVolume() * volume);
					long t = System.currentTimeMillis();
					if (volume == 1 && time == 0) 
					{
						time = t;
					}
					float delta = (t - time) * 0.0005F;
					time = t;
					volume -= delta;
					if (volume < 0) 
					{
						volume = 0;
					}
				}
				if (volume == 0) 
				{
					volume = 1;
					time = 0;
					srcVolume = -1;
					this.client.getSoundHandler().stop(this.currentMusic);
					this.timeUntilNextMusic = MathHelper.nextInt(this.random, 0, musicSound.getMinDelay() / 2);
					this.currentMusic = null;
				}
				if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) 
				{
					this.selectRandomBackgroundMusic(musicSound);
				}
				info.cancel();
			}
			else 
			{
				volume = 1;
			}
		}
	}
	
	private boolean beIsInEnd() 
	{
		return client.world != null && client.world.getDimensionKey().equals(World.THE_END);
	}
	
	private boolean beShouldChangeSound(BackgroundMusicSelector musicSound) 
	{
		return currentMusic != null && !musicSound.getSoundEvent().getName().equals(this.currentMusic.getSoundLocation()) && musicSound.shouldReplaceCurrentMusic();
	}
	
	@Shadow
	public void selectRandomBackgroundMusic(BackgroundMusicSelector selector) {}
}
