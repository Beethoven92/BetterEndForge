package mod.beethoven92.betterendforge.client.event;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.particles.GlowingSphereParticle;
import mod.beethoven92.betterendforge.common.particles.InfusionParticle;
import mod.beethoven92.betterendforge.common.particles.PortalSphereParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleFactoryRegistration 
{
	@SubscribeEvent
	public static void RegisterParticleFactory(ParticleFactoryRegisterEvent event)
	{
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.INFUSION.get(), (sprite) -> new InfusionParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.PORTAL_SPHERE.get(), (sprite) -> new PortalSphereParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.GLOWING_SPHERE.get(), (sprite) -> new GlowingSphereParticle.Factory(sprite));
	}
}
