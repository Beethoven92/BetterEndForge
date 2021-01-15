package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;

@Mixin(Minecraft.class)
public class MinecraftMixin 
{
	@Shadow
	public ClientPlayerEntity player;
	
	@Shadow
	public Screen currentScreen;
	
	@Shadow
	@Final
	public IngameGui ingameGUI;
	
	@Shadow
	public ClientWorld world;

	@Inject(method = "getBackgroundMusicSelector", at = @At("HEAD"), cancellable = true)
	private void be_getEndMusic(CallbackInfoReturnable<BackgroundMusicSelector> info) 
	{
		if (!(this.currentScreen instanceof WinGameScreen) && this.player != null) 
		{
			if (this.player.world.getDimensionKey() == World.THE_END) 
			{
				if (this.ingameGUI.getBossOverlay().shouldPlayEndBossMusic() && ModMathHelper.lengthSqr(this.player.getPosX(), this.player.getPosZ()) < 250000)
				{
					info.setReturnValue(BackgroundMusicTracks.DRAGON_FIGHT_MUSIC);
				}
				else 
				{
					BackgroundMusicSelector sound = (BackgroundMusicSelector) this.world.getBiomeManager().getBiomeAtPosition(this.player.getPosition()).getBackgroundMusic().orElse(BackgroundMusicTracks.END_MUSIC);
					info.setReturnValue(sound);
				}
				info.cancel();
			}
		}
	}
}
