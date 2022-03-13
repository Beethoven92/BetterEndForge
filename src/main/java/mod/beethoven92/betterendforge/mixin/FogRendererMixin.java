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
	private static float be_lastFogDensity;
	private static float be_fogDensity;
	private static float be_lerp;
	private static long be_time;
	
	@Shadow
	private static float fogRed;
	@Shadow
	private static float fogGreen;
	@Shadow
	private static float fogBlue;

	@Inject(method = "setupColor", at = @At("RETURN"))
	private static void be_onRender(ActiveRenderInfo activeRenderInfoIn, float partialTicks, ClientWorld worldIn,
			int renderDistanceChunks, float bossColorModifier, CallbackInfo info)
	{
		long l = Util.getMillis() - be_time;
		be_time += l;
		be_lerp += l * 0.001F;
		if (be_lerp > 1) be_lerp = 1;
		
		FluidState fluidState = activeRenderInfoIn.getFluidInCamera();
		if (fluidState.isEmpty() && worldIn.dimension().equals(World.END)) 
		{
			Entity entity = activeRenderInfoIn.getEntity();
			boolean skip = false;
			if (entity instanceof LivingEntity) 
			{
				EffectInstance effect = ((LivingEntity) entity).getEffect(Effects.NIGHT_VISION);
				skip = effect != null && effect.getDuration() > 0;
			}
			if (!skip) 
			{
				fogRed *= 4;
				fogGreen *= 4;
				fogBlue *= 4;
			}
		}
		
		BackgroundInfo.red = fogRed;
		BackgroundInfo.green = fogGreen;
		BackgroundInfo.blue = fogBlue;
	}
	

	@Inject(at = @At("HEAD"), remap = false, method = "setupFog(Lnet/minecraft/client/renderer/ActiveRenderInfo;Lnet/minecraft/client/renderer/FogRenderer$FogType;FZF)V", cancellable = true)
	private static void be_fogDensity(ActiveRenderInfo activeRenderInfoIn, FogRenderer.FogType fogTypeIn,
			float farPlaneDistance, boolean nearFog, float partialTicks, CallbackInfo info)
	{
		Entity entity = activeRenderInfoIn.getEntity();
		Biome biome = entity.level.getBiome(entity.blockPosition());
		FluidState fluidState = activeRenderInfoIn.getFluidInCamera();
		
		if (ClientOptions.useFogDensity() && biome.getBiomeCategory() == Category.THEEND && fluidState.isEmpty())
		{			
			BetterEndBiome endBiome = ModBiomes.getRenderBiome(biome);
			if (be_fogDensity == 0)
			{
				be_fogDensity = endBiome.getFogDensity();
				be_lastFogDensity = be_fogDensity;
			}
			if (be_lerp == 1)
			{
				be_lastFogDensity = be_fogDensity;
				be_fogDensity = endBiome.getFogDensity();
				be_lerp = 0;
			}
			
			float fog = MathHelper.lerp(be_lerp, be_lastFogDensity, be_fogDensity);
			BackgroundInfo.fog = fog;
			float start = farPlaneDistance * 0.75F / fog;
			float end = farPlaneDistance / fog;
			
			if (entity instanceof LivingEntity) 
			{
				LivingEntity le = (LivingEntity) entity;
				EffectInstance effect = le.getEffect(Effects.BLINDNESS);
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
