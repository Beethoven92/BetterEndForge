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
	public Screen screen;
	
	@Shadow
	@Final
	public IngameGui gui;
	
	@Shadow
	public ClientWorld level;

	@Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
	private void be_getEndMusic(CallbackInfoReturnable<BackgroundMusicSelector> info) 
	{
		if (!(this.screen instanceof WinGameScreen) && this.player != null)
		{
			if (this.player.level.dimension() == World.END) 
			{
				if (this.gui.getBossOverlay().shouldPlayMusic() && ModMathHelper.lengthSqr(this.player.getX(), this.player.getZ()) < 250000)
				{
					info.setReturnValue(BackgroundMusicTracks.END_BOSS);
				}
				else 
				{
					BackgroundMusicSelector sound = (BackgroundMusicSelector) this.level.getBiomeManager().getNoiseBiomeAtPosition(this.player.blockPosition()).getBackgroundMusic().orElse(BackgroundMusicTracks.END);
					info.setReturnValue(sound);
				}
				info.cancel();
			}
		}
	}
}
