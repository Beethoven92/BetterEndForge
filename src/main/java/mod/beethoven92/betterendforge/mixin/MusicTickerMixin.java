package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.client.ClientOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	private Minecraft minecraft;
	
	@Shadow
	@Final
	private Random random;
	
	@Shadow
	private ISound currentMusic;
	
	@Shadow
	private int nextSongDelay;
	
	private static float be_volume = 1;
	private static float be_srcVolume = 0;
	private static long be_time;

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void be_onTick(CallbackInfo info) 
	{
		if (ClientOptions.blendBiomeMusic())
		{
			BackgroundMusicSelector musicSound = minecraft.getSituationalMusic();
			if (be_volume > 0 && beIsInEnd() && beShouldChangeSound(musicSound))
			{
				if (be_volume > 0)
				{
					if (be_srcVolume < 0)
					{
						be_srcVolume = currentMusic.getVolume();
					}
					if (currentMusic instanceof LocatableSound) 
					{
						((SoundVolumeAccessor)currentMusic).setVolume(be_volume);
					}
					minecraft.getSoundManager().updateSourceVolume(currentMusic.getSource(), currentMusic.getVolume() * be_volume);
					long t = System.currentTimeMillis();
					if (be_volume == 1 && be_time == 0)
					{
						be_time = t;
					}
					float delta = (t - be_time) * 0.0005F;
					be_time = t;
					be_volume -= delta;
					if (be_volume < 0)
					{
						be_volume = 0;
					}
				}
				if (be_volume == 0)
				{
					be_volume = 1;
					be_time = 0;
					be_srcVolume = -1;
					this.minecraft.getSoundManager().stop(this.currentMusic);
					this.nextSongDelay = MathHelper.nextInt(this.random, 0, musicSound.getMinDelay() / 2);
					this.currentMusic = null;
				}
				if (this.currentMusic == null && this.nextSongDelay-- <= 0)
				{
					this.startPlaying(musicSound);
				}
				info.cancel();
			}
			else 
			{
				be_volume = 1;
			}
		}
	}
	
	private boolean beIsInEnd() 
	{
		return minecraft.level != null && minecraft.level.dimension().equals(World.END);
	}
	
	private boolean beShouldChangeSound(BackgroundMusicSelector musicSound) 
	{
		return currentMusic != null && !musicSound.getEvent().getLocation().equals(this.currentMusic.getLocation()) && musicSound.replaceCurrentMusic();
	}
	
	@Shadow
	public void startPlaying(BackgroundMusicSelector selector) {}
}
