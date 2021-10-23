package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.client.ClientOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.util.BackgroundInfo;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.config.ClientConfig;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin 
{	
	private static float lastFogDensity;
	private static float fogDensity;
	private static float lerp;
	private static long time;
	
	@Shadow
	private static float red;
	@Shadow
	private static float green;
	@Shadow
	private static float blue;

	@Inject(method = "updateFogColor", at = @At("RETURN"))
	private static void onRender(ActiveRenderInfo activeRenderInfoIn, float partialTicks, ClientWorld worldIn, 
			int renderDistanceChunks, float bossColorModifier, CallbackInfo info)
	{
		long l = Util.milliTime() - time;
		time += l;
		lerp += l * 0.001F;
		if (lerp > 1) lerp = 1;
		
		FluidState fluidState = activeRenderInfoIn.getFluidState();
		if (fluidState.isEmpty() && worldIn.getDimensionKey().equals(World.THE_END)) 
		{
			Entity entity = activeRenderInfoIn.getRenderViewEntity();
			boolean skip = false;
			if (entity instanceof LivingEntity) 
			{
				EffectInstance effect = ((LivingEntity) entity).getActivePotionEffect(Effects.NIGHT_VISION);
				skip = effect != null && effect.getDuration() > 0;
			}
			if (!skip) 
			{
				red *= 4;
				green *= 4;
				blue *= 4;
			}
		}
		
		BackgroundInfo.red = red;
		BackgroundInfo.green = green;
		BackgroundInfo.blue = blue;
	}
	

	@Inject(at = @At("HEAD"), remap = false, method = "setupFog(Lnet/minecraft/client/renderer/ActiveRenderInfo;Lnet/minecraft/client/renderer/FogRenderer$FogType;FZF)V", cancellable = true)
	private static void fogDensity(ActiveRenderInfo activeRenderInfoIn, FogRenderer.FogType fogTypeIn, 
			float farPlaneDistance, boolean nearFog, float partialTicks, CallbackInfo info)
	{
		Entity entity = activeRenderInfoIn.getRenderViewEntity();
		Biome biome = entity.world.getBiome(entity.getPosition());
		FluidState fluidState = activeRenderInfoIn.getFluidState();
		
		if (ClientOptions.useFogDensity() && biome.getCategory() == Category.THEEND && fluidState.isEmpty())
		{			
			BetterEndBiome endBiome = ModBiomes.getRenderBiome(biome);
			if (fogDensity == 0) 
			{
				fogDensity = endBiome.getFogDensity();
				lastFogDensity = fogDensity;
			}
			if (lerp == 1) 
			{
				lastFogDensity = fogDensity;
				fogDensity = endBiome.getFogDensity();
				lerp = 0;
			}
			
			float fog = MathHelper.lerp(lerp, lastFogDensity, fogDensity);
			BackgroundInfo.fog = fog;
			float start = farPlaneDistance * 0.75F / fog;
			float end = farPlaneDistance / fog;
			
			if (entity instanceof LivingEntity) 
			{
				LivingEntity le = (LivingEntity) entity;
				EffectInstance effect = le.getActivePotionEffect(Effects.BLINDNESS);
				if (effect != null) 
				{
					int duration = effect.getDuration();
					if (duration > 20) 
					{
						start = 0;
						end *= 0.03F;
						BackgroundInfo.blindness = 1;
					}
					else 
					{
						float delta = (float) duration / 20F;
						BackgroundInfo.blindness = delta;
						start = MathHelper.lerp(delta, start, 0);
						end = MathHelper.lerp(delta, end, end * 0.03F);
					}
				}
				else 
				{
					BackgroundInfo.blindness = 0;
				}
			}
			
			RenderSystem.fogStart(start);
			RenderSystem.fogEnd(end);
			RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
			RenderSystem.setupNvFogDistance();
			info.cancel();
		}
	}
}
