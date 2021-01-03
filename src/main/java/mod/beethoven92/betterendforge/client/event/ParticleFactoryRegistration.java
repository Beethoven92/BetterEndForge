package mod.beethoven92.betterendforge.client.event;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.particles.GeyserParticle;
import mod.beethoven92.betterendforge.common.particles.GlowingSphereParticle;
import mod.beethoven92.betterendforge.common.particles.InfusionParticle;
import mod.beethoven92.betterendforge.common.particles.PortalSphereParticle;
import mod.beethoven92.betterendforge.common.particles.SulphurParticle;
import mod.beethoven92.betterendforge.common.particles.TenaneaPetalParticle;
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
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.INFUSION.get(), 
				(sprite) -> new InfusionParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.PORTAL_SPHERE.get(), 
				(sprite) -> new PortalSphereParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.GLOWING_SPHERE.get(), 
				(sprite) -> new GlowingSphereParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.AMBER_SPHERE.get(), 
				(sprite) -> new GlowingSphereParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.TENANEA_PETAL.get(), 
				(sprite) -> new TenaneaPetalParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.GEYSER_PARTICLE.get(), 
				(sprite) -> new GeyserParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.SULPHUR_PARTICLE.get(), 
				(sprite) -> new SulphurParticle.Factory(sprite));
	}
}
