package mod.beethoven92.betterendforge.client.event.mod;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.particles.GeyserParticle;
import mod.beethoven92.betterendforge.common.particles.GlowingSphereParticle;
import mod.beethoven92.betterendforge.common.particles.InfusionParticle;
import mod.beethoven92.betterendforge.common.particles.JungleSporeParticle;
import mod.beethoven92.betterendforge.common.particles.PortalSphereParticle;
import mod.beethoven92.betterendforge.common.particles.SnowflakeParticle;
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
		Minecraft mc = Minecraft.getInstance();
		
		mc.particleEngine.register(ModParticleTypes.INFUSION.get(), (sprite) -> new InfusionParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.PORTAL_SPHERE.get(), (sprite) -> new PortalSphereParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.GLOWING_SPHERE.get(), (sprite) -> new GlowingSphereParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.AMBER_SPHERE.get(), (sprite) -> new GlowingSphereParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.TENANEA_PETAL.get(), (sprite) -> new TenaneaPetalParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.GEYSER_PARTICLE.get(), (sprite) -> new GeyserParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.SULPHUR_PARTICLE.get(), (sprite) -> new SulphurParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.SNOWFLAKE_PARTICLE.get(), (sprite) -> new SnowflakeParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.JUNGLE_SPORE.get(), (sprite) -> new JungleSporeParticle.Factory(sprite));
		mc.particleEngine.register(ModParticleTypes.FIREFLY.get(), (sprite) -> new JungleSporeParticle.Factory(sprite));
		//mc.particles.registerFactory(ModParticleTypes.SMARAGDANT.get(), (sprite) -> new GlowingSphereParticle.Factory(sprite));
	}
}
